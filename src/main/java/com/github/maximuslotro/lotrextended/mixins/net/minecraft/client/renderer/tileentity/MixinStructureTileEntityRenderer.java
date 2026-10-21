package com.github.maximuslotro.lotrextended.mixins.net.minecraft.client.renderer.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.StructureTileEntityRenderer;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(value=StructureTileEntityRenderer.class, priority=1001)
public class MixinStructureTileEntityRenderer {
	/**
	 * Redirects the call to {@link StructureBlockTileEntity#getShowAir()} to also include
	 * a configurable client-side flag for forcing air visibility.
	 *
	 * @param tileEntity The structure block tile entity.
	 * @return True if air blocks should be shown, either by default or via forced config.
	 * @Redirect (method = "render(Lnet/minecraft/tileentity/StructureBlockTileEntity;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/StructureBlockTileEntity;getShowAir()Z"))
	 */
	@Redirect(method = "render(Lnet/minecraft/tileentity/StructureBlockTileEntity;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/StructureBlockTileEntity;getShowAir()Z"))
	private boolean injected(StructureBlockTileEntity tileEntity) {
		return tileEntity.getShowAir() || ExtendedClientConfig.forceStructureBlockAirViewing.get();
	}
	/**
	 * Overwrites the original method for rendering invisible structure blocks (like air or structure voids),
	 * adding support for rendering additional custom invisible block types such as {@code GRASS_VOID}.
	 *
	 * <p>This implementation renders differently colored bounding boxes depending on block type:
	 * <ul>
	 *   <li>Air: Light blue</li>
	 *   <li>Structure Void: Red</li>
	 *   <li>Grass Void: Green</li>
	 *   <li>Outline Mode: Black wireframe</li>
	 * </ul>
	 *
	 * @author maximuslotro
	 * @reason Read Above
	 * @param tileEntity The structure block tile entity.
	 * @param vertexBuilder The vertex builder used for rendering lines.
	 * @param relativePos The relative position offset.
	 * @param isOutlineMode Whether to render in outline (wireframe) mode.
	 * @param matrixStack The matrix stack for positioning.
	 */
	@SuppressWarnings("deprecation")
	@Overwrite()
	private void renderInvisibleBlocks(StructureBlockTileEntity tileEntity, IVertexBuilder vertexBuilder, BlockPos relativePos, boolean isOutlineMode, MatrixStack matrixStack) {
		IBlockReader iblockreader = tileEntity.getLevel();
		BlockPos blockpos = tileEntity.getBlockPos();
		BlockPos blockpos1 = blockpos.offset(relativePos);

		for(BlockPos blockpos2 : BlockPos.betweenClosed(blockpos1, blockpos1.offset(tileEntity.getStructureSize()).offset(-1, -1, -1))) {
			BlockState blockstate = iblockreader.getBlockState(blockpos2);
			boolean flag = blockstate.isAir();
			boolean flag1 = blockstate.is(Blocks.STRUCTURE_VOID);
			// Extended Start
			boolean flag2 = blockstate.is(ExtendedBlocks.GRASS_VOID.get());
			if (flag || flag1 || flag2) {
				// Extended End
				float f = flag ? 0.05F : 0.0F;
				double d0 = (double)((float)(blockpos2.getX() - blockpos.getX()) + 0.45F - f);
				double d1 = (double)((float)(blockpos2.getY() - blockpos.getY()) + 0.45F - f);
				double d2 = (double)((float)(blockpos2.getZ() - blockpos.getZ()) + 0.45F - f);
				double d3 = (double)((float)(blockpos2.getX() - blockpos.getX()) + 0.55F + f);
				double d4 = (double)((float)(blockpos2.getY() - blockpos.getY()) + 0.55F + f);
				double d5 = (double)((float)(blockpos2.getZ() - blockpos.getZ()) + 0.55F + f);
				if (isOutlineMode) {
					// IDK TODO figure out what this does
					WorldRenderer.renderLineBox(matrixStack, vertexBuilder, d0, d1, d2, d3, d4, d5, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
				} else if (flag) {
					// Air
					WorldRenderer.renderLineBox(matrixStack, vertexBuilder, d0, d1, d2, d3, d4, d5, 0.5F, 0.5F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F);
					// Extended Start
				} else if (flag2) {
					// Grass Void
					WorldRenderer.renderLineBox(matrixStack, vertexBuilder, d0, d1, d2, d3, d4, d5, 0.25F, 0.5F, 0.25F, 1.0F, 0.25F, 0.5F, 0.25F);
					// Extended End
				}else {
					// Structure Void
					WorldRenderer.renderLineBox(matrixStack, vertexBuilder, d0, d1, d2, d3, d4, d5, 1.0F, 0.25F, 0.25F, 1.0F, 1.0F, 0.25F, 0.25F);
				}
			}
		}

	}
}
