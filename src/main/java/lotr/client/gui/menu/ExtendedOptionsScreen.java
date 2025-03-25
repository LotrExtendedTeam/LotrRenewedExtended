package lotr.client.gui.menu;

import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton;
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
	private ExtendedTogglableTitleButton buttonFriendlyFire;
	private ExtendedTogglableTitleButton buttonFriendlyFireUnit;
	//private Button buttonAlignment;
	private ExtendedTogglableTitleButton buttonMapLocation;
	//private Button buttonConquest;
	private ExtendedTogglableTitleButton buttonFeminineRank;
	private ExtendedTogglableTitleButton buttonHiredUnitDeathMessages;
	private ExtendedTogglableTitleButton buttonHighContrastMessages;

	private ExtendedTogglableTitleButton forceStructureBlockAirViewing;
	private Slider structureBlockOutlineViewDistance;
	private ExtendedTogglableTitleButton enableClientDevMode;

	@Override
	public void onClose() {
		structureBlockOutlineViewDistance.dragging=false;
		setConfigStrBlockRenderDistance((int) structureBlockOutlineViewDistance.getValue());
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		int x=guiLeft;
		int y=guiTop+40;
		addButton(buttonFriendlyFire = new ExtendedTogglableTitleButton(x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFire.setting"), 
				button -> toggleFF(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(buttonFriendlyFireUnit = new ExtendedTogglableTitleButton(x, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFireUnit.setting"), 
				button -> toggleHUFF(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(buttonMapLocation = new ExtendedTogglableTitleButton(x, y+48, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.mapLocation.setting"), 
				button -> toggleML(), "gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(buttonFeminineRank = new ExtendedTogglableTitleButton(x, y+96, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.rankGender.setting"), 
				button -> toggleFRO(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(buttonHiredUnitDeathMessages = new ExtendedTogglableTitleButton(x, y+120, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.hired_unit_death.setting"), 
				button -> toggleHUDM(), "gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(buttonHighContrastMessages = new ExtendedTogglableTitleButton(x, y+144, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.high_contrast_messages.setting"), 
				button -> toggleHCM(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		//addButton(buttonAlignment = new Button(x, y+48, 200, 20, new StringTextComponent("Show Alignment"), button -> toggleA()));
		//addButton(buttonConquest = new Button(x, y+96, 200, 20, new StringTextComponent("Conquest Kills"), button -> toggleCK()));

		addButton(enableClientDevMode = new ExtendedTogglableTitleButton(x+204, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting"), 
				button -> toggleForgeConfigValue(ExtendedClientConfig.enableDevMode), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(forceStructureBlockAirViewing = new ExtendedTogglableTitleButton(x+204, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.fsbr.setting"), 
				button -> toggleForgeConfigValue(ExtendedClientConfig.forceStructureBlockAirViewing), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
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
//		for (Widget widget : this.buttons) {
//			if (widget instanceof ExtendedGuiOptionsButton) {
//				ExtendedGuiOptionsButton menuButton = (ExtendedGuiOptionsButton) widget;
//				if (menuButton.visible && menuButton.isHovered() && menuButton.getMessage() != null)
//					renderTooltip(matStack, menuButton.getMessage(), mouseX, mouseY);
//			}
//		}
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		buttonFriendlyFire.tickTitle(pd.getAlignmentData().isFriendlyFireEnabled());
		buttonFriendlyFireUnit.tickTitle(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHurtHiredUnits());
		buttonMapLocation.tickTitle(pd.getMiscData().getShowMapLocation());
		buttonFeminineRank.tickTitle(pd.getMiscData().getPreferredRankGender().equals(RankGender.FEMININE));
		buttonHiredUnitDeathMessages.tickTitle(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().shouldShowHiredUnitDeathMessages());
		buttonHighContrastMessages.tickTitle(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().doHighContrastMessages());
		
		if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
			enableClientDevMode.visible = true;
			forceStructureBlockAirViewing.visible = true;
			structureBlockOutlineViewDistance.visible = true;
			if(ExtendedServerConfig.enableDevMode.get()) {
				enableClientDevMode.setMessage(new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting.forced"));
			}else {
				enableClientDevMode.tickTitle(ExtendedClientConfig.enableDevMode.get());
			}
			forceStructureBlockAirViewing.tickTitle(ExtendedClientConfig.forceStructureBlockAirViewing.get());
		}else {
			enableClientDevMode.visible = false;
			forceStructureBlockAirViewing.visible = false;
			structureBlockOutlineViewDistance.visible = false;
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	public void toggleFF() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		pd.getAlignmentData().toggleFriendlyFireEnabledAndSendToServer();
	}
	public void toggleHUFF() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHurtHiredUnitsAndSendToServer();
	}
	public void toggleHUDM() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHiredUnitDeathMessagesAndSendToServer();
	}
	public void toggleHCM() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHighContrastMessagesAndSendToServer();
	}
//	public void toggleA() {
//		Minecraft mc = Minecraft.getInstance();
//		mc.gui.getChat().addMessage(new StringTextComponent("Not implemented"));
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

	public static class ExtendedRenderDistanceSliderHandler implements ISlider {
		@Override
		public void onChangeSliderValue(Slider slider) {
			if(slider.dragging) {
				MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = (int) slider.getValue();
			}
			if(!slider.dragging) {
				setConfigStrBlockRenderDistance((int) slider.getValue());
			}
		}
	}

	public static void setConfigStrBlockRenderDistance(int value) {
		ExtendedClientConfig.structureBlockOutlineViewDistance.set(value);
		MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = value;
	}
}
