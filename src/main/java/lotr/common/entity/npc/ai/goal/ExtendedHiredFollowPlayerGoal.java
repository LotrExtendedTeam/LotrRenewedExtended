package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;

public class ExtendedHiredFollowPlayerGoal extends Goal {
    private final NPCEntity entity;
    private LivingEntity owner;
    private final IWorldReader level;
    private final double speedModifier;
    private final PathNavigator navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    private final boolean canFly;

    public ExtendedHiredFollowPlayerGoal(NPCEntity entity, double speedModifier, float goalStartDistance, float goalEndDistance, boolean canFly) {
        this.entity = entity;

        this.level = entity.level;
        this.speedModifier = speedModifier;
        this.navigation = entity.getNavigation();
        this.startDistance = goalStartDistance;
        this.stopDistance = goalEndDistance;
        this.canFly = canFly;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        if (!(entity.getNavigation() instanceof GroundPathNavigator) && !(entity.getNavigation() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }
    @Override
    public boolean canUse() {
        if (!((ExtendedNPCEntity)entity).getUnitInfo().getOrders().isFollowingPlayer()) {
            return false;
        }

        LivingEntity livingentity = ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner();
        if (livingentity == null) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.entity.distanceToSqr(livingentity) < (this.startDistance * this.startDistance)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.navigation.isDone()) {
            return false;
        } else {
            return !(this.entity.distanceToSqr(this.owner) <= this.stopDistance * this.stopDistance);
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.entity.getPathfindingMalus(PathNodeType.WATER);
        this.entity.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }


    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.entity.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.entity.getLookControl().setLookAt(this.owner, 10.0F, this.entity.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.entity.isLeashed() && !this.entity.isPassenger()) {
                if (this.entity.distanceToSqr(this.owner) >= 144.0D) {
                    this.teleportToOwner();
                } else {
                    this.navigation.moveTo(this.owner, this.speedModifier);
                }

            }
        }
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();

        for(int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean teleported = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (teleported) {
                return;
            }
        }

    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (Math.abs(x - this.owner.getX()) < 2.0D && Math.abs(z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z)) || !canTeleportFrom()) {
            return false;
        } else {
            this.entity.moveTo(x + 0.5D, y, z + 0.5D, this.entity.yRot, this.entity.xRot);
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportFrom() {
        return this.entity.isOnGround() || this.entity.isInWaterOrBubble();
    }

    private boolean canTeleportTo(BlockPos teleportToPos) {
    	BlockState belowTeleportState = level.getBlockState(teleportToPos.below());
    	boolean full = Block.isFaceFull(belowTeleportState.getCollisionShape(level, teleportToPos, ISelectionContext.empty()), Direction.UP);
    	boolean isSolid = belowTeleportState.getMaterial().isSolid();
    	
        PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(this.level, teleportToPos.mutable());
        if (pathnodetype != PathNodeType.WALKABLE || !(isSolid && full)) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(teleportToPos.below());
            if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = teleportToPos.subtract(this.entity.blockPosition());
            	//ExtendedLog.info("Checking "+teleportState.toString()+":"+teleportToPos.toString()+":full="+full+":isSolid="+isSolid+":coll="+can+":path="+pathnodetype.name());
                return this.level.noCollision(this.entity, this.entity.getBoundingBox().move(blockpos));
            }
        }
    }

    private int randomIntInclusive(int minRange, int maxRange) {
        return this.entity.getRandom().nextInt(maxRange - minRange + 1) + minRange;
    }
}