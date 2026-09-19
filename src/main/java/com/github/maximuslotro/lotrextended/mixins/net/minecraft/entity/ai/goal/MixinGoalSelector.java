package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.ai.goal;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedGoalSelectorAccessor;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;

@Mixin(GoalSelector.class)
public class MixinGoalSelector implements ExtendedGoalSelectorAccessor {
	@Shadow
    @Final
    private Set<PrioritizedGoal> availableGoals;

	/**
     * Stops and removes all AI goals matching the specified priority.
     * 
     * @param priority Target priority integer to remove
     */
    @Override
    public void removeGoalByPriority$Extended(int priority) {
        this.availableGoals.stream()
            .filter(goal -> goal.getPriority() == priority)
            .filter(PrioritizedGoal::isRunning)
            .forEach(PrioritizedGoal::stop);
        this.availableGoals.removeIf(goal -> goal.getPriority() == priority);
    }
}
