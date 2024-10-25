package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BannerConfigurationScreen extends ExtendedBasicAbstractScreen {
	private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/horn_select.png");

	protected BannerConfigurationScreen() {
		super(new StringTextComponent("BANNERCONFIGURATION"), 176, 256);
	}

	@Override
	public void init() {
		//this.addButton(new Button(this.leftPos + 40, this.topPos + 40, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_halt_reddy"), button -> setHorn(1.0F, button)));
		//this.addButton(new Button(this.leftPos + 40, this.topPos + 75, 120, 20, new TranslationTextComponent("gui.lotrextended.horn_select.option_summon"), button -> setHorn(3.0F, button)));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		minecraft.textureManager.bind(guiTexture);
		blit(matStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		TextComponent title = new TranslationTextComponent("gui.lotrextended.banner_configuration.title");
		this.font.draw(matStack, title, leftPos + (85 - (font.width(title) / 2)), topPos + 11, 4210752);
		super.render(matStack, mouseX, mouseY, tick);
	}

	@Override
	public void tick() {
		super.tick();
	}
}