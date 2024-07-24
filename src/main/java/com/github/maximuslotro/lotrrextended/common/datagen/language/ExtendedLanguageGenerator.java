package com.github.maximuslotro.lotrrextended.common.datagen.language;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBannerPatterns;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedGroupSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedSpawnerBlock;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedLanguageGenerator extends ExtendedLanguageHelper {

	public ExtendedLanguageGenerator(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
	}

	@Override
	protected void makeTranslations() {
		//Add default made Spawner Names
		for (Entry<String, RegistryObject<Block>> block : ExtendedBlocks.ENTITYGROUPSPAWNERBLOCKS.entrySet()) {
			addOverride(block.getValue().get().getDescriptionId(), ((ExtendedGroupSpawnerBlock)block.getValue().get()).getEntitySpawn().getSerializedName() + " Spawner");
		}
		for (Entry<String, RegistryObject<Block>> block : ExtendedBlocks.ENTITYSPAWNERBLOCKS.entrySet()) {
			addOverride(block.getValue().get().getDescriptionId(), ((ExtendedSpawnerBlock)block.getValue().get()).getEntitySpawn().getSerializedName() + " Spawner");
		}

		// Try Add New Block Names
		for (Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			addBlock(block.getValue(), "");
		}

		// Try Add New Item Names
		for (Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			addItem(item.getValue(), "");
		}

		// Try Add New Banner Patterns
		for (BannerPattern entity : ExtendedBannerPatterns.getPatterns()) {
			for(DyeColor col : DyeColor.values()) {
				addOverride("block.minecraft.banner." + entity.getFilename()+"."+col.getName(), TextChanger.getColorFullTextChnager(col) +" "+ ExtendedBannerPatterns.getHashtoname().get(entity.getFilename()));
			}
		}
		for (Entry<String, String> entity : DataGenValueHolder.bannerPatternItemNameHolders.entrySet()) {
			addOverride("item.lotr." + entity.getKey(), "Banner Pattern");
			addOverride("item.lotr." + entity.getKey() + ".desc", ExtendedBannerPatterns.getHashtoname().get(entity.getValue()));
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

		// Try Add Container Titles
		for (Entry<String, String> entity : DataGenValueHolder.containerTitlesHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Try Add New Config Value Names
		for (Entry<String, String> entity : DataGenValueHolder.configCommentHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Try Add New Advancement Values
		for (Entry<String, String> entity : DataGenValueHolder.advancementHolder.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Try Add Statistics titles
		for (Entry<String, String> entity : DataGenValueHolder.statsHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Try Add New Subtitles
		for (Entry<String, String> entity : DataGenValueHolder.soundEventHolder.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Add Menu strings
		addD("gui.lotrextended.menu.fellowships", "Fellowships");
		addD("gui.lotrextended.menu.settings", "Options");
		addD("gui.lotrextended.menu.titles", "Titles");
		addD("gui.lotrextended.menu.achievements", "Achievements");

		//Default option strings
		addD("gui.lotrextended.base.disabled", "Disabled");
		addD("gui.lotrextended.base.enabled", "Enabled");
		addD("gui.lotrextended.base.disable", "Disable");
		addD("gui.lotrextended.base.enable", "Enable");
		addD("gui.lotrextended.base.shown", "Shown");
		addD("gui.lotrextended.base.hidden", "Hidden");
		addD("gui.lotrextended.base.show", "Show");
		addD("gui.lotrextended.base.hide", "Hide");

		// trade system
		addD("gui.lotrextended.trading.trade_button", "Trade");
		addD("gui.lotrextended.trading.trader_busy", "The trader is busy with another player at the moment.");
		addD("gui.lotrextended.trading.trader_alignment_too_low", "Your alignment is too low.");
		addD("gui.lotrextended.trading.talk_button", "Talk");
		addD("gui.lotrextended.trading.exchange_button", "Exchange Coins");
		addD("gui.lotrextended.trading.buy", "Buy");
		addD("gui.lotrextended.trading.sell", "Sell");
		addD("gui.lotrextended.trading.sell_offers", "Sell Offers");
		addD("gui.lotrextended.trading.tooltip.cooldown", "On Cooldown");
		addD("gui.lotrextended.trading.tooltip.new_trade_freq", "A trade comes back every minute.");
		addD("gui.lotrextended.trading.tooltip.trades_used", "%1$s/%2$s Used");

		// wandering trader departure lines
		// TODO: Eventually these should properly be moved to the speechbanks files but that is pretty complex to do
		//  and in order to get 1.6 out quicker I have decided that it can wait until 1.7
		addD("speechbank.oddment_collector.departure.1", "Come now, %1$s. Or you shall forever hold your scrap!");
		addD("speechbank.oddment_collector.departure.2", "%1$s, are you sure you have no scrap to spare? For I shall have to leave soon! I accept firstborns for coin too...");
		addD("speechbank.oddment_collector.departure.3", "It is almost time for me to leave, %1$s. So hurry if you want me to take your oddments off your hands!");
		addD("speechbank.oddment_collector.departure.4", "I'm sure we can strike one last deal, %1$s.");
		addD("speechbank.oddment_collector.departure.5", "Can you spare just a little more scrap before I depart these forsaken lands?");
		addD("speechbank.oddment_collector.departure.6", "It's the scrap that's never collected that takes longest to sell, %1$s. So before I leave, can you not spare a little more scrap for a poor old oddment collector?");
		addD("speechbank.oddment_collector.departure.7", "I shall soon be taking a well-earned break. So, quickly, %1$s, bring me your last scrap!");
		addD("speechbank.oddment_collector.departure.8", "I fear this place has little else to offer me in the scrap market. I shall soon depart to richer lands.");
		addD("speechbank.oddment_collector.departure.9", "The scrap that one hoards is worth little more than pebbles, %$1s. So come now and sell that rubbish of yours before I go far away!");
		addD("speechbank.oddment_collector.departure.10", "The road must go on, %$1s! Who am I but a humble scrap collector to stop it. So before I hasten over the hills, let me rid you of any unwanted odds and ends.");
		addD("speechbank.oddment_collector.departure.12", "Come trade with me quickly, %$1s! For I shall soon be leaving these lands to seek the wealth of the Gundabad Orcs.");
		addD("speechbank.oddment_collector.departure.14", "I'm feeling thirsty, %$1s. A stay at a good inn would do me well! Luckily I know one near the mountains blue, so come quick or you shall miss me.");
		addD("speechbank.oddment_collector.departure.15", "My deals don't last forever, %1$s.");
		addD("speechbank.oddment_collector.departure.16", "A rest in a comfortable bed beckons, %1$s. Oddment collectors have to sleep too!");
		addD("speechbank.oddment_collector.departure.17", "Roll up, roll up! Last scrap deals for many weeks!");
		addD("speechbank.oddment_collector.departure.18", "Do you have no more scrap, %1$s? Then I shall have to leave.");
		addD("speechbank.oddment_collector.departure.19", "We scrap collectors move often to find the best sources of oddments. I am no exception, %$1s.");
		addD("speechbank.oddment_collector.departure.20", "Business is slow here. Perhaps it is time to seek my fortunes elsewhere.");
		addD("speechbank.oddment_collector.departure.21", "I hear the Mango-Men of Harad have plenty of scrap iron. Perhaps I should seek them instead of waiting for you to empty your pockets, %1$s.");

		// Galadhrim Wanderer departure lines
		addD("speechbank.galadhrim_wanderer.departure.1", "My longing for the sea grows ever deeper. I think I shall be leaving soon.");
		addD("speechbank.galadhrim_wanderer.departure.2", "My time here is almost at an end.");
		addD("speechbank.galadhrim_wanderer.departure.3", "I shall not stay in these lands much longer.");
		addD("speechbank.galadhrim_wanderer.departure.4", "The time has almost come to move on.");
		addD("speechbank.galadhrim_wanderer.departure.5", "I will not tarry here forever.");
		addD("speechbank.galadhrim_wanderer.departure.6", "The wind is calling. I must away soon.");
		addD("speechbank.galadhrim_wanderer.departure.7", "If you wish to trade with me, you should hurry. I will be leaving soon.");
		addD("speechbank.galadhrim_wanderer.departure.8", "I will have to depart soon.");
		addD("speechbank.galadhrim_wanderer.departure.9", "Do you hear the sea calling? I shall soon leave these lands.");

		// Rivendell Wanderer departure lines
		addD("speechbank.rivendell_wanderer.departure.1", "My longing for the sea grows ever deeper. I think I shall be leaving soon.");
		addD("speechbank.rivendell_wanderer.departure.2", "My time here is almost at an end.");
		addD("speechbank.rivendell_wanderer.departure.3", "I shall not stay in these lands much longer.");
		addD("speechbank.rivendell_wanderer.departure.4", "The time has almost come to move on.");
		addD("speechbank.rivendell_wanderer.departure.5", "I will not tarry here forever.");
		addD("speechbank.rivendell_wanderer.departure.6", "The wind is calling. I must away soon.");
		addD("speechbank.rivendell_wanderer.departure.7", "If you wish to trade with me, you should hurry. I will be leaving soon.");
		addD("speechbank.rivendell_wanderer.departure.8", "I will have to depart soon.");
		addD("speechbank.rivendell_wanderer.departure.9", "Do you hear the sea calling? I shall soon leave these lands.");

		// Add Fellowship Menu strings
		addD("gui.lotrextended.fellowships.title", "Middle-earth Fellowships");
		addD("gui.lotrextended.fellowships.creation.disabled", "Fellowship creation is disabled!");
		addD("gui.lotrextended.fellowships.use_command", "Please use the command for now!");

		// Add Settings Menu strings
		addD("gui.lotrextended.settings.title", "Middle-earth Options");
		addD("gui.lotrextended.settings.worldsettings", "World Settings");
		addD("gui.lotrextended.settings.usersettings", "User Settings");
		addD("gui.lotrextended.settings.devsettings", "Client Dev Settings");
		addD("gui.lotrextended.settings.friendlyFire.title", "Friendly Fire");
		addD("gui.lotrextended.settings.friendlyFire.setting", "Friendly Fire: ");
		addD("gui.lotrextended.settings.mapLocation.title", "Show Map Location");
		addD("gui.lotrextended.settings.mapLocation.setting", "Show Map Location: ");
		addD("gui.lotrextended.settings.rankGender.title", "Feminine Rank Override");
		addD("gui.lotrextended.settings.rankGender.setting", "Feminine Rank Override: ");
		addD("gui.lotrextended.settings.devtoggle.title", "Dev Mode");
		addD("gui.lotrextended.settings.devtoggle.setting", "Dev Mode: ");
		addD("gui.lotrextended.settings.fsbr.title", "Force StrBlock Air Rendering");
		addD("gui.lotrextended.settings.fsbr.setting", "Force StrBlock Air Rendering: ");
		addD("gui.lotrextended.settings.structureblockoutlineviewdistance.prefix", "StrBlock Outline Render Dist: ");
		addD("gui.lotrextended.settings.structureblockoutlineviewdistance.suffix", " blocks");

		// Horn Select strings
		addD("gui.lotrextended.horn_select.title", "Horn of Command Type Selection");
		addD("gui.lotrextended.horn_select.option_summon", "Summon Units");
		addD("gui.lotrextended.horn_select.option_halt_reddy", "Halt/Reddy Units");

		// Horn Type names
		addD("item.lotr.horn_of_command.halt");
		addD("item.lotr.horn_of_command.reddy");
		addD("item.lotr.horn_of_command.summon");

		//Bow values
		addD("gui.lotrextended.bow.speed", "Draw Speed: %s");
		addD("gui.lotrextended.bow.damage", "Ranged Damage: %s");
		addD("gui.lotrextended.bow.range", "Range: %s");

		// Command strings
		addD("command.lotrextended.info.listPatches", "Injected patches:");
		addD("command.lotrextended.fellowship.error", "Fellowship does not exist or you are missing permissions!");
		addD("command.lotrextended.fellowship.error.noexists", "No fellowship by that name exists!");
		addD("command.lotrextended.fellowship.error.exists", "Fellowship with the name [%s] already exists!");
		addD("command.lotrextended.fellowship.notexists", "No fellowship by that name exists!");
		addD("command.lotrextended.fellowship.player.added", "Added %s to the fellowship");
		addD("command.lotrextended.fellowship.player.removed", "Removed %s from the fellowship");
		addD("command.lotrextended.fellowship.player.invited", "Invited %s to the fellowship");
		addD("command.lotrextended.fellowship.player.setowner", "Transfered ownership of the fellowship to %s");
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
		addD("command.lotrextended.fellowship.data.name", "Fellowship Name: ");
		addD("command.lotrextended.fellowship.data.pvp", "PVP: ");
		addD("command.lotrextended.fellowship.data.hupvp", "Hired Units PVP: ");
		addD("command.lotrextended.fellowship.data.maploc", "MapLoccation: ");
		addD("command.lotrextended.fellowship.data.icon", "Icon: ");
		addD("command.lotrextended.fellowship.data.owner", "Owner: ");
		addD("command.lotrextended.fellowship.data.admins", "Admins: ");
		addD("command.lotrextended.fellowship.data.members", "Members: ");

		//Version lang strings
		addD("command.lotrextended.version.left", "A new version");
		addD("command.lotrextended.version.right", "was found");

		//Extended containers
		addD("container.lotrextended.bookshelf", "Bookshelf");
		addD("container.lotrextended.reed_basket", "Reed Basket");
		addD("container.lotrextended.stone_chest", "Stone Chest");
		addD("container.lotrextended.orc_barrel", "Orc Barrel");
		
	}

	@Override
	public String getName() {
		return "Renewed Extended Language Provider";
	}
}
