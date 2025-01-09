package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.MirkwoodBiome.Northern;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(Northern.class)
public class MixinMirkwoodNorthernBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((Northern)(Object)this, builder, 14, 0.1F,
				LOTRBiomeFeatures.greenOak(), 500, LOTRBiomeFeatures.greenOakBees(), 5,
				LOTRBiomeFeatures.greenOakParty(), 100, LOTRBiomeFeatures.greenOakShrub(), 500,
				LOTRBiomeFeatures.mirkOak(), 50, LOTRBiomeFeatures.mirkOakShrub(), 50,
				LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakBees(), 5,
				LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakFancyBees(), 5,
				LOTRBiomeFeatures.oakParty(), 100, LOTRBiomeFeatures.oakShrub(), 1000,
				LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.spruceThin(), 500,
				LOTRBiomeFeatures.spruceMega(), 200, LOTRBiomeFeatures.spruceThinMega(), 200,
				ExtendedBiomeFeatures.chestnut(), 200, ExtendedBiomeFeatures.chestnutFancy(), 500,
				ExtendedBiomeFeatures.chestnutBees(), 2, ExtendedBiomeFeatures.chestnutFancyBees(), 5,
				LOTRBiomeFeatures.larch(), 500, LOTRBiomeFeatures.fir(), 2000,
				LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 200,
				LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100);
		LOTRBiomeFeatures.addGrass((Northern)(Object)this, builder, 12, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 2);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
		LOTRBiomeFeatures.addFoxBerryBushes(builder);
	}
}
