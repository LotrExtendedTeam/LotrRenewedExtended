package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class ExtendedHiredWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    private NPCEntity entity;
    private ExtendedHirableEntity hirable;

    public ExtendedHiredWaterAvoidingRandomWalkingGoal(NPCEntity entity, double p_25988_) {
        super(entity, p_25988_);
        this.entity = entity;


        ExtendedHirableEntity hirable = ExtendedHiredUnitHelper.getExtendedHirableEntity(entity);
        if (hirable == null) {
            throw new IllegalArgumentException("The provided NPCEntity must implement ExtenedHirableEntity");
        }
        this.hirable = hirable;
    }

    public boolean canUse() {
        if (!hirable.isFollowing()) {
            return false;
        }
        return super.canUse();
    }
}
