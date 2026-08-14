package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBerryHedgeBlock extends Block implements IGrowable{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private final RegistryObject<Item> berryItem;
	private final RegistryObject<Item> berryHedgeItem;
	public ExtendedBerryHedgeBlock(RegistryObject<Item> berryItem, RegistryObject<Item> berryBlock) {
		super(Properties.of(Material.LEAVES, MaterialColor.COLOR_GREEN)
			.randomTicks()
			.sound(SoundType.SWEET_BERRY_BUSH)
			.strength(0.2F)
			.noOcclusion()
			.harvestTool(ToolType.HOE)
			.isValidSpawn(ExtendedBlockParms::allowSpawnOnLeaves)
			.isRedstoneConductor(ExtendedBlockParms::never)
			.isSuffocating(ExtendedBlockParms::never)
			.isViewBlocking(ExtendedBlockParms::never));
		this.berryItem = berryItem;
		if(DatagenModLoader.isRunningDataGen()) {
			this.berryHedgeItem = berryBlock;
		}else {
			this.berryHedgeItem = null;
		}
		registerDefaultState(stateDefinition.any().setValue(AGE, 0));
		//CompostingHelper.prepareCompostable(this, 0.65F);
	}

	@Override
	public VoxelShape getVisualShape(BlockState pState, IBlockReader pReader, BlockPos pPos, ISelectionContext pContext) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
		return true;
	}

	@Override
	public void randomTick(BlockState thisState, ServerWorld thisWorld, BlockPos thisPos, Random rand) {
		int i = thisState.getValue(AGE);
		if (i < 3 && thisWorld.getRawBrightness(thisPos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(thisWorld, thisPos, thisState,rand.nextInt(5) == 0)) {
			thisWorld.setBlock(thisPos, thisState.setValue(AGE, i + 1), 2);
			ForgeHooks.onCropsGrowPost(thisWorld, thisPos, thisState);
		}
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if(placer instanceof PlayerEntity) {
			if(((PlayerEntity)placer).isCreative() && !placer.isCrouching()) {
				world.setBlock(pos, state.setValue(AGE, 3), 3);
			}
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		int i = p_225533_1_.getValue(AGE);
		boolean flag = i == 3;
		if (!flag && p_225533_4_.getItemInHand(p_225533_5_).getItem() == Items.BONE_MEAL) {
			return ActionResultType.PASS;
		} else if (i > 2) {
			popResource(p_225533_2_, p_225533_3_, new ItemStack(berryItem.get(), flag ? p_225533_2_.random.nextInt(5) + 1: 0));
			p_225533_2_.playSound(null, p_225533_3_, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + p_225533_2_.random.nextFloat() * 0.4F);
			p_225533_2_.setBlock(p_225533_3_, p_225533_1_.setValue(AGE, 1), 2);
			return ActionResultType.sidedSuccess(p_225533_2_.isClientSide);
		} else {
			return ActionResultType.PASS;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	/////////////////////////COPY FROM SweetBerryBushBlock.class/////////////////////////////
	@Override
	public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_) {
		return p_176473_3_.getValue(AGE) < 3;
	}

	@Override
	public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
		int i = Math.min(3, p_225535_4_.getValue(AGE) + 1);
		p_225535_1_.setBlock(p_225535_3_, p_225535_4_.setValue(AGE, i), 2);
	}

	public Item getBerryItem() {
		return berryItem.get();
	}

	@Nullable
	public Item getBerryHedgeItem() {
		return berryHedgeItem.get();
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}
}
