package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

@Mixin(ItemFrameEntity.class)
public class MixinItemFrameEntity {

	@Inject(method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;",
			at = @At("HEAD"))
	public void interact$Extended(PlayerEntity pPlayer, Hand pHand, CallbackInfoReturnable<ActionResultType>  cir) {
		if(!((ItemFrameEntity)(Object)this).level.mayInteract(pPlayer, ((ItemFrameEntity)(Object)this).blockPosition())){
			cir.setReturnValue(ActionResultType.PASS);
		}
	}
}
