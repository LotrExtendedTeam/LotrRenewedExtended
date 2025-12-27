package com.github.maximuslotro.lotrrextended.common.entity.ai;

import com.github.maximuslotro.lotrrextended.common.entity.ExtendedEntityPredicates;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

/**
 * An extension of {@link ExtendedTargetSorter} that penalizes targets
 * already being focused by nearby mobs.
 *
 * <p>This sorter discourages multiple mobs from selecting the same target
 * by applying a quadratic crowding penalty.
 *
 * @param <T> the mob type used when detecting nearby entities contributing
 *            to crowding
 */
public class ExtendedCrowdedTargetSorter<T extends MobEntity> extends ExtendedTargetSorter<T> {

    /** The mob class used to detect nearby entities contributing to crowding. */
    private final Class<T> crowdType;

    /**
     * Creates a new crowded-target sorter.
     *
     * @param mob the mob performing the targeting
     * @param crowdType the entity type used to detect nearby mobs already
     *                  targeting a candidate entity
     */
    public ExtendedCrowdedTargetSorter(MobEntity mob, Class<T> crowdType) {
        super(mob);
        this.crowdType = crowdType;
    }

    /**
     * Computes a weighted score that combines distance and crowding.
     *
     * @param target the entity being evaluated
     * @return the computed weighted score
     */
    @Override
    protected double score(Entity target) {
        // Normalize distance (12 blocks = weight 1.0)
        double distanceWeight = mob.distanceToSqr(target) / 144.0D;

        // Count nearby mobs already targeting this entity
        int crowdCount = mob.level.getLoadedEntitiesOfClass(
                crowdType,
                mob.getBoundingBox().inflate(8.0D),
                ExtendedEntityPredicates.sameTargetPredicate(mob, target)
        ).size();

        // Quadratic penalty to discourage crowding
        double crowdPenalty = crowdCount * crowdCount;

        return distanceWeight + crowdPenalty;
    }
}
