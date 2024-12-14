package com.github.maximuslotro.lotrrextended.common.block.containers;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;

import lotr.common.data.ExtendedLevelData;
import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedStoneChestBlock extends ExtendedSingleChestBlock {
	public ExtendedStoneChestBlock(Supplier<ChestTileEntity> tileentityholder) {
		super(tileentityholder);
	}

	@Override
	public ActionResultType use(BlockState pBlockState, World pWorld, BlockPos p_225533_3_, PlayerEntity pPlayerEntity, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (pWorld.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			INamedContainerProvider inamedcontainerprovider = this.getMenuProvider(pBlockState, pWorld, p_225533_3_);
			if (inamedcontainerprovider != null) {
				pPlayerEntity.openMenu(inamedcontainerprovider);
				pPlayerEntity.awardStat(ExtendedStats.INTERACT_WITH_STONE_CHEST);
				PiglinTasks.angerNearbyPiglins(pPlayerEntity, true);
			}
			ExtendedLevelData serverInstance = ExtendedLevelData.serverInstance();
			ServerWorld serverWorld = (ServerWorld) pWorld;
			if (serverInstance.isPlayerBarrowCursed(pPlayerEntity) && pWorld.getBiome(pPlayerEntity.blockPosition()).getRegistryName().toString().equals("lotr:barrow_downs")) {
				ExtendedBarrowWrightEntity barrowWight = new ExtendedBarrowWrightEntity(ExtendedEntities.BARROW_WIGHT.get(), serverWorld);
				boolean spawned = false;
				int tries = 0;
				while (!spawned && tries < 49) {
					double x = pPlayerEntity.getX() + (pWorld.random.nextDouble() - pWorld.random.nextDouble())*5 + 0.5D;
					double y = pPlayerEntity.getY() + pWorld.random.nextInt(3) - 1;
					double z = pPlayerEntity.getZ() + (pWorld.random.nextDouble() - pWorld.random.nextDouble())*5 + 0.5D;
					if (pWorld.getBlockState(new BlockPos((int)x, (int)y, (int)z)).is(Blocks.AIR) && pWorld.getBlockState(new BlockPos((int)x, (int)y+1, (int)z)).is(Blocks.AIR)) {
						barrowWight.setPos(x, y, z);
						serverWorld.addFreshEntity(barrowWight);
						serverWorld.playSound(null, barrowWight, ExtendedSounds.WIGHT_SPAWN.get(), SoundCategory.HOSTILE, 1.0f, 0.7f);
						serverInstance.removeCurse(pPlayerEntity);
						spawned = true;
					}
					tries++;
				}
			}
			return ActionResultType.CONSUME;
		}
	}
}