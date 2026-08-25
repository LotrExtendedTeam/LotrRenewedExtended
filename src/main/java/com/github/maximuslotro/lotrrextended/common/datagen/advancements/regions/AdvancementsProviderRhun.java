package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;

public class AdvancementsProviderRhun extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement into_the_east = build(true, dataMain(Items.GRASS_BLOCK, "enter_rhun", FrameType.TASK)
				.setTitle("Into the East").setDescription("Enter the wide lands of Rhûn."),
				addBiomeTriggers(make(), LOTRBiomes.WILDERLAND));

		Advancement rhudel_10 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_10", FrameType.TASK)
				.setTitle("Easterling Bondsman").setDescription("Reach +10.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 10).parent(into_the_east));
		Advancement rhudel_50 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_50", FrameType.TASK)
				.setTitle("Easterling Levyman").setDescription("Reach +50.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 50).parent(rhudel_10));
		Advancement rhudel_100 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_100", FrameType.TASK)
				.setTitle("Easterling Clansman").setDescription("Reach +100.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 100).parent(rhudel_50));
		Advancement rhudel_200 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_200", FrameType.TASK)
				.setTitle("Easterling Warrior").setDescription("Reach +200.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 200).parent(rhudel_100));
		Advancement rhudel_500 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_500", FrameType.TASK)
				.setTitle("Easterling Champion").setDescription("Reach +500.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 500).parent(rhudel_200));
		Advancement rhudel_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_1000", FrameType.TASK)
				.setTitle("Golden Easterling").setDescription("Reach +1000.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 1000).parent(rhudel_500));
		Advancement rhudel_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_1500", FrameType.GOAL)
				.setTitle("Rhúdel Warlord").setDescription("Reach +1500.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 1500).parent(rhudel_1000));
		Advancement rhudel_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "rhudel_3000", FrameType.CHALLENGE)
				.setTitle("Rhúdel Chieftain").setDescription("Reach +3000.0 alignment with the Easterlings of Rhúdel."),
				addAlignmentTrigger(make(), FactionPointers.RHUDEL, 3000).parent(rhudel_1500));

		Advancement rhudel_crafter = build(true, data(ExtendedItems.RHUNIC_CRAFTING_TABLE.get(), "rhudel_crafter", FrameType.TASK)
			.setTitle("Rhûnic Crafter").setDescription("Craft an item on the Rhûnic crafting table."),
			addCraftingTrigger(make(), ExtendedBlocks.RHUNIC_CRAFTING_TABLE.get()).parent(into_the_east));

		Advancement rhudel_smithy = build(false, data(ExtendedItems.GILDED_IRON_INGOT.get(), "rhudel_smithy", FrameType.TASK)
			.setTitle("Easterling Goldsmith").setDescription("Smelt an ingot of Gilded Iron in the Alloy Forge."),
			addCraftingTrigger(make(), LOTRBlocks.ALLOY_FORGE.get(), ExtendedItems.GILDED_IRON_INGOT.get()).parent(into_the_east));
	}

	@Override
	public String getFolder() {
		return "rhun";
	}

	@Override
	public int getLegacyCount() {
		return 26;
	}

}
