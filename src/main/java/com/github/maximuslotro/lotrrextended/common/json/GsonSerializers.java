package com.github.maximuslotro.lotrrextended.common.json;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnerEntity;
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
			.registerTypeAdapter(ExtendedStructureEntitySpawnerEntity.class, new ExtendedStructureEntitySpawnerEntity.Serializer())
			.registerTypeAdapter(ExtendedStructureEntitySpawner.class, new ExtendedStructureEntitySpawner.Serializer());
	}
}
