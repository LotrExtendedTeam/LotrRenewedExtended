package com.github.maximuslotro.lotrrextended.common.utils;

import java.lang.reflect.Field;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallTorchBlock;

public class ExtendedReflectionHelper {

	public static void setTorchValues(Object obj, LOTRTorchBlock copyFromBlock) {
		try {
			Field privateField = LOTRWallTorchBlock.class.getDeclaredField("torchParticles");
			privateField.setAccessible(true);
			privateField.set(obj, copyFromBlock.copyTorchParticles());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			ExtendedLog.error("Reflection failed to set torchParticles for ExtendedLOTRWallTorchBlock, of type "+copyFromBlock.getRegistryName().toString());
		}
	}

	public static String getStringFieldFromClass(Class<?> classThing, Object obj, String fieldName) {
		String data = null;
		try {
			Field field = classThing.getDeclaredField(fieldName);
			boolean isAccessable = field.isAccessible();
			if(!isAccessable) {
				field.setAccessible(true);
			}
			data = (String) field.get(obj);
			if(!isAccessable) {
				field.setAccessible(false);
			}
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			e.printStackTrace();
			ExtendedLog.error("Reflection failed to get "+classThing.getName()+" String field: "+ fieldName);
		}
		return data;
	}

	public static Boolean getBooleanFieldFromClass(Class<?> classThing, Object obj, String fieldName) {
		Boolean data = null;
		try {
			Field field = classThing.getDeclaredField(fieldName);
			boolean isAccessable = field.isAccessible();
			if(!isAccessable) {
				field.setAccessible(true);
			}
			data = (boolean) field.get(obj);
			if(!isAccessable) {
				field.setAccessible(false);
			}
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			e.printStackTrace();
			ExtendedLog.error("Reflection failed to get "+classThing.getName()+" Boolean field: "+ fieldName);
		}
		return data;
	}
}
