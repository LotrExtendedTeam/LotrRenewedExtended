package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedStructureSpawnerModel;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStructureSpawnerRenderer extends EntityRenderer<ExtendedStructureSpawnerEntity> {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/imgnazgulscreamman.png");
	private ExtendedStructureSpawnerModel model =  new ExtendedStructureSpawnerModel();
	public ExtendedStructureSpawnerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	@Override
	public void render(ExtendedStructureSpawnerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		RenderType rendertype = shouldHide ? null : model.renderType(TEXTURE_LOCATION);
		if (rendertype != null) {
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
			this.model.renderExtendedToBuffer(pEntity, pMatrixStack, ivertexbuilder, pPartialTicks, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			if (ExtendedClientConfig.enableDevMode.get() && !pEntity.getCMinBlockPos().equals(BlockPos.ZERO) && !pEntity.getCMaxBlockPos().equals(BlockPos.ZERO)&&
					!pEntity.getSMinBlockPos().equals(BlockPos.ZERO) && !pEntity.getSMaxBlockPos().equals(BlockPos.ZERO)) {
				IVertexBuilder lineBuilder = pBuffer.getBuffer(RenderType.lines());
				BlockPos minPosC = pEntity.getCMinBlockPos();
				BlockPos maxPosC = pEntity.getCMaxBlockPos();
				BlockPos minPosS = pEntity.getSMinBlockPos();
				BlockPos maxPosS = pEntity.getSMaxBlockPos();
				pMatrixStack.pushPose();
				WorldRenderer.renderLineBox(pMatrixStack, lineBuilder, minPosC.getX(), minPosC.getY(), minPosC.getZ(), maxPosC.getX(), maxPosC.getY(), maxPosC.getZ(), 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
				pMatrixStack.popPose();
				pMatrixStack.pushPose();
				WorldRenderer.renderLineBox(pMatrixStack, lineBuilder, minPosS.getX(), minPosS.getY(), minPosS.getZ(), maxPosS.getX(), maxPosS.getY(), maxPosS.getZ(), 0.9F, 0.9F, 0.9F, 1.0F, 0.2F, 0.2F, 0.2F);
				pMatrixStack.popPose();
			}
		}
		pMatrixStack.popPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedStructureSpawnerEntity entity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected boolean shouldShowName(ExtendedStructureSpawnerEntity pEntity) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		return !shouldHide;
	}
}