package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

public class SpeechbankEditSubScreen extends AbstractGui implements IRenderable, IGuiEventListener {
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	protected SpeechbankEditScreen parentScreen;
	private String fileName;
	private String filePath;
	private String langID;

	public SpeechbankEditSubScreen(String pFileName) {
		fileName = pFileName;
	}

	public void init(int pWidth, int pHeight, int pXOffset, int pYOffset, SpeechbankEditScreen pParentScreen) {
		this.width = pWidth;
		this.height = pHeight;
		this.xOffset = pXOffset;
		this.yOffset = pYOffset;
		this.parentScreen = pParentScreen;
		// TODO int buttons
	}

	public String getFileName() {
		return fileName;
	}

	public void setTitle(String pFileName) {
		this.fileName = pFileName;
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		parentScreen.draw(pMatrixStack, fileName, xOffset + ((this.width - parentScreen.getFont().width(fileName)) / 2), yOffset + 11, 4210752);
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
