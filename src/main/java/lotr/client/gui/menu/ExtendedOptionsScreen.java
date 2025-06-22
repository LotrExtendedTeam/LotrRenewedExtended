package lotr.client.gui.menu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton.IOnTick;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.mixinhelpers.MixinConstants;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMenuScreen;
import lotr.common.config.LOTRConfig;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.RankGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.minecraftforge.fml.client.gui.widget.Slider.ISlider;

public class ExtendedOptionsScreen extends MiddleEarthMenuScreen {
	public int option = 0;
	public List<SettingsCatagory> catagories = new ArrayList<>();

	private Slider structureBlockOutlineViewDistance;
	private Slider dolchestplatewings;
	private Slider cloudRange;
	private Slider alignmentX;
	private Slider alignmentY;
	private Slider speechDuration;


	public ExtendedOptionsScreen() {
		super(new StringTextComponent("OPTIONS"));
	}

	@Override
	public void onClose() {
		structureBlockOutlineViewDistance.dragging=false;
		setConfigStrBlockRenderDistance(structureBlockOutlineViewDistance.getValueInt());
		dolchestplatewings.dragging=false;
		LOTRConfig.CLIENT.dolAmrothChestplateWings.setAndSave(dolchestplatewings.getValueInt());
		cloudRange.dragging=false;
		LOTRConfig.CLIENT.cloudRange.setAndSave(cloudRange.getValueInt());
		alignmentX.dragging=false;
		LOTRConfig.CLIENT.alignmentXOffset.setAndSave(alignmentX.getValueInt());
		alignmentY.dragging=false;
		LOTRConfig.CLIENT.alignmentYOffset.setAndSave(alignmentY.getValueInt());
		speechDuration.dragging=false;
		LOTRConfig.CLIENT.immersiveSpeechDuration.setAndSave(speechDuration.getValueInt());
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		int x=guiLeft+90;
		int xTabs=guiLeft-35;
		int y=guiTop+40;

		for(SettingsCatagory catagories : catagories) {
			catagories.clean();
		}
		catagories.clear();
		structureBlockOutlineViewDistance=null;

		SettingsCatagory world = new SettingsCatagory(this, 0, x, y, xTabs);
		addButton(world.addCatagoryButton("gui.lotrextended.settings.worldsettings"));
		addButton(world.addButton("gui.lotrextended.settings.friendlyFire.setting", button -> toggleFF(), (button, pd) -> pd.getAlignmentData().isFriendlyFireEnabled(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(world.addButton("gui.lotrextended.settings.friendlyFireUnit.setting", button -> toggleHUFF(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHurtHiredUnits(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(world.addButton("gui.lotrextended.settings.mapLocation.setting", button -> toggleML(), (button, pd) -> pd.getMiscData().getShowMapLocation(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		catagories.add(world);


		SettingsCatagory user = new SettingsCatagory(this, 1, x, y, xTabs);
		addButton(user.addCatagoryButton("gui.lotrextended.settings.usersettings"));
		addButton(user.addButton("gui.lotrextended.settings.rankGender.setting", button -> toggleFRO(), (button, pd) -> pd.getMiscData().getPreferredRankGender().equals(RankGender.FEMININE), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(user.addButton("gui.lotrextended.settings.hired_unit_death.setting", button -> toggleHUDM(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().shouldShowHiredUnitDeathMessages(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(user.addButton("gui.lotrextended.settings.high_contrast_messages.setting", button -> toggleHCM(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().doHighContrastMessages(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(user.addButton("gui.lotrextended.settings.unit_v_unit.setting", button -> toggleCBU(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		catagories.add(user);

		SettingsCatagory environment = new SettingsCatagory(this, 2, x, y, xTabs);
		addButton(environment.addCatagoryButton("gui.lotrextended.settings.environmentsettings"));
		addButton(environment.addButton("gui.lotrextended.settings.newweather.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.newWeatherRendering), (button, pd) -> LOTRConfig.CLIENT.newWeatherRendering.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.rainmist.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.rainMist), (button, pd) -> LOTRConfig.CLIENT.rainMist.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.mistymountainsmist.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.mistyMountainsMist), (button, pd) -> LOTRConfig.CLIENT.mistyMountainsMist.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.modsky.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.modSky), (button, pd) -> LOTRConfig.CLIENT.modSky.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.modclouds.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.modClouds), (button, pd) -> LOTRConfig.CLIENT.modClouds.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		cloudRange = addButton(environment.addSlider("gui.lotrextended.settings.cloudrange.prefix", "gui.lotrextended.settings.cloudrange.suffix", 
			0D, 1024D, LOTRConfig.CLIENT.cloudRange.get(), false, true , button -> {}, slider -> {
				if(!slider.dragging) {
					LOTRConfig.CLIENT.cloudRange.setAndSave(slider.getValueInt());
				}
			}));
		addButton(environment.addButton("gui.lotrextended.settings.sunglare.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.sunGlare), (button, pd) -> LOTRConfig.CLIENT.sunGlare.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.northernlights.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.northernLights), (button, pd) -> LOTRConfig.CLIENT.northernLights.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.pipeweedparticles.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enablePlantParticles), (button, pd) -> ExtendedClientConfig.enablePlantParticles.get(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(environment.addButton("gui.lotrextended.settings.newraingroundparticles.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.newRainGroundParticles), (button, pd) -> LOTRConfig.CLIENT.newRainGroundParticles.get(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		catagories.add(environment);


		SettingsCatagory gui = new SettingsCatagory(this, 3, x, y, xTabs);
		addButton(gui.addCatagoryButton("gui.lotrextended.settings.guisettings"));
		addButton(gui.addButton("gui.lotrextended.settings.modmainmenu.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.modMainMenu), (button, pd) -> LOTRConfig.CLIENT.modMainMenu.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(gui.addButton("gui.lotrextended.settings.sepiamap.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.sepiaMap), (button, pd) -> LOTRConfig.CLIENT.sepiaMap.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(gui.addButton("gui.lotrextended.settings.fogofwar.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.fogOfWar), (button, pd) -> LOTRConfig.CLIENT.fogOfWar.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(gui.addButton("gui.lotrextended.settings.imperial.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.imperialWaypointDistances), (button, pd) -> LOTRConfig.CLIENT.imperialWaypointDistances.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(gui.addButton("gui.lotrextended.settings.maplabels.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.mapLabels), (button, pd) -> LOTRConfig.CLIENT.mapLabels.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(gui.addButton("gui.lotrextended.settings.worldhelp.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.showWorldTypeHelp), (button, pd) -> LOTRConfig.CLIENT.showWorldTypeHelp.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		catagories.add(gui);


		SettingsCatagory hud = new SettingsCatagory(this, 4, x, y, xTabs);
		addButton(hud.addCatagoryButton("gui.lotrextended.settings.hudsettings"));
		addButton(hud.addButton("gui.lotrextended.settings.compass.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.compass), (button, pd) -> LOTRConfig.CLIENT.compass.get(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(hud.addButton("gui.lotrextended.settings.compassinfo.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.compassInfo), (button, pd) -> LOTRConfig.CLIENT.compassInfo.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(hud.addButton("gui.lotrextended.settings.alignmentrender.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.displayAlignmentAboveHead), (button, pd) -> LOTRConfig.CLIENT.displayAlignmentAboveHead.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(hud.addButton("gui.lotrextended.settings.renderalignmentbareverywhere.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.showAlignmentEverywhere), (button, pd) -> LOTRConfig.CLIENT.showAlignmentEverywhere.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		alignmentX = addButton(hud.addSlider("gui.lotrextended.settings.alignmentoffsetx.prefix", "gui.lotrextended.settings.alignmentoffset.suffix", 
			-32767, 32767, LOTRConfig.CLIENT.alignmentXOffset.get(), false, true , button -> {}, slider -> {
				if(!slider.dragging) {
					LOTRConfig.CLIENT.alignmentXOffset.setAndSave(slider.getValueInt());
				}
			}));
		alignmentY = addButton(hud.addSlider("gui.lotrextended.settings.alignmentoffsety.prefix", "gui.lotrextended.settings.alignmentoffset.suffix", 
			-32767, 32767, LOTRConfig.CLIENT.alignmentYOffset.get(), false, true , button -> {}, slider -> {
				if(!slider.dragging) {
					LOTRConfig.CLIENT.alignmentYOffset.setAndSave(slider.getValueInt());
				}
			}));
		addButton(hud.addButton("gui.lotrextended.settings.immersivespeech.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.immersiveSpeech), (button, pd) -> LOTRConfig.CLIENT.immersiveSpeech.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		speechDuration = addButton(hud.addSlider("gui.lotrextended.settings.immersivespeechduration.prefix", "gui.lotrextended.settings.immersivespeechduration.suffix", 
			0D, 60D, LOTRConfig.CLIENT.immersiveSpeechDuration.get(), false, true , button -> {}, slider -> {
				if(!slider.dragging) {
					LOTRConfig.CLIENT.immersiveSpeechDuration.setAndSave(slider.getValueInt());
				}
			}));
		addButton(hud.addButton("gui.lotrextended.settings.immersivespeechchat.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.immersiveSpeechChatLog), (button, pd) -> LOTRConfig.CLIENT.immersiveSpeechChatLog.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		catagories.add(hud);


		SettingsCatagory model = new SettingsCatagory(this, 5, x, y, xTabs);
		addButton(model.addCatagoryButton("gui.lotrextended.settings.modelsettings"));
		addButton(model.addButton("gui.lotrextended.settings.dwarfwomen.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.dwarfWomenUseAlexModelStyle), (button, pd) -> LOTRConfig.CLIENT.dwarfWomenUseAlexModelStyle.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(model.addButton("gui.lotrextended.settings.elfwomen.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.elfWomenUseAlexModelStyle), (button, pd) -> LOTRConfig.CLIENT.elfWomenUseAlexModelStyle.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(model.addButton("gui.lotrextended.settings.hobbitwomen.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.hobbitWomenUseAlexModelStyle), (button, pd) -> LOTRConfig.CLIENT.hobbitWomenUseAlexModelStyle.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(model.addButton("gui.lotrextended.settings.manishwomen.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.mannishWomenUseAlexModelStyle), (button, pd) -> LOTRConfig.CLIENT.mannishWomenUseAlexModelStyle.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(model.addButton("gui.lotrextended.settings.orcwomen.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.orcWomenUseAlexModelStyle), (button, pd) -> LOTRConfig.CLIENT.orcWomenUseAlexModelStyle.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		dolchestplatewings = addButton(model.addSlider("gui.lotrextended.settings.dolchestplatewings.prefix", "gui.lotrextended.settings.dolchestplatewings.suffix", 
			0D, 24D, LOTRConfig.CLIENT.dolAmrothChestplateWings.get(), false, true , button -> {}, slider -> {
				if(!slider.dragging) {
					LOTRConfig.CLIENT.dolAmrothChestplateWings.setAndSave(slider.getValueInt());
				}
			}));
		catagories.add(model);


		SettingsCatagory sound = new SettingsCatagory(this, 6, x, y, xTabs);
		addButton(sound.addCatagoryButton("gui.lotrextended.settings.soundsettings"));
		addButton(sound.addButton("gui.lotrextended.settings.newrainsounds.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.newRainSounds), (button, pd) -> LOTRConfig.CLIENT.newRainSounds.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(sound.addButton("gui.lotrextended.settings.newthundersounds.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.newThunderSounds), (button, pd) -> LOTRConfig.CLIENT.newThunderSounds.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(sound.addButton("gui.lotrextended.settings.windambience.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.windAmbience), (button, pd) -> LOTRConfig.CLIENT.windAmbience.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		catagories.add(sound);

		SettingsCatagory misc = new SettingsCatagory(this, 7, x, y, xTabs);
		addButton(misc.addCatagoryButton("gui.lotrextended.settings.miscellaneoussettings"));
		addButton(misc.addButton("gui.lotrextended.settings.lotrextendedversioncheck.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enableClientVersionCheck), (button, pd) -> ExtendedClientConfig.enableClientVersionCheck.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(misc.addButton("gui.lotrextended.settings.jeilotrversioncheck.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.disableJEILOTRversionCheck), (button, pd) -> ExtendedClientConfig.disableJEILOTRversionCheck.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		catagories.add(misc);

		SettingsCatagory dev = new SettingsCatagory(this, 8, x, y, xTabs) {
			@Override
			public boolean isDev() {
				return true;
			}
		};
		addButton(dev.addCatagoryButton("gui.lotrextended.settings.devsettings"));
		addButton(dev.addButton("gui.lotrextended.settings.devtoggle.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enableDevMode), (button, pd) -> {
			if(ExtendedServerConfig.enableDevMode.get()) {
				button.setMessage(new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting.forced"));
				return null; // Return null so we can catch not updating the title
			}else {
				return ExtendedClientConfig.enableDevMode.get();
			}
		}, "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(dev.addButton("gui.lotrextended.settings.fsbr.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.forceStructureBlockAirViewing), (button, pd) -> ExtendedClientConfig.forceStructureBlockAirViewing.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));

		structureBlockOutlineViewDistance = addButton(dev.addSlider("gui.lotrextended.settings.structureblockoutlineviewdistance.prefix", "gui.lotrextended.settings.structureblockoutlineviewdistance.suffix", 
			32.0D, MixinConstants.StructureLimit, ExtendedClientConfig.structureBlockOutlineViewDistance.get(), false, true , button -> {}, slider -> {
				if(slider.dragging) {
					MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = slider.getValueInt();
				}
				if(!slider.dragging) {
					setConfigStrBlockRenderDistance(slider.getValueInt());
				}
			}));
		catagories.add(dev);

		for(SettingsCatagory catagory : catagories) {
			catagory.checkVisability();
		}
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		// Render titles
		TextComponent title = new TranslationTextComponent("gui.lotrextended.settings.title");
		this.font.draw(matStack, title, guiLeft+(100-(this.font.width(title)/2)), (guiTop - 10), 16777215);
		for(SettingsCatagory catagories : catagories) {
			catagories.checkVisability();
		}
		for(SettingsCatagory catagories : catagories) {
			catagories.handleUpdates();
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	public static void toggleFF() {
		getPlayerData().getAlignmentData().toggleFriendlyFireEnabledAndSendToServer();
	}
	public static void toggleHUFF() {
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)getPlayerData();
		pd.getExtendedMiscData().toggleHurtHiredUnitsAndSendToServer();
	}
	public static void toggleHUDM() {
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)getPlayerData();
		pd.getExtendedMiscData().toggleHiredUnitDeathMessagesAndSendToServer();
	}
	public static void toggleHCM() {
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)getPlayerData();
		pd.getExtendedMiscData().toggleHighContrastMessagesAndSendToServer();
	}
	public static void toggleCBU() {
		ExtendedLOTRPlayerData pd = (ExtendedLOTRPlayerData)getPlayerData();
		pd.getExtendedMiscData().toggleCombatBetweenUnitsAndSendToServer();
	}
	//	public static void toggleA() {
	//		Minecraft mc = Minecraft.getInstance();
	//		mc.gui.getChat().addMessage(new StringTextComponent("Not implemented"));
	//	}
	public static void toggleML() {
		getPlayerData().getMiscData().toggleShowMapLocationAndSendToServer();
	}
	//	public static void toggleCK() {
	//		Minecraft mc = Minecraft.getInstance();
	//		mc.gui.getChat().addMessage(new StringTextComponent("Conquest haven't been added yet"));
	//	}
	public static void toggleFRO() {
		LOTRPlayerData pd = getPlayerData();
		RankGender g = pd.getMiscData().getPreferredRankGender();
		if(g==RankGender.MASCULINE) {
			pd.getMiscData().setPreferredRankGenderAndSendToServer(RankGender.FEMININE);
		}
		else {
			pd.getMiscData().setPreferredRankGenderAndSendToServer(RankGender.MASCULINE);
		}
	}

	public static LOTRPlayerData getPlayerData() {
		Minecraft mc = Minecraft.getInstance();
		return LOTRLevelData.clientInstance().getData(mc.player);
	}
	public static void toggleForgeConfigValue(ForgeConfigSpec.BooleanValue value) {
		value.set(!value.get());
	}

	public static void toggleLotrConfigValue(LOTRConfig.BoolHolder value) {
		value.setAndSave(!value.get());
	}

	public static void setConfigStrBlockRenderDistance(int value) {
		ExtendedClientConfig.structureBlockOutlineViewDistance.set(value);
		MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = value;
	}

	public static class SettingsCatagory {
		private ExtendedOptionsScreen screen;
		private final int index;
		private final int placementXOffset;
		private final int placementYOffset;
		private final int catagoryXOffset;
		private int offsetAmount=0;
		private final List<Widget> widgets = new ArrayList<>();
		private Widget catagoryButton;

		public SettingsCatagory(ExtendedOptionsScreen pScreen, int pIndex, int xStart, int yStart, int pCatagoryXOffset) {
			this.screen = pScreen;
			this.index = pIndex;
			this.placementXOffset = xStart;
			this.placementYOffset = yStart;
			this.catagoryXOffset = pCatagoryXOffset;
		}

		public Widget addCatagoryButton(String pMessage) {
			if(catagoryButton!=null) {
				throw new IllegalStateException("Can't register more than one category button for index: "+index);
			}
			catagoryButton = new ExtendedColorChangingButton(catagoryXOffset, placementYOffset + 24 * index, 120, 20, new TranslationTextComponent(pMessage), 
				button -> {
					screen.option=index;
				}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return screen.option==index;
				}
			};
			return catagoryButton;
		}
		public Widget addButton(String pMessage, IPressable pOnPress, IOnTick pOnTick, String enabled, String disabled) {
			ExtendedTogglableTitleButton button = new ExtendedTogglableTitleButton(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, new TranslationTextComponent(pMessage), 
				pOnPress, pOnTick, enabled, disabled);
			widgets.add(button);
			offsetAmount+=1;
			return button;
		}

		public Slider addSlider(String prefix, String suffix, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, IPressable handler, @Nullable ISlider par) {
			Slider slider = new Slider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, new TranslationTextComponent(prefix), new TranslationTextComponent(suffix), 
				minVal, maxVal, currentVal, showDec, drawStr , handler, par);
			widgets.add(slider);
			offsetAmount+=1;
			return slider;
		}

		public void clean() {
			screen=null;
			catagoryButton=null;
			widgets.clear();
		}

		public void handleUpdates() {
			if(screen.option==index) {
				LOTRPlayerData pd = getPlayerData();
				for(Widget widget : widgets) {
					if(widget instanceof ExtendedTogglableTitleButton) {
						((ExtendedTogglableTitleButton)widget).onTick(pd);
					}
					widget.visible = true;
				}
			}
		}
		public void checkVisability(){
			if(isDev()) {
				if(ExtendedClientConfig.enableDevMode.get() || ExtendedServerConfig.enableDevMode.get()) {
					catagoryButton.visible = true;
				}else {
					// Enable setting one up on disable
					if(catagoryButton.visible && screen.option == index) {
						screen.option = index-1;
					}
					catagoryButton.visible = false;
				}
			}
			for(Widget widget : widgets) {
				widget.visible = screen.option==index; // Hide/Show widget if this category is selected
			}
		}

		public boolean isDev() {
			return false;
		}
	}
}
