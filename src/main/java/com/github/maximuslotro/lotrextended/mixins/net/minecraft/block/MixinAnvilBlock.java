package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainer;

import lotr.common.init.LOTRDimensions;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Mixin class that injects additional functionality into {@link AnvilBlock}.
 * <p>
 * Extends the vanilla anvil's behavior by overriding its menu provider logic 
 * when placed in mod-specific dimensions. This allows the anvil to open a 
 * custom repair container instead of the default one.
 * </p>
 * @Mixin (AnvilBlock.class)
 */
@Mixin(AnvilBlock.class)
public class MixinAnvilBlock {

	/**
	 * Injected method that modifies the behavior of {@link AnvilBlock#getMenuProvider}.
	 * <p>
	 * If the world dimension is a mod-defined dimension (checked via 
	 * {@link LOTRDimensions#isModDimension(World)}), the anvil opens an 
	 * {@link ExtendedRepairContainer} instead of the vanilla repair container.
	 * </p>
	 *
	 * @param pState The current block state of the anvil being interacted with.
	 * @param pLevel The world in which the anvil exists.
	 * @param pPos   The position of the anvil in the world.
	 * @param cir    The callback information with the returnable 
	 *               {@link INamedContainerProvider}. This is modified to 
	 *               supply the custom container if conditions are met.
	 *
	 * @see AnvilBlock#getMenuProvider(BlockState, World, BlockPos)
	 * @see ExtendedRepairContainer
	 * @Inject(method = "getMenuProvider(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/inventory/container/INamedContainerProvider;", at = @At("TAIL"), cancellable = true)
	 */
	@Inject(method = "getMenuProvider(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/inventory/container/INamedContainerProvider;", 
		at = @At("TAIL"), cancellable = true)
	public void getMenuProvider$Extended(BlockState pState, World pLevel, BlockPos pPos, CallbackInfoReturnable<INamedContainerProvider> cir) {
		if(LOTRDimensions.isModDimension(pLevel)) {
			cir.setReturnValue(new SimpleNamedContainerProvider((p_220272_2_, p_220272_3_, p_220272_4_) -> {
				return new ExtendedRepairContainer(p_220272_2_, p_220272_3_, IWorldPosCallable.create(pLevel, pPos));
			}, ExtendedRepairContainer.CONTAINER_TITLE));
		}
	}
}
