package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ExtendedHiredPlayerHurtByEntityTargetGoal extends TargetGoal { // TODO migrate to HurtByTargetGoal

	private final ExtendedNPCEntity extendedNpcEntity;
	private LivingEntity ownerLastHurtBy;
	private int timestamp;

	public ExtendedHiredPlayerHurtByEntityTargetGoal(NPCEntity entity) {
		super(entity, false);
		this.extendedNpcEntity = (ExtendedNPCEntity) entity;
		this.setFlags(EnumSet.of(Flag.TARGET));
	}

	@Override
	public boolean canUse() {
		if (extendedNpcEntity.getUnitInfo().isNpcActiveDuty()) {
			LivingEntity unitOwner = extendedNpcEntity.getUnitInfo().getUnitOwner();
			if (unitOwner == null) {
				return false;
			} else {
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
				if (this.ownerLastHurtBy instanceof ExtendedNPCEntity && ((ExtendedNPCEntity)ownerLastHurtBy).getUnitInfo().isNpcActiveDuty()) {
					if (((ExtendedNPCEntity)ownerLastHurtBy).getUnitInfo().getUnitOwner().equals(unitOwner)) {
						if (!((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unitOwner)).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit()) {
							return false;
						}
					}
				}
				int i = unitOwner.getLastHurtByMobTimestamp();
				return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, EntityPredicate.DEFAULT);// && this.hirable.wantsToAttack(this.ownerLastHurtBy, livingentity);
			}
		} else {
			return false;
		}
	}

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
