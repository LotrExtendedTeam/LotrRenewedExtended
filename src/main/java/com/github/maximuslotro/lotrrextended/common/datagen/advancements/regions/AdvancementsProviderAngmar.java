package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;


public class AdvancementsProviderAngmar extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> ANGMAR_BIOMES = ImmutableList.of(LOTRBiomes.ANGMAR, LOTRBiomes.ANGMAR_MOUNTAINS);

	@Override
	public void generateAdvancements() {
		Advancement angmar = build(true, dataMain(LOTRItems.ANGMAR_BRICK.get(), "the_ruin_of_arnor", FrameType.TASK)
				.setTitle("The Ruin of Arnor").setDescription("Enter the Region of Angmar."),
				addBiomeTriggers(make(), ANGMAR_BIOMES));

//		Advancement gun_orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "angmar_orc_slayer", FrameType.TASK)
//				.setTitle("Angmar Orc Slayer").setDescription("Kill an Angmar Orc."),
//				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.GUNDABAD_ORC.get()), LOTREntities.GUNDABAD_ORC_ARCHER.get())
//				.requirements(IRequirementsStrategy.OR).parent(angmar));

		Advancement angmar_armor = build(true, data(ExtendedItems.ANGMAR_CHESTPLATE.get(), "forth_to_ruin", FrameType.TASK)
				.setTitle("Forth to Ruin").setDescription("Acquire a full set of Angmar armor."),
				addArmorTrigger(make(), "ranger_north", ExtendedItems.ANGMAR_HELMET.get(), ExtendedItems.ANGMAR_CHESTPLATE.get(),
						ExtendedItems.ANGMAR_LEGGINGS.get(), ExtendedItems.ANGMAR_BOOTS.get()).parent(angmar));

		Advancement morgul_vassal_armor = build(true, data(ExtendedItems.MORGUL_VASSAL_CHESTPLATE.get(), "morgul_vassal_armor", FrameType.TASK)
			.setTitle("Lord of the Fallen Realm").setDescription("Equip a full suit of Morgul Vassal Armor."),
			addArmorTrigger(make(), "morgul_vassal", ExtendedItems.MORGUL_VASSAL_HELMET.get(), ExtendedItems.MORGUL_VASSAL_CHESTPLATE.get(),
				ExtendedItems.MORGUL_VASSAL_LEGGINGS.get(), ExtendedItems.MORGUL_VASSAL_BOOTS.get()).parent(angmar_armor));

		Advancement orc_10 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_thrall", FrameType.TASK)
				.setTitle("Angmar Thrall").setDescription("Reach +10.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 10).parent(angmar));

		Advancement orc_50 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_snaga", FrameType.TASK)
				.setTitle("Angmar Servant").setDescription("Reach +50.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 50).parent(orc_10));

		Advancement orc_100 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_raider", FrameType.TASK)
				.setTitle("Angmar Kinsman").setDescription("Reach +100.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 100).parent(orc_50));

		Advancement orc_200 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_ravager", FrameType.TASK)
				.setTitle("Angmar Warrior").setDescription("Reach +200.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 200).parent(orc_100));

		Advancement orc_500 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_scourge", FrameType.TASK)
				.setTitle("Angmar Champion").setDescription("Reach +500.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 500).parent(orc_200));

		Advancement orc_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_warlord", FrameType.GOAL)
				.setTitle("Angmar Warlord").setDescription("Reach +1000.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 1000).parent(orc_500));

		Advancement orc_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "angmar_chieftain", FrameType.CHALLENGE)
				.setTitle("Angmar Chieftain").setDescription("Reach +2000.0 alignment with Angmar."),
				addAlignmentTrigger(make(), FactionPointers.ANGMAR, 2000).parent(orc_1000));

		Advancement angmar_crafter = build(true, data(LOTRItems.ANGMAR_CRAFTING_TABLE.get(), "angmar_crafter", FrameType.TASK)
			.setTitle("Angmarin Crafter").setDescription("Craft an item on the Angmar Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.ANGMAR_CRAFTING_TABLE.get()).parent(angmar));

	}

	@Override
	public String getFolder() {
		return "angmar";
	}

	@Override
	public int getLegacyCount() {
		return 17;
	}

}
