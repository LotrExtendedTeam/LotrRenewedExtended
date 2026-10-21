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
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitTypes;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitCombatTypes;
import com.google.gson.Gson;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedHiredUnitProfileProvider implements IDataProvider {
	public static final Gson GSON = ExtendedHiredUnitProfileManager.GSON;
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, ExtendedHiredUnitProfile> dataHere = new TreeMap<>();
	protected final DataGenerator dataGenerator;
	protected final String modId;

	public ExtendedHiredUnitProfileProvider(DataGenerator pDataGenerator, String pModId) {
		this.dataGenerator = pDataGenerator;
		this.modId = pModId;
	}

	public abstract void makeHiredUnitProfiles(Consumer<ExtendedHiredUnitProfile> pConsumer);

	@Override
	public void run(DirectoryCache pCache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder();
		Consumer<ExtendedHiredUnitProfile> consumer = (profile) -> {
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
			for(ResourceLocation key : ExtendedUnitTypes.UNIT_TYPES) {
				if(!dataHere.containsKey(key)) {
					LOGGER.warn("Didn't make expected unit profile type: "+key.toString());
				}
			}
		}
	}

	private static Path createPath(Path pPath, ExtendedHiredUnitProfile profile) {
		return pPath.resolve("data/" + profile.getId().getNamespace()+ "/hired_unit_profiles/" + profile.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Extended Hired Unit Profile Provider";
	}

	public ExtendedHiredUnitProfile.Builder make(EntityType<?> unitEntityType, ExtendedUnitCombatTypes pAttackType) {
		return new ExtendedHiredUnitProfile.Builder(modId, unitEntityType, pAttackType);
	}

}
