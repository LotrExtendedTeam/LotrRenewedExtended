package com.github.maximuslotro.lotrextended.mixins.net.minecraft.client.renderer.entity.layers;

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
 * Handles Ranger Transparency effect for armor rendering without requiring additional classes
 * that may crash with custom armor.
 * <p>
 * This mixin targets {@link BipedArmorLayer} and provides two main features:
 * <ul>
 *     <li>Tracks whether an entity is cloaked during armor rendering.</li>
 *     <li>Conditionally cancels armor rendering if the entity is cloaked, while still rendering
 *         a translucent version of the armor for visual effects.</li>
 * </ul>
 *
 * @param <T> the type of {@link LivingEntity} rendered with this armor layer
 * @param <A> the type of {@link BipedModel} used for rendering the armor
 * @Mixin (BipedArmorLayer.class)
 */
@Mixin(BipedArmorLayer.class)
public abstract class MixinBipedArmorLayer<T extends LivingEntity, A extends BipedModel<T>> {

	/**
	 * Local variable to track cloaking state for Ranger armor logic.
	 * @Unique
	 */
	@Unique
	private boolean isCloakedExtended;

	/**
	 * Injects at the start of {@code renderArmorPiece} to check whether the entity
	 * is cloaked. Necessary because direct type casting to {@link ExtendedRangerModel}
	 * does not reliably expose the cloaking state.
	 *
	 * @param renderStack   the matrix stack used for rendering transformations
	 * @param renderBuffer  the buffer used to draw the armor piece
	 * @param entity        the entity wearing the armor
	 * @param slot          the equipment slot currently being rendered
	 * @param pPackedLight  the packed light value for lighting calculations
	 * @param armorModel    the armor model being rendered
	 * @param ci            callback info, allows cancelling or modifying method behavior
	 */
	@Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", 
			at = @At("HEAD"), remap=true)
	private void renderArmorPieceLotrExtended(MatrixStack renderStack, IRenderTypeBuffer renderBuffer, T entity, EquipmentSlotType slot, int pPackedLight, A armorModel, CallbackInfo ci) {
		if(entity instanceof ExtendedDunedainRangerEntity) {
			isCloakedExtended = ((ExtendedDunedainRangerEntity)entity).isCloaked();
		}
	}

	/**
	 * Injects at the start of {@code renderModel} to conditionally cancel armor rendering
	 * for cloaked entities. When cancelled, a translucent version of the armor is rendered
	 * to give a "ghostly" cloaking effect.
	 *
	 * @param renderStack   the matrix stack used for transformations during rendering
	 * @param renderBuffer  the buffer into which the armor model will be rendered
	 * @param pPackedLight  the packed light value for lighting effects
	 * @param hasFoil       whether the armor has the enchanted glint effect
	 * @param armorModel    the model being rendered
	 * @param colorR        red channel for tinting
	 * @param colorG        green channel for tinting
	 * @param colorB        blue channel for tinting
	 * @param armorResource the texture resource for this armor model
	 * @param ci            callback info, allows cancelling the original method
	 * @Inject(method = "renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V", at = @At("HEAD"), cancellable = true, remap=false)
	 */
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