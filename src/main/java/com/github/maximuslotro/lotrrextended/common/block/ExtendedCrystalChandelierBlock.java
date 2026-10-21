package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedCrystalChandelierBlock extends Block {
	private static final int maxTypes = 3;
	public static final IntegerProperty TYPE = IntegerProperty.create("crystal_type", 1, maxTypes);
	private static final VoxelShape SHAPE = Block.box(3.0D, 2.0D, 3.0D, 13.0D, 16.0D, 13.0D);

	public ExtendedCrystalChandelierBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, 1));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateContainer) {
		stateContainer.add(TYPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.UP && !this.canSurvive(state, world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return canSupportCenter(world, pos.above(), Direction.DOWN);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand pHand, BlockRayTraceResult p_225533_6_) {
		ItemStack itemstack = player.getItemInHand(pHand);
		if(!(itemstack.getItem() instanceof ExtendedChiselItem)) {
			return ActionResultType.PASS;
		}
		int newType = state.getValue(TYPE) + 1;
		if(newType>maxTypes) {
			newType = 1;
		}
		world.setBlock(blockPos, state.setValue(TYPE, newType), 2);
		if (!player.abilities.instabuild) {
			itemstack.hurtAndBreak(1, player, (p_226874_1_) -> {});
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
     }
}