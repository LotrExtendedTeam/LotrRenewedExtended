package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LoneLandsBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LoneLandsBiome.class)
public class MixinLoneLandsBiome {

    /**
     * @author richardb122
     * @reason don't want to replace class
     * @return An array containing tree features followed by their spawn weights.
     * */
    @Overwrite(remap = false)
    protected final Object[] loneLandsTrees() {
        return new Object[]{
                ExtendedBiomeFeatures.willowTree(), 8000,
                LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 3000,
                LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 3,
                LOTRBiomeFeatures.spruce(), 3000, LOTRBiomeFeatures.beech(), 1000,
                LOTRBiomeFeatures.beechFancy(), 500, LOTRBiomeFeatures.beechBees(), 1,
                LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.birch(), 100,
                LOTRBiomeFeatures.birchFancy(), 50, LOTRBiomeFeatures.birchBees(), 1,
                LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.maple(), 50,
                LOTRBiomeFeatures.mapleFancy(), 5, LOTRBiomeFeatures.mapleBees(), 1,
                LOTRBiomeFeatures.mapleFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500,
                LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.apple(), 10,
                LOTRBiomeFeatures.pear(), 10, LOTRBiomeFeatures.appleBees(), 1,
                LOTRBiomeFeatures.pearBees(), 1
        };

    }

}
