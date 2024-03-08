package com.github.maximuslotro.lotrrextended.client.render.item;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ExtendedItemStackRenderer extends ItemStackTileEntityRenderer{
	public ExtendedItemStackRenderer(Supplier<TileEntity> chestRenderer) {
		this.chestRenderer = chestRenderer;
	}

	//public static final ExtendedItemStackRenderer instance = new ExtendedItemStackRenderer();
	private final Supplier<TileEntity> chestRenderer;

	@Override
	public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
		TileEntityRendererDispatcher.instance.renderItem(this.chestRenderer.get(), p_239207_3_, p_239207_4_, p_239207_5_, p_239207_6_);
	}
}
