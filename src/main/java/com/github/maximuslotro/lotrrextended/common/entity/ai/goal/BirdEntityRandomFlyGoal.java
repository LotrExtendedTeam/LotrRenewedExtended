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
    private static final int LANDING_SEARCH_START_TIME = 200;
    private static final int TAKEOFF_CHANCE = 8;
    private static final int MAX_STUCK_TICKS = 40;
    private static final int TAKEOFF_TARGET_ATTEMPTS = 12;
    private static final int AIR_TARGET_ATTEMPTS = 15;
    private static final int AIR_TARGET_HORIZONTAL_RANGE = 12;
    private static final int AIR_TARGET_MIN_RISE = 3;
    private static final int AIR_TARGET_RANDOM_RISE = 8;
    private static final int MAX_TERRAIN_FLIGHT_HEIGHT = 12;
    private static final int LANDING_TARGET_ATTEMPTS = 30;
    private static final int LANDING_TARGET_RANGE = 20;
    private static final int EMERGENCY_LANDING_TARGET_RANGE = 4;
    private static final double TAKEOFF_DISTANCE = 4.0D;
    private static final double TAKEOFF_RISE = 1.75D;
    private static final double TAKEOFF_STEP = 0.25D;
    private static final double TAKEOFF_REACHED_DISTANCE_SQR = 0.75D;
    private static final double TARGET_REACHED_DISTANCE_SQR = 4.0D;
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

        if (bird.isPassenger() || bird.isVehicle()) {
            return false;
        }

        if (bird.isFlying()) {
            return false;
        }

        if (bird.flyingOnCooldown()) {
            return false;
        }

        if (random.nextInt(TAKEOFF_CHANCE) != 0) {
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

        if (landing && bird.isOnGround()) {
            finishLanding();
            return;
        }

        if (recoverIfStuck()) {
            return;
        }

        if (completeTakeoffIfReady()) {
            return;
        }

        if (!takingOff && bird.isOnGround()) {
            finishLanding();
            return;
        }

        if (handleFlightTimeLimit()) {
            return;
        }

        if (handleReachedTarget()) {
            return;
        }

        recoverIfNavigationFinished();
    }

    private boolean recoverIfStuck() {
        if (!isStuck()) {
            return false;
        }

        if (!findRecoveryTarget()) {
            if (landing) {
                moveToTarget();
                resetProgressCheck();
                return true;
            }

            bird.stopFlying();
            return true;
        }

        moveToTarget();
        resetProgressCheck();
        return true;
    }

    private boolean completeTakeoffIfReady() {
        if (!takingOff || bird.distanceToSqr(targetX, targetY, targetZ) >= TAKEOFF_REACHED_DISTANCE_SQR) {
            return false;
        }

        takingOff = false;
        if (!findAirTarget()) {
            bird.stopFlying();
            return true;
        }

        moveToTarget();
        resetProgressCheck();
        return true;
    }

    private boolean handleFlightTimeLimit() {
        if (landing && flightTime >= MAX_FLIGHT_TIME) {
            if (isOverLandingTarget()) {
                descendToLandingTarget();
            } else {
                forceLanding();
            }

            return true;
        }

        if (!landing && flightTime >= MAX_FLIGHT_TIME) {
            forceLanding();
            return true;
        }

        if (!landing && flightTime >= LANDING_SEARCH_START_TIME && findLandingTarget()) {
            beginLanding();
            return true;
        }

        return false;
    }

    private boolean handleReachedTarget() {
        if (bird.distanceToSqr(targetX, targetY, targetZ) >= TARGET_REACHED_DISTANCE_SQR) {
            return false;
        }

        if (landing) {
            if (bird.isOnGround()) {
                finishLanding();
                return true;
            }

            if (isOverLandingTarget()) {
                descendToLandingTarget();
                return true;
            }
        } else {
            if (flightTime >= LANDING_SEARCH_START_TIME) {
                if (findLandingTarget()) {
                    beginLanding();
                }

                return true;
            }

            if (!findAirTarget()) {
                return true;
            }
        }

        moveToTarget();
        resetProgressCheck();
        return true;
    }

    private void recoverIfNavigationFinished() {
        if (!bird.getNavigation().isDone()) {
            return;
        }

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
                setTarget(x, y, z);
                return true;
            }
        }

        return false;
    }

    private boolean findAirTarget() {

        for (int i = 0; i < AIR_TARGET_ATTEMPTS; i++) {

            int x = bird.blockPosition().getX() + randomOffset(AIR_TARGET_HORIZONTAL_RANGE);
            int z = bird.blockPosition().getZ() + randomOffset(AIR_TARGET_HORIZONTAL_RANGE);

            double y = bird.getY() + AIR_TARGET_MIN_RISE + random.nextInt(AIR_TARGET_RANDOM_RISE);

            // Prevent birds from flying more than 12 blocks over the terrain or outside build height.
            double max = Math.min(
                bird.level.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) + MAX_TERRAIN_FLIGHT_HEIGHT,
                bird.level.getMaxBuildHeight() - 1
            );
            y = Math.min(y, max);

            BlockPos pos = new BlockPos(x, y, z);
            double centerX = x + 0.5D;
            double centerZ = z + 0.5D;

            if (bird.level.isEmptyBlock(pos) && canFitAt(centerX, y, centerZ)) {
                setTarget(centerX, y, centerZ);
                return true;
            }
        }

        return false;
    }

    private boolean findLandingTarget() {
        for (int i = 0; i < LANDING_TARGET_ATTEMPTS; i++) {

            int x = bird.blockPosition().getX() + randomOffset(LANDING_TARGET_RANGE);
            int z = bird.blockPosition().getZ() + randomOffset(LANDING_TARGET_RANGE);

            if (trySetLandingTarget(x, z)) {
                return true;
            }
        }

        return false;
    }

    private boolean findEmergencyLandingTarget() {
        int birdX = bird.blockPosition().getX();
        int birdZ = bird.blockPosition().getZ();

        if (trySetLandingTarget(birdX, birdZ)) {
            return true;
        }

        for (int radius = 1; radius <= EMERGENCY_LANDING_TARGET_RANGE; radius++) {
            for (int x = birdX - radius; x <= birdX + radius; x++) {
                for (int z = birdZ - radius; z <= birdZ + radius; z++) {
                    if ((x == birdX - radius || x == birdX + radius || z == birdZ - radius || z == birdZ + radius)
                            && trySetLandingTarget(x, z)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean trySetLandingTarget(int x, int z) {
        int y = bird.level.getHeight(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                x,
                z) - 1;

        BlockPos ground = new BlockPos(x, y, z);
        BlockPos landingPos = ground.above();

        BlockState groundState = bird.level.getBlockState(ground);
        BlockState landingState = bird.level.getBlockState(landingPos);

        if (isLandingSpot(ground, landingPos, groundState, landingState)) {
            setTarget(x + 0.5D, landingPos.getY(), z + 0.5D);
            return true;
        }

        return false;
    }

    private boolean isLandingSpot(BlockPos ground, BlockPos landingPos, BlockState groundState, BlockState landingState) {
        return groundState.isFaceSturdy(
                bird.level,
                ground,
                Direction.UP)
                && landingState.getCollisionShape(bird.level, landingPos).isEmpty()
                && canFitAt(landingPos.getX() + 0.5D, landingPos.getY(), landingPos.getZ() + 0.5D);
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

            if (flightTime >= MAX_FLIGHT_TIME) {
                return findEmergencyLandingTarget();
            }

            landing = false;
            flightTime = MAX_FLIGHT_TIME - 1;
        }

        if (flightTime >= MAX_FLIGHT_TIME) {
            return findEmergencyLandingTarget();
        }

        return findAirTarget();
    }

    private int randomOffset(int range) {
        return random.nextInt(range * 2 + 1) - range;
    }

    private void setTarget(double x, double y, double z) {
        targetX = x;
        targetY = y;
        targetZ = z;
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

    private void beginLanding() {
        landing = true;
        takingOff = false;
        moveToTarget();
        resetProgressCheck();
    }

    private void forceLanding() {
        if (findEmergencyLandingTarget()) {
            beginLanding();
            return;
        }

        bird.stopFlying();
    }

    private void finishLanding() {
        landing = false;
        takingOff = false;
        bird.stopFlying();
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
