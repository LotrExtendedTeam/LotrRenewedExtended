package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inv.FactionCraftingScreen;
import lotr.client.gui.inventory.ExtendedCoinExchangeScreen;
import lotr.common.inv.FactionCraftingContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

@Mixin(FactionCraftingScreen.class)
public abstract class MixinFactionCraftingScreen extends ContainerScreen<FactionCraftingContainer> {

	public MixinFactionCraftingScreen(FactionCraftingContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
		super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
	}

	@Inject(method = "renderLabels", at = @At("TAIL"))
	protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_, CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();

		int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
		if (amount > 0) {
			String amountStr = String.valueOf(amount);
			fill(p_230451_1_, 180, 14 + (4 * 18), 180 + 19 + mc.font.width(amountStr), 29 + (4 * 18), 0x99000000);
			mc.font.draw(p_230451_1_, amountStr, 196, 18 + (4 * 18), 0xFFFFFF);
			mc.getTextureManager().bind(ExtendedCoinExchangeScreen.GUI_TEXTURE);
			blit(p_230451_1_, 179, 13 + (4 * 18), 176, 77, 18, 18);
		}
	}
}
