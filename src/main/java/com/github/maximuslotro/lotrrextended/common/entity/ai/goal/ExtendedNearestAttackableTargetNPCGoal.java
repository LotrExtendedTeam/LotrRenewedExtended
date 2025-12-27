package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.common.entity.ai.ExtendedCrowdedTargetSorter;
import com.github.maximuslotro.lotrrextended.common.entity.ai.ExtendedTargetSorter;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEntityUtils;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedBanditEntity;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * A custom {@link NearestAttackableTargetGoal} for NPCs that takes faction,
 * additional checks, and a weighted target sorter into account.
 *
 * @param <T> the type of {@link LivingEntity} this goal targets
 */
public class ExtendedNearestAttackableTargetNPCGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    /** A custom sorter for prioritizing targets in crowded scenarios. */
    private final ExtendedTargetSorter<NPCEntity> targetSorter;

    /** The faction of the NPC using this goal. */
    private final Faction mobFaction;

    /**
     * Constructs a new goal for an NPC to target entities of a specific class.
     *
     * @param pMob the NPC entity using this goal
     * @param pTargetClass the class of entities to target
     * @param pCheckSight whether line-of-sight is required
     * @param pSelector an additional predicate for filtering potential targets
     */
    public ExtendedNearestAttackableTargetNPCGoal(NPCEntity pMob, Class<T> pTargetClass, boolean pCheckSight, Predicate<LivingEntity> pSelector) {
        super(pMob, pTargetClass, pCheckSight);
        this.mobFaction = pMob.getFaction();
        this.targetConditions.selector((e) -> 
            e != this.mob.getVehicle() && !this.mob.hasPassenger(e) && pSelector.test(e) && addtionalCheck(e)
        );
        this.targetSorter = new ExtendedCrowdedTargetSorter<NPCEntity>(this.mob, NPCEntity.class);
    }

    /**
     * Determines whether this goal can start executing.
     * Overrides base behavior to prevent babies from attacking.
     *
     * @return {@code true} if the NPC is not a baby and base conditions are met
     */
    @Override
    public boolean canUse() {
        return this.mob.isBaby() ? false : super.canUse();
    }

    /**
     * Returns the bounding box around the NPC in which targets will be searched.
     * Expands horizontally by {@code targetDistance} and vertically by the smaller
     * of {@code targetDistance} or 8.0 blocks.
     *
     * @param targetDistance the maximum distance to search for targets
     * @return the search area as an {@link AxisAlignedBB}
     */
    @Override
    protected AxisAlignedBB getTargetSearchArea(double targetDistance) {
        return this.mob.getBoundingBox().inflate(targetDistance, Math.min(targetDistance, 8.0D), targetDistance);
    }

    /**
     * Finds and sets the NPC's target using a weighted selection among valid entities.
     * Utilizes {@link ExtendedEntityUtils#getWeightedEntityofClass} and the custom sorter.
     */
    @Override
    protected void findTarget() {
        this.target = ExtendedEntityUtils.getWeightedEntityofClass(mob, targetType, this.getTargetSearchArea(this.getFollowDistance()), 
        		(e) -> this.targetConditions.test(this.mob, e), this.targetSorter);
    }

    /**
     * Performs additional custom checks for potential targets.
     * Includes NPC duty status, orders, and special checks for players.
     *
     * @param entity the entity to test
     * @return {@code true} if the entity passes additional checks
     */
    public boolean addtionalCheck(LivingEntity entity) {
        if(entity instanceof ExtendedBanditEntity) {
            return ((ExtendedNPCEntity)mob).getUnitInfo().isNpcActiveDuty() && ((ExtendedNPCEntity)mob).getUnitInfo().getOrders().doesOrderAllowsMovement();
        }
        if(entity instanceof PlayerEntity) {
            return additionalPlayeredCheck((PlayerEntity) entity);
        }
        return true;
    }

    /**
     * Determines if a player is a valid target based on faction hostility and alignment.
     *
     * @param player the player to check
     * @return {@code true} if the player is hostile to the NPC's faction
     */
    public boolean additionalPlayeredCheck(PlayerEntity player) {
        if(FactionPointers.HOSTILE.matches(mobFaction)) {
            return true;
        }
        return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(mobFaction, AlignmentPredicates.NEGATIVE);
    }
}