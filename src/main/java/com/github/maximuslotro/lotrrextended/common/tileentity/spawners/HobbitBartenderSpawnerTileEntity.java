package com.github.maximuslotro.lotrrextended.common.tileentity.spawners;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnTypes;

import lotr.common.entity.npc.ExtendedHobbitBartenderEntity;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedTileEntities;

/**
 * Replaced by the new Entity Spawner system for Structures
 * @deprecated Replaced by the new Entity Spawner system for structures.
 * @since beta-1.6
 * */
@Deprecated
public class HobbitBartenderSpawnerTileEntity extends ExtendedStructureSpawnerTileEntity<HobbitBartenderSpawnerTileEntity, ExtendedHobbitBartenderEntity>{

	public HobbitBartenderSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_TILEENTITYS.get(ExtendedEntitySpawnTypes.HOBBIT_BARTENDER.getSerializedName()).get(), ExtendedEntities.HOBBIT_BARTENDER.get());
	}
}
