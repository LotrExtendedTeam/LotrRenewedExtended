package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.util.text.TextFormatting;


public class LindonAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> LINDON_BIOMES = ImmutableList.of(LOTRBiomes.LINDON, LOTRBiomes.LINDON_WOODLANDS);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement tower_hills = build(true, data(LOTRItems.CHALK_BRICK.get(), "emyn_beraid", true, FrameType.TASK, true, true, false)
				.setTitle("Emyn Beraid").setDescription("Enter the Tower Hills.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.TOWER_HILLS));

		Advancement lindon = build(true, data(LOTRItems.HIGH_ELVEN_BRICK.get(), "the_last_fair_realm", true, FrameType.TASK, true, true, false)
				.setTitle("The Last Fair Realm").setDescription("Enter the Land of Lindon.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), LINDON_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement lindon_elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "lindon_elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Lindon Elf Slayer").setDescription("Kill a Lindon Elf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.LINDON_WARRIOR.get()), LOTREntities.LINDON_ELF.get())
				.requirements(IRequirementsStrategy.OR).parent(lindon));

		Advancement elven_ingot = build(true, data(LOTRItems.ELVEN_STEEL_INGOT.get(), "elven_smith", false, FrameType.TASK, true, true, false)
				.setTitle("Elven Smith").setDescription("Acquire an Elven Steel Ingot.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.ELVEN_STEEL_INGOT.get()).parent(lindon));

		Advancement lindon_armor = build(true, data(LOTRItems.LINDON_CHESTPLATE.get(), "a_shining_helm", false, FrameType.TASK, true, true, false)
		.setTitle("A Shining Helm").setDescription("Acquire a full set of Lindon armor.").setTitleStype(TextFormatting.GREEN), 
		addArmorTrigger(make(), "lindon", LOTRItems.LINDON_HELMET.get(), LOTRItems.LINDON_CHESTPLATE.get(), 
				LOTRItems.LINDON_LEGGINGS.get(), LOTRItems.LINDON_BOOTS.get()).parent(elven_ingot));

		Advancement galvorn_ingot = build(false, data(ExtendedItems.GALVORN_INGOT.get(), "secrets_of_eol", false, FrameType.GOAL, true, true, false)
				.setTitle("Secrets of Eöl").setDescription("Find the secret recipe for the coveted Galvorn of the legendary smith Eöl.").setTitleStype(TextFormatting.GOLD), 
				addItemTrigger(make(), ExtendedItems.GALVORN_INGOT.get()).parent(elven_ingot));

//		Advancement galvorn_armor = build(data(ExtendedItems.GALVORN_CHESTPLATE.get(), "dark_elf", false, FrameType.CHALLENGE, true, true, false)
//				.setTitle("Dark Elf").setDescription("Acquire a full set of Galvorn armor.").setTitleStype(TextFormatting.DARK_PURPLE), 
//				addArmorTrigger(make(), "galvorn", ExtendedItems.GALVORN_HELMET.get(), ExtendedItems.GALVORN_CHESTPLATE.get(), 
//						ExtendedItems.GALVORN_LEGGINGS.get(), ExtendedItems.GALVORN_BOOTS.get()).parent(galvorn_ingot));

//		Advancement rein_elven_ingot = build(data(LOTRItems.ELVEN_STEEL_INGOT.get(), "elven_smith", false, FrameType.GOAL, true, true, false)
//				.setTitle("Master Elven Smith").setDescription("Acquire a Reinforced Elven Steel Ingot.").setTitleStype(TextFormatting.GOLD), 
//				addItemTrigger(make(), LOTRItems.ELVEN_STEEL_INGOT.get()).parent(elven_ingot));

		Advancement elf_10 = build(true, data(LOTRItems.GOLD_RING.get(), "high_elf_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Mountains Guest").setDescription("Reach +10.0 alignment with the High Elves.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 10).parent(lindon));

		Advancement elf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "high_elf_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Mountains Friend").setDescription("Reach +50.0 alignment with the High Elves.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 50).parent(elf_10));

		Advancement elf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "mahtar", false, FrameType.TASK, true, true, false)
				.setTitle("Mahtar").setDescription("Reach +100.0 alignment with the High Elves.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 100).parent(elf_50));

		Advancement elf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "tercano", false, FrameType.TASK, true, true, false)
				.setTitle("Tercáno").setDescription("Reach +200.0 alignment with the High Elves.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 200).parent(elf_100));

		Advancement elf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "hesto", false, FrameType.TASK, true, true, false)
				.setTitle("Hesto").setDescription("Reach +500.0 alignment with the High Elves.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 500).parent(elf_200));

		Advancement elf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "arquen", false, FrameType.TASK, true, true, false)
				.setTitle("Arquen").setDescription("Reach +1000.0 alignment with the High Elves.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 1000).parent(elf_500));

		Advancement elf_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "cano_noldoro", false, FrameType.GOAL, true, true, false)
				.setTitle("Cáno Noldoro").setDescription("Reach +2000.0 alignment with the High Elves.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 2000).parent(elf_1000));

		Advancement elf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "heru_heri_noldoro", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Heru/Heri Noldoro").setDescription("Reach +3000.0 alignment with the High Elves.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.HIGH_ELVES, 3000).parent(elf_2000));
	}

	@Override
	public String getFolder() {
		return "lindon";
	}

}
