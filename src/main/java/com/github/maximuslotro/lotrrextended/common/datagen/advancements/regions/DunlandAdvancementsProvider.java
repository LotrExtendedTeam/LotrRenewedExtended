package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
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
		Advancement dunland = build(true, data(Items.STONE_SWORD, "land_of_the_wild_men", true, FrameType.TASK, true, true, false)
				.setTitle("Land of the Wild Men").setDescription("Enter the hills of Dunland.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), DUNLAND_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement dunlending_slayer = build(true, data(Items.BONE, "dunlending_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Slayer").setDescription("Kill a Dunlending.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.DUNLENDING_BOWMAN.get()), 
						LOTREntities.DUNLENDING.get()), LOTREntities.DUNLENDING_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(dunland));

		Advancement dunlending_armor = build(true, data(LOTRItems.DUNLENDING_CHESTPLATE.get(), "barbarian", false, FrameType.TASK, true, true, false)
				.setTitle("Barbarian").setDescription("Acquire a full set of Dunlending armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "dunlending", LOTRItems.DUNLENDING_HELMET.get(), LOTRItems.DUNLENDING_CHESTPLATE.get(), 
						LOTRItems.DUNLENDING_LEGGINGS.get(), LOTRItems.DUNLENDING_BOOTS.get()).parent(dunland));

		Advancement dunland_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Guest").setDescription("Reach +10.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 10).parent(dunland));

		Advancement dunland_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_kinsman", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Kinsman").setDescription("Reach +50.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 50).parent(dunland_10));

		Advancement dunland_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_warrior", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Warrior").setDescription("Reach +100.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 100).parent(dunland_50));

		Advancement dunland_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_club_bearer", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Club-bearer").setDescription("Reach +200.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 200).parent(dunland_100));

		Advancement dunland_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_avenger", false, FrameType.TASK, true, true, false)
				.setTitle("Dunlending Avenger").setDescription("Reach +500.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 500).parent(dunland_200));

		Advancement dunland_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_warlord", false, FrameType.GOAL, true, true, false)
				.setTitle("Dunlending Warlord").setDescription("Reach +1000.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 1000).parent(dunland_500));

		Advancement dunland_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_chieftain", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Dunlending Chieftain").setDescription("Reach +2000.0 alignment with the Dunlendings.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 2000).parent(dunland_1000));
	}

	@Override
	public String getFolder() {
		return "dunland";
	}

}
