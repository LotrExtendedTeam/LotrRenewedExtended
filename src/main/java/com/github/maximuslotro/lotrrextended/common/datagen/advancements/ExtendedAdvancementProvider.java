package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.*;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedAdvancementProvider extends AdvancementProvider{
	public static int advancementsLegacyNum = 0;
	public static float advancementsLegacyNumTotal = 0.0f;
	public static int advancementsExtendedNum = 0;
	public static int secrets = 0;
	public ExtendedAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	@Override
	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
		ExtendedLog.info("###############EXTENDED###ADVANCEMENTS#############");
		new AdvancementsProviderAngmar().accept(consumer);
		new AdvancementsProviderBelegaer().accept(consumer);
		new AdvancementsProviderBlueMountains().accept(consumer);
		new AdvancementsProviderBree().accept(consumer);
		new AdvancementsProviderDale().accept(consumer);
		new AdvancementsProviderDorwinion().accept(consumer);
		new AdvancementsProviderDunland().accept(consumer);
		new AdvancementsProviderEnedwaith().accept(consumer);
		new AdvancementsProviderEregion().accept(consumer);
		new AdvancementsProviderEriador().accept(consumer);
		new AdvancementsProviderFangorn().accept(consumer);
		new AdvancementsProviderFarHarad().accept(consumer);
		new AdvancementsProviderFarHaradJungle().accept(consumer);
		new AdvancementsProviderForodwaith().accept(consumer);
		new AdvancementsProviderGeneral().accept(consumer);
		new AdvancementsProviderGondor().accept(consumer);
		new AdvancementsProviderIronHills().accept(consumer);
		new AdvancementsProviderLindon().accept(consumer);
		new AdvancementsProviderLothlorien().accept(consumer);
		new AdvancementsProviderMirkwood().accept(consumer);
		new AdvancementsProviderMistyMountains().accept(consumer);
		new AdvancementsProviderMordor().accept(consumer);
		new AdvancementsProviderNearHarad().accept(consumer);
		new AdvancementsProviderNindalf().accept(consumer);
		new AdvancementsProviderOrocarni().accept(consumer);
		new AdvancementsProviderPerdorogwaith().accept(consumer);
		new AdvancementsProviderRhovanion().accept(consumer);
		new AdvancementsProviderRhun().accept(consumer);
		new AdvancementsProviderRohan().accept(consumer);
		new AdvancementsProviderShire().accept(consumer);
		new AdvancementsProviderUtumno().accept(consumer);
		ExtendedLog.info("Built "+advancementsLegacyNum+" legacy advancements of "+advancementsLegacyNumTotal+" ("+((advancementsLegacyNum/advancementsLegacyNumTotal)*100.0F)+"%).");
		ExtendedLog.info("Also built "+advancementsExtendedNum+" new advancements unique to Extended!");
		ExtendedLog.info("In addition built "+secrets+" secret advancements!");
		ExtendedLog.info("###################################################");
	}

	@Override
	public String getName() {
		return "Renewed Extended Advancements Provider";
	}
}
