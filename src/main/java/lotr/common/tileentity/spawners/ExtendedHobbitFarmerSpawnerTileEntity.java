package lotr.common.tileentity.spawners;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;

import lotr.common.entity.npc.ExtendedHobbitFarmerEntity;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedTileEntities;

public class ExtendedHobbitFarmerSpawnerTileEntity extends ExtendedStructureSpawnerTileEntity<ExtendedHobbitFarmerSpawnerTileEntity, ExtendedHobbitFarmerEntity>{

	public ExtendedHobbitFarmerSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_TILEENTITYS.get(ExtendedEntitySpawnTypes.HOBBIT_FARMER.getSerializedName()).get(), ExtendedEntities.HOBBIT_FARMER.get());
	}
}
