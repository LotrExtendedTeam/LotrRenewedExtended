package com.github.maximuslotro.lotrrextended.common.tileentity.spawners.group;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;

/**
 * Replaced by the new Entity Spawner system for Structures
 *
 * @param <V> the type of {@link TileEntity} associated with the spawner
 *
 * @deprecated Replaced by the new Entity Spawner system for structures.
 * @since beta-1.6
 *
 * @see TileEntity
 * @see ITickableTileEntity
 * */
@Deprecated
public abstract class ExtendedStructureGroupSpawnerTileEntity<V extends TileEntity> extends TileEntity implements ITickableTileEntity {

	private boolean spawned = false;

	public ExtendedStructureGroupSpawnerTileEntity(TileEntityType<? extends ExtendedStructureGroupSpawnerTileEntity<? extends TileEntity>> tileEntityType) {
		super(tileEntityType);
	}

	@Override
	public void tick() {
		if (!level.isClientSide && !spawned && playerInDistance()&&!ExtendedServerConfig.enableDevMode.get()) {
			if (spawnEntity((ServerWorld)level)) {
				level.destroyBlock(worldPosition, false);
				spawned = true;
			}
		}
	}

	public abstract boolean spawnGroup(ServerWorld world);

	private boolean spawnEntity(ServerWorld world) {
//		T entity = this.entity.create(world);
//		entity.moveTo(worldPosition, 0, 0.0F);
//		entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
//		return world.addFreshEntity(entity);
		return spawnGroup(world);
	}

	public Double playerDistance() {
		return 50D;
	}
	private boolean playerInDistance() {
		return level.hasNearbyAlivePlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), playerDistance());
	}
}
