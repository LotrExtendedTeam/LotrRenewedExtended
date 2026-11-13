package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeMagnifier;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * Heavily copied from {@link WorldEntitySpawner}
 * */
public final class ExtendedWorldEntitySpawner {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final int MAGIC_NUMBER = (int)Math.pow(17.0D, 2.0D);

	// Density tracking
	public static ExtendedWorldEntitySpawner.EntityDensityManager createState(int pSpawnableChunkCount, Iterable<Entity> pEntities, ExtendedWorldEntitySpawner.IInitialDensityAdder pChunkGetter) {
		ExtendedMobDensityTracker mobdensitytracker = new ExtendedMobDensityTracker();
		Object2IntOpenHashMap<EntityClassification> object2intopenhashmap = new Object2IntOpenHashMap<>();
		Iterator<Entity> iterator = pEntities.iterator();

		while(true) {
			Entity entity;
			MobEntity mobentity;
			do {
				if (!iterator.hasNext()) {
					return new ExtendedWorldEntitySpawner.EntityDensityManager(pSpawnableChunkCount, object2intopenhashmap, mobdensitytracker);
				}

				entity = (Entity)iterator.next();
				if (!(entity instanceof MobEntity)) {
					break;
				}

				mobentity = (MobEntity)entity;
			} while(mobentity.isPersistenceRequired() || mobentity.requiresCustomPersistence());

			final Entity entity_f = entity;
			EntityClassification entityclassification = entity.getClassification(true);
			if (entityclassification != EntityClassification.MISC) {
				BlockPos blockpos = entity.blockPosition();
				long i = ChunkPos.asLong(blockpos.getX() >> 4, blockpos.getZ() >> 4);
				pChunkGetter.query(i, (pChunk) -> {
					ExtendedSpawnInfo.SpawnCosts mobspawninfo$spawncosts = getSpawnsForBiome(blockpos, pChunk).getMobSpawnCost(entity_f.getType());
					if (mobspawninfo$spawncosts != null) {
						mobdensitytracker.addCharge(entity_f.blockPosition(), mobspawninfo$spawncosts.getCharge());
					}

					object2intopenhashmap.addTo(entityclassification, 1);
				});
			}
		}
	}

	// TODO limit spawning to every 20 seconds
	public static void spawnForChunk(ServerWorld pLevel, Chunk pChunk, ExtendedWorldEntitySpawner.EntityDensityManager pSpawnState, boolean p_234979_5_) {
		pLevel.getProfiler().push("spawner");
		spawnCategoryForChunk(pLevel, pChunk, pSpawnState::canSpawn, pSpawnState::afterSpawn);
		pLevel.getProfiler().pop();
	}

	// Spawning for a chunk
	public static void spawnCategoryForChunk(ServerWorld pLevel, Chunk pChunk, ExtendedWorldEntitySpawner.IDensityCheck pFilter, ExtendedWorldEntitySpawner.IOnSpawnDensityAdder pCallback) {
		BlockPos blockpos = getRandomPosWithin(pLevel, pChunk);
		if (blockpos.getY() >= 1) {
			spawnCategoryForPosition(pLevel, pChunk, blockpos, pFilter, pCallback);
		}
	}

	private static BlockPos getRandomPosWithin(World pLevel, Chunk pChunk) {
		ChunkPos chunkpos = pChunk.getPos();
		int i = chunkpos.getMinBlockX() + pLevel.random.nextInt(16);
		int j = chunkpos.getMinBlockZ() + pLevel.random.nextInt(16);
		int k = pChunk.getHeight(Heightmap.Type.WORLD_SURFACE, i, j) + 1;
		int l = pLevel.random.nextInt(k + 1);
		return new BlockPos(i, l, j);
	}

	public static void spawnCategoryForPosition(ServerWorld pLevel, IChunk pChunk, BlockPos pPos, ExtendedWorldEntitySpawner.IDensityCheck pFilter, ExtendedWorldEntitySpawner.IOnSpawnDensityAdder pCallback) {
		BlockState blockstate = pChunk.getBlockState(pPos);
		if (!blockstate.isRedstoneConductor(pChunk, pPos)) {
			return;
		}
		StructureManager structuremanager = pLevel.structureFeatureManager();
		ChunkGenerator chunkgenerator = pLevel.getChunkSource().getGenerator();
		int spawnY = pPos.getY();
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		int spawned = 0;
		for(int spawnRuns = 0; spawnRuns < 3; ++spawnRuns) {
			int spawnX = pPos.getX();
			int spawnZ = pPos.getZ();
			int spawnRandomSpread = 6;
			ExtendedSpawnInfo.GroupSpawner groupSpawner = null;
			ILivingEntityData ilivingentitydata = null;
			int spawnCount = MathHelper.ceil(pLevel.random.nextFloat() * 4.0F);
			int spawnedGroupSize = 0;

			for(int spawnedInGroup = 0; spawnedInGroup < spawnCount; ++spawnedInGroup) {
				spawnX += pLevel.random.nextInt(spawnRandomSpread) - pLevel.random.nextInt(spawnRandomSpread);
				spawnZ += pLevel.random.nextInt(spawnRandomSpread) - pLevel.random.nextInt(spawnRandomSpread);
				mutablePos.set(spawnX, spawnY, spawnZ);
				double spawnXOffset = spawnX + 0.5D;
				double spawnZOffset = spawnZ + 0.5D;
				PlayerEntity playerentity = pLevel.getNearestPlayer(spawnXOffset, spawnY, spawnZOffset, -1.0D, false);
				if (playerentity != null) {
					double playerDistance = playerentity.distanceToSqr(spawnXOffset, spawnY, spawnZOffset);
					if (isRightDistanceToPlayerAndSpawnPoint(pLevel, pChunk, mutablePos, playerDistance)) {
						if (groupSpawner == null) {
							groupSpawner = getRandomSpawnMobAt(pLevel, structuremanager, chunkgenerator, pLevel.random, mutablePos);
							if (groupSpawner == null) {
								break;
							}
							spawnCount = groupSpawner.minCount + pLevel.random.nextInt(1 + groupSpawner.maxCount - groupSpawner.minCount);
						}
						EntityType<?> potentialEntityType = groupSpawner.getSpawner(pLevel.random).getType();
						if (isValidSpawnPostitionForType(pLevel, structuremanager, chunkgenerator, potentialEntityType, mutablePos, playerDistance) && pFilter.test(potentialEntityType, mutablePos, pChunk)) {
							MobEntity mobentity = getMobForSpawn(pLevel, potentialEntityType);
							if (mobentity == null) {
								return;
							}

							mobentity.moveTo(spawnXOffset, spawnY, spawnZOffset, pLevel.random.nextFloat() * 360.0F, 0.0F);
							int canSpawn = ForgeHooks.canEntitySpawn(mobentity, pLevel, spawnXOffset, spawnY, spawnZOffset, null, SpawnReason.NATURAL);
							if (canSpawn != -1 && (canSpawn == 1 || isValidPositionForMob(pLevel, mobentity, playerDistance))) {
								if (!ForgeEventFactory.doSpecialSpawn(mobentity, pLevel, (float)spawnXOffset, (float)spawnY, (float)spawnZOffset, null, SpawnReason.NATURAL)) {
									ilivingentitydata = mobentity.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(mobentity.blockPosition()), SpawnReason.NATURAL, ilivingentitydata, null);
								}
								++spawned;
								++spawnedGroupSize;
								pLevel.addFreshEntityWithPassengers(mobentity);
								pCallback.run(mobentity, pChunk);
								if (spawned >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(mobentity)) {
									return;
								}
								if (mobentity.isMaxGroupSizeReached(spawnedGroupSize)) {
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Checks for spawn distance from player and world spawn
	 * @param pLevel The world being spawned in
	 * @param pChunk The chunk being spawned at
	 * @param pPos The block position being spawned at
	 * @param pDistance The distance away from closest player
	 * @return True if within proper distances from player and spawn
	 * */
	private static boolean isRightDistanceToPlayerAndSpawnPoint(ServerWorld pLevel, IChunk pChunk, BlockPos.Mutable pPos, double pDistance) {
		if (pDistance <= 576.0D) {
			return false;
		} else if (pLevel.getSharedSpawnPos().closerThan(new Vector3d(pPos.getX() + 0.5D, pPos.getY(), pPos.getZ() + 0.5D), 24.0D)) {
			return false;
		} else {
			ChunkPos chunkpos = new ChunkPos(pPos);
			return Objects.equals(chunkpos, pChunk.getPos()) || pLevel.getChunkSource().isEntityTickingChunk(chunkpos);
		}
	}
	/**
	 * Checks if the given position is valid for entities to spawn at
	 * @param pLevel The world being spawned in
	 * @param pStructureManager Structure manager for checking structures at position
	 * @param pGenerator The world generator for the world
	 * @param pEntity The entity type being considered for spawning
	 * @param pPos The block position being spawned at
	 * @param pDistance The distance away from closest player
	 * @return True if within proper distances from player and spawn
	 * */
	private static boolean isValidSpawnPostitionForType(ServerWorld pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, EntityType<?> pEntity, BlockPos.Mutable pPos, double pDistance) {
		if (pEntity.getCategory() == EntityClassification.MISC) {
			return false;
		} else if (!pEntity.canSpawnFarFromPlayer() && pDistance > pEntity.getCategory().getDespawnDistance() * pEntity.getCategory().getDespawnDistance()) {
			return false;
		} else if (pEntity.canSummon()) {//&& canSpawnMobAt(pLevel, pStructureManager, pGenerator, pEntity, pPos)) {
			EntitySpawnPlacementRegistry.PlacementType spawnPlacementType = EntitySpawnPlacementRegistry.getPlacementType(pEntity);
			if (!isSpawnPositionOk(spawnPlacementType, pLevel, pPos, pEntity)) {
				return false;
			} else if (!EntitySpawnPlacementRegistry.checkSpawnRules(pEntity, pLevel, SpawnReason.NATURAL, pPos, pLevel.random)) {
				return false;
			} else {
				return pLevel.noCollision(pEntity.getAABB(pPos.getX() + 0.5D, pPos.getY(), pPos.getZ() + 0.5D));
			}
		} else {
			return false;
		}
	}
	/**
	 * Checks for world border and entity placement type restrictions
	 * @param pPlaceType The placement type for the entity
	 * @param pLevel The world being spawned in
	 * @param pPos The block position being spawned at
	 * @param pEntityType The entity type being considered for spawning
	 * @return True if within proper distances from player and spawn
	 * */
	public static boolean isSpawnPositionOk(EntitySpawnPlacementRegistry.PlacementType pPlaceType, IWorldReader pLevel, BlockPos pPos, @Nullable EntityType<?> pEntityType) {
		if (pPlaceType == EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS) {
			return true;
		} else if (pEntityType != null && pLevel.getWorldBorder().isWithinBounds(pPos)) {
			return pPlaceType.canSpawnAt(pLevel, pPos, pEntityType);
		}
		return false;
	}

	private static boolean isValidPositionForMob(ServerWorld pLevel, MobEntity pMob, double pDistance) {
		if (pDistance > pMob.getType().getCategory().getDespawnDistance() * pMob.getType().getCategory().getDespawnDistance() && pMob.removeWhenFarAway(pDistance)) {
			return false;
		} else {
			return pMob.checkSpawnRules(pLevel, SpawnReason.NATURAL) && pMob.checkSpawnObstruction(pLevel);
		}
	}

	// Mob spawning and selection
	@Nullable
	private static MobEntity getMobForSpawn(ServerWorld pLevel, EntityType<?> pEntityType) {
		try {
			Entity entity = pEntityType.create(pLevel);
			if (!(entity instanceof MobEntity)) {
				throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getKey(pEntityType));
			} else {
				return (MobEntity)entity;
			}
		} catch (Exception exception) {
			LOGGER.warn("Failed to create mob", (Throwable)exception);
			return null;
		}
	}

	@Nullable
	private static ExtendedSpawnInfo.GroupSpawner getRandomSpawnMobAt(ServerWorld pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, Random pRandom, BlockPos pPos) {
		return getSpawnsForBiome(pLevel.getBiome(pPos)).getSpawner(pRandom);
	}

	public static ExtendedSpawnInfo getSpawnsForBiome(BlockPos pPos, IChunk pChunk){
		return getSpawnsForBiome(DefaultBiomeMagnifier.INSTANCE.getBiome(0L, pPos.getX(), pPos.getY(), pPos.getZ(), pChunk.getBiomes()));
	}

	public static ExtendedSpawnInfo getSpawnsForBiome(Biome pBiome){
		return null;
	}

	public static class EntityDensityManager {
		private final int spawnableChunkCount;
		private final Object2IntOpenHashMap<EntityClassification> mobCategoryCounts;
		private final ExtendedMobDensityTracker spawnPotential;
		private final Object2IntMap<EntityClassification> unmodifiableMobCategoryCounts;
		@Nullable
		private BlockPos lastCheckedPos;
		@Nullable
		private EntityType<?> lastCheckedType;
		private double lastCharge;

		private EntityDensityManager(int pSpawnableChunkCount, Object2IntOpenHashMap<EntityClassification> pMobCategoryCounts, ExtendedMobDensityTracker pSpawnPotential) {
			this.spawnableChunkCount = pSpawnableChunkCount;
			this.mobCategoryCounts = pMobCategoryCounts;
			this.spawnPotential = pSpawnPotential;
			this.unmodifiableMobCategoryCounts = Object2IntMaps.unmodifiable(pMobCategoryCounts);
		}

		private boolean canSpawn(EntityType<?> pEntityType, BlockPos pBlockpos, IChunk pChunk) {
			this.lastCheckedPos = pBlockpos;
			this.lastCheckedType = pEntityType;
			ExtendedSpawnInfo.SpawnCosts mobspawninfo$spawncosts = getSpawnsForBiome(pBlockpos, pChunk).getMobSpawnCost(pEntityType);
			if (mobspawninfo$spawncosts == null) {
				this.lastCharge = 0.0D;
				return true;
			} else {
				double d0 = mobspawninfo$spawncosts.getCharge();
				this.lastCharge = d0;
				double d1 = this.spawnPotential.getPotentialEnergyChange(pBlockpos, d0);
				return d1 <= mobspawninfo$spawncosts.getEnergyBudget();
			}
		}

		private void afterSpawn(MobEntity pMob, IChunk pChunk) {
			EntityType<?> entitytype = pMob.getType();
			BlockPos blockpos = pMob.blockPosition();
			double d0;
			if (blockpos.equals(this.lastCheckedPos) && entitytype == this.lastCheckedType) {
				d0 = this.lastCharge;
			} else {
				ExtendedSpawnInfo.SpawnCosts mobspawninfo$spawncosts = getSpawnsForBiome(blockpos, pChunk).getMobSpawnCost(entitytype);
				if (mobspawninfo$spawncosts != null) {
					d0 = mobspawninfo$spawncosts.getCharge();
				} else {
					d0 = 0.0D;
				}
			}

			this.spawnPotential.addCharge(blockpos, d0);
			this.mobCategoryCounts.addTo(entitytype.getCategory(), 1);
		}

		@OnlyIn(Dist.CLIENT)
		public int getSpawnableChunkCount() {
			return this.spawnableChunkCount;
		}

		public Object2IntMap<EntityClassification> getMobCategoryCounts() {
			return this.unmodifiableMobCategoryCounts;
		}

		private boolean canSpawnForCategory(EntityClassification pCategory) {
			int i = pCategory.getMaxInstancesPerChunk() * this.spawnableChunkCount / ExtendedWorldEntitySpawner.MAGIC_NUMBER;
			return this.mobCategoryCounts.getInt(pCategory) < i;
		}
	}

	@FunctionalInterface
	public interface IDensityCheck {
		boolean test(EntityType<?> p_test_1_, BlockPos p_test_2_, IChunk p_test_3_);
	}

	@FunctionalInterface
	public interface IInitialDensityAdder {
		void query(long p_query_1_, Consumer<Chunk> p_query_3_);
	}

	@FunctionalInterface
	public interface IOnSpawnDensityAdder {
		void run(MobEntity p_run_1_, IChunk p_run_2_);
	}
}
