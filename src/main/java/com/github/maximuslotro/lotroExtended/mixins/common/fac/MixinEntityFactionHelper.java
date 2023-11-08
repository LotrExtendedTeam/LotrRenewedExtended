package com.github.maximuslotro.lotroExtended.mixins.common.fac;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotroExtended.hooks.MixinHooks;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(EntityFactionHelper.class)
public class MixinEntityFactionHelper {
	
	@Inject(method = "canPlayerCauseDamageToTarget(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Z)Z", 
			at =@At("HEAD"),
			remap=false,
			cancellable = true)
	private static void checkFellowshipPvp(PlayerEntity attacker, Entity target, boolean warnFriendlyFire, CallbackInfoReturnable<Boolean> cir) {
		if (target instanceof PlayerEntity && ((PlayerEntity) target) != attacker) {
			ExtendedFellowshipDataModule fellowshipModule = MixinHooks.getFellowshipData(LOTRLevelData.getSidedData(attacker));
			List<ExtendedFellowship> fellowships = fellowshipModule.getFellowships();
	        for (ExtendedFellowship fs : fellowships) {
	          if (fs.isPreventPVP() && fs.isFellowshipMember(((PlayerEntity) target).getUUID()))
	        	  cir.setReturnValue(false);
	        }
		}
	}
}
