package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSSendServerSettingsPacket;

import lotr.common.config.LOTRConfig;
import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.fellowship.ExtendedFellowshipHolderServer;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketMapPlayerLocations;
import lotr.common.world.map.MapPlayerLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

/**
 * Need to add more server managed data
 * @Mixin (LOTRLevelData.class)
 * */
@Mixin(LOTRLevelData.class)
public class MixinLOTRLevelData {

	/**
	 * @reason Need to add more level saved data, in this case Fellowships
	 * @Inject (method = "save(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	 * @param world The ServerWorld to query for data
	 * @param info The local CallbackInfo for this Injection
	 * */
	@Inject(method = "save(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	public void saveFactionData(ServerWorld world, CallbackInfo info) {
		ExtendedFellowshipHolderServer.saveAll(world);
	}

	/**
	 * @reason Need to load data from saved data, in this case Fellowships
	 * @Inject (method = "load(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	 * @param world The ServerWorld to query for data
	 * @param info The local CallbackInfo for this Injection
	 * */
	@Inject(method = "load(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	public void loadFactionData(ServerWorld world, CallbackInfo info) {
		ExtendedFellowshipHolderServer.loadAll(world);
	}

	/**
	 * Need to get the LOTRPlayerData instance associated with a given player
	 * @Shadow (remap=false)
	 * @param player A PlayerEntity to retrieve data for
	 * @return An instance of LOTRPlayerData
	 * */
	@Shadow(remap=false)
	public LOTRPlayerData getData(PlayerEntity player) {return null;}

	/**
	 * @reason On player login, send additional server settings information
	 * @Inject (method = "sendLoginPacket(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"), remap=false)
	 * @param player The ServerPlayerEntity to send to
	 * @param ci The local CallbackInfo instance of this Inject
	 * */
	@Inject(method = "sendLoginPacket(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"), remap=false)
	public void sendLoginPacket(ServerPlayerEntity player, CallbackInfo ci) {
		ExtendedSSendServerSettingsPacket packet = new ExtendedSSendServerSettingsPacket();
		packet.setMakeMetalDoorsRequirePower(ExtendedServerConfig.makeMetalDoorsRequirePower.get());
		ExtendedPacketHandler.sendToClient(packet, player);
	}

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
			ExtendedFellowship fs = ExtendedFellowshipHolderServer.getFellowship(world, fsID);
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
					for (ExtendedFellowship fs : fellowshipsMapShow) {
						if (fs.isInFellowship(otherPlayer.getUUID())) {
							show = true;
							break;
						}
					}
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
