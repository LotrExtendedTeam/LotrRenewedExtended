package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.gui.screen.inventory;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inventory.ExtendedCoinExchangeScreen;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(ChestScreen.class)
public abstract class MixinChestScreen extends ContainerScreen<ChestContainer> implements IHasContainer<ChestContainer> {

    public MixinChestScreen(ChestContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
    	super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);
        Minecraft mc = Minecraft.getInstance();

        int chestAmount = CoinUtils.totalValueInInventory(menu.getContainer());
        if (chestAmount > 0) {
            String chestStr = String.valueOf(chestAmount);
            fill(p_230451_1_, 180, 14, 180 + 19 + mc.font.width(chestStr), 29, 0x99000000);
            mc.font.draw(p_230451_1_, chestStr, 196, 18, 0xFFFFFF);
            mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
            blit(p_230451_1_, 179, 13, 176, 77, 18, 18);
        }

        int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
        if (amount > 0) {
            String amountStr = String.valueOf(amount);
            fill(p_230451_1_, 180, 14 + ((menu.getRowCount() + 1) * 18), 180 + 19 + mc.font.width(amountStr),
                    29 + ((menu.getRowCount() + 1) * 18), 0x99000000);
            mc.font.draw(p_230451_1_, amountStr, 196, 18 + ((menu.getRowCount() + 1) * 18), 0xFFFFFF);
            mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
            blit(p_230451_1_, 179, 13 + ((menu.getRowCount() + 1) * 18), 176, 77, 18, 18);
        }
    }
}
