package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
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


public class AdvancementsProviderShire extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> SHIRE_BIOMES = ImmutableList.of(LOTRBiomes.SHIRE, LOTRBiomes.SHIRE_MARSHES,
		LOTRBiomes.SHIRE_MOORS, LOTRBiomes.SHIRE_WOODLANDS, LOTRBiomes.WHITE_DOWNS);

	@Override
	public void generateAdvancements() {
		Advancement shire = build(true, dataMain(LOTRItems.SHIRE_HEATHER.get(), "a_simple_land", FrameType.TASK)
			.setTitle("A Simple Land").setDescription("Enter the Shire."),
			addBiomeTriggers(make(), SHIRE_BIOMES));

		Advancement white_downs = build(true, data(LOTRItems.CHALK.get(), "chalken_delvings", FrameType.TASK)
			.setTitle("Chalken Delvings").setDescription("Enter the White Downs."),
			addBiomeTrigger(make(), LOTRBiomes.WHITE_DOWNS).parent(shire));

		Advancement hobbit_crafter = build(true, data(LOTRItems.HOBBIT_CRAFTING_TABLE.get(), "hobbit_crafter", FrameType.TASK)
			.setTitle("Hobbit Crafter").setDescription("Craft an item on the Hobbit Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.HOBBIT_CRAFTING_TABLE.get()).parent(shire));

		Advancement old_forest = build(true, data(Items.OAK_LOG, "unfriendly_trees", FrameType.TASK)
			.setTitle("Unfriendly Trees").setDescription("Enter the Old Forest."),
			addBiomeTrigger(make(), LOTRBiomes.OLD_FOREST).parent(shire));

		Advancement hobbit_slayer = build(true, data(LOTRItems.HOBBIT_BONE.get(), "hobbit_slayer", FrameType.TASK)
			.setTitle("Hobbit Slayer").setDescription("Kill a Hobbit."),
			addKillEntityTrigger(make(), LOTREntities.HOBBIT.get()).parent(shire));

		Advancement clover = build(true, data(LOTRItems.FOUR_LEAF_CLOVER.get(), "feeling_lucky", FrameType.TASK)
			.setTitle("Feeling Lucky?").setDescription("Find a rare Four-Leaf Clover."),
			addItemTrigger(make(), LOTRItems.FOUR_LEAF_CLOVER.get()).parent(shire));

		Advancement hobbit_10 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_guest", FrameType.TASK)
			.setTitle("Hobbit Guest").setDescription("Reach +10.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 10).parent(shire));
		Advancement hobbit_100 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_friend", FrameType.TASK)
			.setTitle("Hobbit Friend").setDescription("Reach +100.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 100).parent(hobbit_10));
		Advancement hobbit_250 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_hayward", FrameType.TASK)
			.setTitle("Hobbit Hayward").setDescription("Reach +250.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 250).parent(hobbit_100));
		Advancement hobbit_500 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_bounder", FrameType.TASK)
			.setTitle("Hobbit Bounder").setDescription("Reach +500.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 500).parent(hobbit_250));
		Advancement hobbit_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_shirriff", FrameType.TASK)
			.setTitle("Hobbit Shirriff").setDescription("Reach +1000.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 1000).parent(hobbit_500));
		Advancement hobbit_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_chief_shirriff", FrameType.GOAL)
			.setTitle("Chief Shirriff").setDescription("Reach +2000.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 2000).parent(hobbit_1000));
		Advancement hobbit_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_thain", FrameType.CHALLENGE)
			.setTitle("Thain of the Shire").setDescription("Reach +3000.0 alignment with the Hobbits of the Shire."),
			addAlignmentTrigger(make(), FactionPointers.HOBBITS, 3000).parent(hobbit_2000));

		Advancement hobbit_farmer_trade = build(false, data(ExtendedItems.SILVER_COIN_ONE.get(), "hobbit_farmer_trade", FrameType.TASK)
			.setTitle("Farmer Maggot's Cousin").setDescription("Trade with a Hobbit Farmer."),
			addTradeEntityTrigger(make(), ExtendedEntities.HOBBIT_FARMER.get()).parent(shire));

		Advancement hobbit_farmer_potato = build(true, data(Items.POTATO, "boil_em", FrameType.GOAL)
			.setTitle("Boil 'Em, Mash 'Em...").setDescription("Buy potatoes from a Hobbit Farmer."),
			addTradeEntityTrigger(make(), ExtendedEntities.HOBBIT_FARMER.get(), true, Items.POTATO).parent(hobbit_farmer_trade));

		Advancement hired_hands = build(true, data(Items.IRON_HOE, "hired_hands", FrameType.TASK)
			.setTitle("Hired Hands").setDescription("Hire a farmhand from a Hobbit Farmer."),
			addUnitHireTrigger(make(), ExtendedEntities.HOBBIT_FARMER.get()).parent(hobbit_farmer_trade));

		Advancement hobbit_bartender_trade = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "welcome_customer", FrameType.TASK)
			.setTitle("Welcome Customer").setDescription("Trade with a Hobbit Bartender."),
			addTradeEntityTrigger(make(), ExtendedEntities.HOBBIT_BARTENDER.get()).parent(shire));

		Advancement hobbit_bartender_pipeweed = build(true, data(LOTRItems.PIPEWEED_LEAF.get(), "street_credit", FrameType.GOAL)
			.setTitle("Street Credit").setDescription("Sell some Pipe-weed leaf to a Hobbit Bartender."),
			addTradeEntityTrigger(make(), ExtendedEntities.HOBBIT_BARTENDER.get(), false, LOTRItems.PIPEWEED_LEAF.get()).parent(hobbit_bartender_trade));
	}

	@Override
	public String getFolder() {
		return "shire";
	}

	@Override
	public int getLegacyCount() {
		return 25;
	}

}
