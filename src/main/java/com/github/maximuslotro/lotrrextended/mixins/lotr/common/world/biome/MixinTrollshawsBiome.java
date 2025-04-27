package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.TrollshawsBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.BiomeGenerationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TrollshawsBiome.class)
public class MixinTrollshawsBiome {

    @Overwrite(remap = false)
    protected void addVegetation(BiomeGenerationSettings.Builder builder) {
        Object[] weightedTrees = new Object[]{
                ExtendedBiomeFeatures.willowTree(), 8000,
                LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakTall(), 5000,
                LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakBees(), 10,
                LOTRBiomeFeatures.oakTallBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 2,
                LOTRBiomeFeatures.beech(), 5000, LOTRBiomeFeatures.beechFancy(), 2000,
                LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
                LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.fir(), 1000,
                LOTRBiomeFeatures.pine(), 1000, LOTRBiomeFeatures.pineDead(), 50,
                LOTRBiomeFeatures.maple(), 500, LOTRBiomeFeatures.mapleFancy(), 200,
                LOTRBiomeFeatures.mapleBees(), 10, LOTRBiomeFeatures.mapleFancyBees(), 10,
                LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 200
        };
        LOTRBiomeFeatures.addTreesWithClusters((TrollshawsBiome) (Object) this, builder, 0, 0.4F, TreeCluster.of(6, 10), weightedTrees);
        LOTRBiomeFeatures.addTreesBelowTreeline((TrollshawsBiome) (Object) this, builder, 10, 0.0F, 82, weightedTrees);
        LOTRBiomeFeatures.addGrass((TrollshawsBiome) (Object) this, builder, 6, GrassBlends.WITH_FERNS);
        LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_WITH_FERNS);
        LOTRBiomeFeatures.addForestFlowers(builder, 3, new Object[0]);
        LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 3, new Object[0]);
        LOTRBiomeFeatures.addAthelasChance(builder);
        LOTRBiomeFeatures.addFoxBerryBushes(builder);
    }
}
