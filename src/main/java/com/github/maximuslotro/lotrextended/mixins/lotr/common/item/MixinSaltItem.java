package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import lotr.common.init.ExtendedBlocks;
import lotr.common.item.SaltItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUseContext;

@Mixin(SaltItem.class)
public class MixinSaltItem {
	@Redirect(method = "useOn(Lnet/minecraft/item/ItemUseContext;)Lnet/minecraft/util/ActionResultType;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;defaultBlockState()Lnet/minecraft/block/BlockState;"))
	private BlockState modifyNewBlock(Block original, ItemUseContext context) {
		if(original==Blocks.COARSE_DIRT) {
			if (context.getLevel().getRandom().nextBoolean()) { 
                return ExtendedBlocks.BARREN_DIRT.get().defaultBlockState();
            }
		}
		return original.defaultBlockState();
	}
}