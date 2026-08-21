package com.github.maximuslotro.lotrrextended.common.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lotr.common.world.spawning.InvasionSpawner;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public class ExtendedInvasionDataHolder extends ExtendedAbstractLevelDataHolder {

	private final Map<UUID, Integer> invasionEnemiesLeft = new ConcurrentHashMap<>();

	/**
	 * This variable stores a map of invasion UUIDs to players who have contributed to the invasion's UUIDs along with
	 * a timer in ticks of how much longer their entry will stay. This timer defaults to 6000 ticks (5m) meaning when
	 * an invasion is defeated only players who have contributed in the past 5 minutes get the achievement
	 * <p>
	 * Does not need to be saved on server restart, as even if restart takes less than 5 minutes, we should not need to save the data here anyway
	 */
	private final Map<UUID, ConcurrentHashMap<UUID, Integer>> recentPlayersByInvasion = new ConcurrentHashMap<>();

	public ExtendedInvasionDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	public Set<UUID> getPlayersWhoFoughtInvasion(UUID invasionUUID) {
		Map<UUID, Integer> playerTimes = recentPlayersByInvasion.get(invasionUUID);
		if (playerTimes == null || playerTimes.isEmpty()) {
			return Collections.emptySet();
		}
		Set<UUID> returnSet = new HashSet<>();
		playerTimes.forEach((playerUUID, ticksLeft) -> {
			if (ticksLeft > 0) {
				returnSet.add(playerUUID);
			}
		});
		return returnSet;
	}

	/**
	 * This method should be called once a second (20 ticks) to update player invasion
	 * times.
	 */
	public void reducePlayerInteractedWithInvasionTimes() {
		recentPlayersByInvasion.values().forEach(playerTimes -> {
	        playerTimes.entrySet().removeIf(entry -> entry.setValue(entry.getValue() - 20) <= 0);
	    });

	    // Clean empty invasions
	    recentPlayersByInvasion.entrySet().removeIf(entry -> entry.getValue().isEmpty());
	}

	/**
	 * Registers an invasion to be tracked by the server, overwrites existing entries that should not exist
	 * @param invasionId The unique id of the invasion
	 * @param initialEnemies How many enemies are left
	 */
	public void registerInvasion(UUID invasionId, int initialEnemies) {
		invasionEnemiesLeft.put(invasionId, initialEnemies);
	}

	/**
	 * Removes an invasion from being tracked by the server
	 * @param invasionId The unique id of the invasion
	 */
	public void unregisterInvasion(UUID invasionId) {
		invasionEnemiesLeft.remove(invasionId);
	}

	/**
	 * Returns how many enemies are left for the invasion or -1 if it's not a valid invasion
	 * @param invasionId The unique id of the invasion
	 * @return How many enemies are left
	 */
	public int getEnemiesLeft(UUID invasionId) {
		return invasionEnemiesLeft.getOrDefault(invasionId, -1);
	}
	/**
	 * Checks if an invasion is being tracked by the server
	 * @param invasionId The unique id of the invasion
	 * @return True if server knows of the invasion
	 */
	public boolean hasInvasion(UUID invasionId) {
		return invasionEnemiesLeft.containsKey(invasionId);
	}

	/**
	 * Never allow the enemies left to go below 0 to help prevent it from being an issue where -1 is used
	 * <p>
	 * Also ensures no modifications to invasionEnemiesLeft unless the invasion is valid
	 * @param invasionId The unique id of the invasion
	 */
	public void decrementEnemiesLeft(UUID invasionId) {
		invasionEnemiesLeft.computeIfPresent(invasionId, (id, count) -> count > 0 ? count - 1 : count);
	}

	public void trackRecentPlayerKill(UUID invasionId, UUID playerId) {
		recentPlayersByInvasion.computeIfAbsent(invasionId, id -> new ConcurrentHashMap<>()).put(playerId, 6000);
		// markDirty(); don't mark dirty since this data does not persist world reload anyways
	}

	@Override
	public void loadDataOnce(CompoundNBT pTag, MinecraftServer server) {
		invasionEnemiesLeft.clear();
		recentPlayersByInvasion.clear();
	}

	@Override
	public void clearData() {
		invasionEnemiesLeft.clear();
		recentPlayersByInvasion.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag) {}

	@Override
	public void tick(ServerWorld level) {
		if (level.getGameTime() % 20L == 0L) {
			reducePlayerInteractedWithInvasionTimes();
			for (ServerPlayerEntity player : level.players()) {
				// skip creative mode players and spectators
				if (player.isCreative() || player.isSpectator()) continue;

				InvasionSpawner.attemptInvasionSpawns(level, player);
			}
		}
	}
}
