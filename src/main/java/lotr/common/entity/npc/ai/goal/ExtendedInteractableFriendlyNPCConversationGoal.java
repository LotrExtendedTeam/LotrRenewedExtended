package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableFriendlyNPCConversationGoal extends FriendlyNPCConversationGoal {

	ExtendedTraderEntity interactableEntity;

    public ExtendedInteractableFriendlyNPCConversationGoal(NPCEntity entity, ExtendedTraderEntity interactableEntity, float chanceIn) {
        super(entity, chanceIn);

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
}
