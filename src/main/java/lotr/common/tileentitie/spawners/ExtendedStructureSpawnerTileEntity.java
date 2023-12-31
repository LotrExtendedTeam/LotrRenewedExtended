package lotr.common.tileentitie.spawners;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;

public abstract class ExtendedStructureSpawnerTileEntity<V extends TileEntity, T extends MobEntity> extends TileEntity implements ITickableTileEntity {

	private final EntityType<T> entity;
	private boolean spawned = false;

	public ExtendedStructureSpawnerTileEntity(TileEntityType<ExtendedStructureSpawnerTileEntity<? extends TileEntity, ? extends MobEntity>> tileEntityType, EntityType<T> entityType) {
		super(tileEntityType);
		this.entity = entityType;
	}

	@Override
	public void tick() {
		if (!level.isClientSide && !spawned && playerInDistance()) {
			if (spawnEntity((ServerWorld)level)) {
				level.destroyBlock(worldPosition, false);
				spawned = true;
			}
		}
	}

	private boolean spawnEntity(ServerWorld world) {
		//Moved code from ExtendedEntitySpawning
		T entity = this.entity.create(world);
		entity.moveTo(worldPosition, 0, 0.0F);
		entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
		return world.addFreshEntity(entity);
	}

	private Double playerDistance() {
		return 50D;
	}
	private boolean playerInDistance() {
		return level.hasNearbyAlivePlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), playerDistance());
	}
}
