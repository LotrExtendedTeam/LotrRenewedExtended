package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedWeaponRackTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedWeaponRackBlock extends HorizontalFaceBlock {
	protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 4.0D, 12.0D, 15.0D, 10.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 4.0D, 0.0D, 15.0D, 10.0D, 4.0D);
	protected static final VoxelShape WEST_AABB = Block.box(12.0D, 4.0D, 1.0D, 16.0D, 10.0D, 15.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 4.0D, 1.0D, 4.0D, 10.0D, 15.0D);
	protected static final VoxelShape UP_AABB_Z = Block.box(5.0D, 0.0D, 1.0D, 11.0D, 11.0D, 15.0D);
	protected static final VoxelShape UP_AABB_X = Block.box(1.0D, 0.0D, 5.0D, 15.0D, 11.0D, 11.0D);
	protected static final VoxelShape DOWN_AABB_Z = Block.box(5.0D, 3.0D, 1.0D, 11.0D, 16.0D, 15.0D);
	protected static final VoxelShape DOWN_AABB_X = Block.box(1.0D, 3.0D, 5.0D, 15.0D, 16.0D, 11.0D);

	public ExtendedWeaponRackBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		switch (p_220053_1_.getValue(FACE)) {
			case FLOOR :
				switch (p_220053_1_.getValue(FACING).getAxis()) {
					case X :
						return UP_AABB_Z;
					case Z :
					default :
						return UP_AABB_X;
				}
			case WALL :
				switch (p_220053_1_.getValue(FACING)) {
					case EAST :
						return EAST_AABB;
					case WEST :
						return WEST_AABB;
					case SOUTH :
						return SOUTH_AABB;
					case NORTH :
					default :
						return NORTH_AABB;
				}
			case CEILING :
			default :
				switch (p_220053_1_.getValue(FACING).getAxis()) {
					case X :
						return DOWN_AABB_Z;
					case Z :
					default :
						return DOWN_AABB_X;
				}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand unused, BlockRayTraceResult p_225533_6_) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof ExtendedWeaponRackTileEntity) {
			ExtendedWeaponRackTileEntity entity = ((ExtendedWeaponRackTileEntity)tileentity);
			ItemStack item = entity.getContainedItem();
			ItemStack playerItem = player.getMainHandItem();
			if(!item.isEmpty()) {
				System.out.println("Getting item");
				if(!world.isClientSide()) {
					if(playerItem.isEmpty()) {
						System.out.println("Giveing the player the item");
						player.setItemInHand(Hand.MAIN_HAND, item.copy());
						player.inventory.setChanged();
						world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 1.4F + 2.0F);
					}else {
						System.out.println("Poping the item for the player");
						popResource(world, pos, item.copy());
					}
					item = null;
					entity.setContainedItem(ItemStack.EMPTY);
				}
			}else {
				if(!playerItem.isEmpty()) {
					System.out.println("Setting Item");
					ItemStack setItem = playerItem.copy();
					setItem.setCount(1);
					entity.setContainedItem(setItem);
					if(!player.abilities.instabuild) {
						System.out.println("Reducing player stack size");
						playerItem.setCount((playerItem.getCount()-1));
						player.inventory.setChanged();
					}
				}
			}
			ActionResultType.sidedSuccess(world.isClientSide());
		}
		return super.use(blockState, world, pos, player, unused, p_225533_6_);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, PlacementType type, EntityType<?> entityType) {
		return false;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExtendedWeaponRackTileEntity();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, FACE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockStateOther, boolean p_196243_5_) {
		if (!blockState.is(blockStateOther.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(blockPos);
			if (tileentity instanceof ExtendedWeaponRackTileEntity) {
				InventoryHelper.dropItemStack(world, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), ((ExtendedWeaponRackTileEntity)tileentity).getContainedItem());
				world.updateNeighbourForOutputSignal(blockPos, this);
			}
			super.onRemove(blockState, world, blockPos, blockStateOther, p_196243_5_);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos blockPos) {
		TileEntity tileentity = world.getBlockEntity(blockPos);
		if (tileentity instanceof ExtendedWeaponRackTileEntity) {
			if(!((ExtendedWeaponRackTileEntity)tileentity).getContainedItem().isEmpty()) {
				return 15;
			}
		}
		return 0;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof ExtendedWeaponRackTileEntity) {
			ItemStack item = ((ExtendedWeaponRackTileEntity)tileentity).getContainedItem();
			if(!item.isEmpty()) {
				return item;
			}
		}
        return super.getPickBlock(state, target, world, pos, player);
    }
}
