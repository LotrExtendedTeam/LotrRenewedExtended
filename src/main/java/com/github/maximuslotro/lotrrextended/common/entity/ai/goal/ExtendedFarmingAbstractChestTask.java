package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

public abstract class ExtendedFarmingAbstractChestTask extends ExtendedFarmingAbstractTask {
	private int chestOpenTime;
	private ItemStack previousHeldItem;

	public ExtendedFarmingAbstractChestTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	public boolean handleChestOpenDone(ServerWorld pServerLevel, ChestTileEntity chest) {
		if(chestOpenTime<40) {
			if(chestOpenTime==0) {
				chest.startOpen(FakePlayerFactory.getMinecraft(pServerLevel));
			}
			chestOpenTime++;
			return false;
		}
		chestOpenTime=0;
		chest.stopOpen(FakePlayerFactory.getMinecraft(pServerLevel));
		return true;
	}

	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
		chestOpenTime=0;
	}

	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}

	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		BlockPos farmhandPos = goalHolder.getEntity().blockPosition();
		//int farmhandChunkX = farmhandPos.getX() >> 4;
		//int farmhandChunkZ = farmhandPos.getZ() >> 4;
		List<BlockPos> validChests = new ArrayList<>();
		for (TileEntity tileEntity : pServerLevel.blockEntityList) {
			if (tileEntity != null && !tileEntity.isRemoved() && tileEntity.hasLevel()) {
				BlockPos pos = tileEntity.getBlockPos();
				//int tileChunkX = pos.getX() >> 4;
				//int tileChunkZ = pos.getZ() >> 4;
				//Math.abs(tileChunkX - farmhandChunkX) <= 1 && Math.abs(tileChunkZ - farmhandChunkZ) <= 1
				// Check if pos is within 24 blocks circled and is on the same relative y-plane
				if (pos.distSqr(farmhandPos)<576 && Math.abs(pos.getY()-farmhandPos.getY())<=YSPLIT) {
					if (pServerLevel.getChunkSource().isTickingChunk(pos) && pServerLevel.getWorldBorder().isWithinBounds(pos)) {
						if (tileEntity instanceof ChestTileEntity) {
							if (isValidPosForTask(pos, pServerLevel)) {
								validChests.add(pos.immutable());
							}
						}
					}
				}
			}
		}
		if (!validChests.isEmpty()) {
			Collections.shuffle(validChests);
			taskPosition.set(validChests.get(0));
		}
	}

	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 2);
	}
}
