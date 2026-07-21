package com.github.maximuslotro.lotrrextended.client.gui.widget;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.Widget;
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

	public ExtendedSlider(int pX, int pY, int pWidth, int pHeight, String localKey, double minVal, double maxVal, float steps, IGetValue valueGetter, ISetValue valueSetter) {
		super(pX, pY, pWidth, pHeight, StringTextComponent.EMPTY, 0);
		this.localKey = localKey;
		this.minValue = minVal;
		this.maxValue = maxVal;
		this.steps = steps;
		if(steps<0.1) {
			ExtendedLog.error("Step count set to low: "+steps);
		}
		this.valueGetter = valueGetter;
		this.valueSetter = valueSetter;
		this.value = toPercentage(valueGetter.getValue());
		this.updateMessage();
	}

	public double toPercentage(double pValue) {
		return MathHelper.clamp((clamp(pValue)-minValue)/(maxValue-minValue), 0.0D, 1.0D);
	}

	public double toValue(double pValue) {
		return this.clamp(MathHelper.lerp(MathHelper.clamp(pValue, 0.0D, 1.0D), minValue, this.maxValue));
	}

	private double clamp(double pValue) {
		if (this.steps > 0.0F) {
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
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		super.renderButton(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
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
