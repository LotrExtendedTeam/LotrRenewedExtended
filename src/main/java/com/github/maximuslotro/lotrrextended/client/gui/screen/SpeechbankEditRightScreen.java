package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.gui.screen.SpeechbankEditScreen.PanelResize;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.util.ResourceLocation;

public class SpeechbankEditRightScreen extends AbstractGui implements PanelResize, IRenderable, IGuiEventListener {
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	protected SpeechbankEditScreen parentScreen;
	private ResourceLocation filePath;
	private ResourceLocation parentBank;
	private String langID;

	public SpeechbankEditRightScreen(SpeechbankEditScreen pParentScreen, ResourceLocation pFilePath) {
		filePath = pFilePath;
		this.parentScreen = pParentScreen;
		// TODO int buttons
	}


	@Override
	public void refreshLayout(int pXOffset, int pYOffset, int pWidth, int pHeight) {
		this.xOffset = pXOffset;
		this.yOffset = pYOffset;
		this.width = pWidth;
		this.height = pHeight;
	}

	public ResourceLocation getFilePath() {
		return filePath;
	}

	public void setFilePath(ResourceLocation pFilePath) {
		this.filePath = pFilePath;
		notifyLeftPanelChanged();
	}

	public String getLangID() {
		return langID;
	}

	public void setLangId(String pLangID) {
		this.langID = pLangID;
		notifyLeftPanelChanged();
	}

	public ResourceLocation getParentBank() {
		return parentBank;
	}

	public void setParentBank(ResourceLocation pParentBank) {
		this.parentBank = pParentBank;
		notifyLeftPanelChanged();
	}

	private void notifyLeftPanelChanged() {
		if (this.parentScreen != null) {
			this.parentScreen.onActiveTabModified(this);
		}
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		parentScreen.draw(pMatrixStack, filePath.toString(), xOffset + ((this.width - parentScreen.getFont().width(filePath.toString())) / 2), yOffset + 11, 4210752);
	}

	public void tick() {

	}

	@Override
	public void mouseMoved(double pMouseX, double pMouseY) {
	}

	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		return false;
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		return false;
	}

	@Override
	public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
		return false;
	}

	@Override
	public boolean charTyped(char pCodePoint, int pModifiers) {
		return false;
	}

	@Override
	public boolean changeFocus(boolean pFocus) {
		return false;
	}

	@Override
	public boolean isMouseOver(double pMouseX, double pMouseY) {
		return false;
	}
}
