package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.TameableEntity;

/**
 * A custom target goal that allows an {@link ExtendedINPCEntity} to target and attack
 * entities recently attacked by its owner. This logic extends behavior from Minecraft's
 * {@code HurtByTargetGoal}, with extra rules for managing interactions with other NPCs,
 * tameable entities, and protected types.
 * <p>
 * Inspired by {@code HurtByTargetGoal}.
 */
public class ExtendedHiredPlayerAttackedEntityTargetGoal extends TargetGoal {
	/**
     * The extended NPC entity that owns this goal.
     */
	private final ExtendedINPCEntity extendedNpcEntity;
	/**
     * The last entity attacked by the NPC's owner.
     */
	private LivingEntity ownerLastAttacked;
	/**
     * Timestamp of the owner's last attack, used to detect when a new attack occurs.
     */
	private int timestamp;

	/**
     * Creates a new target goal for the given NPC entity.
     *
     * @param entity The NPC entity to assign this goal to. Must be an instance of {@link ExtendedINPCEntity}.
     */
	public ExtendedHiredPlayerAttackedEntityTargetGoal(NPCEntity entity) {
		super(entity, false);
		this.extendedNpcEntity = (ExtendedINPCEntity) entity;
		this.setFlags(EnumSet.of(Flag.TARGET));
	}

	/**
     * Determines whether this goal can begin execution.
     * This checks if the NPC is on active duty, its owner recently attacked a new target,
     * and that the target is valid (not tamed, not a creeper, not an armor stand, not
     * another allied unit unless explicitly allowed).
     *
     * @return true if the NPC should target the entity its owner last attacked.
     */
	@Override
	public boolean canUse() {
		if (extendedNpcEntity.getUnitInfo().isNpcActiveDuty()) {
			LivingEntity unitOwner = extendedNpcEntity.getUnitInfo().getUnitOwner();
			if (unitOwner == null) {
				return false;
			} else {
				if(unitOwner.getLastHurtMobTimestamp() != this.timestamp) {
					this.ownerLastAttacked = unitOwner.getLastHurtMob();
					// prevent units attacking tameable entities
					if (this.ownerLastAttacked instanceof TameableEntity) {
						if (((TameableEntity) this.ownerLastAttacked).isTame()) {
							LivingEntity owner1 = ((TameableEntity) this.ownerLastAttacked).getOwner();
							// prevent hired units of the same player from fighting each other
							if (owner1 != null && owner1.equals(unitOwner)) {
								return false;
							}
						}
					} else if (this.ownerLastAttacked instanceof CreeperEntity || this.ownerLastAttacked instanceof ArmorStandEntity) {
						return false;
					}
					// prevent hired units of the same player from fighting each other
					else if (this.ownerLastAttacked instanceof ExtendedINPCEntity && ((ExtendedINPCEntity)ownerLastAttacked).getUnitInfo().isNpcActiveDuty()) {
						if (extendedNpcEntity.getUnitInfo().getOwnerUUID().equals(((ExtendedINPCEntity)ownerLastAttacked).getUnitInfo().getOwnerUUID())) {
							if (!((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(this.mob.level, extendedNpcEntity.getUnitInfo().getOwnerUUID())).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit()) {
								ownerLastAttacked=null;
								return false;
							}
						}
					}
					return this.canAttack(this.ownerLastAttacked, EntityPredicate.DEFAULT);//&& this.hirable.wantsToAttack(this.ownerLastHurt, livingentity);
				}
			}
		}
		return false;
	}

	/**
     * Starts execution of this goal by setting the NPC's attack target to the entity
     * last attacked by its owner, and updates the timestamp.
     */
	@Override
	public void start() {
		this.mob.setTarget(this.ownerLastAttacked);
		LivingEntity livingentity = extendedNpcEntity.getUnitInfo().getUnitOwner();
		if (livingentity != null) {
			this.timestamp = livingentity.getLastHurtMobTimestamp();
		}

		super.start();
	}
}
