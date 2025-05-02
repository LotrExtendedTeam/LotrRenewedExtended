package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedBiomes;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;

public class BreeAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> BREE_BIOMES = ImmutableList.of(LOTRBiomes.BREELAND, LOTRBiomes.CHETWOOD);

	@Override
	public void generateAdvancements() {
		Advancement bree = build(true, data(LOTRItems.DRYSTONE.get(), "of_hobbits_and_men", true, FrameType.TASK, true, true, false)
				.setTitle("Of Hobbits and Men").setDescription("Enter the Region of Bree-land."),
				addBiomeTriggers(make(), BREE_BIOMES));

		Advancement chetwood = build(true, data(Items.OAK_SAPLING, "ruffians_haunt", false, FrameType.TASK, true, true, false)
		.setTitle("Ruffians' Haunt").setDescription("Enter the forest of Chetwood."),
		addBiomeTrigger(make(), LOTRBiomes.CHETWOOD).parent(bree));

		Advancement bree_man_slayer = build(true, data(Items.BONE, "bree_man_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-Man Slayer").setDescription("Kill a Bree-Man."),
				addKillEntityTriggers(make(), LOTREntities.BREE_GUARD.get(), 
					ExtendedEntities.BREE_BAKER.get(), ExtendedEntities.BREE_BREWER.get(), ExtendedEntities.BREE_BUTCHER.get(), 
					ExtendedEntities.BREE_CHEESEMONGER.get(), ExtendedEntities.BREE_FARMER.get(), ExtendedEntities.BREE_FLORIST.get(), 
					ExtendedEntities.BREE_INNKEEPER.get(), ExtendedEntities.BREE_LEATHERWORKER.get(), ExtendedEntities.BREE_LUMBERMAN.get(), 
					ExtendedEntities.BREE_POTTER.get(), ExtendedEntities.BREE_MERCHANT.get(), ExtendedEntities.BREE_SHERIFF.get(), 
					ExtendedEntities.BREE_SMITH.get(), ExtendedEntities.BREE_STONEMASON.get(), LOTREntities.BREE_MAN.get()).parent(bree));

		Advancement bree_hobbit_slayer = build(true, data(LOTRItems.HOBBIT_BONE.get(), "bree_hobbit_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-Hobbit Slayer").setDescription("Kill a Bree-Hobbit."),
				addKillEntityTrigger(make(), LOTREntities.BREE_HOBBIT.get()).parent(bree));

		Advancement bree_10 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Guest").setDescription("Reach +10.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 10).parent(bree));

		Advancement bree_50 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Friend").setDescription("Reach +50.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 50).parent(bree_10));

		Advancement bree_100 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_townsman", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Townsman").setDescription("Reach +100.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 100).parent(bree_50));

		Advancement bree_200 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_trustee", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Trustee").setDescription("Reach +200.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 200).parent(bree_100));

		Advancement bree_500 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_champion", false, FrameType.TASK, true, true, false)
				.setTitle("Champion of Bree").setDescription("Reach +500.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 500).parent(bree_200));

		Advancement bree_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_captain", false, FrameType.GOAL, true, true, false)
				.setTitle("Captain of Bree").setDescription("Reach +1000.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 1000).parent(bree_500));

		Advancement bree_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_master", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Master of Bree").setDescription("Reach +2000.0 alignment with Bree-land."),
				addAlignmentTrigger(make(), FactionPointers.BREE, 2000).parent(bree_1000));

		Advancement one_for_bill_ferny = build(true, data(Items.APPLE, "one_for_bill_ferny", false, FrameType.TASK, true, true, false)
			.setTitle("One for Bill Ferny").setDescription("Buy an apple from a Bree-land Farmer."),
			addTradeTrigger(make(), ExtendedEntities.BREE_FARMER.get(), true, Items.APPLE, LOTRItems.GREEN_APPLE.get()).parent(bree));

		Advancement barlimans_best = build(true, data(LOTRItems.WOODEN_MUG.get(), "barlimans_best", false, FrameType.TASK, true, true, false)
			.setTitle("Barliman's Best").setDescription("Trade with a Bree-land Innkeeper."),
			addTradeEntityTrigger(make(), ExtendedEntities.BREE_INNKEEPER.get()).parent(bree));

		Advancement arms_for_the_road = build(true, data(Items.IRON_SWORD, "arms_for_the_road", false, FrameType.TASK, true, true, false)
			.setTitle("Arms for the Road").setDescription("Trade with a Bree-land Smithy."),
			addTradeEntityTrigger(make(), ExtendedEntities.BREE_SMITH.get()).parent(bree));

		Advancement greenway_watch = build(true, data(Items.IRON_SWORD, "greenway_watch", false, FrameType.TASK, true, true, false)
			.setTitle("The Greenway Watch").setDescription("Hire a unit from a Bree-land Sheriff."),
			addUnitHireTrigger(make(), ExtendedEntities.BREE_SHERIFF.get()).parent(bree));

		Advancement hills_of_the_dead = build(true, data(Items.BONE, "hills_of_the_dead", false, FrameType.TASK, true, true, false)
			.setTitle("The Hills of the Dead").setDescription("Enter the Barrow-downs."),
			addBiomeTriggers(make(), ExtendedBiomes.BARROW_DOWNS, ExtendedBiomes.BARROW_DOWNS_RIVER).parent(bree));
		
		Advancement wight = build(true, data(Items.BONE, "barrow_wight_slayer", false, FrameType.TASK, true, true, false)
			.setTitle("Cold be Hand...").setDescription("Kill a Barrow-wight."),
			addKillEntityTrigger(make(), ExtendedEntities.BARROW_WIGHT.get()).parent(hills_of_the_dead));
	}

	@Override
	public String getFolder() {
		return "bree";
	}

}
