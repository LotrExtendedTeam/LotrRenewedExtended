package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedThrowableAxeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedThrowableAxeRenderer extends EntityRenderer<ExtendedThrowableAxeEntity> {

	public ExtendedThrowableAxeRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	public ResourceLocation getEntityTexture(ExtendedThrowableAxeEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS;
	}
	@Override
	public void render(ExtendedThrowableAxeEntity Axe, float yaw, float ticks, MatrixStack matStack, IRenderTypeBuffer buf, int light) {
		matStack.pushPose();
		matStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(ticks, Axe.yRotO, Axe.yRot) - 90.0F));
		matStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(ticks, Axe.xRotO, Axe.xRot)));
		float shake = (float)Axe.shakeTime - ticks;
		if (shake > 0.0F) {
			float shakeAngle = -MathHelper.sin(shake * 3.0F) * shake;
			matStack.mulPose(Vector3f.ZP.rotationDegrees(shakeAngle));
		}
		matStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		matStack.mulPose(Vector3f.XP.rotationDegrees(100.0F));
		ItemStack AxeItem = Axe.getThrowingAxeItem();
		Minecraft.getInstance().getItemRenderer().renderStatic(AxeItem, TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matStack, buf);
		matStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedThrowableAxeEntity entity) {
		return null;
	}
}