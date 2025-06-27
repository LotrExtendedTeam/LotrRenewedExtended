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
	public static int advancementsExtendedNum = 0;
	public static int secrets = 0;
	public ExtendedAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	@Override
	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
		new AdvancementsProviderGeneral().accept(consumer);
		new AdvancementsProviderEriador().accept(consumer);
		new AdvancementsProviderAngmar().accept(consumer);
		new AdvancementsProviderMirkwood().accept(consumer);
		new AdvancementsProviderBlueMountains().accept(consumer);
		new AdvancementsProviderBree().accept(consumer);
		new AdvancementsProviderDunland().accept(consumer);
		new AdvancementsProviderForodwaith().accept(consumer);
		new AdvancementsProviderEnedwaith().accept(consumer);
		new AdvancementsProviderEregion().accept(consumer);
		new AdvancementsProviderMistyMountains().accept(consumer);
		new AdvancementsProviderLindon().accept(consumer);
		new AdvancementsProviderShire().accept(consumer);
		new AdvancementsProviderRhovanion().accept(consumer);
		new AdvancementsProviderDale().accept(consumer);
		new AdvancementsProviderIronHills().accept(consumer);
		new AdvancementsProviderLothlorien().accept(consumer);
		new AdvancementsProviderFangorn().accept(consumer);
		new AdvancementsProviderRohan().accept(consumer);
		new AdvancementsProviderNindalf().accept(consumer);
		new AdvancementsProviderGondor().accept(consumer);
		new AdvancementsProviderMordor().accept(consumer);
		new AdvancementsProviderDorwinion().accept(consumer);
		new AdvancementsProviderRhun().accept(consumer);
		new AdvancementsProviderNearHarad().accept(consumer);
		ExtendedLog.infoShown("###############EXTENDED###ADVANCEMENTS#############");
		ExtendedLog.infoShown("Built "+advancementsLegacyNum+" legacy advancements of 666 ("+((advancementsLegacyNum/666.0F)*100.0F)+"%).");
		ExtendedLog.infoShown("Also built "+advancementsExtendedNum+" new advancements unique to Extended!");
		ExtendedLog.infoShown("In addition built "+secrets+" secret advancements!");
		ExtendedLog.infoShown("###################################################");
	}

	@Override
	public String getName() {
		return "Renewed Extended Advancements Provider";
	}
}
