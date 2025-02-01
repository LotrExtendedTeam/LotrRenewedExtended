package lotr.client.gui.menu;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.mixinhelpers.MixinConstants;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMenuScreen;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.RankGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.minecraftforge.fml.client.gui.widget.Slider.ISlider;

public class ExtendedOptionsScreen extends MiddleEarthMenuScreen {

	public ExtendedOptionsScreen() {
		super(new StringTextComponent("OPTIONS"));
	}
	private Button buttonFriendlyFire;
	private Button buttonFriendlyFireUnit;
	//private Button buttonHiredDeathMessages;
	//private Button buttonAlignment;
	private Button buttonMapLocation;
	//private Button buttonConquest;
	private Button buttonFeminineRank;

	private Button forceStructureBlockAirViewing;
	private Slider structureBlockOutlineViewDistance;
	private Button enableClientDevMode;

	@Override
	public void onClose() {
		structureBlockOutlineViewDistance.dragging=false;
		setConfigStrBlockRenderDistance(structureBlockOutlineViewDistance.getValue());
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		int x=guiLeft;
		int y=guiTop+40;
		addButton(buttonFriendlyFire = new Button(x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFire.title"), button -> toggleFF()));
		addButton(buttonFriendlyFireUnit = new Button(x, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFireUnit.title"), button -> toggleUFF()));
		addButton(buttonMapLocation = new Button(x, y+48, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.mapLocation.title"), button -> toggleML()));
		addButton(buttonFeminineRank = new Button(x, y+96, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.rankGender.title"), button -> toggleFRO()));

		//addButton(buttonHiredDeathMessages = new Button(x, y+24, 200, 20, new StringTextComponent("Hired Death Messages"), button -> toggleHDM()));
		//addButton(buttonAlignment = new Button(x, y+48, 200, 20, new StringTextComponent("Show Alignment"), button -> toggleA()));
		//addButton(buttonConquest = new Button(x, y+96, 200, 20, new StringTextComponent("Conquest Kills"), button -> toggleCK()));

		addButton(enableClientDevMode = new Button(x+204, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.devtoggle.title"), button -> toggleForgeConfigValue(ExtendedClientConfig.enableDevMode)));
		addButton(forceStructureBlockAirViewing = new Button(x+204, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.fsbr.title"), button -> toggleForgeConfigValue(ExtendedClientConfig.forceStructureBlockAirViewing)));
		addButton(structureBlockOutlineViewDistance = new Slider(x+204, y+48, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.structureblockoutlineviewdistance.prefix"), new TranslationTextComponent("gui.lotrextended.settings.structureblockoutlineviewdistance.suffix"), 32.0D, MixinConstants.StructureLimit, ExtendedClientConfig.structureBlockOutlineViewDistance.get(), false, true , button -> {}, new ExtendedRenderDistanceSliderHandler()));
		if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
			enableClientDevMode.visible = true;
			forceStructureBlockAirViewing.visible = true;
			structureBlockOutlineViewDistance.visible = true;
		}else {
			enableClientDevMode.visible = false;
			forceStructureBlockAirViewing.visible = false;
			structureBlockOutlineViewDistance.visible = false;
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		TextComponent title = new TranslationTextComponent("gui.lotrextended.settings.title");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		title = new TranslationTextComponent("gui.lotrextended.settings.worldsettings");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop + 30), 16777215);
		title = new TranslationTextComponent("gui.lotrextended.settings.usersettings");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop + 126), 16777215);
		if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
			title = new TranslationTextComponent("gui.lotrextended.settings.devsettings");
			this.font.draw(matStack, title, guiLeft+(304-(this.font.width(title)/2)), (guiTop + 30), 16777215);
		}
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
		TextComponent ffu = new TranslationTextComponent("gui.lotrextended.settings.friendlyFireUnit.setting");
		buttonFriendlyFireUnit.setMessage(ffu.append(new TranslationTextComponent(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHurtHiredUnits()? "gui.lotrextended.base.enabled" : "gui.lotrextended.base.disabled")));
		TextComponent map = new TranslationTextComponent("gui.lotrextended.settings.mapLocation.setting");
		buttonMapLocation.setMessage(map.append(new TranslationTextComponent(pd.getMiscData().getShowMapLocation()? "gui.lotrextended.base.shown" : "gui.lotrextended.base.hidden")));
		TextComponent rank = new TranslationTextComponent("gui.lotrextended.settings.rankGender.setting");
		buttonFeminineRank.setMessage(rank.append(new TranslationTextComponent(pd.getMiscData().getPreferredRankGender().equals(RankGender.FEMININE)? "gui.lotrextended.base.enabled" : "gui.lotrextended.base.disabled")));

		if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
			enableClientDevMode.visible = true;
			forceStructureBlockAirViewing.visible = true;
			structureBlockOutlineViewDistance.visible = true;
			if(ExtendedServerConfig.enableDevMode.get()) {
				enableClientDevMode.setMessage(new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting.forced"));
			}else {
				TextComponent ecdm = new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting");
				enableClientDevMode.setMessage(ecdm.append(new TranslationTextComponent(ExtendedClientConfig.enableDevMode.get()? "gui.lotrextended.base.enabled" : "gui.lotrextended.base.disabled")));
			}
			TextComponent fsbr = new TranslationTextComponent("gui.lotrextended.settings.fsbr.setting");
			forceStructureBlockAirViewing.setMessage(fsbr.append(new TranslationTextComponent(ExtendedClientConfig.forceStructureBlockAirViewing.get()? "gui.lotrextended.base.enabled" : "gui.lotrextended.base.disabled")));
		}else {
			enableClientDevMode.visible = false;
			forceStructureBlockAirViewing.visible = false;
			structureBlockOutlineViewDistance.visible = false;
		}
	}

	public void toggleFF() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		pd.getAlignmentData().toggleFriendlyFireEnabledAndSendToServer();
	}
	public void toggleUFF() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHurtHiredUnitsAndSendToServer();
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

	public void toggleForgeConfigValue(ForgeConfigSpec.BooleanValue value) {
		value.set(!value.get());
	}

	public class ExtendedRenderDistanceSliderHandler implements ISlider {
		@Override
		public void onChangeSliderValue(Slider slider) {
			if(slider.dragging) {
				MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = slider.getValue();
			}
			if(!slider.dragging) {
				setConfigStrBlockRenderDistance(slider.getValue());
			}
		}
	}

	public static void setConfigStrBlockRenderDistance(double value) {
		ExtendedClientConfig.structureBlockOutlineViewDistance.set(value);
		MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = value;
	}
}
