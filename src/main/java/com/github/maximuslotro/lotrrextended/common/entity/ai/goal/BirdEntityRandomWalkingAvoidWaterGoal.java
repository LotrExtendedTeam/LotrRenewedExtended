package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedBirdEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class BirdEntityRandomWalkingAvoidWaterGoal extends WaterAvoidingRandomWalkingGoal {

    private static final int POSITION_ATTEMPTS = 8;
    private static final double MIN_HORIZONTAL_TARGET_DISTANCE_SQR = 1.0D;

    private final ExtendedBirdEntity bird;
    private boolean pathStarted;

    public BirdEntityRandomWalkingAvoidWaterGoal(ExtendedBirdEntity bird, double speedModifier) {
        super(bird, speedModifier);
        this.bird = bird;
        this.setInterval(40);
    }

    @Override
    public boolean canUse() {
        this.pathStarted = false;

        if (!canWalk()) {
            return false;
        }

        if (this.bird.consumeGroundedMovementRequest()) {
            this.trigger();
        }

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (!canWalk()) {
            return false;
        }

        if (!this.pathStarted || this.bird.getNavigation().isStuck()) {
            this.bird.getNavigation().stop();
            return false;
        }

        return super.canContinueToUse();
    }

    @Override
    public void start() {
        this.pathStarted = canWalk() && this.bird.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);

        if (!this.pathStarted) {
            this.bird.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.pathStarted = false;
        if (!this.bird.isFlying()) {
            this.bird.getNavigation().stop();
        }
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        for (int i = 0; i < POSITION_ATTEMPTS; i++) {
            Vector3d position = super.getPosition();

            if (position != null && isSafeWalkingTarget(position)) {
                return position;
            }
        }

        return null;
    }

    private boolean isSafeWalkingTarget(Vector3d position) {
        double xOffset = position.x - this.bird.getX();
        double zOffset = position.z - this.bird.getZ();

        if (xOffset * xOffset + zOffset * zOffset < MIN_HORIZONTAL_TARGET_DISTANCE_SQR) {
            return false;
        }

        if (!this.bird.level.noCollision(this.bird, this.bird.getBoundingBox().move(xOffset, position.y - this.bird.getY(), zOffset))) {
            return false;
        }

        Path path = this.bird.getNavigation().createPath(new BlockPos(position.x, position.y, position.z), 1);
        return path != null && path.canReach();
    }

    private boolean canWalk() {
        return !this.bird.isFlying()
                && !this.bird.isPassenger()
                && !this.bird.isVehicle()
                && (this.bird.isOnGround() || this.bird.isInWaterOrBubble());
    }
}
