package com.github.maximuslotro.lotrrextended.common.spawners.types;

import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntityTypeInterface;

import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedRandom;

public class MultiSpawnType implements ExtendedStructureEntityTypeInterface {
	private final List<MultiSpawnEntry> entities;

	public MultiSpawnType(List<MultiSpawnEntry> entities) {
		this.entities = entities;
	}

	@Override
	public EntityType<?> getSpawn(Random random) {
		return WeightedRandom.getRandomItem(random, entities).getEntity();
	}

	public static class MultiSpawnEntry extends WeightedRandom.Item {
		private final EntityType<?> entity;

		public MultiSpawnEntry(int weight, EntityType<?> entity) {
			super(weight);
			this.entity = entity;
		}

		public EntityType<?> getEntity() {
			return entity;
		}
	}

	public List<MultiSpawnEntry> getEntities() {
		return entities;
	}
}
