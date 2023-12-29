package lotr.common.tileentitie.spawners;

import lotr.common.entity.npc.HobbitBartenderEntity;
import lotr.common.init.ExtendedNpcs;
import lotr.common.init.ExtendedTileEntities;

public class HobbitBartenderSpawnerTileEntity extends StructureSpawnerTileEntity<HobbitBartenderEntity, HobbitBartenderSpawnerTileEntity>{

	public HobbitBartenderSpawnerTileEntity() {
		super(ExtendedTileEntities.HOBBIT_BARTENDER_SPAWNER_TILEENTITY.get(), ExtendedNpcs.HOBBIT_BARTENDER.get());
	}
}
