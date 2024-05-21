package com.github.maximuslotro.lotrrextended.common.datagen.language;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonParseException;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.LanguageMap;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class ExtendedLanguageHelper extends LanguageProvider {

	protected Map<String, String> newStrings = new TreeMap<>();
	protected Map<String, String> overrideStrings = new TreeMap<>();
	protected Map<String, String> existingStrings = new TreeMap<>();
	protected final String modidHere;
	protected final String localeHere;

	public ExtendedLanguageHelper(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
		this.modidHere = modid;
		this.localeHere = locale;
	}

	@Override
	protected void addTranslations() {
		// Load From Existing File
		addExistingLocalisationsToMap();

		// Add dynamic and hardcoded keys
		makeTranslations();

		// Save all processed Translations
		makeLocalisationsFile();
	}

	protected abstract void makeTranslations();

	public void addOverride(String key, String name) {
		existingStrings.put(key, name);
	}

	@Override
	public void add(Block key, String name) {
		addD(key.getDescriptionId(), name);
	}

	@Override
	public void add(Item key, String name) {
		addD(key.getDescriptionId(), name);
	}

	@Override
	public void add(ItemStack key, String name) {
		addD(key.getDescriptionId(), name);
	}

	@Override
	public void add(Enchantment key, String name) {
		addD(key.getDescriptionId(), name);
	}

	@Override
	public void add(Effect key, String name) {
		addD(key.getDescriptionId(), name);
	}

	public void addD(String key) {
		addD(key, "");
	}

	public void addD(String key, String value) {
		if (!existingStrings.containsKey(key)) {
			if (!newStrings.containsKey(key)) {
				newStrings.put(key, value);
			} else {
				System.out.println("Discarding Duplicate Key Found in Language Map: " + key);
			}
		}
	}

	public void addExistingLocalisationsToMap() {
		Builder<String, String> builder = ImmutableMap.builder();
		BiConsumer<String, String> biconsumer = builder::put;
		try (InputStream inputstream = LanguageMap.class
				.getResourceAsStream("/assets/" + modidHere + "/lang/" + localeHere + ".json")) {
			LanguageMap.loadFromJson(inputstream, biconsumer);
		} catch (JsonParseException | NullPointerException | IOException exception) {
			ExtendedLog.error("Couldn't read strings from /assets/" + modidHere + "/lang/" + localeHere + ".json");
			exception.printStackTrace();
			return;
		}
		final Map<String, String> map = new java.util.HashMap<>(builder.build());
		for (Entry<String, String> entry : map.entrySet()) {
			if(entry.getKey().contains("stat.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("config.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("subtitles.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("advancements.lotrextended")) {
				continue;
			}
			existingStrings.put(entry.getKey(), entry.getValue());
		}
	}

	public void makeLocalisationsFile() {
		// Sort keys and add to superClass processor
		TreeMap<String, String> sorted = new TreeMap<>();
		sorted.putAll(newStrings);
		sorted.putAll(existingStrings);
		sorted.putAll(overrideStrings);
		for (Entry<String, String> entry : sorted.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}
	}
}
