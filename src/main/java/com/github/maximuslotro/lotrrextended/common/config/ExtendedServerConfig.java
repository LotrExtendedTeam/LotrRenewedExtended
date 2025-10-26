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
	public static ForgeConfigSpec.BooleanValue enableBannerProtection;
	public static ForgeConfigSpec.DoubleValue npcGlobalSpawnDampingCoefficient;
	public static ForgeConfigSpec.BooleanValue enableDefaultWaypointTeleportation;
	public static ForgeConfigSpec.BooleanValue enableCustomWaypointTeleportation;
	public static ForgeConfigSpec.BooleanValue enableLegacyFeastMode;

	// Entity Settings
	public static ForgeConfigSpec.IntValue barrowWightChestFirstSpawnChance;
	public static ForgeConfigSpec.BooleanValue enableLegacyCaptainPassiveBehavior;
	public static ForgeConfigSpec.BooleanValue enableLegacyTraderPassiveBehavior;

	// Block Settings
	public static ForgeConfigSpec.BooleanValue barsConnectToBranches;
	public static ForgeConfigSpec.BooleanValue barsConnectToSlabSides;
	public static ForgeConfigSpec.BooleanValue makeMetalDoorsRequirePower;
	public static ForgeConfigSpec.DoubleValue dripstoneMinimumDamageHeight;

	// Chair Settings
	public static ForgeConfigSpec.BooleanValue enableChairSlabSitting;
	public static ForgeConfigSpec.BooleanValue enableChairSwapping;
	public static ForgeConfigSpec.BooleanValue enableChairProtectionOverride;

	// Vanilla patches
	public static ForgeConfigSpec.BooleanValue enableVanillaEnchanting;
	public static ForgeConfigSpec.BooleanValue enableVanillaXp;
	public static ForgeConfigSpec.BooleanValue enableExtendedModifiers;

	// Special Settings
	public static ForgeConfigSpec.BooleanValue enableDevMode;
	public static ForgeConfigSpec.BooleanValue enableExtendedLogging;
	public static ForgeConfigSpec.BooleanValue enableSpecialLogging;
	public static ForgeConfigSpec.BooleanValue enableStaffServer;

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
	}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Gameplay settings");
		enableFellowshipCreation = createBooleanValue(builder, "enableFellowshipCreation", true, false, "Enable fellowship creation");
		enableRenewedWaypointCreation = createBooleanValue(builder, "enableRenewedWaypointCreation", true, false, "Enable renewed based waypoint creation");
		enableLegacyWaypointCreation = createBooleanValue(builder, "enableLegacyWaypointCreation", false, false, "Enable legacy based waypoint creation (Not added yet)");
		enableBannerProtection = createBooleanValue(builder, "enableBannerProtection", true, false, "Enable banner protection");
		npcGlobalSpawnDampingCoefficient = createDoubleValue(builder, "npcGlobalSpawnDampingCoefficient", 1.0D, 0.0D, 1.0D, false, "Reduce the global npc spawning rate, the lower the value, the less spawns. This is a linear setting, (1.0 is the default renewed spawn rate)");
		enableDefaultWaypointTeleportation = createBooleanValue(builder, "enableDefaultWaypointTeleportation", true, false, "Enable Teleportation To Default Waypoints");
		enableCustomWaypointTeleportation = createBooleanValue(builder, "enableCustomWaypointTeleportation", true, false, "Enable Teleportation To Custom Waypoints");
		enableLegacyFeastMode = createBooleanValue(builder, "enableLegacyFeastMode", true, true, "Enable the eating of food even if player is not hungry, like in legacy");
		builder.pop();

		builder.push("Entity settings");
		barrowWightChestFirstSpawnChance = createIntegerValue(builder, "barrowWightChestFirstSpawnChance", 50, 0, 100, false, "Should stone chests in the barrow downs spawn wight on first open. 0=only when cursed, 100=spawns on every first open like in legacy");
		enableLegacyCaptainPassiveBehavior = createBooleanValue(builder, "enableLegacyCaptainPassiveBehavior", true, true, "Make captains passive until attacked, like in legacy");
		enableLegacyTraderPassiveBehavior = createBooleanValue(builder, "enableLegacyTraderPassiveBehavior", true, true, "Make traders passive until attacked, like in legacy");
		builder.pop();

		builder.push("Block settings");
		barsConnectToBranches = createBooleanValue(builder, "barsConnectToBranches", true, true, "Make bars and panes connect to branch blocks");
		barsConnectToSlabSides = createBooleanValue(builder, "barsConnectToSlabSides", true, true, "Make bars and panes connect to half sides of vertical slabs");
		makeMetalDoorsRequirePower = createBooleanValue(builder, "makeMetalDoorsRequirePower", false, true, "Make Extended's metal doors and trapdoors require power to open");
		dripstoneMinimumDamageHeight = createDoubleValue(builder, "dripstoneMinimumDamageHeight", 1.0D, 0.0D, 5.0D, false, "Set the height at which dripstone hurts entities when interacting, (0.0 is the default value in renewed)");
		builder.pop();

		builder.push("Chair settings");
		enableChairSlabSitting = createBooleanValue(builder, "enableChairSlabSitting", true, false, "Allow players to sit on slabs or stairs (overrides all below)");
		enableChairSwapping = createBooleanValue(builder, "enableChairSwapping", true, false, "Allow players to switch seats without standing");
		enableChairProtectionOverride = createBooleanValue(builder, "enableChairProtectionOverride", true, false, "Allow players sit on seats in banner-protected areas (global setting overriding banner spicific settings)");
		builder.pop();

		builder.push("Gameplay");
		enableVanillaEnchanting = createBooleanValue(builder, "enableVanillaEnchanting", false, true, "Enables vanilla enchanting system in Middle Earth");
		enableVanillaXp = createBooleanValue(builder, "enableVanillaXp", false, true, "Enables Xp orbs and bar in Middle Earth");
		enableExtendedModifiers = createBooleanValue(builder, "enableExtendedModifiers", true, true, "Enables Extended modifier system");
		builder.pop();

		builder.push("Special settings");
		enableDevMode = createBooleanValue(builder,"enableDevMode", false, true, "Never enable this unless you are on the Extended Dev Team or developing the mod!");
		enableExtendedLogging = createBooleanValue(builder,"enableExtendedLogging", false, true, "Toggle to show additional log information if stuff is not working properly");
		enableSpecialLogging = createBooleanValue(builder,"enableSpecialLogging", false, true, "Toggles additional logged information, changes by update, special things");
		enableStaffServer = createBooleanValue(builder,"enableStaffServer", false, true, "");
		builder.pop();
		return builder;
	}
}
