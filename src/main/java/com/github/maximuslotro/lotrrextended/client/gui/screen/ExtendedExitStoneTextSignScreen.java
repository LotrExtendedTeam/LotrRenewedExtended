package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.stream.IntStream;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedCUpdateStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Based on {@link EditSignScreen}
 * */
public class ExtendedExitStoneTextSignScreen extends Screen {
	/** Reference to the sign object. */
	private final ExtendedStoneTextSignTileEntity sign;
	/** Counts the number of screen updates. */
	private int frame;
	/** The index of the line that is being edited. */
	private int line;
	private TextInputUtil signField;
	private final String[] messages;

	public ExtendedExitStoneTextSignScreen(ExtendedStoneTextSignTileEntity pStoneSign) {
		super(new TranslationTextComponent("gui.lotrextended.stone_engraving.title"));
		this.messages = IntStream.range(0, 8).mapToObj(pStoneSign::getMessage).map(ITextComponent::getString).toArray((p_243354_0_) -> {
			return new String[p_243354_0_];
		});
		this.sign = pStoneSign;
	}

	@Override
	protected void init() {
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, DialogTexts.GUI_DONE, (p_238847_1_) -> {
			this.onDone();
		}));
		this.sign.setEditable(false);
		this.signField = new TextInputUtil(() -> {
			return this.messages[this.line];
		}, (p_238850_1_) -> {
			this.messages[this.line] = p_238850_1_;
			this.sign.setMessage(this.line, new StringTextComponent(p_238850_1_));
		}, TextInputUtil.createClipboardGetter(this.minecraft), TextInputUtil.createClipboardSetter(this.minecraft), (p_238848_1_) -> {
			return this.minecraft.font.width(p_238848_1_) <= 90;
		});
	}

	@Override
	public void removed() {
		this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
		ExtendedPacketHandler.sendToServer(new ExtendedCUpdateStoneTextSignPacket(this.sign.getBlockPos(), this.messages));

		this.sign.setEditable(true);
	}

	@Override
	public void tick() {
		++this.frame;
		if (!this.sign.getType().isValid(this.sign.getBlockState().getBlock())) {
			this.onDone();
		}

	}

	private void onDone() {
		this.sign.setChanged();
		this.onClose();
	}

	@Override
	public boolean charTyped(char pCodePoint, int pModifiers) {
		this.signField.charTyped(pCodePoint);
		return true;
	}

	@Override
	public void onClose() {
		this.onDone();
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		if (pKeyCode == 265) {
			this.line = this.line - 1 & 7;
			this.signField.setCursorToEnd();
			return true;
		} else if (pKeyCode != 264 && pKeyCode != 257 && pKeyCode != 335) {
			return this.signField.keyPressed(pKeyCode) ? true : super.keyPressed(pKeyCode, pScanCode, pModifiers);
		} else {
			this.line = this.line + 1 & 7;
			this.signField.setCursorToEnd();
			return true;
		}
	}

	@Override
	public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		RenderHelper.setupForFlatItems();
		this.renderBackground(pMatrixStack);
		drawCenteredString(pMatrixStack, this.font, this.title, this.width / 2, 40, 16777215);
		pMatrixStack.pushPose();
		pMatrixStack.translate((double)(this.width / 2), 0.0D, 50.0D);
		float f = 93.75F;
		pMatrixStack.scale(93.75F, -93.75F, 93.75F);
		pMatrixStack.translate(0.0D, -1.3125D, 0.0D);
		// Bypass StandingSignBlock check
		pMatrixStack.translate(0.0D, -0.3125D, 0.0D);

		boolean flag1 = this.frame / 6 % 2 == 0;
		IRenderTypeBuffer.Impl irendertypebuffer$impl = this.minecraft.renderBuffers().bufferSource();
		
		float f2 = 0.010416667F;
		pMatrixStack.translate(0.0D, (double)0.33333334F, (double)0.046666667F);
		pMatrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int i = this.sign.getColor().getTextColor();
		int j = this.signField.getCursorPos();
		int k = this.signField.getSelectionPos();
		int l = this.line * 10 - this.messages.length * 5;
		Matrix4f matrix4f = pMatrixStack.last().pose();

		for(int i1 = 0; i1 < this.messages.length; ++i1) {
			String s = this.messages[i1];
			if (s != null) {
				if (this.font.isBidirectional()) {
					s = this.font.bidirectionalShaping(s);
				}

				float f3 = (float)(-this.minecraft.font.width(s) / 2);
				this.minecraft.font.drawInBatch(s, f3, (float)(i1 * 10 - this.messages.length * 5), i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
				if (i1 == this.line && j >= 0 && flag1) {
					int j1 = this.minecraft.font.width(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
					int k1 = j1 - this.minecraft.font.width(s) / 2;
					if (j >= s.length()) {
						this.minecraft.font.drawInBatch("_", (float)k1, (float)l, i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
					}
				}
			}
		}

		irendertypebuffer$impl.endBatch();

		for(int i3 = 0; i3 < this.messages.length; ++i3) {
			String s1 = this.messages[i3];
			if (s1 != null && i3 == this.line && j >= 0) {
				int j3 = this.minecraft.font.width(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
				int k3 = j3 - this.minecraft.font.width(s1) / 2;
				if (flag1 && j < s1.length()) {
					fill(pMatrixStack, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
				}

				if (k != j) {
					int l3 = Math.min(j, k);
					int l1 = Math.max(j, k);
					int i2 = this.minecraft.font.width(s1.substring(0, l3)) - this.minecraft.font.width(s1) / 2;
					int j2 = this.minecraft.font.width(s1.substring(0, l1)) - this.minecraft.font.width(s1) / 2;
					int k2 = Math.min(i2, j2);
					int l2 = Math.max(i2, j2);
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuilder();
					RenderSystem.disableTexture();
					RenderSystem.enableColorLogicOp();
					RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
					bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
					bufferbuilder.vertex(matrix4f, (float)k2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.vertex(matrix4f, (float)l2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.vertex(matrix4f, (float)l2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.vertex(matrix4f, (float)k2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
					bufferbuilder.end();
					WorldVertexBufferUploader.end(bufferbuilder);
					RenderSystem.disableColorLogicOp();
					RenderSystem.enableTexture();
				}
			}
		}

		pMatrixStack.popPose();
		RenderHelper.setupFor3DItems();
		super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
	}
}