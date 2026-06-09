package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.ExtendedVanillaProtectionHandlers;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.ProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.ExtendedDevServerEventHandlers;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity extends LivingEntity {

	// Virtual constructor will be discarded on compile
	protected MixinArmorStandEntity(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
		super(p_i48577_1_, p_i48577_2_);
	}

	@Inject(method = "interactAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;",
			at = @At("HEAD"), cancellable = true)
	public void interactAt$Extended(PlayerEntity pPlayer, Vector3d pVec, Hand pHand, CallbackInfoReturnable<ActionResultType> cir) {
		if(!this.level.mayInteract(pPlayer, this.blockPosition())){
			cir.setReturnValue(ActionResultType.PASS);
			return;
		}
		if (!this.level.isClientSide && !pPlayer.isSpectator()) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(this.level, this.blockPosition(), new ProtectionInstance.PlayerProtection(pPlayer)).isNotAllowed()) {
				cir.setReturnValue(ActionResultType.PASS);
				return;
			}
		}
		if (!this.level.isClientSide && ExtendedDevServerEventHandlers.devArmorStandInterract((ArmorStandEntity)(Object)this, pPlayer)) {
			cir.setReturnValue(ActionResultType.PASS);
			return;
		}
	}

	@Inject(method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
	public void interact$Extended(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
		if (!this.level.isClientSide && !this.removed && !this.isInvulnerableTo(pSource)) {
			if(pSource.getEntity() instanceof PlayerEntity && ExtendedVanillaProtectionHandlers.isUnderSpawnProtection((ServerWorld) this.level, this.blockPosition(), (PlayerEntity) pSource.getEntity(), true, true, false)) {
				cir.setReturnValue(false);
				return;
			}else if (ExtendedVanillaProtectionHandlers.isUnderSpawnProtection((ServerWorld) this.level, this.blockPosition(), null, false, false, false)) {
				cir.setReturnValue(false);
				return;
			}else if (ExtendedBannerProtectionManager.getProtectionStatus(this.level, this.blockPosition(), new ProtectionInstance.DamageSourceProtection(pSource)).isNotAllowed()) {
				cir.setReturnValue(false);
				return;
			}else if(pSource.getEntity() instanceof PlayerEntity && ExtendedDevServerEventHandlers.devArmorStandInterract((ArmorStandEntity)(Object)this, (PlayerEntity) pSource.getEntity())) {
				cir.setReturnValue(false);
				return;
			}
		}
	}
}
