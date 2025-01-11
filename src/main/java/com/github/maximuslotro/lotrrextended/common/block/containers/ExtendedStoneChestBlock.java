package com.github.maximuslotro.lotrrextended.common.block.containers;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedStoneChestTileEntity;

import lotr.common.data.ExtendedLevelData;
import lotr.common.entity.npc.ExtendedBarrowWightEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
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

	/**
	 * Ensure stone chests placed by players can't have forced wight spawns
	 * */
	@Override
	public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		TileEntity tileentity = pLevel.getBlockEntity(pPos);
		if (tileentity instanceof ExtendedStoneChestTileEntity) {
			((ExtendedStoneChestTileEntity)tileentity).setHasSpawnedWight(true);
		}

	}

	/**
	 * Logic to check if chest should spawn a wight, depending on the biome and spawn conditions
	 * */
	@Override
	public ActionResultType use(BlockState pBlockState, World pWorld, BlockPos locPos, PlayerEntity pPlayerEntity, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (pWorld.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileEntity = pWorld.getBlockEntity(locPos);
			boolean spawned = false;
			if(tileEntity instanceof ExtendedStoneChestTileEntity) {
				ExtendedStoneChestTileEntity stoneTileEntity = (ExtendedStoneChestTileEntity)tileEntity;
				// Logic for barrow downs
				if (pWorld.getBiome(locPos).getRegistryName().toString().equals("lotr:barrow_downs")) {
					// Run cursed spawn logic since chest has already spawned a Wight
					if(stoneTileEntity.hasSpawnedWight()) {
						spawned = spawnWightCursed(pWorld, locPos, pPlayerEntity, false);
					}else {
						// If server config is set to only accept cursed spawns, then remove from cursed spawn list
						if(ExtendedServerConfig.barrowWightChestFirstSpawnChance.get()==0) {
							spawned = spawnWightCursed(pWorld, locPos, pPlayerEntity, false);
						}else {
							// Otherwise check if random chance allows for entity spawning
							if(pWorld.random.nextInt(100)>=ExtendedServerConfig.barrowWightChestFirstSpawnChance.get()) {
								spawned = spawnWightCursed(pWorld, locPos, pPlayerEntity, true);
							}
						}
						// Set chest spawned value if had forced spawned, or if server config is set to only accept cursed spawns
						if(spawned) {
							stoneTileEntity.setHasSpawnedWight(true);
						}
					}
				}
				// Add more logic for other barrows in different regions
			}
			INamedContainerProvider inamedcontainerprovider = this.getMenuProvider(pBlockState, pWorld, locPos);
			// Disable chest opening if wight was spawned
			if (!spawned && inamedcontainerprovider != null) {
				pPlayerEntity.openMenu(inamedcontainerprovider);
				pPlayerEntity.awardStat(ExtendedStats.INTERACT_WITH_STONE_CHEST);
				PiglinTasks.angerNearbyPiglins(pPlayerEntity, true);
			}
			return ActionResultType.CONSUME;
		}
	}

	/**
	 * Method to spawn a barrow wight.
	 * if {forcedSpawn} is True, bypass the cursed check.
	 * @return true if wight was spawned
	 * */
	private boolean spawnWightCursed(World pWorld, BlockPos locPos, PlayerEntity pPlayerEntity, boolean forcedSpawn) {
		ServerWorld serverWorld = (ServerWorld) pWorld;
		ExtendedLevelData serverInstance = ExtendedLevelData.serverInstance();
		if((serverInstance.isPlayerBarrowCursed(pPlayerEntity) || forcedSpawn)) {
			ExtendedBarrowWightEntity barrowWight = new ExtendedBarrowWightEntity(ExtendedEntities.BARROW_WIGHT.get(), serverWorld);
			int tries = 0;
			while (tries < 49) {
				double x = pPlayerEntity.getX() + (pWorld.random.nextDouble() - pWorld.random.nextDouble())*5 + 0.5D;
				double y = pPlayerEntity.getY() + pWorld.random.nextInt(3) - 1;
				double z = pPlayerEntity.getZ() + (pWorld.random.nextDouble() - pWorld.random.nextDouble())*5 + 0.5D;
				if (pWorld.getBlockState(new BlockPos((int)x, (int)y, (int)z)).is(Blocks.AIR) && pWorld.getBlockState(new BlockPos((int)x, (int)y+1, (int)z)).is(Blocks.AIR)) {
					barrowWight.setPos(x, y, z);
					serverWorld.addFreshEntity(barrowWight);
					serverWorld.playSound(null, barrowWight, ExtendedSounds.WIGHT_SPAWN.get(), SoundCategory.HOSTILE, 1.0f, 0.7f);
					if(!forcedSpawn) {
						serverInstance.removeCurse(pPlayerEntity);
					}
					return true;
				}
				tries++;
			}
		}
		return false;
	}
}