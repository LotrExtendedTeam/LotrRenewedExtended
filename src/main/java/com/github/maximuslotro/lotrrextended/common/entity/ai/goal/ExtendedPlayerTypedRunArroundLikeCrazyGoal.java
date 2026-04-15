package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import lotr.common.entity.npc.ExtendedRidableEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Modified {@link net.minecraft.entity.ai.goal.RunAroundLikeCrazyGoal}
 * */
public class ExtendedPlayerTypedRunArroundLikeCrazyGoal extends Goal {
	   private final WargEntity warg;
	   private final double speedModifier;
	   private double posX;
	   private double posY;
	   private double posZ;

	   public ExtendedPlayerTypedRunArroundLikeCrazyGoal(WargEntity wargEntity, double pSpeedModifier) {
	      this.warg = wargEntity;
	      this.speedModifier = pSpeedModifier;
	      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	   }

	   /**
	    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	    * method as well.
	    */
	   @Override
	public boolean canUse() {
	      if (!((ExtendedRidableEntity)this.warg).isTamed() && this.warg.isVehicle()) {
	         Vector3d vector3d = RandomPositionGenerator.getPos(this.warg, 5, 4);
	         if (vector3d == null) {
	            return false;
	         } else {
	            this.posX = vector3d.x;
	            this.posY = vector3d.y;
	            this.posZ = vector3d.z;
	            return true;
	         }
	      } else {
	         return false;
	      }
	   }

	   /**
	    * Execute a one shot task or start executing a continuous task
	    */
	   @Override
	public void start() {
	      this.warg.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
	   }

	   /**
	    * Returns whether an in-progress EntityAIBase should continue executing
	    */
	   @Override
	public boolean canContinueToUse() {
	      return !((ExtendedRidableEntity)this.warg).isTamed() && !this.warg.getNavigation().isDone() && this.warg.isVehicle();
	   }

	   /**
	    * Keep ticking a continuous task that has already been started
	    */
	   @Override
	public void tick() {
	      if (!((ExtendedRidableEntity)this.warg).isTamed() && this.warg.getRandom().nextInt(50) == 0) {
	         Entity entity = this.warg.getPassengers().get(0);
	         if (entity == null) {
	            return;
	         }

	         if (entity instanceof PlayerEntity) {
	            int i = ((ExtendedRidableEntity)this.warg).getTemper();
	            int j = ((ExtendedRidableEntity)this.warg).getMaxTemper();
	            if (j > 0 && this.warg.getRandom().nextInt(j) < i) {
	            	((ExtendedRidableEntity)this.warg).tameWithName((PlayerEntity)entity);
	            	warg.setPersistenceRequired();
	               return;
	            }

	            ((ExtendedRidableEntity)this.warg).modifyTemper(5);
	         }

	         this.warg.ejectPassengers();
	         ((ExtendedRidableEntity)this.warg).makeMad();
	         this.warg.level.broadcastEntityEvent(this.warg, (byte)6);
	      }

	   }
	}
