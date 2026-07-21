package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;

/**
 * A custom targeting goal for {@link ExtendedINPCEntity} units that enables them to
 * retaliate against entities that have recently attacked their owner.
 * <p>
 * This logic is based on Minecraft's {@code HurtByTargetGoal}, extended with
 * behavior to prevent friendly fire or retaliation against inappropriate targets
 * such as pets or allied units.
 */
public class ExtendedHiredPlayerHurtByEntityTargetGoal extends TargetGoal {
	/**
     * The extended NPC entity that owns this goal.
     */
	private final ExtendedINPCEntity extendedNpcEntity;
	/**
     * The entity that most recently attacked the NPC's owner.
     */
	private LivingEntity ownerLastHurtBy;
	/**
     * Timestamp of the owner's last hurt event, used to detect when new damage occurs.
     */
	private int timestamp;
	/**
     * Constructs a new goal that makes the NPC retaliate against the last attacker
     * of its owner.
     *
     * @param entity The NPC entity to assign this goal to. Must be an instance of {@link ExtendedINPCEntity}.
     */
	public ExtendedHiredPlayerHurtByEntityTargetGoal(NPCEntity entity) {
		super(entity, false);
		this.extendedNpcEntity = (ExtendedINPCEntity) entity;
		this.setFlags(EnumSet.of(Flag.TARGET));
	}
	/**
     * Checks if the goal should begin execution.
     * The NPC will attempt to target the last entity that attacked its owner, as long as:
     * <ul>
     *     <li>The NPC is on active duty</li>
     *     <li>The owner exists and was recently hurt</li>
     *     <li>The attacker is a valid target (e.g., not a tamed pet or allied unit (unless explicitly allowed))</li>
     * </ul>
     *
     * @return true if the NPC should retaliate against its owner's attacker
     */
	@Override
	public boolean canUse() {
		if (extendedNpcEntity.getUnitInfo().isNpcActiveDuty()) {
			LivingEntity unitOwner = extendedNpcEntity.getUnitInfo().getUnitOwner();
			if (unitOwner == null) {
				return false;
			} else {
				if(unitOwner.getLastHurtMobTimestamp() != this.timestamp) {
					this.ownerLastHurtBy = unitOwner.getLastHurtByMob();
					// prevent units attacking tameable entities
					if (this.ownerLastHurtBy instanceof TameableEntity) {
						// Don't attack pets that hurt the owner
						if (((TameableEntity) this.ownerLastHurtBy).isTame()) {
							LivingEntity owner1 = ((TameableEntity) this.ownerLastHurtBy).getOwner();
							if (owner1 != null && owner1.equals(unitOwner)) {
								return false;
							}
						}
					}
					// prevent hired units of the same player from fighting each other
					if (this.ownerLastHurtBy instanceof ExtendedINPCEntity && ((ExtendedINPCEntity)ownerLastHurtBy).getUnitInfo().isNpcActiveDuty()) {
						if (extendedNpcEntity.getUnitInfo().getOwnerUUID().equals(((ExtendedINPCEntity)ownerLastHurtBy).getUnitInfo().getOwnerUUID())) {
							if (!((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(this.mob.level, extendedNpcEntity.getUnitInfo().getOwnerUUID())).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit()) {
								ownerLastHurtBy = null;
								return false;
							}
						}
					}
					return this.canAttack(this.ownerLastHurtBy, EntityPredicate.DEFAULT);// && this.hirable.wantsToAttack(this.ownerLastHurtBy, livingentity);
				}
			}
		}
		return false;
	}
	/**
     * Starts the goal by assigning the attack target to the entity that most recently hurt the NPC's owner.
     * Also updates the internal timestamp to prevent redundant activations.
     */
	@Override
	public void start() {
		this.mob.setTarget(this.ownerLastHurtBy);
		LivingEntity livingentity = extendedNpcEntity.getUnitInfo().getUnitOwner();
		if (livingentity != null) {
			this.timestamp = livingentity.getLastHurtByMobTimestamp();
		}

		super.start();
	}
}
