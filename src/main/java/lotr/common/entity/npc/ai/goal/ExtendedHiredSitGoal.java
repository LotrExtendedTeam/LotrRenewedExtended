package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ExtendedHiredSitGoal extends Goal {
    private final NPCEntity entity;
    private final ExtendedHirableEntity hirable;

    public ExtendedHiredSitGoal(NPCEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));

        ExtendedHirableEntity hirable = ExtendedHiredUnitHelper.getExtendedHirableEntity(entity);
        if (hirable == null) {
            throw new IllegalArgumentException("The provided NPCEntity must implement ExtenedHirableEntity");
        }
        this.hirable = hirable;
    }

    @Override
    public boolean canContinueToUse() {
        return !hirable.getOrders().doesOrderAllowsMovement() || hirable.isInventoryOpen();
    }

    @Override
	public boolean canUse() {
        if (!hirable.isTame()) {
            return false;
        } else if (this.entity.isInWaterOrBubble()) {
            return false;
        } else if (!this.entity.isOnGround()) {
            return false;
        } else {
            LivingEntity livingentity = hirable.getUnitOwner();
            if (livingentity == null) {
                return true;
            } else {
                return (!(entity.distanceToSqr(livingentity) < 144.0D) || livingentity.getLastHurtByMob() == null)
                        && (!hirable.getOrders().doesOrderAllowsMovement() || hirable.isInventoryOpen() || hirable.isEquipmentOpen());
            }
        }
    }

    @Override
	public void start() {
        this.entity.getNavigation().stop();
    }

    @Override
	public void stop() {

    }
}
