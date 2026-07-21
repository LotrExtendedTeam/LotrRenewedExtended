package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayerFactory;
/**
 * Task responsible for converting raw soil blocks (such as dirt or grass) into tilled farmland.
 * <p>
 * This task validates target ground positions using Forge's {@link BlockState#getToolModifiedState} with 
 * a hoe tool query. To ensure farmland validity and stability, target positions are checked for nearby 
 * water sources and guarded against unstable sub-layers like sand. When executed, the NPC clears any 
 * replaceable surface obstructions and tills both the target block and eligible adjacent horizontal blocks 
 * in a single sweep.
 * </p>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingGoalHolder
 * @see FarmlandBlock
 * @see ToolType#HOE
 */
public class ExtendedFarmingTillTask extends ExtendedFarmingAbstractTask{
	/**
	 * Constructs a new ground tilling task bound to the specified goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingTillTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Determines whether the tilling task is generally available for assignment to the NPC.
	 *
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} unconditionally, allowing position searching whenever idle
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		return true;
	}
	/**
	 * Validates whether a target ground-level block position can be converted into farmland.
	 * <p>
	 * Confirms that the position above is replaceable, skips already tilled {@link FarmlandBlock} states, 
	 * queries Forge tool interactions via {@link FakePlayerFactory}, verifies that the sub-block is not sand 
	 * ({@link BlockTags#SAND}), and confirms water presence within hydration range.
	 * </p>
	 *
	 * @param groundLevelPos target soil block position
	 * @param pServerLevel   world where the check takes place
	 * @return {@code true} if the ground block can be tilled into hydrated farmland
	 */
	@Override
	public boolean isValidPosForTask(BlockPos groundLevelPos, ServerWorld pServerLevel) {
		if(isRePlaceablePos(groundLevelPos.above(), pServerLevel)) {
			BlockState groundState = pServerLevel.getBlockState(groundLevelPos);
			if(groundState.getBlock() instanceof FarmlandBlock) {
				return false;
			}
			BlockState modified = groundState.getToolModifiedState(pServerLevel, groundLevelPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.getEntity().getMainHandItem(), ToolType.HOE);
			if(modified!=null && modified.getBlock() instanceof FarmlandBlock) {
				return !pServerLevel.getBlockState(groundLevelPos.below()).is(BlockTags.SAND) && isNearWater(pServerLevel, groundLevelPos);
			}
		}
		return false;
	}
	/**
	 * Converts the target ground position and eligible adjacent horizontal positions into farmland.
	 * <p>
	 * Swings the main hand and attempts tilling on the origin block as well as all four horizontal 
	 * neighbors (North, South, East, West). Plays {@link SoundEvents#HOE_TILL} if at least one block state 
	 * was modified.
	 * </p>
	 *
	 * @param groundLevelPos primary target soil coordinates
	 * @param pServerLevel   world where execution occurs
	 * @return {@code true} indicating completion of the tilling action
	 */
	@Override
	public boolean executeTask(BlockPos groundLevelPos, ServerWorld pServerLevel) {
		goalHolder.getEntity().swing(Hand.MAIN_HAND);
		boolean playTillSound = tryTillBlock(groundLevelPos, pServerLevel);
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos sidePos = groundLevelPos.relative(dir);
			if(this.isValidPosForTask(sidePos, pServerLevel)) {
				playTillSound |= tryTillBlock(sidePos, pServerLevel);
			}
		}
		if(playTillSound) {
			pServerLevel.playSound(null, groundLevelPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		return true;
	}
	/**
	 * Helper method attempting to clear surface cover and convert a single block to farmland.
	 * <p>
	 * Clears any replaceable blocks directly above the ground before applying the modified 
	 * tool state to the ground block.
	 * </p>
	 *
	 * @param groundLevelPos soil block coordinates to till
	 * @param pServerLevel   world where the block modification occurs
	 * @return {@code true} if the block was successfully updated to a tilled state
	 */
	private boolean tryTillBlock(BlockPos groundLevelPos, ServerWorld pServerLevel) {
		if (isRePlaceablePos(groundLevelPos.above(), pServerLevel)) {
			pServerLevel.destroyBlock(groundLevelPos.above(), false);
		};
		BlockState modified = pServerLevel.getBlockState(groundLevelPos).getToolModifiedState(pServerLevel, groundLevelPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.getEntity().getMainHandItem(), ToolType.HOE);
		if (modified != null) {
			pServerLevel.setBlock(groundLevelPos, modified, 11);
			return true;
		}
		return false;
	}
}
