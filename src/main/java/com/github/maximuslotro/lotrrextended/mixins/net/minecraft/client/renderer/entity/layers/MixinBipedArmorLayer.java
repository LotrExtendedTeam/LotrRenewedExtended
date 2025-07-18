package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
 * Handles Ranger Transparency effect without the need for additional classes that crash with custom armor
 * @Mixin (BipedArmorLayer.class)
 * @param <T> the type of {@link LivingEntity} rendered with the armor layer
 * @param <A> the type of {@link BipedModel} used for rendering the armor
 * */
@Mixin(BipedArmorLayer.class)
public abstract class MixinBipedArmorLayer<T extends LivingEntity, A extends BipedModel<T>> {

	/**
	 * Local variable to know how to do cloaking logic
	 * */
	@Unique
	private boolean isCloakedExtended;

	/**
	 * @reason Injects into armor piece rendering to check if the entity is cloaked. Required because direct type casting to {@link ExtendedRangerModel} does not reliably expose the cloaking state.
	 * @param renderStack The matrix stack used for rendering transformations
	 * @param renderBuffer The rendering buffer used to draw the armor piece
	 * @param entity The entity wearing the armor
	 * @param slot The equipment slot currently being rendered
	 * @param pPackedLight Lightmap coordinate used for lighting the model
	 * @param armorModel The model of the armor piece
	 * @param ci Callback info to control method flow
	 * @Inject (method = "renderArmorPiece(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", at = @At("HEAD"), remap=true)
	 * */
	@Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", 
		at = @At("HEAD"), remap=true)
	private void renderArmorPieceLotrExtended(MatrixStack renderStack, IRenderTypeBuffer renderBuffer, T entity, EquipmentSlotType slot, int pPackedLight, A armorModel, CallbackInfo ci) {
		if(entity instanceof ExtendedDunedainRangerEntity) {
			isCloakedExtended = ((ExtendedDunedainRangerEntity)entity).isCloaked();
		}
	}

	/**
	 * @reason Injects into model rendering to cancel armor rendering if the entity is cloaked. This logic is only valid in the base ranger armor class and not in subclasses.
	 * @param renderStack The matrix stack used for transformations during rendering
	 * @param renderBuffer The buffer into which the armor model will be rendered
	 * @param pPackedLight The packed light value (lightmap) for lighting effects
	 * @param hasFoil Whether the armor has the enchanted "foil" glint effect
	 * @param armorModel The model being rendered
	 * @param colorR Red color channel for tinting the armor
	 * @param colorG Green color channel for tinting the armor
	 * @param colorB Blue color channel for tinting the armor
	 * @param armorResource The texture resource location used for this armor model
	 * @param ci Callback info that can cancel the method execution
	 * @Inject (method = "renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V", at = @At("HEAD"), cancellable = true, remap=false)
	 * */
	@Inject(method = "renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V", 
		at = @At("HEAD"), cancellable = true, remap=false)
	private void renderModelLotrExtended(MatrixStack renderStack, IRenderTypeBuffer renderBuffer, int pPackedLight, boolean hasFoil, A armorModel, float colorR, float colorG, float colorB, ResourceLocation armorResource, CallbackInfo ci) {
		if(armorModel instanceof ExtendedRangerModel) {
			if(isCloakedExtended){
				ci.cancel();
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(renderBuffer, RenderType.itemEntityTranslucentCull(armorResource), false, hasFoil);
				armorModel.renderToBuffer(renderStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, colorR, colorG, colorB, 0.6F);
			}
		}
	}
}
