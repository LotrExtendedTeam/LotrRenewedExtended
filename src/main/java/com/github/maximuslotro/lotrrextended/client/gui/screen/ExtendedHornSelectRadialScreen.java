package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.List;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSelectHornPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSelectRadialScreen extends ExtendedRadialScreen {
	private final List<ExtendedUnitHornTypes> options;

	public ExtendedHornSelectRadialScreen() {
		super(new TranslationTextComponent("gui.lotrextended.horn_select.title"));
		this.options = ExtendedUnitHornTypes.getSelectableHornTypes().stream().collect(Collectors.toList());
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0 && hoveredIndex != -1) {
			ExtendedPacketHandler.sendToServer(new ExtendedCSelectHornPacket(options.get(hoveredIndex)));
			this.onClose();
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
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
			return;
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
