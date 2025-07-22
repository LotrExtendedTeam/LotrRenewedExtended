package com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles;

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
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitAttackType;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedServerHiredUnitProfileProvider implements IDataProvider {
	public static final Gson GSON_PRETTY_WRITER = GsonSerializers.createHiredUnitSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedServerHiredUnitProfile> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	public ExtendedServerHiredUnitProfileProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeStructureSpawnPools(Consumer<ExtendedServerHiredUnitProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedServerHiredUnitProfile> consumer = (spawner) -> {
	         if (dataHere.put(spawner.getId(), spawner)!=null) {
	            throw new IllegalStateException("Duplicate hired unit profile " + spawner.getId().toString());
	         } else {
	            Path path1 = createPath(path, spawner);

	            try {
	               save(GSON_PRETTY_WRITER, pCache, spawner, path1);
	            } catch (IOException ioexception) {
	               LOGGER.error("Couldn't save hired unit profile {}", path1, ioexception);
	            }

	         }
	      };
		makeStructureSpawnPools(consumer);
		ExtendedLog.infoShown("Built "+dataHere.size()+" hired unit profiles.");
		for(ResourceLocation key : ExtendedHiredUnitTypes.UNIT_TYPES) {
			if(!dataHere.containsKey(key)) {
				LOGGER.warn("Didn't make expected unit profile type: "+key.toString());
			}
		}
	}

	private static void save(Gson pGson, DirectoryCache pCache, ExtendedServerHiredUnitProfile spawner, Path pPath) throws IOException {
		try {
			String s = ExtendedServerHiredUnitProfileManager.serialize(pGson, spawner);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());

				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save hired unit profile {}", pPath, ioexception);
		}
	}

	private static Path createPath(Path pPath, ExtendedServerHiredUnitProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/hired_unit_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Hired Unit Profile Provider";
	}

	public ExtendedServerHiredUnitProfile.Builder make(EntityType<?> unitEntityType, ExtendedUnitAttackType pAttackType) {
		return new ExtendedServerHiredUnitProfile.Builder(modId, unitEntityType, pAttackType);
	}

}
