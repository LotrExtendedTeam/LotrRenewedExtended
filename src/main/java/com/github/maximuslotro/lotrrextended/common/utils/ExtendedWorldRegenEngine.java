package com.github.maximuslotro.lotrrextended.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;

import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedWorldRegenEngine {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ThreadFactory THREAD_FACTORY = (new ThreadFactoryBuilder()).setDaemon(true).build();
	private final ImmutableSet<RegistryKey<World>> levels;
	private final SaveFormat.LevelSave levelStorage;
	private final Thread thread;
	private final DataFixer dataFixer;
	private volatile boolean checkingLoop = true;
	private volatile boolean canceled = false;
	private volatile boolean finished;
	private volatile int totalChunks;
	private volatile int deletableChunks;
	private volatile int bannerProtectedChunks;
	private volatile int waypointProtectedChunks;
	private volatile int spawnProtectedChunks;
	private volatile int skippedErrorChunks;
	private static final Pattern REGEX = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
	private final Map<RegistryKey<World>, Set<Long>> protectedRegionsIndex = new HashMap<>();
	private final MinecraftServer server;
	private volatile boolean dryRun = true;
	private volatile Stage stage = Stage.SCANNING_CHUNKS;

	private volatile int totalRegions;
	private volatile int currentRegions;
	private volatile int deletedRegions;
	private volatile int protectedRegions;
	private volatile int skippedLoadedRegions;

	private volatile ImmutableMap<RegistryKey<World>, ListIterator<ChunkPos>> worldChunkList;
	private final File checkpointFile;
	private final Set<Long> scannedRegions = new HashSet<>();
	private final Set<Long> deletedRegionsSet = new HashSet<>();

	public ExtendedWorldRegenEngine(boolean shouldDryRun) {
		this.levels = ImmutableSet.of(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY);
		this.dataFixer = DataFixesManager.getDataFixer();
		server = ServerLifecycleHooks.getCurrentServer();
		this.levelStorage = server.storageSource;
		this.dryRun = shouldDryRun;
		this.checkpointFile = new File(this.levelStorage.getDimensionPath(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY), "regen_checkpoint.dat");
		this.thread = THREAD_FACTORY.newThread(this::work);
		this.thread.setName("ExtendedWorldRegenEngine");
		this.thread.setUncaughtExceptionHandler((p_219956_1_, p_219956_2_) -> {
			LOGGER.error("Error pruning world", p_219956_2_);
			this.finished = true;
		});
		this.thread.start();
	}

	public void cancel() {
		this.checkingLoop = false;
		this.canceled = true;
		try {
			this.thread.join();
		} catch (InterruptedException interruptedexception) {
			interruptedexception.printStackTrace();
		}
	}

	private void markProtectedChunks(RegistryKey<World> worldKey, int blockX, int blockZ, int radius) {
		int minX = blockX - radius;
		int maxX = blockX + radius;
		int minZ = blockZ - radius;
		int maxZ = blockZ + radius;

		int minChunkX = minX >> 4;
		int maxChunkX = maxX >> 4;
		int minChunkZ = minZ >> 4;
		int maxChunkZ = maxZ >> 4;

		Set<Long> set = protectedRegionsIndex.get(worldKey);

		for (int cx = minChunkX; cx <= maxChunkX; cx++) {
			for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
				set.add(getRegionKey(cx, cz));
			}
		}
	}

	private long getRegionKey(int chunkX, int chunkZ) {
		int regionX = chunkX >> 5; // divide by 32
			int regionZ = chunkZ >> 5;
			return (((long)regionX) << 32) | (regionZ & 0xffffffffL);
	}

	private void work() {
		try {
			this.totalChunks = 0;
			this.totalRegions = 0;
			this.currentRegions = 0;

			boolean resumed = loadCheckpoint();
	        // CRITICAL: If we resumed during checking or deleting, 
	        // we still need the file list and chunk positions in memory.
	        if (resumed && stage == Stage.CHECKING_CHUNKS) {
	            LOGGER.info("Reconstructing chunk list for resumed task...");
	            scanChunks(); 
	        } else if (!resumed) {
	            this.stage = Stage.SCANNING_CHUNKS;
	        }

			long currentMilisecond = Util.getMillis();
			while(!canceled && !finished) {
				switch(stage) {
				case SCANNING_CHUNKS:
					scanChunks();
				case CHECKING_CHUNKS:
					checkChunks();
				case DELETING_REGIONS:
					deleteRegions();
				default:
					break;
				}
			}
			if(finished) {
				this.stage = Stage.FINISHED;
			}else {
				this.stage = Stage.CANCELED;
			}
			if (stage == Stage.FINISHED && checkpointFile.exists()) {
				checkpointFile.delete();
			}
			currentMilisecond = Util.getMillis() - currentMilisecond;
			LOGGER.info("World pruning finished after {} ms", (long)currentMilisecond);
			LOGGER.info("Found {} total chunks", this.totalChunks);
			LOGGER.info("Found {} deletable chunks", this.deletableChunks);
			LOGGER.info("Found {} error protected chunks", this.skippedErrorChunks);
			LOGGER.info("Found {} spawn protected chunks", this.spawnProtectedChunks);
			LOGGER.info("Found {} banner protected chunks", this.bannerProtectedChunks);
			LOGGER.info("Found {} waypoint protected chunks", this.waypointProtectedChunks);
			LOGGER.info("Found {} total regions", this.totalRegions);
			LOGGER.info("Found {} loaded regions", this.skippedLoadedRegions);
			LOGGER.info("Found {} protected regions", this.protectedRegions);
			if (!isDryRun()) {
				LOGGER.info("Deleated regions {}", deletedRegions);
			} 
		} catch (Throwable t) {
			LOGGER.error("Fatal error during pruning", t);
			this.stage = Stage.CANCELED;
		} finally {
			this.finished = true;
		}
	}

	private void scanChunks() {
		Builder<RegistryKey<World>, ListIterator<ChunkPos>> worldChunkListBuilder = ImmutableMap.builder();
		for(RegistryKey<World> registrykey : this.levels) {
			List<ChunkPos> list = this.getAllChunkPos(registrykey);
			worldChunkListBuilder.put(registrykey, list.listIterator());
		}
		if(canceled) {
			return;
		}
		for (RegistryKey<World> key : levels) {
			protectedRegionsIndex.put(key, new HashSet<>());
		}
		if (this.totalChunks == 0) {
			this.finished = true;
			return;
		}
		worldChunkList = worldChunkListBuilder.build();
		// Only advance the stage if we weren't already further ahead
	    if (this.stage == Stage.SCANNING_CHUNKS) {
	        this.stage = Stage.CHECKING_CHUNKS;
	    }
	}

	private void checkChunks() {
		Builder<RegistryKey<World>, ChunkLoader> worldChunkLoaderBuilder = ImmutableMap.builder();

		for(RegistryKey<World> registrykey1 : this.levels) {
			File file1 = this.levelStorage.getDimensionPath(registrykey1);
			worldChunkLoaderBuilder.put(registrykey1, new ChunkLoader(new File(file1, "region"), this.dataFixer, true));
		}
		ImmutableMap<RegistryKey<World>, ChunkLoader> worldChunkLoaders = worldChunkLoaderBuilder.build();
		this.stage = Stage.CHECKING_CHUNKS;
		
		long lastRegionKey = -1;
	    int chunksSinceLastSave = 0;
		while(this.checkingLoop) {
			boolean shouldKeepRunning = false;
			for(RegistryKey<World> worldKey : this.levels) {
				ListIterator<ChunkPos> chunkiterator = worldChunkList.get(worldKey);
				ChunkLoader chunkloader = worldChunkLoaders.get(worldKey);
				if (chunkiterator.hasNext()) {
					if(canceled) {
						break;
					}
					ChunkPos chunkpos = chunkiterator.next();
					long currentRegionKey = getRegionKey(chunkpos.x, chunkpos.z);
					if (scannedRegions.contains(currentRegionKey)) {
	                    continue; 
	                }
					// If we just entered a NEW region, mark the OLD one as finished
	                if (lastRegionKey != -1 && currentRegionKey != lastRegionKey) {
	                    scannedRegions.add(lastRegionKey);
	                    saveCheckpoint(); // Save progress every time a region is finished
	                }
	                lastRegionKey = currentRegionKey;
	                
					boolean protectedChunk = false;
					try {
						CompoundNBT nbt = chunkloader.read(chunkpos);
						if (nbt == null) {
							++this.skippedErrorChunks;
							shouldKeepRunning = true;
							continue;
						}
						CompoundNBT level = nbt.getCompound("Level");
						ListNBT tileEntities = level.getList("TileEntities", 10);

						for (int blockEntityIndex = 0; blockEntityIndex < tileEntities.size(); blockEntityIndex++) {
							CompoundNBT te = tileEntities.getCompound(blockEntityIndex);

							// Check it actually has your data
							if ((te.getString("id").equals(ExtendedTileEntities.BANNER_STANDING.getId().toString()) ||
								te.getString("id").equals("lotr:banner_s_tileentity")) && te.contains("ProtectionData", 10)) {
								CompoundNBT protection = te.getCompound("ProtectionData");
								if (!protection.getBoolean("used")) {
									continue;
								}
								int radius;
								// Handle old versions (no radius)
								if (protection.contains("radius", 3)) { //int
									radius = protection.getInt("radius");
								} else {
									radius = 32; // default fallback
								}
								int x = te.getInt("x");
								int z = te.getInt("z");

								String playerName = "Unknown";
								if (protection.contains("placedPlayerName", 8)) { //string
									playerName = protection.getString("placedPlayerName");
								} else if (protection.contains("placedPlayer", 11)) { //UUID
									playerName = protection.getUUID("placedPlayer").toString();
								}
								LOGGER.info("Protection found by {} at {},{} (radius {})", playerName, x, z, radius);

								markProtectedChunks(worldKey, x, z, radius);
								++this.bannerProtectedChunks;
								protectedChunk=true;
							}else if(te.getString("id").equals(LOTRTileEntities.CUSTOM_WAYPOINT_MARKER.getId().toString())){
								int x = te.getInt("x");
								int z = te.getInt("z");
								LOGGER.info("Waypoint found at {},{} (radius {})", x, z, 1);
								markProtectedChunks(worldKey, x, z, 8);
								++this.waypointProtectedChunks;
								protectedChunk=true;
							}else if(ExtendedServerConfig.enableExtendedSpawnProtection.get() && worldKey == LOTRDimensions.MIDDLE_EARTH_WORLD_KEY){
								int x = te.getInt("x");
								int z = te.getInt("z");
								int k = Math.max(x, z);
								if(k <= ExtendedServerConfig.middleEarthSpawnProtectionRadius.get()) {
									LOGGER.info("Spawn found at {},{} (radius {})", x, z, 1);
									markProtectedChunks(worldKey, x, z, 0);
									++this.spawnProtectedChunks;
									protectedChunk=true;
								}
							}
						}
					}catch(Exception e) {
						LOGGER.error("Failed to read chunk {}, protecting region", chunkpos, e);
						protectedRegionsIndex.get(worldKey).add(getRegionKey(chunkpos.x, chunkpos.z));
						++this.skippedErrorChunks;
						protectedChunk=true;
					}

					if (!protectedChunk) {
						++this.deletableChunks;
					}

					shouldKeepRunning = true;

					// Periodic checkpoint during long scanning phase
					chunksSinceLastSave++;
	                if (chunksSinceLastSave >= 500) { // Safety save every 500 chunks
	                    saveCheckpoint();
	                    chunksSinceLastSave = 0;
	                }
				}
			}

			if (!shouldKeepRunning) {
				this.checkingLoop = false;
			}
		}
		if (lastRegionKey != -1) {
			scannedRegions.add(lastRegionKey);
		}
	    saveCheckpoint();
		for(ChunkLoader chunkloader1 : worldChunkLoaders.values()) {
			try {
				chunkloader1.close();
			} catch (IOException ioexception) {
				LOGGER.error("Error processing chunk", (Throwable)ioexception);
			}
		}
		if(canceled) {
			return;
		}
		this.stage = Stage.DELETING_REGIONS;
	}

	private void deleteRegions() {
		for (RegistryKey<World> worldKey : this.levels) {
			Set<Long> protectedSet = protectedRegionsIndex.get(worldKey);
			deleteUnprotectedRegions(worldKey, protectedSet);
			if(canceled) {
				break;
			}
		}
		this.finished = true;
	}

	private void deleteUnprotectedRegions(RegistryKey<World> worldKey, Set<Long> protectedSet) {
		File regionDir = new File(this.levelStorage.getDimensionPath(worldKey), "region");

		File[] files = regionDir.listFiles((dir, name) -> name.endsWith(".mca"));
		if (files == null) return;
		for (File file : files) {
			if(canceled) {
				break;
			}
			Matcher matcher = REGEX.matcher(file.getName());
			if (!matcher.matches()) continue;

			int regionX = Integer.parseInt(matcher.group(1));
			int regionZ = Integer.parseInt(matcher.group(2));

			long key = (((long)regionX) << 32) | (regionZ & 0xffffffffL);

			// Skip if this region was already handled in a previous run
			if (deletedRegionsSet.contains(key)) continue;

			if (!protectedSet.contains(key)) {
				if (isRegionLoaded(server, worldKey, regionX, regionZ)) {
					LOGGER.info("Skipping loaded region {}", file.getName());
					this.skippedLoadedRegions++;
					continue;
				}
				if (isDryRun()) {
					//LOGGER.info("[DRY RUN] Would delete {}", file.getName());
				} else {
					//LOGGER.info("Deleting region {}", file.getName());
					if (!file.delete()) {
						LOGGER.warn("Failed to delete region {}", file.getName());
					}
				}
				this.deletedRegions++;
			}else {
				this.protectedRegions++;
			}
			deletedRegionsSet.add(key);
		}
	}

	private boolean isRegionLoaded(MinecraftServer server, RegistryKey<World> worldKey, int regionX, int regionZ) {
		ServerWorld world = server.getLevel(worldKey);
		if (world == null) return false;

		int minChunkX = regionX << 5;
		int minChunkZ = regionZ << 5;

		for (int cx = minChunkX; cx < minChunkX + 32; cx++) {
			for (int cz = minChunkZ; cz < minChunkZ + 32; cz++) {
				if (world.getChunkSource().hasChunk(cx, cz)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<ChunkPos> getAllChunkPos(RegistryKey<World> p_233532_1_) {
		File file1 = this.levelStorage.getDimensionPath(p_233532_1_);
		File file2 = new File(file1, "region");
		File[] afile = file2.listFiles((dir, name) -> {return name.endsWith(".mca");});
		if (afile == null) {
			return ImmutableList.of();
		} else {
			List<ChunkPos> list = Lists.newArrayList();
			this.totalRegions += afile.length;
			for(File file3 : afile) {
				if(canceled) {
					break;
				}
				this.currentRegions += 1;
				Matcher matcher = REGEX.matcher(file3.getName());
				if (matcher.matches()) {
					int i = Integer.parseInt(matcher.group(1)) << 5;
					int j = Integer.parseInt(matcher.group(2)) << 5;
					long key = (((long) i) << 32) | (j & 0xffffffffL);

		            // OPTIMIZATION: Skip the expensive RegionFile opening if we already scanned this
		            if (scannedRegions.contains(key)) {
		                // Note: totalChunks won't be perfectly accurate for the UI progress bar 
		                continue; 
		            }
					try (RegionFile regionfile = new RegionFile(file3, file2, true)) {
						for(int k = 0; k < 32; ++k) {
							for(int l = 0; l < 32; ++l) {
								ChunkPos chunkpos = new ChunkPos(k + i, l + j);
								if (regionfile.doesChunkExist(chunkpos)) {
									list.add(chunkpos);
									this.totalChunks += 1;
								}
							}
						}
					} catch (Throwable throwable) {
						LOGGER.error("Failed reading region file {}", file3.getName(), throwable);
					}
				}
			}

			return list;
		}
	}

	public boolean isFinished() {
		return this.finished;
	}

	public int getTotalChunks() {
		return this.totalChunks;
	}

	public int getDeletableChunks() {
		return this.deletableChunks;
	}

	public int getBannerProtected() {
		return this.bannerProtectedChunks;
	}

	public int getWaypointProtected() {
		return this.waypointProtectedChunks;
	}

	public int getSpawnProtected() {
		return this.spawnProtectedChunks;
	}

	public int getErrorProtected() {
		return this.skippedErrorChunks;
	}

	public int getTotalRegions() {
		return totalRegions;
	}

	public int getCurrentRegions() {
		return currentRegions;
	}

	public int getProtectedRegions() {
		return protectedRegions;
	}

	public int getSkippedLoadedRegions() {
		return skippedLoadedRegions;
	}

	public int getDeletedRegions() {
		return deletedRegions;
	}

	public Stage getStage() {
		return this.stage;
	}

	public boolean isDryRun() {
		return dryRun;
	}

	public enum Stage {
		SCANNING_CHUNKS,     // building chunk list
		CHECKING_CHUNKS,     // reading NBT / finding protections
		DELETING_REGIONS,    // removing region files
		CANCELED,
		FINISHED
	}
	private void saveCheckpoint() {
		try {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("Stage", this.stage.name());
			nbt.putInt("TotalChunks", this.totalChunks);
			nbt.putInt("DeletableChunks", this.deletableChunks);

			// Save the protected regions found so far
			for (Map.Entry<RegistryKey<World>, Set<Long>> entry : protectedRegionsIndex.entrySet()) {
				nbt.putLongArray("Protected_" + entry.getKey().location().toString(), Lists.newArrayList(entry.getValue()));
			}
			nbt.putLongArray("ScannedRegions", Lists.newArrayList(scannedRegions));
			// Save progress (which regions we've already fully processed)
			nbt.putLongArray("ProcessedRegions", Lists.newArrayList(deletedRegionsSet));

			CompressedStreamTools.writeCompressed(nbt, checkpointFile);
		} catch (IOException e) {
			LOGGER.error("Failed to save regen checkpoint", e);
		}
	}

	private boolean loadCheckpoint() {
		if (!checkpointFile.exists()) return false;
		try {
			CompoundNBT nbt = CompressedStreamTools.readCompressed(checkpointFile);
			this.stage = Stage.valueOf(nbt.getString("Stage"));
			this.totalChunks = nbt.getInt("TotalChunks");
			this.deletableChunks = nbt.getInt("DeletableChunks");

			// Restore protected indices
			for (RegistryKey<World> key : levels) {
				String nbtKey = "Protected_" + key.location().toString();
				if (nbt.contains(nbtKey)) {
					long[] array = nbt.getLongArray(nbtKey);
					Set<Long> set = new HashSet<>();
					for (long l : array) set.add(l);
					protectedRegionsIndex.put(key, set);
				}
			}
			// Restore processed progress
			long[] scanned = nbt.getLongArray("ScannedRegions");
			for (long l : scanned) {
				scannedRegions.add(l);
			}
			// Restore processed progress
			long[] processed = nbt.getLongArray("ProcessedRegions");
			for (long l : processed) {
				deletedRegionsSet.add(l);
			}
			LOGGER.info("Resuming world regen from checkpoint: Stage {}", this.stage);
			return true;
		} catch (Exception e) {
			LOGGER.error("Failed to load checkpoint, starting fresh", e);
			return false;
		}
	}
}