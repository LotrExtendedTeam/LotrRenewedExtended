package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.*;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedAdvancementProvider extends AdvancementProvider{
	public static int advancementsNum = 0;
	public ExtendedAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

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
		ExtendedLog.info("Built "+advancementsNum+" advancements");
	}

	@Override
	public String getName() {
		return "Renewed Extended Advancements Provider";
	}
}
