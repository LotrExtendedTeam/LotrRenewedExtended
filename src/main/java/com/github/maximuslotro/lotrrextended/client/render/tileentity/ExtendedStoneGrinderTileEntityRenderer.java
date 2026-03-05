package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedStoneGrinderModel;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneGrinderBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneGrinderTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.init.ExtendedBlocks;
import lotr.common.util.CalendarUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStoneGrinderTileEntityRenderer extends TileEntityRenderer<ExtendedStoneGrinderTileEntity> {
	private static final ResourceLocation GRINDER_CLOSED_TEXTURE = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/grinder/closed.png");
	private static final ResourceLocation GRINDER_OPEN_TEXTURE = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/grinder/open.png");
	private static boolean aprilFools = CalendarUtil.isAprilFools();
	ExtendedStoneGrinderModel model;
	
	public ExtendedStoneGrinderTileEntityRenderer(TileEntityRendererDispatcher pRenderDispatcher) {
		super(pRenderDispatcher);
		model = new ExtendedStoneGrinderModel();
	}

	@Override
	public void render(ExtendedStoneGrinderTileEntity pBlockEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
		BlockState blockstate = pBlockEntity.getLevel() != null ? pBlockEntity.getBlockState() : ExtendedBlocks.STONE_GRINDER.get().defaultBlockState().setValue(ExtendedStoneGrinderBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();
		if (block instanceof ExtendedStoneGrinderBlock) {
			boolean powered = blockstate.getValue(ExtendedStoneGrinderBlock.POWERED);
			if (aprilFools) {
				model.stands.xRot = (float)MathHelper.lerp(pPartialTicks, pBlockEntity.spinO, pBlockEntity.spin) * 10.0F;
			} else {
				model.drum.xRot = (float)MathHelper.lerp(pPartialTicks, pBlockEntity.spinO, pBlockEntity.spin) * 10.0F;
			}

			pMatrixStack.pushPose();
			// Move to center of block
			pMatrixStack.translate(0.5D, 1.5D, 0.5D);
			// Flip model (because ModelRenderer is upside-down in TESR)
			pMatrixStack.scale(1.0F, -1.0F, -1.0F);
			// Apply facing rotation
			float f = blockstate.getValue(ExtendedStoneGrinderBlock.FACING).toYRot();
			pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(f));
			// Render
			IVertexBuilder builder = pBuffer.getBuffer(model.renderType(/*pBlockEntity.isOpen>0 && */!powered ? GRINDER_OPEN_TEXTURE : GRINDER_CLOSED_TEXTURE));
			model.renderToBuffer(pMatrixStack, builder, pCombinedLight, pCombinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
			pMatrixStack.popPose();
		}
	}
}