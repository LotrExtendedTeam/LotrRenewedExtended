package com.github.maximuslotro.lotroExtended.mixins.client.render.entity;

import lotr.client.render.entity.HobbitRenderer;
import lotr.client.render.entity.LOTREntityRenderers;
import lotr.common.init.ExtendedNpcs;
import lotr.common.init.LOTREntities;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LOTREntityRenderers.class)
public class MixinLOTREntityRenderers {

    @Inject(method = "registerEntityRenderers()V", at = @At("HEAD"), remap=false)
    private static void addAdditionalRenderers(CallbackInfo ci) {
        // TODO: Update to bartender renderer
        RenderingRegistry.registerEntityRenderingHandler((EntityType) ExtendedNpcs.HOBBIT_BARTENDER.get(), HobbitRenderer::new);
    }

}