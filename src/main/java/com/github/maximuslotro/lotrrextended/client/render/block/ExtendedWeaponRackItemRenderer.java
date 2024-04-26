package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class ExtendedWeaponRackItemRenderer extends TileEntityRenderer<ExtendedWeaponRackTileEntity>{
	public static double transX = 0.5D;
	public static double transY = 0.5D;
	public static double transZ = 0.5D;
	public static float ROTX = 0.0F;
	public static float ROTY = 0.0F;
	public static float ROTZ = 0.0F;
	public ExtendedWeaponRackItemRenderer(TileEntityRendererDispatcher p_i226006_1_) {
		super(p_i226006_1_);
	}

	@Override
	public void render(ExtendedWeaponRackTileEntity rack, float ticks, MatrixStack stack, IRenderTypeBuffer buffer, int light, int overlay) {
		if(!rack.hasItem()) {
			return;
		}
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
					stack.translate(0.55D, 0.41D, 0.75D);
				}else if(dir==Direction.SOUTH) {
					stack.translate(0.45F, 0.41D, 0.25D);
				}else if(dir==Direction.EAST) {
					stack.translate(0.25D, 0.41D, 0.55D);
				}else if(dir==Direction.WEST) {
					stack.translate(0.75D, 0.41D, 0.45D);
				}
				if(dir==Direction.SOUTH) {
					stack.mulPose(Vector3f.YP.rotationDegrees(180F));
				}else if(dir==Direction.EAST) {
					stack.mulPose(Vector3f.YP.rotationDegrees(270F));
				}else if(dir==Direction.WEST) {
					stack.mulPose(Vector3f.YP.rotationDegrees(90F));
				}
			}else {
				if(face == AttachFace.CEILING){
					stack.translate(0.5D, 0.3D, 0.5D);
				}else {
					stack.translate(0.5D, 0.75D, 0.5D);
				}
				if(dir==Direction.NORTH) {
					stack.mulPose(Vector3f.YP.rotationDegrees(180F));
				}else if(dir==Direction.SOUTH) {
					stack.mulPose(Vector3f.YP.rotationDegrees(0F));
				}else if(dir==Direction.EAST) {
					stack.mulPose(Vector3f.YP.rotationDegrees(90F));
				}else if(dir==Direction.WEST) {
					stack.mulPose(Vector3f.YP.rotationDegrees(270F));
				}
			}
			stack.mulPose(Vector3f.ZP.rotationDegrees(225F));
			ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
			final ItemStack item = rack.getContainedItem().copy();
//			if(item.getItem() instanceof SpearItem) {
//				stack.scale(1.0F, 1.0F, 1.0F);
//			}else if(item.getItem() instanceof HammerItem && !(item.getItem() == LOTRItems.BLACKSMITH_HAMMER.get())){
//				stack.scale(1.0F, 1.0F, 1.0F);
//			}else if(item.getItem() instanceof LOTRSwordItem && !(item.getItem() instanceof DaggerItem)){
//				stack.scale(1.0F, 1.0F, 1.0F);
//			}else {
//				stack.scale(0.625F, 0.625F, 0.625F);
//			}
			if(item.getItem().is(ExtendedTags.Items.WEAPONRACK_ITEMS_FULL_SCALE)) {
				stack.scale(1.0F, 1.0F, 1.0F);
			}else if(item.getItem().is(ExtendedTags.Items.WEAPONRACK_ITEMS_HALF_SCALING)) {
				stack.scale(0.5F, 0.5F, 0.5F);
			}else {
				stack.scale(0.625F, 0.625F, 0.625F);
			}
			renderer.renderStatic(item, TransformType.FIXED, light, overlay, stack, buffer);
			stack.popPose();
		}
	}
}
