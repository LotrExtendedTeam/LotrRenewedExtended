package com.github.maximuslotro.lotrextended.mixins.lotr.common.event;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.data.LOTRLevelData;
import lotr.common.data.MiscDataModule;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.event.LOTREventHandler;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.time.LOTRDate;
import lotr.common.time.LOTRTime;
import lotr.common.world.RingPortalTeleporter;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @Mixin (LOTREventHandler.class)
 * */
@Mixin(LOTREventHandler.class)
public class MixinLOTREventHandler {

	/**
	 * @author Maximuslotro
	 * @reason Fix player data still being sent for players that are offline
	 * @Overwrite (remap=false)
	 * @param event The PlayerLoggedInEvent to question
	 */
	@Overwrite(remap=false)
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		World world = player.level;
		if (!world.isClientSide) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			// Extended Start
			if(serverPlayer.hasDisconnected()) {
				//ExtendedLog.info("Prevented playerData login1 on disconnected player: "+player.getStringUUID());
				return;
			}
			// Extended End
			ServerWorld serverWorld = serverPlayer.getLevel();
			LOTRLevelData levelData = LOTRLevelData.serverInstance();
			MapSettingsManager.serverInstance().sendMapToPlayer(serverPlayer);
			FactionSettingsManager.serverInstance().sendFactionsToPlayer(serverPlayer);
			NPCEntitySettingsManager.serverInstance().sendEntitySettingsToPlayer(serverPlayer);
			if (LOTRWorldTypes.isInstantME(serverWorld) && LOTRDimensions.isDimension(serverWorld, World.OVERWORLD)) {
				MiscDataModule miscData = levelData.getData(serverPlayer).getMiscData();
				if (!miscData.getInitialSpawnedIntoME()) {
					RingPortalTeleporter.transferEntity(serverWorld, serverPlayer, Optional.empty(), false);
					miscData.setInitialSpawnedIntoME(true);
				}
			}
			// Extended Start
			if(serverPlayer.hasDisconnected()) {
				//ExtendedLog.info("Prevented playerData login2 on disconnected player: "+player.getStringUUID());
				return;
			}
			// Extended End
			levelData.sendLoginPacket(serverPlayer);
			levelData.playerDataHandleLogin(serverPlayer);
			// Alignment used to be sent here
			LOTRTime.sendLoginPacket(serverPlayer);
			LOTRDate.sendLoginPacket(serverPlayer);
			// Extended Start
			if(serverPlayer.hasDisconnected()) {
				//ExtendedLog.info("Prevented playerData login3 on disconnected player: "+player.getStringUUID());
				return;
			}
			// Extended End
			// Change send order of alignment so other players are not spammed
			levelData.sendAllOtherPlayerAlignmentsToPlayer(serverPlayer);
			levelData.sendPlayerAlignmentToAllOtherPlayers(serverPlayer);
		}
	}
}