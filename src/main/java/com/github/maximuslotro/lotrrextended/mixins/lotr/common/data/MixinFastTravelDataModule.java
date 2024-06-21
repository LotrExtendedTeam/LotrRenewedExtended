package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import lotr.common.event.LOTRFastTravelWaypointEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

import lotr.common.data.FastTravelDataModule;
import lotr.common.world.map.Waypoint;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
			LotrExtendedMod.INSTANCE.getModBus().post(event);
		}
	}
}
