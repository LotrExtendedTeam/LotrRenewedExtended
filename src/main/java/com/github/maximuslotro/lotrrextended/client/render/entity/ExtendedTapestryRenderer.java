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
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedTapestryRenderer extends EntityRenderer<ExtendedTapestryEntity> {
	private static final ExtendedLastAllianceTapestryModel allianceModel =  new ExtendedLastAllianceTapestryModel();

	public ExtendedTapestryRenderer(EntityRendererManager pRenderManager) {
		super(pRenderManager);
	}

	@Override
	public void render(ExtendedTapestryEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		Direction direction = pEntity.getDirection();
		Vector3d vector3d = this.getRenderOffset(pEntity, pPartialTicks);
		pMatrixStack.translate(-vector3d.x(), -vector3d.y(), -vector3d.z());
		double d0 = 0.46875D;
		pMatrixStack.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);
		if(direction.getAxis()==Axis.X) {
			pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(180F));
		}else if(direction.getAxis()==Axis.Z) {
			pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
		}
		pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(pEntity.xRot));
		pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - pEntity.yRot));
		// Rotate the entity to face the correct direction
		//pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - pEntityYaw));

		// Render texture and model to buffer
		IVertexBuilder ivertexbuilder = pBuffer.getBuffer(RenderType.entitySolid(this.getTextureLocation(pEntity)));
		ExtendedTapestryType paintingtype = pEntity.type;
		if(paintingtype==ExtendedTapestryType.LAST_ALLIANCE.get()) {
			allianceModel.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
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