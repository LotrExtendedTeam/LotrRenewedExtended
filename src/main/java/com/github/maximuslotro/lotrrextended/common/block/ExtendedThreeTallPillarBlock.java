package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.PillarType;
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
import net.minecraft.world.IWorld;

public class ExtendedThreeTallPillarBlock extends RotatedPillarBlock{
	public static final EnumProperty<Axis> AXIS = RotatedPillarBlock.AXIS;
	public static final EnumProperty<PillarType> PILLAR_TYPE = ExtendedBlockStateProperties.PILLAR_STATE;
	public ExtendedThreeTallPillarBlock(Properties p_i48339_1_) {
		super(p_i48339_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(PILLAR_TYPE, PillarType.SINGLE));
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
						ExtendedLog.info("Checking Down Remove");
						if(state.getValue(PILLAR_TYPE).equals(PillarType.TOP_SMALL)) {
							ExtendedLog.info("removing:"+PillarType.TOP_SMALL.getSerializedName());
							return state.setValue(PILLAR_TYPE, PillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(PillarType.TOP_LARGE)) {
							ExtendedLog.info("removing:"+PillarType.TOP_LARGE.getSerializedName());
							return state.setValue(PILLAR_TYPE, PillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(PillarType.MIDDLE_LARGE)) {
							ExtendedLog.info("removing:"+PillarType.MIDDLE_LARGE.getSerializedName());
							return state.setValue(PILLAR_TYPE, PillarType.BOTTOM_SMALL);
						}
					}else {
						ExtendedLog.info("Checking Up Removing");
						if(state.getValue(PILLAR_TYPE).equals(PillarType.BOTTOM_SMALL)) {
							ExtendedLog.info("removing:"+PillarType.BOTTOM_SMALL.getSerializedName());
							return state.setValue(PILLAR_TYPE, PillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(PillarType.BOTTOM_LARGE)) {
							ExtendedLog.info("removing:"+PillarType.BOTTOM_LARGE.getSerializedName());
							return state.setValue(PILLAR_TYPE, PillarType.SINGLE);
						}
						if(state.getValue(PILLAR_TYPE).equals(PillarType.MIDDLE_LARGE)) {
							ExtendedLog.info("removing:"+PillarType.MIDDLE_LARGE.getSerializedName());
							return state.setValue(PILLAR_TYPE, PillarType.TOP_SMALL);
						}
					}
				}
				return state;
			}
			if (facingState.getValue(AXIS) != pillarAxis) {
				return state;
			}
			PillarType type = facingState.getValue(PILLAR_TYPE);
			if (dir.getAxisDirection() == AxisDirection.NEGATIVE) {
				ExtendedLog.info("Checking Down");
				if(type.equals(PillarType.SINGLE) && state.getValue(PILLAR_TYPE).equals(PillarType.SINGLE) && !(state.getValue(PILLAR_TYPE)==PillarType.TOP_SMALL)) {
					ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.TOP_SMALL.getSerializedName());
					return state.setValue(PILLAR_TYPE, PillarType.TOP_SMALL);
				}
				if(type.equals(PillarType.TOP_SMALL) && state.getValue(PILLAR_TYPE).equals(PillarType.SINGLE)) {
					ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.TOP_LARGE.getSerializedName());
					return state.setValue(PILLAR_TYPE, PillarType.TOP_LARGE);
				}
				if(blockUpdate) {
					if(type.equals(PillarType.BOTTOM_LARGE) && state.getValue(PILLAR_TYPE).equals(PillarType.BOTTOM_SMALL)) {
						ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.MIDDLE_LARGE.getSerializedName());
						return state.setValue(PILLAR_TYPE, PillarType.MIDDLE_LARGE);
					}
					if(type.equals(PillarType.MIDDLE_LARGE) && state.getValue(PILLAR_TYPE).equals(PillarType.TOP_SMALL)) {
						ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.TOP_LARGE.getSerializedName());
						return state.setValue(PILLAR_TYPE, PillarType.TOP_LARGE);
					}
				}
			}else {
				ExtendedLog.info("Checking Up");
				if(type.equals(PillarType.BOTTOM_SMALL) && state.getValue(PILLAR_TYPE).equals(PillarType.SINGLE)) {
					ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.BOTTOM_LARGE.getSerializedName());
					return state.setValue(PILLAR_TYPE, PillarType.BOTTOM_LARGE);
				}
				if(blockUpdate) {
					if(type.equals(PillarType.MIDDLE_LARGE) && state.getValue(PILLAR_TYPE)==PillarType.BOTTOM_SMALL) {
						ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.BOTTOM_LARGE.getSerializedName());
						return state.setValue(PILLAR_TYPE, PillarType.BOTTOM_LARGE);
					}
					if((type.equals(PillarType.TOP_SMALL) && state.getValue(PILLAR_TYPE)==PillarType.SINGLE) || (type.equals(PillarType.TOP_SMALL) && state.getValue(PILLAR_TYPE)==PillarType.BOTTOM_LARGE)) {
						ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.BOTTOM_SMALL.getSerializedName());
						return state.setValue(PILLAR_TYPE, PillarType.BOTTOM_SMALL);
					}
					if(type.equals(PillarType.TOP_LARGE) && state.getValue(PILLAR_TYPE)==PillarType.TOP_SMALL) {
						ExtendedLog.info("Pos:"+currentPos.toString()+", facingPos:"+facingPos.toString()+", facingType:"+type.getSerializedName()+", chosenType:"+PillarType.MIDDLE_LARGE.getSerializedName());
						return state.setValue(PILLAR_TYPE, PillarType.MIDDLE_LARGE);
					}
				}
			}

		}
		return state;
	}
}
