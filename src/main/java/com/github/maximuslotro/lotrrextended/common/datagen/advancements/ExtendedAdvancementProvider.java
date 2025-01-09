package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.AngmarAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.BlueMountainsAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.BreeAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.DaleAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.DunlandAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.EnedwaithAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.EregionAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.EriadorAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.FangornAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.ForodwaithAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.GeneralAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.IronHillsAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.LindonAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.LothlorienAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.MirkwoodAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.MistyMountainsAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.RhovanionAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.RohanAdvancementsProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.ShireAdvancementsProvider;

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
		new GeneralAdvancementsProvider().accept(consumer);
		new EriadorAdvancementsProvider().accept(consumer);
		new AngmarAdvancementsProvider().accept(consumer);
		new MirkwoodAdvancementsProvider().accept(consumer);
		new BlueMountainsAdvancementsProvider().accept(consumer);
		new BreeAdvancementsProvider().accept(consumer);
		new DunlandAdvancementsProvider().accept(consumer);
		new ForodwaithAdvancementsProvider().accept(consumer);
		new EnedwaithAdvancementsProvider().accept(consumer);
		new EregionAdvancementsProvider().accept(consumer);
		new MistyMountainsAdvancementsProvider().accept(consumer);
		new LindonAdvancementsProvider().accept(consumer);
		new ShireAdvancementsProvider().accept(consumer);
		new RhovanionAdvancementsProvider().accept(consumer);
		new DaleAdvancementsProvider().accept(consumer);
		new IronHillsAdvancementsProvider().accept(consumer);
		new LothlorienAdvancementsProvider().accept(consumer);
		new FangornAdvancementsProvider().accept(consumer);
		new RohanAdvancementsProvider().accept(consumer);
		ExtendedLog.info("###############EXTENDED###ADVANCEMENTS#############");
		ExtendedLog.info("Built "+advancementsLegacyNum+" legacy advancements of 666 ("+((advancementsLegacyNum/666.0F)*100.0F)+"%).");
		ExtendedLog.info("Also built "+advancementsExtendedNum+" new advancements unique to Extended!");
		ExtendedLog.info("In addition built "+secrets+" secret advancements!");
		ExtendedLog.info("###################################################");
	}

	@Override
	public String getName() {
		return "Renewed Extended Advancements Provider";
	}
}
