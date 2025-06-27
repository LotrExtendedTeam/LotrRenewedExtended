package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import lotr.common.world.biome.ColdfellsBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.BiomeGenerationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ColdfellsBiome.class)
public class MixinColdfellsBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
    protected void addVegetation(BiomeGenerationSettings.Builder builder) {
        LOTRBiomeFeatures.addTrees((ColdfellsBiome) (Object) this, builder, 1, 0.5F,
                ExtendedBiomeFeatures.willowTree(), 2500,
                LOTRBiomeFeatures.fir(), 5000, LOTRBiomeFeatures.pine(), 5000,
                LOTRBiomeFeatures.pineDead(), 500, LOTRBiomeFeatures.spruce(), 4000,
                LOTRBiomeFeatures.spruceThin(), 2000, LOTRBiomeFeatures.spruceDead(), 600,
                LOTRBiomeFeatures.oak(), 2000, LOTRBiomeFeatures.oakFancy(), 300,
                LOTRBiomeFeatures.oakBees(), 2, LOTRBiomeFeatures.oakFancyBees(), 1,
                LOTRBiomeFeatures.larch(), 3000, LOTRBiomeFeatures.maple(), 500,
                LOTRBiomeFeatures.mapleFancy(), 50, LOTRBiomeFeatures.mapleBees(), 1,
                LOTRBiomeFeatures.mapleFancyBees(), 1);
        LOTRBiomeFeatures.addGrass((ColdfellsBiome) (Object) this, builder, 5, GrassBlends.MOORS);
        LOTRBiomeFeatures.addMountainsFlowers(builder, 2, new Object[0]);
        LOTRBiomeFeatures.addAthelasChance(builder);
    }

}
