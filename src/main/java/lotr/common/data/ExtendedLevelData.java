package lotr.common.data;

import lotr.common.LOTRLog;
import lotr.common.world.spawning.WanderingTradersSpawner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

import java.io.File;
import java.util.*;

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

    public ExtendedLevelData(LogicalSide side) {
        this.side = side;
    }

    public static ExtendedLevelData serverInstance() {
        return SERVER_INSTANCE;
    }

    private Map<UUID, Integer> playerTraderSpawnCooldowns = new HashMap<>();

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
                levelData.putInt("timeUntilTrader", this.timeUntilTrader);
                savePlayerTraderCooldowns(levelData);
                levelData.putBoolean("requireRandom", this.requireRandom);

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
            this.timeUntilTrader = levelData.getInt("timeUntilTrader");
            loadPlayerTraderCooldownTimes(levelData);
            this.requireRandom = levelData.getBoolean("requireRandom");

            this.needsLoad = false;
            this.needsSave = true;
            this.save(world);
        } catch (Exception e) {
            LOTRLog.error("Error loading Extended mod data!");
            e.printStackTrace();
        }
    }

    public int getTimeUntilTrader() {
        return timeUntilTrader;
    }

    public int getPlayersTraderCooldown(UUID uuid) {
       if (playerTraderSpawnCooldowns.containsKey(uuid)) {
           return playerTraderSpawnCooldowns.get(uuid);
       } else {
           return -1;
       }
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

    public void checkForWanderingTraderSpawns(World world) {
        if (timeUntilTrader > 0) {
            timeUntilTrader--;
        } else {
            // if no players online try again in another minute
            if (world.players().isEmpty()) {
                timeUntilTrader = 1200;
                return;
            }

            // 1/5th of the time we will actually attempt to spawn otherwise just wait another minute
            if (requireRandom && world.random.nextInt(4) > 0) {
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

	public boolean doesNeedSaving() {
		return needsSave;
	}
}
