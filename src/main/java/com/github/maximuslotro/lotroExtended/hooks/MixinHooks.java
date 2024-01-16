package com.github.maximuslotro.lotroExtended.hooks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.LOTRPlayerData;

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
}
