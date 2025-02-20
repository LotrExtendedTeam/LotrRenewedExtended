package com.github.maximuslotro.lotrrextended.common.codex;

import java.io.File;

import net.minecraft.entity.player.ServerPlayerEntity;

public class ExtendedServerPlayerCodex {
	private final ExtendedCodexServerPlayerList codexServerList;
	private final File codexFile;
	private ServerPlayerEntity serverPlayer;

	public ExtendedServerPlayerCodex(ExtendedCodexServerPlayerList codexServerList, ExtendedCodexManager codexManager, File codexFile, ServerPlayerEntity serverPlayer) {
		this.codexServerList = codexServerList;
		this.codexFile = codexFile;
		this.serverPlayer = serverPlayer;
	}

	public void setServerPlayer(ServerPlayerEntity serverPlayer) {
		this.serverPlayer = serverPlayer;
	}

	public void reload(ExtendedCodexManager pManager) {
		
	}

	public void save() {

	}

	public void stopListening() {

	}
}
