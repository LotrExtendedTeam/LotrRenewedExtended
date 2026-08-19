package com.github.maximuslotro.lotrextended.mixins.lotr.common.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

import lotr.common.config.LOTRConfig;
import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketMapPlayerLocations;
import lotr.common.world.map.MapPlayerLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

/**
 * Need to fellowship support to player location sending
 * @Mixin (LOTRLevelData.class)
 * */
@Mixin(LOTRLevelData.class)
public class MixinLOTRLevelData {

	/**
	 * Need to get the LOTRPlayerData instance associated with a given player
	 * @Shadow (remap=false)
	 * @param player A PlayerEntity to retrieve data for
	 * @return An instance of LOTRPlayerData
	 * */
	@Shadow(remap=false)
	public LOTRPlayerData getData(PlayerEntity player) {return null;}

	/**
	 * @author maximuslotro
	 * @reason Needed to inject into an if statement tree
	 * @Overwrite (remap = false)
	 * @param targetPlayer The PlayerEntity to send player locations to
	 * @param world The current ServerWorld to query for players
	 */
	@Overwrite(remap = false)
	public void sendPlayerLocationsToPlayer(PlayerEntity targetPlayer, ServerWorld world) {
		List<MapPlayerLocation> playerLocations = new ArrayList<>();
		MinecraftServer server = world.getServer();
		boolean isOp = server.getPlayerList().isOp(targetPlayer.getGameProfile());
		boolean creative = targetPlayer.abilities.instabuild;
		// Extended Start
		List<ExtendedFellowship> fellowshipsMapShow = new ArrayList<>();
		ExtendedFellowshipDataModule data = ((ExtendedLOTRPlayerData)getData(targetPlayer)).getFellowshipData();
		for (UUID fsID : data.getFellowshipIDs()) {
			ExtendedFellowship fs = ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(world, fsID);
			if (fs != null && fs.isHideMapLocation())
				fellowshipsMapShow.add(fs);
		}
		// Extended End
		for (PlayerEntity otherPlayer : world.players()) {
			if (otherPlayer == targetPlayer)
				continue;
			boolean show = !getData(otherPlayer).getHideMapLocation();
			if (!isOp && getData(otherPlayer).getAdminHideMap()) {
				show = false;
			} else if (LOTRConfig.SERVER.forceMapLocations.get() == 1) {
				show = false;
			} else if (LOTRConfig.SERVER.forceMapLocations.get() == 2) {
				show = true;
			} else if (!show) {
				if (isOp && creative) {
					show = true;
				} else {
					// Extended Start
					for (ExtendedFellowship fs : fellowshipsMapShow) {
						if (fs.isInFellowship(otherPlayer.getUUID())) {
							show = true;
							break;
						}
					}
					// Extended End
				}
			}
			if (show) {
				playerLocations.add(MapPlayerLocation.ofPlayer(otherPlayer));
			}
		}
		SPacketMapPlayerLocations packet = new SPacketMapPlayerLocations(playerLocations);
		LOTRPacketHandler.sendTo(packet, (ServerPlayerEntity) targetPlayer);
	}
}
