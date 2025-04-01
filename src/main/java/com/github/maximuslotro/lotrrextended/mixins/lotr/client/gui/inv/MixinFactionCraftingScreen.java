package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedCoinExchangeScreen;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inv.FactionCraftingScreen;
import lotr.common.inv.FactionCraftingContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

/**
 * @reason Need to add coin total to inventory
 * @Mixin (FactionCraftingScreen.class)
 * */
@Mixin(FactionCraftingScreen.class)
public abstract class MixinFactionCraftingScreen extends ContainerScreen<FactionCraftingContainer> {

	/**
	 * @reason Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param craftingContainer A placeholder variable instance of FactionCraftingContainer
	 * @param inventory A placeholder variable instance of PlayerInventory
	 * @param titleIn A placeholder variable instance of ITextComponent
	 * */
	public MixinFactionCraftingScreen(FactionCraftingContainer craftingContainer, PlayerInventory inventory, ITextComponent titleIn) {
		super(craftingContainer, inventory, titleIn);
	}

	/**
	 * @reason Adding a total coins display to the inventory
	 * @Inject (method = "renderLabels(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", at = @At("TAIL"))
	 * @param matStack The local MatrixStack to render to
	 * @param x ? Does nothing in MC code
	 * @param y ? Does nothing in MC code
	 * @param ci The local CallbackInfo instance for this injection
	 * */
	@Inject(method = "renderLabels(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", at = @At("TAIL"))
	protected void renderLabels(MatrixStack matStack, int x, int y, CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();

		int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
		if (amount > 0) {
			String amountStr = String.valueOf(amount);
			fill(matStack, 180, 14 + (4 * 18), 180 + 19 + mc.font.width(amountStr), 29 + (4 * 18), 0x99000000);
			mc.font.draw(matStack, amountStr, 196, 18 + (4 * 18), 0xFFFFFF);
			mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
			blit(matStack, 179, 13 + (4 * 18), 176, 77, 18, 18);
		}
	}
}
