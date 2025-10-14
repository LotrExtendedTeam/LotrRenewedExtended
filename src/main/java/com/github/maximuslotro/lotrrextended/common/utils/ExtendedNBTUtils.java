package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.nbt.CompoundNBT;

public class ExtendedNBTUtils {
	public static boolean getBoolean(CompoundNBT nbt, String key, boolean defaultValue) {
		if(!nbt.contains(key)) {
			return defaultValue;
		}else {
			return nbt.getBoolean(key);
		}
	}
	public static int getInt(CompoundNBT nbt, String key, int defaultValue) {
		if(!nbt.contains(key)) {
			return defaultValue;
		}else {
			return nbt.getInt(key);
		}
	}
}
