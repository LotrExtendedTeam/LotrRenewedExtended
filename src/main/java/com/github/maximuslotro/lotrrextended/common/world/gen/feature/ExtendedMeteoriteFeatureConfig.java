package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExtendedMeteoriteFeatureConfig implements IFeatureConfig {
	public static final Codec<ExtendedMeteoriteFeatureConfig> CODEC =
			RecordCodecBuilder.create(instance -> instance.group(
					FeatureSpread.codec(1, 4, 4).fieldOf("radius_center").forGetter(c -> c.centerRadius),
					FeatureSpread.codec(4, 8, 4).fieldOf("radius_outer").forGetter(c -> c.outerRadius),
					Codec.FLOAT.fieldOf("debris_chance").forGetter(c -> c.debrisChance)
					).apply(instance, ExtendedMeteoriteFeatureConfig::new));

	public final FeatureSpread centerRadius;
	public final FeatureSpread outerRadius;
	public final float debrisChance;

	public ExtendedMeteoriteFeatureConfig(FeatureSpread centerRadius, FeatureSpread outerRadius, float debrisChance) {
		this.centerRadius = centerRadius;
		this.outerRadius = outerRadius;
		this.debrisChance = debrisChance;
	}
}