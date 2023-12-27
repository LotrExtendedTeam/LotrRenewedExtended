package com.github.maximuslotro.lotroExtended.mixins.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.ShireBiome;
import lotr.common.world.gen.feature.ExtendedFeatures;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;

@Mixin(ShireBiome.class)
public class MixinShireBiome {
//	@Inject(method = "addStructures(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;)V", at = @At("HEAD"), remap=false)
//	private void bootstrapExtendedFeatures(BiomeGenerationSettings.Builder builder, CallbackInfo ci) {
//		ExtendedBiomeFeatures.addStructure(builder, Decoration.SURFACE_STRUCTURES, 400, ExtendedFeatures.SHIRE_STRUCTURES.configured(new NoFeatureConfig()));
//	}
	
	/**
	 * @author Maximuslotro
	 * @reason Can't have crafting monuments spawning at same location
	 * */
	@Overwrite(remap = false)
	protected void addStructures(BiomeGenerationSettings.Builder builder) {
		ExtendedBiomeFeatures.addStructure(builder, Decoration.SURFACE_STRUCTURES, 50, ExtendedFeatures.SHIRE_STRUCTURES.configured(new NoFeatureConfig()));
//		LOTRBiomeFeatures.addCraftingMonument(builder,
//				(LOTRBlocks.HOBBIT_CRAFTING_TABLE.get()).defaultBlockState(),
//				(new WeightedBlockStateProvider()).add((LOTRBlocks.PINE_PLANKS.get()).defaultBlockState(), 1),
//				(new WeightedBlockStateProvider()).add((LOTRBlocks.PINE_FENCE.get()).defaultBlockState(), 1),
//				(new WeightedBlockStateProvider()).add(Blocks.LANTERN.defaultBlockState(), 1));
	}
}
