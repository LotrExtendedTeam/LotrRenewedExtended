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
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class MirkwoodAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> MIRKWOOD_BIOMES = ImmutableList.of(LOTRBiomes.MIRKWOOD, LOTRBiomes.MIRKWOOD_MOUNTAINS, 
			LOTRBiomes.NORTHERN_MIRKWOOD, LOTRBiomes.WOODLAND_REALM);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement mirkwoodBase = build(data(LOTRItems.HANGING_WEB.get(), "stay_on_the_path", true, FrameType.TASK, true, true, false)
				.setTitle("Stay on the Path!").setDescription("Enter the forest of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), MIRKWOOD_BIOMES).requirements(IRequirementsStrategy.OR));

//		Advancement dol_guldur = build(data(ExtendedItems.DOL_GULDUR_BRICK.get(), "hill_of_sorcery", false, FrameType.TASK, true, true, false)
//				.setTitle("Hill of Sorcery").setDescription("Enter the region of Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
//				addBiomeTrigger(make(), LOTRBiomes.DOL_GULDUR).requirements(IRequirementsStrategy.OR).parent(mirkwoodBase));

		Advancement woodland_realm = build(data(Items.BOW, "guest_of_the_elvenking", false, FrameType.TASK, true, true, false)
		.setTitle("Guest of the Elvenking").setDescription("Enter the Woodland Realm.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.WOODLAND_REALM).requirements(IRequirementsStrategy.OR).parent(mirkwoodBase));

		Advancement wood_elf_slayer = build(data(LOTRItems.ELF_BONE.get(), "wood_elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Wood-Elf Slayer").setDescription("Kill a Wood-Elf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.WOOD_ELF.get()), LOTREntities.WOOD_ELF_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(woodland_realm));

		Advancement ranger_armor = build(data(LOTRItems.WOOD_ELVEN_CHESTPLATE.get(), "captain_of_the_guard", false, FrameType.TASK, true, true, false)
				.setTitle("Captain of the Guard").setDescription("Acquire a full set of Wood-Elven armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "wood_elven", LOTRItems.WOOD_ELVEN_HELMET.get(), LOTRItems.WOOD_ELVEN_CHESTPLATE.get(), 
						LOTRItems.WOOD_ELVEN_LEGGINGS.get(), LOTRItems.WOOD_ELVEN_BOOTS.get()).parent(woodland_realm));
		
		Advancement arnor_armor = build(data(ExtendedItems.DOL_GULDUR_CHESTPLATE.get(), "the_corrupt", false, FrameType.TASK, true, true, false)
				.setTitle("The Corrupt").setDescription( "Acquire a full set of Dol Guldur armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "arnor", ExtendedItems.DOL_GULDUR_HELMET.get(), ExtendedItems.DOL_GULDUR_CHESTPLATE.get(), 
						ExtendedItems.DOL_GULDUR_LEGGINGS.get(), ExtendedItems.DOL_GULDUR_BOOTS.get()).parent(mirkwoodBase));
	}

	@Override
	public String getFolder() {
		return "mirkwood";
	}

}
