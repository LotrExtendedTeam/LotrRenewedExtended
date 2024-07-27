package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TreeMap;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
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

	public abstract void makeStructureSpawnPools(Consumer<ExtendedStructureEntitySpawner> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedStructureEntitySpawner> consumer = (spawner) -> {
	         if (dataHere.put(spawner.getId(), spawner)!=null) {
	            throw new IllegalStateException("Duplicate structure spawner type " + spawner.getId().toString());
	         } else {
	            Path path1 = createPath(path, spawner);

	            try {
	               save(GSON, pCache, spawner, path1);
	            } catch (IOException ioexception) {
	               LOGGER.error("Couldn't save advancement {}", path1, ioexception);
	            }

	         }
	      };
		makeStructureSpawnPools(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" structure entity spawners.");
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

	private static Path createPath(Path pPath, ExtendedStructureEntitySpawner spawner) {
		return pPath.resolve("data/" + spawner.getId().getNamespace()+ "/entity_structure_spawners/" + spawner.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Structure Entity Spawner Provider";
	}

	public ExtendedStructureEntitySpawner.Builder make(boolean inSingleUse) {
		return ExtendedStructureEntitySpawner.Builder.spawner(inSingleUse);
	}

	public ExtendedStructureEntitySpawner.Builder single(EntityType<?> type) {
		return single(type, 1, 1);
	}

	public ExtendedStructureEntitySpawner.Builder single(EntityType<?> type, int spawnMin, int spawnMax) {
		return make(true).addSinglePool(type, 100, spawnMin, spawnMax);
	}

	public ExtendedStructureEntitySpawner.Builder single(EntityType<?> type, int spawnMin, int spawnMax, boolean randomHome, int radius) {
		return make(true).addSinglePool(type, 100, spawnMin, spawnMax).setHomeInfo(randomHome, radius);
	}

	public ExtendedStructureEntitySpawner.Builder multi(MultiSpawnType multiPool) {
		return multi(multiPool, 1, 1);
	}

	public ExtendedStructureEntitySpawner.Builder multi(MultiSpawnType multiPool, int spawnMin, int spawnMax) {
		return make(true).addMultiPool(multiPool, 100, spawnMin, spawnMax);
	}

	public ExtendedStructureEntitySpawner.Builder multi(MultiSpawnType multiPool, int spawnMin, int spawnMax, boolean randomHome, int radius) {
		return make(true).addMultiPool(multiPool, 100, spawnMin, spawnMax).setHomeInfo(randomHome, radius);
	}
}
