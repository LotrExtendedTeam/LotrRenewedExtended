package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBronzeBirdcageTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedBirdCageTileEntityRenderer extends TileEntityRenderer<ExtendedBronzeBirdcageTileEntity> {

    public ExtendedBirdCageTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(ExtendedBronzeBirdcageTileEntity tileEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.5D, 0.0D, 0.5D);

        Entity entity = tileEntity.getOrCreateBirdEntity();
        if (entity != null) {
            float f = 0.90F;
            float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
            if ((double)f1 > 1.0D) {
                f /= f1;
            }

            pMatrixStack.translate(0.0D, 0.4F, 0.0D);
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float) MathHelper.lerp(pPartialTicks, 0.0D, 0.0D) * 10.0F));
            pMatrixStack.translate(0.0D, -0.2F, 0.0D);
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-30.0F));
            pMatrixStack.scale(f, f, f);
            Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0.0D, 0.0D, 0.0D, 0.0F, pPartialTicks, pMatrixStack, pBuffer, pCombinedLight);
        }

        pMatrixStack.popPose();


    }
}
