package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.renderer.tileentity;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStrawBedTileEntity;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

@Mixin(ItemStackTileEntityRenderer.class)
public class MixinItemStackTileEntityRenderer {
	private final ExtendedStrawBedTileEntity strawBed = new ExtendedStrawBedTileEntity();

	@Inject(method = "renderByItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V", at = @At("HEAD"), cancellable = true)
	public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_, CallbackInfo ci) {
		Item item = p_239207_1_.getItem();
	      if (item instanceof BlockItem) {
	    	  Block block = ((BlockItem)item).getBlock();
	    	  }
	      }
	         Block block = ((BlockItem)item).getBlock();
	         if (block instanceof AbstractSkullBlock) {
	            GameProfile gameprofile = null;
	            if (p_239207_1_.hasTag()) {
	               CompoundNBT compoundnbt = p_239207_1_.getTag();
	               if (compoundnbt.contains("SkullOwner", 10)) {
	                  gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
	               } else if (compoundnbt.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundnbt.getString("SkullOwner"))) {
	                  GameProfile gameprofile1 = new GameProfile((UUID)null, compoundnbt.getString("SkullOwner"));
	                  gameprofile = SkullTileEntity.updateGameprofile(gameprofile1);
	                  compoundnbt.remove("SkullOwner");
	                  compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
	               }
	            }

	            SkullTileEntityRenderer.renderSkull((Direction)null, 180.0F, ((AbstractSkullBlock)block).getType(), gameprofile, 0.0F, p_239207_3_, p_239207_4_, p_239207_5_);
	         } else {
	            TileEntity tileentity;
	            if (block instanceof AbstractBannerBlock) {
	               this.banner.fromItem(p_239207_1_, ((AbstractBannerBlock)block).getColor());
	               tileentity = this.banner;
	            } else if (block instanceof BedBlock) {
	               this.bed.setColor(((BedBlock)block).getColor());
	               tileentity = this.bed;
	            } else if (block == Blocks.CONDUIT) {
	               tileentity = this.conduit;
	            } else if (block == Blocks.CHEST) {
	               tileentity = this.chest;
	            } else if (block == Blocks.ENDER_CHEST) {
	               tileentity = this.enderChest;
	            } else if (block == Blocks.TRAPPED_CHEST) {
	               tileentity = this.trappedChest;
	            } else {
	               if (!(block instanceof ShulkerBoxBlock)) {
	                  return;
	               }

	               DyeColor dyecolor = ShulkerBoxBlock.getColorFromItem(item);
	               if (dyecolor == null) {
	                  tileentity = DEFAULT_SHULKER_BOX;
	               } else {
	                  tileentity = SHULKER_BOXES[dyecolor.getId()];
	               }
	            }

	            TileEntityRendererDispatcher.instance.renderItem(tileentity, p_239207_3_, p_239207_4_, p_239207_5_, p_239207_6_);
	}
}
