package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;

public class ExtendedRangerRangedAttackGoal extends NPCRangedAttackGoal<ExtendedDunedainRangerEntity> {

    private final ExtendedDunedainRangerEntity ranger;

    public ExtendedRangerRangedAttackGoal(ExtendedDunedainRangerEntity entity, double moveSpeed, int attackCooldown, float maxAttackDistance) {
        super(entity, moveSpeed, attackCooldown, maxAttackDistance);
        this.ranger = entity;
    }

    public void start() {
        super.start();

        if (!ranger.isCloaked() && ranger.getCooldownLeft() == 0) {
            ranger.cloak();
        }
    }
}
