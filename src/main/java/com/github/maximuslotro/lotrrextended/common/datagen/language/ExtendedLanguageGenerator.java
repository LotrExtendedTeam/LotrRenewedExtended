package com.github.maximuslotro.lotrrextended.common.datagen.language;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBannerPatterns;

import lotr.common.init.ExtendedEntities;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedLanguageGenerator extends ExtendedLanguageHelper {

	public ExtendedLanguageGenerator(DataGenerator gen, ExistingFileHelper fileHelper, String modid, String locale) {
		super(gen, fileHelper, modid, locale);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void makeTranslations() {
		// Try Add New Block Names
		for (RegistryObject<Block> block : ExtendedBlocks.BLOCKS.getEntries()) {
			if(block.getId().getPath().contains("large_pot_")) {
				// Skip adding all variations of large pots
				continue;
			}
			if(block.getId().getPath().contains("bookshelf")) {
				// We are using key for vanilla bookshelf localization
				continue;
			}
			addBlock(block.get(), "");
		}

		// Try Add New Item Names
		for (RegistryObject<Item> item : ExtendedItems.ITEMS.getEntries()) {
			addItem(item.get(), "");
		}

		// Try Add New Biome Names
		for (String biome : DataGenValueHolder.biomeNames) {
			addD(biome);
		}

		// Try Add New Banner Patterns
		for (BannerPattern entity : ExtendedBannerPatterns.getPatterns()) {
			for(DyeColor col : DyeColor.values()) {
				addOverride("block.minecraft.banner." + entity.getFilename()+"."+col.getName(), ExtendedLanguageColorHelper.getColorFullTextChnager(col) +" "+ ExtendedBannerPatterns.getHashtoname().get(entity.getFilename()));
			}
		}
		for (Entry<String, String> entity : DataGenValueHolder.bannerPatternItemNameHolders.entrySet()) {
			addOverride("item.lotrextended." + entity.getKey(), "Banner Pattern");
			addOverride("item.lotrextended." + entity.getKey() + ".desc", ExtendedBannerPatterns.getHashtoname().get(entity.getValue()));
		}

		// Try Add New Entity Names
		for (String entity : ExtendedEntities.REGISTEREDNPCENTITIES) {
			addD("entity.lotr." + entity);
			addD("item.lotr." + entity + "_spawn_egg");
		}
		for (String entity : ExtendedEntities.REGISTEREDANIMALENTITIES) {
			addD("entity.lotr." + entity);
			addD("item.lotr." + entity + "_spawn_egg");
		}
		for (String entity : ExtendedEntities.REGISTEREDNONMOBENTITIES) {
			addD("entity.lotr." + entity);
		}
		for (String entity : ExtendedEntities.REGISTEREDNONMOBENTITIESEXTENDED) {
			addD("entity.lotrextended." + entity);
		}
		// Add Waypoint Strings
		for (String waypointData : DataGenValueHolder.waypointText) {
			addD(waypointData);
		}

		// Fix missing Renewed strings
		addD("entity.lotr.boat", "Boat");
		addD("death.attack.lotr.plant", "%s was slain by a plant");
		addD("death.attack.lotr.plant.player", "%s was slain by a plant whilst fighting %2$s");
		addD("death.attack.lotr.frost", "%s succumbed to the cold");
		addD("death.attack.lotr.frost.player", "%s succumbed to the cold whilst fighting %2$s");
		addD("death.attack.lotr.stalagmite", "%1$s was spiked by a stalagmite");
		addD("death.attack.lotr.stalagmite.player", "%1$s was spiked on a stalagmite by %2$s");
		addD("death.attack.lotr.quagmire", "%1$s got stuck in a swamp");
		addD("death.attack.lotr.quagmire.player", "%1$s was thrown in a swamp by %2$s");
		addD("death.attack.lotr.thrownSpear", "%1$s was speared by %2$s");
		addD("death.attack.lotr.thrownSpear.item", "%1$s was speared by %2$s using %3$s");

		// Generate Container Titles
		for (Entry<String, String> entity : DataGenValueHolder.containerTitlesHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Config Value Names
		for (Entry<String, String> entity : DataGenValueHolder.configCommentHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Attributes Names
		for (Entry<String, String> entity : DataGenValueHolder.attributeNames.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Advancement Values
		for (Entry<String, String> entity : DataGenValueHolder.advancementHolder.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Statistics titles
		for (Entry<String, String> entity : DataGenValueHolder.statsHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Subtitles
		for (Entry<String, String> entity : DataGenValueHolder.soundEventHolder.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Hired Unit Strings
		for (Entry<String, String> entity : DataGenValueHolder.hiredUnitStrings.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Effect Name Strings
		for (Entry<String, String> entity : DataGenValueHolder.effectsHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Generate Invasion Names TODO determine if these can be simplified using existing faction keys
		for (String invasion : DataGenValueHolder.invasionNames) {
			addD(invasion);
		}

		// chat notifications
		addD("chat.lotrextended.no_custom", "Custom waypoint fast travel has been disabled on this server.");
		addD("chat.lotrextended.no_default", "Default waypoint fast travel has been disabled on this server.");
		addD("chat.lotrextended.requires_alignment", "You need at least %1$s %2$s alignment to perform this action");

		// Add Menu strings
		addD("gui.lotrextended.menu.fellowships", "Fellowships");
		addD("gui.lotrextended.menu.settings", "Options");
		addD("gui.lotrextended.menu.titles", "Titles");
		addD("gui.lotrextended.menu.achievements", "Achievements");
		addD("gui.lotrextended.menu.advancements", "Advancements");
		addD("gui.lotrextended.menu.back", "Back");
		addD("gui.lotrextended.menu.overview", "Overview");
		addD("gui.lotrextended.menu.equipment", "Equipment");
		addD("gui.lotrextended.menu.farmhand", "Farmhand");

		//Default option strings
		addD("gui.lotrextended.base.disabled", "Disabled");
		addD("gui.lotrextended.base.enabled", "Enabled");
		addD("gui.lotrextended.base.disable", "Disable");
		addD("gui.lotrextended.base.enable", "Enable");
		addD("gui.lotrextended.base.shown", "Shown");
		addD("gui.lotrextended.base.hidden", "Hidden");
		addD("gui.lotrextended.base.show", "Show");
		addD("gui.lotrextended.base.hide", "Hide");
		addD("gui.lotrextended.base.set", "Set");
		addD("gui.lotrextended.base.true", "True");
		addD("gui.lotrextended.base.false", "False");
		addD("gui.lotrextended.base.start", "Start");
		addD("gui.lotrextended.base.cancel", "Cancel");
		addD("gui.lotrextended.base.on", "On");
		addD("gui.lotrextended.base.off", "Off");
		addD("gui.lotrextended.base.return", "Return");
		addD("gui.lotrextended.base.player_not_found", "Player not found");
		addD("gui.lotrextended.base.unset", "Unset");

		// npc generic buttons
		addD("gui.lotrextended.entity.talk_button", "Talk");
		addD("gui.lotrextended.entity.talk_button.tooltip", "Talk with %1$s.");
		// trade system
		addD("gui.lotrextended.trading.trade_button", "Trade");
		addD("gui.lotrextended.trading.trade_button.tooltip", "Browse goods and strike a deal.");
		addD("gui.lotrextended.trading.exchange_button", "Exchange Coins");
		addD("gui.lotrextended.trading.exchange_button.tooltip", "Exchange coin denominations.");
		addD("gui.lotrextended.trading.trader_busy", "The trader is busy with another player at the moment.");
		addD("gui.lotrextended.trading.trader_alignment_too_low", "Your alignment is too low.");
		addD("gui.lotrextended.trading.trader_unfrendly", "Trader is unfriendly at the moment.");
		addD("gui.lotrextended.trading.buy", "Buy");
		addD("gui.lotrextended.trading.sell", "Sell");
		addD("gui.lotrextended.trading.sell_offers", "Sell Offers");
		addD("gui.lotrextended.trading.tooltip.cooldown", "On Cooldown");
		addD("gui.lotrextended.trading.tooltip.new_trade_freq", "A trade comes back every minute.");
		addD("gui.lotrextended.trading.tooltip.trades_used", "%1$s/%2$s Used");
		addD("gui.lotrextended.trading.coin_exchange.tooltip.down", "Break a higher denomination coin into lower denomination coins.");
		addD("gui.lotrextended.trading.coin_exchange.tooltip.up", "Convert lower denomination coins into a higher denomination coin.");
		addD("chat.wandering_trader.arrive_near_you", "%1$s has arrived nearby");
		addD("chat.wandering_trader.arrive_near_player", "%1$s has arrived nearby %2$s");
		addD("chat.wandering_trader.left_the_area", "%1$s has left the area");

		// hired unit messages and tooltips for captains
		addD("gui.lotrextended.hiring.hire_button", "Hire");
		addD("gui.lotrextended.hiring.hire_button.tooltip", "Enlist the service of NPCs.");
		addD("gui.lotrextended.hiring.captain_alignment_too_low", "Your alignment is too low.");
		addD("gui.lotrextended.hiring.captain_unfriendly", "Captain is unfriendly at the moment.");
		addD("gui.lotrextended.hiring.hire_unit", "Hire: %s");
		addD("gui.lotrextended.hiring.hire_unit.amount", "Hire for %s coins");
		addD("gui.lotrextended.hiring.hire_unit.not_pledged", "Insufficient pledge! Pledge required to hire this unit");
		addD("gui.lotrextended.hiring.hire_unit.not_enough_money", "Insufficient currency! %s coins required to hire this unit");
		addD("gui.lotrextended.hiring.hire_unit.not_alignment", "Insufficient alignment! %s alignment required to hire this unit");
		addD("gui.lotrextended.hiring.not_enough_money", "Not enough money. You need %s coins to hire that unit");
		addD("gui.lotrextended.hiring.not_pledged", "You need to be pledged to hire that unit");
		addD("gui.lotrextended.hiring.not_alignment", "Your alignment is too low. You need %s alignment to hire that unit");
		addD("gui.lotrextended.hiring.unit_has_been_hired", "The %1$s has been hired for %2$s coins");
		addD("gui.lotrextended.hiring.squad_assign", "Squadron?");
		addD("gui.lotrextended.hiring.pledge_required", "Pledge Required");
		// hired unit main menu
		addD("gui.lotrextended.hired_unit.interface_button", "Equipment & Tasks");
		addD("gui.lotrextended.hired_unit.interface_button.tooltip", "Access unit equipment and command tasks.");
		addD("gui.lotrextended.hired_unit.dismiss_button", "Dismiss");
		addD("gui.lotrextended.hired_unit.dismiss_button.tooltip", "Dismiss unit from your service.");
		addD("gui.lotrextended.hired_unit.dismiss_button_confirm", "Confirm Dismissal");
		addD("gui.lotrextended.hired_unit.dismiss_button_confirm.tooltip", "This operation is irreversible!");
		addD("gui.lotrextended.hired_unit.transfer_button", "Transfer");
		addD("gui.lotrextended.hired_unit.transfer_button.tooltip", "Transfer unit to another online player.");
		addD("gui.lotrextended.hired_unit.transfer_button_confirm", "Confirm Transfer");
		addD("gui.lotrextended.hired_unit.transfer_button_confirm.tooltip", "Transfering units to another player is irreversible!");
		addD("gui.lotrextended.hired_unit.error.old_hiring_tooltip_1_11", "Operation not available for unit hired before 1.11.0");
		addD("gui.lotrextended.hired_unit.range.guard", "Guard Range: %1$s");
		addD("gui.lotrextended.hired_unit.range.farming", "Farming Range: %1$s");
		addD("gui.lotrextended.hired_unit.range.tooltip", "The maximum range this unit can travel.");
		// hired unit overview menu
		addD("gui.lotrextended.hired_unit.companion", "Companion:");
		addD("gui.lotrextended.hired_unit.health", "Health");
		addD("gui.lotrextended.hired_unit.combat_level", "Combat Level: %1$s");
		addD("gui.lotrextended.hired_unit.xp", "Combat Xp: %1$s");
		addD("gui.lotrextended.hired_unit.mob_kills", "Mob Kills: %1$s");
		addD("gui.lotrextended.hired_unit.order.disabled.tooltip", "Task is disabled for this type of unit.");
		addD("gui.lotrextended.hired_unit.order.stationary.title.tooltip", "Stationary: %s");
		addD("gui.lotrextended.hired_unit.order.stationary.on.tooltip", "Hired unit will not move unless defending itself.");
		addD("gui.lotrextended.hired_unit.order.stationary.off.tooltip", "Hired unit is active.");
		addD("gui.lotrextended.hired_unit.order.follows.title.tooltip", "Follows: %s");
		addD("gui.lotrextended.hired_unit.order.follows.on.tooltip", "Hired unit is following you.");
		addD("gui.lotrextended.hired_unit.order.follows.off.tooltip", "Hired unit is not following you.");
		addD("gui.lotrextended.hired_unit.order.farming.title.tooltip", "Farming: %s");
		addD("gui.lotrextended.hired_unit.order.farming.on.tooltip", "Farmhand in farming the land.");
		addD("gui.lotrextended.hired_unit.order.farming.off.tooltip", "Farmhand is not farming.");
		addD("gui.lotrextended.hired_unit.order.guard.title.tooltip", "Guarding: %s");
		addD("gui.lotrextended.hired_unit.order.guard.on.tooltip", "Hired unit is guarding you.");
		addD("gui.lotrextended.hired_unit.order.guard.off.tooltip", "Hired unit is not guarding you.");
		addD("gui.lotrextended.hired_unit.squad_button.tooltip", "Change unit squadron.");
		// hired unit equipment menu
		addD("gui.lotrextended.hired_unit.armor", "Armor:");
		addD("gui.lotrextended.hired_unit.armor_points", "Points: %1$s");
		addD("gui.lotrextended.hired_unit.armor_toughness", "Toughness: %1$s");
		addD("gui.lotrextended.hired_unit.attack", "Attack:");
		addD("gui.lotrextended.hired_unit.attack_damage", "Damage: %1$s");
		addD("gui.lotrextended.hired_unit.attack_speed", "Speed: %1$s");
		// hired unit farmhand menu
		addD("gui.lotrextended.hired_unit.farmhand.farming_level", "Farming Level: %s");
		addD("gui.lotrextended.hired_unit.farmhand.crops_farmed", "Crops Harvested: %s");
		addD("gui.lotrextended.hired_unit.farmhand.level.tooltip", "%1$s of %2$s Xp earned towards next level.");
		addD("gui.lotrextended.hired_unit.farmhand.info.tooltip", "A unit's farming level determines how many inventory slots it has and how efficient or fast it farms.");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.title", "Farmhand Issues:");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.no_seeds", "Seeds required to function!");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.one_seed", "More than one seed required to plant!");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.no_composter", "No Composter found nearby!");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.no_crafting", "No Crafting Table found nearby!");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.no_chest", "No valid Chests found nearby!");
		addD("gui.lotrextended.hired_unit.farmhand.warning_tooltips.inventory_full", "Inventory full!");
		// hired unit chat messages
		addD("chat.lotrextended.hired_unit.death", "Your hired unit %1$s");
		addD("chat.lotrextended.hired_unit.desert", "Your hired unit %1$s has deserted");
		addD("chat.lotrextended.hired_unit.dismissed", "Your hired unit %1$s was dismissed from service");
		addD("chat.lotrextended.hired_unit.transfered", "Your hired unit %1$s was transfered to %2$s");
		addD("chat.lotrextended.hired_unit.levelup.combat", "Your hired unit %1$s has reached combat level %2$s");
		addD("chat.lotrextended.hired_unit.levelup.farming", "Your hired unit %1$s has reached farming level %2$s");

		// wandering trader departure lines
		// TODO: Eventually these should properly be moved to the speechbanks files but that is pretty complex to do
		//  and in order to get 1.6 out quicker I have decided that it can wait until 1.7
		addD("speechbank.oddment_collector.departure.1", "%1$s: Come now, %2$s. Or you shall forever hold your scrap!");
		addD("speechbank.oddment_collector.departure.2", "%1$s: %2$s, are you sure you have no scrap to spare? For I shall have to leave soon! I accept firstborns for coin too...");
		addD("speechbank.oddment_collector.departure.3", "%1$s: It is almost time for me to leave, %2$s. So hurry if you want me to take your oddments off your hands!");
		addD("speechbank.oddment_collector.departure.4", "%1$s: I'm sure we can strike one last deal, %2$s.");
		addD("speechbank.oddment_collector.departure.5", "%1$s: Can you spare just a little more scrap before I depart these forsaken lands?");
		addD("speechbank.oddment_collector.departure.6", "%1$s: It's the scrap that's never collected that takes longest to sell, %2$s. So before I leave, can you not spare a little more scrap for a poor old oddment collector?");
		addD("speechbank.oddment_collector.departure.7", "%1$s: I shall soon be taking a well-earned break. So, quickly, %2$s, bring me your last scrap!");
		addD("speechbank.oddment_collector.departure.8", "%1$s: I fear this place has little else to offer me in the scrap market. I shall soon depart to richer lands.");
		addD("speechbank.oddment_collector.departure.9", "%1$s: The scrap that one hoards is worth little more than pebbles, %2$s. So come now and sell that rubbish of yours before I go far away!");
		addD("speechbank.oddment_collector.departure.10", "%1$s: The road must go on, %2$s! Who am I but a humble scrap collector to stop it. So before I hasten over the hills, let me rid you of any unwanted odds and ends.");
		addD("speechbank.oddment_collector.departure.11", "%1$s: Come trade with me quickly, %2$s! For I shall soon be leaving these lands to seek the wealth of the Gundabad Orcs.");
		addD("speechbank.oddment_collector.departure.12", "%1$s: I'm feeling thirsty, %2$s. A stay at a good inn would do me well! Luckily I know one near the mountains blue, so come quick or you shall miss me.");
		addD("speechbank.oddment_collector.departure.13", "%1$s: My deals don't last forever, %2$s.");
		addD("speechbank.oddment_collector.departure.14", "%1$s: A rest in a comfortable bed beckons, %2$s. Oddment collectors have to sleep too!");
		addD("speechbank.oddment_collector.departure.15", "%1$s: Roll up, roll up! Last scrap deals for many weeks!");
		addD("speechbank.oddment_collector.departure.16", "%1$s: Do you have no more scrap, %2$s? Then I shall have to leave.");
		addD("speechbank.oddment_collector.departure.17", "%1$s: We scrap collectors move often to find the best sources of oddments. I am no exception, %2$s.");
		addD("speechbank.oddment_collector.departure.18", "%1$s: Business is slow here. Perhaps it is time to seek my fortunes elsewhere.");
		addD("speechbank.oddment_collector.departure.19", "%1$s: I hear the Mango-Men of Harad have plenty of scrap iron. Perhaps I should seek them instead of waiting for you to empty your pockets, %2$s.");

		// Galadhrim Wanderer departure lines
		addD("speechbank.galadhrim_wanderer.departure.1", "%1$s: My longing for the sea grows ever deeper. I think I shall be leaving soon.");
		addD("speechbank.galadhrim_wanderer.departure.2", "%1$s: My time here is almost at an end.");
		addD("speechbank.galadhrim_wanderer.departure.3", "%1$s: I shall not stay in these lands much longer.");
		addD("speechbank.galadhrim_wanderer.departure.4", "%1$s: The time has almost come to move on.");
		addD("speechbank.galadhrim_wanderer.departure.5", "%1$s: I will not tarry here forever.");
		addD("speechbank.galadhrim_wanderer.departure.6", "%1$s: The wind is calling. I must away soon.");
		addD("speechbank.galadhrim_wanderer.departure.7", "%1$s: If you wish to trade with me, you should hurry. I will be leaving soon.");
		addD("speechbank.galadhrim_wanderer.departure.8", "%1$s: I will have to depart soon.");
		addD("speechbank.galadhrim_wanderer.departure.9", "%1$s: Do you hear the sea calling? I shall soon leave these lands.");

		// Rivendell Wanderer departure lines
		addD("speechbank.rivendell_wanderer.departure.1", "%1$s: My longing for the sea grows ever deeper. I think I shall be leaving soon.");
		addD("speechbank.rivendell_wanderer.departure.2", "%1$s: My time here is almost at an end.");
		addD("speechbank.rivendell_wanderer.departure.3", "%1$s: I shall not stay in these lands much longer.");
		addD("speechbank.rivendell_wanderer.departure.4", "%1$s: The time has almost come to move on.");
		addD("speechbank.rivendell_wanderer.departure.5", "%1$s: I will not tarry here forever.");
		addD("speechbank.rivendell_wanderer.departure.6", "%1$s: The wind is calling. I must away soon.");
		addD("speechbank.rivendell_wanderer.departure.7", "%1$s: If you wish to trade with me, you should hurry. I will be leaving soon.");
		addD("speechbank.rivendell_wanderer.departure.8", "%1$s: I will have to depart soon.");
		addD("speechbank.rivendell_wanderer.departure.9", "%1$s: Do you hear the sea calling? I shall soon leave these lands.");

		// Blue Mountains Merchant departure lines
		addD("speechbank.blue_mountains_merchant.departure.1", "%1$s: Well %2$s, I'll be on my way soon enough.");
		addD("speechbank.blue_mountains_merchant.departure.2", "%1$s: I can't stay here forever. Other lands to visit, other goods to trade...");
		addD("speechbank.blue_mountains_merchant.departure.3", "%1$s: If you wish to trade with me %2$s, you would be wise to hurry!");
		addD("speechbank.blue_mountains_merchant.departure.4", "%1$s: The riches of the Blue Mountains shall not wait here forever.");
		addD("speechbank.blue_mountains_merchant.departure.5", "%1$s: This is your last chance to get an excellent Dwarvish bargain!");
		addD("speechbank.blue_mountains_merchant.departure.6", "%1$s: I shall be leaving soon %2$s, but there is still time for a trade or two!");
		addD("speechbank.blue_mountains_merchant.departure.7", "%1$s: Business cannot wait, %2$s! I will not stay here for too long.");
		addD("speechbank.blue_mountains_merchant.departure.8", "%1$s: Other lands await! I'll be leaving soon.");
		addD("speechbank.blue_mountains_merchant.departure.9", "%1$s: Hurry %2$s! I will have to depart these lands soon.");
		addD("speechbank.blue_mountains_merchant.departure.10", "%1$s: Do you wish to gaze upon the riches of the Dwarves of the Blue Mountains? Well %2$s, you had better be quick. I'll be on my way soon!");
		addD("speechbank.blue_mountains_merchant.departure.11", "%1$s: Time is passing, %2$s! If you wish to trade with me, you must do it now!");

		// Bree-Land Merchant departure lines
		addD("speechbank.breeland_merchant.departure.1", "%1$s: I hear Black Riders have been wandering around these parts, I can't stay here much longer.");
		addD("speechbank.breeland_merchant.departure.2", "%1$s: Hurry %2$s, I'll be leaving soon.");
		addD("speechbank.breeland_merchant.departure.3", "%1$s: If you'd like some Bree-land goods, now is your last chance!");
		addD("speechbank.breeland_merchant.departure.4", "%1$s: I'll be back on the road here soon.");
		addD("speechbank.breeland_merchant.departure.5", "%1$s: I can't be in these lands for too long, don't want robbers or Goblins to get me.");

		// Invasions
		addD("chat.lotrextended.invasion.started", "%s have launched an invasion nearby!");

		// Bandit lines
		addD("chat.lotrextended.bandit.stolen", "A bandit stole some items from your inventory!");

		// Add Fellowship Menu strings
		addD("gui.lotrextended.fellowships.title", "Middle-earth Fellowships");
		addD("gui.lotrextended.fellowships.creation.disabled", "Fellowship creation is disabled!");
		addD("gui.lotrextended.fellowships.use_command", "Please use the command for now!");

		// Add Settings Menu strings
		addD("gui.lotrextended.serversettings.title", "Middle-earth Server Options");
		addD("gui.lotrextended.serversettings.button", "Server Options");
		addD("gui.lotrextended.serversettings.category.gameplay", "Gameplay Settings");
		addD("gui.lotrextended.serversettings.category.entity", "Entity Settings");
		addD("gui.lotrextended.serversettings.category.block", "Block Settings");
		addD("gui.lotrextended.serversettings.category.chair", "Chair Settings");
		addD("gui.lotrextended.serversettings.category.modifiers", "Modifier Settings");
		addD("gui.lotrextended.serversettings.category.protection", "Spawn Protection");
		addD("gui.lotrextended.serversettings.category.worldgen", "World Generation");
		addD("gui.lotrextended.serversettings.category.worldgen.tooltip", "Note: World generation settings do not apply to previously generated chunks.");
		addD("gui.lotrextended.serversettings.category.worldmaintenance", "World Maintenance");
		addD("gui.lotrextended.serversettings.category.worldmaintenance.tooltip", "Note: These options can modify or regenerate parts of the world, including existing chunks. Changes may be permanent.");
		addD("gui.lotrextended.serversettings.bannerprotection.setting", "Banner Protection: %s");
		addD("gui.lotrextended.serversettings.fellowshipcreation.setting", "Fellowship Creation: %s");
		addD("gui.lotrextended.serversettings.renewedwaypointcreate.setting", "Renewed Custom WP: %s");
		addD("gui.lotrextended.serversettings.legacywaypointcreate.setting", "Legacy Custom WP: %s");
		addD("gui.lotrextended.serversettings.defaultwaypointteleportation.setting", "Default WP Teleport: %s");
		addD("gui.lotrextended.serversettings.customwaypointteleportation.setting", "Custom WP Teleport: %s");
		addD("gui.lotrextended.serversettings.feastmode.setting", "Feast Mode: %s");
		addD("gui.lotrextended.serversettings.decreasedhunger.setting", "Decreased Hunger: %s");
		addD("gui.lotrextended.serversettings.chairsitting.setting", "Slab/Stair Sitting: %s");
		addD("gui.lotrextended.serversettings.chairswapping.setting", "Chair Swapping: %s");
		addD("gui.lotrextended.serversettings.chairprotectionoverride.setting", "Chair Prot Override: %s");
		addD("gui.lotrextended.serversettings.fixedstructures.setting", "Fixed Structures: %s");
		addD("gui.lotrextended.serversettings.fixedstructureimladris.setting", "Imladris: %s");
		addD("gui.lotrextended.serversettings.fixedstructuretowerhills.setting", "Tower Hills Towers: %s");
		addD("gui.lotrextended.serversettings.fixedstructureorthanc.setting", "Orthanc: %s");
		addD("gui.lotrextended.serversettings.extendedmodifiers.setting", "Extended Modifiers: %s");
		addD("gui.lotrextended.serversettings.extendedmodifiersremove.setting", "Auto-Remove Modifiers: %s");
		addD("gui.lotrextended.serversettings.vanillaenchanting.setting", "Vanilla Enchanting: %s");
		addD("gui.lotrextended.serversettings.vanillaenchantingremove.setting", "Auto-Remove Enchants: %s");
		addD("gui.lotrextended.serversettings.vanillaxp.setting", "Vanilla XP: %s");
		addD("gui.lotrextended.serversettings.globalspawndamping.setting", "Spawn Rate: %s");
		addD("gui.lotrextended.serversettings.passivetraders.setting", "Passive Traders: %s");
		addD("gui.lotrextended.serversettings.passivecaptains.setting", "Passive Captains: %s");
		addD("gui.lotrextended.serversettings.banditspawning.setting", "Bandit Encounters: %s");
		addD("gui.lotrextended.serversettings.wanderingtraderspawning.setting", "Wandering Traders: %s");
		addD("gui.lotrextended.serversettings.tolkienspawning.setting", "J.R.R. Tolkien: %s");
		addD("gui.lotrextended.serversettings.barrowwightspawnchance.setting", "Barrow Wight Chance: %s%%");
		addD("gui.lotrextended.serversettings.bellchainlength.setting", "Maximum Chain Length: %s blocks");
		addD("gui.lotrextended.serversettings.gatetogglelength.setting", "Gate Toggle Radius: %s blocks");
		addD("gui.lotrextended.serversettings.dripstonehurtheight.setting", "Dripstone Hurt Height: %s blocks");
		addD("gui.lotrextended.serversettings.barsconnecttobranches.setting", "Bars Attach To Branches: %s");
		addD("gui.lotrextended.serversettings.barsconnecttoslabs.setting", "Bars Attach To Slab Sides: %s");
		addD("gui.lotrextended.serversettings.metaldoorsrequirepower.setting", "Metal Doors Require Power: %s");
		addD("gui.lotrextended.serversettings.extendedspawnprotection.setting", "Extended Spawn Protection: %s");
		addD("gui.lotrextended.serversettings.overworldspawnradius.setting", "Overworld Spawn Radius: %s blocks");
		addD("gui.lotrextended.serversettings.middleearthspawnradius.setting", "ME Spawn Radius: %s blocks");
		addD("gui.lotrextended.serversettings.no_perm", "Requires singleplayer cheats or server operator permissions.");
		addD("gui.lotrextended.serversettings.enableconquest.setting", "Conquest: %s");
		addD("gui.lotrextended.settings.title", "Middle-earth Options");
		addD("gui.lotrextended.settings.button", "Client Options");
		addD("gui.lotrextended.settings.worldsettings", "World Settings");
		addD("gui.lotrextended.settings.usersettings", "User Settings");
		addD("gui.lotrextended.settings.environmentsettings", "Environment Settings");
		addD("gui.lotrextended.settings.guisettings", "GUI Settings");
		addD("gui.lotrextended.settings.hudsettings", "Hud Settings");
		addD("gui.lotrextended.settings.modelsettings", "Model Settings");
		addD("gui.lotrextended.settings.soundsettings", "Sound Settings");
		addD("gui.lotrextended.settings.miscellaneoussettings", "Misc Settings");
		addD("gui.lotrextended.settings.devsettings", "Client Dev Settings");
		addD("gui.lotrextended.settings.friendlyFire.setting", "NPC Friendly Fire: ");
		addD("gui.lotrextended.settings.friendlyFireUnit.setting", "Units Friendly Fire: ");
		addD("gui.lotrextended.settings.unit_v_unit.setting", "Combat Between Units: ");
		addD("gui.lotrextended.settings.high_contrast_messages.setting", "High Contrast Messages: ");
		addD("gui.lotrextended.settings.hired_unit_death.setting", "Hired Unit Death Messages: ");
		addD("gui.lotrextended.settings.mapLocation.setting", "Show Map Location: ");
		addD("gui.lotrextended.settings.rankGender.setting", "Feminine Rank Override: ");
		addD("gui.lotrextended.settings.devtoggle.setting", "Client Dev Mode: ");
		addD("gui.lotrextended.settings.devtoggle.setting.forced", "Client Dev Mode: Server Forced");
		addD("gui.lotrextended.settings.fsbr.setting", "Force StrBlock Air Rendering: ");
		addD("gui.lotrextended.settings.structureblockoutlineviewdistance.setting", "StrBlock Outline Dist: %s blocks");
		addD("gui.lotrextended.settings.dwarfwomen.setting", "Dwarf Women use Alex Model: ");
		addD("gui.lotrextended.settings.elfwomen.setting", "Elf Women use Alex Model: ");
		addD("gui.lotrextended.settings.hobbitwomen.setting", "Hobbit Women use Alex Model: ");
		addD("gui.lotrextended.settings.manishwomen.setting", "Manish Women use Alex Model: ");
		addD("gui.lotrextended.settings.orcwomen.setting", "Orc Women use Alex Model: ");
		addD("gui.lotrextended.settings.dolchestplatewings.setting", "Dol Amroth Chestplate: %s wings");
		addD("gui.lotrextended.settings.earlyduckmodel.setting", "Use Old Duck Model: ");
		addD("gui.lotrextended.settings.newrainsounds.setting", "New Rain Sounds: ");
		addD("gui.lotrextended.settings.newthundersounds.setting", "New Thunder Sounds: ");
		addD("gui.lotrextended.settings.windambience.setting", "Wind Ambience: ");
		addD("gui.lotrextended.settings.fogofwar.setting", "Show Exploration FogOfWar: ");
		addD("gui.lotrextended.settings.imperial.setting", "Imperial Units for Distances: ");
		addD("gui.lotrextended.settings.maplabels.setting", "Show Map Labels: ");
		addD("gui.lotrextended.settings.modmainmenu.setting", "Middle Earth Main Menu: ");
		addD("gui.lotrextended.settings.sepiamap.setting", "Sepia Map: ");
		addD("gui.lotrextended.settings.worldhelp.setting", "World Generation Help: ");
		addD("gui.lotrextended.settings.plantparticles.setting", "Plant Particles: %s");
		addD("gui.lotrextended.settings.rainmist.setting", "Misty Rain: ");
		addD("gui.lotrextended.settings.newweather.setting", "Middle Earth Weather: ");
		addD("gui.lotrextended.settings.mistymountainsmist.setting", "Misty Mountains Mist: ");
		addD("gui.lotrextended.settings.sunglare.setting", "Sun Glare: ");
		addD("gui.lotrextended.settings.modsky.setting", "Middle Earth Sky Renderer: ");
		addD("gui.lotrextended.settings.modclouds.setting", "Middle Earth Cloud Renderer: ");
		addD("gui.lotrextended.settings.newraingroundparticles.setting", "New Rain Ground Splashes: ");
		addD("gui.lotrextended.settings.northernlights.setting", "Northern Lights/Aurora: ");
		addD("gui.lotrextended.settings.cloudrange.setting", "Cloud Range: %s chunks");
		addD("gui.lotrextended.settings.compassinfo.setting", "Show Extra Compass Info: ");
		addD("gui.lotrextended.settings.compass.setting", "Middle Earth Compass: ");
		addD("gui.lotrextended.settings.alignmentrender.setting", "Render Player Alignment Bars: ");
		addD("gui.lotrextended.settings.renderalignmentbareverywhere.setting", "Alignment Bars In Other Dims: ");
		addD("gui.lotrextended.settings.immersivespeech.setting", "Immersive Speech Bubbles: ");
		addD("gui.lotrextended.settings.immersivespeechchat.setting", "NPC Chat Override: ");
		addD("gui.lotrextended.settings.alignmentoffsetx.setting", "Alignment Meter X-Offset: %spx");
		addD("gui.lotrextended.settings.alignmentoffsety.setting", "Alignment Meter Y-Offset: %spx");
		addD("gui.lotrextended.settings.immersivespeechduration.setting", "Speech Bubble Duration: %s seconds");
		addD("gui.lotrextended.settings.jeilotrversioncheck.setting", "Patch broken JEI-lotr VC: ");
		addD("gui.lotrextended.settings.lotrextendedversioncheck.setting", "Extended Version Check: ");
		addD("gui.lotrextended.settings.show_hired_unit_hp_numbers.setting", "Large Hired Unit HP Bars: ");
		addD("gui.lotrextended.settings.spawneroutline.setting", "Spawner Outlines: ");
		addD("gui.lotrextended.settings.pathnav.setting", "Path Navigation: ");

		// World Prune stuff
		addD("gui.lotrextended.worldprune.title", "World Prune Panel");
		addD("gui.lotrextended.worldprune.open.message", "World Prune Panel");
		addD("gui.lotrextended.worldprune.open.tooltip", "Opens the world pruning window.");
		addD("gui.lotrextended.worldprune.dryrun.splitter", "%s: %s");
		addD("gui.lotrextended.worldprune.runagain", "Run Again");
		addD("gui.lotrextended.worldprune.returndisabled", "Cannot Exit While Running");
		addD("gui.lotrextended.worldprune.mode", "Mode: %s");
		addD("gui.lotrextended.worldprune.mode.safe", "Dry Run");
		addD("gui.lotrextended.worldprune.mode.live", "Live Deletion");
		addD("gui.lotrextended.worldprune.checking", "Checking chunks: %s%% (%s/%s)");
		addD("gui.lotrextended.worldprune.scanning", "Scanning %s/%s region files (%s chunks discovered)");
		addD("gui.lotrextended.worldprune.processing", "Processing regions: %s%% (%s/%s)");
		addD("gui.lotrextended.worldprune.finished", "Pruning complete! Deleted %s regions, saving %s, and skipped %s");
		addD("gui.lotrextended.worldprune.finished.dry", "[Dry Run] Would delete %s regions, saving %s");
		addD("gui.lotrextended.worldprune.canceled", "Pruning canceled (%s/%s regions processed)");
		// Map
		addD("gui.lotrextended.map.teleport", "Press '%s' to /tp");
		addD("gui.lotrextended.map.region_button", "Region: %s");
		addD("gui.lotrextended.map.conquest.strength", "Conquest Strength: %s");
		addD("gui.lotrextended.map.conquest.benefit.none", "No effect.");
		addD("gui.lotrextended.map.conquest.benefit.low", "Conquest is too low to benefit faction");
		addD("gui.lotrextended.map.conquest.benefit.alies", "Conquest boosts %s allies");
		addD("gui.lotrextended.map.conquest.benefit.crafting", "Enemy crafting tables unlocked");
		addD("gui.lotrextended.map.conquest.benefit.spawning", "Faction units spawn");

		// Banner Menu strings
		addD("gui.lotrextended.banner_configuration.title", "Banner Protection");
		addD("gui.lotrextended.banner_configuration.disabled", "Banner Protection Is Disabled");
		addD("gui.lotrextended.banner_configuration.whitelist.textBox", "Name");
		addD("gui.lotrextended.banner_configuration.whitelist.add_player", "Add Player");
		addD("gui.lotrextended.banner_configuration.whitelist.add_fellowship", "Add Fellowship");
		addD("gui.lotrextended.banner_configuration.whitelist.delete", "Remove");
		addD("gui.lotrextended.banner_configuration.whitelist.delete.disc", "Remove this whitelist entry");
		addD("gui.lotrextended.banner_configuration.type_whitelist", "Protection Type: Whitelist");
		addD("gui.lotrextended.banner_configuration.type_faction", "Protection Type: Faction");
		addD("gui.lotrextended.banner_configuration.type_custom", "Protection Type: Custom");
		addD("gui.lotrextended.banner_configuration.permission_type.full", "Full Permissions");
		addD("gui.lotrextended.banner_configuration.permission_type.limited", "Limited Permissions");
		addD("gui.lotrextended.banner_configuration.send_update", "Sync Banner Now");
		addD("gui.lotrextended.banner_configuration.send_update.disc", "Sync And Validate Banner Info With Server\nThis is done automatically when closing the GUI");
		addD("gui.lotrextended.banner_configuration.self_protection", "Self Protection");
		addD("gui.lotrextended.banner_configuration.self_protection.setting", "Self Protection: %s");
		addD("gui.lotrextended.banner_configuration.self_protection.disc", "Disabling this setting allows players to bypass all settings and break this banner!");
		addD("gui.lotrextended.banner_configuration.faction.requires", "Required Alignment:");
		addD("gui.lotrextended.banner_configuration.faction.aligned", "Protected by: %s");
		addD("gui.lotrextended.banner_configuration.faction.textBox", "Alignment");
		addD("gui.lotrextended.banner_configuration.custom.description", "Protection Message:");
		addD("gui.lotrextended.banner_configuration.custom.textBox", "Message");
		addD("gui.lotrextended.banner_configuration.player.tooltip", "Placed by: %s");
		addD("gui.lotrextended.banner_configuration.perms", "Show Permissions");
		addD("gui.lotrextended.banner_configuration.perms.global", "Global Override Permissions");
		addD("gui.lotrextended.banner_configuration.perms.global.disc", "These permissions take priority over all whitelist and faction permissions and apply to \"ALL\" players!");
		addD("gui.lotrextended.banner_configuration.perms.disc", "Show Additional Permissions");
		addD("gui.lotrextended.banner_configuration.perms.everything", "Full Permission");
		addD("gui.lotrextended.banner_configuration.perms.everything.disc", "This allows \"Everything\" Including Block Breaking/Placing. This is gives the user/group the same permissions as you, with the exception of banner editing.");
		addD("gui.lotrextended.banner_configuration.perms.doors", "Door Permissions");
		addD("gui.lotrextended.banner_configuration.perms.doors.disc", "Allows for opening/closing doors/gates/fences");
		addD("gui.lotrextended.banner_configuration.perms.crafting", "Crafting Table Permissions");
		addD("gui.lotrextended.banner_configuration.perms.crafting.disc", "Allows for users to crafting in workbenches");
		addD("gui.lotrextended.banner_configuration.perms.containers", "Container Permissions");
		addD("gui.lotrextended.banner_configuration.perms.containers.disc", "Allows for accessing container inventories");
		addD("gui.lotrextended.banner_configuration.perms.editables", "Edible Block Permissions");
		addD("gui.lotrextended.banner_configuration.perms.editables.disc", "Allows eating off plates and drinking vessels");
		addD("gui.lotrextended.banner_configuration.perms.beds", "Bed Permissions");
		addD("gui.lotrextended.banner_configuration.perms.beds.disc", "Allows sleeping in beds");
		addD("gui.lotrextended.banner_configuration.perms.flickables", "Flickable Permissions");
		addD("gui.lotrextended.banner_configuration.perms.flickables.disc", "Allows redstone component toggling");
		addD("gui.lotrextended.banner_configuration.perms.knowledge", "Learning Block Permissions");
		addD("gui.lotrextended.banner_configuration.perms.knowledge.disc", "Allows accessing lecterns and bookshelves");
		addD("gui.lotrextended.banner_configuration.perms.banner", "Banner Editing Permissions");
		addD("gui.lotrextended.banner_configuration.perms.banner.disc", "This permission gives the users the ability to edit this banner without your consent. Be careful who you grant this to!");

		// Horn Select strings
		addD("gui.lotrextended.horn_select.title", "Horn of Command Type Selection");
		addD("gui.lotrextended.horn_select.option_summon", "Summon Units");
		addD("gui.lotrextended.horn_select.option_halt_ready", "Halt/Ready Units");
		addD("gui.lotrextended.horn_select.squad.title", "Assign Hired Unit Squad");
		addD("gui.lotrextended.horn_select.squad.textfield", "Hired unit squad text box");
		addD("gui.lotrextended.horn_select.menutype.title", "Horn Selection Menu Type");
		addD("gui.lotrextended.horn_select.menutype.tooltip", "Set horn type selection menu style.");
		addD("gui.lotrextended.horn_select.menutype.legacy", "Legacy Button Menu");
		addD("gui.lotrextended.horn_select.menutype.extended", "Extended Radial Menu");

		// Stone Carving Screen
		addD("gui.lotrextended.stone_engraving.title", "Engrave Message");

		// Banner protection info
		addD("message.lotrextended.banner.land_protected", "This land is under the protection of %1$s");
		addD("message.lotrextended.banner.land_protected.already", "Banner can not be placed since it overlaps with another's banner protection!");

		// Additional item name strings
		addOverride("item.lotrextended.modifier_scroll", "Modifier Scroll");
		addOverride("item.lotrextended.modifier_scroll.named", "%1$s Modifier Scroll");

		// Item description strings
		// Command horn
		addD("gui.lotrextended.horn_of_command.type", "Current Mode - %1$s");
		addD("gui.lotrextended.horn_of_command.type.halt", "Halt Units");
		addD("gui.lotrextended.horn_of_command.type.reddy", "Ready Units");
		addD("gui.lotrextended.horn_of_command.type.summon", "Summon Units");
		addD("gui.lotrextended.horn_of_command.squad", "Squad: %1$s");
		// Orc bombs
		addD("gui.lotrextended.orc_bomb.fire_message", "Ignites the surrounding area");
		addD("gui.lotrextended.orc_bomb.strength_0", "Single Strength");
		addD("gui.lotrextended.orc_bomb.strength_1", "Double Strength");
		addD("gui.lotrextended.orc_bomb.strength_2", "Triple Strength");
		// Armor stats descriptions
		addD("gui.lotrextended.modifiers.warg", "When on Warg:");
		addD("gui.lotrextended.modifiers.boar", "When on Boar:");
		addD("gui.lotrextended.modifiers.horse", "When on Horse:");
		// Reforge Books
		addD("gui.lotrextended.reforge_books.description", "Guarantees %s modifiers on Reforge");
		addD("gui.lotrextended.reforge_books.combat", "Combat");
		addD("gui.lotrextended.reforge_books.artisan", "Artisan");
		// Repair books
		addD("gui.lotrextended.repair_books.description", "%1$s %2$s on Repair");
		addD("gui.lotrextended.repair_books.description.ignore", "Ignores");
		addD("gui.lotrextended.repair_books.smith", "Modifier Material Cost");
		// Banner Item Info
		addD("gui.lotrextended.banner_item.info", "Banner contains protection information");
		//Bow values
		addD("gui.lotrextended.bow.speed", "Draw Speed: %s");
		addD("gui.lotrextended.bow.damage", "Ranged Damage: %s");
		addD("gui.lotrextended.bow.range", "Range: %s");
		//Dagger values
		addD("gui.lotrextended.dagger.poisoned", "Inflicts poison");

		// Generic command strings
		addD("command.lotrextended.generic_error.only_player", "This command can only be run by a player");

		// Command strings
		addD("command.lotrextended.info.listPatches", "Injected patches:");
		addD("command.lotrextended.info.bane_progress", "Bane Progress (for item in your main hand):");
		addD("command.lotrextended.info.server_timeUntilTrader", "Server timeUntilTrader: ");
		addD("command.lotrextended.info.your_trader_cooldown", "Your trader cooldown (seconds): ");
		addD("command.lotrextended.info.your_bandit_cooldown", "Your Bandit Spawn Attempt Cooldown (seconds): ");
		addD("command.lotrextended.fellowship.error", "Fellowship does not exist or you are missing permissions!");
		addD("command.lotrextended.fellowship.error.noexists", "No fellowship by that name exists!");
		addD("command.lotrextended.fellowship.error.exists", "Fellowship with the name [%s] already exists!");
		addD("command.lotrextended.fellowship.error.ownercant", "Owners can't leave Fellowships! You must disband or transfer ownership to leave!");
		addD("command.lotrextended.fellowship.notexists", "No fellowship by that name exists!");
		addD("command.lotrextended.fellowship.player.added", "Added %s to the fellowship");
		addD("command.lotrextended.fellowship.player.removed", "Removed %s from the fellowship");
		addD("command.lotrextended.fellowship.player.invited", "Invited %s to the fellowship");
		addD("command.lotrextended.fellowship.player.setowner", "Transfered ownership of the fellowship to %s");
		addD("command.lotrextended.fellowship.client.invite.recieved", "You have recieved a fellowship invite!");
		addD("command.lotrextended.fellowship.client.invite.rejected", "You have rejected a fellowship invite!");
		addD("command.lotrextended.fellowship.client.invite.accepted", "You have accepted a fellowship invite!");
		addD("command.lotrextended.fellowship.client.admin.added", "You been promoted to fellowship admin in %s");
		addD("command.lotrextended.fellowship.client.admin.removed", "You been demoted from fellowship admin in %s");
		addD("command.lotrextended.fellowship.client.removed", "You been removed from the fellowship %s");
		addD("command.lotrextended.fellowship.client.transfered", "You have been transfered ownership of the fellowship %s");
		addD("command.lotrextended.fellowship.client.created", "Fellowship created!");
		addD("command.lotrextended.fellowship.client.leave", "You have left the fellowship");
		addD("command.lotrextended.fellowship.client.disbanded", "The fellowship %s has been disbanded");
		addD("command.lotrextended.fellowship.icon.set", "Fellowship icon set");
		addD("command.lotrextended.fellowship.icon.removed", "Fellowship icon reset!");
		addD("command.lotrextended.fellowship.disband", "Fellowship disbanded");
		addD("command.lotrextended.fellowship.disband.error", "Failed to disbanded the fellowship!");
		addD("command.lotrextended.fellowship.acceptinvite.error", "Invalid invite!");
		addD("command.lotrextended.fellowship.admin.added", "Added fellowship admin: %s");
		addD("command.lotrextended.fellowship.admin.removed", "Removed fellowship admin: %s");
		addD("command.lotrextended.fellowship.renamed", "Successfully renamed fellowship: %s to %s");
		addD("command.lotrextended.fellowship.pvp", "pvp");
		addD("command.lotrextended.fellowship.hiredunitff", "hired unit friendly fire");
		addD("command.lotrextended.fellowship.maplocation", "Fellowship map location is now");
		addD("command.lotrextended.fellowship.left", "You have left %s");
		addD("command.lotrextended.fellowship.list", "Joined Fellowships: %s");
		addD("command.lotrextended.fellowship.data.name", "Fellowship Name: ");
		addD("command.lotrextended.fellowship.data.pvp", "PVP: ");
		addD("command.lotrextended.fellowship.data.hupvp", "Hired Units PVP: ");
		addD("command.lotrextended.fellowship.data.maploc", "MapLoccation: ");
		addD("command.lotrextended.fellowship.data.icon", "Icon: ");
		addD("command.lotrextended.fellowship.data.owner", "Owner: ");
		addD("command.lotrextended.fellowship.data.admins", "Admins: ");
		addD("command.lotrextended.fellowship.data.members", "Members: ");
		addD("command.lotrextended.fellowship.changecolor", "Faction color updated!");

		//Config Command Values
		addD("command.lotrextended.config.cant", "can only be edited in server config while server is off!");
		addD("command.lotrextended.config.current_value", "Current %1$s value: %2$s");

		//Version strings
		addD("command.lotrextended.version.left", "A new version");
		addD("command.lotrextended.version.right", "was found");

		//Enchant Command Values
		addD("command.lotrextended.enchant.only_one_bane", "Only one bane can be applied to a weapon.");
		addD("command.lotrextended.enchant.no_present_modifiers", "Your item does not have any modifiers.");
		addD("command.lotrextended.enchant.modifier_invalid", "Invalid modifier: %s.");
		addD("command.lotrextended.enchant.modifier_cant_item", "%s can not be applied to this item.");
		addD("command.lotrextended.enchant.modifier_duplicate", "Your item already contains that modifier.");
		addD("command.lotrextended.enchant.modifier_missing", "Your item does not have that modifier.");
		addD("command.lotrextended.enchant.modifier_removed_all", "All modifiers removed from %s.");
		addD("command.lotrextended.enchant.modifier_added", "%s added to %s.");
		addD("command.lotrextended.enchant.modifier_removed", "%s removed from %s.");
		addD("command.lotrextended.enchant.modifier_cant_modifier", "%s can not be applied as there is a conflicting modifier.");
		addD("command.lotrextended.enchant.generated", "Generated random modifiers.");

		// Trigger Commands
		addD("command.lotrextended.trigger.trader.success", "Attempt to spawn wandering trader at %s was successful.");
		addD("command.lotrextended.trigger.trader.failure", "Attempt to spawn wandering trader at %s was unsuccessful.");
		addD("command.lotrextended.trigger.bandit.success", "Attempt to spawn bandit at %s was successful.");
		addD("command.lotrextended.trigger.bandit.failure", "Attempt to spawn bandit at %s was unsuccessful.");
		addD("command.lotrextended.trigger.invasion.success", "An invasion was successfully spawned near %s.");
		addD("command.lotrextended.trigger.invasion.failure", "Could not find an invasion to spawn near %s.");

		// Conquest command
		addD("command.lotrextended.seen.rate.get", "Global conquest rate is %1$s");
		addD("command.lotrextended.conquest.rate.set", "Set global conquest rate to %1$s");
		addD("command.lotrextended.conquest.rate.get", "Global conquest rate is %1$s");
		addD("command.lotrextended.conquest.scale.get", "Conquest grid scale is %1$s, which is %2$s map pixels across");
		addD("command.lotrextended.conquest.scale.set", "Set conquest grid scale to %1$s, which is %2$s map pixels across");
		addD("command.lotrextended.conquest.clear", "Cleared all conquest");
		addD("command.lotrextended.conquest.get", "%1$s has %2$s conquest strength at %3$s");
		addD("command.lotrextended.conquest.get.failure", "No %1$s conquest present at %2$s");
		addD("command.lotrextended.conquest.modify.set", "Set %1$s conquest strength to %2$s at %3$s");
		addD("command.lotrextended.conquest.modify.add", "Added %1$s to %2$s conquest strength at %3$s");
		addD("command.lotrextended.conquest.toggle", "%s conquest");

		// Player time commands
		addD("command.lotrextended.seen.joined", "%1$s first joined on %2$s");
		addD("command.lotrextended.seen.online", "%1$s is currently online");
		addD("command.lotrextended.seen.never", "%1$s has never logged out");
		addD("command.lotrextended.seen.last", "%1$s was last seen %2$s");

		//Extended containers
		addD("container.lotrextended.reed_basket", "Reed Basket");
		addD("container.lotrextended.stone_chest", "Stone Chest");
		addD("container.lotrextended.orc_barrel", "Orc Barrel");
		addD("container.lotrextended.repair", "Lotr Repair & Name");
		addD("container.lotrextended.repair.cost", "Material Cost: %1$s");
		addD("container.lotrextended.repair.reforge", "Reforging repairs the item and re-rolls the modifiers.");
		addD("container.lotrextended.repair.reforge.cost", "Reforge Cost: %1$s");
		addD("container.lotrextended.repair.engrave", "Engrave ownership on item.");
		addD("container.lotrextended.repair.engrave.cost", "Engrave Cost: %1$s");
		addD("container.lotrextended.repair.info.expensive", "Too Expensive!");
		addD("container.lotrextended.repair.info.invalid_combination", "Incompatable Modifiers!");
		addD("container.lotrextended.repair.info.gem_used", "Gem already used!");
		addD("container.lotrextended.repair.info.too_many_modifiers", "Limit: 3 regular modifiers!");
		addD("container.lotrextended.repair.info.modifier_maxed", "Modifier is maxed!");
		addD("container.lotrextended.repair.info.invalid_material", "Invalid Material!");
		addD("container.lotrextended.repair.info.missing_material", "Missing Materials!");

		// item names for base gear
		addD("item.lotrextended.base_gear.chestplate", "Base Chestplate");
		addD("item.lotrextended.base_gear.helmet", "Base Helmet");
		addD("item.lotrextended.base_gear.leggings", "Base Leggings");
		addD("item.lotrextended.base_gear.boots", "Base Boots");
		addD("item.lotrextended.base_gear.weapon", "Base Melee Weapon");
		addD("item.lotrextended.base_gear.weapon_ranged", "Base Ranged Weapon");
		addD("item.lotrextended.base_gear.weapon_mounted", "Base Mounted Weapon");
		addD("item.lotrextended.base_gear.weapon_idle", "Base Idle Weapon");
		addD("item.lotrextended.base_gear.weapon_idle_mounted", "Base Idle Mounted Weapon");
		addD("item.lotrextended.base_gear.shield", "Base Shield");
		addD("item.lotrextended.base_gear.tool", "Base Tool");

		// custom enchantments
		addD("enchant.lotrextended.baneorc", "Orcbane");
		addD("enchant.lotrextended.baneorc.race", "Orcs");
		addD("enchant.lotrextended.banedwarf", "Dwarfbane");
		addD("enchant.lotrextended.banedwarf.race", "Dwarves");
		addD("enchant.lotrextended.baneelf", "Elfbane");
		addD("enchant.lotrextended.baneelf.race", "Elves");
		addD("enchant.lotrextended.banewarg", "Wargbane");
		addD("enchant.lotrextended.banewarg.race", "Wargs");
		addD("enchant.lotrextended.banewight", "Wightbane");
		addD("enchant.lotrextended.banewight.race", "Wights");
		addD("enchant.lotrextended.bane.desc", "damage vs. %s");
		addD("enchant.lotrextended.bane.earn", "Your %s has claimed the lives of many %s, and it shall be their bane evermore!");
		addD("enchant.lotrextended.bane.earn_name", "%s's %s has claimed the lives of many %s, and it shall be their bane evermore!");
		addD("enchant.lotrextended.durability.one", "Hardy");
		addD("enchant.lotrextended.durability.two", "Lasting");
		addD("enchant.lotrextended.durability.three", "Enduring");
		addD("enchant.lotrextended.durability.disc", "durability");
		addD("enchant.lotrextended.looting.one", "Lucky");
		addD("enchant.lotrextended.looting.two", "Fortunate");
		addD("enchant.lotrextended.looting.three", "Blessed");
		addD("enchant.lotrextended.looting.disc", "looting luck");
		addD("enchant.lotrextended.melee_damage.weak_one", "Dull");
		addD("enchant.lotrextended.melee_damage.weak_two", "Blunt");
		addD("enchant.lotrextended.melee_damage.weak_three", "Crooked");
		addD("enchant.lotrextended.melee_damage.one", "Sharp");
		addD("enchant.lotrextended.melee_damage.two", "Keen");
		addD("enchant.lotrextended.melee_damage.three", "Mighty");
		addD("enchant.lotrextended.melee_damage.four", "Legendary");
		addD("enchant.lotrextended.melee_damage.disc", "melee damage");
		addD("enchant.lotrextended.melee_reach.one", "Long");
		addD("enchant.lotrextended.melee_reach.short_one", "Stunted");
		addD("enchant.lotrextended.melee_reach.disc", "melee reach");
		addD("enchant.lotrextended.melee_speed.one", "Swift");
		addD("enchant.lotrextended.melee_speed.slow_one", "Slow");
		addD("enchant.lotrextended.melee_speed.disc", "melee speed");
		addD("enchant.lotrextended.melee_knockback.one", "Hefty");
		addD("enchant.lotrextended.melee_knockback.two", "Hulking");
		addD("enchant.lotrextended.melee_knockback.disc", "melee knockback");
		addD("enchant.lotrextended.tool_speed.one", "Handy");
		addD("enchant.lotrextended.tool_speed.two", "Nimble");
		addD("enchant.lotrextended.tool_speed.three", "Hasty");
		addD("enchant.lotrextended.tool_speed.four", "Masterful");
		addD("enchant.lotrextended.tool_speed.slow_one", "Sluggish");
		addD("enchant.lotrextended.tool_speed.disc", "tool speed");
		addD("enchant.lotrextended.tool_silk", "Silken");
		addD("enchant.lotrextended.tool_silk.disc", "Harvests special blocks");
		addD("enchant.lotrextended.ranged_speed.one", "Strong");
		addD("enchant.lotrextended.ranged_speed.two", "Powerful");
		addD("enchant.lotrextended.ranged_speed.three", "Fierce");
		addD("enchant.lotrextended.ranged_speed.weak_one", "Weak");
		addD("enchant.lotrextended.ranged_speed.weak_two", "Feeble");
		addD("enchant.lotrextended.ranged_speed.disc", "launch speed");
		addD("enchant.lotrextended.ranged_knockback.one", "Punching");
		addD("enchant.lotrextended.ranged_knockback.two", "Striking");
		addD("enchant.lotrextended.ranged_knockback.disc", "ranged knockback");
		addD("enchant.lotrextended.armor_protect.one", "Hardened");
		addD("enchant.lotrextended.armor_protect.two", "Steadfast");
		addD("enchant.lotrextended.armor_protect.weak_one", "Poor");
		addD("enchant.lotrextended.armor_protect.weak_two", "Crude");
		addD("enchant.lotrextended.armor_protect.disc", "armor points");
		addD("enchant.lotrextended.armor_protect_fire.one", "Cooling");
		addD("enchant.lotrextended.armor_protect_fire.two", "Unwithering");
		addD("enchant.lotrextended.armor_protect_fire.three", "Belegostian");
		addD("enchant.lotrextended.armor_protect_fire.disc", "protection vs. fire");
		addD("enchant.lotrextended.armor_protect_ranged.one", "Glancing");
		addD("enchant.lotrextended.armor_protect_ranged.two", "Deflecting");
		addD("enchant.lotrextended.armor_protect_ranged.three", "Eölean");
		addD("enchant.lotrextended.armor_protect_ranged.disc", "protection vs. projectiles");
		addD("enchant.lotrextended.armor_protect_fall.one", "Thicksoled");
		addD("enchant.lotrextended.armor_protect_fall.two", "Strongshod");
		addD("enchant.lotrextended.armor_protect_fall.three", "Lightfooted");
		addD("enchant.lotrextended.armor_protect_fall.disc", "protection vs. falling");
		addD("enchant.lotrextended.armor_protect_blast.one", "Reinforced");
		addD("enchant.lotrextended.armor_protect_blast.two", "Safeguarded");
		addD("enchant.lotrextended.armor_protect_blast.three", "Rúvina Norna");
		addD("enchant.lotrextended.armor_protect_blast.disc", "protection vs. explosions");
		addD("enchant.lotrextended.armor_protect_mithril", "True");
		addD("enchant.lotrextended.armor_protect_mithril.disc", "protection vs. polearms");
		addD("enchant.lotrextended.chilling", "Chilling");
		addD("enchant.lotrextended.chilling.disc", "Chills targets");
		addD("enchant.lotrextended.infernal", "Infernal");
		addD("enchant.lotrextended.infernal.disc", "Sets target aflame");
		addD("enchant.lotrextended.headhunting", "Headhunting");
		addD("enchant.lotrextended.headhunting.disc", "Drops player heads");
		addD("enchant.lotrextended.fishing_luck.one", "Fluked");
		addD("enchant.lotrextended.fishing_luck.two", "Gifted");
		addD("enchant.lotrextended.fishing_luck.three", "Prosperous");
		addD("enchant.lotrextended.fishing_luck.disc", "fishing luck");
		addD("enchant.lotrextended.fishing_speed.one", "Alluring");
		addD("enchant.lotrextended.fishing_speed.two", "Tantalized");
		addD("enchant.lotrextended.fishing_speed.disc", "fishing speed");
		addD("enchant.lotrextended.underwater_movement.one", "Webbed");
		addD("enchant.lotrextended.underwater_movement.two", "Finned");
		addD("enchant.lotrextended.underwater_movement.three", "Ulmoqint");
		addD("enchant.lotrextended.underwater_movement.disc", "underwater speed");
		addD("enchant.lotrextended.underwater_breathing.one", "Shut");
		addD("enchant.lotrextended.underwater_breathing.two", "Latched");
		addD("enchant.lotrextended.underwater_breathing.three", "Sealed");
		addD("enchant.lotrextended.underwater_breathing.disc", "underwater time");
		addD("enchant.lotrextended.toughness.one", "Tough");
		addD("enchant.lotrextended.toughness.two", "Thick");
		addD("enchant.lotrextended.toughness.three", "Unwavering");
		addD("enchant.lotrextended.toughness.disc", "toughness");

		addD("enchant.lotrextended.draw_speed.slow_one", "Heavy");
		addD("enchant.lotrextended.draw_speed.one", "Light");
		addD("enchant.lotrextended.draw_speed.two", "Brisk");
		addD("enchant.lotrextended.draw_speed.three", "Breakneck");
		addD("enchant.lotrextended.draw_speed.disc", "nominal draw time");

		//Damage types
		addD("death.attack.lotrextended.thrownaxe", "%1$s was precision-axed by %2$s");
		addD("death.attack.lotrextended.thrownaxe.item", "%1$s was precision-axed by %2$s using %3$s");
		addD("death.attack.lotrextended.poison_drink", "%1$s drank the wrong drink");
		addD("death.attack.lotrextended.poison_drink.player", "%1$s drank the wrong drink whilst fighting %2$s");
	}

	@Override
	public String getName() {
		return "Renewed Extended Language Provider";
	}
}
