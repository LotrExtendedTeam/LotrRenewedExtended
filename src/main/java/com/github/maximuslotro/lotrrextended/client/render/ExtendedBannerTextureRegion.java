package com.github.maximuslotro.lotrrextended.client.render;

import net.minecraft.util.ResourceLocation;

public class ExtendedBannerTextureRegion {
    public final ResourceLocation texture;
    public final int srcX, srcY, width, height;

    public ExtendedBannerTextureRegion(ResourceLocation texture, int srcX, int srcY, int width, int height) {
        this.texture = texture;
        this.srcX = srcX;
        this.srcY = srcY;
        this.width = width;
        this.height = height;
    }
}
