package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedChestTileEntity;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedSingleChestBlock extends ChestBlock {
	private final Supplier<ChestTileEntity> tileentityholder;
	public ExtendedSingleChestBlock(Supplier<ChestTileEntity> tileentityholder) {
		super(AbstractBlock.Properties.of(Material.STONE).strength(4.0F).sound(SoundType.STONE), ()-> ExtendedTileEntities.STONE_CHEST.get());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, Boolean.valueOf(false)));
		this.tileentityholder = tileentityholder;
	}

	public static TileEntityMerger.Type getExtendedBlockType(BlockState p_226919_0_) {
		return TileEntityMerger.Type.SINGLE;
	}

	@Override
	public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
		if (p_196271_1_.getValue(WATERLOGGED)) {
			p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
		}
		return p_196271_1_.setValue(TYPE, ChestType.SINGLE);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return AABB;
	}

	public static Direction getExtendedConnectedDirection(BlockState p_196311_0_) {
		Direction direction = p_196311_0_.getValue(FACING);
		return p_196311_0_.getValue(TYPE) == ChestType.LEFT ? direction.getClockWise() : direction.getCounterClockWise();
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		FluidState fluidstate = p_196258_1_.getLevel().getFluidState(p_196258_1_.getClickedPos());
		return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite()).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

	@Override
	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (p_225533_2_.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			INamedContainerProvider inamedcontainerprovider = this.getMenuProvider(p_225533_1_, p_225533_2_, p_225533_3_);
			if (inamedcontainerprovider != null) {
				p_225533_4_.openMenu(inamedcontainerprovider);
				p_225533_4_.awardStat(ExtendedStats.OPEN_STONE_CHEST);
				PiglinTasks.angerNearbyPiglins(p_225533_4_, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TileEntityMerger.ICallbackWrapper<? extends ExtendedChestTileEntity> combine(BlockState p_225536_1_, World world, BlockPos blockPos, boolean p_225536_4_) {
		BiPredicate<IWorld, BlockPos> bipredicate;
		if (p_225536_4_) {
			bipredicate = (p_226918_0_, p_226918_1_) -> {
				return false;
			};
		} else {
			bipredicate = ChestBlock::isChestBlockedAt;
		}
		ExtendedChestTileEntity<?> s = (ExtendedChestTileEntity<?>) this.blockEntityType.get().getBlockEntity(world, blockPos);
		if (s == null) {
			return TileEntityMerger.ICallback::acceptNone;
		} else if (bipredicate.test(world, blockPos)) {
			return TileEntityMerger.ICallback::acceptNone;
		} else {
			return new TileEntityMerger.ICallbackWrapper.Single<>(s);
		}
	}

	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return tileentityholder.get();
	}

	   @OnlyIn(Dist.CLIENT)
	   public static TileEntityMerger.ICallback<ChestTileEntity, Float2FloatFunction> opennessCombiner(final IChestLid p_226917_0_) {
	      return new TileEntityMerger.ICallback<ChestTileEntity, Float2FloatFunction>() {
	         public Float2FloatFunction acceptDouble(ChestTileEntity p_225539_1_, ChestTileEntity p_225539_2_) {
	        	 return p_225539_1_::getOpenNess;
	         }

	         public Float2FloatFunction acceptSingle(ChestTileEntity p_225538_1_) {
	            return p_225538_1_::getOpenNess;
	         }

	         public Float2FloatFunction acceptNone() {
	            return p_226917_0_::getOpenNess;
	         }
	      };
	   }
}