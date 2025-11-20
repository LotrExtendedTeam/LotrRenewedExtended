package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedSmithingTableContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedSmithingTableScreen extends AbstractRepairScreen<ExtendedSmithingTableContainer> {
	private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation("textures/gui/container/smithing.png");

	public ExtendedSmithingTableScreen(ExtendedSmithingTableContainer pSmithingMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(pSmithingMenu, pPlayerInventory, pTitle, SMITHING_LOCATION);
		this.titleLabelX = 60;
		this.titleLabelY = 18;
	}

	@Override
	protected void renderLabels(MatrixStack pMatrixStack, int pX, int pY) {
		RenderSystem.disableBlend();
		super.renderLabels(pMatrixStack, pX, pY);
	}
}