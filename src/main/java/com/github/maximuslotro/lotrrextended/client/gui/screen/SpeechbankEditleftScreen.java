package com.github.maximuslotro.lotrrextended.client.gui.screen;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.client.gui.screen.SpeechbankEditScreen.PanelResize;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.ResourceLocation;

public class SpeechbankEditleftScreen extends AbstractGui implements PanelResize, IGuiEventListener {
	private SpeechbankEditScreen parentScreen;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	private static String fileName = "";
	private static ResourceLocation filePath;
	private static ResourceLocation parentBank;
	private static String langId = "";
	private static boolean dirty = false;

	public SpeechbankEditleftScreen(SpeechbankEditScreen pParentScreen) {
		this.parentScreen = pParentScreen;
	}

	public void syncFromTab(@Nullable SpeechbankEditScreen.SpeechbankTab tab) {
		if (tab == null || tab.subScreen == null) {
			filePath = null;
			parentBank = null;
			langId = "";
			dirty = false;
			fileName = "";
			return;
		}
		SpeechbankEditRightScreen sub = tab.subScreen;
		filePath = sub.getFilePath();
		parentBank = sub.getParentBank();
		langId = sub.getLangID() == null ? "" : sub.getLangID();
		dirty = tab.dirty;
		fileName = dirty ? SpeechbankEditScreen.getFileName(sub.getFilePath()) + "*" : SpeechbankEditScreen.getFileName(sub.getFilePath());
	}

	@Override
	public void refreshLayout(int pXOffset, int pYOffset, int pWidth, int pHeight) {
		this.xOffset = pXOffset;
		this.yOffset = pYOffset;
		this.width = pWidth;
		this.height = pHeight;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		FontRenderer font = parentScreen.getFont();
		int padX = 8;
		int maxTextWidth = Math.max(0, this.width - padX * 2);
		int textX = this.xOffset + padX;
	
		drawString(matrixStack, font, font.plainSubstrByWidth("File Info", maxTextWidth), textX, this.yOffset + 10, 0xFFFFFF);
		fill(matrixStack, textX, this.yOffset + 21, this.xOffset + this.width - padX, this.yOffset + 22, 0xFF3A3A3A);
		drawString(matrixStack, font, font.plainSubstrByWidth("File Name: "+fileName, maxTextWidth), textX, this.yOffset + 30, 0xFFFFFF);
		drawString(matrixStack, font, font.plainSubstrByWidth("File Path: "+(filePath==null ? "" : filePath.toString()), maxTextWidth), textX, this.yOffset + 42, 0xFFFFFF);
		drawString(matrixStack, font, font.plainSubstrByWidth("Parent Bank: "+(parentBank==null ? "" : parentBank.toString()), maxTextWidth), textX, this.yOffset + 54, 0xFFFFFF);
		drawString(matrixStack, font, font.plainSubstrByWidth("Lang ID: "+langId, maxTextWidth), textX, this.yOffset + 66, 0xFFFFFF);
	}
}
