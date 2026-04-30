package com.github.maximuslotro.lotrrextended.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
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

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import lotr.common.init.LOTRDimensions;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedWorldRegenEngine {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ThreadFactory THREAD_FACTORY = (new ThreadFactoryBuilder()).setDaemon(true).build();
	private final ImmutableSet<RegistryKey<World>> levels;
	private final boolean eraseCache;
	private final SaveFormat.LevelSave levelStorage;
	private final Thread thread;
	private final DataFixer dataFixer;
	private volatile boolean running = true;
	private volatile boolean finished;
	private volatile float progress;
	private volatile int totalChunks;
	private volatile int converted;
	private volatile int skipped;
	private final Object2FloatMap<RegistryKey<World>> progressMap = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap<>(Util.identityStrategy()));
	private volatile ITextComponent status = new TranslationTextComponent("optimizeWorld.stage.counting");
	private static final Pattern REGEX = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
	private final DimensionSavedDataManager overworldDataStorage;
	private final Set<ChunkPos> protectedChunks = new HashSet<>();

	public ExtendedWorldRegenEngine() {
		this.levels = ImmutableSet.of(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY);
		this.eraseCache = false;
		this.dataFixer = DataFixesManager.getDataFixer();
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		this.levelStorage = server.storageSource;
		this.overworldDataStorage = new DimensionSavedDataManager(new File(this.levelStorage.getDimensionPath(World.OVERWORLD), "data"), dataFixer);
		this.thread = THREAD_FACTORY.newThread(this::work);
		this.thread.setUncaughtExceptionHandler((p_219956_1_, p_219956_2_) -> {
			LOGGER.error("Error regening world", p_219956_2_);
			this.status = new TranslationTextComponent("Failed Regening World");
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
				protectedChunks.add(new ChunkPos(cx, cz));
			}
		}
	}

	private void work() {
		this.totalChunks = 0;
		Builder<RegistryKey<World>, ListIterator<ChunkPos>> worldChunkListBuilder = ImmutableMap.builder();

		for(RegistryKey<World> registrykey : this.levels) {
			List<ChunkPos> list = this.getAllChunkPos(registrykey);
			worldChunkListBuilder.put(registrykey, list.listIterator());
			this.totalChunks += list.size();
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
			this.status = new TranslationTextComponent("optimizeWorld.stage.upgrading");

			for (RegistryKey<World> worldKey : this.levels) {
				List<ChunkPos> chunks = this.getAllChunkPos(worldKey);
				ChunkLoader chunkloader = worldChunkLoaders.get(worldKey);
				for (ChunkPos chunkPos : chunks) {
					try {
						CompoundNBT nbt = chunkloader.read(chunkPos);
						if (nbt == null) continue;
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
								markProtectedChunks(x, z, radius);
							}
						}
					}catch(IOException e) {
						LOGGER.error("Failed to read chunk {}", chunkPos, e);
						int fallbackRadius = 32;
						int blockX = (chunkPos.x << 4) + 8;
						int blockZ = (chunkPos.z << 4) + 8;
						markProtectedChunks(blockX, blockZ, fallbackRadius);
					}
				}
			}
			while(this.running) {
				boolean shouldKeepRunning = false;
				float totalProgress = 0.0F;

				for(RegistryKey<World> worldKey : this.levels) {
					ListIterator<ChunkPos> chunkiterator = worldChunkList.get(worldKey);
					ChunkLoader chunkloader = worldChunkLoaders.get(worldKey);
					if (chunkiterator.hasNext()) {
						ChunkPos chunkpos = chunkiterator.next();
						boolean wasConverted = false;

						try {
							CompoundNBT chunkNbt = chunkloader.read(chunkpos);
							if (chunkNbt != null) {
								int chunkVersion = ChunkLoader.getVersion(chunkNbt);
								CompoundNBT chunkUpdateData = chunkloader.upgradeChunkTag(worldKey, () -> {return this.overworldDataStorage;}, chunkNbt);
								CompoundNBT chunkLevelData = chunkUpdateData.getCompound("Level");
								ChunkPos chunkpos1 = new ChunkPos(chunkLevelData.getInt("xPos"), chunkLevelData.getInt("zPos"));
								if (!chunkpos1.equals(chunkpos)) {
									LOGGER.warn("Chunk {} has invalid position {}", chunkpos, chunkpos1);
								}

								boolean shouldUpdateVersion = chunkVersion < SharedConstants.getCurrentVersion().getWorldVersion();
								if (this.eraseCache) {
									shouldUpdateVersion = shouldUpdateVersion || chunkLevelData.contains("Heightmaps");
									chunkLevelData.remove("Heightmaps");
									shouldUpdateVersion = shouldUpdateVersion || chunkLevelData.contains("isLightOn");
									chunkLevelData.remove("isLightOn");
								}

								if(!protectedChunks.contains(chunkpos)) {
									chunkLevelData = null;
								}
								if (shouldUpdateVersion) {
									chunkloader.write(chunkpos, chunkUpdateData);
									wasConverted = true;
								}
							}
						} catch (ReportedException reportedexception) {
							Throwable throwable = reportedexception.getCause();
							if (!(throwable instanceof IOException)) {
								throw reportedexception;
							}

							LOGGER.error("Error upgrading chunk {}", chunkpos, throwable);
						} catch (IOException ioexception1) {
							LOGGER.error("Error upgrading chunk {}", chunkpos, ioexception1);
						}

						if (wasConverted) {
							++this.converted;
						} else {
							++this.skipped;
						}

						shouldKeepRunning = true;
					}

					float chunkProgress = (float)chunkiterator.nextIndex() / totalChunkCount;
					this.progressMap.put(worldKey, chunkProgress);
					totalProgress += chunkProgress;
				}

				this.progress = totalProgress;
				if (!shouldKeepRunning) {
					this.running = false;
				}
			}

			this.status = new TranslationTextComponent("optimizeWorld.stage.finished");

			for(ChunkLoader chunkloader1 : worldChunkLoaders.values()) {
				try {
					chunkloader1.close();
				} catch (IOException ioexception) {
					LOGGER.error("Error upgrading chunk", (Throwable)ioexception);
				}
			}

			this.overworldDataStorage.save();
			currentMilisecond = Util.getMillis() - currentMilisecond;
			LOGGER.info("World optimizaton finished after {} ms", (long)currentMilisecond);
			this.finished = true;
		}
	}

	private List<ChunkPos> getAllChunkPos(RegistryKey<World> p_233532_1_) {
		File file1 = this.levelStorage.getDimensionPath(p_233532_1_);
		File file2 = new File(file1, "region");
		File[] afile = file2.listFiles((p_219954_0_, p_219954_1_) -> {
			return p_219954_1_.endsWith(".mca");
		});
		if (afile == null) {
			return ImmutableList.of();
		} else {
			List<ChunkPos> list = Lists.newArrayList();

			for(File file3 : afile) {
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
								}
							}
						}
					} catch (Throwable throwable) {
					}
				}
			}

			return list;
		}
	}

	public boolean isFinished() {
		return this.finished;
	}

	@OnlyIn(Dist.CLIENT)
	public float dimensionProgress(RegistryKey<World> p_233531_1_) {
		return this.progressMap.getFloat(p_233531_1_);
	}

	@OnlyIn(Dist.CLIENT)
	public float getProgress() {
		return this.progress;
	}

	public int getTotalChunks() {
		return this.totalChunks;
	}

	public int getConverted() {
		return this.converted;
	}

	public int getSkipped() {
		return this.skipped;
	}

	public ITextComponent getStatus() {
		return this.status;
	}
}