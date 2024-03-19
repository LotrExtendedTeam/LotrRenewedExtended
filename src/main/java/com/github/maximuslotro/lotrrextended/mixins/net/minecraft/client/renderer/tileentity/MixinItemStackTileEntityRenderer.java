package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.renderer.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStrawBedTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

//CURRENTLY DISABLED?NOT CALLED. IF NEEDED, ADD MIXIN TO mixin.lotrextended.json
@Mixin(ItemStackTileEntityRenderer.class)
public class MixinItemStackTileEntityRenderer {
	private final ExtendedStrawBedTileEntity strawBed = new ExtendedStrawBedTileEntity();

	@Inject(method = "renderByItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", at = @At("HEAD"), cancellable = true)
	public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_, CallbackInfo ci) {
		Item item = p_239207_1_.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem)item).getBlock();
			TileEntity tileentity = null;
			if (block == Blocks.CONDUIT) {
				tileentity = strawBed;
			}
			if(tileentity!=null) {
				TileEntityRendererDispatcher.instance.renderItem(tileentity, p_239207_3_, p_239207_4_, p_239207_5_, p_239207_6_);
				ci.cancel();
			}
		}
	}
}
