package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedWorldRegenEngine;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.server.ServerBossInfo;

public class ExtendedPruneWorldCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedPrune")
			.requires(cs -> cs.hasPermission(4))
			.then((Commands.argument("dryRun", BoolArgumentType.bool())
				.executes(ctx -> pruneWorld(ctx.getSource(), BoolArgumentType.getBool(ctx, "dryRun")))))
			);
	}

	static ExtendedWorldRegenEngine ACTIVE_ENGINE = null;
	private static UUID ACTIVE_PLAYER = null;
	private static ServerBossInfo ACTIVE_BAR = null;

	private static int pruneWorld(CommandSource source, boolean dryRun) {
		MinecraftServer server = source.getServer();
		ServerPlayerEntity player;

		try {
			player = source.getPlayerOrException();
		} catch (Exception e) {
			source.sendFailure(new StringTextComponent("Must be a player"));
			return 0;
		}

		// Prevent multiple runs
		if (ACTIVE_ENGINE != null && !ACTIVE_ENGINE.isFinished()) {
			source.sendFailure(new StringTextComponent("Prune already running"));
			return 0;
		}

		// Kick all other players
		for (ServerPlayerEntity other : server.getPlayerList().getPlayers()) {
			if (!other.getUUID().equals(player.getUUID())) {
				other.connection.disconnect(new StringTextComponent("Server is pruning world"));
			}
		}

		// Save who started it
		ACTIVE_PLAYER = player.getUUID();

		// Start engine
		ACTIVE_ENGINE = new ExtendedWorldRegenEngine(dryRun);

		source.sendSuccess(new StringTextComponent("Started world pruning..."), true);

		// Start progress thread
		startProgressWatcher(server);

		ACTIVE_BAR = new ServerBossInfo( new StringTextComponent("Starting prune..."),  Color.BLUE, Overlay.PROGRESS);
		if (player != null) {
			ACTIVE_BAR.addPlayer(player);
		}

		return Command.SINGLE_SUCCESS;
	}

	private static void startProgressWatcher(MinecraftServer server) {
		new Thread(() -> {
			ITextComponent lastStatus = null;

			while (ACTIVE_ENGINE != null && !ACTIVE_ENGINE.isFinished()) {

				ServerPlayerEntity player = server.getPlayerList().getPlayer(ACTIVE_PLAYER);

				if (player == null) {
					break;
				}

				// Ensure player still has the bar
				if (ACTIVE_BAR != null && !ACTIVE_BAR.getPlayers().contains(player)) {
					ACTIVE_BAR.addPlayer(player);
				}
				ExtendedWorldRegenEngine.Stage stage = ACTIVE_ENGINE.getStage();
				float progress = 0f;
				String text;

				if (stage == ExtendedWorldRegenEngine.Stage.SCANNING_CHUNKS) {
					int chunks = ACTIVE_ENGINE.getTotalChunks();
					int regions = ACTIVE_ENGINE.getTotalRegions();
					text = "Scanning " + regions + " region files (" + chunks + " chunks discovered)";
					progress = 1.0f;
				}else if (stage == ExtendedWorldRegenEngine.Stage.DELETING_REGIONS) {
					int regions = ACTIVE_ENGINE.getTotalRegions();
					int done = ACTIVE_ENGINE.getDeletedRegions() + ACTIVE_ENGINE.getProtectedRegions() + ACTIVE_ENGINE.getSkippedLoadedRegions();
					int percent = 0;
					if (regions > 0) {
						progress = (float) done / regions;
						percent = (int)(progress * 100);
					}
					text = "Processing regions: " + percent + "% (" + done + "/" + regions + ")";

				} else {
					int chunks = ACTIVE_ENGINE.getTotalChunks();
					int done = ACTIVE_ENGINE.getDeletable() + ACTIVE_ENGINE.getProtected();
					int percent = 0;
					if (chunks > 0) {
						progress = (float) done / chunks;
						percent = (int)(progress * 100);
					}
					text = "Checking chunks: " + percent + "% (" + done + "/" + chunks + ")";
				}

				float finalProgress = progress;
				String finalText = text;

				server.execute(() -> {
					if (ACTIVE_BAR != null) {
						ACTIVE_BAR.setName(new StringTextComponent(finalText));
						ACTIVE_BAR.setPercent(finalProgress);
					}
				});

				try {
					Thread.sleep(1000L);
				} catch (InterruptedException ignored) {}
			}
			server.execute(() -> {
				if (ACTIVE_BAR != null) {
					ACTIVE_BAR.removeAllPlayers();
					ACTIVE_BAR = null;
				}

				ServerPlayerEntity player = server.getPlayerList().getPlayer(ACTIVE_PLAYER);
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
			});

		}).start();
	}
}