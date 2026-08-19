package com.github.maximuslotro.lotrrextended.common.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lotr.common.world.spawning.InvasionSpawner;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public class ExtendedInvasionDataHolder extends ExtendedAbstractLevelDataHolder {

	private Map<UUID, Integer> invasionEnemiesLeft = new HashMap<>();

	/**
	 * This variable stores a map of invasion UUIDs to players who have contributed to the invasion's UUIDs along with
	 * a timer in ticks of how much longer their entry will stay. This timer defaults to 6000 ticks (5m) meaning when
	 * an invasion is defeated only players who have contributed in the past 5 minutes get the achievement
	 * <p>
	 * Does not need to be saved on server restart, as even if restart takes less than 5 minutes, we should not need to save the data here anyway
	 */
	private Map<UUID, Map<UUID, Integer>> recentPlayersByInvasion = new HashMap<>();

	public ExtendedInvasionDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	public Set<UUID> getPlayersWhoFoughtInvasion(UUID invasionUUID) {
		Set<UUID> returnSet = new HashSet<>();
		if (recentPlayersByInvasion.containsKey(invasionUUID)) {
			Map<UUID, Integer> playerTimes = recentPlayersByInvasion.get(invasionUUID);
			returnSet = playerTimes.entrySet()
				.stream()
				.filter(entry -> entry.getValue() > 0)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
		}

		return returnSet;
	}

	/**
	 * This method should be called once a second (20 ticks) to update player invasion
	 * times.
	 */
	public void reducePlayerInteractedWithInvasionTimes() {
		Iterator<Map<UUID, Integer>> playerTimesIterator = recentPlayersByInvasion.values().iterator();
		while (playerTimesIterator.hasNext()) {
			Map<UUID, Integer> playerTimes = playerTimesIterator.next();
			Iterator<Map.Entry<UUID, Integer>> iterator = playerTimes.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<UUID, Integer> entry = iterator.next();
				int newTime = entry.getValue() - 20;
				if (newTime > 0) {
					entry.setValue(newTime);
				} else {
					iterator.remove();
					if (playerTimes.isEmpty()) playerTimesIterator.remove();
				}
			}
		}
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
		if(hasInvasion(invasionId) && getEnemiesLeft(invasionId) > 0) {
			invasionEnemiesLeft.put(invasionId, Math.max(0, getEnemiesLeft(invasionId) - 1));
		}
	}

	public void trackRecentPlayerKill(UUID invasionId, UUID playerId) {
		if (recentPlayersByInvasion.containsKey(invasionId)) {
			Map<UUID, Integer> timeLeftByPlayer = recentPlayersByInvasion.get(invasionId);
			timeLeftByPlayer.put(playerId, 5000);
			recentPlayersByInvasion.put(invasionId, timeLeftByPlayer);
		} else {
			Map<UUID, Integer> timeLeftByPlayer = new HashMap<>();
			timeLeftByPlayer.put(playerId, 5000);
			recentPlayersByInvasion.put(invasionId, timeLeftByPlayer);
		}
		markDirty();
	}

	@Override
	public void loadData(CompoundNBT pTag, ServerWorld world) {}

	@Override
	public void saveData(CompoundNBT pTag, ServerWorld world) {}

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
