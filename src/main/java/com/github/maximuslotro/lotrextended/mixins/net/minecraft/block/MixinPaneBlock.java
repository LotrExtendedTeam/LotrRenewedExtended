package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.LOTRBlockStates;
import lotr.common.block.VerticalOnlySlabBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

/**
 * A mixin extension for {@link PaneBlock} that allows glass panes or branches to connect to
 * custom vertical slab blocks (such as {@link VerticalOnlySlabBlock} and {@link AxialSlabBlock})
 * when their exposed edges are aligned with the pane's side.
 * <p>
 * Vanilla pane connection logic only considers full blocks or certain predefined
 * connectable blocks. This mixin extends that behavior so that panes can connect
 * to vertical slabs that are aligned along the X or Z axis and are not of type
 * {@link SlabType#DOUBLE}.
 * </p>
 *
 * <p>
 * The connection logic checks both during initial placement
 * ({@link #getStateForPlacement$LotrExtended(BlockItemUseContext, CallbackInfoReturnable, IBlockReader, BlockPos, FluidState, BlockPos, BlockPos, BlockPos, BlockPos, BlockState, BlockState, BlockState, BlockState)})
 * and when neighboring blocks change
 * ({@link #updateShape$LotrExtended(BlockState, Direction, BlockState, IWorld, BlockPos, BlockPos, CallbackInfoReturnable)}),
 * ensuring connections remain consistent as the world updates.
 * </p>
 * @Mixin (PaneBlock.class)
 */
@Mixin(PaneBlock.class)
public abstract class MixinPaneBlock extends FourWayBlock {
	/**
     * Constructor stripped on runtime.
     *
     * @param p_i48420_1_  X-size of the bounding box.
     * @param p_i48420_2_  Y-size of the bounding box.
     * @param p_i48420_3_  Z-size of the bounding box.
     * @param p_i48420_4_  Bounding box offset in X.
     * @param p_i48420_5_  Bounding box offset in Y.
     * @param p_i48420_6_  Block properties.
     */
	public MixinPaneBlock(float p_i48420_1_, float p_i48420_2_, float p_i48420_3_, float p_i48420_4_, float p_i48420_5_, Properties p_i48420_6_) {
		super(p_i48420_1_, p_i48420_2_, p_i48420_3_, p_i48420_4_, p_i48420_5_, p_i48420_6_);
	}

	/**
     * Shadowed method from {@link PaneBlock} that checks if the pane should attach to a given block.
     *
     * @param pState     The state of the block being checked.
     * @param pSolidSide Whether the side is considered solid for attachment.
     * @return {@code true} if the pane can attach, {@code false} otherwise.
     */
	@Shadow
	public abstract boolean attachsTo(BlockState pState, boolean pSolidSide);

	/**
     * Injected logic for determining the pane's initial state when placed.
     * <p>
     * Extends vanilla placement logic so that panes also connect to vertical slabs
     * when the slab's exposed face aligns with the pane's side.
     * </p>
     *
     * @param var1         The block item use context.
     * @param cir          The callback info for returning the new block state.
     * @param iblockreader The block reader for the current world.
     * @param clickedPos   The position where the pane is placed.
     * @param fluidstate   The fluid state at the position.
     * @param northPos     Position of the north neighbor.
     * @param southPos     Position of the south neighbor.
     * @param westPos      Position of the west neighbor.
     * @param eastPos      Position of the east neighbor.
     * @param northState   State of the north neighbor.
     * @param southState   State of the south neighbor.
     * @param westState    State of the west neighbor.
     * @param eastState    State of the east neighbor.
     * @Inject (method = "getStateForPlacement(Lnet/minecraft/item/BlockItemUseContext;)Lnet/minecraft/block/BlockState;", at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
     */
	@Inject(method = "getStateForPlacement(Lnet/minecraft/item/BlockItemUseContext;)Lnet/minecraft/block/BlockState;", 
		at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	public void getStateForPlacement$LotrExtended(BlockItemUseContext var1, CallbackInfoReturnable<BlockState> cir, IBlockReader iblockreader, BlockPos clickedPos, FluidState fluidstate, 
		BlockPos northPos, BlockPos southPos, BlockPos westPos, BlockPos eastPos, BlockState northState, BlockState southState, BlockState westState, BlockState eastState) {
		cir.setReturnValue(this.defaultBlockState()
			.setValue(NORTH, this.attachsTo(northState, northState.isFaceSturdy(iblockreader, northPos, Direction.SOUTH)) || connects(northState, Direction.SOUTH))
			.setValue(SOUTH, this.attachsTo(southState, southState.isFaceSturdy(iblockreader, southPos, Direction.NORTH)) || connects(southState, Direction.NORTH))
			.setValue(WEST, this.attachsTo(westState, westState.isFaceSturdy(iblockreader, westPos, Direction.EAST)) || connects(westState, Direction.EAST))
			.setValue(EAST, this.attachsTo(eastState, eastState.isFaceSturdy(iblockreader, eastPos, Direction.WEST)) || connects(eastState, Direction.WEST))
			.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)));
	}
	/**
     * Injected logic to update pane connections when a neighboring block changes.
     * <p>
     * This method ensures panes will connect to vertical slabs
     * if the slab's exposed face is oriented toward the pane's side.
     * </p>
     *
     * @param pState      The current state of the pane.
     * @param pFacing     The side of the pane being updated.
     * @param pFacingState The state of the neighboring block on that side.
     * @param pLevel      The world.
     * @param pCurrentPos The position of the pane.
     * @param pFacingPos  The position of the neighbor.
     * @param ci          Callback info for returning a modified block state.
     * @Inject (method = "updateShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/Direction;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", at = @At("HEAD"), cancellable = true)
     */
	@Inject(method = "updateShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/Direction;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", 
		at = @At("HEAD"), cancellable = true)
	public void updateShape$LotrExtended(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos, CallbackInfoReturnable<BlockState> ci) {
		// Only care about horizontal sides
		if (!pFacing.getAxis().isHorizontal()) {
			return;
		}
		if (connects(pFacingState, pFacing)) {
			BlockState newState = pState.setValue(PaneBlock.PROPERTY_BY_DIRECTION.get(pFacing), true);
			ci.setReturnValue(newState);
		}
	}

	/**
     * Checks whether the given block state represents a vertical slab
     * whose exposed face should connect to a pane on the specified side.
     *
     * @param pFacingState The state of the neighboring block.
     * @param pFacing      The direction from the pane toward the neighbor.
     * @return {@code true} if the pane should connect, {@code false} otherwise.
     * @Unique
     */
	@Unique
	private static boolean connects(BlockState pFacingState, Direction pFacing) {
		if ((pFacingState.getBlock() instanceof VerticalOnlySlabBlock || pFacingState.getBlock() instanceof AxialSlabBlock) && ExtendedServerConfig.barsConnectToSlabSides.get()) {
			Axis axis = pFacingState.getValue(pFacingState.getBlock() instanceof VerticalOnlySlabBlock ? LOTRBlockStates.VERTICAL_ONLY_SLAB_AXIS : LOTRBlockStates.SLAB_AXIS);
			SlabType type = pFacingState.getValue(BlockStateProperties.SLAB_TYPE);//Do we really need this check, double already connects?

			// Only connect when slab is vertical (X or Z axis) and not DOUBLE
			if (axis != Axis.Y && type != SlabType.DOUBLE && connectsOnSide(axis, pFacing)) {
				return true;
			}
		}
		if (pFacingState.getBlock() instanceof BranchBlock && ExtendedServerConfig.barsConnectToBranches.get()) {
			return true;
		}
		return false;
	}
	/**
     * Checks if a slab with the given axis is oriented such that its flat face
     * aligns with the given side direction of a pane.
     *
     * @param axis  The axis along which the slab is oriented.
     * @param side  The pane's facing direction toward the slab.
     * @return {@code true} if the slab's flat face is flush with the pane's side, {@code false} otherwise.
     * @Unique
     */
	@Unique
	private static boolean connectsOnSide(Axis axis, Direction side) {
		//ExtendedLog.info("Checking slab with axis of "+axis.getSerializedName()+", with the facing direction of "+side.getSerializedName());
		// Axis.Z slabs have partial faces on EAST/WEST
		if (axis == Axis.Z) {
			return side == Direction.EAST || side == Direction.WEST;
		}
		// Axis.X slabs have partial faces  on NORTH/SOUTH
		if (axis == Axis.X) {
			return side == Direction.NORTH || side == Direction.SOUTH;
		}
		return false;
	}
}
