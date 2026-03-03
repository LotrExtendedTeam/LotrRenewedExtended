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

    /**
     * @author richardb122
     * @reason Bump down alignment bar when a bossbar is active
     * @return True if there is an active bossbar
     * */
    @Overwrite
    private boolean isBossActive() {
        return ExtendedClientProxy.INSTANCE.lastTimeBossBarRendered > System.currentTimeMillis() - 100;
    }

    /**
     * @author richardb122
     * @reason Bump down alignment bar when a bossbar is active
     * @param minecraft A Minecraft instance
     * @param player A client player instance
     * */
    @Overwrite
    private void updateBarCurrentPosition(Minecraft minecraft, PlayerEntity player) {
        this.alignmentXPrev = this.alignmentXCurrent;
        this.alignmentYPrev = this.alignmentYCurrent;
        this.alignmentXCurrent = this.alignmentXBase;
        int yMove = (int)((float)(this.alignmentYBase - -20) / 3.0F);
        boolean alignmentOnscreen = (minecraft.screen == null || minecraft.screen instanceof PlayerMessageScreen)
                && !minecraft.options.keyPlayerList.isDown() && !minecraft.options.renderDebug 
                // Extended Start
                && !this.isBossActive();
        // Extended End
        if (alignmentOnscreen) {
            this.alignmentYCurrent = Math.min(this.alignmentYCurrent + yMove, this.alignmentYBase);
        } else {
            this.alignmentYCurrent = Math.max(this.alignmentYCurrent - yMove, -20);
        }

    }

}
