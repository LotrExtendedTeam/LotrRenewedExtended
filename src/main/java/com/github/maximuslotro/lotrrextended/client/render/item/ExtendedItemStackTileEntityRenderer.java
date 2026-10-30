package com.github.maximuslotro.lotrrextended.client.render.item;

import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBedTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
	public static final ExtendedItemStackTileEntityRenderer INSTANCE = new ExtendedItemStackTileEntityRenderer();
	private ExtendedBedTileEntity bed;

	@Override
	public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack pStack, IRenderTypeBuffer pBuffer, int combinedLightIn, int combinedOverlayIn) {
		ensureSetup();
		Item item = itemStackIn.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem)item).getBlock();
			TileEntity tileentity = null;
			if (block instanceof BedBlock) {
				this.bed.setBedType(((ExtendedBedBlock)block).getBedType());
				tileentity = this.bed;
			}
			if(tileentity!=null) {
				TileEntityRendererDispatcher.instance.renderItem(tileentity, pStack, pBuffer, combinedLightIn, combinedOverlayIn);
			}
		}
	}

	public void ensureSetup() {
		if(bed==null) {
			bed = new ExtendedBedTileEntity();
		}
	}
}