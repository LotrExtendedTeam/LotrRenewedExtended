package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class IronHillsAdvancementsProvider extends BaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement iron_hills = build(true, data(Items.STONE, "iron_hills", true, FrameType.TASK, true, true, false)
				.setTitle("Durin's Folk").setDescription("Enter the Iron Hills."),
				addBiomeTrigger(make(), LOTRBiomes.IRON_HILLS));

		Advancement hobbit_slayer = build(true, data(LOTRItems.DWARF_BONE.get(), "dwarf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Dwarf Slayer").setDescription("Kill a dwarf."),
				addKillEntityTriggers(make(), LOTREntities.DWARF.get(), LOTREntities.DWARF_ARCHER.get(),
						LOTREntities.DWARF_WARRIOR.get()).parent(iron_hills));

		Advancement dwarf_armor = build(true, data(LOTRItems.DWARVEN_CHESTPLATE.get(), "tight_fit", false, FrameType.TASK, true, true, false)
				.setTitle("A Tight Fit").setDescription("Equip a full suit of Dwarven armor."),
				addArmorTrigger(make(), "dwarven", LOTRItems.DWARVEN_HELMET.get(), LOTRItems.DWARVEN_CHESTPLATE.get(),
						LOTRItems.DWARVEN_LEGGINGS.get(), LOTRItems.DWARVEN_BOOTS.get()).parent(iron_hills));

		Advancement glowstone_ore = build(true, data(LOTRItems.GLOWSTONE_ORE.get(), "glowstone_ore", false, FrameType.GOAL, true, true, false)
				.setTitle("In Dark Places").setDescription("Mine some glowstone ore."),
				addBreakBlockTriggers(make(), LOTRBlocks.GLOWSTONE_ORE.get(), LOTRBlocks.GLOWSTONE_CRYSTAL.get()).parent(iron_hills));

		Advancement dwarf_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Longbeard Guest").setDescription("Reach +10.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 10).parent(iron_hills));
		Advancement dwarf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Longbeard Friend").setDescription("Reach +50.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 50).parent(dwarf_10));
		Advancement dwarf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_oathfriend", false, FrameType.TASK, true, true, false)
				.setTitle("Longbeard Oathfriend").setDescription("Reach +100.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 100).parent(dwarf_50));
		Advancement dwarf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_axebearer", false, FrameType.TASK, true, true, false)
				.setTitle("Khazâd Axebearer").setDescription("Reach +200.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 200).parent(dwarf_100));
		Advancement dwarf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_champion", false, FrameType.TASK, true, true, false)
				.setTitle("Khazâd Champion").setDescription("Reach +500.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 500).parent(dwarf_200));
		Advancement dwarf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_commander", false, FrameType.TASK, true, true, false)
				.setTitle("Khazâd Commander").setDescription("Reach +1000.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 1000).parent(dwarf_500));
		Advancement dwarf_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_lord", false, FrameType.GOAL, true, true, false)
				.setTitle("Dwarven Lord/Lady").setDescription("Reach +1500.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 1500).parent(dwarf_1000));
		Advancement dwarf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "dwarf_sigin_taragu", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Uzbad/Uzbadûna Sigin-tarâgu").setDescription("Reach +3000.0 alignment with Durin's Folk."),
				addAlignmentTrigger(make(), FactionPointers.DURINS_FOLK, 3000).parent(dwarf_1500));
	}

	@Override
	public String getFolder() {
		return "iron_hills";
	}

}
