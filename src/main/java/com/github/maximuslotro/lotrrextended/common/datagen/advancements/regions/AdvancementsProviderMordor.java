package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;

public class AdvancementsProviderMordor extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement enter_mordor = build(true, dataMain(LOTRItems.MORDOR_ROCK.get(), "enter_mordor", FrameType.TASK)
				.setTitle("One Does Not Simply...").setDescription("Enter the land of Mordor."),
				addBiomeTriggers(make(), LOTRBiomes.MORDOR, LOTRBiomes.MORDOR_MOUNTAINS, LOTRBiomes.MORGUL_VALE, LOTRBiomes.NURN, LOTRBiomes.NURNEN, 
					LOTRBiomes.NURN_MARSHES));

		Advancement slavers = build(true, data(LOTRItems.MORDOR_HOE.get(), "slavers", FrameType.TASK)
				.setTitle("Slaves and Slavers").setDescription("Enter the region of Núrn."),
				addBiomeTriggers(make(), LOTRBiomes.NURN, LOTRBiomes.NURNEN,  LOTRBiomes.NURN_MARSHES).parent(enter_mordor));

		Advancement sickly_moon = build(true, data(LOTRItems.MORGUL_FLOWER.get(), "sickly_moon", FrameType.TASK)
			.setTitle("The Sickly Moon").setDescription("Enter the Morgul Vale."),
			addBiomeTriggers(make(), LOTRBiomes.MORGUL_VALE).parent(enter_mordor));

		Advancement mordor_10 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_10", FrameType.TASK)
				.setTitle("Mordor Thrall").setDescription("Reach +10.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 10).parent(enter_mordor));
		Advancement mordor_50 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_50", FrameType.TASK)
				.setTitle("Mordor Snaga").setDescription("Reach +50.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 50).parent(mordor_10));
		Advancement mordor_100 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_100", FrameType.TASK)
				.setTitle("Mordor Brigand").setDescription("Reach +100.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 100).parent(mordor_50));
		Advancement mordor_200 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_200", FrameType.TASK)
				.setTitle("Mordor Slave-driver").setDescription("Reach +200.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 200).parent(mordor_100));
		Advancement mordor_500 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_500", FrameType.TASK)
				.setTitle("Mordor Despoiler").setDescription("Reach +500.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 500).parent(mordor_200));
		Advancement mordor_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_1000", FrameType.TASK)
				.setTitle("Mordor Captain").setDescription("Reach +1000.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 1000).parent(mordor_500));
		Advancement mordor_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_1500", FrameType.GOAL)
				.setTitle("Lieutnant of Morgul").setDescription("Reach +1500.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 1500).parent(mordor_1000));
		Advancement mordor_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_3000", FrameType.CHALLENGE)
				.setTitle("Commander of Lugbúrz").setDescription("Reach +3000.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 3000).parent(mordor_1500));

		Advancement morgul_crafter = build(true, data(LOTRItems.MORDOR_CRAFTING_TABLE.get(), "morgul_crafter", FrameType.TASK)
			.setTitle("Morgul Crafter").setDescription("Craft an item on the Morgul Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.MORDOR_CRAFTING_TABLE.get()).parent(enter_mordor));

		Advancement the_bombardier = build(true, data(ExtendedItems.ORC_BOMB.get(), "the_bombardier", FrameType.TASK)
			.setTitle("The Bombardier").setDescription("Craft an Orc Bomb."),
			addCraftingTrigger(make(), LOTRBlocks.MORDOR_CRAFTING_TABLE.get(), ExtendedItems.ORC_BOMB.get(),
				LOTRBlocks.ANGMAR_CRAFTING_TABLE.get(), LOTRBlocks.URUK_CRAFTING_TABLE.get(), 
				ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE.get()).parent(morgul_crafter));

		Advancement orc_smithey = build(true, data(LOTRItems.ORC_STEEL_INGOT.get(), "orc_smithey", FrameType.TASK)
			.setTitle("Orc Smith").setDescription("Smelt an ingot of Orc Steel in the Orc Forge."),
			addCraftingTrigger(make(), LOTRBlocks.ORC_FORGE.get(), LOTRItems.ORC_STEEL_INGOT.get()).parent(enter_mordor));

		Advancement guldural_ore = build(true, data(LOTRItems.GULDURIL_ORE_MORDOR.get(), "guldural_ore", FrameType.TASK)
			.setTitle("A Sickly Light").setDescription("Mine some Gulduril ore."),
			addBreakBlockTriggers(make(), LOTRBlocks.GULDURIL_ORE_MORDOR.get(), LOTRBlocks.GULDURIL_ORE_STONE.get(), 
				LOTRBlocks.GULDURIL_CRYSTAL.get()).parent(orc_smithey));

		Advancement durnaur_ore = build(true, data(LOTRItems.DURNOR_ORE.get(), "durnaur_ore", FrameType.TASK)
			.setTitle("Fires of the Deep").setDescription("Mine a gem of Durnaur."),
			addBreakBlockTriggers(make(), LOTRBlocks.DURNOR_ORE.get()).parent(orc_smithey));

		Advancement morgul_black_smithey = build(true, data(ExtendedItems.BLACK_URUK_STEEL_INGOT.get(), "morgul_black_smithey", FrameType.TASK)
			.setTitle("Black Smithery").setDescription("Smelt an ingot of Black Uruk Steel in the Orc Forge."),
			addCraftingTrigger(make(), LOTRBlocks.ORC_FORGE.get(), ExtendedItems.BLACK_URUK_STEEL_INGOT.get()).parent(durnaur_ore));

		Advancement morgul_orc_smithey = build(false, data(ExtendedItems.MORGUL_STEEL_INGOT.get(), "morgul_orc_smithey", FrameType.TASK)
			.setTitle("Morgul Smith").setDescription("Smelt an ingot of Morgul Steel in the Orc Forge."),
			addCraftingTrigger(make(), LOTRBlocks.ORC_FORGE.get(), ExtendedItems.MORGUL_STEEL_INGOT.get()).parent(guldural_ore));

		Advancement mordor_armor = build(true, data(LOTRItems.MORDOR_CHESTPLATE.get(), "mordor_armor", FrameType.TASK)
			.setTitle("Inspection!").setDescription("Equip a full suit of Mordor armor."),
			addArmorTrigger(make(), "mordor", LOTRItems.MORDOR_HELMET.get(), LOTRItems.MORDOR_CHESTPLATE.get(),
					LOTRItems.MORDOR_LEGGINGS.get(), LOTRItems.MORDOR_BOOTS.get()).parent(enter_mordor));

		//Advancement morgul_armor = build(true, data(ExtendedItems.MORGUL_CHESTPLATE.get(), "morgul_armor", FrameType.TASK)
		//	.setTitle("Knight of Dushgoi").setDescription("Equip a full suit of Morgul Knight Armor."),
		//	addArmorTrigger(make(), "morgul", ExtendedItems.MORGUL_HELMET.get(), ExtendedItems.MORGUL_CHESTPLATE.get(),
		//		ExtendedItems.MORGUL_LEGGINGS.get(), ExtendedItems.MORGUL_BOOTS.get()).parent(mordor_armor));

		Advancement mordor_orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "mordor_orc_slayer", FrameType.TASK)
			.setTitle("Mordor Orc Slayer").setDescription("Kill a Mordor Orc."),
			addKillEntityTriggers(make(), LOTREntities.MORDOR_ORC.get(), LOTREntities.MORDOR_ORC_ARCHER.get()).parent(enter_mordor));

		Advancement survivalist = build(true, data(LOTRItems.MORGUL_SHROOM.get(), "survivalist", FrameType.GOAL)
			.setTitle("Survivalist").setDescription("Eat a Morgul-shroom"),
			addConsumeItemTrigger(make(), LOTRItems.MORGUL_SHROOM.get()).parent(enter_mordor));
	}

	@Override
	public String getFolder() {
		return "mordor";
	}

	@Override
	public int getLegacyCount() {
		return 36;
	}

}
