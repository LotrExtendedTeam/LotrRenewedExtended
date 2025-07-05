package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;


public class AdvancementsProviderLindon extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> LINDON_BIOMES = ImmutableList.of(LOTRBiomes.LINDON, LOTRBiomes.LINDON_WOODLANDS);

	@Override
	public void generateAdvancements() {

		Advancement lindon = build(true, dataMain(LOTRItems.HIGH_ELVEN_BRICK.get(), "the_last_fair_realm", FrameType.TASK)
				.setTitle("The Last Fair Realm").setDescription("Enter the Land of Lindon."),
				addBiomeTriggers(make(), LINDON_BIOMES));

		Advancement lindon_crafter = build(true, data(LOTRItems.LINDON_CRAFTING_TABLE.get(), "lindon_crafter", FrameType.TASK)
			.setTitle("Lindon Crafter").setDescription("Craft an item on the Lindon Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.LINDON_CRAFTING_TABLE.get()).parent(lindon));

		Advancement tower_hills = build(true, data(LOTRItems.CHALK_BRICK.get(), "emyn_beraid", FrameType.TASK)
				.setTitle("Emyn Beraid").setDescription("Enter the Tower Hills."),
				addBiomeTrigger(make(), LOTRBiomes.TOWER_HILLS).parent(lindon));

		Advancement lindon_elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "lindon_elf_slayer", FrameType.TASK)
				.setTitle("Lindon Elf Slayer").setDescription("Kill a Lindon Elf."),
				addKillEntityTriggers(make(), LOTREntities.LINDON_WARRIOR.get(), LOTREntities.LINDON_ELF.get()).parent(lindon));

		Advancement elven_ingot = build(true, data(LOTRItems.ELVEN_STEEL_INGOT.get(), "elven_smith", FrameType.TASK)
				.setTitle("Elven Smith").setDescription("Acquire an Elven Steel Ingot."),
				addCraftingTrigger(make(), LOTRBlocks.ELVEN_FORGE.get(), LOTRItems.ELVEN_STEEL_INGOT.get()).parent(lindon));

		Advancement lindon_armor = build(true, data(LOTRItems.LINDON_CHESTPLATE.get(), "a_shining_helm", FrameType.TASK)
		.setTitle("A Shining Helm").setDescription("Acquire a full set of Lindon armor."),
		addArmorTrigger(make(), "lindon", LOTRItems.LINDON_HELMET.get(), LOTRItems.LINDON_CHESTPLATE.get(),
				LOTRItems.LINDON_LEGGINGS.get(), LOTRItems.LINDON_BOOTS.get()).parent(elven_ingot));

		Advancement galvorn_ingot = build(false, data(ExtendedItems.GALVORN_INGOT.get(), "secrets_of_eol", FrameType.GOAL)
				.setTitle("Secrets of Eöl").setDescription("Find the secret recipe for the coveted Galvorn of the legendary smith Eöl."),
				addCraftingTrigger(make(), LOTRBlocks.ELVEN_FORGE.get(), ExtendedItems.GALVORN_INGOT.get()).parent(elven_ingot));

//		Advancement galvorn_armor = build(data(ExtendedItems.GALVORN_CHESTPLATE.get(), "dark_elf", FrameType.CHALLENGE)
//				.setTitle("Dark Elf").setDescription("Acquire a full set of Galvorn armor."),
//				addArmorTrigger(make(), "galvorn", ExtendedItems.GALVORN_HELMET.get(), ExtendedItems.GALVORN_CHESTPLATE.get(),
//						ExtendedItems.GALVORN_LEGGINGS.get(), ExtendedItems.GALVORN_BOOTS.get()).parent(galvorn_ingot));

//		Advancement rein_elven_ingot = build(data(LOTRItems.ELVEN_STEEL_INGOT.get(), "elven_smith", FrameType.GOAL)
//				.setTitle("Master Elven Smith").setDescription("Acquire a Reinforced Elven Steel Ingot."),
//				addItemTrigger(make(), LOTRItems.ELVEN_STEEL_INGOT.get()).parent(elven_ingot));

		Advancement elf_10 = build(true, data(LOTRItems.GOLD_RING.get(), "high_elf_guest", FrameType.TASK)
				.setTitle("High Elf Guest").setDescription("Reach +10.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 10).parent(lindon));

		Advancement elf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "high_elf_friend", FrameType.TASK)
				.setTitle("High Elf Friend").setDescription("Reach +50.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 50).parent(elf_10));

		Advancement elf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "mahtar", FrameType.TASK)
				.setTitle("Mahtar").setDescription("Reach +100.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 100).parent(elf_50));

		Advancement elf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "tercano", FrameType.TASK)
				.setTitle("Tercáno").setDescription("Reach +200.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 200).parent(elf_100));

		Advancement elf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "hesto", FrameType.TASK)
				.setTitle("Hesto").setDescription("Reach +500.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 500).parent(elf_200));

		Advancement elf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "arquen", FrameType.TASK)
				.setTitle("Arquen").setDescription("Reach +1000.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 1000).parent(elf_500));

		Advancement elf_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "cano_noldoro", FrameType.GOAL)
				.setTitle("Cáno Noldoro").setDescription("Reach +2000.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 2000).parent(elf_1000));

		Advancement elf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "heru_heri_noldoro", FrameType.CHALLENGE)
				.setTitle("Heru/Heri Noldoro").setDescription("Reach +3000.0 alignment with the High Elves."),
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 3000).parent(elf_2000));
	}

	@Override
	public String getFolder() {
		return "lindon";
	}

	@Override
	public int getLegacyCount() {
		return 19;
	}

}
