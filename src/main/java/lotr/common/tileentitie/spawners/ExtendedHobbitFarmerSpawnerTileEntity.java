package lotr.common.tileentitie.spawners;

import lotr.common.entity.npc.ExtendedHobbitFarmerEntity;
import lotr.common.init.ExtendedNpcs;
import lotr.common.init.ExtendedTileEntities;

public class ExtendedHobbitFarmerSpawnerTileEntity extends ExtendedStructureSpawnerTileEntity<ExtendedHobbitFarmerSpawnerTileEntity, ExtendedHobbitFarmerEntity>{

	public ExtendedHobbitFarmerSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_TILEENTITYS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()).get(), ExtendedNpcs.HOBBIT_FARMER.get());
	}
}
