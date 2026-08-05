package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedBirdEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.Random;

public class BirdEntityRandomFlyGoal extends Goal {

    private final ExtendedBirdEntity bird;
    private final Random random;

    private double targetX;
    private double targetY;
    private double targetZ;

    private int flightTime;

    public BirdEntityRandomFlyGoal(ExtendedBirdEntity bird) {
        this.bird = bird;
        this.random = bird.getRandom();

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {

        if (bird.isPassenger() || bird.isVehicle()) {
            return false;
        }

        // Already flying
        if (bird.isFlying()) {
            return false;
        }

        // Roughly once every 8 ticks while wandering
        if (random.nextInt(8) != 0) {
            return false;
        }

        for (int i = 0; i < 10; i++) {

            double x = bird.getX() + random.nextInt(17) - 8;
            double y = bird.getY() + 4 + random.nextInt(6);
            double z = bird.getZ() + random.nextInt(17) - 8;

            BlockPos pos = new BlockPos(x, y, z);

            if (bird.level.isEmptyBlock(pos)) {
                targetX = x;
                targetY = y;
                targetZ = z;
                return true;
            }
        }

        return false;
    }

    @Override
    public void start() {
        bird.startFlying();

        bird.getMoveControl().setWantedPosition(
                targetX,
                targetY,
                targetZ,
                1.0D
        );

        flightTime = 0;
    }

    @Override
    public void tick() {
        flightTime++;

        // If we somehow lost our destination, set it again.
        if (!bird.getMoveControl().hasWanted()) {
            bird.getMoveControl().setWantedPosition(
                    targetX,
                    targetY,
                    targetZ,
                    1.0D
            );
        }
    }

    @Override
    public boolean canContinueToUse() {

        if (!bird.isFlying()) {
            return false;
        }

        // Close enough to destination
        if (bird.distanceToSqr(targetX, targetY, targetZ) < 4.0D) {
            return false;
        }

        // Prevent endless flight
        return flightTime < 200;
    }

    @Override
    public void stop() {
        bird.stopFlying();
    }
}