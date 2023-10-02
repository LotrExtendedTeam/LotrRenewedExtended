package com.github.maximuslotro.lotroExtended;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lotr.common.command.data.FellowshipDataModule;
import lotr.common.config.LOTRConfig;
import lotr.common.config.LOTRConfig.ServerConfig;
import lotr.common.data.LOTRPlayerData;

public class LOTRHooks {
	public static FellowshipDataModule getFellowshipData(LOTRPlayerData data) {
		FellowshipDataModule fellowData = null;
		try {
			Method fooMethod = LOTRPlayerData.class.getMethod("getFellowshipData");
			fellowData = (FellowshipDataModule) fooMethod.invoke(data);
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
	
}
