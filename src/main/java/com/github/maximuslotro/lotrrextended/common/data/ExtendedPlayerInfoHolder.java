package com.github.maximuslotro.lotrrextended.common.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;


public class ExtendedPlayerInfoHolder extends ExtendedAbstractLevelDataHolder {
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(InstantTypeAdapter.class, new InstantTypeAdapter()).create();
	private final Map<UUID, ExtendedPlayerInfo> cachedPlayerInfo = new HashMap<>();
	private File playerFolder;

	public ExtendedPlayerInfoHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void loadDataServer(CompoundNBT pTag, MinecraftServer server) {
		playerFolder = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "playerdata"));
		cachedPlayerInfo.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag) {
		for (ExtendedPlayerInfo pd : cachedPlayerInfo.values()) {
			savePlayerInfo(pd);
		}
	}

	@Override
	protected void clearData() {
		cachedPlayerInfo.clear();
		playerFolder=null;
	}

	@Override
	public void tick(ServerWorld level) {}

	public boolean doesPlayerInfoExist(@Nullable UUID id) {
		if (id == null) {
			return false;
		}
		if(cachedPlayerInfo.containsKey(id)) {
			return true;
		}
		return getProfileFile(id).exists() || getProfileFileOLD(id).exists();
	}

	public ExtendedPlayerInfo getPlayerInfo(@Nonnull UUID playerUUID) {
		return cachedPlayerInfo.computeIfAbsent(playerUUID, this::loadPlayerData);
	}

	private @Nonnull ExtendedPlayerInfo loadPlayerData(UUID playerUUID) {
		File datFile = getProfileFile(playerUUID);
		if(datFile.exists()) {
			CompoundNBT nbt = ExtendedIOUtils.load(datFile);
			if (nbt.isEmpty()) {
				return new ExtendedPlayerInfo(playerUUID);
			}
			return ExtendedPlayerInfo.load(nbt);
		}
		File jsonFile = getProfileFileOLD(playerUUID);
		if(jsonFile.exists()) {
			ExtendedPlayerInfo info = null;
			try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
				info = GSON.fromJson(br, ExtendedPlayerInfo.class);
			} catch (JsonParseException | IOException e) {
				ExtendedLog.warn("Error loading legacy playerData for {} ", jsonFile.getAbsolutePath(), e);
			}
			if (info != null) {
				savePlayerInfo(info);
				ExtendedLog.info("Migrated old playerData {} from .json to .dat", playerUUID);
				try {
					Files.deleteIfExists(jsonFile.toPath());
				} catch (IOException e) {
					ExtendedLog.warn("Failed to delete legacy playerData file {}", jsonFile.getAbsolutePath(), e);
				}
				return info;
			}
		}
		return new ExtendedPlayerInfo(playerUUID);
	}

	//Used for query commands that can retrieve data when player is offline, call this to ensure it exits memory
	public void unload(UUID id) {
		ExtendedPlayerInfo info = cachedPlayerInfo.remove(id);
		if(info!=null) {
			savePlayerInfo(info);// Save on unload in the case something was not saved for some reason
		}
	}

	public void savePlayerInfo(ExtendedPlayerInfo info) {
		ExtendedIOUtils.save(getProfileFile(info.getId()), info.save());
	}

	public void login(@Nonnull UUID id) {
		ExtendedPlayerInfo info = getPlayerInfo(id);
		savePlayerInfo(info); // Just need to create on login for first join purposes
	}

	public void logout(@Nonnull UUID uuid) {
		setLastLogout(uuid, Instant.now());
		cachedPlayerInfo.remove(uuid); // Remove from map for memory saving purposes
	}

	public File getProfileFile(@Nonnull UUID id) {
		return new File(playerFolder, id.toString() + ".dat");
	}
	public File getProfileFileOLD(@Nonnull UUID id) {
		return new File(playerFolder, id.toString() + ".json");
	}

	// Saving handlers
	public void setLastLogout(@Nonnull UUID uuid, Instant lastLogout) {
		ExtendedPlayerInfo info = getPlayerInfo(uuid);
		info.setLastLogout(lastLogout);
		savePlayerInfo(info);
	}

	public void setCombatLogged(@Nonnull UUID uuid, boolean value) {
		ExtendedPlayerInfo info = getPlayerInfo(uuid);
		info.setCombatLogged(value);
		savePlayerInfo(info);
	}

	public void setJailed(@Nonnull UUID uuid, boolean jailed) {
		ExtendedPlayerInfo info = getPlayerInfo(uuid);
		info.setJailed(jailed);
		savePlayerInfo(info);
	}

	public static class InstantTypeAdapter extends TypeAdapter<Instant> {
		@Override
		public void write(JsonWriter out, Instant value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else {
				out.value(value.toString());
			}
		}
		@Override
		public Instant read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			return Instant.parse(in.nextString());
		}
	}
}
