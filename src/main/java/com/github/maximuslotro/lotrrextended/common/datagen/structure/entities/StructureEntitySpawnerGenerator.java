package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawners;

import lotr.common.init.ExtendedEntities;
import net.minecraft.data.DataGenerator;

public class StructureEntitySpawnerGenerator extends StructureEntitySpawnersProvider{

	public StructureEntitySpawnerGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools() {
		buildSingleSpawner(ExtendedStructureEntitySpawners.HOBBIT_BARTENDER, ExtendedEntities.HOBBIT_BARTENDER.get(), 1, 1, false, 10);
	}
}
