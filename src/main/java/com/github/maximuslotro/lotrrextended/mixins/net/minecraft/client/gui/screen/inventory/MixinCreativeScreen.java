package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.gui.screen.inventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedCoinExchangeScreen;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(CreativeScreen.class)
public abstract class MixinCreativeScreen extends DisplayEffectsScreen<CreativeScreen.CreativeContainer> {

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param creativeContainer A placeholder variable instance of CreativeContainer
	 * @param inventory A placeholder variable instance of PlayerInventory
	 * @param titleIn A placeholder variable instance of ITextComponent
	 * */
    public MixinCreativeScreen(CreativeScreen.CreativeContainer creativeContainer, PlayerInventory inventory, ITextComponent titleIn) {
        super(creativeContainer, inventory, titleIn);
    }

    @Inject(method = "renderLabels", at = @At("TAIL"))
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_, CallbackInfo info) {
    	Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) mc.screen;
            assert mc.player != null;

            if (screen.getSelectedTab() == 11) {
                int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
                if (amount == 0) return;

                String amountStr = String.valueOf(amount);
                fill(p_230451_1_, 197, 34, 197 + 19 + mc.font.width(amountStr), 49, 0x99000000);
                mc.font.draw(p_230451_1_, amountStr, 213, 38, 0xFFFFFF);
                mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
                blit(p_230451_1_, 196, 33, 176, 77, 18, 18);
            }
        }
    }
}
