package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class WeightedStructureConfig implements IFeatureConfig {
	public static final Codec<WeightedStructureConfig> CODEC = RecordCodecBuilder.create((p_236650_0_) -> p_236650_0_.group(Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("weighted_pools").orElseGet(HashMap::new)
            .forGetter(config -> config.weightedPools)).apply(p_236650_0_, WeightedStructureConfig::new));

	public final Map<String, Integer> weightedPools;

	public WeightedStructureConfig(Map<String, Integer> weightedPools) {
		this.weightedPools = weightedPools;
	}

	public WeightedStructureConfig() {
		this.weightedPools = new HashMap<>();
	}

	public WeightedStructureConfig addStructureToPool(String pool, int weight) {
		weightedPools.put(pool, weight);
		return this;
	}

	public String getRandomLocation(Random rand) {
		if(weightedPools.isEmpty()) {
        	return null;
		}
		double completeWeight = 0.0;
		for (Entry<String, Integer> item : weightedPools.entrySet())
			completeWeight += item.getValue();
		double r = rand.nextDouble() * completeWeight;
		double countWeight = 0.0;
		for (Entry<String, Integer> item : weightedPools.entrySet()) {
			countWeight += item.getValue();
			if (countWeight >= r)
				return item.getKey();
		}
		throw new RuntimeException("LotrExtended error appeared when parsing weighted structure configs!");
	}
}
