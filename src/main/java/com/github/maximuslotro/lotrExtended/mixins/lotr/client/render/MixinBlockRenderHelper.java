package com.github.maximuslotro.lotrExtended.mixins.lotr.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.client.render.BlockRenderHelper;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

@Mixin(BlockRenderHelper.class)
public class MixinBlockRenderHelper {

	@Inject(method = "setupBlocks()V", at = @At("TAIL"), remap=false)
    private static void addAdditionalBlockRenderers(CallbackInfo ci) {
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.BLACKBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.BLUEBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.CRANBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.ELDERBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.RASPBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.WILDBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.GREEN_FIRE_BLOCK.get(), RenderType.cutout());

    }
}
