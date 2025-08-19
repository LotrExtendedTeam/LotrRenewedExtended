package com.github.maximuslotro.lotrextended.mixins.net.minecraft.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ShearsItem.class)
public class MixinShearsItem {

	@Inject(method = "mineBlock(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/LivingEntity;)Z", at = @At("TAIL"), cancellable = true)
	public void extendedInjectShearable(ItemStack pStack, World pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving, CallbackInfoReturnable<Boolean> ci) {
		if(pState.is(ExtendedTags.Blocks.SHEARABLE_BLOCKS)) {
			ci.setReturnValue(true);
		}
	}
}
