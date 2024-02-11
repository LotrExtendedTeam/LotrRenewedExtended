package com.github.maximuslotro.lotroExtended.hooks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.LOTRPlayerData;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.util.ResourceLocation;

public class MixinHooks {
	public static ExtendedFellowshipDataModule getFellowshipData(LOTRPlayerData data) {
		ExtendedFellowshipDataModule fellowData = null;
		try {
			Method fooMethod = LOTRPlayerData.class.getMethod("getFellowshipData");
			fellowData = (ExtendedFellowshipDataModule) fooMethod.invoke(data);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
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
		}
		return location;
	}

	public static void setKegloottable(KegTileEntity data, ResourceLocation location, long seed) {
		try {
			Method fooMethod = KegTileEntity.class.getMethod("setLootTable",ResourceLocation.class, long.class);
			fooMethod.invoke(data, location, seed);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
