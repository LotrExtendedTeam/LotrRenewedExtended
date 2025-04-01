package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedTraderEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class ExtendedInteractableWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    ExtendedTraderEntity interactableEntity;

    public ExtendedInteractableWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47301_1_, ExtendedTraderEntity interactableEntity, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);

        this.interactableEntity = interactableEntity;
    }

    @Override
	public boolean canUse() {
        if (!interactableEntity.isInteractingWithPlayer()) {
            return super.canUse();
        } else {
            return false;
        }
    }

    @Override
	public boolean canContinueToUse() {
        if (!interactableEntity.isInteractingWithPlayer()) {
            return super.canContinueToUse();
        } else {
            return false;
        }
    }
}
