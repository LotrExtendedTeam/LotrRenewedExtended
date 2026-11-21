package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.client.render.ExtendedRenderTypes;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;

public class ExtendedMEBannerTileEntityRenderer extends TileEntityRenderer<ExtendedMEBannerTileEntity> {
	private static final float PX = 1.0f / 16.0f;

	public ExtendedMEBannerTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}

	@Override
	public void render(ExtendedMEBannerTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		Minecraft minecraft = Minecraft.getInstance();
		if(!tileEntity.isActiveHalf() || !minecraft.options.renderDebug || tileEntity.getProtectionRadius() < 1) {
			return;
		}else if(minecraft.player.isSpectator() || minecraft.player.isCreative() || (tileEntity.getPlacedPlayerUUID() != null && tileEntity.getPlacedPlayerUUID().equals(minecraft.player.getUUID()))) {
			Direction direction = tileEntity.getBlockState().getValue(ExtendedMEBannerBlock.FACING);
			boolean small = tileEntity.getBlockState().getValue(ExtendedMEBannerBlock.SMALL);

			float poleTopPx = small ? 32.0f : 48.0f;
			float bannerTopPx = small ? 31.0f : 45.0f;
			float bannerBottomPx = small ? 2.0f : 10.0f;

			matrixStack.pushPose();
			IVertexBuilder builder = buffer.getBuffer(ExtendedRenderTypes.THROUGH_WALL_QUADS);
			// Base
			//renderSolidCubeQuads(matrixStack, builder, 0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f, 0.1f, 0.8f, 1.0f, 0.45f);
			// Pole
			//renderSolidCubeQuads(matrixStack, builder, 0.4375f, 0.125f, 0.4375f, 0.5625f, poleTopPx * PX, 0.5625f, 0.1f, 0.8f, 1.0f, 0.45f);
			renderBase(matrixStack, builder, 0.1f, 0.6f, 1.0f, 1.0f);//0.55f);
			renderPole(matrixStack, builder, 2.0f, poleTopPx, 0.1f, 0.6f, 1.0f, 1.0f);//0.55f);
			renderBanner(matrixStack, builder, direction, bannerBottomPx, bannerTopPx, 0.1f, 0.6f, 1.0f, 1.0f);//0.55f);
			matrixStack.popPose();
		}
	}

	private void renderBase(MatrixStack matrixStack, IVertexBuilder builder, float red, float green, float blue, float alpha) {
		renderSolidCubeQuads(matrixStack, builder, 0.0f, 0.0f, 0.0f, 16.0f * PX, 2.0f * PX, 16.0f * PX, red, green, blue, alpha);
	}

	private void renderPole(MatrixStack matrixStack, IVertexBuilder builder, float minYPx, float maxYPx, float red, float green, float blue, float alpha) {
		renderSolidCubeQuads(matrixStack, builder, 7.0f * PX, minYPx * PX, 7.0f * PX, 9.0f * PX, maxYPx * PX, 9.0f * PX, red, green, blue, alpha);
	}

	private void renderBanner(MatrixStack matrixStack, IVertexBuilder builder, Direction facing, float minYPx, float maxYPx, float red, float green, float blue, float alpha) {
		float minX, maxX, minZ, maxZ;
		float minY = minYPx * PX;
		float maxY = maxYPx * PX;
		switch (facing) {
		case NORTH:
			minX = 0.0f;
			maxX = 16.0f * PX;
			minZ = 5.0f * PX;
			maxZ = 11.0f * PX;
			break;
		case SOUTH: // +Z face
			minX = 0.0f;
			maxX = 16.0f * PX;
			minZ = 5.0f * PX;
			maxZ = 11.0f * PX;
			break;
		case WEST: // -X face
			minX = 5.0f * PX;
			maxX = 11.0f * PX;
			minZ = 0.0f;
			maxZ = 16.0f * PX;
			break;
		case EAST: // +X face
		default:
			minX = 5.0f * PX;
			maxX = 11.0f * PX;
			minZ = 0.0f;
			maxZ = 16.0f * PX;
			break;
		}
		renderSolidCubeQuads(matrixStack, builder, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
	}

	private void renderSolidCubeQuads(MatrixStack matrixStack, IVertexBuilder builder, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
		Matrix4f mat = matrixStack.last().pose();
		// Bottom (-Y)
		quad(builder, mat, minX, minY, maxZ, maxX, minY, maxZ, maxX, minY, minZ, minX, minY, minZ, red, green, blue, alpha);
		// Top (+Y)
		quad(builder, mat, minX, maxY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, minX, maxY, maxZ, red, green, blue, alpha);
		// North (-Z)
		quad(builder, mat, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, minX, minY, minZ, red, green, blue, alpha);
		// South (+Z)
		quad(builder, mat, minX, minY, maxZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, red, green, blue, alpha);
		// West (-X)
		quad(builder, mat, minX, minY, minZ, minX, maxY, minZ, minX, maxY, maxZ, minX, minY, maxZ, red, green, blue, alpha);
		// East (+X)
		quad(builder, mat, maxX, minY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, maxX, minY, minZ, red, green, blue, alpha);
	}

	private void quad(IVertexBuilder builder, Matrix4f mat, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3,  float x4, float y4, float z4, float r, float g, float b, float a) {
		builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();
		builder.vertex(mat, x2, y2, z2).color(r, g, b, a).endVertex();
		builder.vertex(mat, x3, y3, z3).color(r, g, b, a).endVertex();
		builder.vertex(mat, x4, y4, z4).color(r, g, b, a).endVertex();
	}
}