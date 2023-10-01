package com.github.maximuslotro.lotroExtended;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lotr.common.command.data.FellowshipDataModule;
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
	
}
