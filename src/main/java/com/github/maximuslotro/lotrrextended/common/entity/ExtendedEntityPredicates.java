package com.github.maximuslotro.lotrrextended.common.entity;

import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

/**
 * Utility class providing custom predicates for {@link Entity}s.
 */
public class ExtendedEntityPredicates {

    /**
     * Creates a predicate that checks whether a given {@link MobEntity} is alive,
     * is not the source entity itself, and currently targets a specific entity.
     *
     * @param thisEntity the source entity to exclude from the check
     * @param targetEntity the entity that potential mobs must be targeting
     * @return a {@link Predicate} that returns {@code true} for mobs alive, not the source, and targeting {@code targetEntity}
     */
    public static Predicate<MobEntity> sameTargetPredicate(MobEntity thisEntity, Entity targetEntity) {
        return (npc) -> npc != thisEntity && npc.isAlive() && npc.getTarget() == targetEntity;
    }
}