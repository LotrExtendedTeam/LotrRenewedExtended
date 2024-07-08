package com.github.maximuslotro.lotrrextended.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ExtendedClientConfig extends ExtendedBaseConfigHelper{
	public static final ForgeConfigSpec CLIENT_SPEC = setupConfig(new ForgeConfigSpec.Builder()).build();
	public static ForgeConfigSpec.BooleanValue enableDevMode;
	public static ConfigValue<String> lastClientBootVersion;

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, CLIENT_SPEC);
	}

	public static void registerDataGen() {}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Special settings");
		enableDevMode = createBooleanValue(builder, "enableDevModeClient", false, true, "Never enable this unless you are on the Extended Dev Team or developing the mod!");
		lastClientBootVersion = createStringValue(builder, "lastClientBootVersion", "null", true, "Last version this mod launched with. Don't touch! Value handled by extended.");
		builder.pop();
		return builder;
	}
}
