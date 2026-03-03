package com.github.maximuslotro.lotrrextended.common.config;

import javax.annotation.Nonnull;

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
	public static ForgeConfigSpec.BooleanValue enableHungerDecrease;
	public static ForgeConfigSpec.ConfigValue<String> alternateMapTeleportCommand;

	// Entity Settings
	public static ForgeConfigSpec.IntValue barrowWightChestFirstSpawnChance;
	public static ForgeConfigSpec.BooleanValue enableLegacyCaptainPassiveBehavior;
	public static ForgeConfigSpec.BooleanValue enableLegacyTraderPassiveBehavior;
	public static ForgeConfigSpec.BooleanValue enableWanderingTraderSpawns;
	public static ForgeConfigSpec.BooleanValue enableBanditEncounters;
	public static ForgeConfigSpec.BooleanValue enableTolkienSpawning;

	// Block Settings
	public static ForgeConfigSpec.BooleanValue barsConnectToBranches;
	public static ForgeConfigSpec.BooleanValue barsConnectToSlabSides;
	public static ForgeConfigSpec.BooleanValue makeMetalDoorsRequirePower;
	public static ForgeConfigSpec.DoubleValue dripstoneMinimumDamageHeight;
	public static ForgeConfigSpec.IntValue bellRingChainMaxLength;

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

	// Server Enhancements
	public static ForgeConfigSpec.BooleanValue enableExtendedSpawnProtection;
	public static ForgeConfigSpec.IntValue overworldSpawnProtectionRadius;
	public static ForgeConfigSpec.IntValue middleEarthSpawnProtectionRadius;

	// World Generation
	public static ForgeConfigSpec.BooleanValue enableFixedStructures;
	public static ForgeConfigSpec.BooleanValue enableFixedStructureImladris;
	public static ForgeConfigSpec.BooleanValue enableFixedStructureTowerHills;

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
	}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Gameplay settings");
		enableFellowshipCreation = createBooleanValue(builder, "enableFellowshipCreation", true, false, "Enable fellowship creation (if disabled admins can still create via command)");
		enableRenewedWaypointCreation = createBooleanValue(builder, "enableRenewedWaypointCreation", true, false, "Enable renewed based waypoint creation");
		enableLegacyWaypointCreation = createBooleanValue(builder, "enableLegacyWaypointCreation", false, false, "Enable legacy based waypoint creation (Not added yet)");
		enableBannerProtection = createBooleanValue(builder, "enableBannerProtection", true, false, "Enable banner protection");
		npcGlobalSpawnDampingCoefficient = createDoubleValue(builder, "npcGlobalSpawnDampingCoefficient", 1.0D, 0.0D, 1.0D, false, "Reduce the global npc spawning rate, the lower the value, the less spawns. This is a linear setting, (1.0 is the default renewed spawn rate)");
		enableDefaultWaypointTeleportation = createBooleanValue(builder, "enableDefaultWaypointTeleportation", true, false, "Enable Teleportation To Default Waypoints");
		enableCustomWaypointTeleportation = createBooleanValue(builder, "enableCustomWaypointTeleportation", true, false, "Enable Teleportation To Custom Waypoints");
		enableLegacyFeastMode = createBooleanValue(builder, "enableLegacyFeastMode", true, false, "Enable the eating of food even if player is not hungry, like in legacy");
		enableHungerDecrease = createBooleanValue(builder, "enableHungerDecrease", true, false, "Decrease hunger meter exhaustion");
		alternateMapTeleportCommand = createStringValue(builder, "alternateMapTeleportCommand", "/tp %d %d %d", false, "Map menu instant tp command");
		builder.pop();

		builder.push("Entity settings");
		barrowWightChestFirstSpawnChance = createIntegerValue(builder, "barrowWightChestFirstSpawnChance", 50, 0, 100, false, "Should stone chests in the barrow downs spawn wight on first open. 0=only when cursed, 100=spawns on every first open like in legacy");
		enableLegacyCaptainPassiveBehavior = createBooleanValue(builder, "enableLegacyCaptainPassiveBehavior", true, false, "Make captains passive until attacked, like in legacy");
		enableLegacyTraderPassiveBehavior = createBooleanValue(builder, "enableLegacyTraderPassiveBehavior", true, false, "Make traders passive until attacked, like in legacy");
		enableWanderingTraderSpawns = createBooleanValue(builder, "enableWanderingTraderSpawns", true, false, "Enable wandering traders");
		enableBanditEncounters = createBooleanValue(builder, "enableBanditEncounters", true, false, "Enable bandit encounters");
		enableTolkienSpawning = createBooleanValue(builder, "enableTolkienSpawning", true, false, "Enable tolkien spawning");
		builder.pop();

		builder.push("Block settings");
		barsConnectToBranches = createBooleanValue(builder, "barsConnectToBranches", true, true, "Make bars and panes connect to branch blocks");
		barsConnectToSlabSides = createBooleanValue(builder, "barsConnectToSlabSides", true, true, "Make bars and panes connect to half sides of vertical slabs");
		makeMetalDoorsRequirePower = createBooleanValue(builder, "makeMetalDoorsRequirePower", false, true, "Make Extended's metal doors and trapdoors require power to open");
		dripstoneMinimumDamageHeight = createDoubleValue(builder, "dripstoneMinimumDamageHeight", 1.0D, 0.0D, 5.0D, false, "Set the height at which dripstone hurts entities when interacting, (0.0 is the default value in renewed)");
		bellRingChainMaxLength = createIntegerValue(builder, "bellRingChainMaxLength", 10, 1, 50, false, "Max chain length a bell ring will attempt");
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

		builder.push("Server Enhancements");
		enableExtendedSpawnProtection = createBooleanValue(builder,"enableExtendedSpawnProtection", false, false, "Enable extended spawn protection for servers");
		overworldSpawnProtectionRadius = createIntegerValue(builder, "overworldSpawnProtectionRadius", 16, 0, 500, false, "The radius for Overworld spawn server protection (allows larger area than vanilla protection)");
		middleEarthSpawnProtectionRadius = createIntegerValue(builder, "middleEarthSpawnProtectionRadius", 16, 0, 500, false, "The radius for Middle Earth spawn server protection");
		builder.pop();

		builder.push("World Generation");
		enableFixedStructures = createBooleanValue(builder,"enableFixedStructures", true, true, "Enable fixed structures to generate, OVERRIDES ALL OTHER STRUCTURE SETTINGS!");
		enableFixedStructureImladris = createBooleanValue(builder,"enableFixedStructureImladris", true, true, "Enable Imladris lore structure");
		enableFixedStructureTowerHills = createBooleanValue(builder,"enableFixedStructureTowerHills", true, true, "Enable Tower Hills lore structure");
		builder.pop();
		return builder;
	}


	public static boolean canSpawnStructures() {
		return enableFixedStructures.get();
	}

	public static boolean canSpawnStructure(@Nonnull ForgeConfigSpec.BooleanValue additionalCheck) {
		return canSpawnStructures() && additionalCheck.get();
	}
}
