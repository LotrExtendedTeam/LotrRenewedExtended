package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.OldForestBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.BiomeGenerationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(OldForestBiome.class)
public class MixinOldForestBiome {

    @Overwrite(remap = false)
    protected void addVegetation(BiomeGenerationSettings.Builder builder) {
        Object[] weightedTrees = new Object[]{
                ExtendedBiomeFeatures.willowTree(), 8000,
                LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakVines(), 1000,
                LOTRBiomeFeatures.oakTall(), 6000, LOTRBiomeFeatures.oakTallVines(), 2000,
                LOTRBiomeFeatures.oakFancy(), 4000, LOTRBiomeFeatures.oakBees(), 3,
                LOTRBiomeFeatures.oakBeesVines(), 1, LOTRBiomeFeatures.oakTallBees(), 6,
                LOTRBiomeFeatures.oakTallBeesVines(), 2, LOTRBiomeFeatures.oakFancyBees(), 4,
                LOTRBiomeFeatures.oakDead(), 1000, LOTRBiomeFeatures.oakParty(), 300,
                LOTRBiomeFeatures.oakShrub(), 2000, LOTRBiomeFeatures.darkOak(), 8000,
                LOTRBiomeFeatures.darkOakParty(), 500, LOTRBiomeFeatures.darkOakShrub(), 1000,
                LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.firShrub(), 200,
                LOTRBiomeFeatures.shirePine(), 1000, LOTRBiomeFeatures.pineShrub(), 100
        };
        LOTRBiomeFeatures.addTrees((OldForestBiome) (Object) this, builder, 18, 0.0F, weightedTrees);
        LOTRBiomeFeatures.addGrass((OldForestBiome) (Object) this, builder, 12, GrassBlends.WITH_FERNS);
        LOTRBiomeFeatures.addDoubleGrass(builder, 5, GrassBlends.DOUBLE_WITH_FERNS);
        LOTRBiomeFeatures.addForestFlowers(builder, 1, new Object[0]);
        LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
        LOTRBiomeFeatures.addFallenLogs(builder, 1);
    }
}
