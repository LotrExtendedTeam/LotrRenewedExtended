package com.github.maximuslotro.lotrrextended.common.codex;

import java.io.File;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.entity.player.ServerPlayerEntity;

/**
 * Codex implementation of {@Link PlayerAdvancements}'s logic 
 * */
public class ExtendedPlayerCodexes {
	private final ExtendedCodexServerPlayerList codexServerList;
	private final File codexFile;
	private ServerPlayerEntity serverPlayer;
	  private final Map<ExtendedCodexEntry, ExtendedCodexProgress> advancements = Maps.newLinkedHashMap();
	private final Set<ExtendedCodexEntry> visible = Sets.newLinkedHashSet();
	private final Set<ExtendedCodexEntry> visibilityChanged = Sets.newLinkedHashSet();
	private final Set<ExtendedCodexEntry> progressChanged = Sets.newLinkedHashSet();
	private boolean isFirstPacket = true;

	public ExtendedPlayerCodexes(ExtendedCodexServerPlayerList codexServerList, ExtendedCodexManager codexManager, File codexFile, ServerPlayerEntity serverPlayer) {
		this.codexServerList = codexServerList;
		this.codexFile = codexFile;
		this.serverPlayer = serverPlayer;
	}

	public void setServerPlayer(ServerPlayerEntity serverPlayer) {
		this.serverPlayer = serverPlayer;
	}

	public void reload(ExtendedCodexManager pManager) {
		this.advancements.clear();
		this.visible.clear();
		this.visibilityChanged.clear();
		this.progressChanged.clear();
		this.isFirstPacket = true;
	}

	public void save() {

	}

	public boolean award(ExtendedCodexEntry pAdvancement, String pCriterionKey) {
		return false;
	}

	public void stopListening() {

	}

	public void flushDirty(ServerPlayerEntity pServerPlayer) {
		
	}
}
