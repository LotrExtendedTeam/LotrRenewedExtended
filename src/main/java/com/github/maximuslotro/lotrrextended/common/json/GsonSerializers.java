package com.github.maximuslotro.lotrrextended.common.json;

import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexEntry;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitEquipmentEntry;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitEquipmentInstance;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawnerBoundsEntry;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnerEntry;
import com.google.gson.GsonBuilder;

import net.minecraft.loot.BinomialRange;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IntClamper;
import net.minecraft.loot.RandomValueRange;

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
			.registerTypeAdapter(ExtendedHiredUnitEquipmentInstance.class, new ExtendedHiredUnitEquipmentInstance.Serializer())
			.registerTypeAdapter(ExtendedHiredUnitEquipmentEntry.class, new ExtendedHiredUnitEquipmentEntry.Serializer())
			.registerTypeAdapter(ExtendedHiredUnitProfile.class, new ExtendedHiredUnitProfile.Serializer());
	}

	public static GsonBuilder createCodexSerializer() {
		return new GsonBuilder()
				.registerTypeAdapter(ExtendedCodexEntry.class, new ExtendedCodexEntry.Serializer());
	}
}
