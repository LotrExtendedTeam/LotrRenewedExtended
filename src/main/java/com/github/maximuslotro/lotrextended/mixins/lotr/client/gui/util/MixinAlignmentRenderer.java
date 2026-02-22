package com.github.maximuslotro.lotrextended.mixins.lotr.client.gui.util;

import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import lotr.client.gui.util.AlignmentRenderer;
import lotr.common.config.LOTRConfig;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = AlignmentRenderer.class, remap = false)
public class MixinAlignmentRenderer {

    @Shadow
    private int alignmentXBase;
    @Shadow
    private int alignmentYBase;

    @Overwrite
    private boolean isBossActive() {
        return ExtendedClientProxy.INSTANCE.lastTimeBossBarRendered > System.currentTimeMillis() - 100;
    }

    @Overwrite
    private void setBarBasePosition(Minecraft mc) {
        MainWindow mainWindow = mc.getWindow();
        int width = mainWindow.getGuiScaledWidth();
        int height = mainWindow.getGuiScaledHeight();
        this.alignmentXBase = width / 2 + LOTRConfig.CLIENT.alignmentXOffset.get();
        this.alignmentYBase = 4 + LOTRConfig.CLIENT.alignmentYOffset.get();
        if (this.isBossActive()) {
            this.alignmentYBase -= 40;
        }
    }

}
