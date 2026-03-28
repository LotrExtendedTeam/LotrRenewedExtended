package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.state.properties.ExtendedBlockStateProperties;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBerryBushBlock extends SweetBerryBushBlock {
	private final RegistryObject<Item> berryItem;
	private final RegistryObject<Block> berryHedgeBlock;
	public static final BooleanProperty CLIPPED = ExtendedBlockStateProperties.CLIPPED;

	public ExtendedBerryBushBlock(RegistryObject<Item> berryItem, RegistryObject<Block> berryHedgeBlock) {
		super(Properties.of(Material.PLANT, MaterialColor.COLOR_GREEN)
			.randomTicks()
			.sound(SoundType.SWEET_BERRY_BUSH)
			.harvestTool(ToolType.HOE)
			.noCollission()
			.noOcclusion()
			.isRedstoneConductor(LOTRBlocks::posPredicateFalse)
			.isSuffocating(LOTRBlocks::posPredicateFalse)
			.isViewBlocking(LOTRBlocks::posPredicateFalse));
		this.berryItem = berryItem;
		this.berryHedgeBlock = berryHedgeBlock;
		registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(CLIPPED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(AGE, CLIPPED);
	}

	@Override
	public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		if(!pState.hasProperty(CLIPPED) || pState.getValue(CLIPPED)) {
			return;
		}
		int i = pState.getValue(AGE);
		if (i < 3 && pLevel.getRawBrightness(pPos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,pRandom.nextInt(5) == 0)) {
			pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(i + 1)), 2);
			ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
		}else if (i == 3 && pLevel.getRawBrightness(pPos.above(), 0) >= 9 && pRandom.nextInt(1000) == 0) {
			pLevel.setBlock(pPos, berryHedgeBlock.get().defaultBlockState().setValue(ExtendedBerryHedgeBlock.AGE, 3), 2);
			ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return !pState.getValue(CLIPPED);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(berryItem.get());
	}
	@Override
	protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
		if(p_200014_1_.getBlock() instanceof ExtendedBerryHedgeBlock || p_200014_1_.getBlock() instanceof ExtendedGroundFarmlandBlock) {
			return true;
		}
		return super.mayPlaceOn(p_200014_1_, p_200014_2_, p_200014_3_);
	}

	@Override
	public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		if (pEntity instanceof LivingEntity && pEntity.getType() != EntityType.FOX && pEntity.getType() != EntityType.BEE) {
	         pEntity.makeStuckInBlock(pState, new Vector3d((double)0.95F, 0.90D, (double)0.95F));
		}
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity pPlayer, Hand hand, BlockRayTraceResult p_225533_6_) {
		if(pPlayer.getItemInHand(hand).getItem().is(Tags.Items.SHEARS) && !blockState.getValue(CLIPPED)) {
			blockState = blockState.setValue(CLIPPED, true);
			world.setBlock(blockPos, blockState, 10);
			world.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOW_GOLEM_SHEAR, SoundCategory.PLAYERS, 0.5F, 0.4F / (this.RANDOM.nextFloat() * 0.4F + 0.8F));
			pPlayer.getItemInHand(hand).hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pPlayer.getUsedItemHand()));
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
		int i = blockState.getValue(AGE);
		boolean flag = i == 3;
		if (!flag && pPlayer.getItemInHand(hand).getItem() == Items.BONE_MEAL) {
			return ActionResultType.PASS;
		} else if (i > 2) {
			popResource(world, blockPos, new ItemStack(berryItem.get(), flag ? world.random.nextInt(3) + 1 : 0));
			world.playSound(null, blockPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			world.setBlock(blockPos, blockState.setValue(AGE, 1), 2);
			return ActionResultType.sidedSuccess(world.isClientSide);
		} else {
			return ActionResultType.PASS;
		}
	}

	public Item getBerryItem() {
		return berryItem.get();
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 100;
	}
}