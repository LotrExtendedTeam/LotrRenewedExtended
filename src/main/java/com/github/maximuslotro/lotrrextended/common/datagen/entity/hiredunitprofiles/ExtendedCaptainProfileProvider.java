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
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedCaptainProfileManager;
import com.github.maximuslotro.lotrrextended.common.serialization.GsonSerializers;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedCaptainProfileProvider implements IDataProvider {
	public static final Gson GSON_PRETTY_WRITER = GsonSerializers.createCaptainSerializer().disableHtmlEscaping().setPrettyPrinting().create();
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
	               save(GSON_PRETTY_WRITER, pCache, profile, path1);
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

	private static void save(Gson pGson, DirectoryCache pCache, ExtendedCaptainProfile spawner, Path pPath) throws IOException {
		try {
			String s = ExtendedCaptainProfileManager.serialize(pGson, spawner);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());

				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save captain profile {}", pPath, ioexception);
		}
	}

	private static Path createPath(Path pPath, ExtendedCaptainProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/captain_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Captain Profile Provider";
	}

	public ExtendedCaptainProfile.Builder make(ResourceLocation id) {
		return new ExtendedCaptainProfile.Builder(id, modId);
	}

}
