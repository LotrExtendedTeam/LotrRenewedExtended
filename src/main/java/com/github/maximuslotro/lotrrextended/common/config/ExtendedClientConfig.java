package com.github.maximuslotro.lotrrextended.common.config;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ExtendedClientConfig {
	public static final ForgeConfigSpec CLIENT_SPEC = setupConfig(new ForgeConfigSpec.Builder()).build();
	public static ForgeConfigSpec.BooleanValue enableDevMode;

	public static void register() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, CLIENT_SPEC);
	}

	public static void registerDataGen() {}

	private static ForgeConfigSpec.Builder setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Special settings");
		enableDevMode = createBooleanValue(builder,"enableDevModeClient", false, true, "Never enable this unless you are on the Extended Dev Team or developing the mod!");
		builder.pop();
		return builder;
	}

	private static ForgeConfigSpec.BooleanValue createBooleanValue(Builder builder, String key, boolean defaultValue, boolean needsWorldRestart, String comment){
		String transKey = String.format("config.%s.%s", LotrExtendedMod.MODID, key);
		if(DatagenModLoader.isRunningDataGen()) {DataGenValueHolder.configCommentHolders.put(transKey, comment);}
		builder.comment(comment).translation(transKey);
		if (needsWorldRestart) {builder.worldRestart();}
		return builder.define(key, defaultValue);
	}
}
