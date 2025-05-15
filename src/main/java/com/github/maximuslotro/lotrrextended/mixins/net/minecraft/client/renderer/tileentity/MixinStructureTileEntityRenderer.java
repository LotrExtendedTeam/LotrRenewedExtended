package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.renderer.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.init.ExtendedBlocks;
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
	@Redirect(method = "render(Lnet/minecraft/tileentity/StructureBlockTileEntity;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/StructureBlockTileEntity;getShowAir()Z"))
	private boolean injected(StructureBlockTileEntity tileEntity) {
		return tileEntity.getShowAir() || ExtendedClientConfig.forceStructureBlockAirViewing.get();
	}

	/**
	 * @author maximuslotro
	 * @reason I'm Lazy
	 * */
	@SuppressWarnings("deprecation")
	@Overwrite()
	private void renderInvisibleBlocks(StructureBlockTileEntity p_228880_1_, IVertexBuilder p_228880_2_, BlockPos p_228880_3_, boolean p_228880_4_, MatrixStack p_228880_5_) {
		IBlockReader iblockreader = p_228880_1_.getLevel();
		BlockPos blockpos = p_228880_1_.getBlockPos();
		BlockPos blockpos1 = blockpos.offset(p_228880_3_);

		for(BlockPos blockpos2 : BlockPos.betweenClosed(blockpos1, blockpos1.offset(p_228880_1_.getStructureSize()).offset(-1, -1, -1))) {
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
				if (p_228880_4_) {
					// IDK TODO figure out what this does
					WorldRenderer.renderLineBox(p_228880_5_, p_228880_2_, d0, d1, d2, d3, d4, d5, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
				} else if (flag) {
					// Air
					WorldRenderer.renderLineBox(p_228880_5_, p_228880_2_, d0, d1, d2, d3, d4, d5, 0.5F, 0.5F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F);
					// Extended Start
				} else if (flag2) {
					// Grass Void
					WorldRenderer.renderLineBox(p_228880_5_, p_228880_2_, d0, d1, d2, d3, d4, d5, 0.25F, 0.5F, 0.25F, 1.0F, 0.25F, 0.5F, 0.25F);
					// Extended End
				}else {
					// Structure Void
					WorldRenderer.renderLineBox(p_228880_5_, p_228880_2_, d0, d1, d2, d3, d4, d5, 1.0F, 0.25F, 0.25F, 1.0F, 1.0F, 0.25F, 0.25F);
				}
			}
		}

	}
}
