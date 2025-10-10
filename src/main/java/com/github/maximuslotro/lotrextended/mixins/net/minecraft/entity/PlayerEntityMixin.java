package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity;


import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void feastMode(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir) {
        if(ExtendedServerConfig.enableLegacyFeastMode.get()) {
            cir.setReturnValue(true);
        }else {
            cir.setReturnValue(false);
        }
    }

}
