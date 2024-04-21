package com.github.maximuslotro.lotrrextended.mixinhooks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.util.ResourceLocation;

public class MixinHooks {
	public static final int StructureLimit = 256;
	public static final int StructureLimitPosBounds = StructureLimit - 1;
	public static final int StructureLimitNegBounds = -(StructureLimit-1);

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

	public static ExtendedNpcMarriageHolder getMarriageData(NPCEntity data) {
		ExtendedNpcMarriageHolder fellowData = null;
		try {
			Field field = NPCEntity.class.getField("marriageInfo");
			fellowData = (ExtendedNpcMarriageHolder) field.get(data);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			//e.printStackTrace();
			//TODO fix, this is being thrown on clients constantly
			//FIXME before hobbit marriage update
			//System.out.println("Reflection failed to get marriageInfo");
			fellowData = null;
		}
		return fellowData;
	}
}
