package lotr.client.gui.widget.button;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class ExtendedGuiOptionsButton extends Button{

	public final int id;
	public ExtendedGuiOptionsButton(int id, int x, int y, int width, int height, ITextComponent message, Button.IPressable press) {
		super(x, y, width, height, message, press);
		this.id = id;
	}
}
