package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitFarmhandContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedHiredUnitFarmhandScreen extends ExtendedHiredUnitScreen<ExtendedHiredUnitFarmhandContainer> {

	private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(LotrExtendedMod.MODID,"textures/gui/hired_unit_farmhand_menu.png");

	public ExtendedHiredUnitFarmhandScreen(ExtendedHiredUnitFarmhandContainer unitEquipmentContainer, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(unitEquipmentContainer, pPlayerInventory, pTitle);
		setIndex(2);
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		this.renderBackground(pMatrixStack);
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
		this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pMouseX, int pMouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(CONTAINER_BACKGROUND);
		this.blit(pMatrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		for(int i = 0; i<getMenu().farmhandContainterSize; i++) {
			int column = i % 4;
		    int row = i / 4;
		    if (column == 0) {
			    this.blit(pMatrixStack, leftPos+70, topPos+74 + (row * 18), 176, 0, 72, 18);
		    }
		    this.blit(pMatrixStack, leftPos+70 + (column * 18), topPos+74 + (row * 18), 176, 18, 18, 18);
		}
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int pMouseX, int pMouseY) {
		this.font.draw(matrix, title, titleLabelX - (font.width(title)/2), titleLabelY, 4210752);
		this.font.draw(matrix, inventory.getDisplayName(), inventoryLabelX, inventoryLabelY, 4210752);
	}

	@Override
	protected void renderTooltip(MatrixStack stack, int pMouseX, int pMouseY) {
		super.renderTooltip(stack, pMouseX, pMouseY);
	}
}
