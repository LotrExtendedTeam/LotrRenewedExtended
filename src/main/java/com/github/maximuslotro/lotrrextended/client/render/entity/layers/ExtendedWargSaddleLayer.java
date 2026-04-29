package com.github.maximuslotro.lotrrextended.client.render.entity.layers;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.client.render.entity.model.WargModel;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HorseMarkingsLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.IEquipable;
import net.minecraft.util.ResourceLocation;

/**
 * Based upon {@link HorseMarkingsLayer}
 */
public class ExtendedWargSaddleLayer extends LayerRenderer<WargEntity, WargModel<WargEntity>> {
	private final WargModel<WargEntity> model = new WargModel<>(0.1F);
	private final ResourceLocation saddle = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/warg/saddle.png");

	public ExtendedWargSaddleLayer(IEntityRenderer<WargEntity, WargModel<WargEntity>> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, WargEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		if (!pEntity.isInvisible() && ((IEquipable)pEntity).isSaddled()) {
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
			this.model.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(saddle));
			this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0F);
		}
	}
}