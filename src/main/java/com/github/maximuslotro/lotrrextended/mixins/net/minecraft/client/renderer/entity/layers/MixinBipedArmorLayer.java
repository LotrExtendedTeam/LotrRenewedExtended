package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.ExtendedRangerModel;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

/**
 * @reason Handles Ranger Transparency effect without the need for additional classes that crash with custom armor
 * @Mixin (BipedArmorLayer.class)
 * */
@Mixin(BipedArmorLayer.class)
public abstract class MixinBipedArmorLayer<T extends LivingEntity, A extends BipedModel<T>> {

	/**
	 * Local variable to know how to do cloaking logic
	 * */
	private boolean isCloakedExtended;

	/**
	 * @reason There needs to be a local check for ranger transparency, as ((ExtendedRangerModel<?>)armorModel).isCloaked does not work
	 * @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", at = @At("HEAD"), remap=true)
	 * */
	@Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", 
		at = @At("HEAD"), remap=true)
	private void renderArmorPieceLotrExtended(MatrixStack renderStack, IRenderTypeBuffer renderBuffer, T entity, EquipmentSlotType slot, int pPackedLight, A armorModel, CallbackInfo ci) {
		if(entity instanceof ExtendedDunedainRangerEntity) {
			isCloakedExtended = ((ExtendedDunedainRangerEntity)entity).isCloaked();
		}
	}

	/**
	 * @reason Ranger Armor needs to be transparent in only the base class, extends classes won't work
	 * @Inject(method = "renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V", at = @At("HEAD"), cancellable = true, remap=false)
	 * */
	@Inject(method = "renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V", 
		at = @At("HEAD"), cancellable = true, remap=false)
	private void renderModelLotrExtended(MatrixStack renderStack, IRenderTypeBuffer renderBuffer, int pPackedLight, boolean hasFoil, A armorModel, float colorR, float colorG, float colorB, ResourceLocation armorResource, CallbackInfo ci) {
		if(armorModel instanceof ExtendedRangerModel) {
			//if(((ExtendedRangerModel<?>)armorModel).isCloaked){
			if(isCloakedExtended){
				ci.cancel();
				System.out.println("CLOAKED");
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(renderBuffer, RenderType.itemEntityTranslucentCull(armorResource), false, hasFoil);
				armorModel.renderToBuffer(renderStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, colorR, colorG, colorB, 0.6F);
			}
		}
	}
}
