package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedInteractableEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableTalkToCurrentGoal extends TalkToCurrentGoal {

    ExtendedInteractableEntity interactableEntity;

    public ExtendedInteractableTalkToCurrentGoal(NPCEntity entity, ExtendedInteractableEntity interactableEntity) {
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
