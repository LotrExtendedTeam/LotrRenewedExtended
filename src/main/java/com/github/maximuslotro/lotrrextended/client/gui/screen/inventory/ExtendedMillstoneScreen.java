package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedMillstoneContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedMillstoneScreen extends ContainerScreen<ExtendedMillstoneContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/container/millstone.png");

	public ExtendedMillstoneScreen(ExtendedMillstoneContainer pAbstractFurnaceMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
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
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		if (this.menu.isLubed()) {
			this.blit(pMatrixStack, i + 56, j + 36 + 12 - 13, 176, 12 - 13, 14, 13+1);
		}

		int l = this.menu.getMillingProgress();
		this.blit(pMatrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
	}

}