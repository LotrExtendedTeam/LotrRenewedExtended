package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;

import lotr.common.init.ExtendedEntities;
import net.minecraft.data.DataGenerator;

public class StructureEntitySpawnerGenerator extends StructureEntitySpawnersProvider{

	public StructureEntitySpawnerGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedStructureEntitySpawner> pConsumer) {
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER);
		single(ExtendedEntities.HOBBIT_FARMER.get()).setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_FARMER);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 30).setBoundaries(24, 12, -12, 8, 4, -4).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_SPAWNER);
	}
}
