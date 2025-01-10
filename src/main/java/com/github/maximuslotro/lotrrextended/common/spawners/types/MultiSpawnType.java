package com.github.maximuslotro.lotrrextended.common.spawners.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

	@Override
	public List<EntityType<?>> getAllPossableEntities() {
		return entities.stream().map(MultiSpawnEntry::getEntity).collect(Collectors.toList());
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

	public static class Builder {
		private List<MultiSpawnEntry> entries = new ArrayList<>();

		public Builder(List<MultiSpawnEntry> entries) {
			this.entries = entries;
		}

		private Builder() {}

		public static MultiSpawnType.Builder multi() {
			return new MultiSpawnType.Builder();
		}

		public MultiSpawnType.Builder addPool(EntityType<?> type, int weight) {
			entries.add(new MultiSpawnEntry(weight, type));
			return this;
		}

		public MultiSpawnType build() {
			return new MultiSpawnType(entries);
		}
	}
}
