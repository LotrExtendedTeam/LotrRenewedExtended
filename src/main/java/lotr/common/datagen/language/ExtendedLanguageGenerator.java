package lotr.common.datagen.language;

import java.util.TreeMap;
import java.util.Map.Entry;

import lotr.common.datagen.DataGenValueHolder;
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
	protected void addTranslations() {
		//Load From Existing File
		addExistingLocalisationsToMap();

		//Try Add New Block Names
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			addBlock(block.getValue(), "");
		}

		//Try Add New Item Names
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			addItem(item.getValue(), "");
		}

		//Try Add New Entity Names
		for(String entity : ExtendedEntities.REGISTEREDENTITIES) {
			addD("entity.lotr."+entity);
			addD("item.lotr."+entity+"_spawn_egg");
		}

		// Try Add Container Titles
		for (String entity : DataGenValueHolder.containerTitlesHolders) {
			addD(entity);
		}

		// Try Add New Config Value Names
		for (String entity : DataGenValueHolder.configCommentHolders) {
			addD(entity);
		}

		// Try Add New Subtitles
		for (String entity : DataGenValueHolder.soundEventHolder) {
			addD(entity);
		}
		
		//Sort keys and add to superClass processor
		TreeMap<String, String> sorted = new TreeMap<>();
		sorted.putAll(dataPrivate);
		for(Entry<String, String> entry : sorted.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public String getName() {
		return "Renewed Extended Language Provider";
	}
}
