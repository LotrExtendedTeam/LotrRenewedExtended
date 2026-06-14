package com.github.maximuslotro.lotrrextended.common.config;

import com.github.maximuslotro.lotrextended.mixinhelpers.MixinConstants;
import com.github.maximuslotro.lotrrextended.client.gui.screen.settings.ExtendedClientOptionsScreen;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ExtendedClientConfig extends ExtendedBaseConfigHelper{
	public static final ForgeConfigSpec CLIENT_SPEC = setupConfig(new ForgeConfigSpec.Builder()).build();
	public static ForgeConfigSpec.BooleanValue enableClientDevMode;
	public static ConfigValue<String> lastClientBootVersion;
	public static ForgeConfigSpec.BooleanValue disableJEILOTRversionCheck;
	public static ForgeConfigSpec.BooleanValue enableClientVersionCheck;
	public static ForgeConfigSpec.BooleanValue enableLargeHiredUnitHealthbars;

	public static ForgeConfigSpec.BooleanValue enablePlantParticles;

	public static ForgeConfigSpec.BooleanValue enableQuerkyDuckMode;

	/**
	 * Do not set this value itself, set {@link ExtendedClientOptionsScreen}
	 * to ensure {@link com.github.maximuslotro.lotrextended.mixins.net.minecraft.tileentity.MixinStructureBlockTileEntity} is updated with the non config crashable value, since sliders can change values very quickly.
	 * */
	public static ForgeConfigSpec.IntValue structureBlockOutlineViewDistance;
	public static ForgeConfigSpec.BooleanValue forceStructureBlockAirViewing;

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, CLIENT_SPEC);
	}

	public static void registerDataGen() {}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("General Settings");
		enablePlantParticles = createBooleanValue(builder, "enablePlantParticles", true, false, "Allow for certian plants to spawn particles.");
		disableJEILOTRversionCheck = createBooleanValue(builder, "disableJEILOTRversionCheck", true, false, "Disable JEI-LOTR's broken version check.");
		enableClientVersionCheck = createBooleanValue(builder, "enableClientVersionCheck", true, false, "Enable Extended client version check.");
		enableLargeHiredUnitHealthbars = createBooleanValue(builder, "enableShowHiredUnitHPNumbers", false, false, "Enable large hired unit health bars with HP value");
		builder.pop();

		builder.push("Technical Settings");
		structureBlockOutlineViewDistance = createIntegerValue(builder, "structureBlockOutlineDistance", 96, 32, (int) MixinConstants.StructureLimit, false, "Change the distance at which structure block outlines render.");
		forceStructureBlockAirViewing = createBooleanValue(builder, "forceStructureBlockAirViewing", false, false, "Force Structure Voids to render inside of Structure Block Outline Rendering.");
		builder.pop();

		builder.push("Special settings");
		enableClientDevMode = createBooleanValue(builder, "enableDevModeClient", false, true, "Never enable this unless you are on the Extended Dev Team or debugging the mod!");
		lastClientBootVersion = createStringValue(builder, "lastClientBootVersion", "null", true, "Last version this mod launched with. Don't touch! Value handled by extended.");
		builder.pop();

		builder.push("Model Settings");
		enableQuerkyDuckMode = createBooleanValue(builder, "enableQuerkyDuckMode", false, false, "Enable early extended duck model querkyness.");
		builder.pop();
		return builder;
	}
}
