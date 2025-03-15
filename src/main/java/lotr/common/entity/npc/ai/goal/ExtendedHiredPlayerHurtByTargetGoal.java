package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;

public class ExtendedHiredPlayerHurtByTargetGoal extends TargetGoal {

    private final ExtendedHirableEntity hirable;
    private final NPCEntity entity;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public ExtendedHiredPlayerHurtByTargetGoal (NPCEntity entity) {
        super(entity, false);
        this.entity = entity;

        ExtendedHirableEntity hirable = ExtendedHiredUnitHelper.getExtendedHirableEntity(entity);
        if (hirable == null) {
            throw new IllegalArgumentException("The provided NPCEntity must implement ExtenedHirableEntity");
        }
        this.hirable = hirable;

        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
	public boolean canUse() {
        if (this.hirable.isTame()) {
            LivingEntity livingentity = this.hirable.getUnitOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurtBy = livingentity.getLastHurtByMob();
                if (this.ownerLastHurtBy instanceof TameableEntity) {
                    if (((TameableEntity) this.ownerLastHurtBy).isTame()) {
                        LivingEntity owner1 = ((TameableEntity) this.ownerLastHurtBy).getOwner();
                        LivingEntity owner2 = this.hirable.getUnitOwner();
                        // prevent hired units of the same player from fighting each other
                        if (owner1 != null && owner1.equals(owner2)) {
                            return false;
                        }
                    }
                }
                int i = livingentity.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, EntityPredicate.DEFAULT) && this.hirable.wantsToAttack(this.ownerLastHurtBy, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
	public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity livingentity = this.hirable.getUnitOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
