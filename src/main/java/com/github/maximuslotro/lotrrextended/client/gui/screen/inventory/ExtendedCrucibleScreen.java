package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCrucibleContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedCrucibleScreen extends ContainerScreen<ExtendedCrucibleContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/container/crucible.png");

	public ExtendedCrucibleScreen(ExtendedCrucibleContainer pAbstractFurnaceMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(pAbstractFurnaceMenu, pPlayerInventory, pTitle);
	}

	@Override
	public void init() {
		super.init();
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		int i = this.leftPos;
		int j = this.topPos;
		this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		if(!this.getMenu().showExpandedMenu()) {
			this.blit(pMatrixStack, i + 96, j + 51, 176, 89, 42, 26);
		}
		if (this.menu.isLit()) {
			int k = this.menu.getLitProgress();
			this.blit(pMatrixStack, i + 46, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.menu.getMeltingProgress();
		this.blit(pMatrixStack, i + 71, j + 22, 176, 15, 9, l);
	}
}