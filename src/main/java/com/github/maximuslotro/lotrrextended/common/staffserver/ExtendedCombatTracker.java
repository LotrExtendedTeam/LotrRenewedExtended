package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedPlayerInfo;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedPlayerInfoHolder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedCombatTracker implements ExtendedTracker {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Path COMBATLOG_DIR = ExtendedServerData.EXTENDED_DIR.resolve("combatlog/");
	private static final Path LOG_FILE = COMBATLOG_DIR.resolve("violations.log");

	public static Map<UUID, CombatData> combatMap = new HashMap<>();

	public static void load() {
		try {
			Files.createDirectories(COMBATLOG_DIR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static long getCombatTimeoutMillis() {
		return 10 * 1000L; // default 10s
	}

	public static boolean isInCombat(UUID player) {
		CombatData data = combatMap.get(player);
		if (data == null) {
			return false;
		}
		long now = System.currentTimeMillis();
		if (now - data.lastHitTime > getCombatTimeoutMillis()) {
			combatMap.remove(player);
			return false;
		}
		return true;
	}

	public static void handleServerTick() {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server == null) {
			return;
		}
		long now = System.currentTimeMillis();
		long timeout = getCombatTimeoutMillis();
		for (UUID playerId : new java.util.ArrayList<>(combatMap.keySet())) {
			CombatData data = combatMap.get(playerId);
			if (data == null) {
				continue;
			}
			data.opponents.entrySet().removeIf(entry -> (now - entry.getValue() > timeout));
			if (now - data.lastHitTime > timeout || data.opponents.isEmpty()) {
				combatMap.remove(playerId);
				ServerPlayerEntity player = server.getPlayerList().getPlayer(playerId);
				if (player != null) {
					IFormattableTextComponent message = new StringTextComponent("[Combat] ")
						.withStyle(TextFormatting.GREEN)
						.append(new StringTextComponent("You are now out of combat").withStyle(TextFormatting.GRAY));
					player.sendMessage(message, Util.NIL_UUID);
					//player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 1.2F);
				}
			}
		}
	}

	public static void playerDeathEvent(PlayerEntity player) {
		UUID deadId = player.getUUID();
		CombatData deadData = combatMap.remove(deadId);
		if (deadData != null) {
			for (UUID attackerId : deadData.opponents.keySet()) {
				CombatData attackerData = combatMap.get(attackerId);
				if (attackerData != null) {
					attackerData.opponents.remove(deadId);
					if (attackerData.opponents.isEmpty()) {
						combatMap.remove(attackerId);
					}
				}
			}
		}
	}

	public static void playerDamagedEvent(ServerPlayerEntity victim, ServerPlayerEntity attacker) {
		if(victim.equals(attacker) || victim.isCreative() || victim.isSpectator() || attacker.isCreative() || attacker.isSpectator()) {
			return;
		}
		if (ExtendedModTracker.autoBanMap.containsKey("disable_combatlogger")) {
			return;
		}
		long now = System.currentTimeMillis();
		tagPlayer(victim.getUUID(), attacker.getUUID(), now);
		tagPlayer(attacker.getUUID(), victim.getUUID(), now);
	}

	private static void tagPlayer(UUID player, UUID opponent, long time) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (!combatMap.containsKey(player) || (time - combatMap.get(player).lastHitTime > getCombatTimeoutMillis())) {
			if (server != null) {
				ServerPlayerEntity serverPlayer = server.getPlayerList().getPlayer(player);
				if (serverPlayer != null) {
					IFormattableTextComponent message = new StringTextComponent("[Combat] ")
						.withStyle(TextFormatting.RED)
						.append(new StringTextComponent("You are now in combat! Do not leave the game.").withStyle(TextFormatting.GRAY));

					serverPlayer.sendMessage(message, Util.NIL_UUID);
					//serverPlayer.playNotifySound(SoundEvents.NOTE_BLOCK_BASEDRUM, SoundCategory.PLAYERS, 1.0F, 0.8F);
				}
			}
		}
		CombatData data = combatMap.computeIfAbsent(player, k -> new CombatData());
		data.lastHitTime = time;
		data.opponents.put(opponent, time);
	}

	public static void playerLoggedOutEvent(ServerPlayerEntity player) {
		if (ExtendedCombatTracker.isInCombat(player.getUUID()) && !player.isCreative() && !player.isSpectator()) {
			ExtendedCombatTracker.handleCombatLog(player);
		}
	}
	public static void handleCombatLog(ServerPlayerEntity player) {
		UUID uuid = player.getUUID();
		CombatData data = combatMap.remove(uuid);
		if (data == null) {
			return;
		}
		if (ExtendedModTracker.autoBanMap.containsKey("disable_combatlogger")) {
			return;
		}
		long now = System.currentTimeMillis();
		long timeout = getCombatTimeoutMillis();
		StringBuilder attackersList = new StringBuilder();
		// Notify attackers
		for (Map.Entry<UUID, Long> entry : data.opponents.entrySet()) {
			UUID attackerId = entry.getKey();
			long lastAttackTime = entry.getValue();

			// Ignore opponents who haven't interacted with this player within the timeout window
			if (now - lastAttackTime > timeout) {
				continue; 
			}
			ServerPlayerEntity attacker = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(attackerId);
			String attackerName;
			if (attacker != null) {
				attackerName = attacker.getGameProfile().getName();
				IFormattableTextComponent message = new StringTextComponent("[Combat] ").withStyle(TextFormatting.DARK_RED)
					.append(new StringTextComponent(player.getName().getString()).withStyle(TextFormatting.GOLD))
					.append(new StringTextComponent(" logged out during combat!").withStyle(TextFormatting.GRAY));
				attacker.sendMessage(message, Util.NIL_UUID);
				attacker.sendMessage(new StringTextComponent("You can report this player if you wish.").withStyle(TextFormatting.GRAY), Util.NIL_UUID);
				attacker.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 0.8F + attacker.getRandom().nextFloat() * 0.2F);
			} else {
				// fallback if offline
				attackerName = attackerId.toString();
			}
			if (attackersList.length() > 0) {
				attackersList.append(", ");
			}
			attackersList.append(attackerName);
		}
		// If all tracked opponents were old/expired, don't flag them as a combat logged
		if (attackersList.length() == 0) {
			return;
		}
		ExtendedTracker.logToFile(LOGGER, LOG_FILE, player.getGameProfile().getName() + " (" + uuid + ") logged against: [" + attackersList.toString() + "]");		
		ExtendedLevelData.serverInstance().getPlayerData().setCombatLogged(uuid, true);
	}

	public static void playerLoggedInEvent(ServerPlayerEntity player) {
		ExtendedPlayerInfoHolder playerData = ExtendedLevelData.serverInstance().getPlayerData();
		ExtendedPlayerInfo info = playerData.getPlayerInfo(player.getUUID());
		if (!info.didCombatLog()) {
			return;
		}
		IFormattableTextComponent message = new StringTextComponent("[Combat Log] ").withStyle(TextFormatting.DARK_RED)
			.append(new StringTextComponent("You logged out during combat last session.").withStyle(TextFormatting.RED));
		player.sendMessage(message, Util.NIL_UUID);
		player.sendMessage(new StringTextComponent("Other players may choose to report this.").withStyle(TextFormatting.GRAY), Util.NIL_UUID);
		player.playNotifySound(SoundEvents.ANVIL_LAND, SoundCategory.PLAYERS, 1.0F, 0.6F + player.getRandom().nextFloat() * 0.2F);
		player.playNotifySound(SoundEvents.PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 0.5F, 1.2F);
		playerData.setCombatLogged(player.getUUID(), false);

	}

	public static class CombatData {
		public long lastHitTime;
		public Map<UUID, Long> opponents = new HashMap<>();
	}
}
