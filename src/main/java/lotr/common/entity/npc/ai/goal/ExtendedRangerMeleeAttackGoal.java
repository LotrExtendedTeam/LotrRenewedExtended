package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;

public class ExtendedRangerMeleeAttackGoal extends NPCMeleeAttackGoal {

    private final ExtendedDunedainRangerEntity ranger;

    public ExtendedRangerMeleeAttackGoal(ExtendedDunedainRangerEntity ranger, double speed) {
        super(ranger, speed);
        this.ranger = ranger;
    }

    public void start() {
        super.start();
        ranger.unCloak();
    }

}
