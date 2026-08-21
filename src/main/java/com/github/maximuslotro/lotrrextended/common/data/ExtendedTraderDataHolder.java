package com.github.maximuslotro.lotrrextended.common.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.world.spawning.WanderingTradersSpawner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedTraderDataHolder extends ExtendedAbstractLevelDataHolder {
	private final Map<UUID, Integer> playerTraderSpawnCooldowns = new HashMap<>();
	private int timeUntilTrader = 0;
	private boolean requireRandom = true;

	public ExtendedTraderDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	public int getTimeUntilTrader() {
		return timeUntilTrader;
	}

	public int getPlayersTraderCooldown(UUID uuid) {
		return playerTraderSpawnCooldowns.getOrDefault(uuid, -1);
	}

	public void decrementPlayerTraderCooldowns(World world) {
		for (PlayerEntity player : world.players()) {
			UUID playerUUID = player.getUUID();
			if (playerTraderSpawnCooldowns.containsKey(playerUUID)) {
				int secondsLeft = playerTraderSpawnCooldowns.get(playerUUID);
				if (secondsLeft > 0) {
					secondsLeft--;
				}
				playerTraderSpawnCooldowns.put(playerUUID, secondsLeft);
			} else {
				// start their initial cooldown at 3m (180 seconds)
				// so they don't get a wandering trader immediately after spawning
				playerTraderSpawnCooldowns.put(playerUUID, 180);
			}
		}
	}

	@Override
	public void loadDataOnce(CompoundNBT pTag, MinecraftServer server) {
		CompoundNBT traderData = pTag.getCompound("traderData");
		timeUntilTrader = traderData.getInt("timeUntilTrader");
		requireRandom = traderData.getBoolean("requireRandom");

		ListNBT listnbt = pTag.getList("playerCooldowns", 10);
		playerTraderSpawnCooldowns.clear();
		if (listnbt.isEmpty()) {
			return;
		}
		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			int cooldown = compoundnbt.getInt("cooldown");
			playerTraderSpawnCooldowns.put(playerUUID, cooldown);
		}
	}

	@Override
	public void clearData() {
		playerTraderSpawnCooldowns.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag) {
		CompoundNBT traderData = new CompoundNBT();
		traderData.putInt("timeUntilTrader", this.timeUntilTrader);
		traderData.putBoolean("requireRandom", this.requireRandom);
		pTag.put("traderData", traderData);

		ListNBT list = new ListNBT();
		for (UUID uuid : playerTraderSpawnCooldowns.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			entry.putInt("cooldown", playerTraderSpawnCooldowns.get(uuid));
			list.add(entry);
		}
		pTag.put("playerCooldowns", list);
	}

	@Override
	public void tick(ServerWorld level) {
		if (level.getGameTime() % 20L == 0L) {
			decrementPlayerTraderCooldowns(level);
		}
		if(!ExtendedServerConfig.enableWanderingTraderSpawns.get()) {
			return;
		}
		if (timeUntilTrader > 0) {
			timeUntilTrader--;
		} else {
			// If no players online or the 1/2 chance fails, try again in 1 minute (1200 ticks)
			if (level.players().isEmpty() || level.random.nextInt(2) > 0) {
				timeUntilTrader = 1200;
				return;
			}

			// Shuffle players to randomize target selection
			List<PlayerEntity> shuffledPlayers = new ArrayList<>(level.players());
			Collections.shuffle(shuffledPlayers);

			// set them to 3 minutes if they just joined and look for another player
			shuffledPlayers.stream()
			.filter(player -> getPlayersTraderCooldown(player.getUUID()) == -1)
			.forEach(player -> playerTraderSpawnCooldowns.put(player.getUUID(), 60 * 3));

			// skip creative mode players and spectators
			PlayerEntity targetPlayer = shuffledPlayers.stream()
				.filter(EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test)
				.filter(player -> getPlayersTraderCooldown(player.getUUID()) == 0)
				.findFirst()
				.orElse(null);

			if (targetPlayer != null) {
				UUID playerUUID = targetPlayer.getUUID();

				// Attempt spawn (may fail based on biome/conditions, but resets clock regardless)
				WanderingTradersSpawner.attemptSpawn(level, targetPlayer, false, false);
				timeUntilTrader = 1200 * 5; // Reset global timer to 5 minutes
				playerTraderSpawnCooldowns.put(playerUUID, 60 * 30); // Reset player's cooldown to 30 minutes
				requireRandom = true;
			} else {
				// No valid player was found to attempt a spawn on
				timeUntilTrader = 1200; // Wait 1 minute before trying again
				requireRandom = false;
			}
		}

		// Only request saving file every 50 seconds to ensure the server does not lag from saving trader time every tick
		if (timeUntilTrader % 1000L == 0L && timeUntilTrader != 0) {
			markDirty();
		}
	}
}
