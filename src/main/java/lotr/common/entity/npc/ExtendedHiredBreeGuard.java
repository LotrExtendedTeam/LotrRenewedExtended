package lotr.common.entity.npc;

import lotr.common.entity.npc.ai.goal.ExtendedHiredWaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

@Deprecated
public class ExtendedHiredBreeGuard extends BreeGuardEntity {

    public ExtendedHiredBreeGuard(EntityType<? extends BreeGuardEntity> type, World w) {
        super(type, w);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new ExtendedHiredWaterAvoidingRandomWalkingGoal(this,1.0D));
    }
}
