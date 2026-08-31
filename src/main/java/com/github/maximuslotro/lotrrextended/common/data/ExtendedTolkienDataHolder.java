package com.github.maximuslotro.lotrrextended.common.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.entity.npc.ExtendedTolkienEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public class ExtendedTolkienDataHolder extends ExtendedAbstractLevelDataHolder {
	private final List<UUID> tolkienTalkedTo = new ArrayList<>();
	private final int tolkienTalkedToVersion = 1;

	public ExtendedTolkienDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void loadDataServer(CompoundNBT pTag, MinecraftServer server) {
		int tolkienVersion = pTag.getInt("tolkienVersion");
		tolkienTalkedTo.clear();
		if(tolkienVersion!=tolkienTalkedToVersion) {
			return;
		}
		ListNBT listnbt = pTag.getList("tolkienTalkedTo", 10);
		if (listnbt.isEmpty()) {
			return;
		}

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			UUID playerUUID = compoundnbt.getUUID("player");
			tolkienTalkedTo.add(playerUUID);
		}
	}

	@Override
	protected void clearData() {
		tolkienTalkedTo.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag) {
		ListNBT list = new ListNBT();
		for (UUID uuid : tolkienTalkedTo) {
			CompoundNBT entry = new CompoundNBT();
			entry.putUUID("player", uuid);
			list.add(entry);
		}
		pTag.putInt("tolkienVersion", tolkienTalkedToVersion);
		pTag.put("tolkienTalkedTo", list);
	}

	@Override
	public void tick(ServerWorld level) {
		if(!ExtendedServerConfig.enableTolkienSpawning.get()) {
			return;
		}
		// run tolkien spawns every five minutes
		if (level.getGameTime() % 6000L == 0L) {
			List<PlayerEntity> shuffledPlayers = new ArrayList<>(level.players());
			Collections.shuffle(shuffledPlayers);

			boolean attemptedTraderSpawn = false;
			for (PlayerEntity player : shuffledPlayers) {
				if(this.tolkienTalkedTo.contains(player.getUUID())) {
					continue;
				}
				if(ExtendedTolkienEntity.spawn(level, player)) {
					tolkienTalkedTo.add(player.getUUID());
				}
			}
		}
	}
}
