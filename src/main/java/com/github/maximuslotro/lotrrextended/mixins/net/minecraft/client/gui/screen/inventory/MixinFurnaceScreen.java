package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inventory.ExtendedCoinExchangeScreen;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;

@OnlyIn(Dist.CLIENT)
@Mixin(FurnaceScreen.class)
public class MixinFurnaceScreen extends AbstractFurnaceScreen<FurnaceContainer> {

    public MixinFurnaceScreen(FurnaceContainer p_i51104_1_, AbstractRecipeBookGui p_i51104_2_, PlayerInventory p_i51104_3_, ITextComponent p_i51104_4_, ResourceLocation p_i51104_5_) {
        super(p_i51104_1_, p_i51104_2_, p_i51104_3_, p_i51104_4_, p_i51104_5_);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);

        Minecraft mc = Minecraft.getInstance();

        int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
        if (amount > 0) {
            String amountStr = String.valueOf(amount);
            fill(p_230451_1_, 180, 14 + (2 * 18), 180 + 19 + mc.font.width(amountStr),
                    29 + (2 * 18), 0x99000000);
            mc.font.draw(p_230451_1_, amountStr, 196, 18 + (2 * 18), 0xFFFFFF);
            mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
            blit(p_230451_1_, 179, 13 + (2 * 18), 176, 77, 18, 18);
        }
    }

}
