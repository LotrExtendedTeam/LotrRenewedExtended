package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExtendedWallsFeatureConfig implements IFeatureConfig {
	public static final Codec<ExtendedWallsFeatureConfig> CODEC =
		RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("wall_x").forGetter(c -> c.wallsCenterX),
			Codec.INT.fieldOf("wall_z").forGetter(c -> c.wallsCenterZ),
			Codec.INT.fieldOf("wall_r").forGetter(c -> c.wallsRadius),
			Codec.INT.fieldOf("wall_h").forGetter(c -> c.wallHeight),
			Codec.INT.fieldOf("wall_hp").forGetter(c -> c.wallPortHeight),
			Codec.FLOAT.fieldOf("wall_t").forGetter(c -> c.wallThickness),
			BlockState.CODEC.fieldOf("wall_state").forGetter((p_161322_) -> {
				return p_161322_.wallState;
			})
			).apply(instance, ExtendedWallsFeatureConfig::new));

	public final int wallsCenterX;
	public final int wallsCenterZ;
	public final int wallsRadius;
	public final int wallsRadiusSquare;
	public final int wallHeight;
	public final int wallPortHeight;
	public final float wallThickness;
	public final BlockState wallState;
	

	public ExtendedWallsFeatureConfig(int pWallsCenterX, int pWallsCenterZ, int pWallsRadius, int pWallHeight, int pWallPortHeight, float pWallThickness, BlockState pWallState) {
		wallsCenterX = pWallsCenterX;
		wallsCenterZ = pWallsCenterZ;
		wallsRadius= pWallsRadius;
		wallsRadiusSquare = pWallsRadius * pWallsRadius;
		wallHeight = pWallHeight;
		wallPortHeight = pWallPortHeight;
		wallThickness = pWallThickness;
		wallState = pWallState;
	}
}