package lotr.common.item.clientside;

import lotr.client.gui.ExtendedGuiHornSelect;
import net.minecraft.client.Minecraft;

public class CommandHornClientSide {
	public void openMenu() {
		Minecraft.getInstance().setScreen(new ExtendedGuiHornSelect());
	}
}
