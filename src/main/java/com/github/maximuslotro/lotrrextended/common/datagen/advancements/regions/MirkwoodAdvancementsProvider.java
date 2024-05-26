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
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class MirkwoodAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> MIRKWOOD_BIOMES = ImmutableList.of(LOTRBiomes.MIRKWOOD, LOTRBiomes.MIRKWOOD_MOUNTAINS, 
			LOTRBiomes.NORTHERN_MIRKWOOD, LOTRBiomes.WOODLAND_REALM);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement mirkwoodBase = build(true, data(LOTRItems.HANGING_WEB.get(), "stay_on_the_path", true, FrameType.TASK, true, true, false)
				.setTitle("Stay on the Path!").setDescription("Enter the forest of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), MIRKWOOD_BIOMES).requirements(IRequirementsStrategy.OR));

//		Advancement dol_guldur = build(data(ExtendedItems.DOL_GULDUR_BRICK.get(), "hill_of_sorcery", false, FrameType.TASK, true, true, false)
//				.setTitle("Hill of Sorcery").setDescription("Enter the region of Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
//				addBiomeTrigger(make(), LOTRBiomes.DOL_GULDUR).requirements(IRequirementsStrategy.OR).parent(mirkwoodBase));

		Advancement woodland_realm = build(true, data(Items.BOW, "guest_of_the_elvenking", false, FrameType.TASK, true, true, false)
		.setTitle("Guest of the Elvenking").setDescription("Enter the Woodland Realm.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.WOODLAND_REALM).requirements(IRequirementsStrategy.OR).parent(mirkwoodBase));

		Advancement wood_elf_slayer = build(true, data(LOTRItems.ELF_BONE.get(), "wood_elf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Wood-Elf Slayer").setDescription("Kill a Wood-Elf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.WOOD_ELF.get()), LOTREntities.WOOD_ELF_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(woodland_realm));

		Advancement ranger_armor = build(true, data(LOTRItems.WOOD_ELVEN_CHESTPLATE.get(), "captain_of_the_guard", false, FrameType.TASK, true, true, false)
				.setTitle("Captain of the Guard").setDescription("Acquire a full set of Wood-Elven armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "wood_elven", LOTRItems.WOOD_ELVEN_HELMET.get(), LOTRItems.WOOD_ELVEN_CHESTPLATE.get(), 
						LOTRItems.WOOD_ELVEN_LEGGINGS.get(), LOTRItems.WOOD_ELVEN_BOOTS.get()).parent(woodland_realm));
		
		Advancement arnor_armor = build(true, data(ExtendedItems.DOL_GULDUR_CHESTPLATE.get(), "the_corrupt", false, FrameType.TASK, true, true, false)
				.setTitle("The Corrupt").setDescription( "Acquire a full set of Dol Guldur armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "arnor", ExtendedItems.DOL_GULDUR_HELMET.get(), ExtendedItems.DOL_GULDUR_CHESTPLATE.get(), 
						ExtendedItems.DOL_GULDUR_LEGGINGS.get(), ExtendedItems.DOL_GULDUR_BOOTS.get()).parent(mirkwoodBase));

		Advancement dol_guldur_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_thrall", false, FrameType.TASK, true, true, false)
				.setTitle("Mirkwood Thrall").setDescription("Reach +10.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 10).parent(mirkwoodBase));

		Advancement dol_guldur_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_servant", false, FrameType.TASK, true, true, false)
				.setTitle("Mirkwood Servant").setDescription("Reach +50.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 50).parent(dol_guldur_10));

		Advancement dol_guldur_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_brigand", false, FrameType.TASK, true, true, false)
				.setTitle("Mirkwood Brigand").setDescription("Reach +100.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 100).parent(dol_guldur_50));

		Advancement dol_guldur_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_torchbearer", false, FrameType.TASK, true, true, false)
				.setTitle("Mirkwood Torchbearer").setDescription("Reach +200.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 200).parent(dol_guldur_100));

		Advancement dol_guldur_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_despoiler", false, FrameType.TASK, true, true, false)
				.setTitle("Mirkwood Despoiler").setDescription("Reach +500.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 500).parent(dol_guldur_200));

		Advancement dol_guldur_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_captain", false, FrameType.GOAL, true, true, false)
				.setTitle("Dol Guldur Captain").setDescription("Reach +1000.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 1000).parent(dol_guldur_500));

		Advancement dol_guldur_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "dol_guldur_lieutnant", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Khamûl's Lieutnant").setDescription("Reach +2000.0 alignment with Dol Guldur.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.DOL_GULDUR, 2000).parent(dol_guldur_1000));

		Advancement wood_elf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Woodland Guest").setDescription("Reach +50.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 50).parent(mirkwoodBase));

		Advancement wood_elf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Woodland Friend").setDescription("Reach +100.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 100).parent(wood_elf_50));

		Advancement wood_elf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_warden", false, FrameType.TASK, true, true, false)
				.setTitle("Woodland Marchwarden").setDescription("Reach +200.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 200).parent(wood_elf_100));

		Advancement wood_elf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_herald", false, FrameType.TASK, true, true, false)
				.setTitle("Woodland Herald").setDescription("Reach +500.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 500).parent(wood_elf_200));

		Advancement wood_elf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_captain", false, FrameType.TASK, true, true, false)
				.setTitle("Woodland Captain").setDescription("Reach +1000.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 1000).parent(wood_elf_500));

		Advancement wood_elf_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_arphen_eryn", false, FrameType.GOAL, true, true, false)
				.setTitle("Arphen Eryn").setDescription("Reach +2000.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 2000).parent(wood_elf_1000));

		Advancement wood_elf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "wood_elf_hir_hiril_eryn", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Hîr/Híril Eryn").setDescription("Reach +3000.0 alignment with the Wood-elves of Mirkwood.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.WOODLAND_REALM, 3000).parent(wood_elf_2000));

	}

	@Override
	public String getFolder() {
		return "mirkwood";
	}

}
