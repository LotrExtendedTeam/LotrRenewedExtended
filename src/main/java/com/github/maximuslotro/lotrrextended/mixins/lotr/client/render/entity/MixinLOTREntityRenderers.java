package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.entity;

import lotr.client.render.entity.ExtendedHobbitFarmerRenderer;
import lotr.client.render.entity.ExtendedHobbitBartenderRenderer;
import lotr.client.render.entity.ExtendedOddmentCollectorRenderer;
import lotr.client.render.entity.ExtendedDunedainRangerRenderer;
import lotr.client.render.entity.LOTREntityRenderers;
import lotr.common.init.ExtendedEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LOTREntityRenderers.class)
public class MixinLOTREntityRenderers {

    @Inject(method = "registerEntityRenderers()V", at = @At("TAIL"), remap=false)
    private static void addAdditionalEntityRenderers(CallbackInfo ci) {
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedOddmentCollectorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedDunedainRangerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.THROWN_CONKER.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
    }

    //@Inject(method = "registerTileEntityRenderers()V", at = @At("TAIL"), remap=false)
    //private static void addAdditionalTileEntityRenderers(CallbackInfo ci) {
        //ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer<ExtendedStoneChestTileEntity>::new);
    //}

}