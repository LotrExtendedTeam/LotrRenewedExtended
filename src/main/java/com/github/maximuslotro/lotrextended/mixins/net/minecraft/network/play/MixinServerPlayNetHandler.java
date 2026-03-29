package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network.play;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.entity.npc.ExtendedRidableEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CEntityActionPacket;

@Mixin(ServerPlayNetHandler.class)
public class MixinServerPlayNetHandler {

	@Shadow
	public ServerPlayerEntity player;

	@Inject(
		method = "handlePlayerCommand",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/network/PacketThreadUtil;ensureRunningOnSameThread(Lnet/minecraft/network/IPacket;Lnet/minecraft/network/INetHandler;Lnet/minecraft/world/server/ServerWorld;)V",
			shift = At.Shift.AFTER
			),
		cancellable = true
		)
	public void handlePlayerCommand$Extneded(CEntityActionPacket packet, CallbackInfo ci) {
		if (packet.getAction() != CEntityActionPacket.Action.OPEN_INVENTORY) {
			return;
		}

		if (this.player.getVehicle() instanceof ExtendedRidableEntity) {
			((ExtendedRidableEntity)this.player.getVehicle()).openInventory(this.player);
		      this.player.resetLastActionTime();
		      ci.cancel();
		}
	}
}
