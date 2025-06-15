package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;


public class GeneralAdvancementsProvider extends BaseAdvancementType{
	private static final List<ResourceLocation> BIOMES_MAJOR;
	private static final List<ResourceLocation> BIOMES_ALL;
	
	static {
		BIOMES_ALL = new ArrayList<>();
		Collection<RegistryObject<Biome>> eeMES = LOTRBiomes.BIOMES.getEntries();
		for(RegistryObject<Biome> b : eeMES) {
			BIOMES_ALL.add(b.getId());
		}
		BIOMES_MAJOR = LOTRBiomes.listBiomeNamesForClassicGen();
	}

	@Override
	public void generateAdvancements() {
		Advancement base = build(true, data(LOTRItems.RED_BOOK.get(), "first_steps", true, FrameType.TASK, true, true, false)
				.setTitle("First Steps").setDescription("Enter Middle-Earth."),
				addBiomeAltTriggers(addDimensionTrigger(make(), LOTRDimensions.MIDDLE_EARTH_WORLD_KEY), BIOMES_ALL));

		Advancement orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "orc_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Orc Slayer").setDescription("Kill an Orc."),
				addKillEntityTriggers(make(), LOTREntities.GUNDABAD_ORC.get(), LOTREntities.GUNDABAD_ORC_ARCHER.get(),
						LOTREntities.MORDOR_ORC.get(), LOTREntities.MORDOR_ORC_ARCHER.get(),
						LOTREntities.ISENGARD_SNAGA.get(), LOTREntities.ISENGARD_SNAGA_ARCHER.get()).parent(base));

		Advancement athelas = build(true, data(LOTRItems.ATHELAS.get(), "kingsfoil", false, FrameType.TASK, true, true, false)
				.setTitle("Kingsfoil").setDescription("Find the Athelas herb."),
				addItemTrigger(make(), LOTRItems.ATHELAS.get()).parent(base));

		Advancement trade = build(false, data(ExtendedItems.SILVER_COIN_ONE.get(), "trade", false, FrameType.TASK, true, true, false)
			.setTitle("Middle Earth Marketplace").setDescription("Trade with a Middle Earth trader."),
			addTradeNoEntityTrigger(make(), "any_trader", 0).parent(base));
		// Obtainable with the current 200 max item limit, at 5c per item for 64 stackable items
		Advancement filthy_rich = build(true, data(ExtendedItems.SILVER_COIN_TEN.get(), "filthy_rich", false, FrameType.TASK, true, true, false)
			.setTitle("Filthy Rich").setDescription("Earn 1000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 1000).parent(trade));
		// Obtainable with the current 200 max item limit, at 13c per item for 64 stackable items
		Advancement ludicrously_rich = build(false, data(ExtendedItems.SILVER_COIN_HUNDRED.get(), "ludicrously_rich", false, FrameType.GOAL, true, true, false)
			.setTitle("Ludicrously Rich").setDescription("Earn 2500 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 2500).parent(filthy_rich));
		// Obtainable with the current 200 max item limit, at 25c per item for 64 stackable items
		Advancement frighteningly_rich = build(false, data(Items.GOLD_INGOT, "frighteningly_rich", false, FrameType.CHALLENGE, true, true, false)
			.setTitle("Frighteningly Rich").setDescription("Earn 5000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 5000).parent(ludicrously_rich));
		// Obtainable with the current 200 max item limit, at 50c per item for 64 stackable item, only white packed fur currently works for this
		Advancement unfathomably_rich = build(false, data(LOTRItems.MITHRIL_INGOT.get(), "unfathomably_rich", false, FrameType.CHALLENGE, true, true, false)
			.setTitle("Unfathomably Rich").setDescription("Earn 10,000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 10000).parent(frighteningly_rich));
		// Obtainable with the current 200 max item limit, at 100c per item for 64 stackable items, only white packed fur currently works for this
		Advancement god_rich = build(false, data(LOTRItems.MITHRIL_BLOCK.get(), "god_rich", false, FrameType.CHALLENGE, true, true, true)
			.setTitle("Reality-Bendingly Rich").setDescription("Earn 20,000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 20000).parent(unfathomably_rich));
		// Obtainable with the current 200 max item limit, at 5c per item for 64 stackable items
		Advancement big_spender = build(false, data(ExtendedItems.SILVER_COIN_TEN.get(), "big_spender", false, FrameType.TASK, true, true, false)
			.setTitle("Big Spender").setDescription("Spend 1000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 1000).parent(trade));
		// Obtainable with the current 200 max item limit, at 13c per item for 64 stackable items
		Advancement extravagant_spender = build(false, data(ExtendedItems.SILVER_COIN_HUNDRED.get(), "extravagant_spender", false, FrameType.GOAL, true, true, false)
			.setTitle("Extravagant Spender").setDescription("Spend 2500 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 2500).parent(big_spender));
		// Obtainable with the current 200 max item limit, at 25c per item for 64 stackable items
		Advancement high_roller = build(false, data(Items.GOLD_INGOT, "high_roller", false, FrameType.CHALLENGE, true, true, false)
			.setTitle("High Roller").setDescription("Spend 5000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 5000).parent(extravagant_spender));
		// Obtainable with the current 200 max item limit, at 50c per item for 64 stackable items
		Advancement spending_tycoon = build(false, data(LOTRItems.MITHRIL_INGOT.get(), "spending_tycoon", false, FrameType.CHALLENGE, true, true, false)
			.setTitle("Spending Tycoon").setDescription("Spend 10,000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 10000).parent(high_roller));
		// Obtainable with the current 200 max item limit, at 100c per item for 64 stackable items
		Advancement economic_god = build(false, data(LOTRItems.MITHRIL_BLOCK.get(), "economic_god", false, FrameType.CHALLENGE, true, true, true)
			.setTitle("Economic God").setDescription("Spend 20,000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 20000).parent(spending_tycoon));

		Advancement trash = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "one_mans_trash", false, FrameType.TASK, true, true, false)
				.setTitle("One Man's Trash").setDescription("Trade with a travelling Oddment Collector."),
				addTradeEntityTrigger(make(), ExtendedEntities.ODDMENT_COLLECTOR.get()).parent(trade));

		Advancement pouches = build(true, data(LOTRItems.SMALL_POUCH.get(), "bigger_on_the_inside", false, FrameType.TASK, true, true, false)
				.setTitle("Bigger on the Inside").setDescription("Acquire a pouch."),
				addItemTrigger(addItemTrigger(addItemTrigger(make(), LOTRItems.SMALL_POUCH.get()), LOTRItems.MEDIUM_POUCH.get()),
						LOTRItems.LARGE_POUCH.get()).requirements(IRequirementsStrategy.OR).parent(base));

		Advancement true_silver = build(true, data(LOTRItems.MITHRIL_ORE.get(), "true_silver", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("True Silver").setDescription("Obtain Mithril Ore."),
				addItemTrigger(make(), LOTRItems.MITHRIL_ORE.get()).parent(base));
		Advancement very_serious_dedication = build(true, data(LOTRItems.MITHRIL_HOE.get(), "very_serious_dedication", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Very Serious Dedication").setDescription("Acquire a Mithril Hoe."),
				addItemTrigger(make(), LOTRItems.MITHRIL_HOE.get()).parent(true_silver));
		Advancement mithril_armor = build(true, data(LOTRItems.MITHRIL_CHESTPLATE.get(), "treasures_of_old", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Treasures of Old").setDescription("Acquire a full set of Mithril armor."),
				addArmorTrigger(make(), "mithril", LOTRItems.MITHRIL_HELMET.get(), LOTRItems.MITHRIL_CHESTPLATE.get(),
						LOTRItems.MITHRIL_LEGGINGS.get(), LOTRItems.MITHRIL_BOOTS.get()).parent(true_silver));

		Advancement warg_slayer = build(true, data(LOTRItems.WARG_BONE.get(), "warg_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Warg Slayer").setDescription("Kill a Warg."),
				addKillEntityTriggers(make(), LOTREntities.GUNDABAD_WARG.get(), LOTREntities.ISENGARD_WARG.get(),
				LOTREntities.MORDOR_WARG.get()).parent(base));

		Advancement warg_armor = build(true, data(ExtendedItems.BROWN_WARG_CHESTPLATE.get(), "master_hunter", false, FrameType.TASK, true, true, false)
				.setTitle("Master Hunter").setDescription("Acquire a full set of Warg Fur armor."),
				addArmorTrigger(addArmorTrigger(addArmorTrigger(addArmorTrigger(addArmorTrigger(make(), "white_warg",
												ExtendedItems.WHITE_WARG_HELMET.get(), ExtendedItems.WHITE_WARG_CHESTPLATE.get(),
												ExtendedItems.WHITE_WARG_LEGGINGS.get(), ExtendedItems.WHITE_WARG_BOOTS.get()),
												"silver_warg", ExtendedItems.SILVER_WARG_HELMET.get(), ExtendedItems.SILVER_WARG_CHESTPLATE.get(),
												ExtendedItems.SILVER_WARG_LEGGINGS.get(), ExtendedItems.SILVER_WARG_BOOTS.get()),
												"gray_warg", ExtendedItems.GRAY_WARG_HELMET.get(), ExtendedItems.GRAY_WARG_CHESTPLATE.get(),
												ExtendedItems.GRAY_WARG_LEGGINGS.get(), ExtendedItems.GRAY_WARG_BOOTS.get())
												, "warg", ExtendedItems.BROWN_WARG_HELMET.get(), ExtendedItems.BROWN_WARG_CHESTPLATE.get(),
												ExtendedItems.BROWN_WARG_LEGGINGS.get(), ExtendedItems.BROWN_WARG_BOOTS.get())
												, "black_warg", ExtendedItems.BLACK_WARG_HELMET.get(), ExtendedItems.BLACK_WARG_CHESTPLATE.get(),
												ExtendedItems.BLACK_WARG_LEGGINGS.get(), ExtendedItems.BLACK_WARG_BOOTS.get())
				.requirements(IRequirementsStrategy.OR).parent(base));

		Advancement white_warg_armor = build(false, data(ExtendedItems.WHITE_WARG_CHESTPLATE.get(), "super_master_hunter", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Got the Drip").setDescription("Acquire a full set of White Warg Fur armor."),
				addArmorTrigger(make(), "white_warg", ExtendedItems.WHITE_WARG_HELMET.get(), ExtendedItems.WHITE_WARG_CHESTPLATE.get(),
												ExtendedItems.WHITE_WARG_LEGGINGS.get(), ExtendedItems.WHITE_WARG_BOOTS.get()).parent(warg_armor));

		Advancement copper_award = build(false, data(LOTRItems.COPPER_INGOT.get(), "copper_award", false, FrameType.TASK, true, true, false)
				.setTitle("Copper Award").setDescription("Obtain a copper ingot by smelting copper ore."),
				addItemTrigger(make(), LOTRItems.COPPER_INGOT.get()).parent(base));

		Advancement tin_award = build(false, data(LOTRItems.TIN_INGOT.get(), "tin_award", false, FrameType.TASK, true, true, false)
				.setTitle("Tin Award").setDescription("Obtain a tin ingot by smelting tin ore."),
				addItemTrigger(make(), LOTRItems.TIN_INGOT.get()).parent(copper_award));

		Advancement bronze_award = build(true, data(LOTRItems.BRONZE_INGOT.get(), "bronze_award", false, FrameType.TASK, true, true, false)
				.setTitle("Bronze Award").setDescription("Craft a bronze ingot by combining tin and copper."),
				addItemTrigger(make(), LOTRItems.BRONZE_INGOT.get()).parent(tin_award));

		Advancement get_moldy_bread = build(true, data(LOTRItems.MAGGOTY_BREAD.get(), "three_stinking_days", false, FrameType.TASK, true, true, false)
				.setTitle("Three Stinking Days!").setDescription("Eat some Maggoty Bread."),
				addConsumeItemTrigger(make(), LOTRItems.MAGGOTY_BREAD.get()).parent(base));

		Advancement get_bad_meat = build(true, data(LOTRItems.SUSPICIOUS_MEAT.get(), "suspicious", false, FrameType.TASK, true, true, false)
				.setTitle("Suspicious...").setDescription("Produce some suspicious meat from questionable ingredients."),
				addItemTrigger(make(), LOTRItems.SUSPICIOUS_MEAT.get()).parent(get_moldy_bread));

		Advancement eat_bad_meat = build(false, data(LOTRItems.SUSPICIOUS_MEAT.get(), "very_suspicious", false, FrameType.GOAL, true, true, false)
				.setTitle("Yum...").setDescription("Eat some suspicious meat..."),
				addConsumeItemTrigger(make(), LOTRItems.SUSPICIOUS_MEAT.get()).parent(get_bad_meat));

		Advancement get_man_meat = build(true, data(LOTRItems.MAN_FLESH.get(), "i_smell_man_flesh", false, FrameType.TASK, true, true, false)
				.setTitle("I Smell Man-Flesh!").setDescription("Eat a hunk of Man-Flesh."),
				addConsumeItemTrigger(make(), LOTRItems.MAN_FLESH.get()).parent(get_moldy_bread));

		Advancement saddle = build(true, data(Items.SADDLE, "time_to_ride", false, FrameType.TASK, true, true, false)
				.setTitle("Time to Ride!").setDescription("Acquire a Saddle."),
				addItemTrigger(make(), Items.SADDLE).parent(base));
		
		Advancement master_adventurer = build(false, data(Items.SADDLE, "master_adventurer", false, FrameType.CHALLENGE, true, true, false)
			.setTitle("Master Adventurer").setDescription("Visit every biome in Middle Earth"),
			addBiomeANDTriggers(make(), BIOMES_ALL).parent(saddle));
		
		Advancement apple_crumble = build(true, data(LOTRItems.APPLE_CRUMBLE.get(), "traditional_dish", false, FrameType.TASK, true, true, false)
				.setTitle("Traditional Dish").setDescription("Acquire an Apple Crumble."),
				addItemTrigger(make(), LOTRItems.APPLE_CRUMBLE.get()).parent(base));

		Advancement fish_ring = build(true, data(Items.FISHING_ROD, "its_our_birthday", false, FrameType.TASK, true, true, false)
				.setTitle("It's Our Birthday").setDescription("Catch a ring with a fishing rod."),
				addFishTrigger(addFishTrigger(addFishTrigger(make(), LOTRItems.SILVER_RING.get()), LOTRItems.GOLD_RING.get()),
						LOTRItems.MITHRIL_RING.get()).requirements(IRequirementsStrategy.OR).parent(base));

		Advancement exp_fish_ring = build(false, data(Items.FISHING_ROD, "swag_catch", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Swaggest Catch Ever").setDescription("Catch a mithril ring with a fishing rod."),
				addFishTrigger(make(), LOTRItems.MITHRIL_RING.get()).parent(fish_ring));

	}

	@Override
	public String getFolder() {
		return "general";
	}

}
