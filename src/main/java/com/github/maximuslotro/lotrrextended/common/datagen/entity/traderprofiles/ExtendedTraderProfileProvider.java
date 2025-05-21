package com.github.maximuslotro.lotrrextended.common.datagen.entity.traderprofiles;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfile;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfileManager;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedTraderProfileProvider implements IDataProvider {
	public static final Gson GSON_PRETTY_WRITER = GsonSerializers.createTraderSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedTraderProfile> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	public ExtendedTraderProfileProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeStructureSpawnPools(Consumer<ExtendedTraderProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedTraderProfile> consumer = (spawner) -> {
	         if (dataHere.put(spawner.getId(), spawner)!=null) {
	            throw new IllegalStateException("Duplicate trader profile " + spawner.getId().toString());
	         } else {
	            Path path1 = createPath(path, spawner);

	            try {
	               save(GSON_PRETTY_WRITER, pCache, spawner, path1);
	            } catch (IOException ioexception) {
	               LOGGER.error("Couldn't save trader profile {}", path1, ioexception);
	            }

	         }
	      };
		makeStructureSpawnPools(consumer);
		ExtendedLog.infoShown("Built "+dataHere.size()+" trader profiles.");
	}

	private static void save(Gson pGson, DirectoryCache pCache, ExtendedTraderProfile spawner, Path pPath) throws IOException {
		try {
			String s = ExtendedTraderProfileManager.serialize(pGson, spawner);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());

				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save trader profile {}", pPath, ioexception);
		}
	}

	private static Path createPath(Path pPath, ExtendedTraderProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/trader_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Trader Profile Provider";
	}

	public ExtendedTraderProfile.Builder make(EntityType<?> traderEntityType, int pTradingAlignmentRequired) {
		return new ExtendedTraderProfile.Builder(traderEntityType, pTradingAlignmentRequired);
	}

}
