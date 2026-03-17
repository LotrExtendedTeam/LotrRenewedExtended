package lotr.client.gui.menu;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedBasicAbstractScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedClientOptionsScreen;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.client.gui.MiddleEarthMenuScreen;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedAbstractOptionsScreen extends MiddleEarthMenuScreen {
	public static Class<? extends ExtendedBasicAbstractScreen> lastSettingScreen = null;
	private String errorText = null;

	public ExtendedAbstractOptionsScreen() {
		super(new StringTextComponent("Options"));
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		if (errorText != null) {
			int y = 20;
			for (String line : errorText.split("\n")) {
				this.font.draw(matStack, line, 10, y, 0xFF5555);
				y += 10;
			}
			MiddleEarthMasterMenuScreen.resetLastMenuScreen();
			return;
		}
		try {
			if (lastSettingScreen != null) {
				this.minecraft.setScreen(lastSettingScreen.newInstance());
			} else {
				this.minecraft.setScreen(ExtendedClientOptionsScreen.class.newInstance());
			}
		} catch (InstantiationException | IllegalAccessException e) {
			errorText = getStackTrace(e);
		}
	}

	private String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
