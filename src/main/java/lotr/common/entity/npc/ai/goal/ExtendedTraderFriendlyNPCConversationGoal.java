package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedTraderFriendlyNPCConversationGoal extends FriendlyNPCConversationGoal {

    ExtendedTraderEntity trader;

    public ExtendedTraderFriendlyNPCConversationGoal(NPCEntity entity, ExtendedTraderEntity trader, float chanceIn) {
        super(entity, chanceIn);

        this.trader = trader;
    }

    @Override
    public boolean canUse() {
        if (!trader.isInteractingWithPlayer()) {
            return super.canUse();
        } else {
            return false;
        }
    }
}
