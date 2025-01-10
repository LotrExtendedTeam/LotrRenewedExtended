package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedInteractableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class ExtendedInteractableWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    ExtendedInteractableEntity interactableEntity;

    public ExtendedInteractableWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47301_1_, ExtendedInteractableEntity interactableEntity, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);

        this.interactableEntity = interactableEntity;
    }

    public boolean canUse() {
        if (!interactableEntity.isInteractingWithPlayer()) {
            return super.canUse();
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        if (!interactableEntity.isInteractingWithPlayer()) {
            return super.canContinueToUse();
        } else {
            return false;
        }
    }
}
