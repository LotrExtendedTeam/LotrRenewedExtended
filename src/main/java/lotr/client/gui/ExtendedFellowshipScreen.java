package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

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
		TextComponent title = new StringTextComponent("Middle-earth Fellowships");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		super.render(matStack, mouseX, mouseY, tick);
	}
}
