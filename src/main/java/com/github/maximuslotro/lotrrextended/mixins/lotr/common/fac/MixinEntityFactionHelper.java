package com.github.maximuslotro.lotrrextended.mixins.lotr.common.fac;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
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
			ExtendedFellowshipDataModule fellowshipModule = ((ExtendedLOTRPlayerData)LOTRLevelData.getSidedData(attacker)).getFellowshipData();
			List<ExtendedFellowship> fellowships = fellowshipModule.getFellowships();
	        for (ExtendedFellowship fs : fellowships) {
	          if (fs.isPreventPVP() && fs.isInFellowship(((PlayerEntity) target).getUUID()))
	        	  cir.setReturnValue(false);
	        }
		}
	}
}
