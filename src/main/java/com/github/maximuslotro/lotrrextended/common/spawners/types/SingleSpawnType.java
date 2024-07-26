package com.github.maximuslotro.lotrrextended.common.spawners.types;

import java.util.Random;

import net.minecraft.entity.EntityType;

public class SingleSpawnType implements ExtendedStructureEntityTypeInterface {
	private final EntityType<?> entity;

	public SingleSpawnType(EntityType<?> entity) {
		this.entity = entity;
	}

	@Override
	public EntityType<?> getSpawn(Random random) {
		return entity;
	}

	public EntityType<?> getEntity() {
		return entity;
	}
}