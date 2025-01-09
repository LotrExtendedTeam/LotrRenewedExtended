package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExtendedHoleySphereConfig implements IFeatureConfig {
    public static final Codec<ExtendedHoleySphereConfig> CODEC = RecordCodecBuilder.create((p_236518_0_) -> {
        return p_236518_0_.group(BlockState.CODEC.listOf().fieldOf("state").forGetter((p_236521_0_) -> {
            return p_236521_0_.states;
        }), FeatureSpread.codec(0, 4, 4).fieldOf("radius").forGetter((p_236520_0_) -> {
            return p_236520_0_.radius;
        }), Codec.intRange(0, 4).fieldOf("half_height").forGetter((p_236519_0_) -> {
            return p_236519_0_.halfHeight;
        }), BlockState.CODEC.listOf().fieldOf("targets").forGetter((p_236517_0_) -> {
            return p_236517_0_.targets;
        }), Codec.intRange(0, 100).fieldOf("discard_chance").forGetter((p_236519_0_) -> {
            return p_236519_0_.discardChance;
        })).apply(p_236518_0_, ExtendedHoleySphereConfig::new);
    });
    public final List<BlockState> states;
    public final FeatureSpread radius;
    public final int halfHeight;
    public final List<BlockState> targets;
    public final int discardChance;

    /** Feature creates a cylinder of a mixture of blockstates
     * @see net.minecraft.world.gen.feature.SphereReplaceConfig
     * @param states The List of blockstates to pull from
     * @param radius The radius of the base of the sphere
     * @param halfHeight Height of the placed sphere into the ground
     * @param targets What blocks to replace
     * @param discardChance Chance to discard blocks as going out from center, compounds
     */
    public ExtendedHoleySphereConfig(List<BlockState> states, FeatureSpread radius, int halfHeight, List<BlockState> targets, int discardChance) {
        this.states = states;
        this.radius = radius;
        this.halfHeight = halfHeight;
        this.targets = targets;
        this.discardChance = discardChance;
    }
}
