package com.github.maximuslotro.lotrrextended.client.render.item;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ExtendedItemRenderHelper<T extends TileEntity> extends ItemStackTileEntityRenderer {

  private final Supplier<T> tileentity;

  public ExtendedItemRenderHelper(Supplier<T> tileentity) {
    this.tileentity = tileentity;
  }

  @Override
  public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
    TileEntityRendererDispatcher.instance.renderItem(this.tileentity.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
  }
}