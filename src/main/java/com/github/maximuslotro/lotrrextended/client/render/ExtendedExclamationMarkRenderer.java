package com.github.maximuslotro.lotrrextended.client.render;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.utils.ExtendedColorUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import lotr.common.entity.npc.ExtendedIFarmhand;
import lotr.common.entity.npc.ExtendedINPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderLivingEvent;

public class ExtendedExclamationMarkRenderer {
	private static final ResourceLocation EXCLAMATION_TEXTURE = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/exclamation_mark.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(EXCLAMATION_TEXTURE);
	private static final float[] RED = ExtendedColorUtil.unpackRGB(0xE40808);
	private static final float SCALE = 0.035F;

	private static float[] getMarkColor(LivingEntity entity) {
		if(entity instanceof ExtendedIFarmhand && entity instanceof ExtendedINPCEntity && ((ExtendedINPCEntity)entity).getFarmhandInfo().hasWarning()) {
			return RED;
		}
		/*
		if(entity instanceof NPCEntity) {
			return ((NPCEntity)entity).getFaction().getColorComponents();
		}*/
		return null;
	}

	public static void onPostRenderEntity(final RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
		LivingEntity entity = event.getEntity();
		float[] color = getMarkColor(entity);
		if (color == null) {
			return;
		}
		Minecraft mc = Minecraft.getInstance();
		if(mc.options.hideGui) {
			return;
		}
		EntityRendererManager renderMgr = mc.getEntityRenderDispatcher();
		if (!ForgeHooksClient.isNameplateInRenderDistance(entity, renderMgr.distanceToSqr(entity))) {
			return;
		}
		int rInt = (int) (color[0] * 255.0F);
		int gInt = (int) (color[1] * 255.0F);
		int bInt = (int) (color[2] * 255.0F);

		MatrixStack matrixStack = event.getMatrixStack();
		matrixStack.pushPose();

		float height = entity.getBbHeight() + 0.5F;
		matrixStack.translate(0.0D, height, 0.0D);
		matrixStack.mulPose(renderMgr.cameraOrientation());
		matrixStack.scale(SCALE, SCALE, SCALE);
		float xLeft = -3.0F, xRight = 3.0F, yBottom = 0.0F, yTop = 16.0F;
		// UV coordinates (6 pixels out of 16px total width = 0.375)
		float uMin = 0.375F, uMax = 0.0F, vMin = 0.0F, vMax = 1.0F;

		MatrixStack.Entry entry = matrixStack.last();
		Matrix4f poseMatrix = entry.pose();
		Matrix3f normalMatrix = entry.normal();

		IRenderTypeBuffer bufferSource = event.getBuffers();
		IVertexBuilder builder = bufferSource.getBuffer(RENDER_TYPE);
		int light = event.getLight();

		// Quad drawn counter-clockwise (bottom-left, top-left, top-right, bottom-right)
		vertex(builder, poseMatrix, normalMatrix, xLeft, yBottom, rInt, gInt, bInt, uMin, vMax, light);
		vertex(builder, poseMatrix, normalMatrix, xLeft, yTop, rInt, gInt, bInt, uMin, vMin, light);
		vertex(builder, poseMatrix, normalMatrix, xRight, yTop, rInt, gInt, bInt, uMax, vMin, light);
		vertex(builder, poseMatrix, normalMatrix, xRight, yBottom, rInt, gInt, bInt, uMax, vMax, light);

		matrixStack.popPose();
	}
	private static void vertex(IVertexBuilder builder, Matrix4f poseMatrix, Matrix3f normalMatrix, float x, float y, int r, int g, int b, float u, float v, int packedLight) {
		builder.vertex(poseMatrix, x, y, 0.0F).color(r, g, b, 255) .uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
	}
}
