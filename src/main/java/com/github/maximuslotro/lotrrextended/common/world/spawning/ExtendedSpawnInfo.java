package com.github.maximuslotro.lotrrextended.common.world.spawning;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedEntitiesNew;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.entity.EntityType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.MobSpawnInfo;

/**
 * Based upon {@link MobSpawnInfo}
 * */
public class ExtendedSpawnInfo {
	private final float biomeSpawnProbability;
	private final List<FactionSpawner> factionSpawners;
	private final Map<EntityType<?>, SpawnCosts> overrideMobSpawnCosts;

	private ExtendedSpawnInfo(float pSpawnProbability, List<ExtendedSpawnInfo.FactionSpawner> pFactionSpawners, List<SpawnCosts> pOverrideSpawnCosts) {
		this.biomeSpawnProbability = pSpawnProbability;
		this.factionSpawners = pFactionSpawners == null ? Collections.emptyList() : pFactionSpawners.stream().filter(Objects::nonNull).collect(Collectors.toList());
		Map<EntityType<?>, SpawnCosts> costMap = new HashMap<>();
		if (!pOverrideSpawnCosts.isEmpty()) {
			for (SpawnCosts cost : pOverrideSpawnCosts) {
				if (cost != null) {
					costMap.put(cost.getType(), cost);
				}
			}
		}
		this.overrideMobSpawnCosts = Collections.unmodifiableMap(costMap);
	}

	//TODO handle conquest 
	public GroupSpawner getSpawner(Random pRand) {
		return WeightedRandom.getRandomItem(pRand, factionSpawners).getGroupSpawner(pRand);
	}

	@Nullable
	public SpawnCosts getMobSpawnCost(EntityType<?> pEntityType) {
		return this.overrideMobSpawnCosts.get(pEntityType);
	}

	public float getCreatureProbability() {
		return this.biomeSpawnProbability;
	}

	public static class Serializer implements JsonDeserializer<ExtendedSpawnInfo>, JsonSerializer<ExtendedSpawnInfo> {
		@Override
		public ExtendedSpawnInfo deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "BiomeSpawnInfo");
			List<FactionSpawner> factionSpawners = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "factions", new FactionSpawner[0], context, FactionSpawner[].class));
			float biomeWeight = JSONUtils.getAsFloat(jsonobject, "biome_density", 1.0f);
			List<SpawnCosts> overrideCosts = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "override_costs", new SpawnCosts[0], context, SpawnCosts[].class));
			return new ExtendedSpawnInfo(biomeWeight, factionSpawners, overrideCosts);
		}
		@Override
		public JsonElement serialize(ExtendedSpawnInfo biomeSpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.add("factions", context.serialize(biomeSpawner.factionSpawners));
			if(!biomeSpawner.overrideMobSpawnCosts.isEmpty()) {
				jsonobject.add("override_costs", context.serialize(biomeSpawner.overrideMobSpawnCosts.values()));
			}
			jsonobject.addProperty("biome_density", biomeSpawner.getCreatureProbability());
			return jsonobject;
		}
	}

	public static class FactionSpawner extends WeightedRandom.Item {
		private final List<GroupSpawner> groupsSpawners;
		private final ExtendedFactionPointer faction;
		public FactionSpawner(int pWeight, ExtendedFactionPointer pFaction, List<GroupSpawner> pSpawnGroups) {
			super(pWeight);
			this.groupsSpawners = pSpawnGroups == null ? Collections.emptyList() : pSpawnGroups.stream().filter(Objects::nonNull).collect(Collectors.toList());
			this.faction = pFaction;
		}

		public GroupSpawner getGroupSpawner(Random pRand) {
			return WeightedRandom.getRandomItem(pRand, groupsSpawners);
		}

		public static class Serializer implements JsonDeserializer<FactionSpawner>, JsonSerializer<FactionSpawner> {
			@Override
			public FactionSpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
				JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "FactionSpawner");
				List<GroupSpawner> groups = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "groups", new GroupSpawner[0], context, GroupSpawner[].class));
				int weight = JSONUtils.getAsInt(jsonobject, "weight", 1);
				ExtendedFactionPointer faction = new ExtendedFactionPointer(new ResourceLocation(JSONUtils.getAsString(jsonobject, "faction", "lotrextended:unknown")));
				return new FactionSpawner(weight, faction, groups);
			}
			@Override
			public JsonElement serialize(FactionSpawner factionSpawner, Type p_serialize_2_, JsonSerializationContext context) {
				JsonObject jsonobject = new JsonObject();
				jsonobject.addProperty("weight", factionSpawner.weight);
				jsonobject.addProperty("faction", factionSpawner.faction.getFactionID().toString());
				jsonobject.add("groups", context.serialize(factionSpawner.groupsSpawners));
				return jsonobject;
			}
		}
	}

	public static class GroupSpawner extends WeightedRandom.Item {
		private final List<IndividualSpawner> individualSpawners;
		public final int minCount;
		public final int maxCount;

		public GroupSpawner(int pWeight, List<IndividualSpawner> pSpawnIndividuals, int pMinCount, int pMaxCount) {
			super(pWeight);
			this.minCount = pMinCount;
			this.maxCount = pMaxCount;
			this.individualSpawners = pSpawnIndividuals == null ? Collections.emptyList() : pSpawnIndividuals.stream().filter(Objects::nonNull).collect(Collectors.toList());
		}
		public IndividualSpawner getSpawner(Random pRand) {
			return WeightedRandom.getRandomItem(pRand, individualSpawners);
		}

		public static class Serializer implements JsonDeserializer<GroupSpawner>, JsonSerializer<GroupSpawner> {
			@Override
			public GroupSpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
				JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "GroupSpawner");
				List<IndividualSpawner> spawnEntities = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "entities", new IndividualSpawner[0], context, IndividualSpawner[].class));
				int weight = JSONUtils.getAsInt(jsonobject, "weight", 1);
				int min = JSONUtils.getAsInt(jsonobject, "min", 1);
				int max = JSONUtils.getAsInt(jsonobject, "max", 4);
				return new GroupSpawner(weight, spawnEntities, min, max);
			}
			@Override
			public JsonElement serialize(GroupSpawner group, Type p_serialize_2_, JsonSerializationContext context) {
				JsonObject jsonobject = new JsonObject();
				jsonobject.addProperty("weight", group.weight);
				jsonobject.add("entities", context.serialize(group.individualSpawners));
				jsonobject.addProperty("min", group.minCount);
				jsonobject.addProperty("max", group.maxCount);
				return jsonobject;
			}
		}
	}

	public static class IndividualSpawner extends WeightedRandom.Item {
		private final EntityType<?> type;

		public IndividualSpawner(int pWeight, EntityType<?> pEntityType) {
			super(pWeight);
			this.type = pEntityType;
		}

		public EntityType<?> getType() {
			return type;
		}

		public static class Serializer implements JsonDeserializer<IndividualSpawner>, JsonSerializer<IndividualSpawner> {
			@Override
			public IndividualSpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
				JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "IndividualSpawnerEntry");
				String entityId = JSONUtils.getAsString(jsonobject, "entity_type");
				EntityType<?> entityType = ExtendedEntitiesNew.getEntityType(entityId);
				if (entityType == null) {
					ExtendedLog.warn("Failed to load unit entity type: {}", entityId);
					return null;
				}
				int weight = JSONUtils.getAsInt(jsonobject, "weight", 1);
				return new IndividualSpawner(weight, entityType);
			}
			@Override
			public JsonElement serialize(IndividualSpawner spawner, Type p_serialize_2_, JsonSerializationContext context) {
				JsonObject jsonobject = new JsonObject();
				jsonobject.addProperty("entity_type", spawner.getType().getRegistryName().toString());
				jsonobject.addProperty("weight", spawner.weight);
				return jsonobject;
			}
		}
	}

	public static class SpawnCosts {
		private final EntityType<?> type;
		private final float biomeWeight;
		private final float biomeCap;

		public SpawnCosts(EntityType<?> pEntityType, float pBiomeWeight, float pBiomeCap) {
			this.type = pEntityType;
			this.biomeWeight = pBiomeWeight;
			this.biomeCap = pBiomeCap;
		}

		public EntityType<?> getType() {
			return type;
		}

		public double getBiomeSpawnWeight() {
			return biomeWeight;
		}

		public double getBiomeSpawnCap() {
			return biomeCap;
		}

		public static class Serializer implements JsonDeserializer<SpawnCosts>, JsonSerializer<SpawnCosts> {
			@Override
			public SpawnCosts deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
				JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "SpawnCosts");
				String entityId = JSONUtils.getAsString(jsonobject, "entity_type");
				EntityType<?> entityType = ExtendedEntitiesNew.getEntityType(entityId);
				if (entityType == null) {
					ExtendedLog.warn("Failed to load unit entity type: {}", entityId);
					return null;
				}
				float biomeWeight = JSONUtils.getAsFloat(jsonobject, "biome_weight", 1.0F);
				float biomeCap = JSONUtils.getAsFloat(jsonobject, "biome_cap", 10.0F);
				return new SpawnCosts(entityType, biomeWeight, biomeCap);
			}
			@Override
			public JsonElement serialize(SpawnCosts costs, Type p_serialize_2_, JsonSerializationContext context) {
				JsonObject jsonobject = new JsonObject();
				jsonobject.addProperty("entity_type", costs.getType().getRegistryName().toString());
				jsonobject.addProperty("biome_weight", costs.getBiomeSpawnWeight());
				jsonobject.addProperty("biome_cap", costs.getBiomeSpawnCap());
				return jsonobject;
			}
		}
	}
}
