package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.entity;

import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedChestTileEntityRenderer;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneChestTileEntity;
import lotr.client.render.entity.ExtendedHobbitFarmerRenderer;
import lotr.client.render.entity.ExtendedHobbitBartenderRenderer;
import lotr.client.render.entity.LOTREntityRenderers;
import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedTileEntities;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LOTREntityRenderers.class)
public class MixinLOTREntityRenderers {

    @Inject(method = "registerEntityRenderers()V", at = @At("HEAD"), remap=false)
    private static void addAdditionalRenderers(CallbackInfo ci) {
        // entities
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);

        // tile entities
        ClientRegistry.bindTileEntityRenderer((TileEntityType) ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer<ExtendedStoneChestTileEntity>::new);
    }

}