package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedInteractableEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableFriendlyNPCConversationGoal extends FriendlyNPCConversationGoal {

    ExtendedInteractableEntity interactableEntity;

    public ExtendedInteractableFriendlyNPCConversationGoal(NPCEntity entity, ExtendedInteractableEntity interactableEntity, float chanceIn) {
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
