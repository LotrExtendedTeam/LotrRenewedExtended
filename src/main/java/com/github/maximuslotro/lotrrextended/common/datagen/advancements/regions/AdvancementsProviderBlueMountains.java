package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;


public class AdvancementsProviderBlueMountains extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> BLUEMTS_BIOMES = ImmutableList.of(LOTRBiomes.BLUE_MOUNTAINS, LOTRBiomes.BLUE_MOUNTAINS_FOOTHILLS);

	@Override
	public void generateAdvancements() {
		Advancement blue_mountains = build(true, dataMain(LOTRItems.BLUE_ROCK.get(), "firebeards_and_broadbeams", FrameType.TASK)
				.setTitle("Firebeards and Broadbeams").setDescription("Enter the Blue Mountains."),
				addBiomeTriggers(make(), BLUEMTS_BIOMES));

		Advancement blue_dwarf_slayer = build(true, data(LOTRItems.DWARF_BONE.get(), "blue_mountains_dwarf_slayer", FrameType.TASK)
				.setTitle("Blue Dwarf Slayer").setDescription("Kill a Blue Mountains Dwarf."),
				addKillEntityTriggers(make(), LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), LOTREntities.BLUE_MOUNTAINS_DWARF.get(),
						LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), ExtendedEntities.BLUE_DWARVEN_MINER.get(), 
						ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get()).parent(blue_mountains));

		Advancement blue_mountain_crafter = build(true, data(LOTRItems.BLUE_MOUNTAINS_CRAFTING_TABLE.get(), "blue_mountain_crafter", FrameType.TASK)
			.setTitle("Blue Dwarven Crafter").setDescription("Craft an item on the Blue Mountains Crafting Table."),
			addCraftingTrigger(make(), LOTRBlocks.BLUE_MOUNTAINS_CRAFTING_TABLE.get()).parent(blue_mountains));
	
		Advancement blue_dwarf_armor = build(true, data(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get(), "warrior_of_ered_luin", FrameType.TASK)
				.setTitle("Warrior of Ered Luin").setDescription("Acquire a full set of Blue Dwarven armor."),
				addArmorTrigger(make(), "blue_dwarven", LOTRItems.BLUE_DWARVEN_HELMET.get(), LOTRItems.BLUE_DWARVEN_CHESTPLATE.get(),
						LOTRItems.BLUE_DWARVEN_LEGGINGS.get(), LOTRItems.BLUE_DWARVEN_BOOTS.get()).parent(blue_mountains));

		Advancement dwarf_10 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_mountians_guest", FrameType.TASK)
				.setTitle("Blue Mountains Guest").setDescription("Reach +10.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 10).parent(blue_mountains));

		Advancement dwarf_50 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_mountians_friend", FrameType.TASK)
				.setTitle("Blue Mountains Friend").setDescription("Reach +50.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 50).parent(dwarf_10));

		Advancement dwarf_100 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_mountians_warden", FrameType.TASK)
				.setTitle("Blue Mountains Warden").setDescription("Reach +100.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 100).parent(dwarf_50));

		Advancement dwarf_200 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_dwarven_axebearer", FrameType.TASK)
				.setTitle("Blue Dwarven Axebearer").setDescription("Reach +200.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 200).parent(dwarf_100));

		Advancement dwarf_500 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_dwarven_champion", FrameType.TASK)
				.setTitle("Blue Dwarven Champion").setDescription("Reach +500.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 500).parent(dwarf_200));

		Advancement dwarf_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_dwarven_captain", FrameType.TASK)
				.setTitle("Blue Dwarven Captain").setDescription("Reach +1000.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 1000).parent(dwarf_500));

		Advancement dwarf_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_dwarven_noble", FrameType.GOAL)
				.setTitle("Blue Dwarven Noble").setDescription("Reach +1500.0 alignment with the Dwarves of the Blue Mountains."),
				addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 1500).parent(dwarf_1000));

		Advancement dwarf_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "blue_dwarven_khagal_abbadu", FrameType.CHALLENGE)
			.setTitle("Uzbad/Uzbadûna Khagal'abbadu").setDescription("Reach +3000.0 alignment with the Dwarves of the Blue Mountains."),
			addAlignmentTrigger(make(), FactionPointers.BLUE_MOUNTAINS, 3000).parent(dwarf_1500));

		Advancement blue_mountains_merchant_trade = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "wealth_from_abroad", FrameType.TASK)
			.setTitle("Wealth from Abroad").setDescription("Trade with a merchant of the Blue Mountains"),
			addTradeEntityTrigger(make(), ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get()).parent(blue_mountains));

		Advancement blue_riches = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "blue_riches", FrameType.TASK)
			.setTitle("Blue Riches").setDescription("Trade with a Blue Mountains Miner"),
			addTradeEntityTrigger(make(), ExtendedEntities.BLUE_DWARVEN_MINER.get()).parent(blue_mountains));

		Advancement blue_smith = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "blue_smith", FrameType.TASK)
			.setTitle("Fires of the Mountains").setDescription("Trade with a Blue Mountains Smith"),
			addTradeEntityTrigger(make(), ExtendedEntities.BLUE_DWARVEN_SMITH.get()).parent(blue_mountains));
	}

	@Override
	public String getFolder() {
		return "blue_mountains";
	}

}
