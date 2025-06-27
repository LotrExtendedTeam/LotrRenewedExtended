package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.MirkwoodBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(MirkwoodBiome.class)
public class MixinMirkwoodBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((MirkwoodBiome)(Object)this, builder, 14, 0.0F, 
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.mirkOak(), 8000, LOTRBiomeFeatures.mirkOakParty(), 2000, 
			LOTRBiomeFeatures.mirkOakShrub(), 6000, LOTRBiomeFeatures.oakFancy(), 3000, 
			LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.fir(), 1000, 
			LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 200);
		LOTRBiomeFeatures.addGrass((MirkwoodBiome)(Object)this, builder, 12, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
		LOTRBiomeFeatures.addMirkShroomsFreq(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 2);
	}
}
