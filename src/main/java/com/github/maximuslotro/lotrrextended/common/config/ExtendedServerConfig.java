package com.github.maximuslotro.lotrrextended.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ExtendedServerConfig extends ExtendedBaseConfigHelper{
	public static final ForgeConfigSpec SERVER_SPEC = setupConfig(new ForgeConfigSpec.Builder()).build();

	// Gameplay settings
	public static ForgeConfigSpec.BooleanValue enableFellowshipCreation;
	public static ForgeConfigSpec.BooleanValue enableRenewedWaypointCreation;
	public static ForgeConfigSpec.BooleanValue enableLegacyWaypointCreation;
	public static ForgeConfigSpec.BooleanValue makeMetalDoorsRequirePower;
	public static ForgeConfigSpec.BooleanValue enableBannerProtection;
	public static ForgeConfigSpec.IntValue barrowWightChestFirstSpawnChance;
	public static ForgeConfigSpec.DoubleValue npcGlobalSpawnDampingCoefficient;
	public static ForgeConfigSpec.DoubleValue dripstoneMinimumDamageHeight;
	public static ForgeConfigSpec.BooleanValue enableDefaultWaypointTeleportation;
	public static ForgeConfigSpec.BooleanValue enableCustomWaypointTeleportation;
	public static ForgeConfigSpec.BooleanValue enableLegacyCaptainPassiveBehavior;
	public static ForgeConfigSpec.BooleanValue enableLegacyTraderPassiveBehavior;

	// Chair Settings
	public static ForgeConfigSpec.BooleanValue enableChairSlabSitting;
	public static ForgeConfigSpec.BooleanValue enableChairSwapping;
	public static ForgeConfigSpec.BooleanValue enableChairProtectionOverride;

	// Vanilla patches
	public static ForgeConfigSpec.BooleanValue enableFishingEnchantedItems;
	public static ForgeConfigSpec.BooleanValue enableEnchantedLootDrops;

	// Special Settings
	public static ForgeConfigSpec.BooleanValue enableDevMode;
	public static ForgeConfigSpec.BooleanValue enableExtendedLogging;
	public static ForgeConfigSpec.BooleanValue enableSpecialLogging;
	public static ForgeConfigSpec.BooleanValue enableStaffServer;

	// Server Enhancements
	public static ForgeConfigSpec.BooleanValue enableExtendedSpawnProtection;
	public static ForgeConfigSpec.IntValue overworldSpawnProtectionRadius;
	public static ForgeConfigSpec.IntValue middleEarthSpawnProtectionRadius;

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
	}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Gameplay settings");
		enableFellowshipCreation = createBooleanValue(builder, "enableFellowshipCreation", true, false, "Enable fellowship creation");
		enableRenewedWaypointCreation = createBooleanValue(builder, "enableRenewedWaypointCreation", true, false, "Enable renewed based waypoint creation");
		enableLegacyWaypointCreation = createBooleanValue(builder, "enableLegacyWaypointCreation", false, false, "Enable legacy based waypoint creation (Not added yet)");
		makeMetalDoorsRequirePower = createBooleanValue(builder, "makeMetalDoorsRequirePower", false, true, "Make Extended's metal doors and trapdoors require power to open");
		enableBannerProtection = createBooleanValue(builder, "enableBannerProtection", true, false, "Enable banner protection");
		barrowWightChestFirstSpawnChance = createIntegerValue(builder, "barrowWightChestFirstSpawnChance", 50, 0, 100, false, "Should stone chests in the barrow downs spawn wight on first open. 0=only when cursed, 100=spawns on every first open like in legacy");
		npcGlobalSpawnDampingCoefficient = createDoubleValue(builder, "npcGlobalSpawnDampingCoefficient", 1.0D, 0.0D, 1.0D, false, "Reduce the global npc spawning rate, the lower the value, the less spawns. This is a linear setting, (1.0 is the default renewed spawn rate)");
		dripstoneMinimumDamageHeight = createDoubleValue(builder, "dripstoneMinimumDamageHeight", 1.0D, 0.0D, 5.0D, false, "Set the height at which dripstone hurts entities when interacting, (0.0 is the default value in renewed)");
		enableDefaultWaypointTeleportation = createBooleanValue(builder, "enableDefaultWaypointTeleportation", true, false, "Enable Teleportation To Default Waypoints");
		enableCustomWaypointTeleportation = createBooleanValue(builder, "enableCustomWaypointTeleportation", true, false, "Enable Teleportation To Custom Waypoints");
		enableLegacyCaptainPassiveBehavior = createBooleanValue(builder, "enableLegacyCaptainPassiveBehavior", true, true, "Make captains passive until attacked, like in legacy");
		enableLegacyTraderPassiveBehavior = createBooleanValue(builder, "enableLegacyTraderPassiveBehavior", true, true, "Make traders passive until attacked, like in legacy");
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
		enableSpecialLogging = createBooleanValue(builder,"enableSpecialLogging", false, true, "Toggles additional logged information, changes by update, special things");
		enableStaffServer = createBooleanValue(builder,"enableStaffServer", false, true, "");
		builder.pop();

		builder.push("Server Enhancements");
		enableExtendedSpawnProtection = createBooleanValue(builder,"enableExtendedSpawnProtection", false, true, "Enable extended spawn protection for servers");
		overworldSpawnProtectionRadius = createIntegerValue(builder, "overworldSpawnProtectionRadius", 16, 0, 500, true, "The radius for Overworld spawn server protection (allows larger area than vanilla protection)");
		middleEarthSpawnProtectionRadius = createIntegerValue(builder, "middleEarthSpawnProtectionRadius", 16, 0, 500, true, "The radius for Middle Earth spawn server protection");
		builder.pop();
		return builder;
	}
}
