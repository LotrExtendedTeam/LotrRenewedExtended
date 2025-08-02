package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.SeaBiome;
import lotr.common.world.biome.SeaBiome.WesternIsles;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;

@Mixin(WesternIsles.class)
public abstract class MixinWesternIsles extends SeaBiome {


	public MixinWesternIsles(boolean major) {
		super(major);
	}

	@Override
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		super.addVegetation(builder);
		LOTRBiomeFeatures.addGrass(this, builder, 1, GrassBlends.MOORS);
		LOTRBiomeFeatures.addGrass(this, builder, 1, GrassBlends.EXTRA_WHEATGRASS);
	}

	@Override
	protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		super.addBoulders(builder);
		LOTRBiomeFeatures.addBoulders(builder, Blocks.STONE.defaultBlockState(), 1, 2, 4, 5);
		LOTRBiomeFeatures.addBoulders(builder, Blocks.STONE.defaultBlockState(), 1, 2, 7, 4);
		LOTRBiomeFeatures.addBoulders(builder, Blocks.STONE.defaultBlockState(), 3, 6, 45, 2);
	}
}
