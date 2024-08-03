package com.github.maximuslotro.lotrrextended.common.datagen.structure.entities;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
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
		// ANIMALS
		single(ExtendedEntities.DUCK.get()).setHomeInfo(false, 5).save(pConsumer, ExtendedSpawners.DUCK_SINGLE);
		// HOBBITS
		single(ExtendedEntities.HOBBIT_FARMER.get()).setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_BARN_FARMER);
		single(LOTREntities.HOBBIT.get()).setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.HOBBIT_BARN_FARMHAND);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 70, 1, 5).addSinglePool(EntityType.HORSE, 50, 1, 2).save(pConsumer, ExtendedSpawners.HOBBIT_BARN_ANIMALS);
		// un-disable when windmill hobbit is added
		single(LOTREntities.HOBBIT.get()).setHomeInfo(false, 8).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_WINDMILL);
		couple(LOTREntities.HOBBIT.get(), 8).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOLE_SMALL);
		couple(LOTREntities.HOBBIT.get(), 30).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOLE_LARGE);
		couple(LOTREntities.HOBBIT.get(), 12).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOUSE);
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER_SMALL);
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setHomeInfo(false, 32).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER_LARGE);
		persisted(LOTREntities.HOBBIT.get(), 2, 4, false, 10, 8).setBoundaries(4, -4, 12, 6, -2, 8).save(pConsumer, ExtendedSpawners.HOBBIT_PICNIC_TABLE);
		persisted(LOTREntities.HOBBIT.get(), 1, 3, false, 10, 8).setBoundaries(8, -8, 12, 6, -3, 8).save(pConsumer, ExtendedSpawners.HOBBIT_PICNIC_BLANKET);
		persisted(LOTREntities.HOBBIT.get(), 2, 5, false, 15, 8).setBoundaries(10, -8, 15, 6, -2, 8).save(pConsumer, ExtendedSpawners.HOBBIT_TAVERN_SMALL);
		persisted(LOTREntities.HOBBIT.get(), 4, 7, false, 18, 15).setBoundaries(12, -9, 20, 4, -4, 12).save(pConsumer, ExtendedSpawners.HOBBIT_TAVERN_LARGE);
		// un-disable when sheriff hobbit is added
		persisted(LOTREntities.HOBBIT.get(), 1, 1, false, 12, 1).setBoundaries(12, -9, 20, 1, -1, 2).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_TAVERN_LARGE_SHERIFF);

		// RANGERS
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 16, 4).setBoundaries(20, -12, 20, 4, -4, 8).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_WATCHTOWER);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 18, 5).setBoundaries(12, -12, 24, 4, -4, 8).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 3, false, 12, 4).setBoundaries(12, -12, 16, 4, -4, 8).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_ENCAMPMENT);
		// un-disable when ranger captain is added
		single(ExtendedEntities.DUNEDAIN_RANGER.get()).setHomeInfo(false, 10).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_ENCAMPMENT_CAPTAIN);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 3, false, 18, 3).setBoundaries(12, -12, 10, 4, -4, 6).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_HOUSE_CAMP);
		// un-disable when ranger captain is added
		single(ExtendedEntities.DUNEDAIN_RANGER.get()).setHomeInfo(false, 10).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_CAPTAIN);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 90, 1, 5).addSinglePool(EntityType.HORSE, 100, 1, 2).setPoolRolls(3).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_ANIMALS);
	}
}
