package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

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
		Advancement tower_hills = build(data(LOTRItems.CHALK_BRICK.get(), "emyn_beraid", true, FrameType.TASK, true, true, false)
				.setTitle("Emyn Beraid").setDescription("Enter the Tower Hills.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTrigger(make(), LOTRBiomes.TOWER_HILLS));

		Advancement lindon = build(data(LOTRItems.HIGH_ELVEN_BRICK.get(), "the_last_fair_realm", true, FrameType.TASK, true, true, false)
				.setTitle("The Last Fair Realm").setDescription("Enter the Land of Lindon.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), LINDON_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement lindon_elf_slayer = build(data(LOTRItems.ELF_BONE.get(), "lindon_elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Lindon Elf Slayer").setDescription("Kill a Lindon Elf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.LINDON_WARRIOR.get()), LOTREntities.LINDON_ELF.get())
				.requirements(IRequirementsStrategy.OR).parent(lindon));

		Advancement elven_ingot = build(data(LOTRItems.ELVEN_STEEL_INGOT.get(), "elven_smith", false, FrameType.TASK, true, true, false)
				.setTitle("Elven Smith").setDescription("Acquire an Elven Steel Ingot.").setTitleStype(TextFormatting.GREEN), 
				addItemTrigger(make(), LOTRItems.ELVEN_STEEL_INGOT.get()).parent(lindon));

		Advancement lindon_armor = build(data(LOTRItems.LINDON_CHESTPLATE.get(), "a_shining_helm", false, FrameType.GOAL, true, true, false)
		.setTitle("A Shining Helm").setDescription("Acquire a full set of Lindon armor.").setTitleStype(TextFormatting.GREEN), 
		addArmorTrigger(make(), "lindon", LOTRItems.LINDON_HELMET.get(), LOTRItems.LINDON_CHESTPLATE.get(), 
				LOTRItems.LINDON_LEGGINGS.get(), LOTRItems.LINDON_BOOTS.get()).parent(elven_ingot));

		Advancement galvorn_ingot = build(data(ExtendedItems.GALVORN_INGOT.get(), "secrets_of_eol", false, FrameType.GOAL, true, true, false)
				.setTitle("Secrets of Eöl").setDescription("Find the secret recipe for the coveted Galvorn of the legendary smith Eöl.").setTitleStype(TextFormatting.GOLD), 
				addItemTrigger(make(), ExtendedItems.GALVORN_INGOT.get()).parent(elven_ingot));

//		Advancement galvorn_armor = build(data(ExtendedItems.GALVORN_CHESTPLATE.get(), "dark_elf", false, FrameType.CHALLENGE, true, true, false)
//				.setTitle("Dark Elf").setDescription("Acquire a full set of Galvorn armor.").setTitleStype(TextFormatting.DARK_PURPLE), 
//				addArmorTrigger(make(), "galvorn", ExtendedItems.GALVORN_HELMET.get(), ExtendedItems.GALVORN_CHESTPLATE.get(), 
//						ExtendedItems.GALVORN_LEGGINGS.get(), ExtendedItems.GALVORN_BOOTS.get()).parent(galvorn_ingot));

//		Advancement rein_elven_ingot = build(data(LOTRItems.ELVEN_STEEL_INGOT.get(), "elven_smith", false, FrameType.GOAL, true, true, false)
//				.setTitle("Master Elven Smith").setDescription("Acquire a Reinforced Elven Steel Ingot.").setTitleStype(TextFormatting.GOLD), 
//				addItemTrigger(make(), LOTRItems.ELVEN_STEEL_INGOT.get()).parent(elven_ingot));
	}

	@Override
	public String getFolder() {
		return "lindon";
	}

}
