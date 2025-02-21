package com.github.maximuslotro.lotrrextended.common.codex;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.LotrExtendedModServer;
import com.github.maximuslotro.lotrrextended.common.extensions.entity.player.ExtendedServerPlayerEntity;
import com.google.common.collect.Maps;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

/**
 * Codex implementation of {@Link PlayerList}'s advancements logic 
 * */
public class ExtendedCodexServerPlayerList {
	public static final FolderName PLAYER_CODEXES_DIR = new FolderName("lotrextended/codexes");
	private Map<UUID, ExtendedPlayerCodexes> codexes = Maps.newHashMap();

	public void codexPlayerLoggedOut(PlayerLoggedOutEvent event) {
		if(event.getPlayer() instanceof ServerPlayerEntity){
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity)event.getPlayer();
			save(serverPlayer);
			((ExtendedServerPlayerEntity)serverPlayer).getPlayerCodexes().stopListening();
			codexes.remove(event.getPlayer().getUUID());
		}
	}

	public void codexDatapackSync(OnDatapackSyncEvent event) {
		if(event.getPlayer()==null){
			saveAll();
			for(ExtendedPlayerCodexes playerCodex : this.codexes.values()) {
				playerCodex.reload(LotrExtendedModServer.getCodexManager());
			}
		}
	}

	public ExtendedPlayerCodexes getPlayerCodex(ServerPlayerEntity serverPlayer) {
		UUID uuid = serverPlayer.getUUID();
		ExtendedPlayerCodexes playeradvancements = codexes.get(uuid);
		if (playeradvancements == null) {
			File file1 = ServerLifecycleHooks.getCurrentServer().getWorldPath(PLAYER_CODEXES_DIR).toFile();
			File file2 = new File(file1, uuid + ".json");
			playeradvancements = new ExtendedPlayerCodexes(this, LotrExtendedModServer.getCodexManager(), file2, serverPlayer);
			this.codexes.put(uuid, playeradvancements);
		}

		if (!(serverPlayer instanceof net.minecraftforge.common.util.FakePlayer)) {
			playeradvancements.setServerPlayer(serverPlayer);
		}
		return playeradvancements;
	}

	protected void save(ServerPlayerEntity pPlayer) {
		if (pPlayer.connection == null) {
			return;
		}
		ExtendedPlayerCodexes playeradvancements = this.codexes.get(pPlayer.getUUID());
		if (playeradvancements != null) {
			playeradvancements.save();
		}
	}

	public void saveAll() {
		for(ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
			this.save(player);
		}
	}
}