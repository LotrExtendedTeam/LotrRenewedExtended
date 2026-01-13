package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.BaseMordorBiome;
import lotr.common.world.biome.MordorBiome;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;

@Mixin(MordorBiome.class)
public abstract class MixinMordorBiome extends BaseMordorBiome{

	protected MixinMordorBiome(Builder builder, int water, boolean major) {
		super(builder, water, major);
	}

	@Override
	public Biome getRiver(IWorld world) {
		return LOTRBiomes.getBiomeByRegistryName(ExtendedBiomes.MORDOR_RIVER.getRegistryName(), world);
	}
}
