package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.widget.button.ExtendedGuiOptionsButton;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class ExtendedGuiOptions extends MiddleEarthMenuScreen {

	public ExtendedGuiOptions() {
		super(new StringTextComponent("OPTIONS"));
	}
	private ExtendedGuiOptionsButton buttonFriendlyFire;
	private ExtendedGuiOptionsButton buttonHiredDeathMessages;
	private ExtendedGuiOptionsButton buttonAlignment;
	private ExtendedGuiOptionsButton buttonMapLocation;
	private ExtendedGuiOptionsButton buttonConquest;
	private ExtendedGuiOptionsButton buttonFeminineRank;

	@Override
	public void init() {
		super.init();
		int x=guiLeft;
		int y=guiTop+40;
		addButton(buttonFriendlyFire = new ExtendedGuiOptionsButton(0, x, y, 200, 20, new StringTextComponent("Friendly Fire"), button -> toggleFF()));
		addButton(buttonHiredDeathMessages = new ExtendedGuiOptionsButton(1, x, y+24, 200, 20, new StringTextComponent("Hired Death Messages"), button -> toggleHDM()));
		addButton(buttonAlignment = new ExtendedGuiOptionsButton(2, x, y+48, 200, 20, new StringTextComponent("Show Alignment"), button -> toggleA()));
		addButton(buttonMapLocation = new ExtendedGuiOptionsButton(3, x, y+72, 200, 20, new StringTextComponent("Map Location"), button -> toggleML()));
		addButton(buttonConquest = new ExtendedGuiOptionsButton(5, x, y+96, 200, 20, new StringTextComponent("Conquest Kills"), button -> toggleCK()));
		addButton(buttonFeminineRank = new ExtendedGuiOptionsButton(4, x, y+120, 200, 20, new StringTextComponent("Feminine Rank Override"), button -> toggleFRO()));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new StringTextComponent("Middle-earth Options");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 40), 16777215);
		title = new StringTextComponent("World Settings");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop + 30), 16777215);
		super.render(matStack, mouseX, mouseY, tick);
		for (Widget widget : this.buttons) {
			if (widget instanceof ExtendedGuiOptionsButton) {
				ExtendedGuiOptionsButton menuButton = (ExtendedGuiOptionsButton) widget;
				//if (menuButton.isHovered() && menuButton.getMessage() != null)
				//	renderTooltip(matStack, menuButton.getMessage(), mouseX, mouseY);
			}
		}
	}
	
	public void toggleFF() {
		System.out.println("Howdy World!");
	}
	public void toggleHDM() {
		System.out.println("Howdy World!");
	}
	public void toggleA() {
		System.out.println("Howdy World!");
	}
	public void toggleML() {
		System.out.println("Howdy World!");
	}
	public void toggleCK() {
		System.out.println("Howdy World!");
	}
	public void toggleFRO() {
		System.out.println("Howdy World!");
	}

}
