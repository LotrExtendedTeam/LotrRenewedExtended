package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.MidgewaterBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.BiomeGenerationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MidgewaterBiome.class)
public class MixinMidgewaterBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
    protected void addVegetation(BiomeGenerationSettings.Builder builder) {
        Object[] weightedTrees = new Object[]{
                ExtendedBiomeFeatures.willowTree(), 250,
                LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100,
                LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 2500,
                LOTRBiomeFeatures.oakDead(), 500
        };
        LOTRBiomeFeatures.addTreesBelowTreeline((MidgewaterBiome) (Object) this, builder, 2, 0.5F, 63, weightedTrees);
        LOTRBiomeFeatures.addTreesAboveTreeline((MidgewaterBiome) (Object) this, builder, 3, 0.5F, 64, weightedTrees);
        LOTRBiomeFeatures.addGrass((MidgewaterBiome) (Object) this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
        LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
        LOTRBiomeFeatures.addSwampFlowers(builder, 1, new Object[0]);
        LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
        LOTRBiomeFeatures.addWaterLilies(builder, 5);
        LOTRBiomeFeatures.addSwampSeagrass(builder);
        LOTRBiomeFeatures.addFallenLogs(builder, 3);
        LOTRBiomeFeatures.addAthelasChance(builder);
    }
}
