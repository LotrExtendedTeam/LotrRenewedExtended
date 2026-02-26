package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedWallsFeature.EdgeType;
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
			}),
			BlockState.CODEC.fieldOf("spike_state").forGetter((p_161322_) -> {
				return p_161322_.spikeState;
			}),
			Codec.INT.fieldOf("spike_h").forGetter(c -> c.spikeHeight),
			BlockState.CODEC.fieldOf("spike_gap_state").forGetter((p_161322_) -> {
				return p_161322_.spikeGapState;
			}),
			Codec.INT.fieldOf("spike_gap_h").forGetter(c -> c.spikeGapHeight),
			Codec.INT.fieldOf("edge_type").forGetter(c -> c.edgeType.edgeType),
			ExtendedWallsButtressFeatureConfig.CODEC.fieldOf("buttress").forGetter(c -> c.buttressConfig)
			).apply(instance, ExtendedWallsFeatureConfig::new));

	public final int wallsCenterX;
	public final int wallsCenterZ;
	public final int wallsRadius;
	public final int wallHeight;
	public final int wallPortHeight;
	public final float wallThickness;
	public final BlockState wallState;
	public final BlockState spikeState;
	public final int spikeHeight;
	public final BlockState spikeGapState;
	public final int spikeGapHeight;
	public final EdgeType edgeType;
	public final ExtendedWallsButtressFeatureConfig buttressConfig;
	
	public ExtendedWallsFeatureConfig(int pWallsCenterX, int pWallsCenterZ, int pWallsRadius, int pWallHeight, int pWallPortHeight, 
		float pWallThickness, BlockState pWallState, BlockState pSpikeState, int pSpikeHeight, BlockState pSpikeGapState, 
		int pSpikeGapHeight, int pEdgeType, ExtendedWallsButtressFeatureConfig pButtressConfig) {
		wallsCenterX = pWallsCenterX;
		wallsCenterZ = pWallsCenterZ;
		wallsRadius= pWallsRadius;
		wallHeight = pWallHeight;
		wallPortHeight = pWallPortHeight;
		wallThickness = pWallThickness;
		wallState = pWallState;
		spikeState = pSpikeState;
		spikeHeight = pSpikeHeight;
		spikeGapState = pSpikeGapState;
		spikeGapHeight = pSpikeGapHeight;
		edgeType = EdgeType.fromInt(pEdgeType);
		buttressConfig = pButtressConfig;
	}

	public ExtendedWallsFeatureConfig(int pWallsCenterX, int pWallsCenterZ, int pWallsRadius, int pWallHeight, int pWallPortHeight, 
		float pWallThickness, BlockState pWallState, BlockState pSpikeState, int pSpikeHeight, BlockState pSpikeGapState,
		int pSpikeGapHeight, EdgeType pEdgeType, ExtendedWallsButtressFeatureConfig pButtressConfig) {
		wallsCenterX = pWallsCenterX;
		wallsCenterZ = pWallsCenterZ;
		wallsRadius= pWallsRadius;
		wallHeight = pWallHeight;
		wallPortHeight = pWallPortHeight;
		wallThickness = pWallThickness;
		wallState = pWallState;
		spikeState = pSpikeState;
		spikeHeight = pSpikeHeight;
		spikeGapState = pSpikeGapState;
		spikeGapHeight = pSpikeGapHeight;
		edgeType = pEdgeType;
		buttressConfig = pButtressConfig;
	}
}