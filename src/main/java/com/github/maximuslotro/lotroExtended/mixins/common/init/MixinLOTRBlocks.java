package com.github.maximuslotro.lotroExtended.mixins.common.init;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.block.MineralBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;

@Mixin(LOTRBlocks.class)
public class MixinLOTRBlocks {
	
	@Final
	@Shadow(remap=false)
	public static DeferredRegister<Block> BLOCKS;
	
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedBlocks.GALVORN_BLOCK = BLOCKS.register("galvorn_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(4.0F, 8.0F).sound(SoundType.METAL), 2);});
		ExtendedBlocks.BLACK_URUK_STEEL_BLOCK = BLOCKS.register("black_uruk_steel_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_BLUE).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
		ExtendedBlocks.GILDED_IRON_BLOCK = BLOCKS.register("gilded_iron_block", () ->{
			return new MineralBlock(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(4.0F, 6.0F).sound(SoundType.METAL), 1);});
	}
}
