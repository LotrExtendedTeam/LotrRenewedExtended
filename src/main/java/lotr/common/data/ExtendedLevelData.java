package lotr.common.data;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;

import lotr.common.LOTRLog;
import lotr.common.entity.npc.ExtendedTolkienEntity;
import lotr.common.world.spawning.BanditsSpawner;
import lotr.common.world.spawning.InvasionSpawner;
import lotr.common.world.spawning.WanderingTradersSpawner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

/**
 * @author Richardb122
 *
 * This class is only designed to save and handle the data on the server side for now.
 * I plan to eventually synchronize it with the client but for now it will store data
 * on the server side and be used to keep track of when Wandering Traders last spawned
 */
public class ExtendedLevelData {

	private static final ExtendedLevelData SERVER_INSTANCE = new ExtendedLevelData(LogicalSide.SERVER);

	private boolean needsSave = false;
	private boolean needsLoad = true;

	private final LogicalSide side;

	private int timeUntilTrader = 0;

	private boolean requireRandom = true;

	private List<UUID> tolkienTalkedTo = new ArrayList<>();
	private final int tolkienTalkedToVersion = 1;

	public ExtendedLevelData(LogicalSide side) {
		this.side = side;
	}

	public static ExtendedLevelData serverInstance() {
		return SERVER_INSTANCE;
	}

	private Map<UUID, Integer> playerTraderSpawnCooldowns = new HashMap<>();
	private Map<UUID, Integer> playerBanditSpawnCooldowns = new HashMap<>();

	private Map<UUID, Integer> invasionEnemiesLeft = new HashMap<>();

	/**
	 * This variable stores a map of invasion UUIDs to players who have contributed to the invasion's UUIDs along with
	 * a timer in ticks of how much longer their entry will stay. This timer defaults to 6000 ticks (5m) meaning when
	 * an invasion is defeated only players who have contributed in the past 5 minutes get the achievement
	 */
	private Map<UUID, Map<UUID, Integer>> recentPlayersByInvasion = new HashMap<>();

	/** -1 if the player is marked to be cursed
	 *  -2 if the player was cursed
	 *  -3 if the player left the barrow downs after having entered the first time
	 */
	private Map<UUID, Integer> secondsInBarrows = new HashMap<>();

	public boolean needsLoad() {
		return needsLoad;
	}

	public void save(ServerWorld world) {
		try {
			if (this.needsSave) {
				File lotrDataFile = getLOTRDataFile(world);
				if (!lotrDataFile.exists()) {
					SaveUtil.saveNBTToFile(lotrDataFile, new CompoundNBT());
				}
				CompoundNBT levelData = new CompoundNBT();

				CompoundNBT traderData = new CompoundNBT();
				traderData.putInt("timeUntilTrader", this.timeUntilTrader);
				traderData.putBoolean("requireRandom", this.requireRandom);
				levelData.put("traderData", traderData);

				savePlayerTraderCooldowns(levelData);
				savePlayerBanditCooldowns(levelData);
				saveSecondsInBarrows(levelData);
				saveTolkienTalkedTo(levelData);
				saveInvasionEnemiesLeft(levelData);
				saveRecentInvasionPlayersToNBT(levelData);

				SaveUtil.saveNBTToFile(lotrDataFile, levelData);
				this.needsSave = false;
			}
		} catch (Exception e) {
			LOTRLog.error("Error saving Extended mod data!");
			e.printStackTrace();
		}
	}

	public void load(ServerWorld world) {
		try {
			CompoundNBT levelData = SaveUtil.loadNBTFromFile(getLOTRDataFile(world));
			CompoundNBT traderData = levelData.getCompound("traderData");
			this.timeUntilTrader = traderData.getInt("timeUntilTrader");
			this.requireRandom = traderData.getBoolean("requireRandom");

			loadPlayerTraderCooldownTimes(levelData);
			loadPlayerBanditCooldowns(levelData);
			loadSecondsInBarrows(levelData);
			loadTolkienTalkedTo(levelData);
			loadInvasionEnemiesLeft(levelData);
			loadRecentInvasionPlayersToNBT(levelData);

			this.needsLoad = false;
			this.needsSave = true;
			this.save(world);
		} catch (Exception e) {
			LOTRLog.error("Error loading Extended mod data!");
			e.printStackTrace();
		}
	}

	public void logPlayerInvasionTimes() {
		for (Map.Entry<UUID, Map<UUID, Integer>> entry : recentPlayersByInvasion.entrySet()) {
			LOTRLog.info("INVASION UUID: " + entry.getKey());
			for (Map.Entry<UUID, Integer> playerEntry : entry.getValue().entrySet()) {
				LOTRLog.info("	" + playerEntry.getKey() + ": " + playerEntry.getValue());
			}

		}
	}

	public int getTimeUntilTrader() {
		return timeUntilTrader;
	}

	public int getPlayersTraderCooldown(UUID uuid) {
        return playerTraderSpawnCooldowns.getOrDefault(uuid, -1);
	}

	public int getPlayersBanditCooldown(UUID uuid) {
		return playerBanditSpawnCooldowns.getOrDefault(uuid, -1);
	}

	public boolean isPlayerBarrowCursed(PlayerEntity player) {
		return secondsInBarrows.containsKey(player.getUUID()) && (secondsInBarrows.get(player.getUUID()) == -1);
	}

	public void removeCurse(PlayerEntity player) {
		secondsInBarrows.put(player.getUUID(), -2);
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

	public void decrementPlayerBanditCooldowns(World world) {
		for (PlayerEntity player : world.players()) {
			UUID playerUUID = player.getUUID();
			if (playerBanditSpawnCooldowns.containsKey(playerUUID)) {
				int secondsLeft = playerBanditSpawnCooldowns.get(playerUUID);
				if (secondsLeft > 0) {
					secondsLeft--;
				}
				playerBanditSpawnCooldowns.put(playerUUID, secondsLeft);
			} else {
				// start their initial cooldown at 30m
				playerBanditSpawnCooldowns.put(playerUUID, 60 * 30);
			}
		}
	}

	public void incrementPlayerTimeInBarrows(World world) {
		for (PlayerEntity player : world.players()) {
			Biome biome = world.getBiome(player.blockPosition());
			UUID playerUUID = player.getUUID();
			if(biome.getRegistryName().toString().equals("lotr:barrow_downs")) {
				if (secondsInBarrows.containsKey(playerUUID)) {
					int seconds = secondsInBarrows.get(playerUUID);
					if (seconds == -3) {
						//reentered barrows
						double rand = world.random.nextDouble();
						if (rand < 0.13) {
							secondsInBarrows.put(playerUUID, 1);
						}
						else {
							secondsInBarrows.put(playerUUID, -2);
						}
					}
					if (seconds <= 30 && seconds >= 1) {
						secondsInBarrows.put(playerUUID, seconds+1);
					}
					if (seconds > 30) {
						//After 30 seconds in the barrows, get jumpscared the first time!
						((ServerWorld) world).sendParticles((ServerPlayerEntity) player, ExtendedParticles.BARROW_WIGHT_JUMPSCARE.get(), false, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 1);
						secondsInBarrows.put(playerUUID, -1);
					}
				}
				else {
					secondsInBarrows.put(playerUUID, 1);
				}
			}
			else if (secondsInBarrows.containsKey(playerUUID)) {
				if (secondsInBarrows.get(playerUUID) == -2)
					secondsInBarrows.put(playerUUID, -3);
			}
		}
	}

	private void saveRecentInvasionPlayersToNBT(CompoundNBT root) {
		CompoundNBT nbt = new CompoundNBT();

		for (Map.Entry<UUID, Map<UUID, Integer>> invasionEntry : recentPlayersByInvasion.entrySet()) {
			UUID invasionId = invasionEntry.getKey();
			Map<UUID, Integer> players = invasionEntry.getValue();

			CompoundNBT playersTag = new CompoundNBT();

			for (Map.Entry<UUID, Integer> playerEntry : players.entrySet()) {
				UUID playerId = playerEntry.getKey();
				int ticksRemaining = playerEntry.getValue();

				playersTag.putInt(playerId.toString(), ticksRemaining);
			}

			nbt.put(invasionId.toString(), playersTag);
		}

		root.put("invasion_players", nbt);
	}

	private void loadRecentInvasionPlayersToNBT(CompoundNBT root) {
		if (root.contains("invasion_players")) {
			CompoundNBT nbt = root.getCompound("invasion_players");

			for (String invasionKey : nbt.getAllKeys()) {
				UUID invasionId;

				try {
					invasionId = UUID.fromString(invasionKey);
				} catch (IllegalArgumentException e) {
					// skip and log any invalid UUIDs
					LOTRLog.warn("invasion_players tag in EXTENDED.DAT contained invalid UUID: " + invasionKey);
					continue;
				}

				CompoundNBT playersTag = nbt.getCompound(invasionKey);
				Map<UUID, Integer> players = new HashMap<>();

				for (String playerKey : playersTag.getAllKeys()) {
					try {
						UUID playerId = UUID.fromString(playerKey);
						int ticksRemaining = playersTag.getInt(playerKey);
						players.put(playerId, ticksRemaining);
					} catch (IllegalArgumentException ignored) {
						LOTRLog.error("Invalid UUID: ", ignored);
					}
				}

				recentPlayersByInvasion.put(invasionId, players);
			}

		}
	}

	private void loadPlayerTraderCooldownTimes(CompoundNBT tag) {
		ListNBT listnbt = tag.getList("playerCooldowns", 10);
		if (listnbt.isEmpty()) {
			playerTraderSpawnCooldowns = new HashMap<>();
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			int cooldown = compoundnbt.getInt("cooldown");
			playerTraderSpawnCooldowns.put(playerUUID, cooldown);
		}
	}

	private void loadPlayerBanditCooldowns(CompoundNBT tag) {
		ListNBT listnbt = tag.getList("banditCooldowns", 10);
		if (listnbt.isEmpty()) {
			playerBanditSpawnCooldowns = new HashMap<>();
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			int cooldown = compoundnbt.getInt("cooldown");
			playerBanditSpawnCooldowns.put(playerUUID, cooldown);
		}
	}

	private void loadSecondsInBarrows(CompoundNBT tag) {
		ListNBT listnbt = tag.getList("secondsInBarrows", 10);
		if (listnbt.isEmpty()) {
			secondsInBarrows = new HashMap<>();
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			int seconds = compoundnbt.getInt("seconds");
			secondsInBarrows.put(playerUUID, seconds);
		}
	}

	private void loadInvasionEnemiesLeft(CompoundNBT tag) {
		ListNBT listnbt = tag.getList("invasionEnemiesLeft", 10);
		if (listnbt.isEmpty()) {
			invasionEnemiesLeft = new HashMap<>();
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID invasionUUID = compoundnbt.getUUID("invasion");
			int enemiesLeft = compoundnbt.getInt("enemies_left");
			invasionEnemiesLeft.put(invasionUUID, enemiesLeft);
		}
	}

	private void loadTolkienTalkedTo(CompoundNBT tag) {
		int tolkienVersion = tag.getInt("tolkienVersion");
		if(tolkienVersion!=tolkienTalkedToVersion) {
			tolkienTalkedTo = new ArrayList<>();
			return;
		}
		ListNBT listnbt = tag.getList("tolkienTalkedTo", 10);
		if (listnbt.isEmpty()) {
			tolkienTalkedTo = new ArrayList<>();
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			tolkienTalkedTo.add(playerUUID);
		}
	}

	private void savePlayerTraderCooldowns(CompoundNBT tag) {
		ListNBT list = new ListNBT();
		for (UUID uuid : playerTraderSpawnCooldowns.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			entry.putInt("cooldown", playerTraderSpawnCooldowns.get(uuid));
			list.add(entry);
		}
		tag.put("playerCooldowns", list);
	}

	private void savePlayerBanditCooldowns(CompoundNBT tag) {
		ListNBT list = new ListNBT();
		for (UUID uuid : playerBanditSpawnCooldowns.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			entry.putInt("cooldown", playerBanditSpawnCooldowns.get(uuid));
			list.add(entry);
		}
		tag.put("banditCooldowns", list);
	}

	private void saveInvasionEnemiesLeft(CompoundNBT tag) {
		ListNBT list = new ListNBT();
		for (UUID uuid : invasionEnemiesLeft.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("invasion", uuid);
			entry.putInt("enemies_left", invasionEnemiesLeft.get(uuid));
			list.add(entry);
		}
		tag.put("invasionEnemiesLeft", list);
	}

	private void saveSecondsInBarrows(CompoundNBT tag) {
		ListNBT list = new ListNBT();
		for (UUID uuid : secondsInBarrows.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			entry.putInt("seconds", secondsInBarrows.get(uuid));
			list.add(entry);
		}
		tag.put("secondsInBarrows", list);
	}

	private void saveTolkienTalkedTo(CompoundNBT tag) {
		ListNBT list = new ListNBT();
		for (UUID uuid : tolkienTalkedTo) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			list.add(entry);
		}
		tag.putInt("tolkienVersion", tolkienTalkedToVersion);
		tag.put("tolkienTalkedTo", list);
	}

	public void checkForTolkienSpawns(World world) {
		if(!ExtendedServerConfig.enableTolkienSpawning.get()) {
			return;
		}
		if (world.players().isEmpty() || (requireRandom && world.random.nextInt(4) > 0)) {
			return;
		}
		List<PlayerEntity> shuffledPlayers = new ArrayList<>(world.players());
		Collections.shuffle(shuffledPlayers);

		boolean attemptedTraderSpawn = false;
		for (PlayerEntity player : shuffledPlayers) {
			if(this.tolkienTalkedTo.contains(player.getUUID())) {
				continue;
			}
			if(ExtendedTolkienEntity.spawn(world, player)) {
				tolkienTalkedTo.add(player.getUUID());
			}
		}
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

	public void checkForBanditSpawns(ServerWorld world) {
		if(!ExtendedServerConfig.enableBanditEncounters.get()) {
			return;
		}
		for (PlayerEntity player : world.players()) {
			int cooldownLeft = getPlayersBanditCooldown(player.getUUID());
			// Start at 30 minutes to give them some leeway
			if (cooldownLeft == -1) {
				playerBanditSpawnCooldowns.put(player.getUUID(), 60 * 30);
				continue;
			}

			// skip creative mode players and spectators
			if (player.isCreative() || player.isSpectator()) continue;

			// attempt to spawn if it's at 0
			if (cooldownLeft == 0) {
				boolean success = BanditsSpawner.spawn(world, player, false, false);
				if (!success) {
					// try again in between 1 and 13 minutes
					int newCooldown = world.random.nextInt(12 * 60) + 60;
					playerBanditSpawnCooldowns.put(player.getUUID(), newCooldown);
				} else {
					// give the player between 10 and 35 minutes of leeway
					int newCooldown = world.random.nextInt(1500) + 600;
					playerBanditSpawnCooldowns.put(player.getUUID(), newCooldown);
				}
			}
		}
	}

	public void attemptToSpawnInvasions(ServerWorld world) {
		for (ServerPlayerEntity player : world.players()) {
			// skip creative mode players and spectators
			if (player.isCreative() || player.isSpectator()) continue;

			InvasionSpawner.attemptInvasionSpawns(world, player);
		}
	}

	public void checkForWanderingTraderSpawns(ServerWorld world) {
		if(!ExtendedServerConfig.enableWanderingTraderSpawns.get()) {
			return;
		}
		if (timeUntilTrader > 0) {
			timeUntilTrader--;
		} else {
			// if no players online try again in another minute
			// 1/5th of the time we will actually attempt to spawn otherwise just wait another minute
			if (world.players().isEmpty() || (world.random.nextInt(2) > 0)) {
				timeUntilTrader = 1200;
				return;
			}

			// pick a random player and spawn a trader near them
			List<PlayerEntity> shuffledPlayers = new ArrayList<>(world.players());
			Collections.shuffle(shuffledPlayers);

			boolean attemptedTraderSpawn = false;
			for (PlayerEntity player : shuffledPlayers) {
				int cooldownLeft = getPlayersTraderCooldown(player.getUUID());

				// skip creative mode players and spectators
				if (player.isCreative() || player.isSpectator()) continue;

				// set them to 3 minutes if they just joined and look for another player
				if (cooldownLeft == -1) {
					playerTraderSpawnCooldowns.put(player.getUUID(), 60 * 3);
					continue;
				}

				if (cooldownLeft == 0) {
					// this may not actually spawn if they are in a biome with no traders, but we'll still count it and reset their clock
					WanderingTradersSpawner.spawn(world, player);
					timeUntilTrader = 1200 * 5; // reset to 5 minute cooldown
					attemptedTraderSpawn = true;
					playerTraderSpawnCooldowns.put(player.getUUID(), 60 * 30); // reset player's cooldown 30 minutes
					requireRandom = true;
					break;
				}
			}

			// if a trader could not be spawned we will no longer require random
			if (!attemptedTraderSpawn) {
				timeUntilTrader = 1200; // if no trader was spawned wait another minute before trying again
				requireRandom = false;
			}
		}

		// Only request saving file every 50 seconds to ensure the server does not lag from saving trader time every tick
		if(timeUntilTrader % 1000L == 0L) {
			//Handle when tick just hit 0, and on next tick traders will be spawned and save function will be called since 4000%1000=0
			if(timeUntilTrader!=0) {
				needsSave = true;
			}
		}
	}

	private static File getLOTRDataFile(ServerWorld world) {
		String filename = "extended".toUpperCase();
		return new File(SaveUtil.getOrCreateLOTRDir(world), filename + ".dat");
	}

	public void registerInvasion(UUID invasionId, int initialEnemies) {
		invasionEnemiesLeft.put(invasionId, initialEnemies);
		this.needsSave = true;
	}

	public void unregisterInvasion(UUID invasionId) {
		invasionEnemiesLeft.remove(invasionId);
		this.needsSave = true;
	}

	/**
	 * Returns how many enemies are left for the invasion or -1 if it's not a valid invasion
	 * @param invasionId
	 * @return
	 */
	public int getEnemiesLeft(UUID invasionId) {
        return invasionEnemiesLeft.getOrDefault(invasionId, -1);
	}

	/**
	 * Never allow the enemies left to go below 0 to help prevent it from being an issue where -1 is used
	 * @param invasionId
	 */
	public void decrementEnemiesLeft(UUID invasionId) {
		int current = getEnemiesLeft(invasionId);
		invasionEnemiesLeft.put(invasionId, Math.max(0, current - 1));
		needsSave = true;
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
		needsSave = true;
	}

	public boolean doesNeedSaving() {
		return needsSave;
	}
}
