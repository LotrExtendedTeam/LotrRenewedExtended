package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ExtendedAvoidNonLivingEntityGoal<T extends Entity> extends Goal {
	protected final CreatureEntity mob;
	private final double walkSpeedModifier;
	private final double sprintSpeedModifier;
	protected T toAvoid;
	protected final float maxDist;
	protected Path path;
	protected final PathNavigator pathNav;
	/** Class of entity this behavior seeks to avoid */
	protected final Class<T> avoidClass;

	public ExtendedAvoidNonLivingEntityGoal(CreatureEntity mob, Class<T> avoiding, float maxDist, double walkSpeedModifier, double runSpeedModifier) {
		this.mob = mob;
		this.avoidClass = avoiding;
		this.maxDist = maxDist;
		this.walkSpeedModifier = walkSpeedModifier;
		this.sprintSpeedModifier = runSpeedModifier;
		this.pathNav = mob.getNavigation();
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		this.toAvoid = this.getNearestLoadedNonLivingEntity(this.mob.level, this.avoidClass, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().inflate((double)this.maxDist, 3.0D, (double)this.maxDist));
		if (this.toAvoid == null) {
			return false;
		} else {
			Vector3d vector3d = RandomPositionGenerator.getPosAvoid(this.mob, 16, 7, this.toAvoid.position());
			if (vector3d == null) {
				return false;
			} else if (this.toAvoid.distanceToSqr(vector3d.x, vector3d.y, vector3d.z) < this.toAvoid.distanceToSqr(this.mob)) {
				return false;
			} else {
				this.path = this.pathNav.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
				return this.path != null;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return !this.pathNav.isDone();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.pathNav.moveTo(this.path, this.walkSpeedModifier);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.toAvoid = null;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.mob.distanceToSqr(this.toAvoid) < 49.0D) {
			this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
		} else {
			this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
		}

	}

	@Nullable
	public T getNearestLoadedNonLivingEntity(World level, Class<? extends T> avoidClass, double pX, double pY, double pZ, AxisAlignedBB boundingBox) {
		return this.getNearestEntityCopied(level.getLoadedEntitiesOfClass(avoidClass, boundingBox, (Predicate<? super T>)null), pX, pY, pZ);
	}

	@Nullable
	public T getNearestEntityCopied(List<? extends T> pEntities, double pX, double pY, double pZ) {
		double d0 = -1.0D;
		T t = null;

		for(T t1 : pEntities) {
			double d1 = t1.distanceToSqr(pX, pY, pZ);
			if (d0 == -1.0D || d1 < d0) {
				d0 = d1;
				t = t1;
			}
		}

		return t;
	}
}