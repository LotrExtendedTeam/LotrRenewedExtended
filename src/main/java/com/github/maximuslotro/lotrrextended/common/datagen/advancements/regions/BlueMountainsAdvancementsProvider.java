package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

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
				.setTitle("Blue Mountains Dwarf Slayer").setDescription("Kill a Blue Mountains Dwarf.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.BLUE_MOUNTAINS_ARCHER.get()), 
						LOTREntities.BLUE_MOUNTAINS_DWARF.get()), LOTREntities.BLUE_MOUNTAINS_WARRIOR.get())
				.requirements(IRequirementsStrategy.OR).parent(blue_mountains));

		Advancement blue_dwarf_armor = build(data(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get(), "warrior_of_ered_luin", false, FrameType.TASK, true, true, false)
				.setTitle("Warrior of Ered Luin").setDescription("Acquire a full set of Blue Dwarven armor.").setTitleStype(TextFormatting.GREEN), 
				addArmorTrigger(make(), "blue_dwarven", LOTRItems.BLUE_DWARVEN_HELMET.get(), LOTRItems.BLUE_DWARVEN_CHESTPLATE.get(), 
						LOTRItems.BLUE_DWARVEN_LEGGINGS.get(), LOTRItems.BLUE_DWARVEN_BOOTS.get()).parent(blue_mountains));
	}

	@Override
	public String getFolder() {
		return "blue_mountains";
	}

}
