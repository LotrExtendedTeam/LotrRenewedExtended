package com.github.maximuslotro.lotrrextended.common.datagen.map;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public abstract class ExtendedMapBiomeColorsProvider implements IDataProvider {
	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	protected final DataGenerator dataGenerator;
	protected final String modId;
	private final int loadOrder;
	private Map<String, String> biomeColor = new TreeMap<>();

	public ExtendedMapBiomeColorsProvider(DataGenerator pDataGenerator, String pModId, int pLoadOrder) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
		this.loadOrder = pLoadOrder;
	}

	public abstract void makeBiomes();

	public void addBiome(RegistryKey<Biome> pBiome, String colorID) {
		addBiome(pBiome.location(), colorID);
	}

	public void addBiome(ResourceLocation pBiome, String colorID) {
		biomeColor.put(pBiome.toString(), colorID);
	}

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		makeBiomes();
		Path json = createPath(this.dataGenerator.getOutputFolder(), modId);
		IDataProvider.save(GSON, pCache, serialize(), json);
	}

	public static Path createPath(Path pPath, String pModId) {
		return pPath.resolve("data/" +pModId+ "/map/biomecolors/" + pModId + "_biome_colors.json");
	}

	@Override
	public String getName() {
		return "Extended Map Biome Color Provider";
	}

	public JsonObject serialize() {
		JsonObject biomeColorsJson = new JsonObject();
		biomeColorsJson.addProperty("load_order", loadOrder);
		JsonObject biomes = new JsonObject();
		for(Entry<String, String> biome : biomeColor.entrySet()) {
			biomes.addProperty(biome.getKey(), biome.getValue());
		}
		biomeColorsJson.add("biome_colors", biomes);
		return biomeColorsJson;
	}
}
