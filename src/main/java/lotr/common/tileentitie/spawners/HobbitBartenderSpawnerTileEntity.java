package lotr.common.tileentitie.spawners;

import com.github.maximuslotro.lotroExtended.enums.ExtendedEntitySpawnTypes;

import lotr.common.entity.npc.ExtendedHobbitBartenderEntity;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedTileEntities;

public class HobbitBartenderSpawnerTileEntity extends ExtendedStructureSpawnerTileEntity<HobbitBartenderSpawnerTileEntity, ExtendedHobbitBartenderEntity>{

	public HobbitBartenderSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_TILEENTITYS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get(), ExtendedEntities.HOBBIT_BARTENDER.get());
	}
}
