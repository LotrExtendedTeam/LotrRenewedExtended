package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.MobSpawnInfo;

/**
 * Based upon {@link MobSpawnInfo}
 * */
public class ExtendedSpawnInfo {
	private final float creatureGenerationProbability;
	private final Map<ExtendedFactionPointer, List<ExtendedSpawnInfo.Spawners>> spawners;
	private final Map<EntityType<?>, ExtendedSpawnInfo.SpawnCosts> mobSpawnCosts;
	private final boolean playerSpawnFriendly;
	private final Set<ExtendedFactionPointer> typesView;
	private final Set<EntityType<?>> costView;

	private ExtendedSpawnInfo(float p_i241946_1_, Map<ExtendedFactionPointer, List<ExtendedSpawnInfo.Spawners>> p_i241946_2_, Map<EntityType<?>, ExtendedSpawnInfo.SpawnCosts> p_i241946_3_, boolean p_i241946_4_) {
		this.creatureGenerationProbability = p_i241946_1_;
		this.spawners = p_i241946_2_;
		this.mobSpawnCosts = p_i241946_3_;
		this.playerSpawnFriendly = p_i241946_4_;
		this.typesView = Collections.unmodifiableSet(this.spawners.keySet());
		this.costView = Collections.unmodifiableSet(this.mobSpawnCosts.keySet());
	}

	public List<ExtendedSpawnInfo.Spawners> getMobs(ExtendedFactionPointer faction) {
		return this.spawners.getOrDefault(faction, ImmutableList.of());
	}

	public Set<ExtendedFactionPointer> getSpawnerFactions() {
		return this.typesView;
	}

	@Nullable
	public ExtendedSpawnInfo.SpawnCosts getMobSpawnCost(EntityType<?> pEntityType) {
		return this.mobSpawnCosts.get(pEntityType);
	}

	public Set<EntityType<?>> getEntityTypes() {
		return this.costView;
	}

	public float getCreatureProbability() {
		return this.creatureGenerationProbability;
	}

	public boolean playerSpawnFriendly() {
		return this.playerSpawnFriendly;
	}

	public static class Builder {
		protected final Map<ExtendedFactionPointer, List<ExtendedSpawnInfo.Spawners>> spawners = new HashMap<>();
		protected final Map<EntityType<?>, ExtendedSpawnInfo.SpawnCosts> mobSpawnCosts = Maps.newLinkedHashMap();
		protected float creatureGenerationProbability = 0.1F;
		protected boolean playerCanSpawn;

		public ExtendedSpawnInfo.Builder addSpawn(ExtendedFactionPointer pClassification, ExtendedSpawnInfo.Spawners pSpawner) {
			this.spawners.get(pClassification).add(pSpawner);
			return this;
		}

		public ExtendedSpawnInfo.Builder addMobCharge(EntityType<?> pEntityType, double pSpawnCostPerEntity, double pMaxSpawnCost) {
			this.mobSpawnCosts.put(pEntityType, new ExtendedSpawnInfo.SpawnCosts(pMaxSpawnCost, pSpawnCostPerEntity));
			return this;
		}

		public ExtendedSpawnInfo.Builder creatureGenerationProbability(float pProbability) {
			this.creatureGenerationProbability = pProbability;
			return this;
		}

		public ExtendedSpawnInfo.Builder setPlayerCanSpawn() {
			this.playerCanSpawn = true;
			return this;
		}

		public ExtendedSpawnInfo build() {
			return new ExtendedSpawnInfo(this.creatureGenerationProbability, this.spawners.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_242576_0_) -> {
				return ImmutableList.copyOf(p_242576_0_.getValue());
			})), ImmutableMap.copyOf(this.mobSpawnCosts), this.playerCanSpawn);
		}
	}

	public static class SpawnCosts {
		public static final Codec<ExtendedSpawnInfo.SpawnCosts> CODEC = RecordCodecBuilder.create((p_242584_0_) -> {
			return p_242584_0_.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter((p_242586_0_) -> {
				return p_242586_0_.maxCost;
			}), Codec.DOUBLE.fieldOf("charge").forGetter((p_242583_0_) -> {
				return p_242583_0_.charge;
			})).apply(p_242584_0_, ExtendedSpawnInfo.SpawnCosts::new);
		});
		/**
		 * Determines the total amount of entities that can spawn in a location based on their current cost (e.g. a cost
		 * of 0.1 and a max total of 1 means at most ten entities can spawn in the given locatoin).
		 */
		private final double maxCost;
		/** Determines the cost per entity towards the maximum spawn cap. */
		private final double charge;

		private SpawnCosts(double pMaxCost, double pCharge) {
			this.maxCost = pMaxCost;
			this.charge = pCharge;
		}

		public double getEnergyBudget() {
			return this.maxCost;
		}

		public double getCharge() {
			return this.charge;
		}
	}

	public static class Spawners extends WeightedRandom.Item {
		public static final Codec<ExtendedSpawnInfo.Spawners> CODEC = RecordCodecBuilder.create((p_242592_0_) -> {
			return p_242592_0_.group(Registry.ENTITY_TYPE.fieldOf("type").forGetter((p_242595_0_) -> {
				return p_242595_0_.type;
			}), Codec.INT.fieldOf("weight").forGetter((p_242594_0_) -> {
				return p_242594_0_.weight;
			}), Codec.INT.fieldOf("minCount").forGetter((p_242593_0_) -> {
				return p_242593_0_.minCount;
			}), Codec.INT.fieldOf("maxCount").forGetter((p_242591_0_) -> {
				return p_242591_0_.maxCount;
			})).apply(p_242592_0_, ExtendedSpawnInfo.Spawners::new);
		});
		public final EntityType<?> type;
		public final int minCount;
		public final int maxCount;

		public Spawners(EntityType<?> pEntityType, int pWeight, int pMinCount, int pMaxCount) {
			super(pWeight);
			this.type = pEntityType;
			this.minCount = pMinCount;
			this.maxCount = pMaxCount;
		}

		@Override
		public String toString() {
			return EntityType.getKey(this.type) + "*(" + this.minCount + "-" + this.maxCount + "):" + this.weight;
		}
	}
}
