package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedGrinderContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedGrinderScreen extends ContainerScreen<ExtendedGrinderContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/container/stone_grinder.png");

	public ExtendedGrinderScreen(ExtendedGrinderContainer pAbstractFurnaceMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
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
			this.blit(pMatrixStack, i + 81, j + 31, 177, 104, 17, 17);
		}

		int l = this.menu.getMillingProgress();
		if (this.menu.isLubed()) {
			this.blit(pMatrixStack, i + 81, j + 31, 195, 104, 17, 1+l);
		}else {
			this.blit(pMatrixStack, i + 81, j + 31, 177, 122, 17, 1+l);
		}
	}

}