package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

@Mixin(ArmorStandEntity.class)
public class MixinArmorStandEntity {

	@Inject(method = "interactAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;",
			at = @At("HEAD"))
	public void interactAt$Extended(PlayerEntity pPlayer, Vector3d pVec, Hand pHand, CallbackInfoReturnable<ActionResultType>  cir) {
		if(!((ArmorStandEntity)(Object)this).level.mayInteract(pPlayer, ((ArmorStandEntity)(Object)this).blockPosition())){
			cir.setReturnValue(ActionResultType.PASS);
		}
	}
}
