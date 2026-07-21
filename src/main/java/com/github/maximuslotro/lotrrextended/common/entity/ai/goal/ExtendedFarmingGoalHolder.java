package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

/**
 * AI goal manager that implements a modular farming tasks for NPC farmhands.
 * <p>
 * Serves as an umbrella {@link Goal} container holding registered {@link ExtendedFarmingAbstractTask} 
 * implementations (such as planting, harvesting, tilling, collecting, and depositing). Task selection 
 * is governed by a configurable {@link SelectionMode} and throttled by a proficiency success chance calculation. 
 * Once a task and reachable target block are selected, this goal manages path navigation, entity looking control, 
 * task execution, and automatic anti-deadlock timeouts.
 * </p>
 * 
 * Based on vanilla brain tasks:
 * <ul>
 *   <li>{@link FarmTask}</li>
 *   <li>{@link FarmerWorkTask}</li>
 *   <li>{@link BoneMealCropsTask}</li>
 * </ul>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedINPCEntity
 * @see ExtendedUnitOrders#FARMING
 */
public class ExtendedFarmingGoalHolder extends Goal {
	/** The NPC entity performing the farming AI actions. */
	private final NPCEntity farmhand;
	/** Extended NPC interface providing access to unit info, farmhand data, and farmer level. */
	protected final ExtendedINPCEntity farmhandData;
	/** The server-side world instance where AI routines run. Null on client side. */
	protected final ServerWorld level;
	/** Navigation movement speed modifier applied when traveling to farm target blocks. */
	private final double walkSpeedModifier;
	/** Calculated path navigation route to the currently selected farm block target. */
	protected Path path;
	/** Path navigator instance handling entity movement. */
	protected final PathNavigator pathNav;
	/** Currently active sub-task selected for execution, or {@code null} if idle. */
	protected ExtendedFarmingAbstractTask currentTask = null;
	/** Registered set of farming tasks evaluated during task selection sweeps. */
	protected Set<ExtendedFarmingAbstractTask> farmingTasks = new LinkedHashSet<>();
	/** The target block position selected for the active task. */
	private BlockPos targetFarmBlock;
	/** Maximum number of random position evaluation attempts per task during target search. */
	private static final int taskRetryCount = 16;//tweak how many tries this goal tries to find a valid task
	/** Evaluation mode determining whether tasks are prioritized by order or by spatial position. */
	private final SelectionMode mode = SelectionMode.PRIORITY;
	/** Flag preventing further tick logic execution once the task has successfully completed. */
	private boolean isActionCompleted = false;
	/** Ticks elapsed since the goal began running, used for anti-deadlock timeout safety. */
	protected int tickTime;
	/** Maximum allowable squared distance (24 blocks squared = 576) between NPC and target position. */
	protected static final double MAXFARMDISTANCE_SQU = Math.pow(24, 2);
	/** Flat bonus added to the base execution chance calculation. */
	private final float additionalProficiency;
	/**
	 * Constructs a new farming goal holder for the specified NPC.
	 *
	 * @param farmhand               the NPC entity taking on farming duties
	 * @param walkSpeedModifier      movement speed multiplier applied during path navigation
	 * @param pAdditionalProficiency flat proficiency modifier boosting execution chance checks
	 */
	public ExtendedFarmingGoalHolder(NPCEntity farmhand, double walkSpeedModifier, float pAdditionalProficiency) {
		this.farmhand = farmhand;
		this.level = farmhand.level.isClientSide() ? null : (ServerWorld) farmhand.level;
		this.farmhandData = (ExtendedINPCEntity) farmhand;
		this.walkSpeedModifier = walkSpeedModifier;
		this.pathNav = farmhand.getNavigation();
		this.additionalProficiency = pAdditionalProficiency;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	/**
	 * Registers the standard set of default farming tasks in recommended priority order.
	 * <p>
	 * Added tasks include depositing, tilling, collecting ground items, crafting bonemeal, 
	 * planting, harvesting, bonemealing crops, and acquiring seed inventory.
	 * </p>
	 *
	 * @return this goal holder instance for method chaining
	 */
	public ExtendedFarmingGoalHolder standardOrder() {
		farmingTasks.add(new ExtendedFarmingDepositTask(this));
		farmingTasks.add(new ExtendedFarmingTillTask(this));
		farmingTasks.add(new ExtendedFarmingCollectTask(this));
		farmingTasks.add(new ExtendedFarmingBonemealCraftTask(this));
		farmingTasks.add(new ExtendedFarmingPlantingTask(this));
		farmingTasks.add(new ExtendedFarmingHarvestTask(this));
		farmingTasks.add(new ExtendedFarmingBonemealTask(this));
		farmingTasks.add(new ExtendedFarmingAquireTask(this));
		return this;
	}
	/**
	 * Determines whether the farming goal can be activated.
	 * <p>
	 * Verifies mob griefing world settings, checks active unit orders (if a hired unit), evaluates 
	 * farmer proficiency execution chance via {@link #canFarmerExecuteChance()}, and iterates over 
	 * registered tasks according to the active {@link SelectionMode} to find a valid task and pathable 
	 * block position.
	 * </p>
	 *
	 * @return {@code true} if a valid task and target location were successfully assigned
	 * @throws IllegalStateException if no farming tasks have been registered in {@link #farmingTasks}
	 */
	@Override
	public boolean canUse() {
		if(farmingTasks.isEmpty()) {
			throw new IllegalStateException("Farmhand can't have no farming tasks added!");
		}
		if (level==null || !net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, farmhand)) {
			return false;
		}
		if(farmhandData.getUnitInfo().isNpcActiveDuty() && farmhandData.getUnitInfo().getOrders()!=ExtendedUnitOrders.FARMING) {
			return false; //Only allow hired units that are farming to run, regular NPCs are unaffected
		}
		if (!canFarmerExecuteChance()) {
			return false; // Failed level and bonus check
		}
		BlockPos.Mutable taskPosition = new BlockPos.Mutable();
		// TODO decide if there is a better way to randomize task block, while still keeping the farming delay
		if (this.mode == SelectionMode.PRIORITY) {
			for (ExtendedFarmingAbstractTask task : farmingTasks) {
				if (!task.isTaskValidForNPC(level)) {
					continue;
				}
				for (int i = 0; i < taskRetryCount; i++) {
					if (tryAssignTask(task, taskPosition)) {
						return true;
					}
				}
			}
		} else {
			for (int i = 0; i < taskRetryCount; i++) {
				for (ExtendedFarmingAbstractTask task : farmingTasks) {
					if (!task.isTaskValidForNPC(level)) {
						continue;
					}
					if (tryAssignTask(task, taskPosition)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * Calculates whether the NPC successfully triggers a farming action attempt based on level and proficiency.
	 * <p>
	 * Linear interpolation maps farmer level (1–100) onto a probability scale starting from a base 
	 * ({@code 0.1 + additionalProficiency}) capped at a maximum success chance of {@code 0.5} (50%).
	 * </p>
	 *
	 * @return {@code true} if the random roll passes the calculated success threshold
	 */
	public boolean canFarmerExecuteChance() {
		float baseChance = 0.1f + additionalProficiency;
		int farmLevel = Math.max(1, Math.min(100, farmhandData.getFarmhandInfo().getFarmerLevel()));
		float progress = (farmLevel - 1) / 99.0f;
		float successChance = Math.min(0.5F, baseChance + (0.5F - baseChance) * progress);
		return farmhand.random.nextFloat() < successChance;
	}
	/**
	 * Helper method that attempts to locate and validate a target position for a given task, generating a path.
	 * <p>
	 * Evaluates candidate positions provided by {@link ExtendedFarmingAbstractTask#setRandomBlockByTask}, 
	 * checks target position validity, verifies distance limits, and attempts to generate a navigation path. 
	 * Converts mutable block positions into immutable instances before pathing to prevent mutation bugs.
	 * </p>
	 *
	 * @param task         the candidate task being evaluated
	 * @param taskPosition mutable coordinate instance mutated during location searching
	 * @return {@code true} if a valid position and navigation path were established for the task
	 */
	private boolean tryAssignTask(ExtendedFarmingAbstractTask task, BlockPos.Mutable taskPosition) {
		task.setRandomBlockByTask(taskPosition, level);
		if (!task.isValidPosForTask(taskPosition, level)) {
			task.invalidateCache();
			return false;
		}
		// Use an immutable position for path generation to ensure the pathfinder doesn't ingest mutations (ask me how I know) Max derp
		BlockPos targetPos = taskPosition.immutable();
		if (!pathNav.isDone() && farmhand.distanceToSqr(targetPos.getX(), targetPos.getY(), targetPos.getZ()) > MAXFARMDISTANCE_SQU) {
			return false;
		}
		Path taskPath = pathNav.createPath(targetPos, 0);
		if (taskPath != null) {
			targetFarmBlock = targetPos;
			currentTask = task;
			path = taskPath;
			return true;
		}
		return false;
	}
	/**
	 * Checks whether the current task goal should continue execution.
	 * <p>
	 * Verifies that the task action has not finished, target reference points exist, navigation is active 
	 * or within range, navigation is not stuck, and both NPC and location state remain valid for the active task.
	 * </p>
	 *
	 * @return {@code true} if the active task can continue moving or executing
	 */
	@Override
	public boolean canContinueToUse() {
		if (isActionCompleted || currentTask == null || targetFarmBlock == null) {
			return false;
		}
		// Allow for executing action, even when navigation has ended at destination
		boolean headingOrArrived = pathNav.isInProgress() || currentTask.closeEnoughToExecuteTask(farmhand, targetFarmBlock);
		return headingOrArrived && !pathNav.isStuck() && currentTask.isTaskStillValidForNPC(this.level) && currentTask.isValidPosForTask(targetFarmBlock, level);
	}
	/**
	 * Initializes the goal cycle.
	 * <p>
	 * Resets tick counter and completion flags, invokes {@link ExtendedFarmingAbstractTask#startTask()} on 
	 * the assigned task, and instructs path navigation to move toward the pre-calculated target path.
	 * </p>
	 */
	@Override
	public void start() {
		tickTime = 0;
		isActionCompleted = false;
		currentTask.startTask();
		pathNav.moveTo(path, walkSpeedModifier);
	}
	/**
	 * Stops goal execution and cleans up internal references.
	 * <p>
	 * Halts active navigation, invokes {@link ExtendedFarmingAbstractTask#stopTask()} on the active task, 
	 * and clears task, path, and target coordinate references.
	 * </p>
	 */
	@Override
	public void stop() {
		pathNav.stop();
		if(currentTask!=null) {
			currentTask.stopTask();
			currentTask = null;
		}
		path=null;
		targetFarmBlock=null;
		isActionCompleted = false;
		tickTime=0;
	}
	/**
	 * Performs per-tick execution logic for the active goal.
	 * <p>
	 * Increments execution timer, points entity look control toward the target block center, stops navigation 
	 * and executes {@link ExtendedFarmingAbstractTask#executeTask} once within execution range, and triggers 
	 * a safety completion flag if ticks exceed 300 (15 seconds) to prevent deadlock.
	 * </p>
	 */
	@Override
	public void tick() {
		tickTime++;
		if (targetFarmBlock == null) {
			return;
		}
		farmhand.getLookControl().setLookAt(targetFarmBlock.getX() + 0.5D, targetFarmBlock.getY() + 0.5D, targetFarmBlock.getZ() + 0.5D);
		if(currentTask.closeEnoughToExecuteTask(farmhand, targetFarmBlock)) {
			pathNav.stop();
			// Don't worry about spot being valid since it was just checked during canContinueToUse()
			if(currentTask.executeTask(targetFarmBlock, level)) {//Allow tasks to sit at a spot for a bit
				isActionCompleted = true; //Allow for task to be stopped even if there is pathfinding distance to target
			}
		}
		if(tickTime>300) {
			isActionCompleted = true; //Because we allow tasks to persist when at location, ensure it is canceled if deadlock happens
		}
	}

	/**
	 * Strategy selection mode governing how tasks and target positions are evaluated during goal selection.
	 */
	public enum SelectionMode {
		/** Position -> Task mode; prioritizes spatial efficiency and performance. */
		BALANCED,
		/** Task -> Position mode; prioritizes task order priority over spatial proximity. */
		PRIORITY
	}
	/**
	 * Gets the underlying NPC entity managed by this goal holder.
	 *
	 * @return the farmhand NPC entity
	 */
	public NPCEntity getEntity() {
		return farmhand;
	}
}
