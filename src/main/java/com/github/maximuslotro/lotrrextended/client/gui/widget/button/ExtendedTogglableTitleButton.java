package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTogglableTitleButton<T> extends Button {
	// Use translatable keys with %s parameters instead
	private final TextComponent baseMessage;
	@Deprecated
	private final String stringMessageKey;
	private final String enabledKey;
	private final String disabledKey;
	private boolean renderGreen = false;
	protected final IOnTick<T> onTick;
	private boolean buttonForceDisabled = false;
	private boolean buttonForceEnabled = false;
	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, ITooltip pOnTooltip, IOnTick<T> pOnTick, String enabledKey, String disabledKey) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
		this.baseMessage = pMessage;
		this.enabledKey = enabledKey;
		this.disabledKey = disabledKey;
		this.onTick = pOnTick;
		this.stringMessageKey = null;
	}
	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, IOnTick<T> pOnTick, String enabledKey, String disabledKey) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		this.baseMessage = pMessage;
		this.enabledKey = enabledKey;
		this.disabledKey = disabledKey;
		this.onTick = pOnTick;
		this.stringMessageKey = null;
	}
	@Deprecated
	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, String stringMessageKey, IPressable pOnPress, ITooltip pOnTooltip, IOnTick<T> pOnTick, String enabledKey, String disabledKey) {
		super(pX, pY, pWidth, pHeight, StringTextComponent.EMPTY, pOnPress, pOnTooltip);
		this.baseMessage = null;
		this.enabledKey = enabledKey;
		this.disabledKey = disabledKey;
		this.onTick = pOnTick;
		this.stringMessageKey = stringMessageKey;
	}
	@Deprecated
	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, String stringMessageKey, IPressable pOnPress, IOnTick<T> pOnTick, String enabledKey, String disabledKey) {
		super(pX, pY, pWidth, pHeight, StringTextComponent.EMPTY, pOnPress);
		this.baseMessage = null;
		this.enabledKey = enabledKey;
		this.disabledKey = disabledKey;
		this.onTick = pOnTick;
		this.stringMessageKey = stringMessageKey;
	}

	public ExtendedTogglableTitleButton<T> forceDisableButton() {
		buttonForceEnabled = false;
		buttonForceDisabled = true;
		return this;
	}

	public ExtendedTogglableTitleButton<T> forceEnableButton() {
		buttonForceEnabled = true;
		buttonForceDisabled = false;
		return this;
	}

	public void onTick(@Nullable T tickData) {
		tickTitle(onTick.onTick(this, tickData));
	}

	public void tickTitle(@Nullable Boolean toggle) {
		Boolean state = null;
		if (buttonForceEnabled) {
			state = true;
		} else if (buttonForceDisabled) {
			state = false;
		} else if (toggle != null) {
			state = toggle;
		} else  {
			renderGreen = true; // Handle forced dev mode bypassing title setting for forced mode
			return;
		}
		renderGreen = state;
		if (stringMessageKey == null) {
			setMessage(baseMessage.copy().append(new TranslationTextComponent(state ? enabledKey : disabledKey)));
		} else {
			setMessage(new TranslationTextComponent(stringMessageKey, new TranslationTextComponent(state ? enabledKey : disabledKey)));
		}
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.font;
		minecraft.getTextureManager().bind(WIDGETS_LOCATION);
		if(renderGreen) {
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
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}


	public interface IOnTick<T> {
		Boolean onTick(ExtendedTogglableTitleButton<T> button, @Nullable T tickData);
	}
}
