package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class ExtendedHiredFollowPlayerGoal extends Goal {
    private final NPCEntity entity;
    private final ExtendedHirableEntity hirable;
    private LivingEntity owner;
    private final IWorldReader level;
    private final double speedModifier;
    private final PathNavigator navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    private final boolean canFly;

    public ExtendedHiredFollowPlayerGoal(NPCEntity entity, double p_i225711_2_, float p_i225711_4_, float p_i225711_5_, boolean p_i225711_6_) {
        this.entity = entity;

        ExtendedHirableEntity hirable = ExtendedHiredUnitHelper.getExtendedHirableEntity(entity);
        if (hirable == null) {
            throw new IllegalArgumentException("The provided NPCEntity must implement ExtenedHirableEntity");
        }
        this.hirable = hirable;

        this.level = entity.level;
        this.speedModifier = p_i225711_2_;
        this.navigation = entity.getNavigation();
        this.startDistance = p_i225711_4_;
        this.stopDistance = p_i225711_5_;
        this.canFly = p_i225711_6_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        if (!(entity.getNavigation() instanceof GroundPathNavigator) && !(entity.getNavigation() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }
    @Override
    public boolean canUse() {
        if (!hirable.isFollowing()) {
            return false;
        }

        LivingEntity livingentity = this.hirable.getOwner();
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
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }

    }

    private boolean maybeTeleportTo(int p_226328_1_, int p_226328_2_, int p_226328_3_) {
        if (Math.abs((double)p_226328_1_ - this.owner.getX()) < 2.0D && Math.abs((double)p_226328_3_ - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(p_226328_1_, p_226328_2_, p_226328_3_))) {
            return false;
        } else {
            this.entity.moveTo(p_226328_1_ + 0.5D, p_226328_2_, p_226328_3_ + 0.5D, this.entity.yRot, this.entity.xRot);
            this.navigation.stop();
            return true;
        }
    }


    private boolean canTeleportTo(BlockPos p_226329_1_) {
        PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(this.level, p_226329_1_.mutable());
        if (pathnodetype != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(p_226329_1_.below());
            if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = p_226329_1_.subtract(this.entity.blockPosition());
                return this.level.noCollision(this.entity, this.entity.getBoundingBox().move(blockpos));
            }
        }
    }

    private int randomIntInclusive(int p_226327_1_, int p_226327_2_) {
        return this.entity.getRandom().nextInt(p_226327_2_ - p_226327_1_ + 1) + p_226327_1_;
    }
}