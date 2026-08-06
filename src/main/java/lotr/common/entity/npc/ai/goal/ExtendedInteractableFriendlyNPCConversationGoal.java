package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableFriendlyNPCConversationGoal extends FriendlyNPCConversationGoal {

	ExtendedIMerchantEntity interactableEntity;

    public ExtendedInteractableFriendlyNPCConversationGoal(NPCEntity entity, ExtendedIMerchantEntity interactableEntity, float chanceIn) {
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
