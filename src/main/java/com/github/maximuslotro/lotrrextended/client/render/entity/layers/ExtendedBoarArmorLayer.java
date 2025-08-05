package com.github.maximuslotro.lotrrextended.client.render.entity.layers;

import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedHorseBoarModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBoarArmorItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedDyeableBoarArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

/**
 * Based upon {@link LeatherHorseArmorLayer}
 * 
 */
public class ExtendedBoarArmorLayer extends LayerRenderer<ExtendedHorseBoarEntity, ExtendedHorseBoarModel<ExtendedHorseBoarEntity>> {
	private final ExtendedHorseBoarModel<ExtendedHorseBoarEntity> model = new ExtendedHorseBoarModel<>(0.1F);

	public ExtendedBoarArmorLayer(IEntityRenderer<ExtendedHorseBoarEntity, ExtendedHorseBoarModel<ExtendedHorseBoarEntity>> p_i50937_1_) {
		super(p_i50937_1_);
	}

	@Override
	public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, ExtendedHorseBoarEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		ItemStack itemstack = pLivingEntity.getArmor();
		if (itemstack.getItem() instanceof ExtendedBoarArmorItem) {
			ExtendedBoarArmorItem horsearmoritem = (ExtendedBoarArmorItem)itemstack.getItem();
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
			this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
			float f;
			float f1;
			float f2;
			if (horsearmoritem instanceof ExtendedDyeableBoarArmorItem) {
				int i = ((ExtendedDyeableBoarArmorItem)horsearmoritem).getColor(itemstack);
				f = (float)(i >> 16 & 255) / 255.0F;
				f1 = (float)(i >> 8 & 255) / 255.0F;
				f2 = (float)(i & 255) / 255.0F;
			} else {
				f = 1.0F;
				f1 = 1.0F;
				f2 = 1.0F;
			}

			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(horsearmoritem.getTexture()));
			this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
		}
	}
}