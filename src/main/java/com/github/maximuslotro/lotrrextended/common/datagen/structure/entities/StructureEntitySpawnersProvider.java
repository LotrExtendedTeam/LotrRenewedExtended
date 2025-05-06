package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.SpawnerType;
import com.github.maximuslotro.lotrrextended.common.spawners.types.MultiSpawnType;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class StructureEntitySpawnersProvider implements IDataProvider {
	public static final Gson GSON_PRETTY_WRITER = GsonSerializers.createStructureSpawnerSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedStructureEntitySpawner> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;
	protected static final List<ResourceLocation> madeSpawners = new ArrayList<>();

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
	               save(GSON_PRETTY_WRITER, pCache, spawner, path1);
	            } catch (IOException ioexception) {
	               LOGGER.error("Couldn't save structure spawner {}", path1, ioexception);
	            }

	         }
	      };
		makeStructureSpawnPools(consumer);
		for(ResourceLocation location : ExtendedSpawners.getSpawners()) {
			if(!madeSpawners.contains(location)) {
				System.out.println("Didn't make a sepcified Structure Spawner type: "+location.toString());
			}
		}
		ExtendedLog.infoShown("Built "+dataHere.size()+" structure entity spawners.");
	}

	private static void save(Gson pGson, DirectoryCache pCache, ExtendedStructureEntitySpawner spawner, Path pPath) throws IOException {
		try {
			String s = ExtendedStructureEntitySpawnManager.serialize(pGson, spawner);
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
		madeSpawners.add(spawner.getId());
		return pPath.resolve("data/" + spawner.getId().getNamespace()+ "/entity_structure_spawners/" + spawner.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Structure Entity Spawner Provider";
	}

	public ExtendedStructureEntitySpawner.Builder make(SpawnerType spawnType) {
		return ExtendedStructureEntitySpawner.Builder.spawner(spawnType);
	}

	public ExtendedStructureEntitySpawner.Builder single(EntityType<?> type) {
		return singleWeighted(type, 100, 1, 1, 0);
	}

	public ExtendedStructureEntitySpawner.Builder single(EntityType<?> type, int spawnMin, int spawnMax, int spawnCap) {
		return singleWeighted(type, 100, spawnMin, spawnMax, spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder single(EntityType<?> type, int spawnMin, int spawnMax, boolean randomHome, int radius, int spawnCap) {
		return singleWeighted(type, 100, spawnMin, spawnMax, spawnCap).setHomeInfo(randomHome, radius);
	}

	public ExtendedStructureEntitySpawner.Builder couple(EntityType<?> type, int radius) {
		return multiWeighted(SpawnerType.SINGLE_FAMILY, type, 100, 1, 1, 0).setHomeInfo(false, radius);
	}

	public ExtendedStructureEntitySpawner.Builder singleWeighted(EntityType<?> type, int weight, int spawnMin, int spawnMax) {
		return multiWeighted(SpawnerType.SINGLE, type, weight, spawnMin, spawnMax, 0);
	}
	public ExtendedStructureEntitySpawner.Builder singleWeighted(EntityType<?> type, int weight, int spawnMin, int spawnMax, int spawnCap) {
		return multiWeighted(SpawnerType.SINGLE, type, weight, spawnMin, spawnMax, spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder persisted(EntityType<?> type) {
		return persisted(type, 1, 1, 4);
	}

	public ExtendedStructureEntitySpawner.Builder persisted(EntityType<?> type, int spawnMin, int spawnMax, int spawnCap) {
		return multiWeighted(SpawnerType.PERSISTED, type, 100, spawnMin, spawnMax, spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder persisted(EntityType<?> type, int spawnMin, int spawnMax, boolean randomHome, int radius, int spawnCap) {
		return multiWeighted(SpawnerType.PERSISTED, type, 100, spawnMin, spawnMax, spawnCap).setHomeInfo(randomHome, radius);
	}

	public ExtendedStructureEntitySpawner.Builder single(MultiSpawnType type) {
		return singleWeighted(type, 100, 1, 1, 0);
	}

	public ExtendedStructureEntitySpawner.Builder single(MultiSpawnType type, int spawnMin, int spawnMax, int spawnCap) {
		return singleWeighted(type, 100, spawnMin, spawnMax, spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder single(MultiSpawnType type, int spawnMin, int spawnMax, boolean randomHome, int radius, int spawnCap) {
		return singleWeighted(type, 100, spawnMin, spawnMax, spawnCap).setHomeInfo(randomHome, radius);
	}

	public ExtendedStructureEntitySpawner.Builder singleWeighted(MultiSpawnType type, int weight, int spawnMin, int spawnMax) {
		return multiWeighted(SpawnerType.SINGLE, type, weight, spawnMin, spawnMax, 0);
	}
	public ExtendedStructureEntitySpawner.Builder singleWeighted(MultiSpawnType type, int weight, int spawnMin, int spawnMax, int spawnCap) {
		return multiWeighted(SpawnerType.SINGLE, type, weight, spawnMin, spawnMax, spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder persisted(MultiSpawnType type) {
		return persisted(type, 1, 1, 4);
	}

	public ExtendedStructureEntitySpawner.Builder persisted(MultiSpawnType type, int spawnMin, int spawnMax, int spawnCap) {
		return multiWeighted(SpawnerType.PERSISTED, type, 100, spawnMin, spawnMax, spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder persisted(MultiSpawnType type, int spawnMin, int spawnMax, boolean randomHome, int radius, int spawnCap) {
		return multiWeighted(SpawnerType.PERSISTED, type, 100, spawnMin, spawnMax, spawnCap).setHomeInfo(randomHome, radius);
	}

	public ExtendedStructureEntitySpawner.Builder multiWeighted(SpawnerType spawnerType, MultiSpawnType type, int weight, int spawnMin, int spawnMax, int spawnCap) {
		return make(spawnerType).addMultiPool(type, weight, spawnMin, spawnMax).setlocalSpawnCap(spawnCap);
	}

	public ExtendedStructureEntitySpawner.Builder multiWeighted(SpawnerType spawnerType, EntityType<?> type, int weight, int spawnMin, int spawnMax, int spawnCap) {
		return make(spawnerType).addSinglePool(type, weight, spawnMin, spawnMax).setlocalSpawnCap(spawnCap);
	}

}
