package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.util.text.TextFormatting;


public class BlueMountainsAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> BLUEMTS_BIOMES = ImmutableList.of(LOTRBiomes.BLUE_MOUNTAINS, LOTRBiomes.BLUE_MOUNTAINS_FOOTHILLS);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement blue_mountains = build(data(LOTRItems.BLUE_ROCK.get(), "firebeards_and_broadbeams", true, FrameType.TASK, true, true, false)
				.setTitle("Firebeards and Broadbeams").setDescription("Enter the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), BLUEMTS_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement blue_dwarf_slayer = build(data(LOTRItems.DWARF_BONE.get(), "blue_mountains_dwarf_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Dwarf Slayer").setDescription("Kill a Blue Mountains Dwarf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.BLUE_MOUNTAINS_ARCHER.get()), 
						LOTREntities.BLUE_MOUNTAINS_DWARF.get()), LOTREntities.BLUE_MOUNTAINS_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(blue_mountains));

		Advancement blue_dwarf_armor = build(data(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get(), "warrior_of_ered_luin", false, FrameType.TASK, true, true, false)
				.setTitle("Warrior of Ered Luin").setDescription("Acquire a full set of Blue Dwarven armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "blue_dwarven", LOTRItems.BLUE_DWARVEN_HELMET.get(), LOTRItems.BLUE_DWARVEN_CHESTPLATE.get(), 
						LOTRItems.BLUE_DWARVEN_LEGGINGS.get(), LOTRItems.BLUE_DWARVEN_BOOTS.get()).parent(blue_mountains));

		Advancement dwarf_10 = build(data(LOTRItems.GOLD_RING.get(), "blue_mountians_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Mountains Guest").setDescription("Reach +10.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 10).parent(blue_mountains));

		Advancement dwarf_50 = build(data(LOTRItems.GOLD_RING.get(), "blue_mountians_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Mountains Friend").setDescription("Reach +50.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 50).parent(dwarf_10));

		Advancement dwarf_100 = build(data(LOTRItems.GOLD_RING.get(), "blue_mountians_warden", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Mountains Warden").setDescription("Reach +100.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 100).parent(dwarf_50));

		Advancement dwarf_200 = build(data(LOTRItems.GOLD_RING.get(), "blue_dwarven_axebearer", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Dwarven Axebearer").setDescription("Reach +200.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 200).parent(dwarf_100));

		Advancement dwarf_500 = build(data(LOTRItems.GOLD_RING.get(), "blue_dwarven_champion", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Dwarven Champion").setDescription("Reach +500.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 500).parent(dwarf_200));

		Advancement dwarf_1000 = build(data(LOTRItems.GOLD_RING.get(), "blue_dwarven_captain", false, FrameType.TASK, true, true, false)
				.setTitle("Blue Dwarven Captain").setDescription("Reach +1000.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GREEN), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 1000).parent(dwarf_500));

		Advancement dwarf_1500 = build(data(LOTRItems.GOLD_RING.get(), "blue_dwarven_noble", false, FrameType.GOAL, true, true, false)
				.setTitle("Blue Dwarven Noble").setDescription("Reach +1500.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.GOLD), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 1500).parent(dwarf_1000));

		Advancement dwarf_3000 = build(data(LOTRItems.GOLD_RING.get(), "blue_dwarven_khagal_abbadu", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Uzbad/Uzbadûna Khagal'abbadu").setDescription("Reach +3000.0 alignment with the Dwarves of the Blue Mountains.").setTitleStype(TextFormatting.DARK_PURPLE), 
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 3000).parent(dwarf_1500));
	}

	@Override
	public String getFolder() {
		return "blue_mountains";
	}

}
