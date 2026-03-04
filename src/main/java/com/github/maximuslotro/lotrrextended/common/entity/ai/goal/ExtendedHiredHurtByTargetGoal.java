package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;

/**
 * A customized {@link HurtByTargetGoal} for NPC units that introduces extended logic
 * to prevent friendly fire between NPCs owned by the same player.
 * <p>
 * This goal overrides the target validation logic to ensure that NPC units in "active duty"
 * will not attack other NPCs under the same player's ownership, unless explicitly allowed
 * by the player's configuration via {@link ExtendedLOTRPlayerData#getExtendedMiscData()}.
 * <p>
 * Additionally, the goal bypasses the default home restriction check to allow attacks
 * even outside the NPC's home area.
 */
public class ExtendedHiredHurtByTargetGoal extends HurtByTargetGoal {

	/** The extended NPC entity using this goal. */
	private final ExtendedNPCEntity extendedEntity;

	/**
	 * Constructs a new targeting goal for the specified NPC entity.
	 *
	 * @param entity the base {@link NPCEntity} instance; must implement {@link ExtendedNPCEntity}
	 */
	public ExtendedHiredHurtByTargetGoal(NPCEntity entity) {
		super(entity);
		this.extendedEntity = (ExtendedNPCEntity) entity;
	}

	/**
	 * Determines whether a potential target can be attacked by this NPC unit.
	 * <p>
	 * Overrides the base logic to block attacks against allied NPC units owned by the same player,
	 * unless explicitly permitted in the player’s configuration. Also bypasses home area restrictions.
	 *
	 * @param pPotentialTarget the entity being evaluated as a target
	 * @param pTargetPredicate the predicate used to validate the target
	 * @return {@code true} if the NPC can attack the potential target, {@code false} otherwise
	 */
	@Override
	protected boolean canAttack(LivingEntity pPotentialTarget, EntityPredicate pTargetPredicate) {
		if (pPotentialTarget == null) {
			return false;
		} else if (!pTargetPredicate.test(this.mob, pPotentialTarget)) {
			return false;
		//} else if (!this.mob.isWithinRestriction(pPotentialTarget.blockPosition())) {
		//	return false;
		} 
		else if (extendedEntity.getUnitInfo().isNpcActiveDuty()
				&& pPotentialTarget instanceof ExtendedNPCEntity
				&& ((ExtendedNPCEntity)pPotentialTarget).getUnitInfo().isNpcActiveDuty()
				&& ((ExtendedNPCEntity)pPotentialTarget).getUnitInfo().getOwnerUUID().equals(extendedEntity.getUnitInfo().getOwnerUUID())
				&& !((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(this.mob.level, extendedEntity.getUnitInfo().getOwnerUUID())).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit()) {
			return false;
		} else {
			if (this.mustReach) {
				if (--this.reachCacheTime <= 0) {
					this.reachCache = 0;
				}
				if (this.reachCache == 0) {
					this.reachCache = this.canReach(pPotentialTarget) ? 1 : 2;
				}
				if (this.reachCache == 2) {
					return false;
				}
			}
			return true;
		}
	}
}
