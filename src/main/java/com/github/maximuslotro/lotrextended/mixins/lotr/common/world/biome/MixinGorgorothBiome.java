package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.BaseMordorBiome;
import lotr.common.world.biome.GorgorothBiome;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;

@Mixin(GorgorothBiome.class)
public abstract class MixinGorgorothBiome extends BaseMordorBiome{

	protected MixinGorgorothBiome(Builder builder, int water, boolean major) {
		super(builder, water, major);
	}

	@Override
	public Biome getRiver(IWorld world) {
		return LOTRBiomes.getBiomeByRegistryName(ExtendedBiomes.GORGOROTH_RIVER.getRegistryName(), world);
	}
}
