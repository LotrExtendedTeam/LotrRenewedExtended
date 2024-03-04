package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.hooks.MixinHooks;

import org.spongepowered.asm.mixin.injection.At;

import lotr.common.config.LOTRConfig;
import lotr.common.data.ExtendedFellowshipDataModule;
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

@Mixin(LOTRLevelData.class)
public class MixinLOTRLevelData {
	
	@Inject(method = "save(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	public void saveFactionData(ServerWorld world, CallbackInfo info) {
		ExtendedFellowshipHolderServer.saveAll(world);
	}

	@Inject(method = "save(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	public void loadFactionData(ServerWorld world, CallbackInfo info) {
		ExtendedFellowshipHolderServer.loadAll(world);
	}

	@Shadow(remap=false)
	public LOTRPlayerData getData(PlayerEntity player) {return null;}

	/**
	 * @reason Needed to inject into an if statement tree
	 * @author maximuslotro
	 */
	@Overwrite(remap = false)
	public void sendPlayerLocationsToPlayer(PlayerEntity targetPlayer, ServerWorld world) {
		List<MapPlayerLocation> playerLocations = new ArrayList<>();
		MinecraftServer server = world.getServer();
		boolean isOp = server.getPlayerList().isOp(targetPlayer.getGameProfile());
		boolean creative = targetPlayer.abilities.instabuild;
		List<ExtendedFellowship> fellowshipsMapShow = new ArrayList<ExtendedFellowship>();
		ExtendedFellowshipDataModule data = MixinHooks.getFellowshipData(getData(targetPlayer));
		for (UUID fsID : data.getFellowshipIDs()) {
			ExtendedFellowship fs = ExtendedFellowshipHolderServer.getFellowship(world, fsID);
			if (fs != null && fs.isHideMapLocation())
				fellowshipsMapShow.add(fs);
		}
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
						if (fs.isFellowshipMember(otherPlayer.getUUID())
								|| fs.isFellowshipOwner(otherPlayer.getUUID())) {
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
