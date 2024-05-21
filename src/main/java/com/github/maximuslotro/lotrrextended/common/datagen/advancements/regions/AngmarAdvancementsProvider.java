package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.util.text.TextFormatting;


public class AngmarAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> ANGMAR_BIOMES = ImmutableList.of(LOTRBiomes.ANGMAR, LOTRBiomes.ANGMAR_MOUNTAINS);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement angmar = build(data(LOTRItems.ANGMAR_BRICK.get(), "the_ruin_of_arnor", true, FrameType.TASK, true, true, false)
				.setTitle("The Ruin of Arnor").setDescription("Enter the Region of Angmar.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), ANGMAR_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement gun_orc_slayer = build(data(LOTRItems.ORC_BONE.get(), "angmar_orc_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Angmar Orc Slayer").setDescription("Kill an Angmar Orc.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.GUNDABAD_ORC.get()), LOTREntities.GUNDABAD_ORC_ARCHER.get())
				.requirements(IRequirementsStrategy.OR).parent(angmar));

		Advancement angmar_armor = build(data(ExtendedItems.ANGMAR_CHESTPLATE.get(), "forth_to_ruin", false, FrameType.TASK, true, true, false)
				.setTitle("Forth to Ruin").setDescription("Acquire a full set of Angmar armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "ranger_north", ExtendedItems.ANGMAR_HELMET.get(), ExtendedItems.ANGMAR_CHESTPLATE.get(), 
						ExtendedItems.ANGMAR_LEGGINGS.get(), ExtendedItems.ANGMAR_BOOTS.get()).parent(angmar));
	}

	@Override
	public String getFolder() {
		return "angmar";
	}

}
