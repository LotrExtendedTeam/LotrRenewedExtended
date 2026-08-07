package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
/**
 * Specialized abstract base task for farmer NPC behaviors that require interacting with container tile entities (chests).
 * <p>
 * Handles container interaction lifecycle management, including:
 * <ul>
 *   <li>Temporarily stashing main-hand items during task execution to free the entity's hands.</li>
 *   <li>Ticking chest opening and closing animation states via simulated player events.</li>
 *   <li>Scanning and filtering nearby active container tile entities within horizontal and vertical search bounds.</li>
 * </ul>
 * </p>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingGoalHolder
 * @see ChestTileEntity
 */
public abstract class ExtendedFarmingAbstractChestTask extends ExtendedFarmingAbstractTask {
	/**
	 * Ticks elapsed since initiating a chest interaction cycle.
	 */
	private int chestOpenTime;
	/**
	 * Remembers the item stack held in the NPC's main hand prior to beginning the task,
	 * allowing it to be safely restored when the task finishes or cancels.
	 */
	private ItemStack previousHeldItem;
	/**
	 * Constructs a new chest interaction task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingAbstractChestTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Manages the timed animation sequence for opening and closing a target chest tile entity.
	 * <p>
	 * Triggers the chest open event on the initial tick using a {@link FakePlayerFactory} instance, 
	 * holds the container open for a set duration, and dispatches the close event once complete.
	 * </p>
	 *
	 * @param pServerLevel the world where the chest interaction is taking place
	 * @param chest        the chest tile entity being accessed
	 * @return {@code true} when the opening/closing animation cycle is complete; {@code false} while still in progress
	 */
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
	/**
	 * Prepares the NPC for container interaction.
	 * <p>
	 * Caches the currently held main-hand item, clears the main hand slot for visual/functional 
	 * interaction, and resets the chest interaction timer.
	 * </p>
	 */
	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
		chestOpenTime=0;
	}
	/**
	 * Cleans up container interaction state upon task completion or termination.
	 * <p>
	 * Restores the original main-hand item stack back to the NPC.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
	/**
	 * Scans nearby loaded tile entities within distance and elevation limits to select a valid chest target.
	 * <p>
	 * Validates potential targets against world border bounds, active chunk ticking status, tile entity lifecycle state, 
	 * and task-specific position checks before randomly selecting a candidate.
	 * </p>
	 *
	 * @param taskPosition target position buffer mutated in-place if a valid chest is located
	 * @param pServerLevel world where the tile entity search takes place
	 */
	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		if (cachedWorkBlockPos != null) {
			if (goalHolder.getEntity().distanceToSqr(cachedWorkBlockPos.getX() + 0.5, cachedWorkBlockPos.getY() + 0.5, cachedWorkBlockPos.getZ() + 0.5) <= MAX_DISTANCE_SQ) {
				taskPosition.set(cachedWorkBlockPos);
				return;
			}
		}
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
				if (pos.distSqr(farmhandPos)<ExtendedFarmingGoalHolder.MAXFARMDISTANCE_SQU && Math.abs(pos.getY()-farmhandPos.getY())<=YSPLIT) {
					if (pServerLevel.getChunkSource().isTickingChunk(pos) && pServerLevel.getWorldBorder().isWithinBounds(pos)) {
						if (tileEntity instanceof ChestTileEntity) {
							if (isValidPosForTask(pos, pServerLevel) && hasHoeItemFrameOnFront(pos, pServerLevel)) {
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
	/**
	 * Checks whether the given chest position, or its connected neighbor if part of a double chest,
	 * has an item frame mounted on its front face containing a hoe.
	 *
	 * @param pos          the position of the target chest block
	 * @param pServerLevel the server world context in which to query block states and entities
	 * @return {@code true} if an item frame holding a {@link HoeItem} is mounted on the front face of this chest or its connected half; {@code false} otherwise
	 */
	private boolean hasHoeItemFrameOnFront(BlockPos pos, ServerWorld pServerLevel) {
		if (checkSingleChestFront(pos, pServerLevel)) {
			return true;
		}
		BlockState state = pServerLevel.getBlockState(pos);
		if (state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
			BlockPos otherHalfPos = pos.relative(ChestBlock.getConnectedDirection(state));
			if(pServerLevel.getBlockEntity(otherHalfPos) instanceof ChestTileEntity) {
				if (pServerLevel.getBlockState(otherHalfPos).getValue(ChestBlock.FACING) == state.getValue(ChestBlock.FACING)) {
					if (checkSingleChestFront(otherHalfPos, pServerLevel)) {
						return true;
					}
				}
			}
		}

		return false;
	}
	/**
	 * Evaluates a single block position to determine if an item frame is mounted on its front facing direction
	 * and holds a hoe item stack.
	 *
	 * @param pos          the specific block position of the chest segment to inspect
	 * @param pServerLevel the server world context used to query entities and state
	 * @return {@code true} if an item frame facing the chest's front direction contains a {@link HoeItem}; {@code false} otherwise
	 */
	private boolean checkSingleChestFront(BlockPos pos, ServerWorld pServerLevel) {
		BlockState state = pServerLevel.getBlockState(pos);
		
		Direction chestFacing = state.getValue(ChestBlock.FACING);
		BlockPos frontPos = pos.relative(chestFacing);
		List<ItemFrameEntity> itemFrames = pServerLevel.getEntitiesOfClass(ItemFrameEntity.class, new AxisAlignedBB(frontPos));
		for (ItemFrameEntity frame : itemFrames) {
			if (frame.getDirection() == chestFacing) {
				ItemStack displayedItem = frame.getItem();
				if (!displayedItem.isEmpty() && displayedItem.getItem() instanceof HoeItem) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Evaluates whether the NPC is within interaction range of the target chest position.
	 *
	 * @param farmer    the entity attempting to access the chest
	 * @param targetPos position of the target container
	 * @return {@code true} if the entity is close enough to access the target chest
	 */
	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 2);
	}
}
