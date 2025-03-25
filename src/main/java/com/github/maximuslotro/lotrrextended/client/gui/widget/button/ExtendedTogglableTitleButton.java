package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTogglableTitleButton extends Button {
	private final TextComponent baseMessage;
	private final String enabled;
	private final String disabled;
	private boolean enabledToggle = false;

	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, ITooltip pOnTooltip, String enabled, String disabled) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
		this.baseMessage = pMessage;
		this.enabled = enabled;
		this.disabled = disabled;
	}

	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, String enabled, String disabled) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		this.baseMessage = pMessage;
		this.enabled = enabled;
		this.disabled = disabled;
	}

	public void tickTitle(boolean toggle) {
		setMessage(baseMessage.copy().append(new TranslationTextComponent(toggle ? enabled : disabled)));
		this.visible = true;
		enabledToggle = toggle;
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.font;
		minecraft.getTextureManager().bind(WIDGETS_LOCATION);
		if(enabledToggle) {
			RenderSystem.color4f(0.13f, 0.77f, 0.42f, this.alpha);

		}else {
			RenderSystem.color4f(0.88f, 0.33f, 0.33f, this.alpha);

		}
		int i = this.getYImage(this.isHovered());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.blit(pMatrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
		this.blit(pMatrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
		this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);
		int j = getFGColor();
		drawCenteredString(pMatrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}
}
