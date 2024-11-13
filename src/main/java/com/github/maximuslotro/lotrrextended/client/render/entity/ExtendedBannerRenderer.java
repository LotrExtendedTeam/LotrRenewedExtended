package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedBannerModel;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
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
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		RenderType rendertype = shouldHide ? null : model.renderType(TEXTURE_LOCATION);
		if (rendertype != null) {
			pMatrixStack.pushPose();
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
			this.model.renderExtendedToBuffer(pEntity, pMatrixStack, ivertexbuilder, pPartialTicks, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			pMatrixStack.popPose();
		}
        IVertexBuilder ivertexBuilder = pBuffer.getBuffer(RenderType.lines());
        if (pEntity.getProtectionRange()>0) {
           WorldRenderer.renderLineBox(pMatrixStack, ivertexBuilder, pEntity.getProtectionArea(), 0F, 255F, 0F, 1F);
        }
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedBannerEntity entity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected boolean shouldShowName(ExtendedBannerEntity pEntity) {
		return false;
	}
}