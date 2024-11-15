package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedBannerModel;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
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
		pMatrixStack.pushPose();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		RenderType rendertype = shouldHide ? null : model.renderType(TEXTURE_LOCATION);
		if (rendertype != null) {
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
			this.model.renderExtendedToBuffer(pEntity, pMatrixStack, ivertexbuilder, pPartialTicks, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		IVertexBuilder ivertexBuilder = pBuffer.getBuffer(RenderType.lines());
		if (minecraft.options.renderDebug && pEntity.getProtectionRange()>0) {

			RenderSystem.lineWidth(10.0f);
	        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
	        if(buffer == null)
	        	return;
	        IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
	        ClientPlayerEntity player = minecraft.player;
	        double x = player.xOld + (player.getX() - player.xOld) * pPartialTicks;
	        double y = player.yOld + (player.getY() - player.yOld) * pPartialTicks;
	        double z = player.zOld + (player.getZ() - player.zOld) * pPartialTicks;
			minecraft.player.sendMessage(new StringTextComponent(pEntity.getProtectionArea().toString()), Util.NIL_UUID);
			minecraft.player.sendMessage(new StringTextComponent("X: "+x), Util.NIL_UUID);
			minecraft.player.sendMessage(new StringTextComponent("Y: "+y), Util.NIL_UUID);
			minecraft.player.sendMessage(new StringTextComponent("Z: "+z), Util.NIL_UUID);
			minecraft.player.sendMessage(new StringTextComponent("x: "+pEntity.getX()), Util.NIL_UUID);
			minecraft.player.sendMessage(new StringTextComponent("y: "+pEntity.getY()), Util.NIL_UUID);
			minecraft.player.sendMessage(new StringTextComponent("z: "+pEntity.getZ()), Util.NIL_UUID);

	        pMatrixStack.pushPose();
	        pMatrixStack.translate(-x, -y, -z);
	        WorldRenderer.renderLineBox(pMatrixStack, builder, pEntity.getProtectionArea(), 1.0f, 1.0f, 1.0f, 1.0f);
	        pMatrixStack.popPose();
	        RenderSystem.disableDepthTest();
	        buffer.endBatch(RenderType.LINES);
	        
		}
		pMatrixStack.popPose();
		//super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

//	if (minecraft.options.renderDebug && pEntity.getProtectionRange()>0) {
//		RenderSystem.lineWidth(10.0f);
//        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
//        if(buffer == null)
//        	return;
//        IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
//        ClientPlayerEntity player = minecraft.player;
//        double x = player.xOld + (player.getX() - player.xOld) * pPartialTicks;
//        double y = player.yOld + (player.getY() - player.yOld) * pPartialTicks;
//        double z = player.zOld + (player.getZ() - player.zOld) * pPartialTicks;
//		minecraft.player.sendMessage(new StringTextComponent("Show "+pEntity.getProtectionRange()+" "+pEntity.getProtectionArea().toString()+"X:"+x+", Y:"+y+", Z:"+z), Util.NIL_UUID);
//        pMatrixStack.pushPose();
//        pMatrixStack.translate(x, -y, z);
//        WorldRenderer.renderLineBox(pMatrixStack, builder, pEntity.getProtectionArea(), 1.0f, 1.0f, 1.0f, 1.0f);
//        pMatrixStack.popPose();
//        RenderSystem.disableDepthTest();
//        buffer.endBatch(RenderType.LINES);
//        
//	}
//	pMatrixStack.popPose();
//	//super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
//}
	@Override
	public ResourceLocation getTextureLocation(ExtendedBannerEntity entity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected boolean shouldShowName(ExtendedBannerEntity pEntity) {
		return false;
	}
}