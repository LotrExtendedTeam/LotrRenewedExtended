package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeMagnifier;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.MobDensityTracker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Heavily copied from {@link WorldEntitySpawner}
 * */
public final class ExtendedWorldEntitySpawner {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final int MAGIC_NUMBER = (int)Math.pow(17.0D, 2.0D);

	public static ExtendedWorldEntitySpawner.EntityDensityManager createState(int pSpawnableChunkCount, Iterable<Entity> pEntities, ExtendedWorldEntitySpawner.IInitialDensityAdder pChunkGetter) {
		MobDensityTracker mobdensitytracker = new MobDensityTracker();
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
				pChunkGetter.query(i, (p_234971_5_) -> {
					MobSpawnInfo.SpawnCosts mobspawninfo$spawncosts = getRoughBiome(blockpos, p_234971_5_).getMobSettings().getMobSpawnCost(entity_f.getType());
					if (mobspawninfo$spawncosts != null) {
						mobdensitytracker.addCharge(entity_f.blockPosition(), mobspawninfo$spawncosts.getCharge());
					}

					object2intopenhashmap.addTo(entityclassification, 1);
				});
			}
		}
	}

	private static Biome getRoughBiome(BlockPos pPos, IChunk pChunk) {
		return DefaultBiomeMagnifier.INSTANCE.getBiome(0L, pPos.getX(), pPos.getY(), pPos.getZ(), pChunk.getBiomes());
	}

	public static void spawnForChunk(ServerWorld pLevel, Chunk pChunk, ExtendedWorldEntitySpawner.EntityDensityManager pSpawnState, boolean pSpawnFriendlies, boolean pSpawnMonsters, boolean p_234979_5_) {
		pLevel.getProfiler().push("spawner");

		for(EntityClassification entityclassification : SPAWNING_CATEGORIES) {
			if ((pSpawnFriendlies || !entityclassification.isFriendly()) && (pSpawnMonsters || entityclassification.isFriendly()) && (p_234979_5_ || !entityclassification.isPersistent()) && pSpawnState.canSpawnForCategory(entityclassification)) {
				spawnCategoryForChunk(entityclassification, pLevel, pChunk, (p_234969_1_, p_234969_2_, p_234969_3_) -> {
					return pSpawnState.canSpawn(p_234969_1_, p_234969_2_, p_234969_3_);
				}, (p_234970_1_, p_234970_2_) -> {
					pSpawnState.afterSpawn(p_234970_1_, p_234970_2_);
				});
			}
		}

		pLevel.getProfiler().pop();
	}

	public static void spawnCategoryForChunk(EntityClassification pCategory, ServerWorld pLevel, Chunk pChunk, ExtendedWorldEntitySpawner.IDensityCheck pFilter, ExtendedWorldEntitySpawner.IOnSpawnDensityAdder pCallback) {
		BlockPos blockpos = getRandomPosWithin(pLevel, pChunk);
		if (blockpos.getY() >= 1) {
			spawnCategoryForPosition(pCategory, pLevel, pChunk, blockpos, pFilter, pCallback);
		}
	}

	public static void spawnCategoryForPosition(EntityClassification pCategory, ServerWorld pLevel, IChunk pChunk, BlockPos pPos, ExtendedWorldEntitySpawner.IDensityCheck pFilter, ExtendedWorldEntitySpawner.IOnSpawnDensityAdder pCallback) {
		StructureManager structuremanager = pLevel.structureFeatureManager();
		ChunkGenerator chunkgenerator = pLevel.getChunkSource().getGenerator();
		int i = pPos.getY();
		BlockState blockstate = pChunk.getBlockState(pPos);
		if (!blockstate.isRedstoneConductor(pChunk, pPos)) {
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
			int j = 0;

			for(int k = 0; k < 3; ++k) {
				int l = pPos.getX();
				int i1 = pPos.getZ();
				int spawnRandomSpread = 6;
				MobSpawnInfo.Spawners mobspawninfo$spawners = null;
				ILivingEntityData ilivingentitydata = null;
				int k1 = MathHelper.ceil(pLevel.random.nextFloat() * 4.0F);
				int l1 = 0;

				for(int i2 = 0; i2 < k1; ++i2) {
					l += pLevel.random.nextInt(spawnRandomSpread) - pLevel.random.nextInt(spawnRandomSpread);
					i1 += pLevel.random.nextInt(spawnRandomSpread) - pLevel.random.nextInt(spawnRandomSpread);
					blockpos$mutable.set(l, i, i1);
					double d0 = (double)l + 0.5D;
					double d1 = (double)i1 + 0.5D;
					PlayerEntity playerentity = pLevel.getNearestPlayer(d0, (double)i, d1, -1.0D, false);
					if (playerentity != null) {
						double d2 = playerentity.distanceToSqr(d0, (double)i, d1);
						if (isRightDistanceToPlayerAndSpawnPoint(pLevel, pChunk, blockpos$mutable, d2)) {
							if (mobspawninfo$spawners == null) {
								mobspawninfo$spawners = getRandomSpawnMobAt(pLevel, structuremanager, chunkgenerator, pCategory, pLevel.random, blockpos$mutable);
								if (mobspawninfo$spawners == null) {
									break;
								}

								k1 = mobspawninfo$spawners.minCount + pLevel.random.nextInt(1 + mobspawninfo$spawners.maxCount - mobspawninfo$spawners.minCount);
							}

							if (isValidSpawnPostitionForType(pLevel, pCategory, structuremanager, chunkgenerator, mobspawninfo$spawners, blockpos$mutable, d2) && pFilter.test(mobspawninfo$spawners.type, blockpos$mutable, pChunk)) {
								MobEntity mobentity = getMobForSpawn(pLevel, mobspawninfo$spawners.type);
								if (mobentity == null) {
									return;
								}

								mobentity.moveTo(d0, (double)i, d1, pLevel.random.nextFloat() * 360.0F, 0.0F);
								int canSpawn = net.minecraftforge.common.ForgeHooks.canEntitySpawn(mobentity, pLevel, d0, i, d1, null, SpawnReason.NATURAL);
								if (canSpawn != -1 && (canSpawn == 1 || isValidPositionForMob(pLevel, mobentity, d2))) {
									if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mobentity, pLevel, (float)d0, (float)i, (float)d1, null, SpawnReason.NATURAL))
										ilivingentitydata = mobentity.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(mobentity.blockPosition()), SpawnReason.NATURAL, ilivingentitydata, (CompoundNBT)null);
									++j;
									++l1;
									pLevel.addFreshEntityWithPassengers(mobentity);
									pCallback.run(mobentity, pChunk);
									if (j >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(mobentity)) {
										return;
									}

									if (mobentity.isMaxGroupSizeReached(l1)) {
										break;
									}
								}
							}
						}
					}
				}
			}

		}
	}

	private static boolean isRightDistanceToPlayerAndSpawnPoint(ServerWorld pLevel, IChunk pChunk, BlockPos.Mutable pPos, double pDistance) {
		if (pDistance <= 576.0D) {
			return false;
		} else if (pLevel.getSharedSpawnPos().closerThan(new Vector3d((double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D), 24.0D)) {
			return false;
		} else {
			ChunkPos chunkpos = new ChunkPos(pPos);
			return Objects.equals(chunkpos, pChunk.getPos()) || pLevel.getChunkSource().isEntityTickingChunk(chunkpos);
		}
	}

	private static boolean isValidSpawnPostitionForType(ServerWorld pLevel, EntityClassification pCategory, StructureManager pStructureManager, ChunkGenerator pGenerator, MobSpawnInfo.Spawners pData, BlockPos.Mutable pPos, double pDistance) {
		EntityType<?> entitytype = pData.type;
		if (entitytype.getCategory() == EntityClassification.MISC) {
			return false;
		} else if (!entitytype.canSpawnFarFromPlayer() && pDistance > (double)(entitytype.getCategory().getDespawnDistance() * entitytype.getCategory().getDespawnDistance())) {
			return false;
		} else if (entitytype.canSummon() && canSpawnMobAt(pLevel, pStructureManager, pGenerator, pCategory, pData, pPos)) {
			EntitySpawnPlacementRegistry.PlacementType entityspawnplacementregistry$placementtype = EntitySpawnPlacementRegistry.getPlacementType(entitytype);
			if (!isSpawnPositionOk(entityspawnplacementregistry$placementtype, pLevel, pPos, entitytype)) {
				return false;
			} else if (!EntitySpawnPlacementRegistry.checkSpawnRules(entitytype, pLevel, SpawnReason.NATURAL, pPos, pLevel.random)) {
				return false;
			} else {
				return pLevel.noCollision(entitytype.getAABB((double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D));
			}
		} else {
			return false;
		}
	}

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

	private static boolean isValidPositionForMob(ServerWorld pLevel, MobEntity pMob, double pDistance) {
		if (pDistance > (double)(pMob.getType().getCategory().getDespawnDistance() * pMob.getType().getCategory().getDespawnDistance()) && pMob.removeWhenFarAway(pDistance)) {
			return false;
		} else {
			return pMob.checkSpawnRules(pLevel, SpawnReason.NATURAL) && pMob.checkSpawnObstruction(pLevel);
		}
	}

	@Nullable
	private static MobSpawnInfo.Spawners getRandomSpawnMobAt(ServerWorld p_234977_0_, StructureManager p_234977_1_, ChunkGenerator p_234977_2_, EntityClassification p_234977_3_, Random p_234977_4_, BlockPos p_234977_5_) {
		Biome biome = p_234977_0_.getBiome(p_234977_5_);
		if (p_234977_3_ == EntityClassification.WATER_AMBIENT && biome.getBiomeCategory() == Biome.Category.RIVER && p_234977_4_.nextFloat() < 0.98F) {
			return null;
		} else {
			List<MobSpawnInfo.Spawners> list = mobsAt(p_234977_0_, p_234977_1_, p_234977_2_, p_234977_3_, p_234977_5_, biome);
			list = net.minecraftforge.event.ForgeEventFactory.getPotentialSpawns(p_234977_0_, p_234977_3_, p_234977_5_, list);
			return list.isEmpty() ? null : WeightedRandom.getRandomItem(p_234977_4_, list);
		}
	}

	private static boolean canSpawnMobAt(ServerWorld pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, EntityClassification pCategory, MobSpawnInfo.Spawners pData, BlockPos pPos) {
		return mobsAt(pLevel, pStructureManager, pGenerator, pCategory, pPos, (Biome)null).contains(pData);
	}

	private static List<MobSpawnInfo.Spawners> mobsAt(ServerWorld p_241463_0_, StructureManager p_241463_1_, ChunkGenerator p_241463_2_, EntityClassification p_241463_3_, BlockPos p_241463_4_, @Nullable Biome p_241463_5_) {
		return p_241463_3_ == EntityClassification.MONSTER && p_241463_0_.getBlockState(p_241463_4_.below()).getBlock() == Blocks.NETHER_BRICKS && p_241463_1_.getStructureAt(p_241463_4_, false, Structure.NETHER_BRIDGE).isValid() ? Structure.NETHER_BRIDGE.getSpecialEnemies() : p_241463_2_.getMobsAt(p_241463_5_ != null ? p_241463_5_ : p_241463_0_.getBiome(p_241463_4_), p_241463_1_, p_241463_3_, p_241463_4_);
	}

	private static BlockPos getRandomPosWithin(World pLevel, Chunk pChunk) {
		ChunkPos chunkpos = pChunk.getPos();
		int i = chunkpos.getMinBlockX() + pLevel.random.nextInt(16);
		int j = chunkpos.getMinBlockZ() + pLevel.random.nextInt(16);
		int k = pChunk.getHeight(Heightmap.Type.WORLD_SURFACE, i, j) + 1;
		int l = pLevel.random.nextInt(k + 1);
		return new BlockPos(i, l, j);
	}

	public static boolean isSpawnPositionOk(EntitySpawnPlacementRegistry.PlacementType pPlaceType, IWorldReader pLevel, BlockPos pPos, @Nullable EntityType<?> pEntityType) {
		if (pPlaceType == EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS) {
			return true;
		} else if (pEntityType != null && pLevel.getWorldBorder().isWithinBounds(pPos)) {
			return pPlaceType.canSpawnAt(pLevel, pPos, pEntityType);
		}
		return false;
	}

	public static class EntityDensityManager {
		private final int spawnableChunkCount;
		private final Object2IntOpenHashMap<EntityClassification> mobCategoryCounts;
		private final MobDensityTracker spawnPotential;
		private final Object2IntMap<EntityClassification> unmodifiableMobCategoryCounts;
		@Nullable
		private BlockPos lastCheckedPos;
		@Nullable
		private EntityType<?> lastCheckedType;
		private double lastCharge;

		private EntityDensityManager(int pSpawnableChunkCount, Object2IntOpenHashMap<EntityClassification> pMobCategoryCounts, MobDensityTracker pSpawnPotential) {
			this.spawnableChunkCount = pSpawnableChunkCount;
			this.mobCategoryCounts = pMobCategoryCounts;
			this.spawnPotential = pSpawnPotential;
			this.unmodifiableMobCategoryCounts = Object2IntMaps.unmodifiable(pMobCategoryCounts);
		}

		private boolean canSpawn(EntityType<?> pEntityType, BlockPos pPos, IChunk pChunk) {
			this.lastCheckedPos = pPos;
			this.lastCheckedType = pEntityType;
			MobSpawnInfo.SpawnCosts mobspawninfo$spawncosts = ExtendedWorldEntitySpawner.getRoughBiome(pPos, pChunk).getMobSettings().getMobSpawnCost(pEntityType);
			if (mobspawninfo$spawncosts == null) {
				this.lastCharge = 0.0D;
				return true;
			} else {
				double d0 = mobspawninfo$spawncosts.getCharge();
				this.lastCharge = d0;
				double d1 = this.spawnPotential.getPotentialEnergyChange(pPos, d0);
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
				MobSpawnInfo.SpawnCosts mobspawninfo$spawncosts = ExtendedWorldEntitySpawner.getRoughBiome(blockpos, pChunk).getMobSettings().getMobSpawnCost(entitytype);
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
