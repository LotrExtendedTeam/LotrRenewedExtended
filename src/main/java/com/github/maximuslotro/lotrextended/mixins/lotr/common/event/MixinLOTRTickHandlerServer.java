package com.github.maximuslotro.lotrextended.mixins.lotr.common.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.data.LOTRLevelData;
import lotr.common.event.LOTRTickHandlerServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

/**
 * @Mixin (LOTRTickHandlerServer.class)
 * */
@Mixin(LOTRTickHandlerServer.class)
public class MixinLOTRTickHandlerServer {
	/**
	 * @author Maximuslotro
	 * @reason Stop offline player data being sent to the player
	 * @Overwrite (remap=false)
	 * @param event The PlayerTickEvent to check against
	 */
	@Overwrite(remap=false)
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.player;
			ServerWorld world = serverPlayer.getLevel();
			if (event.phase == Phase.END) {
				// Extended Start
				if(serverPlayer.hasDisconnected()) {
					//ExtendedLog.info("Prevented playerData update on disconnected player: "+serverPlayer.getStringUUID());
					return;
				}
				// Extended End
				LOTRLevelData.serverInstance().getData(serverPlayer).onUpdate(serverPlayer, world);
			}
		}

	}
}