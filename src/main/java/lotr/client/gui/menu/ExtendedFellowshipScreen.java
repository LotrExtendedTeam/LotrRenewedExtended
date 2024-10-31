package lotr.client.gui.menu;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMenuScreen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedFellowshipScreen extends MiddleEarthMenuScreen {

	public ExtendedFellowshipScreen() {
		super(new StringTextComponent("FELLOWSHIPS"));
	}


	@Override
	public void init() {
		super.init();
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new TranslationTextComponent("gui.lotrextended.fellowships.title");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		TextComponent message = new TranslationTextComponent("gui.lotrextended.fellowships.use_command");
		this.font.draw(matStack, message.withStyle(TextFormatting.RED), guiLeft+(100-(this.font.width(message)/2)), (guiTop + 30), 16777215);
		TextComponent command = new TranslationTextComponent("/fellowship");
		this.font.draw(matStack, command.withStyle(TextFormatting.AQUA), guiLeft+(100-(this.font.width(command)/2)), (guiTop + 40), 16777215);
		super.render(matStack, mouseX, mouseY, tick);
	}
}
