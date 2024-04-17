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
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class ExtendedWeaponRackItemRenderer extends TileEntityRenderer<ExtendedWeaponRackTileEntity>{
	public static double transX = 0.5D;
	public static double transY = 0.5D;
	public static double transZ = 0.5D;
	public static float scaleX = 1F;
	public static float scaleY = 1F;
	public static float scaleZ = 1F;
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
			Direction dir = blockstate.getValue(ExtendedWeaponRackBlock.FACING);
			AttachFace face = blockstate.getValue(ExtendedWeaponRackBlock.FACE);
			stack.pushPose();
			if(face == AttachFace.WALL) {
				if(dir==Direction.NORTH) {
					
				}else if(dir==Direction.SOUTH) {
					
				}else if(dir==Direction.EAST) {
					
				}else if(dir==Direction.WEST) {
					
				}
			}else if(face == AttachFace.CEILING){
				if(dir==Direction.NORTH) {
					
				}else if(dir==Direction.SOUTH) {
					
				}else if(dir==Direction.EAST) {
					
				}else if(dir==Direction.WEST) {
					
				}
			}else {
				if(dir==Direction.NORTH) {
					
				}else if(dir==Direction.SOUTH) {
					
				}else if(dir==Direction.EAST) {
					
				}else if(dir==Direction.WEST) {
					
				}
			}
			float f = blockstate.getValue(ExtendedWeaponRackBlock.FACING).toYRot();
			stack.translate(transX, transY, transZ);
			stack.mulPose(Vector3f.YP.rotationDegrees(-f));
			ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
			stack.scale(scaleX, scaleY, scaleZ);
			renderer.renderStatic(new ItemStack(Items.DIAMOND_AXE), TransformType.FIXED, light, overlay, stack, buffer);
			stack.popPose();
		}
	}
}
