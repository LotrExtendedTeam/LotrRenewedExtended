package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneTextSignBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedStoneTextSignTileEntityRenderer extends TileEntityRenderer<ExtendedStoneTextSignTileEntity> {
	private static final int OUTLINE_RENDER_DISTANCE = 256;

	public ExtendedStoneTextSignTileEntityRenderer(TileEntityRendererDispatcher p_i226014_1_) {
		super(p_i226014_1_);
	}

	/**
	 * Use method from {@link net.minecraft.client.renderer.tileentity.SignTileEntityRenderer} but remove standing sign logic, and modify sign to render against block
	 * */
	@Override
	public void render(ExtendedStoneTextSignTileEntity pBlockEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
		BlockState blockstate = pBlockEntity.getBlockState();
		pMatrixStack.pushPose();
		pMatrixStack.translate(0.5D, 0.5D, 0.5D);
		float f4 = -blockstate.getValue(ExtendedStoneTextSignBlock.FACING).toYRot();
		pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(f4));
		pMatrixStack.translate(0.0D, -0.3125D, -0.4975D);
		//pMatrixStack.translate(0.0D, -0.3125D, -0.4375D);
		pMatrixStack.translate(0.0D, 0.33333334F, 0.0D);
		float f2 = 0.010416667F;
		pMatrixStack.scale(f2, -f2, f2);
		FontRenderer fontrenderer = this.renderer.getFont();
		int textColor = getDarkColor(pBlockEntity);
		int light = pCombinedLight;
		if (pBlockEntity.hasGlowingText()) {
			textColor = pBlockEntity.getColor().getTextColor();
			light = 15728880;
		}
		for(int row = 0; row < 8; ++row) {
			IReorderingProcessor charSequence = pBlockEntity.getRenderMessage(row, (messageComponent) -> {
				List<IReorderingProcessor> list = fontrenderer.split(messageComponent, 90);
				return list.isEmpty() ? IReorderingProcessor.EMPTY : list.get(0);
			});
			if (charSequence != null) {
				float xPos = -fontrenderer.width(charSequence) / 2;
				fontrenderer.drawInBatch(charSequence, xPos, (row * 11 - 40), textColor, false, pMatrixStack.last().pose(), pBuffer, false, 0, light);
			}
		}
		pMatrixStack.popPose();
	}

	private static int getDarkColor(ExtendedStoneTextSignTileEntity pBlockEntity) {
		int i = pBlockEntity.getColor().getTextColor();
		double d0 = 0.4D;
		int j = (int)((double)NativeImage.getR(i) * 0.4D);
		int k = (int)((double)NativeImage.getG(i) * 0.4D);
		int l = (int)((double)NativeImage.getB(i) * 0.4D);
		return NativeImage.combine(0, l, k, j);
	}
}