package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.List;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class ExtendedHurtByTargetGoal extends HurtByTargetGoal{

	public ExtendedHurtByTargetGoal(CreatureEntity creatureEntity, Class<?>... classes) {
		super(creatureEntity, classes);
	}

	@Override
	protected void alertOthers() {
		double d0 = this.getFollowDistance();
		AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0D, d0);
		List<MobEntity> mobs = this.mob.level.getLoadedEntitiesOfClass(this.mob.getClass(), axisalignedbb);

		for(MobEntity mobentity : mobs) {
			if (this.mob != mobentity && mobentity.getTarget() == null && !mobentity.isAlliedTo(this.mob.getLastHurtByMob())) {
				if(this.mob instanceof TameableEntity && ((TameableEntity)this.mob).getOwner() != ((TameableEntity)mobentity).getOwner()){
					// Don't attack if other tameable entity has a different owner (also handles null cases for un-tamed entities)
					continue;
				}else if(this.mob instanceof AbstractHorseEntity && this.mob.getLastHurtByMob().getUUID() == ((AbstractHorseEntity)mobentity).getOwnerUUID()){
					// Don't attack player if player is the alerted entity's owner
					continue;
				}
				// If the mob has specific reinforcementType sub-classes check against those
				if (this.toIgnoreAlert != null) {
					boolean reinforcement = false;

					for(Class<?> reinformementClass : this.toIgnoreAlert) {
						if (mobentity.getClass() == reinformementClass) {
							reinforcement = true;
							break;
						}
					}

					if (!reinforcement) {
						continue;
					}
				}
				this.alertOther(mobentity, this.mob.getLastHurtByMob());
			}
		}
	}
}
