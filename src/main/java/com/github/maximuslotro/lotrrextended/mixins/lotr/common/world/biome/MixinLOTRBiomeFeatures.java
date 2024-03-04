package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;

@Mixin(LOTRBiomeFeatures.class)
public class MixinLOTRBiomeFeatures {

	/**
	 * @reason Removing crafting pyramids from finished biomes
	 * @author maximuslotro
	 */
	@Inject(method = "addCraftingMonument(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;ILnet/minecraft/block/BlockState;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;)V", at = @At("HEAD"), cancellable = true, remap = false)
	private static void addCraftingMonumentInject(BiomeGenerationSettings.Builder builder, int relativeChance, BlockState table, BlockStateProvider baseProvider, BlockStateProvider postProvider, BlockStateProvider torchProvider, CallbackInfo info) {
		if(table.getBlock()==LOTRBlocks.HOBBIT_CRAFTING_TABLE.get()) {
			info.cancel();
		}
	}
}
