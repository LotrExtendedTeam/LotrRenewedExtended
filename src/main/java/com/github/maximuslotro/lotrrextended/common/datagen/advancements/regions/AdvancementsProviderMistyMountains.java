package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBiomePointers;
import com.google.common.collect.ImmutableList;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;


public class AdvancementsProviderMistyMountains extends ExtendedBaseAdvancementType{
	private static final List<RegistryKey<Biome>> MISTY_MOUNTAIN_BIOMES = ImmutableList.of(ExtendedBiomePointers.MISTY_MOUNTAINS,
			ExtendedBiomePointers.MISTY_MOUNTAINS_FOOTHILLS);

	@Override
	public void generateAdvancements() {
		Advancement mm = build(true, dataMain(Items.SNOW_BLOCK, "the_misty_mountains_cold", FrameType.TASK)
				.setTitle("The Mountains Cold").setDescription("Enter the Misty Mountains."),
				addBiomeTriggers(make(), MISTY_MOUNTAIN_BIOMES));
	}

	@Override
	public String getFolder() {
		return "misty_mountains";
	}

	@Override
	public int getLegacyCount() {
		return 3;
	}

}
