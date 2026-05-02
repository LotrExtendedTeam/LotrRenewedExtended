package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.ProtectionInstance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Mixin(HangingEntity.class)
public abstract class MixinHangingEntity extends Entity {

	// Virtual constructor will be discarded on compile
	public MixinHangingEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}

	@Inject(method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
	public void interact$Extended(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
		if(!this.level.isClientSide && !this.isInvulnerableTo(pSource)) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(this.level, this.blockPosition(), new ProtectionInstance.DamageSourceProtection(pSource)).isNotAllowed()) {
				cir.setReturnValue(false);
				return;
			}
		}
	}
}
