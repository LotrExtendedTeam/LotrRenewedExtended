package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedStructureSpawnerModel;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStructureSpawnerRenderer extends EntityRenderer<ExtendedStructureSpawnerEntity> {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/block/spawner_block.png");
	private ExtendedStructureSpawnerModel model =  new ExtendedStructureSpawnerModel();
	public ExtendedStructureSpawnerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	@Override
	public void render(ExtendedStructureSpawnerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
	      pMatrixStack.pushPose();
	      float f = MathHelper.rotLerp(pPartialTicks, pEntity.yRotO, pEntity.yRot);
	      this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, f, 0.0F);
	      Minecraft minecraft = Minecraft.getInstance();
	      boolean flag = !pEntity.isInvisibleTo(minecraft.player);
	      RenderType rendertype = flag ? model.renderType(TEXTURE_LOCATION) : null;
	      if (rendertype != null) {
	         IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
	         this.model.renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, flag ? 0.15F : 1.0F);
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
		return !pEntity.isInvisibleTo(minecraft.player);
	}
}