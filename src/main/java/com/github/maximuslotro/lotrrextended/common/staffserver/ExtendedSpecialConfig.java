package com.github.maximuslotro.lotrrextended.common.staffserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ExtendedSpecialConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Path CONFIG_DIR = Paths.get("config/modchecker/");
    private static final Path ALLOW_FILE = CONFIG_DIR.resolve("allowlist.json");
    private static final Path AUTOBAN_FILE = CONFIG_DIR.resolve("autoban.json");

    public static Set<String> allowList = new HashSet<>();
    public static Map<String, String> autoBanMap = new HashMap<>();

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
        return allowList.contains(modId.toLowerCase(Locale.ROOT));
    }

    public static boolean isAutoBan(String modId) {
        return autoBanMap.containsKey(modId.toLowerCase(Locale.ROOT));
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
}