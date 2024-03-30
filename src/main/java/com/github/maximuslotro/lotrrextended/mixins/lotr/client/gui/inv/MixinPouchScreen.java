package com.github.maximuslotro.lotrrextended.mixins.lotr.client.gui.inv;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.inv.PouchScreen;
import lotr.common.inv.PouchContainer;
import lotr.common.util.CoinUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@Mixin(PouchScreen.class)
public abstract class MixinPouchScreen extends ContainerScreen<PouchContainer> {

	@Shadow(remap = false)
	@Final
	private int pouchRows;

	private static final ResourceLocation COIN_LOCATION = new ResourceLocation("lotr:gui/coin_exchange.png");

	public MixinPouchScreen(PouchContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
		super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
	}

	@Inject(method = "renderLabels", at = @At("TAIL"))
	protected void renderLabels(MatrixStack matStack, int mouseX, int mouseY, CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();

		int chestAmount = CoinUtils.totalValueInInventory(menu.getPouchInventory());
		if (chestAmount > 0) {
			String chestStr = String.valueOf(chestAmount);
			fill(matStack, 180, 14, 180 + 19 + mc.font.width(chestStr), 29, 0x99000000);
			mc.font.draw(matStack, chestStr, 196, 18, 0xFFFFFF);
			mc.getTextureManager().bind(COIN_LOCATION);
			blit(matStack, 179, 13, 176, 77, 18, 18);
		}

		int amount = CoinUtils.totalValueInPlayerInventory(mc.player.inventory);
		if (amount > 0) {
			String amountStr = String.valueOf(amount);
			fill(matStack, 180, 14 + ((pouchRows + 1) * 18), 180 + 19 + mc.font.width(amountStr), 29 + ((pouchRows + 1) * 18), 0x99000000);
			mc.font.draw(matStack, amountStr, 196, 18 + ((pouchRows + 1) * 18), 0xFFFFFF);
			mc.getTextureManager().bind(COIN_LOCATION);
			blit(matStack, 179, 13 + ((pouchRows + 1) * 18), 176, 77, 18, 18);
		}
	}
}
