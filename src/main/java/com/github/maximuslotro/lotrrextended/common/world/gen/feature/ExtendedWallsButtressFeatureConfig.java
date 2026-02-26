package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ExtendedWallsButtressFeatureConfig {
    public final int count;
    public final double widthRad;
    public final double maxExtra;

    public static final Codec<ExtendedWallsButtressFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("count").forGetter(c -> c.count),
        Codec.DOUBLE.fieldOf("width_rad").forGetter(c -> c.widthRad),
        Codec.DOUBLE.fieldOf("max_extra").forGetter(c -> c.maxExtra)
    ).apply(instance, ExtendedWallsButtressFeatureConfig::new));

    public ExtendedWallsButtressFeatureConfig(int count, double widthRad, double maxExtra) {
        this.count = count;
        this.widthRad = widthRad;
        this.maxExtra = maxExtra;
    }
}