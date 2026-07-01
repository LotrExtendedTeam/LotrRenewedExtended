package com.github.maximuslotro.lotrrextended.client.render.entity.layers;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMountedDyeableArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.WargModel;
import lotr.common.entity.npc.ExtendedIRidableEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ExtendedWargArmorStrapLayer extends LayerRenderer<WargEntity, WargModel<WargEntity>> {
	private final WargModel<WargEntity> model = new WargModel<>(0.08F);
	private final ResourceLocation strap = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/warg/armor/leather_overlay.png");

	public ExtendedWargArmorStrapLayer(IEntityRenderer<WargEntity, WargModel<WargEntity>> p_i50937_1_) {
		super(p_i50937_1_);
	}

	@Override
	public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, WargEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		ItemStack itemstack = ((ExtendedIRidableEntity)pLivingEntity).getArmor();
		if (itemstack.getItem() instanceof ExtendedMountedDyeableArmorItem) {
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
			this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(strap));
			this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}