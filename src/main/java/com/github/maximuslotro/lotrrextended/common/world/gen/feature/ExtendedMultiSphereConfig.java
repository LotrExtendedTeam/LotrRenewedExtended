package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;

import java.util.List;

public class ExtendedMultiSphereConfig implements IFeatureConfig {
    public static final Codec<ExtendedMultiSphereConfig> CODEC = RecordCodecBuilder.create((p_236518_0_) -> {
        return p_236518_0_.group(BlockState.CODEC.listOf().fieldOf("state").forGetter((p_236521_0_) -> {
            return p_236521_0_.states;
        }), FeatureSpread.codec(0, 4, 4).fieldOf("radius").forGetter((p_236520_0_) -> {
            return p_236520_0_.radius;
        }), Codec.intRange(0, 4).fieldOf("half_height").forGetter((p_236519_0_) -> {
            return p_236519_0_.halfHeight;
        }), BlockState.CODEC.listOf().fieldOf("targets").forGetter((p_236517_0_) -> {
            return p_236517_0_.targets;
        })).apply(p_236518_0_, ExtendedMultiSphereConfig::new);
    });
    public final List<BlockState> states;
    public final FeatureSpread radius;
    public final int halfHeight;
    public final List<BlockState> targets;

    /** Feature creates a cylinder of a mixture of blockstates
     * @see net.minecraft.world.gen.feature.SphereReplaceConfig
     * @param states the List of blockstates to pull from
     * @param radius the radius of the base of the cylinder
     * @param halfHeight half the height of the cylinder
     * @param targets what blocks to replace
     */
    public ExtendedMultiSphereConfig(List<BlockState> states, FeatureSpread radius, int halfHeight, List<BlockState> targets) {
        this.states = states;
        this.radius = radius;
        this.halfHeight = halfHeight;
        this.targets = targets;
    }
}
