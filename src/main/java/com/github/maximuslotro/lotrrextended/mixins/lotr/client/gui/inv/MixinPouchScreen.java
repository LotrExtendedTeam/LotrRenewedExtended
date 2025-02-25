package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inv.PouchScreen;
import lotr.client.gui.inventory.ExtendedCoinExchangeScreen;
import lotr.common.inv.PouchContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

/**
 * @reason Need to add coin total to inventory
 * @Mixin (PouchScreen.class)
 * */
@Mixin(PouchScreen.class)
public abstract class MixinPouchScreen extends ContainerScreen<PouchContainer> {

	@Shadow(remap = false)
	@Final
	private int pouchRows;

	/**
	 * @reason Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param pouchContainer A placeholder variable instance of PouchContainer
	 * @param inventory A placeholder variable instance of PlayerInventory
	 * @param titleIn A placeholder variable instance of ITextComponent
	 * */
	public MixinPouchScreen(PouchContainer pouchContainer, PlayerInventory inventory, ITextComponent titleIn) {
		super(pouchContainer, inventory, titleIn);
	}

	/**
	 * @reason Adding a total coins display to the inventory
	 * @Inject (method = "renderLabels(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", at = @At("TAIL"))
	 * @param matStack The local MatrixStack to render to
	 * @param mouseX ? Does nothing in MC code
	 * @param mouseY ? Does nothing in MC code
	 * @param ci The local CallbackInfo instance for this injection
	 * */
	@Inject(method = "renderLabels(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", at = @At("TAIL"))
	protected void renderLabels(MatrixStack matStack, int mouseX, int mouseY, CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();

		int chestAmount = CoinUtils.totalValueInInventory(menu.getPouchInventory());
		if (chestAmount > 0) {
			String chestStr = String.valueOf(chestAmount);
			fill(matStack, 180, 14, 180 + 19 + mc.font.width(chestStr), 29, 0x99000000);
			mc.font.draw(matStack, chestStr, 196, 18, 0xFFFFFF);
			mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
			blit(matStack, 179, 13, 176, 77, 18, 18);
		}

		int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
		if (amount > 0) {
			String amountStr = String.valueOf(amount);
			fill(matStack, 180, 14 + 81, 180 + 19 + mc.font.width(amountStr), 29 + 81, 0x99000000);
			mc.font.draw(matStack, amountStr, 196, 18 + 81, 0xFFFFFF);
			mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
			blit(matStack, 179, 13 + 81, 176, 77, 18, 18);
		}
	}
}
