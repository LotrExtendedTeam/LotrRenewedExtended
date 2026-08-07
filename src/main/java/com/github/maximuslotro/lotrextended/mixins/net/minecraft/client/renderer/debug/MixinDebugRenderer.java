package com.github.maximuslotro.lotrextended.mixins.net.minecraft.client.renderer.debug;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientDebugToggles;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingDebugRenderer;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer {
	@Final
	@Shadow
	public PathfindingDebugRenderer pathfindingRenderer;

	@Inject(method = "render", at = @At("RETURN"))
	public void render(MatrixStack pMatrixStack, IRenderTypeBuffer.Impl pBuffer, double pCamX, double pCamY, double pCamZ, CallbackInfo ci) {
		if(ExtendedClientConfig.isDevMode()) {
			if(ExtendedClientDebugToggles.renderNavPaths) {
				pathfindingRenderer.render(pMatrixStack, pBuffer, pCamX, pCamY, pCamZ);
			}
		}
	}
}
