package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.widget.button.ExtendedGuiOptionsButton;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.RankGender;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class ExtendedGuiOptions extends MiddleEarthMenuScreen {

	public ExtendedGuiOptions() {
		super(new StringTextComponent("OPTIONS"));
	}
	private ExtendedGuiOptionsButton buttonFriendlyFire;
	//private ExtendedGuiOptionsButton buttonHiredDeathMessages;
	//private ExtendedGuiOptionsButton buttonAlignment;
	private ExtendedGuiOptionsButton buttonMapLocation;
	//private ExtendedGuiOptionsButton buttonConquest;
	private ExtendedGuiOptionsButton buttonFeminineRank;

	@Override
	public void init() {
		super.init();
		int x=guiLeft;
		int y=guiTop+40;
		addButton(buttonFriendlyFire = new ExtendedGuiOptionsButton(0, x, y, 200, 20, new StringTextComponent("Friendly Fire"), button -> toggleFF()));
		//addButton(buttonHiredDeathMessages = new ExtendedGuiOptionsButton(1, x, y+24, 200, 20, new StringTextComponent("Hired Death Messages"), button -> toggleHDM()));
		//addButton(buttonAlignment = new ExtendedGuiOptionsButton(2, x, y+48, 200, 20, new StringTextComponent("Show Alignment"), button -> toggleA()));
		addButton(buttonMapLocation = new ExtendedGuiOptionsButton(3, x, y+72, 200, 20, new StringTextComponent("Map Location"), button -> toggleML()));
		//addButton(buttonConquest = new ExtendedGuiOptionsButton(5, x, y+96, 200, 20, new StringTextComponent("Conquest Kills"), button -> toggleCK()));
		addButton(buttonFeminineRank = new ExtendedGuiOptionsButton(4, x, y+120, 200, 20, new StringTextComponent("Feminine Rank Override"), button -> toggleFRO()));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new StringTextComponent("Middle-earth Options");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		title = new StringTextComponent("World Settings");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop + 30), 16777215);
		super.render(matStack, mouseX, mouseY, tick);
//		for (Widget widget : this.buttons) {
//			if (widget instanceof ExtendedGuiOptionsButton) {
//				ExtendedGuiOptionsButton menuButton = (ExtendedGuiOptionsButton) widget;
//				if (menuButton.visible && menuButton.isHovered() && menuButton.getMessage() != null)
//					renderTooltip(matStack, menuButton.getMessage(), mouseX, mouseY);
//			}
//		}
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		buttonFriendlyFire.setMessage(new StringTextComponent("Friendly Fire: "+ (pd.getAlignmentData().isFriendlyFireEnabled()?"ON":"OFF")));
		buttonMapLocation.setMessage(new StringTextComponent("Show Map Location: "+ (pd.getMiscData().getShowMapLocation()?"ON":"OFF")));
		buttonFeminineRank.setMessage(new StringTextComponent("Femining Rank Override: "+ (pd.getMiscData().getPreferredRankGender()==RankGender.FEMININE?"ON":"OFF")));
	}
	
	public void toggleFF() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		pd.getAlignmentData().toggleFriendlyFireEnabledAndSendToServer();
	}
//	public void toggleHDM() {
//		Minecraft mc = Minecraft.getInstance();
//		mc.gui.getChat().addMessage(new StringTextComponent("Hired Units haven't been added yet"));
//	}
//	public void toggleA() {
//		Minecraft mc = Minecraft.getInstance();
//		mc.gui.getChat().addMessage(new StringTextComponent("Not implimented"));
//	}
	public void toggleML() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		pd.getMiscData().toggleShowMapLocationAndSendToServer();
	}
//	public void toggleCK() {
//		Minecraft mc = Minecraft.getInstance();
//		mc.gui.getChat().addMessage(new StringTextComponent("Conquest haven't been added yet"));
//	}
	public void toggleFRO() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		RankGender g = pd.getMiscData().getPreferredRankGender();
		if(g==RankGender.MASCULINE) {
			pd.getMiscData().setPreferredRankGenderAndSendToServer(RankGender.FEMININE);
		}
		else {
			pd.getMiscData().setPreferredRankGenderAndSendToServer(RankGender.MASCULINE);
		}
	}

}
