package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Optional;

public class ExtendedInvasionSpawnerRenderer extends EntityRenderer<ExtendedInvasionSpawnerEntity> {

    public ExtendedInvasionSpawnerRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(ExtendedInvasionSpawnerEntity entity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();

        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float)MathHelper.lerp(pPartialTicks, entity.spinO, entity.spin) * 10.0F));
        pMatrixStack.translate(0, 1.75F, 0);
        pMatrixStack.scale(3F, 3F, 3F);
        Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromKey(entity.getFactionKey());
        if (invasion.isPresent()) {
            ItemStack item = invasion.get().getStack();
            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer);
        }
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
