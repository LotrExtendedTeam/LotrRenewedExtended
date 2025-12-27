package com.github.maximuslotro.lotrrextended.common.entity.ai;

import java.util.Comparator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

/**
 * A {@link Comparator} that orders {@link Entity} instances using a
 * scoring function evaluated relative to a {@link MobEntity}.
 *
 * <p>Lower scores indicate higher priority targets.
 *
 * @param <T> the mob type performing the target evaluation
 */
public class ExtendedTargetSorter<T extends MobEntity> implements Comparator<Entity> {

	/** The mob performing the target evaluation. */
	protected final MobEntity mob;

	/**
	 * Creates a new sorter for the given mob.
	 *
	 * @param mob the mob used as the reference point for evaluation
	 */
	public ExtendedTargetSorter(MobEntity mob) {
		this.mob = mob;
	}

	/**
	 * Compares two entities using their computed score.
	 *
	 * @param a the first entity to compare
	 * @param b the second entity to compare
	 * @return a negative value if {@code a} is preferred over {@code b},
	 *         a positive value if {@code b} is preferred over {@code a},
	 *         or {@code 0} if both are equally preferred
	 */
	@Override
	public final int compare(Entity a, Entity b) {
		return Double.compare(score(a), score(b));
	}

	/**
	 * Computes the score for a target entity.
	 *
	 * <p>The default implementation scores targets purely by squared distance.
	 *
	 * @param target the entity being evaluated
	 * @return the computed score
	 */
	protected double score(Entity target) {
		return mob.distanceToSqr(target);
	}
}
