package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;

public class ExtendedHiredPlayerHurtTargetGoal extends TargetGoal {
    private final NPCEntity entity;
    private final ExtendedHirableEntity hirable;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public ExtendedHiredPlayerHurtTargetGoal(NPCEntity entity) {
        super(entity, false);
        this.entity = entity;

        ExtendedHirableEntity hirable = ExtendedHiredUnitHelper.getExtendedHirableEntity(entity);
        if (hirable == null) {
            throw new IllegalArgumentException("The provided NPCEntity must implement ExtenedHirableEntity");
        }
        this.hirable = hirable;

        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (this.hirable.isTame()) {
            LivingEntity livingentity = this.hirable.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                if (this.ownerLastHurt instanceof TameableEntity) {
                    if (((TameableEntity) this.ownerLastHurt).isTame()) {
                        LivingEntity owner1 = ((TameableEntity) this.ownerLastHurt).getOwner();
                        LivingEntity owner2 = this.hirable.getOwner();
                        // prevent hired units of the same player from fighting each other
                        if (owner1 != null && owner1.equals(owner2)) {
                            return false;
                        }
                    }
                } else if (this.ownerLastHurt instanceof CreeperEntity || this.ownerLastHurt instanceof ArmorStandEntity) {
                    return false;
                }
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, EntityPredicate.DEFAULT) && this.hirable.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.hirable.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
