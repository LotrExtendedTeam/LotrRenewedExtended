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

    public static final ExtendedCrabanModel<ExtendedCrabanEntity> CRABAN_MODEL = new ExtendedCrabanModel<>();

    public ExtendedBirdCageTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(ExtendedBirdcageTileEntity tileEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
        if (tileEntity.getLevel() == null) {
            return;
        }

        if (tileEntity.getBird().equals("craban")) {
            renderCraban(tileEntity, pPartialTicks, pMatrixStack, pBuffer, pCombinedLight, pCombinedOverlay);
        }
    }

    private void renderCraban(ExtendedBirdcageTileEntity tile, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
        double gameTime = tile.getLevel().getGameTime() + pPartialTicks;
        float animationTime = (float) (gameTime % 80.0D);

        double hopDistance = 0.15D *
                (0.9D - Math.cos(2.0D * Math.PI * animationTime / 80.0D));

        // always flap wings
        CRABAN_MODEL.setupFlappingCageAnim(gameTime);

        CRABAN_MODEL.resetHeadPose();

        // occasionally animate head
        long cycle = tile.getLevel().getGameTime() / 80;
        if (cycle % 4 == 0) {
            CRABAN_MODEL.setupHeadMoveCageAnim(animationTime);
        }

        pMatrixStack.pushPose();
        pMatrixStack.translate(0.5D, 0.15D + hopDistance, 0.5D);
        pMatrixStack.scale(0.90F, -0.90F, -0.90F);
        pMatrixStack.translate(0.0D, -1.501D, 0.0D);
        CRABAN_MODEL.renderToBuffer(pMatrixStack, pBuffer.getBuffer(CRABAN_MODEL.renderType(ExtendedCrabanRenderer.LOCATION)),
                pCombinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
    }
}
