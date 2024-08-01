package com.github.maximuslotro.lotrrextended.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.data.PlayerDataModule;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.util.ResourceLocation;

public class ExtendedReflectionHelper {

	public static UUID getAlignmentData(PlayerDataModule data) {
		UUID fellowData = null;
		try {
			Method fooMethod = PlayerDataModule.class.getDeclaredMethod("getPlayerUUID");
			fooMethod.setAccessible(true);
			fellowData = (UUID) fooMethod.invoke((PlayerDataModule)data);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			ExtendedLog.error("Reflection failed to get PlayerDataModule UUID");
		}
		return fellowData;
	}

	public static ResourceLocation getKegLootTable(KegTileEntity data) {
		ResourceLocation location = null;
		try {
			Field field = KegTileEntity.class.getField("lootTable");
			location = (ResourceLocation) field.get(data);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			e.printStackTrace();
			ExtendedLog.error("Reflection failed to get keg LootTable");
		}
		return location;
	}

	public static void setKegloottable(KegTileEntity data, ResourceLocation location, long seed) {
		try {
			Method fooMethod = KegTileEntity.class.getMethod("setLootTable",ResourceLocation.class, long.class);
			fooMethod.invoke(data, location, seed);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			ExtendedLog.error("Reflection failed to set keg LootTable");
		}
	}

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
