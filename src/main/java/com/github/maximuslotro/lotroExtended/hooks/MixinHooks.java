package com.github.maximuslotro.lotroExtended.hooks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import lotr.common.command.data.ExtendedFellowshipDataModule;
import lotr.common.config.LOTRConfig;
import lotr.common.config.LOTRConfig.ServerConfig;
import lotr.common.data.LOTRPlayerData;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

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
	public static boolean getFellowshipConfigData() {
		boolean fellowData = false;
		try {
			Field fooMethod = ServerConfig.class.getField("enableFellowshipCreation");
			fellowData = ((LOTRConfig.BoolHolder) fooMethod.get(LOTRConfig.SERVER)).get();
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fellowData;
	}

	@SuppressWarnings("unchecked")
	public static RegistryObject<Item> getLotrRegistryItem(String fieldName) {
		RegistryObject<Item> fellowData = null;
		try {
			Field fooMethod = LOTRItems.class.getField(fieldName);
			fellowData = ((RegistryObject<Item>) fooMethod.get(Optional.empty()));
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fellowData;
	}
	
}
