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
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitCombatTypes;
import com.github.maximuslotro.lotrrextended.common.serialization.GsonSerializers;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedHiredUnitProfileProvider implements IDataProvider {
	public static final Gson GSON = GsonSerializers.createHiredUnitSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedServerHiredUnitProfile> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	public ExtendedHiredUnitProfileProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeHiredUnitProfiles(Consumer<ExtendedServerHiredUnitProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedServerHiredUnitProfile> consumer = (profile) -> {
			if (dataHere.put(profile.getId(), profile)!=null) {
				throw new IllegalStateException("Duplicate hired unit profile " + profile.getId().toString());
			} else {
				Path path1 = createPath(path, profile);
				try {
					IDataProvider.save(GSON, pCache, GSON.toJsonTree(profile), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save hired unit profile {}", path1, ioexception);
				}

			}
		};
		makeHiredUnitProfiles(consumer);
		ExtendedLog.info("Built "+dataHere.size()+" hired unit profiles.");
		if(modId.equals(LotrExtendedMod.MODID)) {
			for(ResourceLocation key : ExtendedHiredUnitTypes.UNIT_TYPES) {
				if(!dataHere.containsKey(key)) {
					LOGGER.warn("Didn't make expected unit profile type: "+key.toString());
				}
			}
		}
	}

	private static Path createPath(Path pPath, ExtendedServerHiredUnitProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/hired_unit_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Hired Unit Profile Provider";
	}

	public ExtendedServerHiredUnitProfile.Builder make(EntityType<?> unitEntityType, ExtendedUnitCombatTypes pAttackType) {
		return new ExtendedServerHiredUnitProfile.Builder(modId, unitEntityType, pAttackType);
	}

}
