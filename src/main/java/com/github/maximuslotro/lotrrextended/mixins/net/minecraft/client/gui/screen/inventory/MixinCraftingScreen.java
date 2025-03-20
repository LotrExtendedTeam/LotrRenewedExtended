package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.client.gui.screen.inventory;

import org.spongepowered.asm.mixin.Mixin;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedCoinExchangeScreen;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(CraftingScreen.class)
public abstract class MixinCraftingScreen extends ContainerScreen<WorkbenchContainer> implements IRecipeShownListener {

	/**
	 * @reason Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param craftingContainer A placeholder variable instance of WorkbenchContainer
	 * @param inventory A placeholder variable instance of PlayerInventory
	 * @param titleIn A placeholder variable instance of ITextComponent
	 * */
    public MixinCraftingScreen(WorkbenchContainer craftingContainer, PlayerInventory inventory, ITextComponent titleIn) {
        super(craftingContainer, inventory, titleIn);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
       super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);
        Minecraft mc = Minecraft.getInstance();

        int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
        if (amount > 0) {
            String amountStr = String.valueOf(amount);
            fill(p_230451_1_, 180, 14 + (4 * 18), 180 + 19 + mc.font.width(amountStr),
                    29 + (4 * 18), 0x99000000);
            mc.font.draw(p_230451_1_, amountStr, 196, 18 + (4 * 18), 0xFFFFFF);
            mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
            blit(p_230451_1_, 179, 13 + (4 * 18), 176, 77, 18, 18);
        }
    }
}
