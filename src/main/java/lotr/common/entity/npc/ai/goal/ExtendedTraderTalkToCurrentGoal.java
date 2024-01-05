package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedTraderTalkToCurrentGoal extends TalkToCurrentGoal {

    ExtendedTraderEntity trader;

    public ExtendedTraderTalkToCurrentGoal(NPCEntity entity, ExtendedTraderEntity trader) {
        super(entity);
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
