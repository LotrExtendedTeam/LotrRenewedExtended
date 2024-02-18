package com.github.maximuslotro.lotrExtended.common.config;

import com.github.maximuslotro.lotrExtended.LotrExtendedMod;
import com.github.maximuslotro.lotrExtended.common.datagen.DataGenValueHolder;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ExtendedServerConfig {
	public static final ForgeConfigSpec SERVER_SPEC;
	public static ForgeConfigSpec.BooleanValue enableDevMode;
	public static ForgeConfigSpec.BooleanValue enableFellowshipCreation;
	public static ForgeConfigSpec.BooleanValue enableRenewedWaypointCreation;
	public static ForgeConfigSpec.BooleanValue enableLegacyWaypointCreation;

	static {
	    ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
	    setupConfig(configBuilder);
	    SERVER_SPEC = configBuilder.build();
	}

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
	}

	private static void setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Gameplay settings");
		enableFellowshipCreation = createBooleanValue(builder, "enableFellowshipCreation", true, false, "Enable or disable fellowship creation");
		//enableRenewedWaypointCreation = createBooleanValue(builder, "enableRenewedWaypointCreation", true, false, "Enable or disable renewed based waypoint creation");
		//enableLegacyWaypointCreation = createBooleanValue(builder, "enableLegacyWaypointCreation", false, false, "Enable or disable legacy based waypoint creation");
		builder.pop();

		builder.push("Special settings");
		enableDevMode = createBooleanValue(builder,"enableDevMode", false, true, "Never enable this unless you are on the Extended Dev Team or developing the mod!");
		builder.pop();
	}

	private static ForgeConfigSpec.BooleanValue createBooleanValue(Builder builder, String key, boolean defaultValue, boolean needsWorldRestart, String comment){
		String transKey = String.format("config.%s.%s", LotrExtendedMod.MODID, key);
		if(DatagenModLoader.isRunningDataGen()) {DataGenValueHolder.configCommentHolders.put(transKey, comment);}
		builder.comment(comment).translation(transKey);
		if (needsWorldRestart) {builder.worldRestart();}
		return builder.define(key, defaultValue);
	}
}
