package com.github.maximuslotro.lotrrextended.common.config;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.DatagenModLoader;

public class ExtendedBaseConfigHelper {
	protected static ForgeConfigSpec.BooleanValue createBooleanValue(Builder builder, String key, boolean defaultValue, boolean needsWorldRestart, String comment){
		String transKey = String.format("config.%s.%s", LotrExtendedMod.MODID, key);
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.configCommentHolders.put(transKey, comment);
		}
		builder.comment(comment).translation(transKey);
		if (needsWorldRestart) {
			builder.worldRestart();
		}
		return builder.define(key, defaultValue);
	}

	protected static ForgeConfigSpec.ConfigValue<String> createStringValue(Builder builder, String key, String defaultValue, boolean needsWorldRestart, String comment){
		String transKey = String.format("config.%s.%s", LotrExtendedMod.MODID, key);
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.configCommentHolders.put(transKey, comment);
		}
		builder.comment(comment).translation(transKey);
		if (needsWorldRestart) {
			builder.worldRestart();
		}
		return builder.define(key, defaultValue);
	}
}
