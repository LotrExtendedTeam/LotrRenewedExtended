package com.github.maximuslotro.lotrrextended.common.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 * Utility methods for working with {@link Entity} instances.
 */
public class ExtendedEntityUtils {

    /**
     * Retrieves the highest-priority entity of a given class within a bounding box,
     * based on a provided comparator.
     * <p>
     * The method:
     * <ol>
     *   <li>Collects all entities of {@code targetClass} within {@code bb}</li>
     *   <li>Optionally filters them using {@code pFilter}</li>
     *   <li>Sorts the remaining entities using {@code sorter}</li>
     *   <li>Returns the first (best-weighted) entity, or {@code null} if none exist</li>
     * </ol>
     *
     * @param <T> the specific type of {@link LivingEntity} to search for
     * @param originEntity the entity whose level is used to query entities
     * @param targetClass the class of entities to retrieve
     * @param bb the axis-aligned bounding box defining the search area
     * @param pFilter an optional predicate to filter matching entities, or {@code null} for no filtering
     * @param sorter a comparator defining entity priority (lower values are preferred)
     * @return the highest-priority matching {@link LivingEntity}, or {@code null} if none are found
     *
     * @see net.minecraft.world.World#getEntitiesOfClass(Class, AxisAlignedBB, Predicate)
     * @see java.util.Comparator
     */
    @Nullable
    public static <T extends LivingEntity> LivingEntity getWeightedEntityofClass(LivingEntity originEntity, Class<? extends T> targetClass, 
    		AxisAlignedBB bb, @Nullable Predicate<? super T> pFilter, Comparator<? super T> sorter) {
        List<T> targets = originEntity.level.getEntitiesOfClass(targetClass, bb, pFilter);
        Collections.sort(targets, sorter);
        return targets.isEmpty() ? null : targets.get(0);
    }

    public static BlockPos getRandomPosInBounds(BlockPos.Mutable position, Entity entity, int horizontalBounds, int minYBounds, int maxYBounds) {
    	return position.set(
    			entity.getX() + MathHelper.nextInt(entity.random, -horizontalBounds, horizontalBounds), 
    			entity.getY() + MathHelper.nextInt(entity.random, minYBounds, maxYBounds),
    			entity.getZ() + MathHelper.nextInt(entity.random, -horizontalBounds, horizontalBounds));
    }
}