package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ExtendedHiredPlayerAttackedEntityTargetGoal extends TargetGoal { // TODO migrate to HurtByTargetGoal
	private final ExtendedNPCEntity extendedNpcEntity;
	private LivingEntity ownerLastAttacked;
	private int timestamp;

    public ExtendedHiredPlayerAttackedEntityTargetGoal(NPCEntity entity) {
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
                else if (this.ownerLastAttacked instanceof ExtendedNPCEntity && ((ExtendedNPCEntity)ownerLastAttacked).getUnitInfo().isNpcActiveDuty()) {
					if (((ExtendedNPCEntity)ownerLastAttacked).getUnitInfo().getUnitOwner().equals(unitOwner)) {
						if (!((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData((PlayerEntity) unitOwner)).getExtendedMiscData().canHiredUnitAttackOtherHiredUnit()) {
							return false;
						}
					}
				}
                int i = unitOwner.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastAttacked, EntityPredicate.DEFAULT);//&& this.hirable.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

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
