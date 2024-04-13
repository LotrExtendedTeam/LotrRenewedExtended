package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ExtendedWeaponRackItemRenderer extends TileEntityRenderer<ExtendedWeaponRackTileEntity>{

	public ExtendedWeaponRackItemRenderer(TileEntityRendererDispatcher p_i226006_1_) {
		super(p_i226006_1_);
	}

	@Override
	public void render(ExtendedWeaponRackTileEntity rack, float ticks, MatrixStack stack, IRenderTypeBuffer buffer, int light, int overlay) {
		if(rack.hasItem()) {
			ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
			stack.pushPose();
			stack.translate(0.5D, 0.6D, 0.5D);
			stack.scale(1F, 1F, 1F);
			renderer.renderStatic(rack.getContainedItem(), TransformType.FIXED, light, overlay, stack, buffer);
			stack.popPose();
		}
		else {
			ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
			stack.pushPose();
			stack.translate(0.5D, 0.6D, 0.5D);
			stack.scale(1F, 1F, 1F);
			renderer.renderStatic(new ItemStack(Items.DIAMOND_AXE), TransformType.FIXED, light, overlay, stack, buffer);
			stack.popPose();
		}
	}
}
