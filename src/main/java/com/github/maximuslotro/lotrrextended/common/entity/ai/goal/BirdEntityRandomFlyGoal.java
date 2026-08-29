package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedBirdEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;

import java.util.EnumSet;
import java.util.Random;

public class BirdEntityRandomFlyGoal extends Goal {

    private static final int MAX_FLIGHT_TIME = 250;
    private static final int MAX_STUCK_TICKS = 40;
    private static final int TAKEOFF_TARGET_ATTEMPTS = 12;
    private static final double TAKEOFF_DISTANCE = 4.0D;
    private static final double TAKEOFF_RISE = 1.75D;
    private static final double TAKEOFF_STEP = 0.25D;
    private static final double TAKEOFF_REACHED_DISTANCE_SQR = 0.75D;
    private static final double MIN_PROGRESS_DISTANCE_SQR = 0.0625D;
    private static final double LANDING_HORIZONTAL_DISTANCE_SQR = 0.49D;
    private static final double LANDING_DESCENT_Y_OFFSET = -0.25D;
    private static final double LANDING_DESCENT_SPEED = 0.55D;

    private final ExtendedBirdEntity bird;
    private final Random random;

    private double targetX;
    private double targetY;
    private double targetZ;
    private double lastX;
    private double lastY;
    private double lastZ;

    private int flightTime;
    private int stuckTicks;

    private boolean takingOff;
    private boolean landing;

    public BirdEntityRandomFlyGoal(ExtendedBirdEntity bird) {
        this.bird = bird;
        this.random = bird.getRandom();

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {

        if (bird.isPassenger() || bird.isVehicle())
            return false;

        if (bird.isFlying())
            return false;

        if (bird.flyingOnCooldown()) return false;

        // roughly once every 8 ticks
        if (random.nextInt(8) != 0) {
            return false;
        }

        if (findTakeoffTarget()) {
            return true;
        }

        bird.requestGroundedMovement();
        bird.startBlockedTakeoffCooldown();
        return false;
    }

    @Override
    public void start() {

        bird.startFlying();

        takingOff = true;
        landing = false;
        flightTime = 0;
        resetProgressCheck();
        moveToTarget();
    }

    @Override
    public void tick() {

        flightTime++;

        if (isStuck()) {
            if (!findRecoveryTarget()) {
                if (landing) {
                    moveToTarget();
                    resetProgressCheck();
                    return;
                }

                bird.stopFlying();
                return;
            }

            moveToTarget();
            resetProgressCheck();
            return;
        }

        if (takingOff && bird.distanceToSqr(targetX, targetY, targetZ) < TAKEOFF_REACHED_DISTANCE_SQR) {
            takingOff = false;
            if (!findAirTarget()) {
                bird.stopFlying();
                return;
            }

            moveToTarget();
            resetProgressCheck();
            return;
        }

        if (!landing && flightTime >= MAX_FLIGHT_TIME) {

            if (findLandingTarget()) {
                landing = true;
            } else {
                // Couldn't find somewhere to land, try again next tick.
                flightTime = MAX_FLIGHT_TIME - 1;
            }
        }

        double dist = bird.distanceToSqr(targetX, targetY, targetZ);

        // Reached destination
        if (dist < 4.0D) {

            if (landing) {

                if (bird.isOnGround()) {
                    bird.stopFlying();
                    return;
                }

                if (isOverLandingTarget()) {
                    descendToLandingTarget();
                    return;
                }

            } else {

                // Continue flying to another point.
                if (!findAirTarget()) {
                    return;
                }
            }

            moveToTarget();
            resetProgressCheck();
        }

        // Recover if navigation finishes unexpectedly.
        if (bird.getNavigation().isDone()) {

            if (landing && isOverLandingTarget()) {
                descendToLandingTarget();
                return;
            }

            if (!findRecoveryTarget()) {
                if (landing) {
                    moveToTarget();
                    resetProgressCheck();
                    return;
                }

                bird.stopFlying();
                return;
            }

            moveToTarget();
            resetProgressCheck();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return bird.isFlying() && !bird.isPassenger() && !bird.isVehicle();
    }

    @Override
    public void stop() {
        bird.getNavigation().stop();
        if (bird.isFlying()) {
            bird.stopFlying();
        } else {
            bird.startFlyingCooldown();
        }
    }

    private boolean findTakeoffTarget() {
        for (int i = 0; i < TAKEOFF_TARGET_ATTEMPTS; i++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double xOffset = Math.cos(angle) * TAKEOFF_DISTANCE;
            double yOffset = TAKEOFF_RISE;
            double zOffset = Math.sin(angle) * TAKEOFF_DISTANCE;

            double x = bird.getX() + xOffset;
            double y = Math.min(bird.getY() + yOffset, bird.level.getMaxBuildHeight() - 1);
            double z = bird.getZ() + zOffset;

            if (canFitAt(x, y, z) && hasClearTakeoffPath(x - bird.getX(), y - bird.getY(), z - bird.getZ())) {
                targetX = x;
                targetY = y;
                targetZ = z;
                return true;
            }
        }

        return false;
    }

    private boolean findAirTarget() {

        for (int i = 0; i < 15; i++) {

            int x = bird.blockPosition().getX() + random.nextInt(25) - 12;
            int z = bird.blockPosition().getZ() + random.nextInt(25) - 12;

            double y = bird.getY() + 3 + random.nextInt(8);

            // Prevent birds from flying more than 12 blocks over the terrain or outside build height.
            double max = Math.min(
                bird.level.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z) + 12,
                bird.level.getMaxBuildHeight() - 1
            );
            y = Math.min(y, max);

            BlockPos pos = new BlockPos(x, y, z);
            double centerX = x + 0.5D;
            double centerZ = z + 0.5D;

            if (bird.level.isEmptyBlock(pos) && canFitAt(centerX, y, centerZ)) {

                targetX = centerX;
                targetY = y;
                targetZ = centerZ;

                return true;
            }
        }

        return false;
    }

    private boolean findLandingTarget() {
        for (int i = 0; i < 30; i++) {

            int x = bird.blockPosition().getX() + random.nextInt(41) - 20;
            int z = bird.blockPosition().getZ() + random.nextInt(41) - 20;

            int y = bird.level.getHeight(
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    x,
                    z) - 1;

            BlockPos ground = new BlockPos(x, y, z);
            BlockPos landingPos = ground.above();

            BlockState groundState = bird.level.getBlockState(ground);
            BlockState landingState = bird.level.getBlockState(landingPos);

            if (groundState.isFaceSturdy(
                    bird.level,
                    ground,
                    Direction.UP)
                    && landingState.getCollisionShape(bird.level, landingPos).isEmpty()
                    && canFitAt(x + 0.5D, landingPos.getY(), z + 0.5D)) {

                targetX = x + 0.5D;
                targetY = landingPos.getY();
                targetZ = z + 0.5D;

                return true;
            }
        }

        return false;
    }

    private void moveToTarget() {
        bird.getNavigation().moveTo(targetX, targetY, targetZ, 1.0D);
        if (takingOff) {
            bird.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.0D);
        }
    }

    private boolean findRecoveryTarget() {
        if (takingOff) {
            return findTakeoffTarget();
        }

        if (landing) {
            if (findLandingTarget()) {
                return true;
            }

            landing = false;
            flightTime = MAX_FLIGHT_TIME - 1;
        }

        return findAirTarget();
    }

    private boolean hasClearTakeoffPath(double xOffset, double yOffset, double zOffset) {
        double distance = Math.sqrt(xOffset * xOffset + yOffset * yOffset + zOffset * zOffset);

        for (double step = TAKEOFF_STEP; step <= distance; step += TAKEOFF_STEP) {
            double progress = step / distance;

            if (!bird.level.noCollision(bird, bird.getBoundingBox().move(xOffset * progress, yOffset * progress, zOffset * progress))) {
                return false;
            }
        }

        return true;
    }

    private boolean canFitAt(double x, double y, double z) {
        return bird.level.noCollision(bird, bird.getBoundingBox().move(x - bird.getX(), y - bird.getY(), z - bird.getZ()));
    }

    private void descendToLandingTarget() {
        bird.getNavigation().stop();
        bird.getMoveControl().setWantedPosition(targetX, targetY + LANDING_DESCENT_Y_OFFSET, targetZ, LANDING_DESCENT_SPEED);
    }

    private boolean isOverLandingTarget() {
        double dx = bird.getX() - targetX;
        double dz = bird.getZ() - targetZ;
        return dx * dx + dz * dz < LANDING_HORIZONTAL_DISTANCE_SQR;
    }

    private boolean isStuck() {
        if (bird.distanceToSqr(lastX, lastY, lastZ) > MIN_PROGRESS_DISTANCE_SQR) {
            resetProgressCheck();
            return false;
        }

        stuckTicks++;
        return stuckTicks >= MAX_STUCK_TICKS;
    }

    private void resetProgressCheck() {
        lastX = bird.getX();
        lastY = bird.getY();
        lastZ = bird.getZ();
        stuckTicks = 0;
    }

}
