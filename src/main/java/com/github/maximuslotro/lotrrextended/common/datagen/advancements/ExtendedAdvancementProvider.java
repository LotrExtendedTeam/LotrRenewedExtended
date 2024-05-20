package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions.*;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedAdvancementProvider extends AdvancementProvider{

	public ExtendedAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
		new EriadorAdvancementsProvider().accept(consumer);
	}
}
