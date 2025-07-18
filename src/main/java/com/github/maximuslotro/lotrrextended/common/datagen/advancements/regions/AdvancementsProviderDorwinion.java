package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;

public class AdvancementsProviderDorwinion extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement enter_dorwinion = build(true, dataMain(LOTRItems.ALE.get(), "enter_dorwinion", FrameType.TASK)
				.setTitle("Elves and Vineyards").setDescription("Enter the land of Dorwinion."),
				addBiomeTriggers(make(), LOTRBiomes.DORWINION, LOTRBiomes.EMYN_WINION));

		Advancement chalk_land = build(true, data(LOTRItems.CHALK.get(), "chalk_land", FrameType.TASK)
				.setTitle("The Chalkstone Hills").setDescription("Enter the hills of Emyn Winion."),
				addBiomeTrigger(make(), LOTRBiomes.EMYN_WINION).parent(enter_dorwinion));

		Advancement dorwinion_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_10", FrameType.TASK)
				.setTitle("Vintner Guest").setDescription("Reach +10.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 10).parent(enter_dorwinion));
		Advancement dorwinion_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_50", FrameType.TASK)
				.setTitle("Vinehand").setDescription("Reach +50.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 50).parent(dorwinion_10));
		Advancement dorwinion_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_100", FrameType.TASK)
				.setTitle("Vintner Merchant").setDescription("Reach +100.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 100).parent(dorwinion_50));
		Advancement dorwinion_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_200", FrameType.TASK)
				.setTitle("Vintner Guard").setDescription("Reach +200.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 200).parent(dorwinion_100));
		Advancement dorwinion_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_500", FrameType.TASK)
				.setTitle("Vintner Captain").setDescription("Reach +500.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 500).parent(dorwinion_200));
		Advancement dorwinion_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_1000", FrameType.TASK)
				.setTitle("Vintner Master").setDescription("Reach +1000.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 1000).parent(dorwinion_500));
		Advancement dorwinion_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_1500", FrameType.GOAL)
				.setTitle("Chief Wine-taster").setDescription("Reach +1500.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 1500).parent(dorwinion_1000));
		Advancement dorwinion_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "dorwinion_3000", FrameType.CHALLENGE)
				.setTitle("Vintner Lord/Lady").setDescription("Reach +3000.0 alignment with Dorwinion."),
				addAlignmentTrigger(make(), FactionPointers.DORWINION, 3000).parent(dorwinion_1500));

		Advancement dorwinion_crafter = build(true, data(LOTRItems.DORWINION_CRAFTING_TABLE.get(), "dorwinion_crafter", FrameType.TASK)
			.setTitle("Dorwinion Crafter").setDescription("Craft an item on the Dorwinion Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.DORWINION_CRAFTING_TABLE.get()).parent(enter_dorwinion));

		Advancement dorwinion_armor = build(true, data(LOTRItems.DORWINION_CHESTPLATE.get(), "dorwinion_armor", FrameType.TASK)
			.setTitle("Vintner Guard").setDescription("Equip a full suit of Dorwinion armour."),
			addArmorTrigger(make(), "dorwinion", LOTRItems.DORWINION_HELMET.get(), LOTRItems.DORWINION_CHESTPLATE.get(),
					LOTRItems.DORWINION_LEGGINGS.get(), LOTRItems.DORWINION_BOOTS.get()).parent(enter_dorwinion));

		Advancement dorwinion_elven_armor = build(true, data(LOTRItems.DORWINION_ELVEN_CHESTPLATE.get(), "dorwinion_elven_armor", FrameType.TASK)
			.setTitle("Warrior of Bladorthin").setDescription("Equip a full suit of Dorwinion Elven armour."),
			addArmorTrigger(make(), "dorwinion_elven", LOTRItems.DORWINION_ELVEN_HELMET.get(), LOTRItems.DORWINION_ELVEN_CHESTPLATE.get(),
					LOTRItems.DORWINION_ELVEN_LEGGINGS.get(), LOTRItems.DORWINION_ELVEN_BOOTS.get()).parent(enter_dorwinion));

//		Advancement dorwinion_elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "dorwinion_elf_slayer", FrameType.TASK)
//			.setTitle("Dorwinion Elf Slayer").setDescription("Kill a Dorwinion Elf."),
//			addKillEntityTriggers(make(), LOTREntities.DORWINION_ELF.get()).parent(enter_dorwinion));

//		Advancement dorwinion_man_slayer = build(true, data(Items.BONE, "dorwinion_man_slayer", FrameType.TASK)
//			.setTitle("Dorwinion Man Slayer").setDescription("Kill a Dorwinion Man."),
//			addKillEntityTriggers(make(), LOTREntities.DORWINION_MAN.get()).parent(enter_dorwinion));
	}

	@Override
	public String getFolder() {
		return "dorwinion";
	}

	@Override
	public int getLegacyCount() {
		return 24;
	}

}
