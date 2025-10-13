package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.function.Predicate;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;

public class ExtendedAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T>{
	protected final Predicate<LivingEntity> shouldProcedPredicate;
	public ExtendedAvoidEntityGoal(CreatureEntity mob, Class<T> avoidClass, float maxDist, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> selfPredicate) {
		super(mob, avoidClass, maxDist, walkSpeedModifier, sprintSpeedModifier);
		this.shouldProcedPredicate = selfPredicate;
	}

	@Override
	public boolean canUse() {
		if(shouldProcedPredicate.test(mob)) {
			return super.canUse();
		}
		return false;
	}
}
