package lotr.client.gui.menu;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.client.gui.MiddleEarthMenuScreen;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedAdvancementsScreen extends MiddleEarthMenuScreen {
	public ExtendedAdvancementsScreen() {
		super(new StringTextComponent("ADVANCEMENTS"));
	}

	@Override
	public void init() {}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		MiddleEarthMasterMenuScreen.resetLastMenuScreen();
		this.minecraft.setScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancements()));
	}
}
