package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

/**
 * Modified version of {@link OreFeatureConfig}
 * Adds support for slab/stair rule tests for stone replacing
 */
public class ExtendedBlobFeatureConfig implements IFeatureConfig {
	public static final Codec<ExtendedBlobFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
		return p_236568_0_.group(RuleTest.CODEC.fieldOf("target_block").forGetter((p_236570_0_) -> {
			return p_236570_0_.targetBlock;
		}), RuleTest.CODEC.fieldOf("target_slab").forGetter((p_236570_0_) -> {
			return p_236570_0_.targetSlab;
		}), RuleTest.CODEC.fieldOf("target_stair").forGetter((p_236570_0_) -> {
			return p_236570_0_.targetStair;
		}), BlockState.CODEC.fieldOf("state_block").forGetter((p_236569_0_) -> {
			return p_236569_0_.stateBlock;
		}), BlockState.CODEC.fieldOf("state_slab").forGetter((p_236569_0_) -> {
			return p_236569_0_.stateSlab;
		}), BlockState.CODEC.fieldOf("state_stair").forGetter((p_236569_0_) -> {
			return p_236569_0_.stateStair;
		}), Codec.intRange(0, 64).fieldOf("size").forGetter((p_236567_0_) -> {
			return p_236567_0_.size;
		})).apply(p_236568_0_, ExtendedBlobFeatureConfig::new);
	});
	public final RuleTest targetBlock;
	public final RuleTest targetSlab;
	public final RuleTest targetStair;
	public final BlockState stateBlock;
	public final BlockState stateSlab;
	public final BlockState stateStair;
	public final int size;

	public ExtendedBlobFeatureConfig(RuleTest pTargetBlock, RuleTest pTargetSlab, RuleTest pTargetStair, BlockState pStateBlock, BlockState pStateSlab, BlockState pStateStair, int pSize) {
		this.targetBlock = pTargetBlock;
		this.targetSlab = pTargetSlab;
		this.targetStair = pTargetStair;
		this.stateBlock = pStateBlock;
		this.stateSlab = pStateSlab;
		this.stateStair = pStateStair;
		this.size = pSize;
	}

	public static final class ExtendedFillerBlockType {
		public static final RuleTest NATURAL_STONE_MIDDLE_EARTH = new TagMatchRuleTest(ExtendedTags.Blocks.BASE_STONE_MIDDLE_EARTH);
		public static final RuleTest NATURAL_STONE_SLAB_MIDDLE_EARTH = new TagMatchRuleTest(ExtendedTags.Blocks.BASE_STONE_SLAB_MIDDLE_EARTH);
		public static final RuleTest NATURAL_STONE_STAIRS_MIDDLE_EARTH = new TagMatchRuleTest(ExtendedTags.Blocks.BASE_STONE_STAIRS_MIDDLE_EARTH);
	}
}