package com.github.maximuslotro.lotroExtended.mixins.client.render.entity;

import lotr.client.render.entity.HobbitBartenderRenderer;
import lotr.client.render.entity.LOTREntityRenderers;
import lotr.common.init.ExtendedNpcs;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LOTREntityRenderers.class)
public class MixinLOTREntityRenderers {

    @Inject(method = "registerEntityRenderers()V", at = @At("HEAD"), remap=false)
    private static void addAdditionalRenderers(CallbackInfo ci) {
        RenderingRegistry.registerEntityRenderingHandler(ExtendedNpcs.HOBBIT_BARTENDER.get(), HobbitBartenderRenderer::new);
    }

}