package com.github.maximuslotro.lotrrextended.common.json;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitEquipmentItem;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitEquipmentPool;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedSignTextList;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawnerBoundsEntry;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnerEntry;
import com.google.gson.GsonBuilder;

import net.minecraft.loot.BinomialRange;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IntClamper;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.ResourceLocation;

public class GsonSerializers {
	public static GsonBuilder createConditionSerializer() {
		return new GsonBuilder()
			.registerTypeAdapter(RandomValueRange.class, new RandomValueRange.Serializer())
			.registerTypeAdapter(BinomialRange.class, new BinomialRange.Serializer())
			.registerTypeAdapter(ConstantRange.class, new ConstantRange.Serializer())
			.registerTypeAdapter(IntClamper.class, new IntClamper.Serializer());
	}

	public static GsonBuilder createStructureSpawnerSerializer() {
		return createConditionSerializer()
			.registerTypeAdapter(ExtendedStructureEntitySpawnerEntry.class, new ExtendedStructureEntitySpawnerEntry.Serializer())
			.registerTypeAdapter(ExtendedStructureEntitySpawner.class, new ExtendedStructureEntitySpawner.Serializer())
			.registerTypeAdapter(ExtendedSpawnerBoundsEntry.class, new ExtendedSpawnerBoundsEntry.Serializer());
	}

	public static GsonBuilder createHiredUnitSerializer() {
		return createConditionSerializer()
			.registerTypeAdapter(ExtendedHiredUnitEquipmentPool.class, new ExtendedHiredUnitEquipmentPool.Serializer())
			.registerTypeAdapter(ExtendedHiredUnitEquipmentItem.class, new ExtendedHiredUnitEquipmentItem.Serializer())
			.registerTypeAdapter(ExtendedServerHiredUnitProfile.class, new ExtendedServerHiredUnitProfile.Serializer());
	}

	public static GsonBuilder createSignTextSerializer() {
		return createConditionSerializer()
				.registerTypeAdapter(ExtendedSignTextList.class, new ExtendedSignTextList.Serializer())
				.registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer());
	}
}
