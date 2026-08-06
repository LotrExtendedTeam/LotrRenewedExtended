package com.github.maximuslotro.lotrextended.mixins.net.minecraft.client.multiplayer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.entity.npc.ExtendedIRidableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerController;

@Mixin(PlayerController.class)
public class MixinPlayerController {

	@Final
	@Shadow
	private  Minecraft minecraft;
	
	@Inject(method = "isServerControlledInventory()Z", at = @At("HEAD"), remap=true, cancellable = true)
	public void isServerControlledInventory$Extended(CallbackInfoReturnable<Boolean> cir) {
		if(this.minecraft.player.isPassenger() && this.minecraft.player.getVehicle() instanceof ExtendedIRidableEntity) {
			cir.setReturnValue(true);
		}
	}
}
