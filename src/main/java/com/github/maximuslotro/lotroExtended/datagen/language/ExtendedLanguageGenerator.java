package com.github.maximuslotro.lotroExtended.datagen.language;
import java.util.Map.Entry;

import com.github.maximuslotro.lotroExtended.datagen.DataGenValueHolder;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedLanguageGenerator extends ExtendedLanguageHelper {

	public ExtendedLanguageGenerator(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
	}

	@Override
	protected void makeTranslations() {
		// Try Add New Block Names
		for (Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			addBlock(block.getValue(), "");
		}

		// Try Add New Item Names
		for (Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			addItem(item.getValue(), "");
		}

		// Try Add New Entity Names
		for (String entity : ExtendedEntities.REGISTEREDENTITIES) {
			addD("entity.lotr." + entity);
			addD("item.lotr." + entity + "_spawn_egg");
		}

		// Try Add Container Titles
		for (String entity : DataGenValueHolder.containerTitlesHolders) {
			addD(entity);
		}

		// Try Add New Config Value Names
		for (Entry<String, String> entity : DataGenValueHolder.configCommentHolders.entrySet()) {
			addD(entity.getKey(), entity.getValue());
		}

		// Try Add New Subtitles
		for (String entity : DataGenValueHolder.soundEventHolder) {
			addD(entity);
		}

		// Add Menu strings
		addD("gui.lotrextended.menu.fellowships");
		addD("gui.lotrextended.menu.settings");
		addD("gui.lotrextended.menu.titles");
		addD("gui.lotrextended.menu.achievements");

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
	}

	@Override
	public String getName() {
		return "Renewed Extended Language Provider";
	}
}
