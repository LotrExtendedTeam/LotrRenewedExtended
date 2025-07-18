package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderDale extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> DALE_BIOMES = ImmutableList.of(LOTRBiomes.DALE, LOTRBiomes.NORTHERN_DALE);

	@Override
	public void generateAdvancements() {
		Advancement dale = build(true, dataMain(LOTRItems.DALE_PAVING.get(), "kingdom_of_the_northmen", FrameType.TASK)
				.setTitle("Kingdom of the Northmen").setDescription("Enter the land of Dale."),
				addBiomeTriggers(make(), DALE_BIOMES));

		Advancement dale_slayer = build(true, data(Items.BONE, "dale_slayer", FrameType.TASK)
				.setTitle("Dalishman Slayer").setDescription("Kill a Man of Dale."),
				addKillEntityTriggers(make(), LOTREntities.DALE_MAN.get(), LOTREntities.DALE_BOWMAN.get(), LOTREntities.DALE_SOLDIER.get())
				.parent(dale));

		Advancement dale_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_guest", FrameType.TASK)
				.setTitle("Barding Guest").setDescription("Reach +10.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 10).parent(dale));
		Advancement dale_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_friend", FrameType.TASK)
				.setTitle("Barding Friend").setDescription("Reach +50.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 50).parent(dale_10));
		Advancement dale_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_soldier", FrameType.TASK)
				.setTitle("Dalish Soldier").setDescription("Reach +100.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 100).parent(dale_50));
		Advancement dale_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_herald", FrameType.TASK)
				.setTitle("Dalish Herald").setDescription("Reach +200.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 200).parent(dale_100));
		Advancement dale_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_captain", FrameType.TASK)
				.setTitle("Dalish Captain").setDescription("Reach +500.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 500).parent(dale_200));
		Advancement dale_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_marchal", FrameType.GOAL)
				.setTitle("Dalish Marshal").setDescription("Reach +1000.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 1000).parent(dale_500));
		Advancement dale_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_noble", FrameType.CHALLENGE)
				.setTitle("Dalish Lord/Lady").setDescription("Reach +2000.0 alignment with Dale."),
				addAlignmentTrigger(make(), FactionPointers.DALE, 2000).parent(dale_1000));

		Advancement dalish_crafter = build(true, data(LOTRItems.DALE_CRAFTING_TABLE.get(), "dalish_crafter", FrameType.TASK)
			.setTitle("Dalish Crafter").setDescription("Craft an item on the Dalish Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.DALE_CRAFTING_TABLE.get()).parent(dale));

		Advancement dale_armor = build(true, data(LOTRItems.DALE_CHESTPLATE.get(), "dale_armor", FrameType.TASK)
			.setTitle("For the North!").setDescription("Equip a full suit of Dalish armour."),
			addArmorTrigger(make(), "dale", LOTRItems.DALE_HELMET.get(), LOTRItems.DALE_CHESTPLATE.get(),
					LOTRItems.DALE_LEGGINGS.get(), LOTRItems.DALE_BOOTS.get()).parent(dale));
	}

	@Override
	public String getFolder() {
		return "dale";
	}

	@Override
	public int getLegacyCount() {
		return 18;
	}

}
