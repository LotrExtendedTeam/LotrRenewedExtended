package lotr.client.gui.widget.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedGuiOptionsButton extends Button{

	public final int id;
	public ExtendedGuiOptionsButton(int id, int x, int y, int width, int height, ITextComponent message) {
		super(x, y, width, height, message, button -> {
			Minecraft instance = Minecraft.getInstance();
            instance.gui.getChat().addMessage(new StringTextComponent("Button: "+id+" pressed"));
		});
		this.id = id;
	}
	
//	@Override
//	public void renderToolTip(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
//		if(this.visible && this.isHovered()) {
//			
//		}
//	}

}
