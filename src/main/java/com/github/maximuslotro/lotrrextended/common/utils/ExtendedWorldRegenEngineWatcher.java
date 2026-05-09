package com.github.maximuslotro.lotrrextended.common.utils;

import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSWorldPruneProgress;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.server.ServerBossInfo;

public class ExtendedWorldRegenEngineWatcher {
	public static ExtendedWorldRegenEngine ACTIVE_ENGINE = null;
	public static UUID ACTIVE_PLAYER = null;
	public static ServerBossInfo ACTIVE_BAR = null;
	public static MinecraftServer server;

	public static boolean startWorldPrune(MinecraftServer pServer, ServerPlayerEntity player, boolean isCommand, boolean dryRun) {
		// Kick all other players
		for (ServerPlayerEntity other : pServer.getPlayerList().getPlayers()) {
			if (!other.getUUID().equals(player.getUUID())) {
				other.connection.disconnect(new StringTextComponent("Server is pruning world"));
			}
		}
		if (ExtendedWorldRegenEngineWatcher.ACTIVE_ENGINE != null && !ExtendedWorldRegenEngineWatcher.ACTIVE_ENGINE.isFinished()) {
			return false;
		}
		server = pServer;
		ACTIVE_PLAYER = player.getUUID();
		ACTIVE_ENGINE = new ExtendedWorldRegenEngine(dryRun);

		// Start command progress thread
		startProgressWatcher(server);

		ExtendedWorldRegenEngineWatcher.ACTIVE_BAR = new ServerBossInfo( new StringTextComponent("Starting prune..."),  Color.BLUE, Overlay.PROGRESS);
		if (player != null) {
			ExtendedWorldRegenEngineWatcher.ACTIVE_BAR.addPlayer(player);
		}

		return true;

	}

	public static void stopWorldPrune() {
		if (ExtendedWorldRegenEngineWatcher.ACTIVE_ENGINE == null || ExtendedWorldRegenEngineWatcher.ACTIVE_ENGINE.isFinished()) {
			return;
		}
		ExtendedWorldRegenEngineWatcher.ACTIVE_ENGINE.cancel();
	}
	public static void startProgressWatcher(MinecraftServer server) {
		new Thread(() -> {
			ITextComponent lastStatus = null;
			if(ACTIVE_ENGINE==null) {
				return;
			}
			while (ACTIVE_ENGINE!=null && !ACTIVE_ENGINE.isFinished()) {

				ServerPlayerEntity player = server.getPlayerList().getPlayer(ACTIVE_PLAYER);

				if (player == null) {
					continue;
				}

				// Ensure player still has the bar
				if (ACTIVE_BAR != null && !ACTIVE_BAR.getPlayers().contains(player)) {
					ACTIVE_BAR.addPlayer(player);
				}
				ExtendedWorldRegenEngine.Stage stage = ACTIVE_ENGINE.getStage();
				float progress = 0f;
				TranslationTextComponent text;

				if (stage == ExtendedWorldRegenEngine.Stage.SCANNING_CHUNKS) {
					int chunks = ACTIVE_ENGINE.getTotalChunks();
					int currentRegions = ACTIVE_ENGINE.getCurrentRegions();
					int totalRegions = ACTIVE_ENGINE.getTotalRegions();
					text = new TranslationTextComponent("gui.lotrextended.worldprune.scanning", currentRegions, totalRegions, chunks);
					progress = 1.0f;
				}else if (stage == ExtendedWorldRegenEngine.Stage.DELETING_REGIONS) {
					int regions = ACTIVE_ENGINE.getTotalRegions();
					int done = ACTIVE_ENGINE.getDeletedRegions() + ACTIVE_ENGINE.getProtectedRegions() + ACTIVE_ENGINE.getSkippedLoadedRegions();
					int percent = 0;
					if (regions > 0) {
						progress = (float) done / regions;
						percent = (int)(progress * 100);
					}
					text = new TranslationTextComponent("gui.lotrextended.worldprune.processing", percent, done, regions);

				} else {
					int chunks = ACTIVE_ENGINE.getTotalChunks();
					int done = ACTIVE_ENGINE.getDeletableChunks() + ACTIVE_ENGINE.getBannerProtected() + ACTIVE_ENGINE.getWaypointProtected() + ACTIVE_ENGINE.getSpawnProtected() + ACTIVE_ENGINE.getErrorProtected();
					int percent = 0;
					if (chunks > 0) {
						progress = (float) done / chunks;
						percent = (int)(progress * 100);
					}
					text = new TranslationTextComponent("gui.lotrextended.worldprune.checking", percent, done, chunks);
				}

				float finalProgress = progress;
				TranslationTextComponent finalText = text;

				if (ACTIVE_BAR != null) {
					ACTIVE_BAR.setName(finalText);
					ACTIVE_BAR.setPercent(finalProgress);
				}
				sendPruneDataPacket(player);
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException ignored) {}
			}
			if (ACTIVE_BAR != null) {
				ACTIVE_BAR.removeAllPlayers();
				ACTIVE_BAR = null;
			}
			ServerPlayerEntity player = server.getPlayerList().getPlayer(ACTIVE_PLAYER);
			sendPruneDataPacket(player);
			if (player != null) {
				if(ACTIVE_ENGINE.isDryRun()) {
					player.sendMessage(new StringTextComponent("[Dry Run Mode] Would have pruned ["+ACTIVE_ENGINE.getDeletedRegions()+"] region files"), Util.NIL_UUID);
					player.sendMessage(new StringTextComponent("While skipping ["+ACTIVE_ENGINE.getProtectedRegions()+"] region files"), Util.NIL_UUID);
				}else {
					player.sendMessage(new StringTextComponent("Pruning complete! Deleted ["+ACTIVE_ENGINE.getDeletedRegions()+"] region files"), Util.NIL_UUID);
					player.sendMessage(new StringTextComponent("Skipped ["+ACTIVE_ENGINE.getProtectedRegions()+"] protected region files"), Util.NIL_UUID);
					player.sendMessage(new StringTextComponent("Skipped ["+ACTIVE_ENGINE.getSkippedLoadedRegions()+"] loaded region files"), Util.NIL_UUID);

				}
			}
			ACTIVE_ENGINE = null;
			ACTIVE_PLAYER = null;
		}).start();
	}

	public static void sendPruneDataPacket(ServerPlayerEntity player) {
		if(player!=null && !player.hasDisconnected()) {
			ExtendedPacketHandler.sendToClient(new ExtendedSWorldPruneProgress(ACTIVE_ENGINE), player);
		}
	}
}
