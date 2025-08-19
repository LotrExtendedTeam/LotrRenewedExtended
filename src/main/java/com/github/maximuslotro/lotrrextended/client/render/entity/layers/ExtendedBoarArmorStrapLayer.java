package com.github.maximuslotro.lotrrextended.client.render.entity.layers;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedHorseBoarModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBoarArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Based upon {@link LeatherHorseArmorLayer}
 * 
 */
public class ExtendedBoarArmorStrapLayer extends LayerRenderer<ExtendedHorseBoarEntity, ExtendedHorseBoarModel<ExtendedHorseBoarEntity>> {
	private final ExtendedHorseBoarModel<ExtendedHorseBoarEntity> model = new ExtendedHorseBoarModel<>(0.1F);
	private final ResourceLocation strap = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/animals/boar/armor/boar_armor_strap.png");

	public ExtendedBoarArmorStrapLayer(IEntityRenderer<ExtendedHorseBoarEntity, ExtendedHorseBoarModel<ExtendedHorseBoarEntity>> p_i50937_1_) {
		super(p_i50937_1_);
	}

	@Override
	public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, ExtendedHorseBoarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		ItemStack itemstack = pLivingEntity.getArmor();
		if (itemstack.getItem() instanceof ExtendedBoarArmorItem) {
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
			this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(strap));
			this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0F);
		}
	}
}