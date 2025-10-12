package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.item.BowItem;

/**
 * Copied from {@link RangedBowAttackGoal} to add support for throwing axes
 * @param <T> The Entity type that implements this goal
 * 
 */
public class ThrowingAxeAttackGoal<T extends NPCEntity & IRangedAttackMob> extends Goal {
	private final T mob;
	private final double speedModifier;
	private int attackIntervalMin;
	private final float attackRadiusSqr;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;
	private int ticksUsingItem = -1;

	public ThrowingAxeAttackGoal(T pEntity, double pSpeedModifier, int pAttackIntervalMin, float pAttackRadius) {
		this.mob = pEntity;
		this.speedModifier = pSpeedModifier;
		this.attackIntervalMin = pAttackIntervalMin;
		this.attackRadiusSqr = pAttackRadius * pAttackRadius;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public void setMinAttackInterval(int pAttackIntervalMin) {
		this.attackIntervalMin = pAttackIntervalMin;
	}

	@Override
	public boolean canUse() {
		return this.mob.getTarget() == null ? false : this.isHoldingThrowingAxe();
	}

	protected boolean isHoldingThrowingAxe() {
		return this.mob.isHolding(item -> item instanceof ExtendedThrowableAxeItem);
	}

	@Override
	public boolean canContinueToUse() {
		return (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingThrowingAxe();
	}

	@Override
	public void start() {
		super.start();
		this.mob.setAggressive(true);
	}

	@Override
	public void stop() {
		super.stop();
		this.mob.setAggressive(false);
		this.seeTime = 0;
		this.attackTime = -1;
		this.mob.stopUsingItem();
	}

	@Override
	public void tick() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null) {
			double squareDistanceFrom = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			boolean canSee = this.mob.getSensing().canSee(livingentity);
			boolean hasSeen = this.seeTime > 0;
			if (canSee != hasSeen) {
				this.seeTime = 0;
			}

			if (canSee) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (!(squareDistanceFrom > this.attackRadiusSqr) && this.seeTime >= 20) {
				this.mob.getNavigation().stop();
				++this.strafingTime;
			} else {
				this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= 20) {
				if (mob.getRandom().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if (mob.getRandom().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}
				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (squareDistanceFrom > (this.attackRadiusSqr * 0.75F)) {
					this.strafingBackwards = false;
				} else if (squareDistanceFrom < (this.attackRadiusSqr * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				this.mob.lookAt(livingentity, 30.0F, 30.0F);
			} else {
				this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			}
			if (ticksUsingItem>=0) {
				if (!canSee && this.seeTime < -60) {
					ticksUsingItem = -1;
				} else if (canSee) {
					if (ticksUsingItem >= 10) {
						ticksUsingItem = -1;
						this.mob.performRangedAttack(livingentity, BowItem.getPowerForTime(ticksUsingItem));
						this.attackTime = this.attackIntervalMin;
					}else {
						ticksUsingItem+=1;
					}
				}
			} else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				ticksUsingItem=0;
			}
		}
	}
}
