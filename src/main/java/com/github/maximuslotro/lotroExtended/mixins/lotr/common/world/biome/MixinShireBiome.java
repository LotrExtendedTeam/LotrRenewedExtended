package com.github.maximuslotro.lotroExtended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ShireBiome;
import net.minecraft.world.biome.BiomeGenerationSettings;

@Mixin(ShireBiome.class)
public class MixinShireBiome {
	/**
	 * @author Maximuslotro
	 * @reason Remove crafting monuments
	 * */
	@Overwrite(remap = false)
	protected void addStructures(BiomeGenerationSettings.Builder builder) {}
}
