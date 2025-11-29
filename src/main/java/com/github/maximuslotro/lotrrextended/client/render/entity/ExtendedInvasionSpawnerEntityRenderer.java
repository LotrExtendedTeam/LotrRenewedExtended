package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.init.ExtendedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedInvasionSpawnerEntityRenderer extends EntityRenderer<ExtendedInvasionSpawnerEntity> {

    public ExtendedInvasionSpawnerEntityRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(ExtendedInvasionSpawnerEntity entity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();

        // Apply rotation - interpolate between previous and current rotation for smooth animation
        float interpolatedYaw = entity.yRotO + (entity.yRot - entity.yRotO) * pPartialTicks;
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(-interpolatedYaw));

        pMatrixStack.translate(0, 1.75F, 0);
        pMatrixStack.scale(3F, 3F, 3F);
        // TODO: Use Faction data to pick a specific item for rendering
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
