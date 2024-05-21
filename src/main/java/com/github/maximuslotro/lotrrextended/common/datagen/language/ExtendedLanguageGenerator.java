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
		addD("gui.lotrextended.menu.fellowships");
		addD("gui.lotrextended.menu.settings");
		addD("gui.lotrextended.menu.titles");
		addD("gui.lotrextended.menu.achievements");

		// trade system
		addD("gui.lotrextended.trading.trade_button");
		addD("gui.lotrextended.trading.trader_busy");
		addD("gui.lotrextended.trading.trader_alignment_too_low");
		addD("gui.lotrextended.trading.talk_button");
		addD("gui.lotrextended.trading.exchange_button");
		addD("gui.lotrextended.trading.buy");
		addD("gui.lotrextended.trading.sell");
		addD("gui.lotrextended.trading.sell_offers");

		// Add Fellowship Menu strings
		addD("gui.lotrextended.fellowships.title");
		addD("gui.lotrextended.fellowships.creation.disabled");

		// Add Settings Menu strings
		addD("gui.lotrextended.settings.title");
		addD("gui.lotrextended.settings.worldsettings");
		addD("gui.lotrextended.settings.usersettings");
		addD("gui.lotrextended.settings.friendlyFire");
		addD("gui.lotrextended.settings.friendlyFire.enabled");
		addD("gui.lotrextended.settings.friendlyFire.disabled");
		addD("gui.lotrextended.settings.mapLocation");
		addD("gui.lotrextended.settings.mapLocation.enabled");
		addD("gui.lotrextended.settings.mapLocation.disabled");
		addD("gui.lotrextended.settings.rankGender");
		addD("gui.lotrextended.settings.rankGender.enabled");
		addD("gui.lotrextended.settings.rankGender.disabled");

		// Horn Select strings
		addD("gui.lotrextended.horn_select.title");
		addD("gui.lotrextended.horn_select.option_summon");
		addD("gui.lotrextended.horn_select.option_halt_reddy");
		// Horn Type names
		addD("item.lotr.horn_of_command.halt");
		addD("item.lotr.horn_of_command.reddy");
		addD("item.lotr.horn_of_command.summon");

		// Command strings
		addD("command.lotrextended.info.listPatches");
		addD("command.lotrextended.structureSummon.success");
		addD("command.lotrextended.structureSummon.failure");

		// Bookshelf
		addD("gui.lotrextended.bookshelf.title");
	}

	@Override
	public String getName() {
		return "Renewed Extended Language Provider";
	}
}
