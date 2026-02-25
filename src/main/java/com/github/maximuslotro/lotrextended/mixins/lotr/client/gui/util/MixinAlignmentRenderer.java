package com.github.maximuslotro.lotrextended.mixins.lotr.client.gui.util;

import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import lotr.client.gui.PlayerMessageScreen;
import lotr.client.gui.util.AlignmentRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = AlignmentRenderer.class, remap = false)
public class MixinAlignmentRenderer {

    @Shadow
    private int alignmentXBase;
    @Shadow
    private int alignmentYBase;
    @Shadow
    private int alignmentXCurrent;
    @Shadow
    private int alignmentYCurrent;
    @Shadow
    private int alignmentXPrev;
    @Shadow
    private int alignmentYPrev;

    @Overwrite
    private boolean isBossActive() {
        return ExtendedClientProxy.INSTANCE.lastTimeBossBarRendered > System.currentTimeMillis() - 100;
    }

    @Overwrite
    private void updateBarCurrentPosition(Minecraft mc, PlayerEntity player) {
        this.alignmentXPrev = this.alignmentXCurrent;
        this.alignmentYPrev = this.alignmentYCurrent;
        this.alignmentXCurrent = this.alignmentXBase;
        int yMove = (int)((float)(this.alignmentYBase - -20) / 3.0F);
        boolean alignmentOnscreen = (mc.screen == null || mc.screen instanceof PlayerMessageScreen)
                && !mc.options.keyPlayerList.isDown()
                && !mc.options.renderDebug
                && !this.isBossActive();
        if (alignmentOnscreen) {
            this.alignmentYCurrent = Math.min(this.alignmentYCurrent + yMove, this.alignmentYBase);
        } else {
            this.alignmentYCurrent = Math.max(this.alignmentYCurrent - yMove, -20);
        }

    }

}
