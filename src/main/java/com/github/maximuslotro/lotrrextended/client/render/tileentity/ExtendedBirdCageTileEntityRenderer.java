package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.client.render.entity.ExtendedCrabanRenderer;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedCrabanModel;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedCrabanEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBirdcageTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class ExtendedBirdCageTileEntityRenderer extends TileEntityRenderer<ExtendedBirdcageTileEntity> {
    private final ExtendedCrabanModel<ExtendedCrabanEntity> birdModel = new ExtendedCrabanModel<>();

    public ExtendedBirdCageTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(ExtendedBirdcageTileEntity tileEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
        if (tileEntity.getLevel() == null) {
            return;
        }

        double gameTime = tileEntity.getLevel().getGameTime() + (double) pPartialTicks;
        float animationTime = (tileEntity.getLevel().getGameTime() + pPartialTicks) % 60;
        double hopDistance = 0.0;
        if (animationTime >= 0 && animationTime <= 20) {
            birdModel.setupHeadMoveCageAnim(gameTime);
        } else if (animationTime > 20 && animationTime <= 40) {
            hopDistance = 0.2D * ((animationTime - 20) / 20);
        } else {
            birdModel.setupFlappingCageAnim(gameTime);
        }

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.5D, 0.2D + hopDistance, 0.5D);
        pMatrixStack.scale(0.90F, -0.90F, -0.90F);
        pMatrixStack.translate(0.0D, -1.501D, 0.0D);
        birdModel.renderToBuffer(pMatrixStack, pBuffer.getBuffer(birdModel.renderType(ExtendedCrabanRenderer.LOCATION)),
                pCombinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
    }
}
