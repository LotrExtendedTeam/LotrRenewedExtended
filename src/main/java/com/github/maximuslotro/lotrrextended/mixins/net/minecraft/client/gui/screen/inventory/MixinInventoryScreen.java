package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inv.ExtendedCoinExchangeScreen;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen extends DisplayEffectsScreen<PlayerContainer> implements IRecipeShownListener {

    public MixinInventoryScreen(PlayerContainer p_i51091_1_, PlayerInventory p_i51091_2_, ITextComponent p_i51091_3_) {
        super(p_i51091_1_, p_i51091_2_, p_i51091_3_);
    }

    @Inject(method = "renderLabels", at = @At("TAIL"))
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_, CallbackInfo info) {
        Minecraft mc = Minecraft.getInstance();
            int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
            if (amount == 0) return;

            String amountStr = String.valueOf(amount);
            fill(p_230451_1_, 180, 14, 180 + 19 + mc.font.width(amountStr), 29, 0x99000000);
            mc.font.draw(p_230451_1_, amountStr, 196, 18, 0xFFFFFF);
            mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
            blit(p_230451_1_, 179, 13, 176, 77, 18, 18);
    }
}
