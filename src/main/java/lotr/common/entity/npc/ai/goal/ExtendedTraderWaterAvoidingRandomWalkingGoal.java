package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedTraderEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class ExtendedTraderWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    ExtendedTraderEntity trader;

    public ExtendedTraderWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47301_1_, ExtendedTraderEntity trader, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);

        this.trader = trader;
    }

    public boolean canUse() {
        if (!trader.isInteractingWithPlayer()) {
            return super.canUse();
        } else {
            return false;
        }
    }
}
