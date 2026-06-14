package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedGrindstoneContainer;

import lotr.common.init.LOTRDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(GrindstoneBlock.class)
public class MixinGrindstoneBlock {

	/**
	 * Injected method that modifies the behavior of {@link SmithingTableBlock#getMenuProvider}.
	 * <p>
	 * If the world dimension is a mod-defined dimension (checked via 
	 * {@link LOTRDimensions#isModDimension(World)}), the grindstone opens an 
	 * {@link ExtendedGrindstoneContainer} instead of the vanilla smithing container.
	 * </p>
	 *
	 * @param pState The current block state of the grindstone being interacted with.
	 * @param pLevel The world in which the smithing table exists.
	 * @param pPos   The position of the smithing table in the world.
	 * @param cir    The callback information with the returnable 
	 *               {@link INamedContainerProvider}. This is modified to 
	 *               supply the custom container if conditions are met.
	 *
	 * @see SmithingTableBlock#getMenuProvider(BlockState, World, BlockPos)
	 * @see ExtendedGrindstoneContainer
	 * @Inject(method = "getMenuProvider(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/inventory/container/INamedContainerProvider;", at = @At("TAIL"), cancellable = true)
	 */
	@Inject(method = "getMenuProvider(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/inventory/container/INamedContainerProvider;", 
		at = @At("TAIL"), cancellable = true)
	public void getMenuProvider$Extended(BlockState pState, World pLevel, BlockPos pPos, CallbackInfoReturnable<INamedContainerProvider> cir) {
		cir.setReturnValue(new SimpleNamedContainerProvider((p_220272_2_, p_220272_3_, p_220272_4_) -> {
			return new ExtendedGrindstoneContainer(p_220272_2_, p_220272_3_, IWorldPosCallable.create(pLevel, pPos));
		}, ExtendedGrindstoneContainer.CONTAINER_TITLE));
	}
}
