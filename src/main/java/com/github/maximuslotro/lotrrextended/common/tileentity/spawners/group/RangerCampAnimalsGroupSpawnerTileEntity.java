package com.github.maximuslotro.lotrrextended.common.tileentity.spawners.group;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntitySpawnGroupTypes;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.world.server.ServerWorld;

/**
 * Replaced by the new Entity Spawner system for Structures
 * @deprecated
 * @since beta-1.6
 * */
public class RangerCampAnimalsGroupSpawnerTileEntity extends ExtendedStructureGroupSpawnerTileEntity<RangerCampAnimalsGroupSpawnerTileEntity>{

	public RangerCampAnimalsGroupSpawnerTileEntity() {
		super(ExtendedTileEntities.SPAWNER_GROUP_TILEENTITYS.get(ExtendedEntitySpawnGroupTypes.RANGER_CAMP_ANIMALS.getSerializedName()).get());
	}

	@Override
	public boolean spawnGroup(ServerWorld world) {
		Random rand = new Random();
		for(int i = rand.nextInt(4); i > 0; i--) {
			MobEntity entity = EntityType.COW.create(world);
			entity.moveTo(worldPosition, 0, 0.0F);
			entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
			if(! world.addFreshEntity(entity)) {
				return false;
			}
		}
		for(int i = rand.nextInt(4); i > 0; i--) {
			MobEntity entity = EntityType.SHEEP.create(world);
			entity.moveTo(worldPosition, 0, 0.0F);
			entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
			if(! world.addFreshEntity(entity)) {
				return false;
			}
		}
		for(int i = rand.nextInt(4); i > 0; i--) {
			MobEntity entity = EntityType.PIG.create(world);
			entity.moveTo(worldPosition, 0, 0.0F);
			entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
			if(! world.addFreshEntity(entity)) {
				return false;
			}
		}
		for(int i = rand.nextInt(4); i > 0; i--) {
			MobEntity entity = EntityType.CHICKEN.create(world);
			entity.moveTo(worldPosition, 0, 0.0F);
			entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
			if(! world.addFreshEntity(entity)) {
				return false;
			}
		}
		for(int i = rand.nextInt(2); i > 0; i--) {
			MobEntity entity = EntityType.HORSE.create(world);
			entity.moveTo(worldPosition, 0, 0.0F);
			entity.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), SpawnReason.SPAWNER, null, null);
			if(! world.addFreshEntity(entity)) {
				return false;
			}
		}
		return true;
	}
}
