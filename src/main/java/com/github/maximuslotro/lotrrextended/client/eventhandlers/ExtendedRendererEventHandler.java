package com.github.maximuslotro.lotrrextended.client.eventhandlers;

import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import com.github.maximuslotro.lotrrextended.client.render.ExtendedHiredUnitHpBar;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ExtendedRendererEventHandler {

	@SubscribeEvent
	public static void renderHpBar(RenderNameplateEvent event) {
		if (event.getEntity() instanceof NPCEntity && ((ExtendedNPCEntity)event.getEntity()).getUnitInfo().isNpcActiveDuty()) {
			if (ExtendedClientProxy.usersUUID.equals(null) || !ExtendedClientProxy.usersUUID.equals(((ExtendedNPCEntity)event.getEntity()).getUnitInfo().getOwnerUUID())) {
				return;
			}

			Minecraft minecraft = Minecraft.getInstance();
			if(minecraft.options.hideGui) {
				return;
			}
			MatrixStack matrixStackIn = event.getMatrixStack();

			double d0 = minecraft.getEntityRenderDispatcher().distanceToSqr(event.getEntity());
			FontRenderer fontrenderer = event.getEntityRenderer().getFont();
			IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
			Matrix4f matrix4f = matrixStackIn.last().pose();

			if (ForgeHooksClient.isNameplateInRenderDistance(event.getEntity(), d0)) {
				float f = event.getEntity().getBbHeight() + 1.2F;
				matrixStackIn.pushPose();
				matrixStackIn.translate(0.0D, f, 0.0D);
				matrixStackIn.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
				matrixStackIn.scale(-0.025F, -0.025F, 0.025F);

				ExtendedHiredUnitHpBar.draw((LivingEntity) event.getEntity(),
					100 / 100.0F, // text scale default 1X
					matrixStackIn,
					fontrenderer
					);

				matrixStackIn.popPose();
				RenderSystem.disableDepthTest();
			}
		}
	}
	@SubscribeEvent
	public void onPreRenderGameOverlay(final RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getInstance();
		if(LOTRDimensions.isModDimension(mc.level)) {
			if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !ExtendedServerConfig.enableVanillaXp.get()) {
				event.setCanceled(true);
				return;
			}
		}
	}
}