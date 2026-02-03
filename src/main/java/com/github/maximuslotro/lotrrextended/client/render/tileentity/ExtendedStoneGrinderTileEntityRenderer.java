package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneGrinderBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneGrinderTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStoneGrinderTileEntityRenderer<T extends ExtendedStoneGrinderTileEntity> extends TileEntityRenderer<T> {
	   public static final RenderMaterial GRINDER_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(LotrExtendedMod.MODID, "entity/grinder/base"));

	private final ModelRenderer stands;
	private final ModelRenderer drum;

	public ExtendedStoneGrinderTileEntityRenderer(TileEntityRendererDispatcher pRenderDispatcher) {
		super(pRenderDispatcher);

		stands = new ModelRenderer(64, 64, 0, 0);
		stands.setPos(0.0F, 16.0F, 0.0F);
		stands.texOffs(36, 0).addBox(-8.0F, 7.0F, -5.0F, 4.0F, 1.0F, 10.0F, 0.0F, false);
		stands.texOffs(56, 12).addBox(-7.5F, -2.0F, -1.5F, 1.0F, 9.0F, 3.0F, 0.0F, false);
		stands.texOffs(36, 28).addBox(4.0F, 7.0F, -5.0F, 4.0F, 1.0F, 10.0F, 0.0F, false);
		stands.texOffs(56, 52).addBox(6.5F, -2.0F, -1.5F, 1.0F, 9.0F, 3.0F, 0.0F, false);

		drum = new ModelRenderer(64, 64, 0, 0);
		drum.setPos(0.0F, 16.0F, 0.0F);
		drum.texOffs(1, 44).addBox(-6.0F, -5.0F, -5.0F, 12.0F, 10.0F, 10.0F, 0.0F, false);
		drum.texOffs(1, 35).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 8.0F, 1.0F, 0.0F, false);
		drum.texOffs(1, 26).addBox(-6.0F, -4.0F, 5.0F, 12.0F, 8.0F, 1.0F, 0.0F, false);
		drum.texOffs(0, 17).addBox(-6.0F, 5.0F, -4.0F, 12.0F, 1.0F, 8.0F, 0.0F, false);
		drum.texOffs(1, 8).addBox(-6.0F, -6.0F, -4.0F, 12.0F, 1.0F, 8.0F, 0.0F, false);
		drum.texOffs(0, 0).addBox(-8.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		drum.texOffs(11, 0).addBox(6.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void render(T pBlockEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
		BlockState blockstate = pBlockEntity.getLevel() != null ? pBlockEntity.getBlockState() : ExtendedBlocks.STONE_GRINDER.get().defaultBlockState().setValue(ExtendedStoneGrinderBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();
		if (block instanceof ExtendedStoneGrinderBlock) {
			pMatrixStack.pushPose();
			float f = blockstate.getValue(ExtendedStoneGrinderBlock.FACING).toYRot();
			pMatrixStack.translate(0.5D, 0.5D, 0.5D);
			pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(-f));
			pMatrixStack.translate(-0.5D, -0.5D, -0.5D);

			IVertexBuilder ivertexbuilder = GRINDER_TEXTURE.buffer(pBuffer, RenderType::entitySolid);
			stands.render(pMatrixStack, ivertexbuilder, pCombinedLight, pCombinedOverlay);
			drum.render(pMatrixStack, ivertexbuilder, pCombinedLight, pCombinedOverlay);
			pMatrixStack.popPose();
		}
	}
}