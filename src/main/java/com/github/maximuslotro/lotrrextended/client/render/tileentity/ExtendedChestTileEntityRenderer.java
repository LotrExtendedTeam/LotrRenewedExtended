package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.client.render.block.BlockRendererSetup;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedSingleChestBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedChestTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedChestTileEntityRenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
	private final ModelRenderer lid;
	private final ModelRenderer bottom;
	private final ModelRenderer lock;
	private boolean xmasTextures;

	public ExtendedChestTileEntityRenderer(TileEntityRendererDispatcher p_i226008_1_) {
	      super(p_i226008_1_);

	      this.bottom = new ModelRenderer(64, 64, 0, 19);
	      this.bottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
	      this.lid = new ModelRenderer(64, 64, 0, 0);
	      this.lid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
	      this.lid.y = 9.0F;
	      this.lid.z = 1.0F;
	      this.lock = new ModelRenderer(64, 64, 0, 0);
	      this.lock.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
	      this.lock.y = 8.0F;
	   }

	   public void render(T tileEntity, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
	      World world = tileEntity.getLevel();
	      boolean flag = world != null;
	      BlockState blockstate = flag ? tileEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ExtendedSingleChestBlock.FACING, Direction.SOUTH);
	      Block block = blockstate.getBlock();
	      if (block instanceof ExtendedSingleChestBlock) {
	    	  ExtendedSingleChestBlock chestblock = (ExtendedSingleChestBlock) block;
	    	  
	         p_225616_3_.pushPose();
	         float f = blockstate.getValue(ExtendedSingleChestBlock.FACING).toYRot();
	         p_225616_3_.translate(0.5D, 0.5D, 0.5D);
	         p_225616_3_.mulPose(Vector3f.YP.rotationDegrees(-f));
	         p_225616_3_.translate(-0.5D, -0.5D, -0.5D);
	         
	         @SuppressWarnings("rawtypes")
			TileEntityMerger.ICallbackWrapper<? extends ExtendedChestTileEntity> icallbackwrapper;
	         if (flag) {
	            icallbackwrapper = chestblock.combine(blockstate, world, tileEntity.getBlockPos(), true);
	         } else {
	            icallbackwrapper = TileEntityMerger.ICallback::acceptNone;
	         }

	         float f1 = icallbackwrapper.<Float2FloatFunction>apply(ExtendedSingleChestBlock.opennessCombiner(tileEntity)).get(p_225616_2_);
	         f1 = 1.0F - f1;
	         f1 = 1.0F - f1 * f1 * f1;
	         int i = icallbackwrapper.<Int2IntFunction>apply(new DualBrightnessCallback<>()).applyAsInt(p_225616_5_);
	         
	         RenderMaterial rendermaterial = getMaterial(tileEntity);
	         IVertexBuilder ivertexbuilder = rendermaterial.buffer(p_225616_4_, RenderType::entityCutout);
	         this.renderChest(p_225616_3_, ivertexbuilder, this.lid, this.lock, this.bottom, f1, i, p_225616_6_);

	         p_225616_3_.popPose();
	    }
	}

	private void renderChest(MatrixStack p_228871_1_, IVertexBuilder p_228871_2_, ModelRenderer p_228871_3_, ModelRenderer p_228871_4_, ModelRenderer p_228871_5_, float p_228871_6_, int p_228871_7_, int p_228871_8_) {
		p_228871_3_.xRot = -(p_228871_6_ * ((float)Math.PI / 2F));
		p_228871_4_.xRot = p_228871_3_.xRot;
		p_228871_3_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
		p_228871_4_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
		p_228871_5_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
	}

	protected RenderMaterial getMaterial(T tileEntity) {
		if(tileEntity instanceof ExtendedStoneChestTileEntity) {
			return BlockRendererSetup.STONE_CHEST_MATERIAL;
		}
		return null;
	}
}