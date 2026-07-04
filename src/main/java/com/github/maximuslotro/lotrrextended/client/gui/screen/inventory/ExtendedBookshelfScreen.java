package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedBookshelfContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedBookshelfScreen extends ExtendedContainerScreen<ExtendedBookshelfContainer> implements IHasContainer<ExtendedBookshelfContainer> {
	/** The ResourceLocation containing the chest GUI texture. */
	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
	private final int containerRows;

	public ExtendedBookshelfScreen(ExtendedBookshelfContainer pChestMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(pChestMenu, pPlayerInventory, pTitle);
		this.passEvents = false;
		int i = 222;
		int j = 114;
		this.containerRows = pChestMenu.containerRows;
		this.imageHeight = 114 + this.containerRows * 18;
		this.inventoryLabelY = this.imageHeight - 94;
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
		this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
		this.blit(pMatrixStack, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
	}
}