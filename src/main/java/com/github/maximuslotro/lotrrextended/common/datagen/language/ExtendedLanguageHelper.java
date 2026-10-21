package com.github.maximuslotro.lotrrextended.common.datagen.language;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.LanguageMap;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ExtendedLanguageHelper implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final Map<String, String> dataHere = new TreeMap<>(NATURAL_ORDER);
	private final Map<String, String> newStrings = new HashMap<>();
	private final Map<String, String> overrideStrings = new HashMap<>();
	private final Map<String, String> existingStrings = new HashMap<>();
	private final List<String> removeStrings = new ArrayList<>();
	protected final DataGenerator genHere;
	private final ExistingFileHelper fileHelper;
	protected final String modidHere;
	protected final String localeHere;

	public ExtendedLanguageHelper(DataGenerator gen, ExistingFileHelper fileHelper, String modid, String locale) {
		this.genHere = gen;
		this.fileHelper = fileHelper;
		this.modidHere = modid;
		this.localeHere = locale;
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		addTranslations();
		if (!dataHere.isEmpty()) {
			Path targetEnglishPath = this.genHere.getOutputFolder().resolve("assets/" + modidHere + "/lang/" + localeHere + ".json");
			saveHere(cache, dataHere, targetEnglishPath);
			syncOtherLanguageFiles(cache, dataHere);
		}
	}

	private void saveHere(DirectoryCache cache, Object object, Path target) throws IOException {
		String data = GSON.toJson(object);
		String hash = IDataProvider.SHA1.hashString(data, StandardCharsets.UTF_8).toString();
		if(!Objects.equals(cache.getHash(target), hash) || !Files.exists(target)) {
			Files.createDirectories(target.getParent());
			try(BufferedWriter bufferedwriter = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
				bufferedwriter.write(data);
			}
		}
		cache.putNew(target, hash);
	}

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
		overrideStrings.put(key, name);
	}

	public void addOverrideRemove(String key) {
		removeStrings.add(key);
	}


	public void addBlock(Block key, String name) {
		addD(key.getDescriptionId(), name);
	}

	public void addItem(Item key, String name) {
		addD(key.getDescriptionId(), name);
	}

	public void addEffect(Effect key, String name) {
		addD(key.getDescriptionId(), name);
	}

	public void addEntityType(EntityType<?> key, String name) {
		addD(key.getDescriptionId(), name);
	}

	public void addD(String key) {
		addD(key, "");
	}

	public void addD(String key, String value) {
		if(!newStrings.containsKey(key)) {
			newStrings.put(key, value);
		} else {
			if(!value.isEmpty()) {
				System.out.println("Discarding Duplicate Key Found in Language Map With Initialised text: " + key);
			}
		}
	}

	public void addExistingLocalisationsToMap() {
		Builder<String, String> builder = ImmutableMap.builder();
		BiConsumer<String, String> biconsumer = builder::put;
		Path path = this.genHere.getOutputFolder().resolve("assets/" + modidHere + "/lang/" + localeHere + ".json");
		if(Files.exists(path)) {
			try(InputStream inputstream = Files.newInputStream(path)) {
				LanguageMap.loadFromJson(inputstream, biconsumer);
			} catch(JsonParseException | NullPointerException | IOException exception) {
				ExtendedLog.error("Couldn't read strings from " + path);
			}
		} else {
			try(InputStream inputstream = LanguageMap.class.getResourceAsStream("/assets/" + modidHere + "/lang/" + localeHere + ".json")) {
				if(inputstream != null) {
					LanguageMap.loadFromJson(inputstream, biconsumer);
				}
			} catch(JsonParseException | NullPointerException | IOException exception) {
				ExtendedLog.error("Couldn't read strings from classpath asset /assets/" + modidHere + "/lang/" + localeHere + ".json");
			}
		}
		Map<String, String> map;
		try {
			map = builder.build();
		} catch(IllegalArgumentException e) {
			map = new HashMap<>();
		}
		for(Entry<String, String> entry : map.entrySet()) {
			if(entry.getValue().isEmpty()) {
				continue;
			}
			if(entry.getKey().contains("container.lotr")) {
				continue;
			}
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
			if(entry.getKey().contains("command.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("gui.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("chat.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("block.minecraft.banner")) {
				continue;
			}
			if(entry.getKey().contains("speechbank.")) {
				continue;
			}
			if(entry.getKey().contains("block.lotr.large_pot_")) {
				continue;
			}
			if(entry.getKey().contains("enchant.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("message.lotrextended")) {
				continue;
			}
			if(entry.getKey().contains("banner_pattern.desc")) {
				continue;
			}
			if(entry.getKey().contains("entity_shortname.lotrextended.")||
				entry.getKey().contains("entity_unit_type.lotrextended.")||
				entry.getKey().contains("entity_title.lotrextended.")) {
				continue;
			}
			existingStrings.put(entry.getKey(), entry.getValue());
		}
	}

	public void makeLocalisationsFile() {
		// Check entries from existingStrings that do not exists in the mod
		List<String> unusedKeys = existingStrings.keySet().stream()
			.filter(key -> !newStrings.containsKey(key) && !overrideStrings.containsKey(key))
			.filter(key -> !key.startsWith("block.lotrextended") && !key.startsWith("item.lotrextended.morgul"))
			.collect(Collectors.toList());

		if(!unusedKeys.isEmpty()) {
			System.out.println("Found " + unusedKeys.size() + " unused lang entries");
			unusedKeys.forEach(existingStrings::remove);
		}
		//.forEach(key -> System.out.println("Found unused lang entry: " + key));
		// Skip keys containing 'placeholder'
		Map<String, String> combinedMap = new HashMap<>();
		newStrings.forEach((key, value) -> {
			if(!key.contains("placeholder")) {
				combinedMap.put(key, value);
			}
		});
		existingStrings.forEach((key, value) -> {
			if(!key.contains("placeholder")) {
				combinedMap.put(key, value);
			}
		});
		overrideStrings.forEach((key, value) -> {
			if(!key.contains("placeholder")) {
				combinedMap.put(key, value);
			}
		});
		for(Entry<String, String> entry : combinedMap.entrySet()) {
			if (this.dataHere.put(entry.getKey(), entry.getValue()) != null) {
				throw new IllegalStateException("Duplicate translation key " + entry.getKey());
			}
		}
		for(String str : removeStrings) {
			dataHere.remove(str);
		}
	}

	private void syncOtherLanguageFiles(DirectoryCache cache, Map<String, String> parentMap) throws IOException {
		Path root = this.genHere.getOutputFolder().getParent().getParent().getParent();
		if(root == null) {
			return;
		}
		Path mainLangDir = root.resolve("src/main/resources/assets/" + modidHere + "/lang");
		if(!Files.exists(mainLangDir) || !Files.isDirectory(mainLangDir)) {
			ExtendedLog.warn("Additional language files not found at {}!", mainLangDir.toString());
			return;
		}
		try(Stream<Path> stream = Files.list(mainLangDir)) {
			List<Path> localizationFiles = stream
				.filter(p -> p.toString().endsWith(".json"))
				.filter(p -> !p.getFileName().toString().equalsIgnoreCase(localeHere + ".json"))
				.collect(Collectors.toList());
			for(Path localizationFile : localizationFiles) {
				Map<String, String> loadedLocalizationMap = loadJsonToMap(localizationFile);
				Map<String, String> finalLocaliztionMap = new TreeMap<>(NATURAL_ORDER);
				for(Entry<String, String> entry : loadedLocalizationMap.entrySet()) {
					String key = entry.getKey();
					if(!parentMap.containsKey(key)) {
						if(key.startsWith("item.lotr.")) {
							key = key.replace("item.lotr.", "item." + modidHere + ".");
						} else if(key.startsWith("block.lotr.")) {
							key = key.replace("block.lotr.", "block." + modidHere + ".");
						}
					}
					if(parentMap.containsKey(key)) {
						finalLocaliztionMap.put(key, entry.getValue());
					}
				}
				saveHere(cache, finalLocaliztionMap, localizationFile);
			}
		}
	}
	private Map<String, String> loadJsonToMap(Path path) {
		Map<String, String> localizations = new LinkedHashMap<>();
		try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			JsonObject jsonObject = JSONUtils.fromJson(GSON, reader, JsonObject.class);
			if(jsonObject != null) {
				for(Entry<String, JsonElement> entry : jsonObject.entrySet()) {
					localizations.put(entry.getKey(), entry.getValue().getAsString());
				}
			}
		} catch(Exception e) {
			ExtendedLog.error("Could not parse target lang file: " + path);
		}
		return localizations;
	}

	public static final Comparator<String> NATURAL_ORDER = new Comparator<String>() {
        private final Pattern PATTERN = Pattern.compile("\\d+|\\D+");

        @Override
        public int compare(String s1, String s2) {
            Matcher m1 = PATTERN.matcher(s1);
            Matcher m2 = PATTERN.matcher(s2);
            while (m1.find() && m2.find()) {
                String token1 = m1.group();
                String token2 = m2.group();
                int result;
                if (Character.isDigit(token1.charAt(0)) && Character.isDigit(token2.charAt(0))) {
                    result = Integer.compare(Integer.parseInt(token1), Integer.parseInt(token2));
                } else {
                    result = token1.compareTo(token2);
                }
                if (result != 0) {
                    return result;
                }
            }
            return Integer.compare(s1.length(), s2.length());
        }
    };
}