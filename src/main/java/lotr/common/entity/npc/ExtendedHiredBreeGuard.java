package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.ExtendedHiredFollowPlayerGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredSitGoal;
import lotr.common.entity.npc.ai.goal.ExtendedHiredWaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.world.World;

@Deprecated
public class ExtendedHiredBreeGuard extends BreeGuardEntity {

    public ExtendedHiredBreeGuard(EntityType<? extends BreeGuardEntity> type, World w) {
        super(type, w);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ExtendedHiredSitGoal(this));
        this.goalSelector.addGoal(3, new ExtendedHiredFollowPlayerGoal( this,1.3D, 8.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new ExtendedHiredWaterAvoidingRandomWalkingGoal(this,1.0D));

        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
    }
}
