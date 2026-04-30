package com.github.maximuslotro.lotrrextended.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;

import lotr.common.init.LOTRDimensions;
import net.minecraft.nbt.CompoundNBT;
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
	private volatile boolean running = true;
	private volatile boolean finished;
	private volatile int totalChunks;
	private volatile int deletableChunks;
	private volatile int protectedChunks;
	private static final Pattern REGEX = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
	private final Set<Long> protectedRegionsIndex = new HashSet<>();
	private final MinecraftServer server;
	private volatile boolean dryRun = true;
	private volatile Stage stage = Stage.SCANNING_CHUNKS;

	private volatile int totalRegions;
	private volatile int deletedRegions;
	private volatile int protectedRegions;
	private volatile int skippedLoadedRegions;

	private static ExtendedWorldRegenEngine ACTIVE_ENGINE = null;
	private static UUID ACTIVE_PLAYER = null;

	public ExtendedWorldRegenEngine(boolean shouldDryRun) {
		this.levels = ImmutableSet.of(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY);
		this.dataFixer = DataFixesManager.getDataFixer();
		server = ServerLifecycleHooks.getCurrentServer();
		this.levelStorage = server.storageSource;
		this.dryRun = shouldDryRun;
		this.thread = THREAD_FACTORY.newThread(this::work);
		this.thread.setUncaughtExceptionHandler((p_219956_1_, p_219956_2_) -> {
			LOGGER.error("Error pruning world", p_219956_2_);
			this.finished = true;
		});
		this.thread.start();
	}

	public void cancel() {
		this.running = false;

		try {
			this.thread.join();
		} catch (InterruptedException interruptedexception) {
		}

	}

	private void markProtectedChunks(int blockX, int blockZ, int radius) {
		int minX = blockX - radius;
		int maxX = blockX + radius;
		int minZ = blockZ - radius;
		int maxZ = blockZ + radius;

		int minChunkX = minX >> 4;
		int maxChunkX = maxX >> 4;
		int minChunkZ = minZ >> 4;
		int maxChunkZ = maxZ >> 4;

		for (int cx = minChunkX; cx <= maxChunkX; cx++) {
			for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
				protectedRegionsIndex.add(getRegionKey(cx, cz));
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
			Builder<RegistryKey<World>, ListIterator<ChunkPos>> worldChunkListBuilder = ImmutableMap.builder();

			this.stage = Stage.SCANNING_CHUNKS;
			for(RegistryKey<World> registrykey : this.levels) {
				List<ChunkPos> list = this.getAllChunkPos(registrykey);
				worldChunkListBuilder.put(registrykey, list.listIterator());
			}

			if (this.totalChunks == 0) {
				this.finished = true;
			} else {
				float totalChunkCount = (float)this.totalChunks;
				ImmutableMap<RegistryKey<World>, ListIterator<ChunkPos>> worldChunkList = worldChunkListBuilder.build();
				Builder<RegistryKey<World>, ChunkLoader> worldChunkLoaderBuilder = ImmutableMap.builder();

				for(RegistryKey<World> registrykey1 : this.levels) {
					File file1 = this.levelStorage.getDimensionPath(registrykey1);
					worldChunkLoaderBuilder.put(registrykey1, new ChunkLoader(new File(file1, "region"), this.dataFixer, true));
				}
				ImmutableMap<RegistryKey<World>, ChunkLoader> worldChunkLoaders = worldChunkLoaderBuilder.build();
				long currentMilisecond = Util.getMillis();
				this.stage = Stage.CHECKING_CHUNKS;
				while(this.running) {
					boolean shouldKeepRunning = false;
					for(RegistryKey<World> worldKey : this.levels) {
						ListIterator<ChunkPos> chunkiterator = worldChunkList.get(worldKey);
						ChunkLoader chunkloader = worldChunkLoaders.get(worldKey);
						if (chunkiterator.hasNext()) {
							ChunkPos chunkpos = chunkiterator.next();
							boolean shouldSave = false;

							try {
								CompoundNBT nbt = chunkloader.read(chunkpos);
								if (nbt == null) {
									++this.protectedChunks;
									shouldKeepRunning = true;
									continue;
								}
								CompoundNBT level = nbt.getCompound("Level");
								ListNBT tileEntities = level.getList("TileEntities", 10);

								for (int blockEntityIndex = 0; blockEntityIndex < tileEntities.size(); blockEntityIndex++) {
									CompoundNBT te = tileEntities.getCompound(blockEntityIndex);

									// Check it actually has your data
									if (te.contains("ProtectionData", 10)) {
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
									   
										markProtectedChunks(x, z, radius);
										shouldSave=true;
									}
								}
							}catch(Exception e) {
								LOGGER.error("Failed to read chunk {}, protecting region", chunkpos, e);
								protectedRegionsIndex.add(getRegionKey(chunkpos.x, chunkpos.z));
								shouldSave=true;
							}

							if (shouldSave) {
								++this.protectedChunks;
							} else {
								++this.deletableChunks;
							}

							shouldKeepRunning = true;
						}
					}

					if (!shouldKeepRunning) {
						this.running = false;
					}
				}

				for(ChunkLoader chunkloader1 : worldChunkLoaders.values()) {
					try {
						chunkloader1.close();
					} catch (IOException ioexception) {
						LOGGER.error("Error processing chunk", (Throwable)ioexception);
					}
				}
				this.stage = Stage.DELETING_REGIONS;
				for (RegistryKey<World> worldKey : this.levels) {
					deleteUnprotectedRegions(worldKey);
				}

				currentMilisecond = Util.getMillis() - currentMilisecond;
				LOGGER.info("World pruning finished after {} ms", (long)currentMilisecond);
				LOGGER.info("Found {} total chunks", this.totalChunks);
				LOGGER.info("Found {} deletable chunks", this.deletableChunks);
				LOGGER.info("Found {} protected chunks", this.protectedChunks);
				LOGGER.info("Found {} total regions", this.totalRegions);
				LOGGER.info("Found {} loaded regions", this.skippedLoadedRegions);
				LOGGER.info("Found {} protected regions", this.protectedRegions);
				if (!isDryRun()) {
					LOGGER.info("Deleated regions {}", deletedRegions);
				} 
				this.finished = true;
			}
		} catch (Throwable t) {
			LOGGER.error("Fatal error during pruning", t);
		} finally {
			this.finished = true;
		}
		this.stage = Stage.FINISHED;
	}

	private void deleteUnprotectedRegions(RegistryKey<World> worldKey) {
		File regionDir = new File(this.levelStorage.getDimensionPath(worldKey), "region");

		File[] files = regionDir.listFiles((dir, name) -> name.endsWith(".mca"));
		if (files == null) return;
		for (File file : files) {
			Matcher matcher = REGEX.matcher(file.getName());
			if (!matcher.matches()) continue;

			int regionX = Integer.parseInt(matcher.group(1));
			int regionZ = Integer.parseInt(matcher.group(2));

			long key = (((long)regionX) << 32) | (regionZ & 0xffffffffL);

			if (!protectedRegionsIndex.contains(key)) {
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

			for(File file3 : afile) {
				this.totalRegions += 1;
				Matcher matcher = REGEX.matcher(file3.getName());
				if (matcher.matches()) {
					int i = Integer.parseInt(matcher.group(1)) << 5;
					int j = Integer.parseInt(matcher.group(2)) << 5;

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

	public int getDeletable() {
		return this.deletableChunks;
	}

	public int getProtected() {
		return this.protectedChunks;
	}

	public int getTotalRegions() {
	    return totalRegions;
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
		FINISHED
	}

}