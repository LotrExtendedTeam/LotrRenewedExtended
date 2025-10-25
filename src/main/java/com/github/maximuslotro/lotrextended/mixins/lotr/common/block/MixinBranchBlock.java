package com.github.maximuslotro.lotrextended.mixins.lotr.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.block.BranchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;

/**
 * A mixin that modifies {@link BranchBlock} behavior to extend its connection logic.
 * <p>
 * This class intercepts the {@code doesBranchConnect} method of {@link BranchBlock}
 * to include additional connection types, such as {@link PaneBlock} (window panes or bars),
 * allowing branches to connect visually and logically to those blocks.
 * @Mixin (BranchBlock.class)
 */
@Mixin(BranchBlock.class)
public abstract class MixinBranchBlock extends FourWayBlock{
	/**
	 * Required synthetic constructor for mixins extending a concrete superclass.
	 * <p>
	 * This constructor exists only to satisfy the Java compiler and class hierarchy
	 * during compilation. It is <strong>not</strong> used at runtime — Mixin will
	 * strip it when applying the transformation.
	 * </p>
	 *
	 * @param p1 Unknown variable
	 * @param p2 Unknown variable
	 * @param p3 Unknown variable
	 * @param p4 Unknown variable
	 * @param p5 Unknown variable
	 * @param pProps  A placeholder Properties instance required by the superclass.
	 */
	public MixinBranchBlock(float p1, float p2, float p3, float p4, float p5, Properties pProps) {
		super(p1, p2, p3, p3, p5, pProps);
	}

	/**
     * Injects at the start of {@code doesBranchConnect} to override the default connection logic.
     * <p>
     * This modified logic adds additional checks for connection compatibility with
     * pane blocks and ensures branches properly attach to a wider range of block types.
     * </p>
     *
     * @param state          The current {@link BlockState} being checked for connection.
     * @param isSolidSide    Whether the adjacent face is solid and supports connection.
     * @param oppositeFace   The direction of the face being evaluated.
     * @param cir            The {@link CallbackInfoReturnable} used to override the method’s return value.
     * @Inject (method = "doesBranchConnect", at = @At("HEAD"), remap=false, cancellable = true)
     */
    @Inject(method = "doesBranchConnect", at = @At("HEAD"), remap=false, cancellable = true)
    private void injectDoesBranchConnect(BlockState state, boolean isSolidSide, Direction oppositeFace, CallbackInfoReturnable<Boolean> cir) {
        Block block = state.getBlock();
        boolean isOtherBranch = block.is(lotr.common.init.LOTRTags.Blocks.BRANCHES);
        boolean isLeaves = block.is(BlockTags.LEAVES);
        boolean isWoodenFence = block.is(BlockTags.FENCES) && block.is(BlockTags.WOODEN_FENCES);
        boolean isParallelFenceGate = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, oppositeFace);
        boolean isPane = block instanceof PaneBlock && ExtendedServerConfig.barsConnectToBranches.get();
        boolean result = (!isExceptionForConnection(block) && isSolidSide) || isOtherBranch || isLeaves || isWoodenFence || isParallelFenceGate
                || isPane; // <-- added pane support
        cir.setReturnValue(result);
    }
}
