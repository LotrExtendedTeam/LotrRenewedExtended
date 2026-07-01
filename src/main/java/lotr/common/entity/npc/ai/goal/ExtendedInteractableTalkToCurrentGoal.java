package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableTalkToCurrentGoal extends TalkToCurrentGoal {

	ExtendedIMerchantEntity interactableEntity;

    public ExtendedInteractableTalkToCurrentGoal(NPCEntity entity, ExtendedIMerchantEntity interactableEntity) {
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
