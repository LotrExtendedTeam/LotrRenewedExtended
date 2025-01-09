package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.data.FastTravelDataModule;
import lotr.common.event.LOTRFastTravelWaypointEvent;
import lotr.common.world.map.Waypoint;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;

@Mixin(FastTravelDataModule.class)
public abstract class MixinFastTravelDataModule {

	/**
	 * @author maximuslotro
	 * @reason because injecting into a code tree is hard
	 * */
	@Inject(at = @At(value = "HEAD"), method="fastTravelTo", remap = false)
	private void fastTravelTo(ServerPlayerEntity player, Waypoint waypoint, CallbackInfo info) {
		ServerWorld world = player.getLevel();
		BlockPos orig = new BlockPos(player.getX(), player.getY(), player.getZ());
		BlockPos travelPos = waypoint.getTravelPosition(world, player);
		if (travelPos != null) {
			LOTRFastTravelWaypointEvent event = new LOTRFastTravelWaypointEvent(player, world, orig, travelPos);
			MinecraftForge.EVENT_BUS.post(event);
		}
	}
}
