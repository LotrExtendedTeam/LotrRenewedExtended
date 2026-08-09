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

    private final ExtendedBirdEntity bird;
    private final Random random;

    private double targetX;
    private double targetY;
    private double targetZ;

    private int flightTime;

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
        return random.nextInt(8) == 0 && findAirTarget();
    }

    @Override
    public void start() {

        bird.startFlying();

        bird.getNavigation().moveTo(targetX, targetY, targetZ, 1.0D);
        bird.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.0D);

        landing = false;
        flightTime = 0;
    }

    @Override
    public void tick() {

        flightTime++;

        if (!landing && flightTime >= 250) {

            if (findLandingTarget()) {
                landing = true;
            } else {
                // Couldn't find somewhere to land, try again next tick.
                flightTime = 249;
            }
        }

        double dist = bird.distanceToSqr(targetX, targetY, targetZ);

        // Reached destination
        if (dist < 4.0D) {

            if (landing) {

                // Wait until we've actually landed.
                if (bird.isOnGround()) {
                    bird.stopFlying();
                    return;
                }

            } else {

                // Continue flying to another point.
                findAirTarget();
            }

            bird.getNavigation().moveTo(targetX, targetY, targetZ, 1.0D);
            bird.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.0D);
        }

        // Recover if navigation finishes unexpectedly.
        if (bird.getNavigation().isDone()) {

            if (landing) {
                findLandingTarget();
            } else {
                findAirTarget();
            }

            bird.getNavigation().moveTo(targetX, targetY, targetZ, 1.0D);
            bird.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.0D);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return bird.isFlying();
    }

    @Override
    public void stop() {
        bird.getNavigation().stop();
        bird.startFlyingCooldown();
    }

    private boolean findAirTarget() {

        for (int i = 0; i < 15; i++) {

            double x = bird.getX() + random.nextInt(25) - 12;
            double z = bird.getZ() + random.nextInt(25) - 12;

            double y = bird.getY() + 3 + random.nextInt(8);

            // prevent birds from flying 12 blocks in the air or the world build height
            double max = Math.min(
                bird.level.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z) + 12,
                bird.level.getMaxBuildHeight()
            );
            y = Math.min(y, max);

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

    private boolean findLandingTarget() {
        for (int i = 0; i < 30; i++) {

            int x = bird.blockPosition().getX() + random.nextInt(41) - 20;
            int z = bird.blockPosition().getZ() + random.nextInt(41) - 20;

            int y = bird.level.getHeight(
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    x,
                    z) - 1;

            BlockPos ground = new BlockPos(x, y, z);

            BlockState groundState = bird.level.getBlockState(ground);
            BlockState above = bird.level.getBlockState(ground.above());

            if (groundState.isFaceSturdy(
                    bird.level,
                    ground,
                    Direction.UP)
                    && above.getMaterial().isReplaceable()) {

                targetX = x + 0.25D;
                targetY = y;
                targetZ = z + 0.25D;

                return true;
            }
        }

        return false;
    }

}