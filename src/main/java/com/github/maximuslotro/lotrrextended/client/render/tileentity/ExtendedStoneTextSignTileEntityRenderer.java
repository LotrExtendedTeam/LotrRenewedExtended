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

   public ExtendedStoneTextSignTileEntityRenderer(TileEntityRendererDispatcher p_i226014_1_) {
      super(p_i226014_1_);
   }

   /**
    * Use method from {@link SignTileEntityRenderer} but remove standing sign logic, and modify sign to render against block
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
	   int i = pBlockEntity.getColor().getTextColor();
	   double d0 = 0.4D;
	   int j = (int)((double)NativeImage.getR(i) * d0);
	   int k = (int)((double)NativeImage.getG(i) * d0);
	   int l = (int)((double)NativeImage.getB(i) * d0);
	   int i1 = NativeImage.combine(0, l, k, j);
	   int j1 = 20;
	   FontRenderer fontrenderer = this.renderer.getFont();
	   for(int k1 = 0; k1 < 4; ++k1) {
		   IReorderingProcessor ireorderingprocessor = pBlockEntity.getRenderMessage(k1, (messageComponent) -> {
			   List<IReorderingProcessor> list = fontrenderer.split(messageComponent, 90);
			   return list.isEmpty() ? IReorderingProcessor.EMPTY : list.get(0);
		   });
		   if (ireorderingprocessor != null) {
			   float f3 = (float)(-fontrenderer.width(ireorderingprocessor) / 2);
			   fontrenderer.drawInBatch(ireorderingprocessor, f3, (float)(k1 * 10 - j1), i1, false, pMatrixStack.last().pose(), pBuffer, false, 0, pCombinedLight);
		   }
	   }
	   pMatrixStack.popPose();
   }
}