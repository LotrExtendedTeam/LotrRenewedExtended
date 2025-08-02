package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Mixin targeting {@link LOTRBiomeFeatures} to modify biome feature behavior
 * without replacing the original class.
 * 
 * <p>This mixin removes certain crafting pyramids from biome generation,
 * and injects custom biome features such as ores and extended biome setups
 * after vanilla registrations.</p>
 * 
 * @author maximuslotro
 * @Mixin (LOTRBiomeFeatures.class)
 */
@Mixin(LOTRBiomeFeatures.class)
public class MixinLOTRBiomeFeatures {

	/**
     * Cancels the addition of crafting monuments for specific crafting tables
     * (Hobbit, Ranger, Bree, Blue Mountains) during biome generation.
     * 
     * <p>This prevents crafting pyramids from spawning in finished biomes.</p>
     * 
     * @author maximuslotro
     * @param builder Biome generation settings builder.
     * @param relativeChance Chance parameter for monument placement.
     * @param table The crafting table block state.
     * @param baseProvider Block state provider for base blocks.
     * @param postProvider Block state provider for post blocks.
     * @param torchProvider Block state provider for torches.
     * @param info Callback info used to cancel the method execution.
     * @Inject (method = "addCraftingMonument(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;ILnet/minecraft/block/BlockState;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;)V", at = @At("HEAD"), cancellable = true, remap = false)
     */
	@Inject(method = "addCraftingMonument(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;ILnet/minecraft/block/BlockState;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;Lnet/minecraft/world/gen/blockstateprovider/BlockStateProvider;)V", at = @At("HEAD"), cancellable = true, remap = false)
	private static void addCraftingMonumentInject(BiomeGenerationSettings.Builder builder, int relativeChance, BlockState table, BlockStateProvider baseProvider, BlockStateProvider postProvider, BlockStateProvider torchProvider, CallbackInfo info) {
		if(table.getBlock()==LOTRBlocks.HOBBIT_CRAFTING_TABLE.get()) {
			info.cancel();
		}
		if(table.getBlock()==LOTRBlocks.RANGER_CRAFTING_TABLE.get()) {
			info.cancel();
		}
		if(table.getBlock()==LOTRBlocks.BREE_CRAFTING_TABLE.get()) {
			info.cancel();
		}
		if(table.getBlock()==LOTRBlocks.BLUE_MOUNTAINS_CRAFTING_TABLE.get()) {
			info.cancel();
		}
	}
	/**
     * Injected at the end of the {@code setup} method to add extended biome feature
     * setup after vanilla block registry events.
     *
     * @param event The block registry event.
     * @param info Callback info.
     * @Inject (method = "setup(Lnet/minecraftforge/event/RegistryEvent$Register;)V", at = @At("TAIL"), remap = false)
     */
	@Inject(method = "setup(Lnet/minecraftforge/event/RegistryEvent$Register;)V", at = @At("TAIL"), remap = false)
	private static void setup(RegistryEvent.Register<Block> event, CallbackInfo info) {
		if (event.getRegistry() == ForgeRegistries.BLOCKS) {
			ExtendedBiomeFeatures.setup(event);
		}
	}
	/**
     * Injected at the end of the {@code addOres} method to append custom ore
     * generation features to biomes.
     *
     * @param builder The biome generation settings builder.
     * @param info Callback info.
     * @Inject (method = "addOres(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;)V", at = @At("TAIL"), remap = false)
     */
	@Inject(method = "addOres(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;)V", at = @At("TAIL"), remap = false)
	private static void addOres(BiomeGenerationSettings.Builder builder, CallbackInfo info) {
		ExtendedBiomeFeatures.addOres(builder);
	}
}
