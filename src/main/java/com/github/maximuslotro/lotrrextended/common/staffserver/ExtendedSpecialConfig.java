package com.github.maximuslotro.lotrrextended.common.staffserver;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.authlib.GameProfile;

import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedSpecialConfig {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger LOGGER = LogManager.getLogger();

	private static final Path CONFIG_DIR = Paths.get("config/modchecker/");
	private static final Path ALLOW_FILE = CONFIG_DIR.resolve("allowlist.json");
	private static final Path AUTOBAN_FILE = CONFIG_DIR.resolve("autoban.json");
	private static final Path LOG_FILE = CONFIG_DIR.resolve("detections.log");

	public static Set<String> allowList = new HashSet<>();
	public static Map<String, String> autoBanMap = new HashMap<>();
	private static final Map<UUID, Set<String>> playerModCache = new HashMap<>();

	// Default messages
	private static final String DEFAULT_BAN_MSG = "You have been banned for using a forbidden mod!";

	public static void loadAll() {
		try {
			Files.createDirectories(CONFIG_DIR);

			// Load allowlist
			if (!Files.exists(ALLOW_FILE)) {
				allowList.addAll(Arrays.asList("minecraft", "forge"));
				saveJson(ALLOW_FILE, allowList);
			} else {
				Type setType = new TypeToken<Set<String>>() {}.getType();
				allowList = GSON.fromJson(Files.newBufferedReader(ALLOW_FILE), setType);
			}

			// Load autoban
			if (!Files.exists(AUTOBAN_FILE)) {
				saveJson(AUTOBAN_FILE, new HashMap<String, String>());
			} else {
				Type mapType = new TypeToken<Map<String, String>>() {}.getType();
				autoBanMap = GSON.fromJson(Files.newBufferedReader(AUTOBAN_FILE), mapType);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveAll() {
		try {
			Files.createDirectories(CONFIG_DIR);
			saveJson(ALLOW_FILE, allowList);
			saveJson(AUTOBAN_FILE, autoBanMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveJson(Path path, Object obj) throws IOException {
		Files.write(path, GSON.toJson(obj).getBytes());
	}

	public static boolean isAllowed(String modId) {
		return !ExtendedServerConfig.enableModWhiteList.get() || allowList.contains(modId.toLowerCase(Locale.ROOT));
	}

	public static boolean isAutoBan(String modId) {
		return autoBanMap.containsKey(modId.toLowerCase(Locale.ROOT));
	}

	public static void onPlayerLoggout(UUID pPlayerID) {
		playerModCache.remove(pPlayerID);
	}

	public static String getKickMessage(List<String> mods) {
		Collections.sort(mods);
		return "The following mods are not allowed on this server: ["
		+ String.join(", ", mods)
		+ "] If you think any of these are a mistake, please contact the server administrator.";
	}

	public static String getAutoBanMessage(String modId) {
		return autoBanMap.getOrDefault(modId.toLowerCase(Locale.ROOT), DEFAULT_BAN_MSG);
	}

	public static ITextComponent handleModsList(NetworkManager connection, MinecraftServer server, GameProfile gameProfile, List<String> mods, boolean logDiffrences) {
		UUID uuid = gameProfile.getId();
		// Normalize incoming mods
		Set<String> currentMods = mods.stream()
			.map(mod -> mod.toLowerCase(Locale.ROOT))
			.collect(Collectors.toSet());

		if (!playerModCache.containsKey(uuid)) {
			playerModCache.put(uuid, new HashSet<>(currentMods));
			List<String> disallowedMods = new ArrayList<>();
			for (String mod : currentMods) {
				String modId = mod.toLowerCase(Locale.ROOT);
				if (ExtendedSpecialConfig.isAutoBan(modId)) {
					String msg = ExtendedSpecialConfig.getAutoBanMessage(modId);
					logToFile("[ModChecker][AUTOBAN] " + gameProfile.getName() + " (" + uuid + ")  triggered autoban for mod: " + modId);
					server.getPlayerList().getBans().add(new ProfileBanEntry(gameProfile, null, "AntiCheat", null, msg));
					return new StringTextComponent(msg);

				}
				// Collect disallowed mods instead of kicking immediately
				if (!ExtendedSpecialConfig.isAllowed(modId)) {
					disallowedMods.add(modId);
				}
			}
			// Kick once, listing all disallowed mods
			if (!disallowedMods.isEmpty()) {
				logToFile("[ModChecker][DISALLOWED] " + gameProfile.getName() + " (" + uuid + ")  has disallowed mods on join: " + disallowedMods);
				String msg = ExtendedSpecialConfig.getKickMessage(disallowedMods);
				return new StringTextComponent(msg);
			}
			return null;
		}
		if (logDiffrences) {
			Set<String> oldMods = playerModCache.get(uuid);

			Set<String> added = new HashSet<>(currentMods);
			added.removeAll(oldMods);

			Set<String> removed = new HashSet<>(oldMods);
			removed.removeAll(currentMods);

			if (!added.isEmpty() || !removed.isEmpty()) {
				String msg = "[ModChecker][SUSPICIOUS] " + gameProfile.getName() + " (" + uuid + ") mod mismatch | Added: " + added + " | Removed: " + removed;
				logToFile(msg);
			}
		}

		// Never enforce on second list to be able to mass ban cheaters later
		return null;
	}

	private static void logToFile(String message) {
		LOGGER.warn(message);
		try {
			Files.write(LOG_FILE, (message + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE,  StandardOpenOption.APPEND
				);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}