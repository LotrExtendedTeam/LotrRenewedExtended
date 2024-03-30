package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeScreen.class)
public abstract class MixinCreativeScreen extends DisplayEffectsScreen<CreativeScreen.CreativeContainer> {

    private static final ResourceLocation COIN_LOCATION = new ResourceLocation("lotr:gui/coin_exchange.png");

    public MixinCreativeScreen(CreativeScreen.CreativeContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
    }

    @Inject(method = "renderLabels", at = @At("TAIL"))
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_, CallbackInfo info) {

        if (Minecraft.getInstance().screen instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) Minecraft.getInstance().screen;
            Minecraft mc = Minecraft.getInstance();
            assert mc.player != null;

            if (screen.getSelectedTab() == 11) {
                int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
                if (amount == 0) return;

                String amountStr = String.valueOf(amount);
                fill(p_230451_1_, 197, 34, 197 + 19 + mc.font.width(amountStr), 49, 0x99000000);
                Minecraft.getInstance().font.draw(p_230451_1_, amountStr, 213, 38, 0xFFFFFF);
                Minecraft.getInstance().getTextureManager().bind(COIN_LOCATION);
                blit(p_230451_1_, 196, 33, 176, 77, 18, 18);
            }
        }
    }
}
