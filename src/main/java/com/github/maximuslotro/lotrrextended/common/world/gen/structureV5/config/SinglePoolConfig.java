package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class SinglePoolConfig implements IFeatureConfig {
	public static final Codec<SinglePoolConfig> CODEC = RecordCodecBuilder.create((p_236650_0_) -> {
		return p_236650_0_.group(Codec.STRING.fieldOf("pool").orElseGet(() -> "empty")
				.forGetter(config -> config.pool)).apply(p_236650_0_, SinglePoolConfig::new);});

	private final String pool;

	public SinglePoolConfig(String pool) {
		this.pool = pool;
	}

	public String getPool() {
		return pool;
	}
}
