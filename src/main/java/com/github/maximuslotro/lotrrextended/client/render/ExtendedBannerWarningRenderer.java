package com.github.maximuslotro.lotrrextended.client.render;

import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedBannerWarningWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ExtendedBannerWarningRenderer {

    private static final float WIDGET_WIDTH = 38;
    private static final float WIDGET_HEIGHT = 72;


    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            MatrixStack matrixStack = event.getMatrixStack();
            Minecraft mc = Minecraft.getInstance();

            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            float x = screenWidth - WIDGET_WIDTH - 10;
            float y = (screenHeight / 2f) - (WIDGET_HEIGHT / 2f);

            ExtendedBannerWarningWidget.renderBannerWarningWidget(matrixStack, mc, x, y);
        }
    }
}
