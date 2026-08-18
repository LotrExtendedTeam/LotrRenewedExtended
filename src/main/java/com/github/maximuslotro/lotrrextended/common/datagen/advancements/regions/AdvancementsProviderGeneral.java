package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedAlignmentLevelTrigger;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;


public class AdvancementsProviderGeneral extends ExtendedBaseAdvancementType{
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
		Advancement base = build(true, dataMain(LOTRItems.RED_BOOK.get(), "first_steps", FrameType.TASK)
				.setTitle("First Steps").setDescription("Enter Middle-Earth."),
				addBiomeAltTriggers(addDimensionTrigger(make(), LOTRDimensions.MIDDLE_EARTH_WORLD_KEY), BIOMES_ALL));

		Advancement orc_slayer = build(true, data(LOTRItems.ORC_BONE.get(), "orc_slayer", FrameType.TASK)
				.setTitle("Orc Slayer").setDescription("Kill an Orc."),
				addKillEntityTriggers(make(), LOTREntities.GUNDABAD_ORC.get(), LOTREntities.GUNDABAD_ORC_ARCHER.get(),
						LOTREntities.MORDOR_ORC.get(), LOTREntities.MORDOR_ORC_ARCHER.get(),
						LOTREntities.ISENGARD_SNAGA.get(), LOTREntities.ISENGARD_SNAGA_ARCHER.get()).parent(base));

		Advancement athelas = build(true, data(LOTRItems.ATHELAS.get(), "kingsfoil", FrameType.TASK)
				.setTitle("Kingsfoil").setDescription("Find the Athelas herb."),
				addBreakBlockTrigger(make(), LOTRBlocks.ATHELAS.get()).parent(base));

		Advancement life_to_the_dying = build(true, data(LOTRItems.ATHELAS_BREW.get(), "life_to_the_dying", FrameType.TASK)
				.setTitle("Life to the Dying").setDescription("Drink a mug of Athelas Brew."),
				addDrinkItemTrigger(make(), LOTRItems.ATHELAS_BREW.get()).parent(athelas));

		Advancement trade = build(false, data(ExtendedItems.SILVER_COIN_ONE.get(), "trade", FrameType.TASK)
			.setTitle("Middle Earth Marketplace").setDescription("Trade with a Middle Earth trader."),
			addTradeNoEntityTrigger(make(), "any_trader", 0).parent(base));
		// Obtainable with the current 200 max item limit, at 5c per item for 64 stackable items
		Advancement filthy_rich = build(true, data(ExtendedItems.SILVER_COIN_TEN.get(), "filthy_rich", FrameType.TASK)
			.setTitle("Filthy Rich").setDescription("Earn 1000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 1000).parent(trade));
		// Obtainable with the current 200 max item limit, at 13c per item for 64 stackable items
		Advancement ludicrously_rich = build(false, data(ExtendedItems.SILVER_COIN_HUNDRED.get(), "ludicrously_rich", FrameType.GOAL)
			.setTitle("Ludicrously Rich").setDescription("Earn 2500 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 2500).parent(filthy_rich));
		// Obtainable with the current 200 max item limit, at 25c per item for 64 stackable items
		Advancement frighteningly_rich = build(false, data(Items.GOLD_INGOT, "frighteningly_rich", FrameType.CHALLENGE)
			.setTitle("Frighteningly Rich").setDescription("Earn 5000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 5000).parent(ludicrously_rich));
		// Obtainable with the current 200 max item limit, at 50c per item for 64 stackable item, only white matted fur currently works for this
		Advancement unfathomably_rich = build(false, data(LOTRItems.MITHRIL_INGOT.get(), "unfathomably_rich", FrameType.CHALLENGE)
			.setTitle("Unfathomably Rich").setDescription("Earn 10,000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 10000).parent(frighteningly_rich));
		// Obtainable with the current 200 max item limit, at 100c per item for 64 stackable items, only white matted fur currently works for this
		Advancement god_rich = build(false, data(LOTRItems.MITHRIL_BLOCK.get(), "god_rich", FrameType.CHALLENGE).setHidden()
			.setTitle("Reality-Bendingly Rich").setDescription("Earn 20,000 coins from a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", false, 20000).parent(unfathomably_rich));
		// Obtainable with the current 200 max item limit, at 5c per item for 64 stackable items
		Advancement big_spender = build(false, data(ExtendedItems.SILVER_COIN_TEN.get(), "big_spender", FrameType.TASK)
			.setTitle("Big Spender").setDescription("Spend 1000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 1000).parent(trade));
		// Obtainable with the current 200 max item limit, at 13c per item for 64 stackable items
		Advancement extravagant_spender = build(false, data(ExtendedItems.SILVER_COIN_HUNDRED.get(), "extravagant_spender", FrameType.GOAL)
			.setTitle("Extravagant Spender").setDescription("Spend 2500 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 2500).parent(big_spender));
		// Obtainable with the current 200 max item limit, at 25c per item for 64 stackable items
		Advancement high_roller = build(false, data(Items.GOLD_INGOT, "high_roller", FrameType.CHALLENGE)
			.setTitle("High Roller").setDescription("Spend 5000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 5000).parent(extravagant_spender));
		// Obtainable with the current 200 max item limit, at 50c per item for 64 stackable items
		Advancement spending_tycoon = build(false, data(LOTRItems.MITHRIL_INGOT.get(), "spending_tycoon", FrameType.CHALLENGE)
			.setTitle("Spending Tycoon").setDescription("Spend 10,000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 10000).parent(high_roller));
		// Obtainable with the current 200 max item limit, at 100c per item for 64 stackable items
		Advancement economic_god = build(false, data(LOTRItems.MITHRIL_BLOCK.get(), "economic_god", FrameType.CHALLENGE).setHidden()
			.setTitle("Economic God").setDescription("Spend 20,000 coins in a single trade."),
			addTradeNoEntityTrigger(make(), "any_trader", true, 20000).parent(spending_tycoon));

		Advancement trash = build(true, data(ExtendedItems.SILVER_COIN_ONE.get(), "one_mans_trash", FrameType.TASK)
				.setTitle("One Man's Trash").setDescription("Trade with a travelling Oddment Collector."),
				addTradeEntityTrigger(make(), ExtendedEntities.ODDMENT_COLLECTOR.get()).parent(trade));

		Advancement pouches = build(true, data(LOTRItems.SMALL_POUCH.get(), "bigger_on_the_inside", FrameType.TASK)
				.setTitle("Bigger on the Inside").setDescription("Acquire a pouch."),
				addItemTrigger(addItemTrigger(addItemTrigger(make(), LOTRItems.SMALL_POUCH.get()), LOTRItems.MEDIUM_POUCH.get()),
						LOTRItems.LARGE_POUCH.get()).requirements(IRequirementsStrategy.OR).parent(base));

		Advancement true_silver = build(true, data(LOTRItems.MITHRIL_ORE.get(), "true_silver", FrameType.CHALLENGE)
				.setTitle("True Silver").setDescription("Obtain Mithril Ore."),
				addBreakBlockTrigger(make(), LOTRBlocks.MITHRIL_ORE.get()).parent(base));
		Advancement very_serious_dedication = build(true, data(LOTRItems.MITHRIL_HOE.get(), "very_serious_dedication", FrameType.CHALLENGE)
				.setTitle("Very Serious Dedication").setDescription("Acquire a Mithril Hoe."),
				addItemTrigger(make(), LOTRItems.MITHRIL_HOE.get()).parent(true_silver));
		Advancement mithril_armor = build(true, data(LOTRItems.MITHRIL_CHESTPLATE.get(), "treasures_of_old", FrameType.CHALLENGE)
				.setTitle("Treasures of Old").setDescription("Acquire a full set of Mithril armor."),
				addArmorTrigger(make(), "mithril", LOTRItems.MITHRIL_HELMET.get(), LOTRItems.MITHRIL_CHESTPLATE.get(),
						LOTRItems.MITHRIL_LEGGINGS.get(), LOTRItems.MITHRIL_BOOTS.get()).parent(true_silver));

		Advancement warg_slayer = build(true, data(LOTRItems.WARG_BONE.get(), "warg_slayer", FrameType.TASK)
				.setTitle("Warg Slayer").setDescription("Kill a Warg."),
				addKillEntityTriggers(make(), LOTREntities.GUNDABAD_WARG.get(), LOTREntities.ISENGARD_WARG.get(),
				LOTREntities.MORDOR_WARG.get()).parent(base));

		Advancement warg_armor = build(true, data(ExtendedItems.BROWN_WARG_CHESTPLATE.get(), "master_hunter", FrameType.TASK)
				.setTitle("Master Hunter").setDescription("Acquire a full set of Warg Fur armor."),
				addArmorTrigger(addArmorTrigger(addArmorTrigger(addArmorTrigger(addArmorTrigger(make(), 
					"white_warg", ExtendedItems.WHITE_WARG_HELMET.get(), ExtendedItems.WHITE_WARG_CHESTPLATE.get(), ExtendedItems.WHITE_WARG_LEGGINGS.get(), ExtendedItems.WHITE_WARG_BOOTS.get()),
					"silver_warg", ExtendedItems.SILVER_WARG_HELMET.get(), ExtendedItems.SILVER_WARG_CHESTPLATE.get(), ExtendedItems.SILVER_WARG_LEGGINGS.get(), ExtendedItems.SILVER_WARG_BOOTS.get()),
					"gray_warg", ExtendedItems.GRAY_WARG_HELMET.get(), ExtendedItems.GRAY_WARG_CHESTPLATE.get(), ExtendedItems.GRAY_WARG_LEGGINGS.get(), ExtendedItems.GRAY_WARG_BOOTS.get()), 
					"warg", ExtendedItems.BROWN_WARG_HELMET.get(), ExtendedItems.BROWN_WARG_CHESTPLATE.get(), ExtendedItems.BROWN_WARG_LEGGINGS.get(), ExtendedItems.BROWN_WARG_BOOTS.get()), 
					"black_warg", ExtendedItems.BLACK_WARG_HELMET.get(), ExtendedItems.BLACK_WARG_CHESTPLATE.get(), ExtendedItems.BLACK_WARG_LEGGINGS.get(), ExtendedItems.BLACK_WARG_BOOTS.get())
				.requirements(IRequirementsStrategy.OR).parent(warg_slayer));

		Advancement white_warg_armor = build(false, data(ExtendedItems.WHITE_WARG_CHESTPLATE.get(), "super_master_hunter", FrameType.CHALLENGE)
				.setTitle("Got the Drip").setDescription("Acquire a full set of White Warg Fur armor."),
				addArmorTrigger(make(), "white_warg", ExtendedItems.WHITE_WARG_HELMET.get(), ExtendedItems.WHITE_WARG_CHESTPLATE.get(),
												ExtendedItems.WHITE_WARG_LEGGINGS.get(), ExtendedItems.WHITE_WARG_BOOTS.get()).parent(warg_armor));

		Advancement copper_award = build(false, data(LOTRItems.COPPER_INGOT.get(), "copper_award", FrameType.TASK)
				.setTitle("Copper Award").setDescription("Obtain a copper ingot by smelting copper ore."),
				addItemTrigger(make(), LOTRItems.COPPER_INGOT.get()).parent(base));

		Advancement tin_award = build(false, data(LOTRItems.TIN_INGOT.get(), "tin_award", FrameType.TASK)
				.setTitle("Tin Award").setDescription("Obtain a tin ingot by smelting tin ore."),
				addItemTrigger(make(), LOTRItems.TIN_INGOT.get()).parent(copper_award));

		Advancement bronze_award = build(true, data(LOTRItems.BRONZE_INGOT.get(), "bronze_award", FrameType.TASK)
				.setTitle("Bronze Award").setDescription("Craft a bronze ingot by combining tin and copper."),
				addCraftingTrigger(make(), Blocks.CRAFTING_TABLE, LOTRItems.BRONZE_INGOT.get()).parent(tin_award));

		Advancement double_bronze = build(true, data(LOTRItems.BRONZE_INGOT.get(), "double_bronze", FrameType.GOAL)
			.setTitle("Double Bronze").setDescription("Smelt bronze ingots in any alloy forge."),
			addCraftingTrigger(make(), LOTRBlocks.ALLOY_FORGE.get(), LOTRItems.BRONZE_INGOT.get(), LOTRBlocks.DWARVEN_FORGE.get(), LOTRBlocks.ELVEN_FORGE.get(), LOTRBlocks.ORC_FORGE.get()).parent(bronze_award));

		Advancement get_moldy_bread = build(true, data(LOTRItems.MAGGOTY_BREAD.get(), "three_stinking_days", FrameType.TASK)
				.setTitle("Three Stinking Days!").setDescription("Eat some Maggoty Bread."),
				addConsumeItemTrigger(make(), LOTRItems.MAGGOTY_BREAD.get()).parent(base));

		Advancement get_bad_meat = build(true, data(LOTRItems.SUSPICIOUS_MEAT.get(), "suspicious", FrameType.TASK)
				.setTitle("Suspicious...").setDescription("Produce some suspicious meat from questionable ingredients."),
				addCraftingTrigger(make(), Blocks.CRAFTING_TABLE, LOTRItems.SUSPICIOUS_MEAT.get()).parent(get_moldy_bread));

		Advancement eat_bad_meat = build(false, data(LOTRItems.SUSPICIOUS_MEAT.get(), "very_suspicious", FrameType.GOAL)
				.setTitle("Yum...").setDescription("Eat some suspicious meat..."),
				addConsumeItemTrigger(make(), LOTRItems.SUSPICIOUS_MEAT.get()).parent(get_bad_meat));

		Advancement get_man_meat = build(true, data(LOTRItems.MAN_FLESH.get(), "i_smell_man_flesh", FrameType.TASK)
				.setTitle("I Smell Man-Flesh!").setDescription("Eat a hunk of Man-Flesh."),
				addConsumeItemTrigger(make(), LOTRItems.MAN_FLESH.get()).parent(get_moldy_bread));

		Advancement saddle = build(true, data(Items.SADDLE, "time_to_ride", FrameType.TASK)
				.setTitle("Time to Ride!").setDescription("Craft a saddle with three leather and two iron ingots."),
				addCraftingTrigger(make(), Blocks.CRAFTING_TABLE, Items.SADDLE).parent(base));
		
		Advancement master_adventurer = build(false, data(Items.SADDLE, "master_adventurer", FrameType.CHALLENGE)
			.setTitle("Master Adventurer").setDescription("Visit every biome in Middle Earth"),
			addBiomeANDTriggers(make(), BIOMES_MAJOR).parent(saddle));
		
		Advancement apple_crumble = build(true, data(LOTRItems.APPLE_CRUMBLE.get(), "traditional_dish", FrameType.TASK)
				.setTitle("Traditional Dish").setDescription("Acquire an Apple Crumble."),
				addItemTrigger(make(), LOTRItems.APPLE_CRUMBLE.get()).parent(base));

		Advancement fish_ring = build(true, data(Items.FISHING_ROD, "its_our_birthday", FrameType.TASK)
				.setTitle("It's Our Birthday").setDescription("Catch a ring with a fishing rod."),
				addFishTrigger(addFishTrigger(make(), LOTRItems.GOLD_RING.get()),
						LOTRItems.MITHRIL_RING.get()).requirements(IRequirementsStrategy.OR).parent(base));

		Advancement exp_fish_ring = build(false, data(Items.FISHING_ROD, "swag_catch", FrameType.CHALLENGE)
				.setTitle("Swaggest Catch Ever").setDescription("Catch a mithril ring with a fishing rod."),
				addFishTrigger(make(), LOTRItems.MITHRIL_RING.get()).parent(fish_ring));

		Advancement stand_and_deliver = build(true, data(LOTRItems.IRON_DAGGER.get(), "stand_and_deliver", FrameType.TASK)
			.setTitle("Stand and Deliver!").setDescription("Kill a bandit who has stolen some items."),
			addKillEntityTrigger(make(), ExtendedEntities.BANDIT.get()).parent(base));

		Advancement alignment = build(false, data(LOTRItems.SILVER_RING.get(), "alignment", FrameType.TASK)
			.setTitle("Alignment").setDescription("Reach +1.0 alignment with any faction."),
			addAlignmentLevelTrigger(make(), 1).parent(base));

		Advancement pledged = build(true, data(LOTRItems.GOLD_RING.get(), "pledged", FrameType.TASK)
			.setTitle("Here Do I Swear").setDescription("Pledge your service to a faction."),
			make().addCriterion("has_pledged", ExtendedAlignmentLevelTrigger.Instance.pledged()).parent(alignment));

		Advancement true_fealty = build(false, data(LOTRItems.MITHRIL_RING.get(), "true_fealty", FrameType.GOAL)
			.setTitle("True Fealty").setDescription("Reach +10000.0 alignment with any faction."),
			addAlignmentLevelTrigger(make(), 10000).parent(alignment));

		Advancement fasttravel = build(false, data(Items.LEATHER_BOOTS, "fasttravel", FrameType.TASK)
				.setTitle("Light Speed Traveler").setDescription("Fast travel to anywhere in Middle Earth."),
				addStringTrigger(make(), "fastTraveled").parent(base));

		Advancement waypointcreate = build(false, data(Items.IRON_BOOTS, "waypointcreate", FrameType.TASK)
				.setTitle("New Destinations").setDescription("Create a custom waypoint."),
				addStringTrigger(make(), "waypointCreated").parent(fasttravel));

		Advancement conqueror = build(true, data(ExtendedItems.GONDOR_BANNER.get(), "conqueror", FrameType.TASK)
			.setTitle("The Conqueror").setDescription("Lay claim to some land by protecting it with a banner."),
			addStringTrigger(make(), "bannerPlaced").parent(base));

		Advancement conqueror_faction = build(false, data(ExtendedItems.GONDOR_BANNER.get(), "conqueror_faction", FrameType.TASK)
			.setTitle("Faction’s Standard").setDescription("Lay claim to some land for your faction."),
			addStringTrigger(make(), "bannerFaction").parent(conqueror));

		Advancement conqueror_self = build(false, data(ExtendedItems.GONDOR_BANNER.get(), "conqueror_self", FrameType.TASK)
			.setTitle("The Sovereign").setDescription("Lay claim to some land for yourself."),
			addStringTrigger(make(), "bannerSelf").parent(conqueror_faction));

		Advancement shared_dominion = build(false, data(ExtendedItems.GONDOR_BANNER.get(), "shared_dominion", FrameType.TASK)
			.setTitle("Shared Dominion").setDescription("Grant land rights to trusted companions."),
			addStringTrigger(make(), "bannerOthers").parent(conqueror));

		Advancement refreshing = build(false, data(LOTRItems.WATER_DRINK.get(), "refreshing", FrameType.TASK)
				.setTitle("Refreshing").setDescription("Drink any beverage."),
				addDrinkTrigger(make()).parent(base));

		Advancement brewing = build(true, data(LOTRItems.ALE.get(), "brewing", FrameType.TASK)
				.setTitle("Time to Drink!").setDescription("Start brewing a drink in a keg."),
				addStringTrigger(make(), "brewDrink").parent(refreshing));

		Advancement burning_sensation = build(true, data(LOTRItems.ORC_DRAUGHT.get(), "burning_sensation", FrameType.TASK)
				.setTitle("Burning Sensation").setDescription("Drink an Orc Draught."),
				addDrinkItemTrigger(make(), LOTRItems.ORC_DRAUGHT.get()).parent(refreshing));

		Advancement drunkard = build(true, data(LOTRItems.ALE.get(), "drunkard", FrameType.TASK)
				.setTitle("Drunkard").setDescription("Suffer the effects of an alcoholic drink."),
				addDrinkDurationTrigger(make(), 1).parent(refreshing));

		Advancement hooligan = build(true, data(LOTRItems.ALE.get(), "hooligan", FrameType.TASK)
				.setTitle("Hooligan").setDescription("Kill a foe while drunk."),
				addKillEntityPlayerEffectTrigger(make(), Effects.CONFUSION).parent(drunkard));

		Advancement tipsy = build(false, data(LOTRItems.MEAD.get(), "tipsy", FrameType.TASK)
				.setTitle("Tipsy").setDescription("Drink a very strong drink."),
				addDrinkDurationTrigger(make(), 120).parent(drunkard));

		Advancement seasoned_drinker = build(false, data(LOTRItems.RUM.get(), "seasoned_drinker", FrameType.GOAL)
				.setTitle("Seasoned Drinker").setDescription("Start to build an alcohol tolerance."),
				addDrinkToleranceTrigger(make(), 100).parent(tipsy));

		Advancement alcoholism = build(true, data(LOTRItems.VODKA.get(), "alcoholism", FrameType.CHALLENGE)
				.setTitle("Alcoholism").setDescription("Achieve a fearsome level of alcohol tolerance."),
				addDrinkToleranceTrigger(make(), 250).parent(seasoned_drinker));//guessing the legacy value though trial and error

		Advancement blacksmith = build(true, data(LOTRItems.BLACKSMITH_HAMMER.get(), "blacksmith", FrameType.TASK)
				.setTitle("Blacksmith").setDescription("Reforge an item at an anvil."),
				addStringTrigger(make(), "reforged").parent(base));

		Advancement engraver = build(false, data(ExtendedItems.CHISEL.get(), "engraver", FrameType.TASK)
				.setTitle("Autographed!").setDescription("Engrave your name on an item at an anvil."),
				addStringTrigger(make(), "engraved").parent(blacksmith));

		Advancement defeatInvasion = build(true, data(Items.IRON_SWORD, "defeat_invasion", FrameType.CHALLENGE)
				.setTitle("Hail the Victorious").setDescription("Defeat an invasion"),
				addStringTrigger(make(), "defeatedInvasion").parent(base));
	}

	@Override
	public String getFolder() {
		return "general";
	}

	@Override
	public int getLegacyCount() {
		return 65;
	}

}
