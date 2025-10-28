package com.github.maximuslotro.lotrrextended.common.block;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedLargePotTileEntity;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedLargePotBlock extends HorizontalBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

	public ExtendedLargePotBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof ExtendedLargePotTileEntity) {
            tileentity.onLoad();
            worldIn.setBlockEntity(pos, tileentity);
        }
    }

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand unused, BlockRayTraceResult p_225533_6_) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if(!world.isClientSide && tileentity instanceof ExtendedLargePotTileEntity) {
			ExtendedLargePotTileEntity entity = ((ExtendedLargePotTileEntity)tileentity);
			if (!player.isCrouching()) {
				ItemStack stack = player.getMainHandItem();
                if(!stack.isEmpty()) {
                	if(entity.getContainedItem(true).isEmpty()) {
                        entity.setContainedItem(stack.split(1));
    					//world.playSound(null, pos, ExtendedSounds.POT_FILLED.get(), SoundCategory.PLAYERS, 1.0F, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 1.4F + 0.1F);
    					return ActionResultType.SUCCESS;
                	}
                	else {
                		if(ExtendedItemStackUtils.isSameItemStackItemAndTag(stack, entity.getContainedItem(true)) && entity.getContainedItem(true).getCount()+1<=entity.getContainedItem(true).getMaxStackSize()) {
                			stack.shrink(1);
                			entity.getContainedItem(true).grow(1);
        					world.playSound(null, pos, ExtendedSounds.POT_FILLED.get(), SoundCategory.BLOCKS, 1.0F, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 1.4F + 0.3F);
        					return ActionResultType.SUCCESS;
                		}
                	}
                }
			}
			/*
			else {
                if (entity.hasItem()) {
                    final ItemStack toDrop = entity.getContainedItem().split(1);
                    if(player.getMainHandItem().isEmpty()) {
                    	player.setItemInHand(Hand.MAIN_HAND, toDrop);
                    }
                    else {
                        popResource(world, pos, toDrop);
                    }
					world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 1.4F + 2.0F);
                }
			}*/
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState pNewState, boolean pIsMoving) {
		if (!blockState.is(pNewState.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof ExtendedLargePotTileEntity) {
				InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ((ExtendedLargePotTileEntity)tileentity).getContainedItem(true).copy());
				world.updateNeighbourForOutputSignal(blockPos, this);
			}
			super.onRemove(blockState, world, blockPos, pNewState, pIsMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos blockPos) {
		TileEntity tileentity = world.getBlockEntity(blockPos);
		if (tileentity instanceof ExtendedLargePotTileEntity) {
			if(!((ExtendedLargePotTileEntity)tileentity).getContainedItem(false).isEmpty()) {
				return 15;
			}
		}
		return 0;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext placeContext) {
		return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExtendedLargePotTileEntity();
	}
}
