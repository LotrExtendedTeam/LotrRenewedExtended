package com.github.maximuslotro.lotrrextended.client.gui.screen.settings;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrextended.mixinhelpers.MixinConstants;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider.IGetValue;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider.ISetValue;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton.IOnTick;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.menu.ExtendedAbstractOptionsScreen;
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

public class ExtendedClientOptionsScreen extends AbstractExtendedOptionsScreen {
	public List<SettingsCatagory> catagories = new ArrayList<>();
	private int unlockClientDevCount=0;
	public static int optionClient = 0;

	public ExtendedClientOptionsScreen() {
		super(new StringTextComponent("Client Options"), SettingsPage.CLIENT);
	}

	@Override
	public void onClose() {
		MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = ExtendedClientConfig.structureBlockOutlineViewDistance.get();
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		ExtendedAbstractOptionsScreen.lastSettingScreen=this.getClass();
		int x=leftPos+90;
		int xTabs=leftPos-35;
		int y=topPos+40;

		for(SettingsCatagory catagories : catagories) {
			catagories.clean();
		}
		catagories.clear();

		SettingsCatagory world = new SettingsCatagory(this, 0, x, y, xTabs);
		addButton(world.addCatagoryButton("gui.lotrextended.settings.worldsettings"));
		addButton(world.addButton("gui.lotrextended.settings.friendlyFire.setting", button -> toggleFF(), (button, pd) -> pd.getAlignmentData().isFriendlyFireEnabled(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(world.addButton("gui.lotrextended.settings.friendlyFireUnit.setting", button -> toggleHUFF(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHurtHiredUnits(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(world.addButton("gui.lotrextended.settings.mapLocation.setting", button -> toggleML(), (button, pd) -> pd.getMiscData().getShowMapLocation(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(world.addButton("gui.lotrextended.settings.unit_v_unit.setting", button -> toggleCBU(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		catagories.add(world);


		SettingsCatagory user = new SettingsCatagory(this, 1, x, y, xTabs);
		addButton(user.addCatagoryButton("gui.lotrextended.settings.usersettings"));
		addButton(user.addButton("gui.lotrextended.settings.rankGender.setting", button -> toggleFRO(), (button, pd) -> pd.getMiscData().getPreferredRankGender().equals(RankGender.FEMININE), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(user.addButton("gui.lotrextended.settings.hired_unit_death.setting", button -> toggleHUDM(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().shouldShowHiredUnitDeathMessages(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden"));
		addButton(user.addButton("gui.lotrextended.settings.high_contrast_messages.setting", button -> toggleHCM(), (button, pd) -> ((ExtendedLOTRPlayerData)pd).getExtendedMiscData().doHighContrastMessages(), 
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
		addButton(environment.addSlider("gui.lotrextended.settings.cloudrange.setting", 0D, 1024D, 1F, 
			() -> {
				return (double)LOTRConfig.CLIENT.cloudRange.get();
			}, sliderValue -> {
				LOTRConfig.CLIENT.cloudRange.setAndSave((int)sliderValue);
			}));
		addButton(environment.addButton("gui.lotrextended.settings.sunglare.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.sunGlare), (button, pd) -> LOTRConfig.CLIENT.sunGlare.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButton("gui.lotrextended.settings.northernlights.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.northernLights), (button, pd) -> LOTRConfig.CLIENT.northernLights.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(environment.addButtonNew("gui.lotrextended.settings.plantparticles.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enablePlantParticles), (button, pd) -> ExtendedClientConfig.enablePlantParticles.get(), 
			"gui.lotrextended.base.shown", "gui.lotrextended.base.hidden", "enablePlantParticles"));
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
		addButton(gui.addButtonNew("gui.lotrextended.settings.show_hired_unit_hp_numbers.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enableLargeHiredUnitHealthbars), (button, pd) -> ExtendedClientConfig.enableLargeHiredUnitHealthbars.get(),
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableShowHiredUnitHPNumbers"));
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
		addButton(hud.addSlider("gui.lotrextended.settings.alignmentoffsetx.setting", -3000D, 3000D, 1F, 
			() -> {
				return (double)LOTRConfig.CLIENT.alignmentXOffset.get();
			}, sliderValue -> {
				LOTRConfig.CLIENT.alignmentXOffset.setAndSave((int)sliderValue);
			}));
		addButton(hud.addSlider("gui.lotrextended.settings.alignmentoffsety.setting", 0, 3000, 1F, 
			() -> {
				return (double)LOTRConfig.CLIENT.alignmentYOffset.get();
			}, sliderValue -> {
				LOTRConfig.CLIENT.alignmentYOffset.setAndSave((int)sliderValue);
			}));
		addButton(hud.addButton("gui.lotrextended.settings.immersivespeech.setting", button -> toggleLotrConfigValue(LOTRConfig.CLIENT.immersiveSpeech), (button, pd) -> LOTRConfig.CLIENT.immersiveSpeech.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled"));
		addButton(hud.addSlider("gui.lotrextended.settings.immersivespeechduration.setting", 0D, 60D, 1F, 
			() -> {
				return (double)LOTRConfig.CLIENT.immersiveSpeechDuration.get();
			}, sliderValue -> {
				LOTRConfig.CLIENT.immersiveSpeechDuration.setAndSave((int)sliderValue);
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
		addButton(model.addSlider("gui.lotrextended.settings.dolchestplatewings.setting", 0D, 24D, 1F, 
			() -> {
				return (double)LOTRConfig.CLIENT.dolAmrothChestplateWings.get();
			}, sliderValue -> {
				LOTRConfig.CLIENT.dolAmrothChestplateWings.setAndSave((int)sliderValue);
			}));
		addButton(model.addButtonNew("gui.lotrextended.settings.earlyduckmodel.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enableQuerkyDuckMode), (button, pd) -> ExtendedClientConfig.enableQuerkyDuckMode.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableQuerkyDuckMode"));
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
		addButton(misc.addButtonNew("gui.lotrextended.settings.lotrextendedversioncheck.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.enableClientVersionCheck), (button, pd) -> ExtendedClientConfig.enableClientVersionCheck.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableClientVersionCheck"));
		addButton(misc.addButtonNew("gui.lotrextended.settings.jeilotrversioncheck.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.disableJEILOTRversionCheck), (button, pd) -> ExtendedClientConfig.disableJEILOTRversionCheck.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "disableJEILOTRversionCheck"));
		catagories.add(misc);

		SettingsCatagory dev = new SettingsCatagory(this, 8, x, y, xTabs) {
			@Override
			public boolean isDev() {
				return true;
			}
		};
		addButton(dev.addCatagoryButton("gui.lotrextended.settings.devsettings"));
		addButton(dev.addButtonNew("gui.lotrextended.settings.devtoggle.setting", button -> {
			if(ExtendedServerConfig.enableDevMode.get()) {
				setForgeConfigValue(ExtendedClientConfig.enableClientDevMode, true);
			}else{
				toggleForgeConfigValue(ExtendedClientConfig.enableClientDevMode);
			}
		}, (button, pd) -> {
			if(ExtendedServerConfig.enableDevMode.get()) {
				button.setMessage(new TranslationTextComponent("gui.lotrextended.settings.devtoggle.setting.forced"));
				return null; // Return null so we can catch not updating the title
			}else {
				return ExtendedClientConfig.enableClientDevMode.get();
			}
		}, "gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableDevModeClient"));
		addButton(dev.addButtonNew("gui.lotrextended.settings.fsbr.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.forceStructureBlockAirViewing), (button, pd) -> ExtendedClientConfig.forceStructureBlockAirViewing.get(), 
			"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "forceStructureBlockAirViewing"));
		addButton(dev.addSliderNew("gui.lotrextended.settings.structureblockoutlineviewdistance.setting", 32.0D, MixinConstants.StructureLimit, 1F, 
			() -> {
				return (double)ExtendedClientConfig.structureBlockOutlineViewDistance.get();
			}, sliderValue -> {
				ExtendedClientConfig.structureBlockOutlineViewDistance.set((int)sliderValue);
				MixinConstants.changeableNonConfigCrashableStrBlockRenderDistance = (int)sliderValue;
			}, "structureBlockOutlineDistance"));
		addButton(dev.addButtonNew("gui.lotrextended.settings.spawneroutline.setting", button -> toggleForgeConfigValue(ExtendedClientConfig.showSpawnerOutlines), (button, pd) -> ExtendedClientConfig.showSpawnerOutlines.get(), 
			"gui.lotrextended.base.show", "gui.lotrextended.base.hide", "showSpawnerOutlines"));
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
		this.font.draw(matStack, title, leftPos+(100-(this.font.width(title)/2)), (topPos+10), 16777215);
		for(SettingsCatagory catagories : catagories) {
			catagories.checkVisability();
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
	public static void setForgeConfigValue(ForgeConfigSpec.BooleanValue value, boolean valueToSet) {
		value.set(valueToSet);
	}

	public static void toggleLotrConfigValue(LOTRConfig.BoolHolder value) {
		value.setAndSave(!value.get());
	}

	public static class SettingsCatagory {
		private ExtendedClientOptionsScreen screen;
		private final int index;
		private final int placementXOffset;
		private final int placementYOffset;
		private final int catagoryXOffset;
		private int offsetAmount=0;
		private final List<ExtendedTogglableTitleButton<LOTRPlayerData>> bottons = new ArrayList<>();
		private final List<ExtendedSlider> sliders = new ArrayList<>();
		private Widget catagoryButton;

		public SettingsCatagory(ExtendedClientOptionsScreen pScreen, int pIndex, int xStart, int yStart, int pCatagoryXOffset) {
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
					ExtendedClientOptionsScreen.optionClient=index;
					if(screen.unlockClientDevCount==0&&index==0) {
						screen.unlockClientDevCount=1;
					}else if(screen.unlockClientDevCount==1&&index==2) {
						screen.unlockClientDevCount=2;
					}else if(screen.unlockClientDevCount==2&&index==4) {
						screen.unlockClientDevCount=3;
					}else if(screen.unlockClientDevCount==3&&index==6) {
						screen.unlockClientDevCount=4;
					}else if(screen.unlockClientDevCount>=4&&index==7) {
						screen.unlockClientDevCount+=1;
						if(screen.unlockClientDevCount>=9) {
							ExtendedClientConfig.enableClientDevMode.set(true);
							screen.unlockClientDevCount=0;
						}
					}else {
						screen.unlockClientDevCount=0;
					}
				}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return ExtendedClientOptionsScreen.optionClient==index;
				}
			};
			return catagoryButton;
		}
		public Widget addButtonNew(String stringMessageKey, IPressable pOnPress, IOnTick<LOTRPlayerData> pOnTick, String enabled, String disabled, String configKey) {
			ExtendedTogglableTitleButton<LOTRPlayerData> button = new ExtendedTogglableTitleButton<>(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, new TranslationTextComponent(stringMessageKey), pOnPress, 
				(wdget, matrixStack, pMouseX, pMouseY) -> {
					screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX, pMouseY);
				}, pOnTick, enabled, disabled);
			bottons.add(button);
			offsetAmount+=1;
			return button;
		}
		public Widget addButton(String stringMessageKey, IPressable pOnPress, IOnTick<LOTRPlayerData> pOnTick, String enabled, String disabled) {
			ExtendedTogglableTitleButton<LOTRPlayerData> button = new ExtendedTogglableTitleButton<>(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, new TranslationTextComponent(stringMessageKey), pOnPress, pOnTick, enabled, disabled);
			bottons.add(button);
			offsetAmount+=1;
			return button;
		}
		public ExtendedSlider addSliderNew(String localKey, double minVal, double maxVal, float steps, IGetValue valueGetter, ISetValue valueSetter, String configKey) {
			ExtendedSlider slider = new ExtendedSlider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, localKey, minVal, maxVal, steps, valueGetter, valueSetter);
			slider.setToolTip((wdget, matrixStack, pMouseX1, pMouseY1) -> {
				screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX1, pMouseY1);
			});
			sliders.add(slider);
			offsetAmount+=1;
			return slider;
		}
		public ExtendedSlider addSlider(String localKey, double minVal, double maxVal, float steps, IGetValue valueGetter, ISetValue valueSetter) {
			ExtendedSlider slider = new ExtendedSlider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, localKey, minVal, maxVal, steps, valueGetter, valueSetter);
			sliders.add(slider);
			offsetAmount+=1;
			return slider;
		}

		public void clean() {
			screen=null;
			catagoryButton=null;
			bottons.clear();
			sliders.clear();
		}

		public void handleUpdates() {
			if(ExtendedClientOptionsScreen.optionClient==index) {
				LOTRPlayerData pd = getPlayerData();
				for(ExtendedTogglableTitleButton<LOTRPlayerData> widget : bottons) {
					widget.onTick(pd);
				}
			}
		}
		public void checkVisability(){
			if(isDev()) {
				if(ExtendedClientConfig.isDevMode()) {
					catagoryButton.visible = true;
				}else {
					// Enable setting one up on disable
					if(catagoryButton.visible && ExtendedClientOptionsScreen.optionClient == index) {
						ExtendedClientOptionsScreen.optionClient = index-1;
					}
					catagoryButton.visible = false;
				}
			}
			for(Widget widget : bottons) {
				widget.visible = ExtendedClientOptionsScreen.optionClient==index; // Hide/Show widget if this category is selected
			}
			for(Widget widget : sliders) {
				widget.visible = ExtendedClientOptionsScreen.optionClient==index; // Hide/Show widget if this category is selected
			}
		}

		public boolean isDev() {
			return false;
		}
	}
}
