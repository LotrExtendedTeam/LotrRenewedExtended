package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.feature.IFeatureConfig;

/**
 * Configuration object for weighted structure pool selection during structure feature generation.
 * <p>
 * This class allows defining multiple structure pools with associated integer weights, enabling
 * probabilistic selection of a pool during generation based on their relative weights.
 * <p>
 * Used in procedural generation systems where certain pools should appear more frequently
 * while maintaining variety across different generation passes.
 */
public class WeightedStructureConfig implements IFeatureConfig {
	/**
     * Codec for serializing and deserializing {@link WeightedStructureConfig} from JSON or NBT
     * during data pack loading or network synchronization.
     * <p>
     * It serializes {@code weightedPools} as a map from pool names to integer weights,
     * defaulting to an empty map if unspecified.
     */
	public static final Codec<WeightedStructureConfig> CODEC = RecordCodecBuilder.create((p_236650_0_) -> p_236650_0_.group(Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("weighted_pools").orElseGet(HashMap::new)
            .forGetter(config -> config.weightedPools)).apply(p_236650_0_, WeightedStructureConfig::new));
	/**
     * A map containing structure pool names as keys and their associated integer weights as values.
     * <p>
     * Used to determine the probability of selecting a given pool during generation.
     */
	public final Map<String, Integer> weightedPools;
	/**
     * Constructs a {@link WeightedStructureConfig} with a specified weighted pool map.
     *
     * @param weightedPools Map of structure pool names to their weights.
     */
	public WeightedStructureConfig(Map<String, Integer> weightedPools) {
		this.weightedPools = weightedPools;
	}
	/**
     * Constructs a {@link WeightedStructureConfig} with an empty pool map.
     * Use {@link #addStructureToPool(String, int)} to populate after construction.
     */
	public WeightedStructureConfig() {
		this.weightedPools = new HashMap<>();
	}
	 /**
     * Adds a structure pool with a specified weight to the configuration.
     *
     * @param pool   The name of the structure pool.
     * @param weight The weight associated with the pool, determining its selection probability.
     * @return This {@link WeightedStructureConfig} for chaining.
     */
	public WeightedStructureConfig addStructureToPool(String pool, int weight) {
		weightedPools.put(pool, weight);
		return this;
	}
	/**
     * Selects a pool randomly from the configured weighted pools based on their relative weights.
     *
     * @param rand The random generator to use for selection.
     * @return The selected structure pool name, or {@code null} if no pools are configured.
     * @throws RuntimeException if selection fails due to an internal inconsistency.
     */
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
