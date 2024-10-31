package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedBannerModel;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedBannerRenderer extends EntityRenderer<ExtendedBannerEntity> {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/imgnazgulscreamman.png");
	private ExtendedBannerModel model =  new ExtendedBannerModel();
	public ExtendedBannerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	@Override
	public void render(ExtendedBannerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		RenderType rendertype = shouldHide ? null : model.renderType(TEXTURE_LOCATION);
		if (rendertype != null) {
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
			this.model.renderExtendedToBuffer(pEntity, pMatrixStack, ivertexbuilder, pPartialTicks, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		pMatrixStack.popPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedBannerEntity entity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected boolean shouldShowName(ExtendedBannerEntity pEntity) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		return !shouldHide;
	}
}