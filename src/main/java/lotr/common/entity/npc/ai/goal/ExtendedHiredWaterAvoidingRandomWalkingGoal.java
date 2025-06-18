package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class ExtendedHiredWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    private NPCEntity entity;

    public ExtendedHiredWaterAvoidingRandomWalkingGoal(NPCEntity entity, double p_25988_) {
        super(entity, p_25988_);
        this.entity = entity;
    }

    @Override
	public boolean canUse() {
        if (!(((ExtendedNPCEntity)entity).getUnitInfo().getOrders().isFollowingPlayer())) {
            return false;
        }
        return super.canUse();
    }
}
