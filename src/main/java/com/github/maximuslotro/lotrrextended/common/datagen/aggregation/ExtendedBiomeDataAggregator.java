package com.github.maximuslotro.lotrrextended.common.datagen.aggregation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lotr.common.init.LOTRBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBiomeDataAggregator extends ExtendedInfoAggregator {
	public static final Gson GSON_PRETTY_WRITER = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private final Map<EntityType<?>, List<ResourceLocation>> entityBiomeMap = new HashMap<>();
	protected final DataGenerator dataGenerator;
	private final ExistingFileHelper fileHelper;

	public ExtendedBiomeDataAggregator(DataGenerator pDataGenerator, ExistingFileHelper fileHelper) {
		this.dataGenerator = pDataGenerator;
		this.fileHelper = fileHelper;
	}

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		for (RegistryObject<Biome> regBiome : LOTRBiomes.BIOMES.getEntries()) {
		    Biome biome = regBiome.get();
		    ResourceLocation biomeName = biome.getRegistryName();
		    MobSpawnInfo mobSettings = biome.getMobSettings();
		    for (EntityClassification category : EntityClassification.values()) {
		        for (MobSpawnInfo.Spawners spawner : mobSettings.getMobs(category)) {
		            EntityType<?> type = spawner.type;
		            entityBiomeMap.computeIfAbsent(type, k -> new ArrayList<>()).add(biomeName);
		        }
		    }
		}
		Path output = this.dataGenerator.getOutputFolder();

		for (Map.Entry<EntityType<?>, List<ResourceLocation>> entry : entityBiomeMap.entrySet()) {
		    EntityType<?> type = entry.getKey();
		    List<ResourceLocation> biomes = entry.getValue();
		    EntitySpawnInfo info = new EntitySpawnInfo(type, biomes);
		    Path filePath = createPath(output, type.getRegistryName(), "creatures");
		    save(GSON_PRETTY_WRITER, pCache, GSON_PRETTY_WRITER.toJsonTree(info), filePath);
		}
		ExtendedLog.info("Dumped creature spawn info types: "+ entityBiomeMap.size());
	}

	@Override
	public String getName() {
		return "Extended biome info generator";
	}

	public static class EntitySpawnInfo {
	    public String entity;
	    public List<String> biomes;

	    public EntitySpawnInfo(EntityType<?> type, List<ResourceLocation> biomes) {
	        this.entity = type.getRegistryName().toString();
	        this.biomes = biomes.stream().map(ResourceLocation::toString).sorted().collect(Collectors.toList());
	    }
	}
}
