package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

public class ExtendedLandBlotFeatureConfig implements IFeatureConfig {
	public static final Codec<ExtendedLandBlotFeatureConfig> CODEC =
		RecordCodecBuilder.create(instance -> instance.group(
			FeatureSpread.codec(0, 16, 16).fieldOf("radius").forGetter(config -> config.radius),
			RuleTest.CODEC.fieldOf("replacing").forGetter(config -> config.canReplace),
			Codec.FLOAT.fieldOf("fire_range").forGetter(config -> config.replaceChance),
			BlockState.CODEC.fieldOf("placing").forGetter(config -> config.placementState),
			Codec.BOOL.fieldOf("fire").forGetter(config -> config.fire),
			Codec.INT.fieldOf("fire_chance").forGetter(config -> config.fireChance),
			Codec.FLOAT.fieldOf("fire_range").forGetter(config -> config.fireRange),
			Codec.BOOL.fieldOf("skull").forGetter(config -> config.skull),
			Codec.INT.fieldOf("skull_chance").forGetter(config -> config.skullChance)
			).apply(instance, ExtendedLandBlotFeatureConfig::new));

	public final FeatureSpread radius;
	public final RuleTest canReplace;
	public final float replaceChance;
	public final BlockState placementState;

	public final boolean fire;
	public final int fireChance;
	public final float fireRange;

	public final boolean skull;
	public final int skullChance;

	public ExtendedLandBlotFeatureConfig(FeatureSpread pRadius, RuleTest pCanReplace, float pReplaceChance, 
		BlockState pPlacementState, boolean pFire, int pFireChance, float pFireRange, boolean pSkull, int pSkullChance) {
		this.radius = pRadius;
		this.canReplace = pCanReplace;
		this.replaceChance = pReplaceChance;
		this.placementState = pPlacementState;
		this.fire = pFire;
		this.fireChance = pFireChance;
		this.fireRange = pFireRange;
		this.skull = pSkull;
		this.skullChance = pSkullChance;
	}
}