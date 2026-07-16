package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * Based on {@link FarmTask}, {@link FarmerWorkTask}, and {@link BoneMealCropsTask} villager tasks
 * */
public class ExtendedFarmingGoalHolder extends Goal {
	private final NPCEntity farmhand;
	protected final ExtendedINPCEntity farmhandData;
	protected final ServerWorld level;
	private final double walkSpeedModifier;
	protected Path path;
	protected final PathNavigator pathNav;
	protected ExtendedFarmingAbstractTask currentTask = null;
	protected Set<ExtendedFarmingAbstractTask> farmingTasks = new LinkedHashSet<>();
	private BlockPos targetFarmBlock;
	private static final int taskRetryCount = 16;//tweak how many tries this goal tries to find a valid task
	private final SelectionMode mode = SelectionMode.PRIORITY;
	private boolean isActionCompleted = false; // Prevents ticking after completion

	public ExtendedFarmingGoalHolder(NPCEntity farmhand, double walkSpeedModifier) {
		this.farmhand = farmhand;
		this.level = farmhand.level.isClientSide() ? null : (ServerWorld) farmhand.level;
		this.farmhandData = (ExtendedINPCEntity) farmhand;
		this.walkSpeedModifier = walkSpeedModifier;
		this.pathNav = farmhand.getNavigation();
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public ExtendedFarmingGoalHolder standardOrder() {
		farmingTasks.add(new ExtendedFarmingDepositTask(this));
		farmingTasks.add(new ExtendedFarmingTillTask(this));
		farmingTasks.add(new ExtendedFarmingCollectTask(this));
		farmingTasks.add(new ExtendedFarmingPlantingTask(this));
		farmingTasks.add(new ExtendedFarmingHarvestTask(this));
		farmingTasks.add(new ExtendedFarmingBonemealTask(this));
		return this;
	}

	@Override
	public boolean canUse() {
		if(farmingTasks.isEmpty()) {
			throw new IllegalStateException("Farmhand can't have no farming tasks added!");
		}
		if (level==null || !net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, farmhand)) {
			return false;
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

	private boolean tryAssignTask(ExtendedFarmingAbstractTask task, BlockPos.Mutable taskPosition) {
		task.setRandomBlockByTask(taskPosition, level);
		if (!task.isValidPosForTask(taskPosition, level)) {
			return false;
		}
		// Use an immutable position for path generation to ensure the pathfinder doesn't ingest mutations (ask me how I know)
		BlockPos targetPos = taskPosition.immutable();
		Path taskPath = pathNav.createPath(targetPos, 0);
		if (taskPath != null) {
			this.targetFarmBlock = targetPos;
			this.currentTask = task;
			this.path = taskPath;
			return true;
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		if (isActionCompleted || currentTask == null || targetFarmBlock == null) {
			return false;
		}
		// Allow for executing action, even when navigation has ended at destination
		boolean headingOrArrived = pathNav.isInProgress() || currentTask.closeEnoughToExecuteTask(farmhand, targetFarmBlock);
		return headingOrArrived && !pathNav.isStuck() && currentTask.isTaskValidForNPC(this.level) && currentTask.isValidPosForTask(targetFarmBlock, level);
	}

	@Override
	public void start() {
		isActionCompleted = false;
		currentTask.startTask();
		this.pathNav.moveTo(this.path, this.walkSpeedModifier);
	}

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
	}

	@Override
	public void tick() {
		if (targetFarmBlock == null) {
			return;
		}
		farmhand.getLookControl().setLookAt(targetFarmBlock.getX() + 0.5D, targetFarmBlock.getY() + 0.5D, targetFarmBlock.getZ() + 0.5D);
		if(currentTask.closeEnoughToExecuteTask(farmhand, targetFarmBlock)) {
			currentTask.executeTaskChecked(targetFarmBlock, level);
			isActionCompleted = true; //Allow for task to be stopped even if there is pathfinding distance to target
		}
	}

	public enum SelectionMode {
		BALANCED,    // Option B: Position -> Task (Performance-friendly)
		PRIORITY     // Option A: Task -> Position (Task-focused)
	}

	public NPCEntity getEntity() {
		return farmhand;
	}
}
