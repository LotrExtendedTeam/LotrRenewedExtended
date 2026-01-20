package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(ItemFrameEntity.class)
public abstract class MixinItemFrameEntity extends Entity {

	// Virtual constructor will be discarded on compile
	public MixinItemFrameEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}

	@Shadow
	private boolean fixed;

	@Inject(method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;",
			at = @At("HEAD"), cancellable = true)
	public void interact$Extended(PlayerEntity pPlayer, Hand pHand, CallbackInfoReturnable<ActionResultType> cir) {
		if(!this.level.mayInteract(pPlayer, this.blockPosition())){
			cir.setReturnValue(ActionResultType.PASS);
			return;
		}
		if(!this.level.isClientSide && !fixed) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(this.level, this.blockPosition(), new SingleProtectionInstance.PlayerProtection(pPlayer)).isNotAllowed()) {
				cir.setReturnValue(ActionResultType.PASS);
				return;
			}
		}
	}

	@Inject(method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
	public void interact$Extended(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean>  cir) {
		if(!this.level.isClientSide && !fixed && !this.isInvulnerableTo(pSource)) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(this.level, this.blockPosition(), new SingleProtectionInstance.DamageSourceProtection(pSource)).isNotAllowed()) {
				cir.setReturnValue(false);
				return;
			}
		}
	}
}
