package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

/**
 * Based on {@link FarmTask}, {@link FarmerWorkTask}, and {@link BoneMealCropsTask} villager tasks
 * */
public class ExtendedFarmingGoalsGoal extends Goal {
	protected final NPCEntity farmhand;
	protected final ExtendedINPCEntity farmhandData;
	protected final World level;

	public ExtendedFarmingGoalsGoal(NPCEntity farmhand) {
		this.farmhand = farmhand;
		this.level = farmhand.level;
		this.farmhandData = (ExtendedINPCEntity) farmhand;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, farmhand)) {
			return false;
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return this.canUse();
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void tick() {
	}

}
