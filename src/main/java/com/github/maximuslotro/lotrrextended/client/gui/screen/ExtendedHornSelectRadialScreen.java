package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.List;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedImageButtonButton;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSelectHornPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSelectRadialScreen extends ExtendedRadialScreen {
	private final List<ExtendedUnitHornTypes> options;

	public ExtendedHornSelectRadialScreen() {
		super(new TranslationTextComponent("gui.lotrextended.horn_select.title"));
		this.options = ExtendedUnitHornTypes.getSelectableHornTypes().stream().collect(Collectors.toList());
	}

	@Override
	public void init() {
		super.init();
		this.addButton(new ExtendedImageButtonButton(0, this.height -20, 20, 20, 20, 0, 20, ExtendedAbstractWindowedScreen.EXTENDED_WIDGETS, button -> {
			Minecraft.getInstance().setScreen(new ExtendedHornSelectTypeScreen());
		}) {
			@Override
			public void renderToolTip(MatrixStack pPoseStack, int pMouseX, int pMouseY) {
				ExtendedHornSelectRadialScreen.this.renderTooltip(pPoseStack, font.split(new TranslationTextComponent("gui.lotrextended.horn_select.menutype.tooltip"), 200), pMouseX, pMouseY);
			}
		});
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(super.mouseClicked(mouseX, mouseY, button)) {
			return true;
		}
		if (button == 0 && hoveredIndex != -1) {
			ExtendedPacketHandler.sendToServer(new ExtendedCSelectHornPacket(options.get(hoveredIndex)));
			this.onClose();
			return true;
		}
		return false;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		ItemStack itemstack = this.minecraft.player.getMainHandItem();
		if (itemstack == null || itemstack.getItem() != ExtendedItems.COMMANDHORN.get()) {
			this.onClose();
		}
	}

	@Override
	public int getOptionsCount() {
		return options.size();
	}

	@Override
	public ExtendedColoredText getText(int index) {
		return options.get(index);
	}
}
