package com.github.maximuslotro.lotrrextended.common.spawners;

import java.util.Random;

import net.minecraft.entity.EntityType;

public interface ExtendedStructureEntityTypeInterface {
	EntityType<?> getSpawn(Random random);
}