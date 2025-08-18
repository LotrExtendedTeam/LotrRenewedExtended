package com.github.maximuslotro.lotrrextended.common.datagen.entity.structures;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawner;
import com.github.maximuslotro.lotrrextended.common.spawners.types.MultiSpawnType;

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
		single(EntityType.HORSE).save(pConsumer, ExtendedSpawners.HORSE_SINGLE);
		// HOBBITS
		single(ExtendedEntities.HOBBIT_FARMER.get()).setRespawnOnDeath().setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_BARN_FARMER);
		// un-disable when hobbit farmhands is added
		single(LOTREntities.HOBBIT.get()).setHomeInfo(false, 12).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_BARN_FARMHAND);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 70, 1, 5).addSinglePool(EntityType.HORSE, 50, 1, 2).save(pConsumer, ExtendedSpawners.HOBBIT_BARN_ANIMALS);
		// un-disable when windmill hobbit is added
		single(LOTREntities.HOBBIT.get()).setRespawnOnDeath().setHomeInfo(false, 8).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_WINDMILL);
		couple(LOTREntities.HOBBIT.get(), 8).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOLE_SMALL);
		couple(LOTREntities.HOBBIT.get(), 30).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOLE_LARGE);
		couple(LOTREntities.HOBBIT.get(), 12).save(pConsumer, ExtendedSpawners.HOBBIT_FAMILY_HOUSE);
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setRespawnOnDeath().setHomeInfo(false, 16).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER_SMALL);
		single(ExtendedEntities.HOBBIT_BARTENDER.get()).setRespawnOnDeath().setHomeInfo(false, 32).save(pConsumer, ExtendedSpawners.HOBBIT_BARTENDER_LARGE);
		persisted(LOTREntities.HOBBIT.get(), 2, 4, false, 10, 8).setBoundaries(4, -4, 12, 6, -2, 8).setPersistenceRequired(false).save(pConsumer, ExtendedSpawners.HOBBIT_PICNIC_TABLE);
		persisted(LOTREntities.HOBBIT.get(), 1, 3, false, 10, 8).setBoundaries(8, -8, 12, 6, -3, 8).setPersistenceRequired(false).save(pConsumer, ExtendedSpawners.HOBBIT_PICNIC_BLANKET);
		persisted(LOTREntities.HOBBIT.get(), 2, 5, false, 15, 8).setBoundaries(10, -8, 15, 6, -2, 8).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_TAVERN_SMALL);
		persisted(LOTREntities.HOBBIT.get(), 4, 7, false, 18, 15).setBoundaries(12, -9, 20, 4, -4, 12).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_TAVERN_LARGE);
		// un-disable when sheriff hobbit is added
		single(LOTREntities.HOBBIT.get(), 1, 1, false, 12, 1).setBoundaries(12, -9, 20, 1, -1, 2).disableForFutureSpawning().enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.HOBBIT_TAVERN_LARGE_SHERIFF);

		// RANGERS
		single(ExtendedEntities.DUNEDAIN_RANGER.get()).setHomeInfo(false, 3).setRandomDiscardChance(60).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_SINGLE_CHANCE);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 16, 4).setBoundaries(20, -12, 20, 4, -4, 8).setPersistenceRequired(false).enableNonRandomSpawning(60).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_WATCHTOWER);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 4, false, 18, 5).setBoundaries(12, -12, 24, 4, -4, 8).setPersistenceRequired(false).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 3, false, 12, 4).setBoundaries(12, -12, 16, 4, -4, 8).setPersistenceRequired(false).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_ENCAMPMENT);
		single(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get()).setRespawnOnDeath().setHomeInfo(false, 10).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_ENCAMPMENT_CAPTAIN);
		persisted(ExtendedEntities.DUNEDAIN_RANGER.get(), 1, 1, false, 18, 2).setBoundaries(12, -12, 10, 4, -4, 6).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_HOUSE_CAMP);
		single(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get()).setHomeInfo(false, 10).setRandomDiscardChance(80).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_CAPTAIN);
		single(ExtendedEntities.DUNEDAIN_RANGER_CAPTAIN.get()).setRespawnOnDeath().setHomeInfo(false, 10).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_CAPTAIN_PERMANENT);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 90, 1, 5).addSinglePool(EntityType.HORSE, 100, 1, 2).setPoolRolls(3).save(pConsumer, ExtendedSpawners.DUNEDAIN_RANGER_CAMP_ANIMALS);
		//Gundabad
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.GUNDABAD_ORC.get(), 100).addPool(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 80)
			.build(), 1, 4, false, 18, 5).setBoundaries(12, -12, 24, 4, -4, 8).setPersistenceRequired(false).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.GUNDABAD_CAMP_ORC);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.GUNDABAD_ORC.get(), 100).addPool(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 80)
			.build(), 1, 4, false, 10, 4).enableNonRandomSpawning().setPersistenceRequired(false).setEntitySpawnTickRate(24000).save(pConsumer, ExtendedSpawners.GUNDABAD_ORC_RUIN);
		single(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get()).setHomeInfo(false, 10).setRandomDiscardChance(80).save(pConsumer, ExtendedSpawners.GUNDABAD_CAMP_CAPTAIN);
		single(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get()).setRespawnOnDeath().setHomeInfo(false, 10).save(pConsumer, ExtendedSpawners.GUNDABAD_CAMP_CAPTAIN_PERMANENT);
		single(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.HOBBIT.get(), 80).addPool(LOTREntities.BREE_HOBBIT.get(), 80)
			.addPool(LOTREntities.BREE_MAN.get(), 90).addPool(ExtendedEntities.DUNEDAIN_RANGER.get(), 50)
			.addPool(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), 30).addPool(LOTREntities.LINDON_ELF.get(), 1)
			.addPool(LOTREntities.RIVENDELL_ELF.get(), 1).addPool(LOTREntities.GUNDABAD_ORC.get(), 5)
			.addPool(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 5).build()).setRandomDiscardChance(40).save(pConsumer, ExtendedSpawners.GUNDABAD_CAMP_PRISONERS);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.GUNDABAD_ORC.get(), 100).addPool(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 80)
			.build(), 5, 7, false, 26, 20).setPersistenceRequired(false).setBoundaries(12, -12, 24, 4, -4, 20).enableNonRandomSpawning(70).save(pConsumer, ExtendedSpawners.GUNDABAD_ENCAMPMENT_ORC);
		single(ExtendedEntities.GUNDABAD_ORC_CHIEFTAIN.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.GUNDABAD_ENCAMPMENT_CAPTAIN);
		single(ExtendedEntities.GUNDABAD_CAMP_TRADER.get()).setRespawnOnDeath().setHomeInfo(false, 5).save(pConsumer, ExtendedSpawners.GUNDABAD_ENCAMPMENT_TRADER);
		single(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.HOBBIT.get(), 80).addPool(LOTREntities.BREE_HOBBIT.get(), 80)
			.addPool(LOTREntities.BREE_MAN.get(), 90).addPool(ExtendedEntities.DUNEDAIN_RANGER.get(), 50)
			.addPool(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), 30).addPool(LOTREntities.LINDON_ELF.get(), 1)
			.addPool(LOTREntities.RIVENDELL_ELF.get(), 1).addPool(LOTREntities.GUNDABAD_ORC.get(), 5)
			.addPool(LOTREntities.GUNDABAD_ORC_ARCHER.get(), 5).build()).setRandomDiscardChance(30).save(pConsumer, ExtendedSpawners.GUNDABAD_ENCAMPMENT_PRISONERS);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 90, 1, 5).setPoolRolls(2).save(pConsumer, ExtendedSpawners.GUNDABAD_ENCAMPMENT_ANIMAL);
		persisted(MultiSpawnType.Builder.multi().addPool(LOTREntities.GUNDABAD_WARG.get(), 100).build(), 2, 3, true, 26, 8)
		.setBoundaries(12, -12, 24, 4, -4, 20).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.GUNDABAD_ENCAMPMENT_WARG);
		// Bree Hamlets
		couple(LOTREntities.BREE_MAN.get(), 10).save(pConsumer, ExtendedSpawners.BREE_HAMLET_FAMILY);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 70, 1, 5).addSinglePool(EntityType.HORSE, 50, 1, 2).save(pConsumer, ExtendedSpawners.BREE_HAMLET_ANIMALS);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.BREE_GUARD.get(), 10).addPool(LOTREntities.BREE_MAN.get(), 100)
			.build(), 1, 4, false, 25, 5).setPersistenceRequired(false).setBoundaries(12, -12, 24, 8, -4, 14).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.BREE_HAMLET_AREA);
		single(ExtendedEntities.BREE_SHERIFF.get()).setRespawnOnDeath().setHomeInfo(false, 12).setRandomDiscardChance(20).save(pConsumer, ExtendedSpawners.BREE_HAMLET_GUARD_HOUSE_SHERIFF);
		single(ExtendedEntities.BREE_POTTER.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_HAMLET_POTTER);
		single(ExtendedEntities.BREE_FARMER.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_HAMLET_FARMER);
		persisted(LOTREntities.BREE_GUARD.get(), 1, 4, false, 24, 5).setBoundaries(12, -12, 32, 4, -4, 16).setPersistenceRequired(false).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.BREE_HAMLET_GUARD_HOUSE);
		single(LOTREntities.BREE_MAN.get()).setRespawnOnDeath().setHomeInfo(false, 8).disableForFutureSpawning().save(pConsumer, ExtendedSpawners.BREE_HAMLET_MILLER);
		// Bree Villages
		couple(LOTREntities.BREE_MAN.get(), 10).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_FAMILY_SPAWNER);
		singleWeighted(EntityType.COW, 90, 1, 3).addSinglePool(EntityType.SHEEP, 90, 1, 3).addSinglePool(EntityType.PIG, 90, 1, 3)
		.addSinglePool(EntityType.CHICKEN, 70, 1, 5).addSinglePool(EntityType.HORSE, 50, 1, 2).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_ANIMALS);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.BREE_GUARD.get(), 20).addPool(LOTREntities.BREE_MAN.get(), 100).addPool(LOTREntities.BREE_HOBBIT.get(), 20)
			.build(), 1, 4, false, 25, 5).setBoundaries(12, -12, 32, 8, -4, 18).setPersistenceRequired(false).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_AREA_SPAWNER);
		single(ExtendedEntities.BREE_SHERIFF.get()).setRespawnOnDeath().setHomeInfo(false, 12).setRandomDiscardChance(20).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_GUARD_HOUSE_SHERIFF);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_POTTER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_POTTER.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_POTTER);
		single(ExtendedEntities.BREE_FARMER.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_FARMER);
		persisted(LOTREntities.BREE_GUARD.get(), 1, 4, false, 24, 5).setBoundaries(12, -12, 32, 4, -4, 16).setPersistenceRequired(false).enableNonRandomSpawning(20).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_GUARD_HOUSE);
		single(ExtendedEntities.BREE_SMITH.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_SMITH);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_BAKER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_BAKER.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_BAKER);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_BUTCHER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_BUTCHER.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_BUTCHER);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_BREWER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_BREWER.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_BREWER);
		single(ExtendedEntities.BREE_STONEMASON.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_STONEMASON);
		single(ExtendedEntities.BREE_LUMBERMAN.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_LUMBERMAN);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_INNKEEPER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_INNKEEPER.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_INNKEEPER);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_FLORIST.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_FLORIST.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_FLORIST);
		single(ExtendedEntities.BREE_CHEESEMONGER.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_CHEESEMONGER);
		single(ExtendedEntities.BREE_LEATHERWORKER.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_LEATHERWORKER);
		single(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.BREE_BAKER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_BAKER.get(), 1)
			.addPool(ExtendedEntities.BREE_BREWER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_BREWER.get(), 1)
			.addPool(ExtendedEntities.BREE_BUTCHER.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_BUTCHER.get(), 1)
			.addPool(ExtendedEntities.BREE_FLORIST.get(), 1).addPool(ExtendedEntities.BREE_HOBBIT_FLORIST.get(), 1)
			.addPool(ExtendedEntities.BREE_STONEMASON.get(), 1)
			.addPool(ExtendedEntities.BREE_LUMBERMAN.get(), 1)
			.addPool(ExtendedEntities.BREE_SMITH.get(), 1)
			.addPool(ExtendedEntities.BREE_FARMER.get(), 1)
			.build()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_STALL);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.BREE_MAN.get(), 1).addPool(LOTREntities.BREE_HOBBIT.get(), 1)
			.build(), 2, 5, false, 15, 8).setBoundaries(10, -8, 15, 10, -2, 8).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.BREE_VILLAGE_INN);
		single(ExtendedEntities.BREE_STABLEMASTER.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_STABLEMASTER);
		single(LOTREntities.BREE_MAN.get()).setRespawnOnDeath().disableForFutureSpawning().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_MANOR_LORD);
		single(ExtendedEntities.BREE_SHERIFF.get()).setRespawnOnDeath().setHomeInfo(false, 6).setRandomDiscardChance(50).save(pConsumer, ExtendedSpawners.BREE_VILLAGE_MANOR_SHERIFF);
		// BLUE MOUNTAINS
		single(ExtendedEntities.BLUE_DWARVEN_SMITH.get()).setRespawnOnDeath().setHomeInfo(false, 8).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_SMITH);
		single(ExtendedEntities.BLUE_DWARVEN_COMMANDER.get()).setRespawnOnDeath().setHomeInfo(false, 4).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_COMMANDER);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), 2).addPool(LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), 1)
			.build(), 2, 5, false, 15, 8).setBoundaries(10, -8, 15, 4, -2, 5).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_STRONGHOLD);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.BLUE_MOUNTAINS_WARRIOR.get(), 2).addPool(LOTREntities.BLUE_MOUNTAINS_ARCHER.get(), 1)
			.build(), 2, 5, false, 30, 8).setBoundaries(10, -8, 15, 4, -2, 5).setPersistenceRequired(false).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_STRONGHOLD_ALT);
		single(ExtendedEntities.BLUE_DWARVEN_BARTENDER.get()).setRespawnOnDeath().setHomeInfo(false, 8).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_BARTENDER);
		couple(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), 12).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_FAMILY);
		persisted(MultiSpawnType.Builder.multi()
			.addPool(LOTREntities.BLUE_MOUNTAINS_DWARF.get(), 1)
			.build(), 5, 7, false, 26, 20).setBoundaries(12, -12, 24, 4, -4, 16).setPersistenceRequired(false).enableNonRandomSpawning(30).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_AREA);
		single(ExtendedEntities.BLUE_DWARVEN_STONEMASON.get()).setRespawnOnDeath().setHomeInfo(false, 8).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_STONEMASON);
		single(ExtendedEntities.BLUE_DWARVEN_BUTCHER.get()).setRespawnOnDeath().setHomeInfo(false, 8).save(pConsumer, ExtendedSpawners.BLUE_MOUNTIAN_BUTCHER);
		persistedSingle(MultiSpawnType.Builder.multi()
			.addPool(ExtendedEntities.RIVENDELL_WANDERER.get(), 5)
			.addPool(ExtendedEntities.GALADHRIM_WANDERER.get(), 1)
			.build(), false, 5).setPersistenceRequired(false).setEntitySpawnMinutes(15).enableNonRandomSpawning().save(pConsumer, ExtendedSpawners.BLUE_MOUNTAINS_TEMP_ELF_TRADER);
		// DWARVEN
		single(ExtendedEntities.DWARVEN_SMITH.get()).setRespawnOnDeath().setHomeInfo(false, 12).save(pConsumer, ExtendedSpawners.DWARVEN_SMITH);
	}
}