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
		for (String entity : ExtendedEntities.REGISTEREDMOBENTITIES) {
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
