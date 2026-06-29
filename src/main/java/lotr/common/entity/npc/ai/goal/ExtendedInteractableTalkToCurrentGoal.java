package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedMerchantEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableTalkToCurrentGoal extends TalkToCurrentGoal {

	ExtendedMerchantEntity interactableEntity;

    public ExtendedInteractableTalkToCurrentGoal(NPCEntity entity, ExtendedMerchantEntity interactableEntity) {
        super(entity);
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
