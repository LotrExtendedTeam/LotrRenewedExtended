package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.util.Map.Entry;

import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


public class ExtendedPlayerInfo {
	private static final File profileFolder = new File(ServerLifecycleHooks.getCurrentServer().getServerDirectory(), "world/lotrextended/playerdata");
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(InstantTypeAdapter.class, new InstantTypeAdapter()).create();
	private static HashMap<UUID, ExtendedPlayerInfo> cachedPlayerInfo = new HashMap<>();

	private final UUID id;

	private Instant firstLogin = Instant.now();

	private Instant lastLogout;

	private boolean combatLoggedLastSession = false;

	private boolean jailed = false;

	private ExtendedPlayerInfo(UUID uuid) {
		this.id = uuid;
	}

	public Instant getFirstLogin() {
		return firstLogin;
	}

	public Instant getLastLogout() {
		return lastLogout;
	}

	public void setCombatLogged(boolean value) {
		this.combatLoggedLastSession = value;
	}

	public boolean didCombatLog() {
		return combatLoggedLastSession;
	}

	public void clearCombatLogFlag() {
		this.combatLoggedLastSession = false;
	}

	public void sendToJail(boolean jail) {
		jailed = jail;
	}

	public boolean isInJail() {
		return jailed;
	}

	public static boolean doesPlayerInfoExist(@Nullable UUID id) {
		if (id == null) {
			return false;
		}
		if(cachedPlayerInfo.containsKey(id)) {
			return true;
		}
		return getProfileFile(id).exists();
	}

	public static ExtendedPlayerInfo getPlayerInfo(@Nonnull UUID id) {
		if(cachedPlayerInfo.containsKey(id)) {
			return cachedPlayerInfo.get(id);
		}
		File profileFile = getProfileFile(id);
		ExtendedPlayerInfo info = null;
		if(profileFile.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(profileFile))) {
				info = gson.fromJson(br, ExtendedPlayerInfo.class);
			}
			catch (JsonParseException | IOException e) {
				ExtendedLog.info("Error loading playerData "+profileFile.getAbsolutePath());
				e.printStackTrace();
			}
		}
		// Catch gson also returning a null value
		if (info == null) {
			info = new ExtendedPlayerInfo(id);
		}
		cachedPlayerInfo.put(id, info);
		return info;
	}

	public static void clear(UUID id) {
		ExtendedPlayerInfo info = cachedPlayerInfo.remove(id);
		if(info!=null) {
			// Save on clear in the case something was not saved for some reason
			info.savePlayerInfo();
		}
	}

	public void savePlayerInfo() {
		File profileFile = getProfileFile(id);
		try (FileWriter out = new FileWriter(profileFile)) {
			gson.toJson(this, out);
		}
		catch (RuntimeException | Error | IOException e) {
			ExtendedLog.info("Error saving playerData "+profileFile.getAbsolutePath());
		}
	}

	public static void login(UUID id) {
		ExtendedPlayerInfo info = getPlayerInfo(id);
		// Just need to create on login for first join purposes
		info.savePlayerInfo();
	}

	public static void logout(UUID uuid) {
		ExtendedPlayerInfo info = getPlayerInfo(uuid);
		info.lastLogout = Instant.now();
		info.savePlayerInfo();
		cachedPlayerInfo.remove(uuid); // Remove from map for memory saving purposes
	}

	public static File getProfileFile(@Nonnull UUID id) {
		profileFolder.mkdirs();
		return new File(profileFolder, id.toString() + ".json");
	}

	public static class InstantTypeAdapter extends TypeAdapter<Instant> {

		@Override
		public void write(JsonWriter out, Instant value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else {
				out.value(value.toString()); // ISO-8601
			}
		}

		@Override
		public Instant read(JsonReader in) throws IOException {
			if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			return Instant.parse(in.nextString());
		}
	}

	@Nullable
	public static UUID getPlayerUUIDFromUsername(String username) {
		UUID id = null;
		for(Entry<UUID, String> profile : UsernameCache.getMap().entrySet()) {
			if(profile.getValue().equals(username)) {
				id = profile.getKey();
				break;
			}
		}
		return id;
	}
}
