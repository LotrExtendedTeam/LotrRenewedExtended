package com.github.maximuslotro.lotrrextended.common.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

public class ExtendedBarrowsDataHolder extends ExtendedAbstractLevelDataHolder {
	/** -1 if the player is marked to be cursed
	 *  -2 if the player was cursed
	 *  -3 if the player left the barrow downs after having entered the first time
	 */
	private Map<UUID, Integer> secondsInBarrows = new HashMap<>();

	public ExtendedBarrowsDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	public boolean isPlayerBarrowCursed(PlayerEntity player) {
		return secondsInBarrows.containsKey(player.getUUID()) && (secondsInBarrows.get(player.getUUID()) == -1);
	}

	public void removeCurse(PlayerEntity player) {
		secondsInBarrows.put(player.getUUID(), -2);
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

	@Override
	public void loadData(CompoundNBT pTag) {
		ListNBT listnbt = pTag.getList("secondsInBarrows", 10);
		if (listnbt.isEmpty()) {
			secondsInBarrows = new HashMap<>();
			return;
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			int seconds = compoundnbt.getInt("seconds");
			secondsInBarrows.put(playerUUID, seconds);
		}
	}

	@Override
	public void saveData(CompoundNBT pTag, ServerWorld world) {
		ListNBT list = new ListNBT();
		for (UUID uuid : secondsInBarrows.keySet()) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			entry.putInt("seconds", secondsInBarrows.get(uuid));
			list.add(entry);
		}
		pTag.put("secondsInBarrows", list);
	}

	@Override
	public void tick(ServerWorld level) {
		if(level.getGameTime() % 20L == 0L) {
			incrementPlayerTimeInBarrows(level);
		}
	}

}
