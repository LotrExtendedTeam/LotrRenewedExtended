package com.github.maximuslotro.lotrrextended.common.datagen.entity;

import com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles.ExtendedCaptainProfileGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles.ExtendedServerHiredUnitProfileGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.settings.ExtendedEntitySettingsGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.settings.ExtendedEntitySettingsMissingValidator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.spawners.ExtendedEntityStructureSpawnerGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.traderprofiles.ExtendedTraderProfileGenerator;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedEntityRelatedGenerators {
	public static void gatherData(DataGenerator generator, ExistingFileHelper existingFileHelper, String modid) {
		generator.addProvider(new ExtendedServerHiredUnitProfileGenerator(generator, modid));
		generator.addProvider(new ExtendedTraderProfileGenerator(generator, modid));
		generator.addProvider(new ExtendedCaptainProfileGenerator(generator, modid));
		generator.addProvider(new ExtendedEntityStructureSpawnerGenerator(generator, modid));
		generator.addProvider(new ExtendedEntitySettingsGenerator(generator, existingFileHelper));
		generator.addProvider(new ExtendedEntitySettingsMissingValidator(existingFileHelper));
	}
}
