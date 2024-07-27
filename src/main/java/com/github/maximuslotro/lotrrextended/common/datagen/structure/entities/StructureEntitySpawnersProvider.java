package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TreeMap;

import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.types.MultiSpawnType;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class StructureEntitySpawnersProvider implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = GsonSerializers.createStructureSpawnerSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private final Map<ResourceLocation, ExtendedStructureEntitySpawner> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	StructureEntitySpawnersProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;

	}

	public abstract void makeStructureSpawnPools();

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		makeStructureSpawnPools();
		Path path = this.dataGenerator.getOutputFolder();
		for (Entry<ResourceLocation, ExtendedStructureEntitySpawner> spawner : dataHere.entrySet()) {
			save(GSON, pCache, spawner.getValue(), path.resolve("data/" + modId + "/entity_structure_spawners/" + spawner.getKey().getPath() + ".json"));
		}
	}

	private static void save(Gson pGson, DirectoryCache pCache, Object object, Path pPath) throws IOException {
		try {
			String s = pGson.toJson(object);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());

				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save structure spawn pool {}", pPath, ioexception);
		}
	}

	@Override
	public String getName() {
		return "Extended Structure Entity Spawner Provider";
	}

	public void buildSingleSpawner(ResourceLocation location, EntityType<?> type) {
		buildSpawner(location, ExtendedStructureEntitySpawner.Builder.spawner(true).addSinglePool(type, 100, 1, 1));
	}

	public void buildSingleSpawner(ResourceLocation location, EntityType<?> type, int spawnMin, int spawnMax) {
		buildSpawner(location, ExtendedStructureEntitySpawner.Builder.spawner(true).addSinglePool(type, 100, spawnMin, spawnMax));
	}

	public void buildSingleSpawner(ResourceLocation location, EntityType<?> type, int spawnMin, int spawnMax, boolean randomHome, int radius) {
		buildSpawner(location, ExtendedStructureEntitySpawner.Builder.spawner(true).addSinglePool(type, 100, spawnMin, spawnMax).setHomeInfo(randomHome, radius));
	}

	public void buildMultiSpawner(ResourceLocation location, MultiSpawnType multiPool) {
		buildSpawner(location, ExtendedStructureEntitySpawner.Builder.spawner(true).addMultiPool(multiPool, 100, 1, 1));
	}

	public void buildMultiSpawner(ResourceLocation location, MultiSpawnType multiPool, int spawnMin, int spawnMax) {
		buildSpawner(location, ExtendedStructureEntitySpawner.Builder.spawner(true).addMultiPool(multiPool, 100, spawnMin, spawnMax));
	}

	public void buildMultiSpawner(ResourceLocation location, MultiSpawnType multiPool, int spawnMin, int spawnMax, boolean randomHome, int radius) {
		buildSpawner(location, ExtendedStructureEntitySpawner.Builder.spawner(true).addMultiPool(multiPool, 100, spawnMin, spawnMax).setHomeInfo(randomHome, radius));
	}

	public void buildSpawner(ResourceLocation location, ExtendedStructureEntitySpawner.Builder builder) {
		dataHere.put(location, builder.build());
	}
}
