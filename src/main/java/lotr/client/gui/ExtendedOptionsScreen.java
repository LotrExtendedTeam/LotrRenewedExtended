package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.widget.button.ExtendedGuiOptionsButton;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.RankGender;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedOptionsScreen extends MiddleEarthMenuScreen {

	public ExtendedOptionsScreen() {
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
		addButton(buttonFriendlyFire = new ExtendedGuiOptionsButton(0, x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFire.title"), button -> toggleFF()));
		//addButton(buttonHiredDeathMessages = new ExtendedGuiOptionsButton(1, x, y+24, 200, 20, new StringTextComponent("Hired Death Messages"), button -> toggleHDM()));
		//addButton(buttonAlignment = new ExtendedGuiOptionsButton(2, x, y+48, 200, 20, new StringTextComponent("Show Alignment"), button -> toggleA()));
		addButton(buttonMapLocation = new ExtendedGuiOptionsButton(3, x, y+72, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.mapLocation.title"), button -> toggleML()));
		//addButton(buttonConquest = new ExtendedGuiOptionsButton(5, x, y+96, 200, 20, new StringTextComponent("Conquest Kills"), button -> toggleCK()));
		addButton(buttonFeminineRank = new ExtendedGuiOptionsButton(4, x, y+120, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.rankGender.title"), button -> toggleFRO()));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new TranslationTextComponent("gui.lotrextended.settings.title");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		title = new TranslationTextComponent("gui.lotrextended.settings.worldsettings");
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
		TextComponent ff = new TranslationTextComponent("gui.lotrextended.settings.friendlyFire.setting");
		buttonFriendlyFire.setMessage(ff.append(new TranslationTextComponent(pd.getAlignmentData().isFriendlyFireEnabled()? "gui.lotrextended.base.enabled" : "gui.lotrextended.base.disabled")));
		TextComponent map = new TranslationTextComponent("gui.lotrextended.settings.mapLocation.setting");
		buttonMapLocation.setMessage(map.append(new TranslationTextComponent(pd.getMiscData().getShowMapLocation()? "gui.lotrextended.base.shown" : "gui.lotrextended.base.hidden")));
		TextComponent rank = new TranslationTextComponent("gui.lotrextended.settings.rankGender.setting");
		buttonFeminineRank.setMessage(rank.append(new TranslationTextComponent(pd.getMiscData().getPreferredRankGender().equals(RankGender.FEMININE)? "gui.lotrextended.base.enabled" : "gui.lotrextended.base.disabled")));
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
