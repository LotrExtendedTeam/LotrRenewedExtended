package lotr.common.tileentitie.spawners;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class StructureSpawnerTileEntity<T extends MobEntity, V extends TileEntity> extends TileEntity implements ITickableTileEntity {

	protected final EntityType<T> entity;

	public StructureSpawnerTileEntity(TileEntityType<V> tileType, EntityType<T> entityType) {
		super(tileType);
		this.entity = entityType;
	}


	@Override
	public void tick() {
		// TODO ADD SPAWN CODE FOR ENTITY
	}
}
