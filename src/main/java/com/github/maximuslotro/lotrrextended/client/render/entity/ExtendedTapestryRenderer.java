package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.client.render.entity.tapestry.ExtendedLastAllianceTapestryModel;
import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedTapestryEntity;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedTapestryType;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedTapestryRenderer extends EntityRenderer<ExtendedTapestryEntity> {
	private ExtendedLastAllianceTapestryModel model =  new ExtendedLastAllianceTapestryModel();

	public ExtendedTapestryRenderer(EntityRendererManager pRenderManager) {
		super(pRenderManager);
	}

	@Override
	public void render(ExtendedTapestryEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		// Rotate the entity to face the correct direction
		pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - pEntityYaw));

		// Render texture and model to buffer
		IVertexBuilder ivertexbuilder = pBuffer.getBuffer(RenderType.entitySolid(this.getTextureLocation(pEntity)));
		ExtendedTapestryType paintingtype = pEntity.type;
		if(paintingtype==ExtendedTapestryType.LAST_ALLIANCE.get()) {
			// Compute translation offset based on yaw
			Direction dir =pEntity.getDirection();
			double offsetX = dir.getStepX() * 0.5D;
			double offsetZ = dir.getStepZ() * 0.5D;
			// Apply translation
			pMatrixStack.translate(offsetX, -1.5D, offsetZ);
			this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		pMatrixStack.popPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(ExtendedTapestryEntity pEntity) {
		return new ResourceLocation(pEntity.type.getRegistryName().getNamespace(), "textures/entity/tapestry/"+pEntity.type.getRegistryName().getPath()+".png");
	}


}