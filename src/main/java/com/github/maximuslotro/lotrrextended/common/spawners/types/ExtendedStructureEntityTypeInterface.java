package com.github.maximuslotro.lotrrextended.common.spawners.types;

import java.util.Random;

import net.minecraft.entity.EntityType;

public interface ExtendedStructureEntityTypeInterface {
	EntityType<?> getSpawn(Random random);
}