package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public abstract class ExtendedContainerScreen<T extends Container> extends ContainerScreen<T>{

	public ExtendedContainerScreen(T pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	public void drawScaled(MatrixStack pMatrixStack, ITextComponent pText, float pX, float pY, int pColor, float scale){
		pMatrixStack.pushPose();
		pMatrixStack.scale(scale, scale, scale);
		int scaledX = Math.round(pX / scale);
		int scaledY = Math.round(pY / scale);
		font.draw(pMatrixStack, pText, scaledX, scaledY, pColor);
		pMatrixStack.popPose();
	}
}
