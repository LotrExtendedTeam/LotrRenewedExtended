package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.MobSpawnInfo;

/**
 * Based upon {@link MobSpawnInfo}
 * */
public class ExtendedSpawnInfo {
	private final float biomeSpawnProbability;
	private final List<FactionSpawner> factionSpawners;
	private final Map<EntityType<?>, ExtendedSpawnInfo.SpawnCosts> overrideMobSpawnCosts;

	private ExtendedSpawnInfo(float pSpawnProbability, List<ExtendedSpawnInfo.FactionSpawner> pFactionSpawners, Map<EntityType<?>, ExtendedSpawnInfo.SpawnCosts> pOverrideSpawnCosts) {
		this.biomeSpawnProbability = pSpawnProbability;
		this.factionSpawners = pFactionSpawners;
		this.overrideMobSpawnCosts = pOverrideSpawnCosts;
	}

	//TODO handle conquest 
	public GroupSpawner getSpawner(Random pRand) {
		return WeightedRandom.getRandomItem(pRand, factionSpawners).getGroupSpawner(pRand);
	}

	@Nullable
	public ExtendedSpawnInfo.SpawnCosts getMobSpawnCost(EntityType<?> pEntityType) {
		return this.overrideMobSpawnCosts.get(pEntityType);
	}

	public float getCreatureProbability() {
		return this.biomeSpawnProbability;
	}

	public static class FactionSpawner extends WeightedRandom.Item {
		private final List<GroupSpawner> groupsSpawners;

		public FactionSpawner(int pWeight, List<GroupSpawner> pSpawnGroups) {
			super(pWeight);
			groupsSpawners = pSpawnGroups;
		}

		public GroupSpawner getGroupSpawner(Random pRand) {
			return WeightedRandom.getRandomItem(pRand, groupsSpawners);
		}
	}

	public static class GroupSpawner extends WeightedRandom.Item {
		private final List<IndividualSpawner> individualSpawners;
		public final int minCount;
		public final int maxCount;

		public GroupSpawner(List<IndividualSpawner> pSpawnIndividuals, int pWeight, int pMinCount, int pMaxCount) {
			super(pWeight);
			this.minCount = pMinCount;
			this.maxCount = pMaxCount;
			individualSpawners = pSpawnIndividuals;
		}
		public IndividualSpawner getSpawner(Random pRand) {
			return WeightedRandom.getRandomItem(pRand, individualSpawners);
		}
	}

	public static class IndividualSpawner extends WeightedRandom.Item {
		private final EntityType<?> type;

		public IndividualSpawner(EntityType<?> pEntityType, int pWeight) {
			super(pWeight);
			this.type = pEntityType;
		}

		public EntityType<?> getType() {
			return type;
		}
	}

	public static class SpawnCosts {
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
}
