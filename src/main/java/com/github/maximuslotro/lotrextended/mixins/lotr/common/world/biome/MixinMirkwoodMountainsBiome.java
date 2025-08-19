package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.MirkwoodBiome.Mountains;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(Mountains.class)
public class MixinMirkwoodMountainsBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		int treeline = 100;
		LOTRBiomeFeatures.addTreesBelowTreeline((Mountains)(Object)this, builder, 3, 0.25F, treeline, 
			ExtendedBiomeFeatures.willowTree(), 250,
			LOTRBiomeFeatures.mirkOak(), 200, LOTRBiomeFeatures.spruce(), 300, 
			LOTRBiomeFeatures.fir(), 1000, LOTRBiomeFeatures.pine(), 300, 
			LOTRBiomeFeatures.pineDead(), 50);
		LOTRBiomeFeatures.addTreesBelowTreeline((Mountains)(Object)this, builder, 1, 0.25F, treeline + 10, 
			ExtendedBiomeFeatures.willowTree(), 250,
			LOTRBiomeFeatures.mirkOakShrub(), 200, LOTRBiomeFeatures.spruceShrub(), 300, 
			LOTRBiomeFeatures.firShrub(), 1000, LOTRBiomeFeatures.pineShrub(), 300);
		LOTRBiomeFeatures.addGrass((Mountains)(Object)this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
		LOTRBiomeFeatures.addMirkShroomsFreq(builder, 1);
		LOTRBiomeFeatures.addFallenLogsBelowTreeline(builder, 1, treeline);
	}
}
