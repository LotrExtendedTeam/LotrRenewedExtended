package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Random;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.world.gen.placement.ExtendedCaveSurface;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExtendedVegetationPatchConfig implements IFeatureConfig {
	public static final Codec<ExtendedVegetationPatchConfig> CODEC = RecordCodecBuilder.create((p_161304_) -> {
		return p_161304_.group(ITag.codec(() -> {
			return TagCollectionManager.getInstance().getBlocks();
		}).fieldOf("tag").forGetter((p_204869_) -> {
			return p_204869_.replaceable;
		}), BlockStateProvider.CODEC.fieldOf("ground_state").forGetter((p_161322_) -> {
			return p_161322_.groundState;
		}), ConfiguredFeature.CODEC.fieldOf("vegetation_feature").forGetter((p_236494_0_) -> {
			return p_236494_0_.vegetationFeature;
		}), ExtendedCaveSurface.CODEC.fieldOf("surface").forGetter((p_161318_) -> {
			return p_161318_.surface;
		}), Codec.intRange(1, 4).fieldOf("depth").forGetter((p_161316_) -> {
			return p_161316_.depth;
		}), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter((p_161314_) -> {
			return p_161314_.extraBottomBlockChance;
		}), Codec.intRange(1, 256).fieldOf("vertical_range").forGetter((p_161312_) -> {
			return p_161312_.verticalRange;
		}), Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter((p_161310_) -> {
			return p_161310_.vegetationChance;
		}), Codec.intRange(1, 256).fieldOf("xz_radius_min").forGetter((p_161312_) -> {
			return p_161312_.xzRadiusMin;
		}), Codec.intRange(1, 256).fieldOf("xz_radius_max").forGetter((p_161312_) -> {
			return p_161312_.xzRadiusMax;
		}), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((p_161306_) -> {
			return p_161306_.extraEdgeColumnChance;
		}), Codec.floatRange(0.0F, 1.0F).fieldOf("discard_block_chance").forGetter((p_161306_) -> {
			return p_161306_.discardBlockChance;
		})).apply(p_161304_, ExtendedVegetationPatchConfig::new);
	});
	public final ITag<Block> replaceable;
	public final BlockStateProvider groundState;
	public final Supplier<ConfiguredFeature<?, ?>> vegetationFeature;
	public final ExtendedCaveSurface surface;
	public final int depth;
	public final float extraBottomBlockChance;
	public final int verticalRange;
	public final float vegetationChance;
	public final int xzRadiusMin;
	public final int xzRadiusMax;
	public final float extraEdgeColumnChance;
	public final float discardBlockChance;

	public ExtendedVegetationPatchConfig(ITag<Block> replaceable, BlockStateProvider groundState, Supplier<ConfiguredFeature<?, ?>> vegetationFeature, ExtendedCaveSurface surface, int depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, int xzRadiusMin, int xzRadiusMax, float extraEdgeColumnChance, float discardBlockChance) {
		this.replaceable = replaceable;
		this.groundState = groundState;
		this.vegetationFeature = vegetationFeature;
		this.surface = surface;
		this.depth = depth;
		this.extraBottomBlockChance = extraBottomBlockChance;
		this.verticalRange = verticalRange;
		this.vegetationChance = vegetationChance;
		this.xzRadiusMin = xzRadiusMin;
		this.xzRadiusMax = xzRadiusMax;
		this.extraEdgeColumnChance = extraEdgeColumnChance;
		this.discardBlockChance = discardBlockChance;
	}

	public int samplexzRadius(Random random) {
		return random.nextInt(xzRadiusMax - xzRadiusMin + 1) + xzRadiusMin;
	}

	public boolean shouldUseBlock(Random random) {
		return discardBlockChance==0.0f ? true : random.nextFloat()>discardBlockChance;
	}
}