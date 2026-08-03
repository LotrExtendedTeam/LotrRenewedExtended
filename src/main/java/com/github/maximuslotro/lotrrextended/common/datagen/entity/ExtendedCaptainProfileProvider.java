package com.github.maximuslotro.lotrrextended.common.datagen.entity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfile;
import com.github.maximuslotro.lotrrextended.common.serialization.GsonSerializers;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedCaptainProfileProvider implements IDataProvider {
	public static final Gson GSON = GsonSerializers.createCaptainSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedCaptainProfile> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	public ExtendedCaptainProfileProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeCaptainProfiles(Consumer<ExtendedCaptainProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedCaptainProfile> consumer = (profile) -> {
			if (dataHere.put(profile.getId(), profile)!=null) {
				throw new IllegalStateException("Duplicate captain profile " + profile.getId().toString());
			} else {
				Path path1 = createPath(path, profile);

				try {
					IDataProvider.save(GSON, pCache, GSON.toJsonTree(profile), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save captain profile {}", path1, ioexception);
				}

			}
		};
		makeCaptainProfiles(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" captain profiles.");
		if(modId.equals(LotrExtendedMod.MODID)) {
			for(ResourceLocation key : ExtendedCaptainTypes.CAPTAIN_TYPES) {
				if(!dataHere.containsKey(key)) {
					LOGGER.warn("Didn't make expected captain profile type: "+key.toString());
				}
			}
		}
	}

	private static Path createPath(Path pPath, ExtendedCaptainProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/captain_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Captain Profile Provider";
	}

	public ExtendedCaptainProfile.Builder make(ResourceLocation id, int alignmentRequired) {
		return new ExtendedCaptainProfile.Builder(id, modId, alignmentRequired);
	}

}
