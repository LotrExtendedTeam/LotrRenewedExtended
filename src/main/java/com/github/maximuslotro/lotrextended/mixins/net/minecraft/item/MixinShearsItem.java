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

/**
 * A mixin targeting {@link ShearsItem} to extend its functionality.
 * <p>
 * This class modifies the default behavior of shears when interacting
 * with custom-defined shearable blocks. It introduces new block-breaking
 * and destruction speed logic to respect the {@code ExtendedTags.Blocks.SHEARABLE_BLOCKS}
 * tag, allowing more flexibility and customization in block interactions.
 */
@Mixin(ShearsItem.class)
public class MixinShearsItem {
	/**
     * Injects custom logic into {@link ShearsItem#mineBlock(ItemStack, World, BlockState, BlockPos, LivingEntity)}.
     * <p>
     * If the block being mined is part of {@code ExtendedTags.Blocks.SHEARABLE_BLOCKS},
     * the method ensures that shears can successfully mine it by setting the return value
     * to {@code true}.
     *
     * @param pStack        the item stack of the shears being used
     * @param pLevel        the world where the mining is taking place
     * @param pState        the current block state of the block being mined
     * @param pPos          the position of the block being mined
     * @param pEntityLiving the entity using the shears (typically a player)
     * @param ci            callback information to set the method’s return value
     */
	@Inject(method = "mineBlock(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/LivingEntity;)Z", 
		at = @At("TAIL"), cancellable = true)
	public void mineBlock$Extended(ItemStack pStack, World pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving, CallbackInfoReturnable<Boolean> ci) {
		if(pState.is(ExtendedTags.Blocks.SHEARABLE_BLOCKS)) {
			ci.setReturnValue(true);
		}
	}
	/**
     * Injects custom logic into {@link ShearsItem#getDestroySpeed(ItemStack, BlockState)}.
     * <p>
     * If the block is tagged as {@code ExtendedTags.Blocks.SHEARABLE_BLOCKS},
     * the destruction speed of the shears is overridden to a fixed value of {@code 5.0f}.
     *
     * @param pStack the item stack of the shears being used
     * @param pState the block state being tested for destruction speed
     * @param ci     callback information to set the method’s return value
     */
	@Inject(method = "getDestroySpeed(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/BlockState;)F", 
		at = @At("HEAD"), cancellable = true)
	public void getDestroySpeed$Extended(ItemStack pStack, BlockState pState, CallbackInfoReturnable<Float> ci) {
		if(pState.is(ExtendedTags.Blocks.SHEARABLE_BLOCKS)) {
			ci.setReturnValue(5.0f);
		}
	}
}
