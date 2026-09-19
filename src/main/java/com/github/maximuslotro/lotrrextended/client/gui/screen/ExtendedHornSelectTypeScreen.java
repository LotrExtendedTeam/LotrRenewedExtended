package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHornSelectTypeScreen extends ExtendedBasicAbstractScreen {
	private static final ResourceLocation TEXTURE = new ResourceLocation("lotrextended:textures/gui/screen160x90.png");

	public ExtendedHornSelectTypeScreen() {
		super(new TranslationTextComponent("gui.lotrextended.horn_select.menutype.title"), 160, 96);
	}

	@Override
	public void init() {
		super.init();
		this.addButton(new Button(this.leftPos + 20, this.topPos + 36, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.menutype.legacy"), button -> {
			ExtendedClientConfig.hornSelectScreenType.set(1);
			ExtendedClientsideSeparatedScreenHandler.openHornModeSelect();
		}));
		this.addButton(new Button(this.leftPos + 20, this.topPos + 66, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.menutype.extended"), button -> {
			ExtendedClientConfig.hornSelectScreenType.set(2);
			ExtendedClientsideSeparatedScreenHandler.openHornModeSelect();
		}));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		minecraft.getTextureManager().bind(TEXTURE);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.font.draw(matStack, title, leftPos + ((this.imageWidth - font.width(title)) / 2), topPos + 10, 4210752);
		super.render(matStack, mouseX, mouseY, tick);
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
	public boolean isPauseScreen() {
		return false;
	}
}
