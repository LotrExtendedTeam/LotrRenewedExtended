package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class DunlandAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> DUNLAND_BIOMES = ImmutableList.of(LOTRBiomes.DUNLAND);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement dunland = build(data(Items.STONE_SWORD, "land_of_the_wild_men", true, FrameType.TASK, true, true, false)
				.setTitle("Land of the Wild Men").setDescription("Enter the hills of Dunland.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), DUNLAND_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement dunlending_slayer = build(data(Items.BONE, "dunlending_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Slayer").setDescription("Kill a Dunlending.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.DUNLENDING_BOWMAN.get()), 
						LOTREntities.DUNLENDING.get()), LOTREntities.DUNLENDING_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(dunland));

		Advancement dunlending_armor = build(data(LOTRItems.DUNLENDING_CHESTPLATE.get(), "barbarian", false, FrameType.TASK, true, true, false)
				.setTitle("Barbarian").setDescription("Acquire a full set of Dunlending armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "dunlending", LOTRItems.DUNLENDING_HELMET.get(), LOTRItems.DUNLENDING_CHESTPLATE.get(), 
						LOTRItems.DUNLENDING_LEGGINGS.get(), LOTRItems.DUNLENDING_BOOTS.get()).parent(dunland));
	}

	@Override
	public String getFolder() {
		return "dunland";
	}

}
