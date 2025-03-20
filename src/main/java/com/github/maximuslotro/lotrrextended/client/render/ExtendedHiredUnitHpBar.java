package com.github.maximuslotro.lotrrextended.client.render;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class ExtendedHiredUnitHpBar {

    private static final float HEALTHBAR_OPACITY = 80 / 100f;

    private static final float BACKGROUND_RED = 0 / 255f;
    private static final float BACKGROUND_GREEN = 0 / 255f;
    private static final float BACKGROUND_BLUE = 0 / 255f;

    private static final int TEXT_POS_X = 0;
    private static final int TEXT_POS_Y = 0;

    private static final int HB_POS_X_OFFSET = 0;
    private static final int HB_POS_Y_OFFSET = 0;

    private static final int HB_POS_X = -50;
    private static final int HB_POS_Y = 20;

    private static final int HB_WIDTH = 100;
    private static final int HB_HEIGHT = 9;

    private static final float HB_GREEN_R = 85 / 255.0F;
    private static final float HB_GREEN_G = 255 / 255.0F;
    private static final float HB_GREEN_B = 85 / 255.0F;

    private static final float HB_YELLOW_R = 255 / 255.0F;
    private static final float HB_YELLOW_G = 255 / 255.0F;
    private static final float HB_YELLOW_B = 85 / 255.0F;

    private static final float HB_RED_R = 255 / 255.0F;
    private static final float HB_RED_G = 85 / 255.0F;
    private static final float HB_RED_B = 85 / 255.0F;



	public static void draw(LivingEntity entity, float textScale, MatrixStack matrixStackIn, FontRenderer font) {
        float vit = entity.getMaxHealth(), hp = entity.getHealth();
        float hpWidth = ((float) -font.width((int) hp + "/" + (int) vit) / 2);

        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        matrixStackIn.pushPose();

        // render healthbar
        ResourceLocation bar = new ResourceLocation(LotrExtendedMod.MODID, "textures/gui/healthbar.png");

        matrixStackIn.translate(HB_POS_X_OFFSET, HB_POS_Y_OFFSET, 0.0D);
        matrixStackIn.scale(1.0F, 1.0F, 1.0F);
        ExtendedBarRendererHelper.bind(bar);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int endOfHealth = (int) Math.ceil((double) HB_WIDTH * (hp / vit));
        if (endOfHealth >= 80) {
            RenderSystem.color4f(HB_GREEN_R, HB_GREEN_G, HB_GREEN_B, HEALTHBAR_OPACITY);
        } else if (endOfHealth >= 40) {
            RenderSystem.color4f(HB_YELLOW_R, HB_YELLOW_G, HB_YELLOW_B, HEALTHBAR_OPACITY);
        } else {
            RenderSystem.color4f(HB_RED_R, HB_RED_G, HB_RED_B, HEALTHBAR_OPACITY);
        }
        ExtendedBarRendererHelper.blit(matrixStackIn, HB_POS_X, HB_POS_Y, 0, 19, endOfHealth, HB_HEIGHT);

        // render background
        if (endOfHealth < 100) {
            matrixStackIn.translate(HB_POS_X_OFFSET, HB_POS_Y_OFFSET, 0.0D);
            matrixStackIn.scale(1.0F, 1.0F, 1.0F);
            ExtendedBarRendererHelper.bind(bar);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            RenderSystem.color4f(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE, HEALTHBAR_OPACITY);
            ExtendedBarRendererHelper.blit(matrixStackIn, HB_POS_X + endOfHealth, HB_POS_Y, 0, 19, HB_WIDTH - endOfHealth, HB_HEIGHT);
        }

        // render text
        matrixStackIn.translate(0.0D, 0.0D, -0.01D);
        matrixStackIn.translate(TEXT_POS_X, TEXT_POS_Y, -0.03D);
        matrixStackIn.scale(textScale, textScale, 1.0F);
        int color = ExtendedColorHelper.ARGB32.color(255, 255, 255, 255); // set black text color no opacity
        ExtendedBarRendererHelper.drawString(matrixStackIn, font, (int) hp + "/" + (int) vit, (int)hpWidth, 20, color, false);

        RenderSystem.disableBlend();
        matrixStackIn.popPose();
        RenderSystem.disableDepthTest();
    }

}
