package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

public class ExtendedVineBlock extends VineBlock {
	public static final BooleanProperty CLIPPED = BlockStateProperties.VINE_END;

	public ExtendedVineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, false).setValue(NORTH, false).setValue(EAST, false)
			.setValue(SOUTH, false).setValue(WEST, false).setValue(CLIPPED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(UP, NORTH, EAST, SOUTH, WEST, CLIPPED);
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 15;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 1000;
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return !pState.getValue(CLIPPED);
	}


	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity pPlayer, Hand hand, BlockRayTraceResult p_225533_6_) {
		if(pPlayer.getItemInHand(hand).getItem().is(Tags.Items.SHEARS) && !blockState.getValue(CLIPPED)) {
			blockState = blockState.setValue(CLIPPED, true);
			world.setBlock(blockPos, blockState, 10);
			world.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOW_GOLEM_SHEAR, SoundCategory.PLAYERS, 0.5F, 0.4F / (this.RANDOM.nextFloat() * 0.4F + 0.8F));
			pPlayer.getItemInHand(hand).hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pPlayer.getUsedItemHand()));
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}

	/**
	 * make grow delay twice vanilla and add clipped properties
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		// Extended Start
		if (pState.hasProperty(CLIPPED) && !pState.getValue(CLIPPED) &&pLevel.random.nextInt(8) == 0 && pLevel.isAreaLoaded(pPos, 4)) { // Forge: check area to prevent loading unloaded chunks
			// Extended End
			Direction direction = Direction.getRandom(pRandom);
			BlockPos blockpos = pPos.above();
			if (direction.getAxis().isHorizontal() && !pState.getValue(getPropertyForFace(direction))) {
				if (this.canSpread(pLevel, pPos)) {
					BlockPos blockpos4 = pPos.relative(direction);
					BlockState blockstate4 = pLevel.getBlockState(blockpos4);
					if (blockstate4.isAir(pLevel, blockpos4)) {
						Direction direction3 = direction.getClockWise();
						Direction direction4 = direction.getCounterClockWise();
						boolean flag = pState.getValue(getPropertyForFace(direction3));
						boolean flag1 = pState.getValue(getPropertyForFace(direction4));
						BlockPos blockpos2 = blockpos4.relative(direction3);
						BlockPos blockpos3 = blockpos4.relative(direction4);
						if (flag && isAcceptableNeighbour(pLevel, blockpos2, direction3)) {
							pLevel.setBlock(blockpos4, this.defaultBlockState().setValue(getPropertyForFace(direction3), Boolean.TRUE), 2);
						} else if (flag1 && isAcceptableNeighbour(pLevel, blockpos3, direction4)) {
							pLevel.setBlock(blockpos4, this.defaultBlockState().setValue(getPropertyForFace(direction4), Boolean.TRUE), 2);
						} else {
							Direction direction1 = direction.getOpposite();
							if (flag && pLevel.isEmptyBlock(blockpos2) && isAcceptableNeighbour(pLevel, pPos.relative(direction3), direction1)) {
								pLevel.setBlock(blockpos2, this.defaultBlockState().setValue(getPropertyForFace(direction1), Boolean.TRUE), 2);
							} else if (flag1 && pLevel.isEmptyBlock(blockpos3) && isAcceptableNeighbour(pLevel, pPos.relative(direction4), direction1)) {
								pLevel.setBlock(blockpos3, this.defaultBlockState().setValue(getPropertyForFace(direction1), Boolean.TRUE), 2);
							} else if (pLevel.random.nextFloat() < 0.05D && isAcceptableNeighbour(pLevel, blockpos4.above(), Direction.UP)) {
								pLevel.setBlock(blockpos4, this.defaultBlockState().setValue(UP, Boolean.TRUE), 2);
							}
						}
					} else if (isAcceptableNeighbour(pLevel, blockpos4, direction)) {
						pLevel.setBlock(pPos, pState.setValue(getPropertyForFace(direction), Boolean.TRUE), 2);
					}

				}
			} else {
				if (direction == Direction.UP && pPos.getY() < 255) {
					if (this.canSupportAtFace(pLevel, pPos, direction)) {
						pLevel.setBlock(pPos, pState.setValue(UP, Boolean.TRUE), 2);
						return;
					}

					if (pLevel.isEmptyBlock(blockpos)) {
						if (!this.canSpread(pLevel, pPos)) {
							return;
						}

						BlockState blockstate3 = pState;

						for(Direction direction2 : Direction.Plane.HORIZONTAL) {
							if (pRandom.nextBoolean() || !isAcceptableNeighbour(pLevel, blockpos.relative(direction2), Direction.UP)) {
								blockstate3 = blockstate3.setValue(getPropertyForFace(direction2), Boolean.FALSE);
							}
						}

						if (this.hasHorizontalConnection(blockstate3)) {
							pLevel.setBlock(blockpos, blockstate3, 2);
						}

						return;
					}
				}

				if (pPos.getY() > 0) {
					BlockPos blockpos1 = pPos.below();
					BlockState blockstate = pLevel.getBlockState(blockpos1);
					boolean isAir = blockstate.isAir(pLevel, blockpos1);
					if (isAir || blockstate.is(this)) {
						BlockState blockstate1 = isAir ? this.defaultBlockState() : blockstate;
						BlockState blockstate2 = this.copyRandomFaces(pState, blockstate1, pRandom);
						if (blockstate1 != blockstate2 && this.hasHorizontalConnection(blockstate2)) {
							pLevel.setBlock(blockpos1, blockstate2, 2);
						}
					}
				}

			}
		}
	}

	private boolean canSupportAtFace(IBlockReader pLevel, BlockPos pPos, Direction pDirection) {
		if (pDirection == Direction.DOWN) {
			return false;
		} else {
			BlockPos blockpos = pPos.relative(pDirection);
			if (isAcceptableNeighbour(pLevel, blockpos, pDirection)) {
				return true;
			} else if (pDirection.getAxis() == Direction.Axis.Y) {
				return false;
			} else {
				BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(pDirection);
				BlockState blockstate = pLevel.getBlockState(pPos.above());
				return blockstate.is(this) && blockstate.getValue(booleanproperty);
			}
		}
	}

	private BlockState copyRandomFaces(BlockState p_196544_1_, BlockState p_196544_2_, Random pRandom) {
		for(Direction direction : Direction.Plane.HORIZONTAL) {
			if (pRandom.nextBoolean()) {
				BooleanProperty booleanproperty = getPropertyForFace(direction);
				if (p_196544_1_.getValue(booleanproperty)) {
					p_196544_2_ = p_196544_2_.setValue(booleanproperty, Boolean.TRUE);
				}
			}
		}

		return p_196544_2_;
	}

	private boolean hasHorizontalConnection(BlockState pState) {
		return pState.getValue(NORTH) || pState.getValue(EAST) || pState.getValue(SOUTH) || pState.getValue(WEST);
	}

	private boolean canSpread(IBlockReader pBlockReader, BlockPos pPos) {
		int i = 4;
		Iterable<BlockPos> iterable = BlockPos.betweenClosed(pPos.getX() - 4, pPos.getY() - 1, pPos.getZ() - 4, pPos.getX() + 4, pPos.getY() + 1, pPos.getZ() + 4);
		int j = 5;

		for(BlockPos blockpos : iterable) {
			if (pBlockReader.getBlockState(blockpos).is(this)) {
				--j;
				if (j <= 0) {
					return false;
				}
			}
		}

		return true;
	}
}
