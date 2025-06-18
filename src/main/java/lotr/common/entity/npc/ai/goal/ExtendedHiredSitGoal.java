package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ExtendedHiredSitGoal extends Goal {
    private final NPCEntity entity;
  
    public ExtendedHiredSitGoal(NPCEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));

    }

    @Override
    public boolean canContinueToUse() {
        return !((ExtendedNPCEntity)entity).getUnitInfo().getOrders().doesOrderAllowsMovement() || ((ExtendedNPCEntity)entity).getUnitInfo().isInterracting;
    }

    @Override
	public boolean canUse() {
        if (!((ExtendedNPCEntity)entity).getUnitInfo().isNpcActiveDuty()) {
            return false;
        } else if (this.entity.isInWaterOrBubble()) {
            return false;
        } else if (!this.entity.isOnGround()) {
            return false;
        } else {
            LivingEntity livingentity = ((ExtendedNPCEntity)entity).getUnitInfo().getUnitOwner();
            if (livingentity == null) {
                return true;
            } else {
                return (!(entity.distanceToSqr(livingentity) < 144.0D) || livingentity.getLastHurtByMob() == null)
                        && (!((ExtendedNPCEntity)entity).getUnitInfo().getOrders().doesOrderAllowsMovement() || ((ExtendedNPCEntity)entity).getUnitInfo().isInterracting);
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
