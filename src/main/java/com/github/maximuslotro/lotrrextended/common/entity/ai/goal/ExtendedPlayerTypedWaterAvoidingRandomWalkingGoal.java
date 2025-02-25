package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Modified {@see RandomWalkingGoal}
 * */
public class ExtendedPlayerTypedWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

	public ExtendedPlayerTypedWaterAvoidingRandomWalkingGoal(CreatureEntity creature, double speed) {
		super(creature, speed);
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		if (this.mob.isVehicle() && this.mob.canBeControlledByRider()) {
			System.out.println("NA Using walk goal");
			return false;
		} else {
			System.out.println("using Goal");
			if (!this.forceTrigger && (this.mob.getNoActionTime() >= 100 || this.mob.getRandom().nextInt(this.interval) != 0)) {
				return false;
			}

			Vector3d vector3d = this.getPosition();
			if (vector3d == null) {
				return false;
			} else {
				this.wantedX = vector3d.x;
				this.wantedY = vector3d.y;
				this.wantedZ = vector3d.z;
				this.forceTrigger = false;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone() && !(this.mob.isVehicle() && this.mob.canBeControlledByRider());
	}
}
