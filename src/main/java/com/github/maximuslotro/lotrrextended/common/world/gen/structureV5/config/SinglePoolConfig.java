package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Configuration object for a single pool structure, used in structure feature placement.
 * <p>
 * This configuration specifies which pool identifier to use during generation,
 * allowing modular structure generation from a named pool within the system.
 */
public class SinglePoolConfig implements ExtendedPoolFeatureConfig {
	/**
     * Codec for serializing and deserializing {@link SinglePoolConfig} from JSON or NBT
     * during data pack loading or network synchronization.
     * <p>
     * It serializes the {@code pool} field as a string, defaulting to "empty" if unspecified.
     */
	public static final Codec<SinglePoolConfig> CODEC = RecordCodecBuilder.create((p_236650_0_) -> p_236650_0_.group(Codec.STRING.fieldOf("pool").orElseGet(() -> "empty")
            .forGetter(config -> config.pool)).apply(p_236650_0_, SinglePoolConfig::new));
	/**
     * The name of the pool from which structures will be drawn for generation.
     */
	private final String pool;
	/**
     * Constructs a new {@link SinglePoolConfig} with the specified pool name.
     *
     * @param pool The identifier of the structure pool to use, or "empty" if none.
     */
	public SinglePoolConfig(String pool) {
		this.pool = pool;
	}
	/**
     * Gets the name of the pool from which structures will be drawn.
     *
     * @return The structure pool identifier as a string.
     */
	@Override
	public String getRandomPool(Random rand) {
		return pool;
	}
}
