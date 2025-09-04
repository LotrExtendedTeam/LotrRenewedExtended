package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedSmithingTableContainer;

import lotr.common.init.LOTRDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(SmithingTableBlock.class)
public class MixinSmithingTableBlock {

	/**
	 * Injected method that modifies the behavior of {@link SmithingTableBlock#getMenuProvider}.
	 * <p>
	 * If the world dimension is a mod-defined dimension (checked via 
	 * {@link LOTRDimensions#isModDimension(World)}), the smithing table opens an 
	 * {@link ExtendedRepairContainer} instead of the vanilla smithing container.
	 * </p>
	 *
	 * @param pState The current block state of the smithing table being interacted with.
	 * @param pLevel The world in which the smithing table exists.
	 * @param pPos   The position of the smithing table in the world.
	 * @param cir    The callback information with the returnable 
	 *               {@link INamedContainerProvider}. This is modified to 
	 *               supply the custom container if conditions are met.
	 *
	 * @see SmithingTableBlock#getMenuProvider(BlockState, World, BlockPos)
	 * @see ExtendedSmithingTableContainer
	 * @Inject(method = "getMenuProvider(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/inventory/container/INamedContainerProvider;", at = @At("TAIL"), cancellable = true)
	 */
	@Inject(method = "getMenuProvider(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/inventory/container/INamedContainerProvider;", 
		at = @At("TAIL"), cancellable = true)
	public void getMenuProvider$Extended(BlockState pState, World pLevel, BlockPos pPos, CallbackInfoReturnable<INamedContainerProvider> cir) {
		if(LOTRDimensions.isModDimension(pLevel)) {
			cir.setReturnValue(new SimpleNamedContainerProvider((p_220272_2_, p_220272_3_, p_220272_4_) -> {
				return new ExtendedSmithingTableContainer(p_220272_2_, p_220272_3_, IWorldPosCallable.create(pLevel, pPos));
			}, ExtendedSmithingTableContainer.CONTAINER_TITLE));
		}
	}
}
