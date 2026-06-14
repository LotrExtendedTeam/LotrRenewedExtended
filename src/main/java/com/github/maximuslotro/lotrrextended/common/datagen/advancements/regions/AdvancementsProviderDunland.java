package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderDunland extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> DUNLAND_BIOMES = ImmutableList.of(LOTRBiomes.DUNLAND);

	@Override
	public void generateAdvancements() {
		Advancement dunland = build(true, dataMain(Items.STONE_SWORD, "land_of_the_wild_men", FrameType.TASK)
				.setTitle("Land of the Wild Men").setDescription("Enter the hills of Dunland."),
				addBiomeTriggers(make(), DUNLAND_BIOMES));

		Advancement dunlending_slayer = build(true, data(Items.BONE, "dunlending_slayer", FrameType.TASK)
				.setTitle("Dunlending Slayer").setDescription("Kill a Dunlending."),
				addKillEntityTriggers(make(), LOTREntities.DUNLENDING_BOWMAN.get(), LOTREntities.DUNLENDING.get(),
						LOTREntities.DUNLENDING_WARRIOR.get(), ExtendedEntities.DUNLENDING_WARLORD.get()).parent(dunland));

		Advancement dunlending_armor = build(true, data(LOTRItems.DUNLENDING_CHESTPLATE.get(), "barbarian", FrameType.TASK)
				.setTitle("Barbarian").setDescription("Acquire a full set of Dunlending armor."),
				addArmorTrigger(make(), "dunlending", LOTRItems.DUNLENDING_HELMET.get(), LOTRItems.DUNLENDING_CHESTPLATE.get(),
						LOTRItems.DUNLENDING_LEGGINGS.get(), LOTRItems.DUNLENDING_BOOTS.get()).parent(dunland));

		Advancement dunland_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_guest", FrameType.TASK)
				.setTitle("Dunlending Guest").setDescription("Reach +10.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 10).parent(dunland));

		Advancement dunland_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_kinsman", FrameType.TASK)
				.setTitle("Dunlending Kinsman").setDescription("Reach +50.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 50).parent(dunland_10));

		Advancement dunland_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_warrior", FrameType.TASK)
				.setTitle("Dunlending Warrior").setDescription("Reach +100.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 100).parent(dunland_50));

		Advancement dunland_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_club_bearer", FrameType.TASK)
				.setTitle("Dunlending Club-bearer").setDescription("Reach +200.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 200).parent(dunland_100));

		Advancement dunland_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_avenger", FrameType.TASK)
				.setTitle("Dunlending Avenger").setDescription("Reach +500.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 500).parent(dunland_200));

		Advancement dunland_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_warlord", FrameType.GOAL)
				.setTitle("Dunlending Warlord").setDescription("Reach +1000.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 1000).parent(dunland_500));

		Advancement dunland_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "dunland_chieftain", FrameType.CHALLENGE)
				.setTitle("Dunlending Chieftain").setDescription("Reach +2000.0 alignment with the Dunlendings."),
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 2000).parent(dunland_1000));

		Advancement dunland_crafter = build(true, data(LOTRItems.DUNLENDING_CRAFTING_TABLE.get(), "dunland_crafter", FrameType.TASK)
			.setTitle("Dunlending Crafter").setDescription("Craft an item on the Dunlending Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.DUNLENDING_CRAFTING_TABLE.get()).parent(dunland));

		Advancement we_fight = build(true, data(LOTRItems.DUNLENDING_CLUB.get(), "we_fight", FrameType.TASK)
				.setTitle("We Fight for You!").setDescription("Hire a unit from a Dunlending Warlord."),
				addUnitHireTrigger(make(), ExtendedEntities.DUNLENDING_WARLORD.get()).parent(dunland));
	}

	@Override
	public String getFolder() {
		return "dunland";
	}

	@Override
	public int getLegacyCount() {
		return 15;
	}

}
