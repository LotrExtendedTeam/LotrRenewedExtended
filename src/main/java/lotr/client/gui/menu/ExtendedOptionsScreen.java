package lotr.client.gui.menu;

import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
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
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.minecraftforge.fml.client.gui.widget.Slider.ISlider;

public class ExtendedOptionsScreen extends MiddleEarthMenuScreen {
	public int option = 0;

	// World settings
	private ExtendedTogglableTitleButton buttonFriendlyFire;
	private ExtendedTogglableTitleButton buttonFriendlyFireUnit;
	private ExtendedTogglableTitleButton buttonMapLocation;
	//private Button buttonConquest;

	// Player settings
	private ExtendedTogglableTitleButton buttonFeminineRank;
	private ExtendedTogglableTitleButton buttonHiredUnitDeathMessages;
	private ExtendedTogglableTitleButton buttonHighContrastMessages;
	//private Button buttonAlignment;

	// Detail settings
	private ExtendedTogglableTitleButton removeCertianPlantParticles;

	// Dev settings
	private ExtendedTogglableTitleButton forceStructureBlockAirViewing;
	private Slider structureBlockOutlineViewDistance;
	private ExtendedTogglableTitleButton enableClientDevMode;

	// Tabs
	private ExtendedColorChangingButton worldSettings;
	private ExtendedColorChangingButton playerSettings;
	private ExtendedColorChangingButton detailSettings;
	private ExtendedColorChangingButton devSettings;

	public ExtendedOptionsScreen() {
		super(new StringTextComponent("OPTIONS"));
	}

	@Override
	public void onClose() {
		structureBlockOutlineViewDistance.dragging=false;
		setConfigStrBlockRenderDistance((int) structureBlockOutlineViewDistance.getValue());
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		int x=guiLeft+80;
		int xTabs=guiLeft-30;
		int y=guiTop+40;
		addButton(worldSettings = new ExtendedColorChangingButton(xTabs, y, 100, 20, new TranslationTextComponent("gui.lotrextended.settings.worldsettings"), 
			button -> {
				option=0;
			}, 0.13f, 0.92f, 0.71f) {
			@Override
			public boolean setColor(){
				return option==0;
			}
		});
		addButton(playerSettings = new ExtendedColorChangingButton(xTabs, y+24, 100, 20, new TranslationTextComponent("gui.lotrextended.settings.usersettings"), 
			button -> {
				option=1;
			}, 0.13f, 0.92f, 0.71f) {
			@Override
			public boolean setColor(){
				return option==1;
			}
		});
		addButton(detailSettings = new ExtendedColorChangingButton(xTabs, y+48, 100, 20, new TranslationTextComponent("gui.lotrextended.settings.detailsettings"), 
			button -> {
				option=2;
			}, 0.13f, 0.92f, 0.71f) {
			@Override
			public boolean setColor(){
				return option==2;
			}
		});
		addButton(devSettings = new ExtendedColorChangingButton(xTabs, y+72, 100, 20, new TranslationTextComponent("gui.lotrextended.settings.devsettings"), 
			button -> {
				option=3;
			}, 0.13f, 0.92f, 0.71f) {
			@Override
			public boolean setColor(){
				return option==3;
			}
		});

		// World Settings
		addButton(buttonFriendlyFire = new ExtendedTogglableTitleButton(x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFire.setting"), 
				button -> toggleFF(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(buttonFriendlyFireUnit = new ExtendedTogglableTitleButton(x, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.friendlyFireUnit.setting"), 
				button -> toggleHUFF(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(buttonMapLocation = new ExtendedTogglableTitleButton(x, y+48, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.mapLocation.setting"), 
				button -> toggleML(), "gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		//addButton(buttonConquest = new Button(x, y+96, 200, 20, new StringTextComponent("Conquest Kills"), button -> toggleCK()));

		// Player Settings
		addButton(buttonFeminineRank = new ExtendedTogglableTitleButton(x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.rankGender.setting"), 
				button -> toggleFRO(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(buttonHiredUnitDeathMessages = new ExtendedTogglableTitleButton(x, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.hired_unit_death.setting"), 
				button -> toggleHUDM(), "gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(buttonHighContrastMessages = new ExtendedTogglableTitleButton(x, y+48, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.high_contrast_messages.setting"), 
				button -> toggleHCM(), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		//addButton(buttonAlignment = new Button(x, y+48, 200, 20, new StringTextComponent("Show Alignment"), button -> toggleA()));

		// Detail Settings
		addButton(removeCertianPlantParticles = new ExtendedTogglableTitleButton(x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.pipeweedparticles.setting"), 
			button -> toggleForgeConfigValue(ExtendedClientConfig.enablePlantParticles), "gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));

		// Dev settings
		addButton(enableClientDevMode = new ExtendedTogglableTitleButton(x, y, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting"), 
				button -> toggleForgeConfigValue(ExtendedClientConfig.enableDevMode), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(forceStructureBlockAirViewing = new ExtendedTogglableTitleButton(x, y+24, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.fsbr.setting"), 
				button -> toggleForgeConfigValue(ExtendedClientConfig.forceStructureBlockAirViewing), "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(structureBlockOutlineViewDistance = new Slider(x, y+48, 200, 20, new TranslationTextComponent("gui.lotrextended.settings.structureblockoutlineviewdistance.prefix"), 
			new TranslationTextComponent("gui.lotrextended.settings.structureblockoutlineviewdistance.suffix"), 32.0D, MixinConstants.StructureLimit, 
			ExtendedClientConfig.structureBlockOutlineViewDistance.get(), false, true , button -> {}, new ExtendedRenderDistanceSliderHandler()));
		
		for(Widget button : this.buttons) {
			if(button instanceof ExtendedTogglableTitleButton || button instanceof Slider) {
				button.visible = false;
			}
		}
		if(option==0) {
			buttonFriendlyFire.visible = true;
			buttonFriendlyFireUnit.visible = true;
			buttonMapLocation.visible = true;
		}else if(option==1) {
			buttonFeminineRank.visible = true;
			buttonHiredUnitDeathMessages.visible = true;
			buttonHighContrastMessages.visible = true;
		}else if(option==2){
			removeCertianPlantParticles.visible = true;
		}else if(option==3) {
			enableClientDevMode.visible = true;
			forceStructureBlockAirViewing.visible = true;
			structureBlockOutlineViewDistance.visible = true;
		}
		if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
			devSettings.visible = true;
		}else {
			devSettings.visible = false;
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		// Render titles
		TextComponent title = new TranslationTextComponent("gui.lotrextended.settings.title");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		// Handle dev mode visibility
		if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
			devSettings.visible = true;
		}else {
			devSettings.visible = false;
			if(option==3) {
				option = 2;
			}
		}
		// Hide all buttons
		for(Widget button : this.buttons) {
			if(button instanceof ExtendedTogglableTitleButton || button instanceof Slider) {
				button.visible = false;
			}
		}
		// Update and show buttons by catagory
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		if(option==0) {
			buttonFriendlyFire.tickTitle(pd.getAlignmentData().isFriendlyFireEnabled());
			buttonFriendlyFireUnit.tickTitle(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHurtHiredUnits());
			buttonMapLocation.tickTitle(pd.getMiscData().getShowMapLocation());
		}else if(option==1) {
			buttonFeminineRank.tickTitle(pd.getMiscData().getPreferredRankGender().equals(RankGender.FEMININE));
			buttonHiredUnitDeathMessages.tickTitle(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().shouldShowHiredUnitDeathMessages());
			buttonHighContrastMessages.tickTitle(((ExtendedLOTRPlayerData)pd).getExtendedMiscData().doHighContrastMessages());
		}else if(option==2){
			removeCertianPlantParticles.tickTitle(ExtendedClientConfig.enablePlantParticles.get());
		}else if(option==3) {
			if(ExtendedServerConfig.enableDevMode.get()) {
				enableClientDevMode.visible = true;
				enableClientDevMode.setMessage(new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting.forced"));
			}else {
				enableClientDevMode.tickTitle(ExtendedClientConfig.enableDevMode.get());
			}
			forceStructureBlockAirViewing.tickTitle(ExtendedClientConfig.forceStructureBlockAirViewing.get());
			structureBlockOutlineViewDistance.visible = true;
		}else {
			option = 0;
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	public static void toggleFF() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		pd.getAlignmentData().toggleFriendlyFireEnabledAndSendToServer();
	}
	public static void toggleHUFF() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHurtHiredUnitsAndSendToServer();
	}
	public static void toggleHUDM() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHiredUnitDeathMessagesAndSendToServer();
	}
	public static void toggleHCM() {
		Minecraft mc = Minecraft.getInstance();
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)LOTRLevelData.clientInstance().getData(mc.player);
		pd.getExtendedMiscData().toggleHighContrastMessagesAndSendToServer();
	}
//	public static void toggleA() {
//		Minecraft mc = Minecraft.getInstance();
//		mc.gui.getChat().addMessage(new StringTextComponent("Not implemented"));
//	}
	public static void toggleML() {
		Minecraft mc = Minecraft.getInstance();
		LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(mc.player);
		pd.getMiscData().toggleShowMapLocationAndSendToServer();
	}
//	public static void toggleCK() {
//		Minecraft mc = Minecraft.getInstance();
//		mc.gui.getChat().addMessage(new StringTextComponent("Conquest haven't been added yet"));
//	}
	public static void toggleFRO() {
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

	public static void toggleForgeConfigValue(ForgeConfigSpec.BooleanValue value) {
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
