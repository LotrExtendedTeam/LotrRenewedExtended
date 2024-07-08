package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;


public class ShireAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> SHIRE_BIOMES = ImmutableList.of(LOTRBiomes.SHIRE, LOTRBiomes.SHIRE_MARSHES, 
			LOTRBiomes.SHIRE_MOORS, LOTRBiomes.SHIRE_WOODLANDS, LOTRBiomes.WHITE_DOWNS);

	@Override
	public void generateAdvancements() {
		Advancement shire = build(true, data(LOTRItems.SHIRE_HEATHER.get(), "a_simple_land", true, FrameType.TASK, true, true, false)
				.setTitle("A Simple Land").setDescription("Enter the Shire."), 
				addBiomeTriggers(make(), SHIRE_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement white_downs = build(true, data(LOTRItems.CHALK.get(), "chalken_delvings", false, FrameType.TASK, true, true, false)
		.setTitle("Chalken Delvings").setDescription("Enter the White Downs."), 
		addBiomeTrigger(make(), LOTRBiomes.WHITE_DOWNS).parent(shire));

		Advancement old_forest = build(true, data(Items.OAK_LOG, "unfriendly_trees", false, FrameType.TASK, true, true, false)
		.setTitle("Unfriendly Trees").setDescription("Enter the Old Forest."), 
		addBiomeTrigger(make(), LOTRBiomes.OLD_FOREST).parent(shire));

		Advancement hobbit_slayer = build(true, data(LOTRItems.HOBBIT_BONE.get(), "hobbit_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Slayer").setDescription("Kill a Hobbit."), 
				addKillEntityTrigger(make(), LOTREntities.HOBBIT.get()).requirements(IRequirementsStrategy.OR).parent(shire));

		Advancement clover = build(true, data(LOTRItems.FOUR_LEAF_CLOVER.get(), "feeling_lucky", false, FrameType.GOAL, true, true, false)
				.setTitle("Feeling Lucky?").setDescription("Find a rare Four-Leaf Clover."), 
				addItemTrigger(make(), LOTRItems.FOUR_LEAF_CLOVER.get()).parent(shire));

		Advancement hobbit_10 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Guest").setDescription("Reach +10.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 10).parent(shire));
		Advancement hobbit_100 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Friend").setDescription("Reach +100.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 100).parent(hobbit_10));
		Advancement hobbit_250 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_hayward", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Hayward").setDescription("Reach +250.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 250).parent(hobbit_100));
		Advancement hobbit_500 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_bounder", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Bounder").setDescription("Reach +500.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 500).parent(hobbit_250));
		Advancement hobbit_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_shirriff", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Shirriff").setDescription("Reach +1000.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 1000).parent(hobbit_500));
		Advancement hobbit_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_chief_shirriff", false, FrameType.GOAL, true, true, false)
				.setTitle("Chief Shirriff").setDescription("Reach +2000.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 2000).parent(hobbit_1000));
		Advancement hobbit_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "hobbit_thain", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Thain of the Shire").setDescription("Reach +3000.0 alignment with the Hobbits of the Shire."), 
				addAlignmentTrigger(make(), FactionPointers.HOBBITS, 3000).parent(hobbit_2000));

		Advancement hobbit_farmer_trade = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "treasures_of_imladris", false, FrameType.TASK, true, true, false)
				.setTitle("Treasures of Imladris").setDescription("Trade with a Rivendell Wanderer."), 
				addTradeEntityTrigger(make(), ExtendedEntities.RIVENDELL_WANDERER.get()).parent(shire));

		Advancement hobbit_bartender_trade = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "welcome_customer", false, FrameType.TASK, true, true, false)
				.setTitle("Welcome Customer").setDescription("Trade with a Hobbit Bartender."), 
				addTradeEntityTrigger(make(), ExtendedEntities.HOBBIT_BARTENDER.get()).parent(shire));

		Advancement hobbit_bartender_pipeweed = build(true, data(LOTRItems.PIPEWEED_LEAF.get(), "street_credit", false, FrameType.TASK, true, true, false)
				.setTitle("Street Credit").setDescription("Sell some Pipe-weed leaf to a Hobbit Bartender."), 
				addTradeTrigger(make(), ExtendedEntities.HOBBIT_BARTENDER.get(), false, LOTRItems.PIPEWEED_LEAF.get()).parent(shire));

		Advancement hobbit_farmer_potato = build(true, data(Items.POTATO, "boil_em", false, FrameType.TASK, true, true, false)
				.setTitle("Boil 'Em, Mash 'Em...").setDescription("Buy potatoes from a Hobbit Farmer."), 
				addTradeTrigger(make(), ExtendedEntities.HOBBIT_FARMER.get(), true, Items.POTATO).parent(shire));
	}

	@Override
	public String getFolder() {
		return "shire";
	}

}
