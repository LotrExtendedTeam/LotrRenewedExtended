package com.github.maximuslotro.lotrrextended.common.datagen.world;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedBiomeInformation;
import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedBiomeInformationManager;
import com.google.gson.Gson;

import lotr.common.init.LOTRBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;

public abstract class ExtendedBiomeInformationProvider implements IDataProvider {
	public static final Gson GSON = ExtendedBiomeInformationManager.GSON;
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedBiomeInformation> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	public ExtendedBiomeInformationProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeBiomeInformation(Consumer<ExtendedBiomeInformation> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedBiomeInformation> consumer = (information) -> {
			if (dataHere.put(information.getBiomeId(), information)!=null) {
				throw new IllegalStateException("Duplicate biome information " + information.getBiomeId().toString());
			} else {
				Path path1 = createPath(path, information);
				try {
					IDataProvider.save(GSON, pCache, GSON.toJsonTree(information), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save hired unit profile {}", path1, ioexception);
				}

			}
		};
		makeBiomeInformation(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" biome information.");
		if(modId.equals(LotrExtendedMod.MODID)) {
			for(RegistryObject<Biome> key : LOTRBiomes.BIOMES.getEntries()) {
				if(!dataHere.containsKey(key.getId())) {
					LOGGER.warn("Didn't make expected biome information: "+key.getId().toString());
				}
			}
		}
	}

	private static Path createPath(Path pPath, ExtendedBiomeInformation profile) {
		return pPath.resolve("data/" + profile.getBiomeId().getNamespace() +"/"+ ExtendedBiomeInformationManager.directory +"/"+ profile.getBiomeId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Biome Information Provider";
	}

	public ExtendedBiomeInformation.Builder make(Biome pBiome) {
		return new ExtendedBiomeInformation.Builder(pBiome);
	}

}
