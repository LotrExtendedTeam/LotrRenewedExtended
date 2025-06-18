package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.TameableEntity;

public class ExtendedHiredPlayerHurtTargetGoal extends TargetGoal {
    private final NPCEntity entity;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public ExtendedHiredPlayerHurtTargetGoal(NPCEntity entity) {
        super(entity, false);
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
	public boolean canUse() {
        if (((ExtendedNPCEntity)entity).getUnitInfo().isNpcActiveDuty()) {
            LivingEntity livingentity = ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                if (this.ownerLastHurt instanceof TameableEntity) {
                    if (((TameableEntity) this.ownerLastHurt).isTame()) {
                        LivingEntity owner1 = ((TameableEntity) this.ownerLastHurt).getOwner();
                        LivingEntity owner2 = ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner();
                        // prevent hired units of the same player from fighting each other
                        if (owner1 != null && owner1.equals(owner2)) {
                            return false;
                        }
                    }
                } else if (this.ownerLastHurt instanceof CreeperEntity || this.ownerLastHurt instanceof ArmorStandEntity) {
                    return false;
                }
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, EntityPredicate.DEFAULT);//&& this.hirable.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
	public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
