package com.github.maximuslotro.lotrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedCoinExchangeScreen;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inv.AlloyForgeScreen;
import lotr.common.inv.AbstractAlloyForgeContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

/**
 * Need to add coin total to inventory
 * @Mixin (AlloyForgeScreen.class)
 * */
@Mixin(AlloyForgeScreen.class)
public abstract class MixinAlloyForgeScreen extends ContainerScreen<AbstractAlloyForgeContainer> {

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param alloyContainter A placeholder variable instance of AbstractAlloyForgeContainer
	 * @param inventory A placeholder variable instance of PlayerInventory
	 * @param titleIn A placeholder variable instance of ITextComponent
	 * */
	public MixinAlloyForgeScreen(AbstractAlloyForgeContainer alloyContainter, PlayerInventory inventory, ITextComponent titleIn) {
		super(alloyContainter, inventory, titleIn);
	}

	/**
	 * @reason Adding a total coins display to the inventory
	 * @Inject (method = "renderLabels(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", at = @At("TAIL"))
	 * @param matStack The local MatrixStack to render to
	 * @param x ? Does nothing in MC code
	 * @param y ? Does nothing in MC code
	 * @param info The local CallbackInfo instance for this injection
	 * */
	@Inject(method = "renderLabels(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", at = @At("TAIL"))
	protected void renderLabels(MatrixStack matStack, int x, int y, CallbackInfo info) {
		Minecraft mc = Minecraft.getInstance();
		assert mc.player != null;

		int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
		if (amount > 0) {
			String amountStr = String.valueOf(amount);
			fill(matStack, 180, 14 + 135, 180 + 19 + mc.font.width(amountStr), 29 + 135, 0x99000000);
			mc.font.draw(matStack, amountStr, 196, 18 + 135, 0xFFFFFF);
			mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
			blit(matStack, 179, 13 + 135, 176, 77, 18, 18);
		}
	}
}
