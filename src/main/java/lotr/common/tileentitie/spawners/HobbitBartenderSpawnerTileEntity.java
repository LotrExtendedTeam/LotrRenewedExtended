package lotr.common.tileentitie.spawners;

import lotr.common.entity.npc.ExtendedHobbitBartenderEntity;
import lotr.common.init.ExtendedNpcs;
import lotr.common.init.ExtendedTileEntities;

public class HobbitBartenderSpawnerTileEntity extends ExtendedStructureSpawnerTileEntity<HobbitBartenderSpawnerTileEntity, ExtendedHobbitBartenderEntity>{

	public HobbitBartenderSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_TILEENTITYS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get(), ExtendedNpcs.HOBBIT_BARTENDER.get());
	}
}
