package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;

public class AdvancementsProviderIronHills extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement iron_hills = build(true, dataMain(LOTRItems.DWARVEN_PICKAXE.get(), "iron_hills", FrameType.TASK)
			.setTitle("Durin's Folk").setDescription("Enter the Iron Hills."),
			addBiomeTrigger(make(), LOTRBiomes.IRON_HILLS));

		Advancement dwarf_slayer = build(true, data(LOTRItems.DWARF_BONE.get(), "dwarf_slayer", FrameType.TASK)
			.setTitle("Dwarf Slayer").setDescription("Kill a dwarf."),
			addKillEntityTriggers(make(), LOTREntities.DWARF.get(), LOTREntities.DWARF_ARCHER.get(),
				LOTREntities.DWARF_WARRIOR.get()).parent(iron_hills));

		Advancement dwarf_armor = build(true, data(LOTRItems.DWARVEN_CHESTPLATE.get(), "tight_fit", FrameType.TASK)
			.setTitle("A Tight Fit").setDescription("Equip a full suit of Dwarven armor."),
			addArmorTrigger(make(), "dwarven", LOTRItems.DWARVEN_HELMET.get(), LOTRItems.DWARVEN_CHESTPLATE.get(),
				LOTRItems.DWARVEN_LEGGINGS.get(), LOTRItems.DWARVEN_BOOTS.get()).parent(iron_hills));

		Advancement glowstone_ore = build(true, data(LOTRItems.GLOWSTONE_ORE.get(), "glowstone_ore", FrameType.GOAL)
			.setTitle("In Dark Places").setDescription("Mine some glowstone ore."),
			addBreakBlockTriggers(make(), LOTRBlocks.GLOWSTONE_ORE.get(), LOTRBlocks.GLOWSTONE_CRYSTAL.get()).parent(iron_hills));

		Advancement dwarf_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_guest", FrameType.TASK)
			.setTitle("Longbeard Guest").setDescription("Reach +10.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 10).parent(iron_hills));
		Advancement dwarf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_friend", FrameType.TASK)
			.setTitle("Longbeard Friend").setDescription("Reach +50.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 50).parent(dwarf_10));
		Advancement dwarf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_oathfriend", FrameType.TASK)
			.setTitle("Longbeard Oathfriend").setDescription("Reach +100.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 100).parent(dwarf_50));
		Advancement dwarf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_axebearer", FrameType.TASK)
			.setTitle("Khazâd Axebearer").setDescription("Reach +200.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 200).parent(dwarf_100));
		Advancement dwarf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_champion", FrameType.TASK)
			.setTitle("Khazâd Champion").setDescription("Reach +500.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 500).parent(dwarf_200));
		Advancement dwarf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_commander", FrameType.TASK)
			.setTitle("Khazâd Commander").setDescription("Reach +1000.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 1000).parent(dwarf_500));
		Advancement dwarf_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_lord", FrameType.GOAL)
			.setTitle("Dwarven Lord/Lady").setDescription("Reach +1500.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 1500).parent(dwarf_1000));
		Advancement dwarf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_sigin_taragu", FrameType.CHALLENGE)
			.setTitle("Uzbad/Uzbadûna Sigin-tarâgu").setDescription("Reach +3000.0 alignment with Durin's Folk."),
			addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 3000).parent(dwarf_1500));

		Advancement dwarven_crafter = build(true, data(LOTRItems.DWARVEN_CRAFTING_TABLE.get(), "dwarven_crafter", FrameType.TASK)
			.setTitle("Dwarven Crafter").setDescription("Craft an item on the Dwarven Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.DWARVEN_CRAFTING_TABLE.get()).parent(iron_hills));

		Advancement dwarven_smith = build(true, data(LOTRItems.DWARVEN_STEEL_INGOT.get(), "dwarven_smith", FrameType.TASK)
			.setTitle("Dwarven Smith").setDescription("Smelt an ingot of Dwarven Steel in the Dwarven Forge."),
			addCraftingTrigger(make(), LOTRBlocks.DWARVEN_FORGE.get(), LOTRItems.DWARVEN_STEEL_INGOT.get()).parent(iron_hills));

		Advancement wealthy_dwarven_crafter = build(true, data(LOTRItems.MITHRIL_TRIMMED_DWARVEN_BRICK.get(), "wealthy_dwarven_crafter", FrameType.GOAL)
			.setTitle("Ludicrously Wealthy").setDescription("Use some spare mithril to decorate your Dwarven bricks."),
			addCraftingTrigger(make(), LOTRBlocks.DWARVEN_CRAFTING_TABLE.get(), LOTRItems.MITHRIL_TRIMMED_DWARVEN_BRICK.get()).parent(dwarven_crafter));

		Advancement delving_deep = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "delving_deep", FrameType.TASK)
			.setTitle("Delving Deep").setDescription("Trade with a Dwarf Miner."),
			addTradeEntityTrigger(make(), ExtendedEntities.DWARVEN_MINER.get()).parent(iron_hills));

		Advancement dwarf_smith = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "dwarf_smith", FrameType.TASK)
			.setTitle("At Durin's Anvil").setDescription("Trade with a Dwarven Smith."),
			addTradeEntityTrigger(make(), ExtendedEntities.DWARVEN_SMITH.get()).parent(iron_hills));

		Advancement dwarven_tonic = build(true, data(LOTRItems.DWARVEN_TONIC.get(), "dwarven_tonic", FrameType.TASK)
			.setTitle("Eyes of the Dwarves").setDescription("Drink a mug of Dwarven Tonic."),
			addDrinkItemTrigger(make(), LOTRItems.DWARVEN_TONIC.get()).parent(iron_hills));

		Advancement dwarven_hire = build(true, data(LOTRItems.DWARVEN_SWORD.get(), "dwarven_hire", FrameType.TASK)
			.setTitle("Khazâd ai-mênu!").setDescription("Hire a unit from a Dwarf Commander."),
			addUnitHireTrigger(make(), ExtendedEntities.DWARVEN_COMMANDER.get()).parent(iron_hills));
	}

	@Override
	public String getFolder() {
		return "iron_hills";
	}

	@Override
	public int getLegacyCount() {
		return 25;
	}

}
