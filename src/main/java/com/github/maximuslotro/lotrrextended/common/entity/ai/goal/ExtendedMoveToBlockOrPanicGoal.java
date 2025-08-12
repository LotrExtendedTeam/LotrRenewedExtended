package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

/**
 * Combined {@link MoveToBlockGoal} and {@link PanicGoal} into one goal
 * */
public abstract class ExtendedMoveToBlockOrPanicGoal extends Goal {
	protected final CreatureEntity mob;
	public final double speedModifier;
	/** Controls task execution delay */
	protected int executionTicks;
	private int maxTicksToStayAtBlock;
	/** Block to move to */
	protected BlockPos targetPos = BlockPos.ZERO;
	private boolean hasBlockTarget;
	private boolean reachedTarget;
	private final int searchRange;
	private final int verticalSearchRange;
	protected int verticalSearchStart;
	protected int ticksWaited;
	protected boolean isHiding;

	public ExtendedMoveToBlockOrPanicGoal(CreatureEntity creature, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
		this.mob = creature;
		this.speedModifier = pSpeedModifier;
		this.searchRange = pSearchRange;
		this.verticalSearchStart = 0;
		this.verticalSearchRange = pVerticalSearchRange;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		if (this.mob.getLastHurtByMob() == null && !this.mob.isOnFire()) {
			return false;
		} else {
			if (this.mob.isOnFire()) {
				BlockPos blockpos = this.lookForWater(this.mob.level, this.mob, 5, 4);
				if (blockpos != null) {
					targetPos = blockpos;
					hasBlockTarget = false;
					return true;
				}
			}
			return findNearestBlock() ? true : findRandomPosition();
		}
	}

	@Override
	public boolean canContinueToUse() {
		return hasBlockTarget ? (this.executionTicks >= -this.maxTicksToStayAtBlock && this.executionTicks <= 1200 && this.isValidTarget(this.mob.level, this.targetPos)) : !this.mob.getNavigation().isDone() ;
	}

	@Override
	public void start() {
		this.isHiding = false;
		this.ticksWaited = 0;
		if(hasBlockTarget) {
			this.mob.getNavigation().moveTo(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 1, this.targetPos.getZ() + 0.5D, this.speedModifier);
		}else {
			this.mob.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), this.speedModifier);
		}
		this.executionTicks = 0;
		this.maxTicksToStayAtBlock = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
	}

	public boolean isHiding() {
		return this.isHiding;
	}

	@Override
	public void stop() {
		this.isHiding = false;
		hasBlockTarget = false;
	}

	@Override
	public void tick() {
		if(hasBlockTarget) {
			if (this.isReachedTarget()) {
				mob.getNavigation().stop();
				if (this.ticksWaited >= 5) {
					this.onReachedTarget();
					isHiding = true;
				} else {
					++this.ticksWaited;
				}
			} else if (!this.isReachedTarget() && mob.random.nextFloat() < 0.05F) {
				//mob.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
			}
			BlockPos blockpos = this.getMoveToTarget();
			if (!blockpos.closerThan(this.mob.position(), this.acceptedDistance())) {
				this.reachedTarget = false;
				++this.executionTicks;
				if (this.shouldRecalculatePath()) {
					this.mob.getNavigation().moveTo(blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, this.speedModifier);
				}
			} else {
				this.reachedTarget = true;
				--this.executionTicks;
			}
		}
	}

	public boolean shouldRecalculatePath() {
		return this.executionTicks % 100 == 0;
	}

	public abstract void onReachedTarget();

	public double acceptedDistance() {
		return 1.0D;
	}

	protected BlockPos getMoveToTarget() {
		return this.targetPos.above();
	}

	protected boolean isReachedTarget() {
		return this.reachedTarget;
	}

	/**
	 * Searches and sets new destination block and returns true if a suitable block (specified in {@link ExtendedMoveToBlockOrPanicGoal#isValidTarget(IWorldReader, BlockPos)})  can be found.
	 * @return True if found a suitable block for entity to target
	 */
	protected boolean findNearestBlock() {
		BlockPos origin = this.mob.blockPosition();
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();

		for(int k = this.verticalSearchStart; k <= verticalSearchRange; k = k > 0 ? -k : 1 - k) {
			for(int radius = 0; radius < searchRange; ++radius) {
				for(int x = 0; x <= radius; x = x > 0 ? -x : 1 - x) {
					for(int z = x < radius && x > -radius ? radius : 0; z <= radius; z = z > 0 ? -z : 1 - z) {
						mutablePos.setWithOffset(origin, x, k - 1, z);
						if (this.mob.isWithinRestriction(mutablePos) && this.isValidTarget(this.mob.level, mutablePos)) {
							this.targetPos = mutablePos;
							hasBlockTarget = true;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	protected boolean findRandomPosition() {
		Vector3d vector3d = RandomPositionGenerator.getPos(this.mob, 5, 4);
		if (vector3d == null) {
			return false;
		} else {
			targetPos = new BlockPos(vector3d);
			hasBlockTarget = false;
			return true;
		}
	}

	protected abstract boolean isValidTarget(IWorldReader pLevel, BlockPos pPos);

	@Nullable
	protected BlockPos lookForWater(IBlockReader pLevel, Entity pEntity, int pHorizontalRange, int pVerticalRange) {
		BlockPos blockpos = pEntity.blockPosition();
		int i = blockpos.getX();
		int j = blockpos.getY();
		int k = blockpos.getZ();
		float f = (float)(pHorizontalRange * pHorizontalRange * pVerticalRange * 2);
		BlockPos blockpos1 = null;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(int l = i - pHorizontalRange; l <= i + pHorizontalRange; ++l) {
			for(int i1 = j - pVerticalRange; i1 <= j + pVerticalRange; ++i1) {
				for(int j1 = k - pHorizontalRange; j1 <= k + pHorizontalRange; ++j1) {
					blockpos$mutable.set(l, i1, j1);
					if (pLevel.getFluidState(blockpos$mutable).is(FluidTags.WATER)) {
						float f1 = (float)((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));
						if (f1 < f) {
							f = f1;
							blockpos1 = new BlockPos(blockpos$mutable);
						}
					}
				}
			}
		}
		return blockpos1;
	}
}
