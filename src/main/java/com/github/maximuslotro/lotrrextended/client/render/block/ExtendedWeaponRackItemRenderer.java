package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class ExtendedWeaponRackItemRenderer extends TileEntityRenderer<ExtendedWeaponRackTileEntity>{

	public ExtendedWeaponRackItemRenderer(TileEntityRendererDispatcher p_i226006_1_) {
		super(p_i226006_1_);
	}

	@Override
	public void render(ExtendedWeaponRackTileEntity rack, float ticks, MatrixStack stack, IRenderTypeBuffer buffer, int light, int overlay) {
//		if(rack.hasItem()) {
//			ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//			stack.pushPose();
//			stack.translate(0.5D, 0.6D, 0.5D);
//			stack.scale(1F, 1F, 1F);
//			renderer.renderStatic(rack.getContainedItem(), TransformType.FIXED, light, overlay, stack, buffer);
//			stack.popPose();
//		}
		World world = rack.getLevel();
		boolean flag = world != null;
		BlockState blockstate = flag ? rack.getBlockState() : ExtendedBlocks.WEAPON_RACK.get().defaultBlockState();
		Block block = blockstate.getBlock();
		if(block instanceof ExtendedWeaponRackBlock) {
			stack.pushPose();
	         float f = blockstate.getValue(ChestBlock.FACING).toYRot();
	         stack.translate(0.5D, 0.5D, 0.5D);
	         stack.mulPose(Vector3f.YP.rotationDegrees(-f));
	         ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
	         stack.scale(1F, 1F, 1F);
	         renderer.renderStatic(new ItemStack(Items.DIAMOND_AXE), TransformType.FIXED, light, overlay, stack, buffer);
	         stack.popPose();
		}
	}
}
