package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.gui.widget.ExtendedSlider;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedColorChangingButton;
import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTogglableTitleButton;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCServerSettingsPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCServerSettingsPacket.ExtendedSettingToggles;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.client.gui.menu.ExtendedAbstractOptionsScreen;
import lotr.client.gui.widget.button.LeftRightButton;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

public class ExtendedServerOptionsScreen extends ExtendedBasicAbstractScreen {
	public static int option = 0;
	public List<SettingsCatagory> catagories = new ArrayList<>();

	public ExtendedServerOptionsScreen() {
		super(new StringTextComponent("Server Options"), 200, 256);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		ExtendedAbstractOptionsScreen.lastSettingScreen=this.getClass();
		this.addButton(new LeftRightButton(Math.max(0, this.leftPos - 140), this.topPos + (this.imageHeight + 20) / 4, 100, 20, 
				true, new TranslationTextComponent("gui.lotr.menu.return"), (b) -> {
					this.minecraft.setScreen(new MiddleEarthMasterMenuScreen());
				}));
		this.addButton(new Button(Math.max(0, this.leftPos - 140), this.topPos + 20 + (this.imageHeight + 20) / 4, 100, 20, 
				new TranslationTextComponent("gui.lotrextended.settings.button"), 
				button -> {
					this.minecraft.setScreen(new ExtendedClientOptionsScreen());
				}) {
		});
		this.addButton(new ExtendedColorChangingButton(Math.max(0, this.leftPos - 140), this.topPos + 40 + (this.imageHeight + 20) / 4, 100, 20, 
				new TranslationTextComponent("gui.lotrextended.serversettings.button"), 
				button -> {
					this.minecraft.setScreen(new ExtendedServerOptionsScreen());
				}, 0.13f, 0.92f, 0.71f) {
			@Override
			public boolean setColor(){
				return true;
			}
		});
		int x=leftPos+90;
		int xTabs=leftPos-35;
		int y=topPos+40;

		for(SettingsCatagory catagories : catagories) {
			catagories.clean();
		}
		catagories.clear();

		SettingsCatagory gameplay = new SettingsCatagory(this, 0, x, y, xTabs);
		addButton(gameplay.addCatagoryButton("gui.lotrextended.serversettings.category.gameplay"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.bannerprotection.setting", 
				ExtendedServerConfig.enableBannerProtection, ExtendedSettingToggles.BANNER_PROTECTION, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableBannerProtection"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.fellowshipcreation.setting", 
				ExtendedServerConfig.enableFellowshipCreation, ExtendedSettingToggles.FELLOWSHIP_CREATION, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableFellowshipCreation"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.renewedwaypointcreate.setting", 
				ExtendedServerConfig.enableRenewedWaypointCreation, ExtendedSettingToggles.RENEWED_WAYPOINT_CREATE, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableRenewedWaypointCreation"));
		addButton(gameplay.addButtonDisabled("gui.lotrextended.serversettings.legacywaypointcreate.setting", 
				ExtendedServerConfig.enableLegacyWaypointCreation, ExtendedSettingToggles.LEGACY_WAYPOINT_CREATE, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableLegacyWaypointCreation"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.defaultwaypointteleportation.setting", 
				ExtendedServerConfig.enableDefaultWaypointTeleportation, ExtendedSettingToggles.DEFAULT_WAYPOINT_TELEPORT, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableDefaultWaypointTeleportation"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.customwaypointteleportation.setting", 
				ExtendedServerConfig.enableCustomWaypointTeleportation, ExtendedSettingToggles.CUSTOM_WAYPOINT_TELEPORT, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableCustomWaypointTeleportation"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.feastmode.setting", 
				ExtendedServerConfig.enableLegacyFeastMode, ExtendedSettingToggles.FEAST_MODE, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableLegacyFeastMode"));
		addButton(gameplay.addButton("gui.lotrextended.serversettings.decreasedhunger.setting", 
				ExtendedServerConfig.enableHungerDecrease, ExtendedSettingToggles.DECREASED_HUNGER, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableHungerDecrease"));
		catagories.add(gameplay);

		SettingsCatagory entity = new SettingsCatagory(this, 1, x, y, xTabs);
		addButton(entity.addCatagoryButton("gui.lotrextended.serversettings.category.entity"));
		addButton(entity.addButton("gui.lotrextended.serversettings.banditspawning.setting", 
				ExtendedServerConfig.enableBanditEncounters, ExtendedSettingToggles.BANDIT_ENCOUNTERS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableBanditEncounters"));
		addButton(entity.addButton("gui.lotrextended.serversettings.tolkienspawning.setting", 
				ExtendedServerConfig.enableTolkienSpawning, ExtendedSettingToggles.TOLKIEN_ENCOUNTER, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableTolkienSpawning"));
		addButton(entity.addButton("gui.lotrextended.serversettings.wanderingtraderspawning.setting", 
				ExtendedServerConfig.enableWanderingTraderSpawns, ExtendedSettingToggles.WANDERING_TRADERS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableWanderingTraderSpawns"));
		addButton(entity.addSliderD("gui.lotrextended.serversettings.globalspawndamping.setting", "npcGlobalSpawnDampingCoefficient", 0D, 1D, 0.05F, 
				ExtendedServerConfig.npcGlobalSpawnDampingCoefficient, ExtendedSettingToggles.GOBAL_NPC_SPAWN_DAMPING));
		addButton(entity.addSliderI("gui.lotrextended.serversettings.barrowwightspawnchance.setting", "barrowWightChestFirstSpawnChance", 0, 100, 1, 
				ExtendedServerConfig.barrowWightChestFirstSpawnChance, ExtendedSettingToggles.BARROW_WIGHT_SPAWN_CHANCE));
		addButton(entity.addButton("gui.lotrextended.serversettings.passivecaptains.setting", 
				ExtendedServerConfig.enableLegacyCaptainPassiveBehavior, ExtendedSettingToggles.AGGRESSIVE_CAPTAINS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableLegacyCaptainPassiveBehavior"));
		addButton(entity.addButton("gui.lotrextended.serversettings.passivetraders.setting", 
				ExtendedServerConfig.enableLegacyTraderPassiveBehavior, ExtendedSettingToggles.AGGRESSIVE_TRADERS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableLegacyTraderPassiveBehavior"));
		catagories.add(entity);
		
		SettingsCatagory block = new SettingsCatagory(this, 2, x, y, xTabs);
		addButton(block.addCatagoryButton("gui.lotrextended.serversettings.category.block"));
		addButton(block.addButton("gui.lotrextended.serversettings.barsconnecttobranches.setting", 
				ExtendedServerConfig.barsConnectToBranches, ExtendedSettingToggles.BARS_CONNECT_TO_BRANCHES, 
				"gui.lotrextended.base.true", "gui.lotrextended.base.false", "barsConnectToBranches"));
		addButton(block.addButton("gui.lotrextended.serversettings.barsconnecttoslabs.setting", 
				ExtendedServerConfig.barsConnectToSlabSides, ExtendedSettingToggles.BARS_CONNECT_TO_SLAB_SIDES, 
				"gui.lotrextended.base.true", "gui.lotrextended.base.false", "barsConnectToSlabSides"));
		addButton(block.addButton("gui.lotrextended.serversettings.metaldoorsrequirepower.setting", 
				ExtendedServerConfig.makeMetalDoorsRequirePower, ExtendedSettingToggles.METAL_DOORS_REQUIRE_POWER, 
				"gui.lotrextended.base.true", "gui.lotrextended.base.false", "makeMetalDoorsRequirePower"));
		addButton(block.addSliderD("gui.lotrextended.serversettings.dripstonehurtheight.setting", "dripstoneMinimumDamageHeight", 0D, 5D, 0.1F, 
				ExtendedServerConfig.dripstoneMinimumDamageHeight, ExtendedSettingToggles.DRIPSTONE_HURT_HEIGHT));
		addButton(block.addSliderI("gui.lotrextended.serversettings.bellchainlength.setting", "bellRingChainMaxLength", 0, 50, 1, 
				ExtendedServerConfig.bellRingChainMaxLength, ExtendedSettingToggles.BELL_RING_CHAIN_LENGTH));
		catagories.add(block);

		SettingsCatagory chair = new SettingsCatagory(this, 3, x, y, xTabs);
		addButton(chair.addCatagoryButton("gui.lotrextended.serversettings.category.chair"));
		addButton(chair.addButton("gui.lotrextended.serversettings.chairsitting.setting", 
				ExtendedServerConfig.enableChairSlabSitting, ExtendedSettingToggles.CHAIR_SITTING, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableChairSlabSitting"));
		addButton(chair.addButton("gui.lotrextended.serversettings.chairswapping.setting", 
				ExtendedServerConfig.enableChairSwapping, ExtendedSettingToggles.CHAIR_SWAPPING, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableChairSwapping"));
		addButton(chair.addButton("gui.lotrextended.serversettings.chairprotectionoverride.setting", 
				ExtendedServerConfig.enableChairProtectionOverride, ExtendedSettingToggles.CHAIR_PROTECTION_OVERRIDE, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableChairProtectionOverride"));
		catagories.add(chair);

		SettingsCatagory modifiers = new SettingsCatagory(this, 4, x, y, xTabs);
		addButton(modifiers.addCatagoryButton("gui.lotrextended.serversettings.category.modifiers"));
		addButton(modifiers.addButton("gui.lotrextended.serversettings.extendedmodifiers.setting", 
				ExtendedServerConfig.enableExtendedModifiers, ExtendedSettingToggles.EXTENDED_MODIFIERS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableExtendedModifiers"));
		addButton(modifiers.addButton("gui.lotrextended.serversettings.vanillaenchanting.setting", 
				ExtendedServerConfig.enableVanillaEnchanting, ExtendedSettingToggles.VANILLA_ENCHANTING, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableVanillaEnchanting"));
		addButton(modifiers.addButton("gui.lotrextended.serversettings.vanillaxp.setting", 
				ExtendedServerConfig.enableVanillaXp, ExtendedSettingToggles.VANILLA_XP, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableVanillaXp"));
		catagories.add(modifiers);

		SettingsCatagory protection = new SettingsCatagory(this, 5, x, y, xTabs);
		addButton(protection.addCatagoryButton("gui.lotrextended.serversettings.category.protection"));
		addButton(protection.addButton("gui.lotrextended.serversettings.extendedspawnprotection.setting", 
				ExtendedServerConfig.enableExtendedSpawnProtection, ExtendedSettingToggles.EXTENDED_SPAWN_PROTECTION, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableExtendedSpawnProtection"));
		addButton(protection.addSliderI("gui.lotrextended.serversettings.overworldspawnradius.setting", "overworldSpawnProtectionRadius", 0, 500, 1, 
				ExtendedServerConfig.overworldSpawnProtectionRadius, ExtendedSettingToggles.OVERWORLD_SPAWN_RADIUS));
		addButton(protection.addSliderI("gui.lotrextended.serversettings.middleearthspawnradius.setting", "middleEarthSpawnProtectionRadius", 0, 500, 1, 
				ExtendedServerConfig.middleEarthSpawnProtectionRadius, ExtendedSettingToggles.MIDDLE_EARTH_SPAWN_RADIUS));
		catagories.add(protection);

		SettingsCatagory worldgen = new SettingsCatagory(this, 6, x, y, xTabs);
		addButton(worldgen.addCatagoryButton("gui.lotrextended.serversettings.category.worldgen", "gui.lotrextended.serversettings.category.worldgen.tooltip"));
		addButton(worldgen.addButton("gui.lotrextended.serversettings.fixedstructures.setting", 
				ExtendedServerConfig.enableFixedStructures, ExtendedSettingToggles.FIXED_STRUCTURES, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableFixedStructures"));
		addButton(worldgen.addButton("gui.lotrextended.serversettings.fixedstructureimladris.setting", 
				ExtendedServerConfig.enableFixedStructureImladris, ExtendedSettingToggles.FIXED_STRUCTURE_IMLADRIS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableFixedStructureImladris"));
		addButton(worldgen.addButton("gui.lotrextended.serversettings.fixedstructuretowerhills.setting", 
				ExtendedServerConfig.enableFixedStructureTowerHills, ExtendedSettingToggles.FIXED_STRUCTURE_TOWER_HILLS, 
				"gui.lotrextended.base.enabled", "gui.lotrextended.base.disabled", "enableFixedStructureTowerHills"));
		catagories.add(worldgen);

		for(SettingsCatagory catagory : catagories) {
			catagory.checkVisability();
		}
	}

	private static void toggleValue(ForgeConfigSpec.BooleanValue configSpec, ExtendedSettingToggles type) {
		ExtendedPacketHandler.sendToServer(new ExtendedCServerSettingsPacket(type, !configSpec.get()));
	}

	private static void setValue(ForgeConfigSpec.DoubleValue configSpec, ExtendedSettingToggles type, double newValue) {
		ExtendedPacketHandler.sendToServer(new ExtendedCServerSettingsPacket(type, newValue));
	}

	private static void setValue(ForgeConfigSpec.IntValue configSpec, ExtendedSettingToggles type, int newValue) {
		ExtendedPacketHandler.sendToServer(new ExtendedCServerSettingsPacket(type, newValue));
	}

	@Override
	public void render(MatrixStack matStack, int mouseX, int mouseY, float tick) {
		renderBackground(matStack);
		// Render titles
		TextComponent title = new TranslationTextComponent("gui.lotrextended.serversettings.title");
		this.font.draw(matStack, title, leftPos+(100-(this.font.width(title)/2)), (topPos+10), 16777215);
		for(SettingsCatagory catagories : catagories) {
			catagories.checkVisability();
			catagories.handleUpdates();
		}
		for(Widget button : this.buttons) {
			if(button.isHovered() && button.visible) {
				button.renderToolTip(matStack, mouseX, mouseY);
			}
		}
		super.render(matStack, mouseX, mouseY, tick);
	}

	public static class SettingsCatagory {
		private ExtendedServerOptionsScreen screen;
		private final int index;
		private final int placementXOffset;
		private final int placementYOffset;
		private final int catagoryXOffset;
		private int offsetAmount=0;
		private final List<ExtendedTogglableTitleButton<ExtendedServerConfig>> bottons = new ArrayList<>();
		private final List<ExtendedSlider> sliders = new ArrayList<>();
		private Widget catagoryButton;

		public SettingsCatagory(ExtendedServerOptionsScreen pScreen, int pIndex, int xStart, int yStart, int pCatagoryXOffset) {
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
						ExtendedServerOptionsScreen.option=index;
					}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return ExtendedServerOptionsScreen.option==index;
				}
			};
			return catagoryButton;
		}
		public Widget addCatagoryButton(String pMessage, String toolTip) {
			if(catagoryButton!=null) {
				throw new IllegalStateException("Can't register more than one category button for index: "+index);
			}
			catagoryButton = new ExtendedColorChangingButton(catagoryXOffset, placementYOffset + 24 * index, 120, 20, new TranslationTextComponent(pMessage), 
					button -> {
						ExtendedServerOptionsScreen.option=index;
					}, (wdget, matrixStack, pMouseX, pMouseY) -> {
						screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(toolTip), 200), pMouseX, pMouseY);
					}, 0.13f, 0.92f, 0.71f) {
				@Override
				public boolean setColor(){
					return ExtendedServerOptionsScreen.option==index;
				}
			};
			return catagoryButton;
		}
		public ExtendedTogglableTitleButton<ExtendedServerConfig> addButton(String stringMessageKey, ForgeConfigSpec.BooleanValue configSpec, ExtendedSettingToggles type, String enabled, String disabled, String configKey) {
			ExtendedTogglableTitleButton<ExtendedServerConfig> button = new ExtendedTogglableTitleButton<ExtendedServerConfig>(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, stringMessageKey, 
					wdget -> toggleValue(configSpec, type), (wdget, matrixStack, pMouseX, pMouseY) -> {
						screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX, pMouseY);
					}, (wdget, tickData) -> configSpec.get(), enabled, disabled);
			bottons.add(button);
			offsetAmount+=1;
			return button;
		}
		public ExtendedTogglableTitleButton<ExtendedServerConfig> addButtonDisabled(String stringMessageKey, ForgeConfigSpec.BooleanValue configSpec, ExtendedSettingToggles type, String enabled, String disabled, String configKey) {
			ExtendedTogglableTitleButton<ExtendedServerConfig> button = new ExtendedTogglableTitleButton<ExtendedServerConfig>(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, stringMessageKey, 
					wdget -> {}, (wdget, matrixStack, pMouseX, pMouseY) -> {
						screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX, pMouseY);
					}, (wdget, tickData) -> configSpec.get(), enabled, disabled).forceDisableButton();
			bottons.add(button);
			offsetAmount+=1;
			return button;
		}
		public ExtendedSlider addSliderD(String localKey, String configKey, double minVal, double maxVal, float steps, ForgeConfigSpec.DoubleValue configSpec, ExtendedSettingToggles type) {
			ExtendedSlider slider = new ExtendedSlider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, localKey, minVal, maxVal, steps, () -> {
				return configSpec.get();
			}, sliderValue -> {
				setValue(configSpec, type, sliderValue);
			});
			sliders.add(slider);
			offsetAmount+=1;
			slider.setToolTip((wdget, matrixStack, pMouseX, pMouseY) -> {
				screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX, pMouseY);
			});
			return slider;
		}
		public ExtendedSlider addSliderI(String localKey, String configKey, int minVal, int maxVal, int steps, ForgeConfigSpec.IntValue configSpec, ExtendedSettingToggles type) {
			ExtendedSlider slider = new ExtendedSlider(placementXOffset, placementYOffset + 24 * offsetAmount, 200, 20, localKey, minVal, maxVal, steps, () -> {
				return (double)configSpec.get();
			}, sliderValue -> {
				setValue(configSpec, type, (int)sliderValue);
			});
			sliders.add(slider);
			offsetAmount+=1;
			slider.setToolTip((wdget, matrixStack, pMouseX, pMouseY) -> {
				screen.renderTooltip(matrixStack, screen.font.split(new TranslationTextComponent(String.format("config.%s.%s", LotrExtendedMod.MODID, configKey)), 200), pMouseX, pMouseY);
			});
			return slider;
		}
		public void clean() {
			screen=null;
			catagoryButton=null;
			bottons.clear();
			sliders.clear();
		}

		public void handleUpdates() {
			if(ExtendedServerOptionsScreen.option==index) {
				for(ExtendedTogglableTitleButton<ExtendedServerConfig> widget : bottons) {
					widget.onTick(null);
				}
			}
		}
		public void checkVisability(){
			for(Widget widget : bottons) {
				widget.visible = ExtendedServerOptionsScreen.option==index; // Hide/Show widget if this category is selected
			}
			for(Widget widget : sliders) {
				widget.visible = ExtendedServerOptionsScreen.option==index; // Hide/Show widget if this category is selected
			}
		}

	}
}
