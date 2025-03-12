package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;

public class ExtendedInteractableTalkToCurrentGoal extends TalkToCurrentGoal {

	ExtendedTraderEntity interactableEntity;

    public ExtendedInteractableTalkToCurrentGoal(NPCEntity entity, ExtendedTraderEntity interactableEntity) {
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
