package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedPillarType;
import com.github.maximuslotro.lotrrextended.common.state.properties.ExtendedBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ExtendedThreeTallPillarBlock extends RotatedPillarBlock {
	public static final EnumProperty<Axis> AXIS = RotatedPillarBlock.AXIS;
	public static final EnumProperty<ExtendedPillarType> PILLAR_TYPE = ExtendedBlockStateProperties.PILLAR_STATE;
	public ExtendedThreeTallPillarBlock(Properties p_i48339_1_) {
		super(p_i48339_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(PILLAR_TYPE, ExtendedPillarType.SINGLE));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(AXIS).add(PILLAR_TYPE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		BlockPos pos = context.getClickedPos();
		IWorld world = context.getLevel();
		for (Direction dir : Direction.values()) {
			BlockPos facingPos = pos.relative(dir);
			state = this.checkSurroundingPillarLogic(state, dir, world.getBlockState(facingPos), world, pos, facingPos, false);
		}
		return state;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		return this.checkSurroundingPillarLogic(state, dir, facingState, world, currentPos, facingPos, true);
	}

	private BlockState checkSurroundingPillarLogic(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos, boolean blockUpdate) {
		Axis pillarAxis = state.getValue(AXIS);
		if (dir.getAxis() == pillarAxis) {
			if (!(facingState.getBlock() instanceof ExtendedThreeTallPillarBlock)) {
				if(blockUpdate) {
					if (dir.getAxisDirection() == AxisDirection.NEGATIVE) {
						if(state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.TOP_SMALL)) {
							return state.setValue(PILLAR_TYPE, ExtendedPillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.TOP_LARGE)) {
							return state.setValue(PILLAR_TYPE, ExtendedPillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.MIDDLE_LARGE)) {
							return state.setValue(PILLAR_TYPE, ExtendedPillarType.BOTTOM_SMALL);
						}
					}else {
						if(state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.BOTTOM_SMALL)) {
							return state.setValue(PILLAR_TYPE, ExtendedPillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.BOTTOM_LARGE)) {
							return state.setValue(PILLAR_TYPE, ExtendedPillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.MIDDLE_LARGE)) {
							return state.setValue(PILLAR_TYPE, ExtendedPillarType.TOP_SMALL);
						}
					}
				}
				return state;
			}
			if (facingState.getValue(AXIS) != pillarAxis) {
				return state;
			}
			ExtendedPillarType type = facingState.getValue(PILLAR_TYPE);
			if (dir.getAxisDirection() == AxisDirection.NEGATIVE) {
				if(type.equals(ExtendedPillarType.SINGLE) && state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.SINGLE) && !(state.getValue(PILLAR_TYPE)==ExtendedPillarType.TOP_SMALL)) {
					return state.setValue(PILLAR_TYPE, ExtendedPillarType.TOP_SMALL);
				}
				if(type.equals(ExtendedPillarType.TOP_SMALL) && state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.SINGLE)) {
					return state.setValue(PILLAR_TYPE, ExtendedPillarType.TOP_LARGE);
				}
				if(blockUpdate) {
					if(type.equals(ExtendedPillarType.BOTTOM_LARGE) && state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.BOTTOM_SMALL)) {
						return state.setValue(PILLAR_TYPE, ExtendedPillarType.MIDDLE_LARGE);
					}
					if(type.equals(ExtendedPillarType.MIDDLE_LARGE) && state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.TOP_SMALL)) {
						return state.setValue(PILLAR_TYPE, ExtendedPillarType.TOP_LARGE);
					}
				}
			}else {
				if(type.equals(ExtendedPillarType.BOTTOM_SMALL) && state.getValue(PILLAR_TYPE).equals(ExtendedPillarType.SINGLE)) {
					return state.setValue(PILLAR_TYPE, ExtendedPillarType.BOTTOM_LARGE);
				}
				if(blockUpdate) {
					if(type.equals(ExtendedPillarType.MIDDLE_LARGE) && state.getValue(PILLAR_TYPE)==ExtendedPillarType.BOTTOM_SMALL) {
						return state.setValue(PILLAR_TYPE, ExtendedPillarType.BOTTOM_LARGE);
					}
					if((type.equals(ExtendedPillarType.TOP_SMALL) && state.getValue(PILLAR_TYPE)==ExtendedPillarType.SINGLE) || (type.equals(ExtendedPillarType.TOP_SMALL) && state.getValue(PILLAR_TYPE)==ExtendedPillarType.BOTTOM_LARGE)) {
						return state.setValue(PILLAR_TYPE, ExtendedPillarType.BOTTOM_SMALL);
					}
					if(type.equals(ExtendedPillarType.TOP_LARGE) && state.getValue(PILLAR_TYPE)==ExtendedPillarType.TOP_SMALL) {
						return state.setValue(PILLAR_TYPE, ExtendedPillarType.MIDDLE_LARGE);
					}
				}
			}

		}
		return state;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 40;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 40;
    }
}
