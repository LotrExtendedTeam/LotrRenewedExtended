package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config;

import java.util.Random;

import net.minecraft.world.gen.feature.IFeatureConfig;

public interface ExtendedPoolFeatureConfig extends IFeatureConfig {

	public String getRandomPool(Random rand);
}
