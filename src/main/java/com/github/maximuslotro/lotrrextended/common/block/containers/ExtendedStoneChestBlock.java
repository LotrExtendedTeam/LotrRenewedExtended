package com.github.maximuslotro.lotrrextended.common.block.containers;

import java.util.function.Supplier;

import javax.annotation.Nullable;

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
/**
 * Extended stone chest block with custom behavior for spawning Barrow Wights
 * under specific biome and game conditions.
 * 
 * <p>This chest prevents forced Wight spawns when placed by players,
 * and contains logic to spawn cursed Wight entities when opened in
 * designated biomes, such as the Barrow Downs.</p>
 */
public class ExtendedStoneChestBlock extends ExtendedSingleChestBlock {
	/**
	 * Constructs an {@code ExtendedStoneChestBlock} with the given tile entity supplier.
	 * 
	 * @param tileentityholder Supplier for the associated chest tile entity.
	 */
	public ExtendedStoneChestBlock(Supplier<ChestTileEntity> tileentityholder) {
		super(tileentityholder);
	}

	/**
	 * Called when the chest is placed by a player.
	 * 
	 * <p>Marks the tile entity as having spawned a Wight to prevent forced spawns
	 * from this chest instance.</p>
	 * 
	 * @param pLevel The world where the block is placed.
	 * @param pPos The block position.
	 * @param pState The block state.
	 * @param pPlacer The entity placing the block.
	 * @param pStack The item stack used to place the block.
	 */
	@Override
	public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		TileEntity tileentity = pLevel.getBlockEntity(pPos);
		if (tileentity instanceof ExtendedStoneChestTileEntity) {
			((ExtendedStoneChestTileEntity)tileentity).setHasSpawnedWight(true);
		}

	}
	/**
	 * Handles player breaking the chest.
	 * 
	 * <p>If the player is in the Barrow Downs biome and the chest has not spawned a Wight,
	 * and the chest contain contents spawn a wight. Otherwise, opens the chest container normally.</p>
	 * 
	 * @param pState The block state of the chest.
	 * @param pLevel The world instance.
	 * @param pPos The position of the chest.
	 * @param pNewState The new block state of the chest location
	 * @param pIsMoving Something
	 */
	@Override
	public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (!pState.is(pNewState.getBlock())) {
	         TileEntity tileentity = pLevel.getBlockEntity(pPos);
	         if (tileentity instanceof ExtendedStoneChestTileEntity ) {
	        	 ExtendedStoneChestTileEntity te = ((ExtendedStoneChestTileEntity)tileentity);
	        	 // Since we haven't spawned a wight, and we are not empty assume player is trying to break chest
	        	 if(!te.hasSpawnedWight() && !te.isEmpty() && pLevel.getBiome(pPos).getRegistryName().toString().equals("lotr:barrow_downs")) {
	        		 te.setHasSpawnedWight(spawnWightCursed(pLevel, pPos, null, true));
	        	 }
	         }
	         super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
	      }
	}


	/**
	 * Handles player interaction with the chest.
	 * 
	 * <p>If the player is in the Barrow Downs biome and the chest has not spawned a Wight,
	 * attempts to spawn a cursed Barrow Wight based on config and random chance.
	 * Otherwise, opens the chest container normally.</p>
	 * 
	 * @param pBlockState The block state of the chest.
	 * @param pWorld The world instance.
	 * @param locPos The position of the chest.
	 * @param pPlayerEntity The player interacting.
	 * @param p_225533_5_ The hand used for interaction.
	 * @param p_225533_6_ The ray trace result.
	 * @return {@code ActionResultType.CONSUME} to consume the interaction, {@code ActionResultType.SUCCESS} if client side.
	 */
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
	 * Spawns a cursed Barrow Wight entity near the player under certain conditions.
	 * 
	 * <p>If {@code forcedSpawn} is true, bypasses curse checks. Tries up to 49 times
	 * to find a suitable spawn location nearby in air blocks.</p>
	 * 
	 * @param pWorld The world instance (must be a server world).
	 * @param locPos The position of the chest.
	 * @param pPlayerEntity The player entity near which the Wight will spawn.
	 * @param forcedSpawn If true, bypasses player curse requirement.
	 * @return {@code true} if the Wight was successfully spawned, {@code false} otherwise.
	 */
	private boolean spawnWightCursed(World pWorld, BlockPos locPos, @Nullable PlayerEntity pPlayerEntity, boolean forcedSpawn) {
		ServerWorld serverWorld = (ServerWorld) pWorld;
		ExtendedLevelData serverInstance = ExtendedLevelData.serverInstance();
		if(forcedSpawn || (pPlayerEntity== null || serverInstance.isPlayerBarrowCursed(pPlayerEntity))) {
			ExtendedBarrowWightEntity barrowWight = new ExtendedBarrowWightEntity(ExtendedEntities.BARROW_WIGHT.get(), serverWorld);
			int tries = 0;
			while (tries < 49) {
				double x = locPos.getX() + (pWorld.random.nextDouble() - pWorld.random.nextDouble())*5 + 0.5D;
				double y = locPos.getY() + pWorld.random.nextInt(3) - 1;
				double z = locPos.getZ() + (pWorld.random.nextDouble() - pWorld.random.nextDouble())*5 + 0.5D;
				if (pWorld.getBlockState(new BlockPos((int)x, (int)y, (int)z)).is(Blocks.AIR) && pWorld.getBlockState(new BlockPos((int)x, (int)y+1, (int)z)).is(Blocks.AIR)) {
					barrowWight.setPos(x, y, z);
					serverWorld.addFreshEntity(barrowWight);
					serverWorld.playSound(null, barrowWight, ExtendedSounds.WIGHT_SPAWN.get(), SoundCategory.HOSTILE, 1.0f, 0.7f);
					if(!forcedSpawn && pPlayerEntity!=null) {
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