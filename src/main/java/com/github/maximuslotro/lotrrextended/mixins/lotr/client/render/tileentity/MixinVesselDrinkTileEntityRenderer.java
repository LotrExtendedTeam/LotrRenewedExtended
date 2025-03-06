package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.model.vessel.VesselDrinkModel;
import lotr.client.render.tileentity.VesselDrinkTileEntityRenderer;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.item.VesselType;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

@Mixin(VesselDrinkTileEntityRenderer.class)
public abstract class MixinVesselDrinkTileEntityRenderer<T extends VesselDrinkTileEntity> extends TileEntityRenderer<T> {

	public MixinVesselDrinkTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
		super(p_i226006_1_);
	}

	@Shadow(remap=false)
	private void renderLiquidSurface(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float x0, float z0, float x1, float z1, float y, int light, int overlay) {}

	@Shadow(remap=false)
	private void renderLiquidSurface(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float width, float y, int light, int overlay) {}

	@Shadow(remap=false)
	private Direction getRotationToMatchBlockstateJson(Direction stateFacing) {return null;}

	@Override
	public void render(T vessel, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (!vessel.isEmpty()) {
			ItemStack vesselItem = vessel.getVesselItem();
			VesselType vesselType = vessel.getVesselType();
			RenderSystem.enableRescaleNormal();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			matStack.pushPose();
			matStack.translate(0.5D, 0.0D, 0.5D);
			Direction rotateDir = getRotationToMatchBlockstateJson(vessel.getBlockState().getValue(VesselDrinkBlock.FACING));
			matStack.mulPose(Vector3f.YP.rotationDegrees(rotateDir.toYRot()));
			matStack.translate(-0.5D, 0.0D, -0.5D);
			matStack.scale(0.0625F, 0.0625F, 0.0625F);
			TextureAtlasSprite liquidIcon = VesselDrinkModel.getLiquidIconFor(vesselItem);
			RenderType renderType = RenderType.entitySolid(liquidIcon.atlas().location());
			IVertexBuilder vb = buffer.getBuffer(renderType);
			if(vesselType == VesselType.WOODEN_MUG || vesselType == VesselType.CERAMIC_MUG) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 10, 3.0F, 5.25F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.GOLDEN_GOBLET || vesselType == VesselType.SILVER_GOBLET || vesselType == VesselType.COPPER_GOBLET || vesselType == VesselType.WOODEN_CUP) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 2.25F, 6.0F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.ALE_HORN || vesselType == VesselType.GOLDEN_ALE_HORN) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 9.5F, 6.875F, 12.5F, 9.125F, 6.375F, combinedLight, combinedOverlay);
			}else if(vesselType == VesselType.valueOf("SKULL_GOBLET")) {
				this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 12, 6.0F, 9.0F, combinedLight, combinedOverlay);
			}
			matStack.popPose();
			RenderSystem.disableBlend();
			RenderSystem.enableCull();
			RenderSystem.disableRescaleNormal();
		}

	}
}
