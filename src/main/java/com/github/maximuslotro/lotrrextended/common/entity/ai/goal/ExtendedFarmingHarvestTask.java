package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Based on {@link FarmTask}
 * */
public class ExtendedFarmingHarvestTask extends ExtendedFarmingAbstractTask {

	public ExtendedFarmingHarvestTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	// Only harvest if there is empty slot or can fill with more seeds
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		if(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().isEmpty()) {
			return false;
		}
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		return goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(seedsStack);
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		Item plantItem = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem();
		BlockState plantState = pServerLevel.getBlockState(pAboveBlockPos);
		Block plantBlock = plantState.getBlock();
		if(plantBlock instanceof CropsBlock && ((CropsBlock)plantBlock).isMaxAge(plantState)) {
			if (plantItem instanceof BlockItem && ((BlockItem)plantItem.getItem()).getBlock()==plantBlock) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		goalHolder.getEntity().swing(Hand.MAIN_HAND);
		//pServerLevel.destroyBlock(pAboveBlockPos, true, goalHolder.getEntity());
		destroyBlock(pAboveBlockPos, pServerLevel, goalHolder.getEntity());
		goalHolder.farmhandData.getFarmhandInfo().addCropsHarvested(1, 1);
	}

	// Copied from World.destroyBlock to Modify Block.dropResources
	public boolean destroyBlock(BlockPos pPos, ServerWorld pServerLevel, Entity pEntity) {
		BlockState blockstate = pServerLevel.getBlockState(pPos);
		if (blockstate.isAir()) {
			return false;
		} else {
			FluidState fluidstate = pServerLevel.getFluidState(pPos);
			if (!(blockstate.getBlock() instanceof AbstractFireBlock)) {
				pServerLevel.levelEvent(2001, pPos, Block.getId(blockstate));
			}
			TileEntity tileentity = blockstate.hasTileEntity() ? pServerLevel.getBlockEntity(pPos) : null;
			//Block.dropResources(blockstate, this, pPos, tileentity, pEntity, ItemStack.EMPTY);
			dropResourcesOwning(blockstate, pServerLevel, pPos, tileentity, pEntity, ItemStack.EMPTY);
			return pServerLevel.setBlock(pPos, fluidstate.createLegacyBlock(), 3, 512);
		}
	}

	// Copied from Block.dropResources
	public static void dropResourcesOwning(BlockState pState, World pLevel, BlockPos pPos, @Nullable TileEntity pBlockEntity, Entity pEntity, ItemStack pTool) {
		if (pLevel instanceof ServerWorld) {
			Block.getDrops(pState, (ServerWorld)pLevel, pPos, pBlockEntity, pEntity, pTool).forEach((itemStack) -> {
				popResourceOwning(pLevel, pPos, itemStack, pEntity);
			});
			pState.spawnAfterBreak((ServerWorld)pLevel, pPos, pTool);
		}
	}

	// Copied from Block.popResource
	public static void popResourceOwning(World pLevel, BlockPos pPos, ItemStack pStack, Entity pEntity) {
		if (!pLevel.isClientSide && !pStack.isEmpty() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.restoringBlockSnapshots) {
			float f = 0.5F;
			double d0 = (double)(pLevel.random.nextFloat() * 0.5F) + 0.25D;
			double d1 = (double)(pLevel.random.nextFloat() * 0.5F) + 0.25D;
			double d2 = (double)(pLevel.random.nextFloat() * 0.5F) + 0.25D;
			ItemEntity itementity = new ItemEntity(pLevel, (double)pPos.getX() + d0, (double)pPos.getY() + d1, (double)pPos.getZ() + d2, pStack);
			itementity.setDefaultPickUpDelay();
			itementity.setOwner(pEntity.getUUID());// Add entity as owner
			pLevel.addFreshEntity(itementity);
		}
	}
}
