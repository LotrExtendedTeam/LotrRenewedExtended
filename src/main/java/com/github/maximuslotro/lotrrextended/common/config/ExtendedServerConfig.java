package com.github.maximuslotro.lotrrextended.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ExtendedServerConfig extends ExtendedBaseConfigHelper{
	public static final ForgeConfigSpec SERVER_SPEC = setupConfig(new ForgeConfigSpec.Builder()).build();

	public static ForgeConfigSpec.BooleanValue enableFellowshipCreation;
	public static ForgeConfigSpec.BooleanValue enableRenewedWaypointCreation;
	public static ForgeConfigSpec.BooleanValue enableLegacyWaypointCreation;
	public static ForgeConfigSpec.BooleanValue makeMetalDoorsRequirePower;

	public static ForgeConfigSpec.BooleanValue enableFishingEnchantedItems;
	public static ForgeConfigSpec.BooleanValue enableEnchantedLootDrops;

	public static ForgeConfigSpec.BooleanValue enableDevMode;
	public static ForgeConfigSpec.BooleanValue enableExtendedLogging;

	public static ForgeConfigSpec.BooleanValue enableBannerProtection;
	public static ForgeConfigSpec.BooleanValue enableChairSlabSitting;
	public static ForgeConfigSpec.BooleanValue enableChairSwapping;
	public static ForgeConfigSpec.BooleanValue enableChairProtectionOverride;
	
	public static void register() {
		ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
	}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Gameplay settings");
		enableFellowshipCreation = createBooleanValue(builder, "enableFellowshipCreation", true, false, "Enable fellowship creation");
		enableRenewedWaypointCreation = createBooleanValue(builder, "enableRenewedWaypointCreation", true, false, "Enable renewed based waypoint creation");
		enableLegacyWaypointCreation = createBooleanValue(builder, "enableLegacyWaypointCreation", false, false, "Enable legacy based waypoint creation");
		makeMetalDoorsRequirePower = createBooleanValue(builder, "makeMetalDoorsRequirePower", false, true, "Make Extended's metal doors and trapdoors require power to open");
		enableBannerProtection = createBooleanValue(builder, "enableBannerProtection", true, false, "Enable banner protection");
		builder.pop();

		builder.push("Chair settings");
		enableChairSlabSitting = createBooleanValue(builder, "enableChairSlabSitting", true, false, "Allow players to sit on slabs or stairs (overrides all below)");
		enableChairSwapping = createBooleanValue(builder, "enableChairSwapping", true, false, "Allow players to switch seats without standing");
		enableChairProtectionOverride = createBooleanValue(builder, "enableChairProtectionOverride", true, false, "Allow players sit on seats in banner-protected areas (global setting overriding banner spicific settings)");
		builder.pop();

		builder.push("Vanilla Patches");
		enableEnchantedLootDrops = createBooleanValue(builder, "enableEnchantedLootDrops", true, false, "Enable any enchated loot drops in Middle Earth (overall setting)");
		enableFishingEnchantedItems = createBooleanValue(builder, "enableFishingEnchantedItems", false, false, "Enable fishing enchanted items in Middle Earth");
		builder.pop();

		builder.push("Special settings");
		enableDevMode = createBooleanValue(builder,"enableDevMode", false, true, "Never enable this unless you are on the Extended Dev Team or developing the mod!");
		enableExtendedLogging = createBooleanValue(builder,"enableExtendedLogging", false, true, "Toggle to show additional log information if stuff is not working properly");
		builder.pop();
		return builder;
	}
}
