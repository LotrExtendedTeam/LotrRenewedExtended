package com.github.maximuslotro.lotrrextended.client.render;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.utils.ExtendedColorUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedIFarmhand;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderLivingEvent;

public class ExtendedExclamationMarkRenderer {
	private static final ResourceLocation EXCLAMATION_TEXTURE = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/exclamation_mark.png");
	private static final float[] RED = ExtendedColorUtil.unpackRGB(0xE40808);

	private static float[] getMarkColor(LivingEntity entity) {
		if(entity instanceof ExtendedIFarmhand && entity instanceof ExtendedINPCEntity && ((ExtendedINPCEntity)entity).getFarmhandInfo().hasWarning()) {
			return RED;
		}
		if(entity instanceof NPCEntity) {
			return ((NPCEntity)entity).getFaction().getColorComponents();
		}
		return null;
	}

	public static void onPostRenderEntity(final RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
		LivingEntity entity = event.getEntity();
		float[] color = getMarkColor(entity);
		if (color == null) {
			return;
		}
		Minecraft mc = Minecraft.getInstance();
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
		matrixStack.scale(-0.025F, -0.025F, 0.025F);

		RenderSystem.enableDepthTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		mc.getTextureManager().bind(EXCLAMATION_TEXTURE);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		float uMin = 0.0F, uMax = 0.25F, vMin = 0.0F, vMax = 1.0F;
		float xLeft = -2.0F, xRight = 2.0F, yBottom = 0.0F, yTop = 16.0F;

		Matrix4f poseMatrix = matrixStack.last().pose();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder builder = tessellator.getBuilder();

		builder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
		int light = event.getLight();

		builder.vertex(poseMatrix, xLeft, yTop, 0.0F).color(rInt, gInt, bInt, 255).uv(uMin, vMin).uv2(light).endVertex();
		builder.vertex(poseMatrix, xLeft, yBottom, 0.0F).color(rInt, gInt, bInt, 255).uv(uMin, vMax).uv2(light).endVertex();
		builder.vertex(poseMatrix, xRight, yBottom, 0.0F).color(rInt, gInt, bInt, 255).uv(uMax, vMax).uv2(light).endVertex();
		builder.vertex(poseMatrix, xRight, yTop, 0.0F).color(rInt, gInt, bInt, 255).uv(uMax, vMin).uv2(light).endVertex();
		tessellator.end();

		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();
		matrixStack.popPose();
	}
}
