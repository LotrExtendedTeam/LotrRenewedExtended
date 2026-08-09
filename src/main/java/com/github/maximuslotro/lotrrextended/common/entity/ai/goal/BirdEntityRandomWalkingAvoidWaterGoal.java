package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedBirdEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class BirdEntityRandomWalkingAvoidWaterGoal extends WaterAvoidingRandomWalkingGoal {

    private final ExtendedBirdEntity bird;

    public BirdEntityRandomWalkingAvoidWaterGoal(ExtendedBirdEntity bird, double speedModifier) {
        super(bird, speedModifier);
        this.bird = bird;
    }

    @Override
    public boolean canUse() {
        return canWalk() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (!canWalk()) {
            this.bird.getNavigation().stop();
            return false;
        }

        return super.canContinueToUse();
    }

    @Override
    public void start() {
        if (canWalk()) {
            super.start();
        }
    }

    private boolean canWalk() {
        return !this.bird.isFlying() && !this.bird.isPassenger() && !this.bird.isVehicle();
    }
}
