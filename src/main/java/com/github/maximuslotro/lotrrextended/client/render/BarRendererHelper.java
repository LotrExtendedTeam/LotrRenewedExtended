package com.github.maximuslotro.lotrrextended.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class BarRendererHelper {

        public static void bind(ResourceLocation res) {
            Minecraft.getInstance().getTextureManager().bind(res);
        }

        public static void blit(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
            AbstractGui.blit(matrixStack, x, y, 0, uOffset, vOffset, uWidth, vHeight, 256, 256);
        }

        public static void drawString(MatrixStack stack, FontRenderer font, String text, int x, int y, int color, boolean shadow) {
            drawShadow(stack, font, text, x, y, color, shadow);
        }

        public static int drawShadow(MatrixStack stack, FontRenderer font, String text, float x, float y, int color, boolean shadow) {
            return drawInternal(font, text, x, y, color, stack.last().pose(), false, shadow);
        }

        public static int drawInternal(FontRenderer font, String text, float x, float y, int color, Matrix4f matrix, boolean p_92809_, boolean shadow) {
            if (text == null) return 0;

            IRenderTypeBuffer.Impl bufferSource = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
            int i = font.drawInBatch(text, x, y, color, shadow, matrix, bufferSource, false, 0, 15728880, p_92809_);
            bufferSource.endBatch();
            return i;
        }
}
