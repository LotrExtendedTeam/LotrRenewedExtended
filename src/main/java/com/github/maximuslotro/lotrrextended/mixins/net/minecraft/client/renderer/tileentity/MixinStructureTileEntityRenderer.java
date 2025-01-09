package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.renderer.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;

import net.minecraft.client.renderer.tileentity.StructureTileEntityRenderer;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(StructureTileEntityRenderer.class)
public class MixinStructureTileEntityRenderer {

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/StructureBlockTileEntity;getShowAir()Z"))
	private boolean injected(StructureBlockTileEntity tileEntity) {
		return tileEntity.getShowAir() || ExtendedClientConfig.forceStructureBlockAirViewing.get();
	}
}
