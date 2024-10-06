package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExtendedSimpleBlockFeatureConfig implements IFeatureConfig {
	public static final Codec<ExtendedSimpleBlockFeatureConfig> CODEC = BlockStateProvider.CODEC.fieldOf("state").xmap(ExtendedSimpleBlockFeatureConfig::new, (p_236456_0_) -> {
		return p_236456_0_.state;
	}).codec();
	public final BlockStateProvider state;

	public ExtendedSimpleBlockFeatureConfig(BlockStateProvider p_i225831_1_) {
		this.state = p_i225831_1_;
	}
}