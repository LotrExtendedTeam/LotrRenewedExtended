package lotr.common.tileentitie.spawners;

import lotr.common.entity.npc.HobbitBartenderEntity;
import lotr.common.init.ExtendedNpcs;
import lotr.common.init.ExtendedTileEntities;

public class HobbitBartenderSpawnerTileEntity extends StructureSpawnerTileEntity<HobbitBartenderSpawnerTileEntity, HobbitBartenderEntity>{

	public HobbitBartenderSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_TILEENTITYS.get(EntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get(), ExtendedNpcs.HOBBIT_BARTENDER.get());
	}
}
