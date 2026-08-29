package com.github.maximuslotro.lotrrextended.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedSlider extends AbstractSlider {
	public final String localKey;
	private final double minValue;
	private final double maxValue;
	private final float steps;
	private final IGetValue valueGetter;
	private final ISetValue valueSetter;
	private ITooltip onTooltip;

	// Modification fields
	protected ResourceLocation customTexture = WIDGETS_LOCATION;
	protected int handleLength = 8;
	protected int textureU = 0;
	protected int textureV = 46;
	protected boolean drawBackground = true;
	protected int textureUBackground = 0;
	protected int textureVBackground = 46;
	protected int textureWidth = 256;
	protected int textureHeight = 256;
	protected boolean disableMessage = false;

	public ExtendedSlider(int pX, int pY, int pWidth, int pHeight, String localKey, double minVal, double maxVal, float steps, IGetValue valueGetter, ISetValue valueSetter) {
		super(pX, pY, pWidth, pHeight, StringTextComponent.EMPTY, 0);
		this.localKey = localKey;
		this.minValue = minVal;
		this.maxValue = maxVal;
		this.steps = steps;
		this.valueGetter = valueGetter;
		this.valueSetter = valueSetter;
		this.value = toPercentage(valueGetter.getValue());
		this.updateMessage();
	}

	public ExtendedSlider setSliderTexture(ResourceLocation texture, int handleLength) {
		this.customTexture = texture;
		this.handleLength = handleLength;
		return this;
	}

	public ExtendedSlider setSliderTextureUV(int u, int v) {
		this.textureU = u;
		this.textureV = v;
		return this;
	}

	public ExtendedSlider setBackgroundTextureUV(int u, int v) {
		this.textureUBackground = u;
		this.textureVBackground = v;
		return this;
	}

	public ExtendedSlider setTextureSize(int width, int height) {
		this.textureWidth = width;
		this.textureHeight = height;
		return this;
	}

	public ExtendedSlider setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
		return this;
	}

	public ExtendedSlider disableMessage() {
		this.disableMessage = true;
		return this;
	}

	private boolean isHandleHoveredX(int mouseX, int mouseY) {
        int handleX = this.x + (int) (this.value * (double) (this.width - this.handleLength));
        return mouseX >= handleX && mouseX < handleX + this.handleLength && mouseY >= this.y && mouseY < this.y + this.height;
    }

	@Override
	protected void renderBg(MatrixStack pMatrixStack, Minecraft pMinecraft, int pMouseX, int pMouseY) {
		pMinecraft.getTextureManager().bind(customTexture);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		int handleX = this.x + (int) (this.value * (double) (this.width - this.handleLength));
		boolean handleHovered = isHandleHoveredX(pMouseX, pMouseY);
		if(this.customTexture.equals(WIDGETS_LOCATION) && this.handleLength == 8) {
			int hoverOffset = (handleHovered ? 2 : 1) * this.height;
			blit(pMatrixStack, handleX, this.y, this.getBlitOffset(), 0, 46 + hoverOffset, 4, 20, textureWidth, textureHeight);
			blit(pMatrixStack, handleX + 4, this.y, this.getBlitOffset(), 196, 46 + hoverOffset, 4, 20, textureWidth, textureHeight);
		} else {
			blit(pMatrixStack, handleX, this.y, this.getBlitOffset(), this.textureU, this.textureV + (handleHovered ? this.height : 0), this.handleLength, this.height, this.textureWidth, this.textureHeight);
		}
	}

	public double toPercentage(double pValue) {
		return MathHelper.clamp((clamp(pValue)-minValue)/(maxValue-minValue), 0.0D, 1.0D);
	}

	public double toValue(double pValue) {
		return this.clamp(MathHelper.lerp(MathHelper.clamp(pValue, 0.0D, 1.0D), minValue, this.maxValue));
	}

	private double clamp(double pValue) {
		if(this.steps > 0.0F) {
			// Snap to nearest step
			pValue = steps * Math.round(pValue / steps);
			// Round to 2 decimals (works because step >= 0.01)
			pValue = Math.round(pValue * 100.0) / 100.0;
		}
		return MathHelper.clamp(pValue, this.minValue, this.maxValue);
	}

	public ExtendedSlider setToolTip(ITooltip onTooltip) {
		this.onTooltip = onTooltip;
		return this;
	}

	@Override
	public void onClick(double pMouseX, double pMouseY) {
		this.setValueFromMouseX(pMouseX);
	}
	@Override
	protected void onDrag(double pMouseX, double pMouseY, double pDragX, double pDragY) {
		this.setValueFromMouseX(pMouseX);
	}
	protected void setValueFromMouseX(double pMouseX) {
		if(width<=handleLength) {
			throw new IllegalStateException("Handle length must be larger than slider width!");
		}
		this.setSliderValue((pMouseX - (this.x + (this.handleLength / 2.0D))) / (double)(width - handleLength));
	}
	protected void setValueFromMouseY(double pMouseY) {
		if(height<=handleLength) {
			throw new IllegalStateException("Handle length must be larger than slider height!");
		}
		this.setSliderValue((pMouseY - (this.y + (this.handleLength / 2.0D))) / (double)(height - handleLength));
	}
	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		boolean isLeft = pKeyCode == 263;
		if (isLeft || pKeyCode == 262) {
			float stepAmount = isLeft ? -1.0F : 1.0F;
			this.setSliderValue(this.value + (stepAmount / Math.max(1.0D, this.width - this.handleLength)));
			return true;
		}
		return false;
	}
	protected void setSliderValue(double pValue) {
		double d0 = this.value;
		this.value = MathHelper.clamp(pValue, 0.0D, 1.0D);
		if(d0 != this.value) {
			this.applyValue();
		}
		this.updateMessage();
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.font;
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		if(this.drawBackground) {
			minecraft.getTextureManager().bind(customTexture);
			if(this.customTexture.equals(WIDGETS_LOCATION)) {
				blit(pMatrixStack, this.x, this.y, this.getBlitOffset(), 0, 46, this.width / 2, this.height, textureWidth, textureHeight);
				blit(pMatrixStack, this.x + this.width / 2, this.y, this.getBlitOffset(), 200 - this.width / 2, 46, this.width / 2, this.height, textureWidth, textureHeight);
			} else {
				blit(pMatrixStack, this.x, this.y, this.getBlitOffset(), this.textureUBackground, this.textureVBackground, this.width, this.height, textureWidth, textureHeight);
			}
		}
		this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);

		if(!disableMessage) {
			int j = getFGColor();
			drawCenteredString(pMatrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
		}
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}

	@Override
	public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
		if(onTooltip!=null) {
			onTooltip.onTooltip(this, pPoseStack, pMouseX, pMouseY);
		}
	}

	@Override
	protected void updateMessage() {
		if(localKey.isEmpty()) {
			this.setMessage(new StringTextComponent(String.valueOf(toValue(value))));
			return;
		}
		this.setMessage(new TranslationTextComponent(localKey, toValue(value)));
	}

	@Override
	protected void applyValue() {
		valueSetter.setValue(toValue(value));
	}
	public static interface IGetValue {
		double getValue();
	}

	public static interface ISetValue {
		void setValue(double sliderValue);
	}

	public interface ITooltip {
		void onTooltip(Widget wdget, MatrixStack matrixStack, int pMouseX, int pMouseY);
	}
}
