package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.SpawnerType;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTREntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;

public class StructureEntitySpawnerGenerator extends StructureEntitySpawnersProvider{

	public StructureEntitySpawnerGenerator(DataGenerator pDataGenerator, String pModId) {
		super(pDataGenerator, pModId);
	}

	@Override
	public void makeStructureSpawnPools(Consumer<ExtendedStructureEntitySpawner> pConsumer) {
		//HOBBITS
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER_SMALL);
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setHomeInfo(false, 32).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER_LARGE);
		single(ExtendedEntities.HOBBIT_FARMER.get()).setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_FARMER);
		single(LOTREntities.HOBBIT.get()).setHomeInfo(false, 8).save(pConsumer, ExtendedSpawners.HOBBIT_WINDMILL);
		couple(LOTREntities.HOBBIT.get(), 8).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOLE_SMALL);
		couple(LOTREntities.HOBBIT.get(), 30).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOLE_LARGE);
		couple(LOTREntities.HOBBIT.get(), 12).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOUSE);
		// RANGERS
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 18).setBoundaries(20, -12, 24, 4, -4, 8).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_WATCHTOWER);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 18).setBoundaries(12, -12, 24, 4, -4, 8).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP);
		make(SpawnerType.SINGLE).addSinglePool(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 90, 1, 5).addSinglePool(EntityType.HORSE, 100, 1, 2).setPoolRolls(3).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_ANIMALS);
	}
}
