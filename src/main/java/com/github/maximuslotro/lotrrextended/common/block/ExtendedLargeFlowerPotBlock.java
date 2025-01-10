package com.github.maximuslotro.lotrrextended.common.block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

// A good amount of code in this class was copied from FlowerPotBlock
public class ExtendedLargeFlowerPotBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
	private static final Map<ResourceLocation, Supplier<? extends Block>> fullPots = new HashMap<>();
	private final java.util.function.Supplier<? extends Block> flowerDelegate;

	public ExtendedLargeFlowerPotBlock(Supplier<? extends Block> pContent, AbstractBlock.Properties properties) {
		super(properties);
		if(pContent==null) {
			flowerDelegate = ()-> Blocks.AIR;
		}else {
			this.flowerDelegate = pContent;
			addPlant(pContent.get().getRegistryName(), () -> this);
		}
	}

	public ExtendedLargeFlowerPotBlock() {
		this(null, Properties.of(Material.DECORATION).instabreak().noOcclusion());
	}
	public ExtendedLargeFlowerPotBlock(Supplier<? extends Block> pContent) {
		this(pContent, Properties.of(Material.DECORATION).instabreak().noOcclusion());
	}

	public ExtendedLargeFlowerPotBlock(Block pContent) {
		this(()->pContent);
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		Item item = itemstack.getItem();
		Block block = item instanceof BlockItem ? fullPots.getOrDefault(((BlockItem)item).getBlock().getRegistryName(), Blocks.AIR.delegate).get() : Blocks.AIR;
		boolean flag = block == Blocks.AIR;
		boolean flag1 = this.flowerDelegate.get() == Blocks.AIR;
		if (flag != flag1) {
			if (flag1) {
				pLevel.setBlock(pPos, block.defaultBlockState(), 3);
				pPlayer.awardStat(Stats.POT_FLOWER);
				if (!pPlayer.abilities.instabuild) {
					itemstack.shrink(1);
				}
			} else {
				ItemStack itemstack1 = new ItemStack(this.flowerDelegate.get());
				if (itemstack.isEmpty()) {
					pPlayer.setItemInHand(pHand, itemstack1);
				} else if (!pPlayer.addItem(itemstack1)) {
					pPlayer.drop(itemstack1, false);
				}

				pLevel.setBlock(pPos, getEmptyPot().defaultBlockState(), 3);
			}

			return ActionResultType.sidedSuccess(pLevel.isClientSide);
		} else {
			return ActionResultType.CONSUME;
		}
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
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	@SuppressWarnings("deprecation")
	public ItemStack getCloneItemStack(IBlockReader pLevel, BlockPos pPos, BlockState pState) {
		return this.flowerDelegate.get().equals(Blocks.AIR) ? super.getCloneItemStack(pLevel, pPos, pState) : new ItemStack(this.flowerDelegate.get());
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	public Block getContent() {
		return flowerDelegate.get();
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return false;
	}

	public Block getEmptyPot() {
		return ExtendedBlocks.LARGE_FLOWER_POT.isPresent() ? ExtendedBlocks.LARGE_FLOWER_POT.get() : Blocks.AIR;
	}

	public void addPlant(net.minecraft.util.ResourceLocation flower, java.util.function.Supplier<? extends Block> fullPot) {
		fullPots.put(flower, fullPot);
	}

	public Map<net.minecraft.util.ResourceLocation, java.util.function.Supplier<? extends Block>> getFullPotsView() {
		return java.util.Collections.unmodifiableMap(fullPots);
	}
	//Forge End
}
