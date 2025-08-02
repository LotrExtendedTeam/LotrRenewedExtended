package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBookshelfBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.registry.Registry;

@Mixin(Blocks.class)
public class MixinBlocks {

	@SuppressWarnings("deprecation")
	@Inject(method = "register(Ljava/lang/String;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;", at = @At("HEAD"), cancellable = true)
	private static void register(String name, Block blockInstance, CallbackInfoReturnable<Block> clr) {
		if("bookshelf".equals(name)) {
			clr.setReturnValue(Registry.register(Registry.BLOCK, "bookshelf", new ExtendedBookshelfBlock(AbstractBlock.Properties.of(Material.WOOD).strength(1.5F).sound(SoundType.WOOD))));
		}
	}
}
