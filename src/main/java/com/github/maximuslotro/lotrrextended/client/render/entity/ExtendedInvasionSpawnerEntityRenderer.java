package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedRespawnerModel;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedRespawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ExtendedInvasionSpawnerEntityRenderer extends EntityRenderer<ExtendedInvasionSpawnerEntity> {

    public ExtendedInvasionSpawnerEntityRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(ExtendedInvasionSpawnerEntity entity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();

        pMatrixStack.translate(0, 1.75F, 0);
        pMatrixStack.scale(3F, 3F, 3F);
        ItemStack heldItem = new ItemStack(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 1);
        Minecraft.getInstance().getItemRenderer().renderStatic(heldItem, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer);

        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedInvasionSpawnerEntity entity) {
        return null;
    }

    @Override
    protected boolean shouldShowName(ExtendedInvasionSpawnerEntity entity) {
        return false;
    }
}
