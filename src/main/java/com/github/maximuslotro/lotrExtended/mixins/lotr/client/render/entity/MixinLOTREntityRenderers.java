package com.github.maximuslotro.lotrExtended.mixins.lotr.client.render.entity;

import lotr.client.render.entity.ExtendedHobbitFarmerRenderer;
import lotr.client.render.entity.ExtendedHobbitBartenderRenderer;
import lotr.client.render.entity.LOTREntityRenderers;
import lotr.common.init.ExtendedEntities;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LOTREntityRenderers.class)
public class MixinLOTREntityRenderers {

    @Inject(method = "registerEntityRenderers()V", at = @At("HEAD"), remap=false)
    private static void addAdditionalRenderers(CallbackInfo ci) {
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);
    }

}