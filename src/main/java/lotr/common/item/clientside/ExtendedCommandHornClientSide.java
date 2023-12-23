package lotr.common.item.clientside;

import lotr.client.gui.ExtendedGuiHornSelect;
import net.minecraft.client.Minecraft;

public class ExtendedCommandHornClientSide {
	public void openMenu() {
		Minecraft.getInstance().setScreen(new ExtendedGuiHornSelect());
	}
}
