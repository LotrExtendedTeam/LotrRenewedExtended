package com.github.maximuslotro.lotrrextended.common.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.world.spawning.BanditsSpawner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;

public class ExtendedBanditDataHolder extends ExtendedAbstractLevelDataHolder {
	private Map<UUID, Integer> playerBanditSpawnCooldowns = new HashMap<>();

	public ExtendedBanditDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	public int getPlayersBanditCooldown(UUID uuid) {
		return playerBanditSpawnCooldowns.getOrDefault(uuid, -1);
	}

	@Override
	public void loadData(CompoundNBT pTag) {
		ListNBT listnbt = pTag.getList("banditCooldowns", 10);
		if (listnbt.isEmpty()) {
			playerBanditSpawnCooldowns = new HashMap<>();
			return;
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			int cooldown = compoundnbt.getInt("cooldown");
			playerBanditSpawnCooldowns.put(playerUUID, cooldown);
		}
	}

	@Override
	public void saveData(CompoundNBT pTag, ServerWorld world) {
		ListNBT list = new ListNBT();
		for (UUID uuid : playerBanditSpawnCooldowns.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			entry.putInt("cooldown", playerBanditSpawnCooldowns.get(uuid));
			list.add(entry);
		}
		pTag.put("banditCooldowns", list);
	}

	@Override
	public void tick(ServerWorld level) {
		if(level.getGameTime() % 20L == 0L) {
			for (PlayerEntity player : level.players()) {
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
		if(!ExtendedServerConfig.enableBanditEncounters.get()) {
			return;
		}
		for (PlayerEntity player : level.players()) {
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
				boolean success = BanditsSpawner.attemptSpawn(level, player, false);
				if (!success) {
					// try again in between 1 and 13 minutes
					int newCooldown = level.random.nextInt(12 * 60) + 60;
					playerBanditSpawnCooldowns.put(player.getUUID(), newCooldown);
				} else {
					// give the player between 10 and 35 minutes of leeway
					int newCooldown = level.random.nextInt(1500) + 600;
					playerBanditSpawnCooldowns.put(player.getUUID(), newCooldown);
				}
			}
		}
	}

}
