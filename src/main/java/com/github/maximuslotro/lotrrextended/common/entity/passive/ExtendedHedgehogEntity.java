package com.github.maximuslotro.lotrrextended.common.entity.passive;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryHedgeBlock;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedMoveToBlockOrPanicGoal;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import lotr.common.init.ExtendedEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedHedgehogEntity extends AnimalEntity{
	private ExtendedHedgehogPanicGoal panicGoal;

	public ExtendedHedgehogEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute createHedgehogAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8).add(Attributes.ARMOR, 2).add(Attributes.MOVEMENT_SPEED, 0.25F);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return ExtendedEntities.HEDGEHOG.get().create(pServerLevel);
	}

	@Override
	public boolean isFood(ItemStack pStack) {
		return ExtendedTags.Items.HEDGEHOG_FOOD.contains(pStack.getItem());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		panicGoal = new ExtendedHedgehogEntity.ExtendedHedgehogPanicGoal(this, 1.25D, 24, 3);
		this.goalSelector.addGoal(1, panicGoal);
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(ExtendedTags.Items.HEDGEHOG_FOOD), false));
		this.goalSelector.addGoal(4, new ExtendedHedgehogEntity.ExtendedHedgehogChildFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, PlayerEntity.class, 16.0F, 1.6D, 1.4D));
		//this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, NPCEntity.class, 8.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, MonsterEntity.class, 4.0F, 1.6D, 1.4D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntitySize pSize) {
		return super.getStandingEyeHeight(pPose, pSize);
	}

	private class FindShelterGoal extends FleeSunGoal {
		private int interval = 100;

		public FindShelterGoal(double pSpeedModifier) {
			super(ExtendedHedgehogEntity.this, pSpeedModifier);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			if (!ExtendedHedgehogEntity.this.isSleeping() && this.mob.getTarget() == null) {
				if (ExtendedHedgehogEntity.this.level.isThundering()) {
					return true;
				} else if (this.interval > 0) {
					--this.interval;
					return false;
				} else {
					this.interval = 100;
					BlockPos blockpos = this.mob.blockPosition();
					return ExtendedHedgehogEntity.this.level.isDay() && ExtendedHedgehogEntity.this.level.canSeeSky(blockpos) && this.setWantedPos();
				}
			} else {
				return false;
			}
		}
	}

	private class ExtendedHedgehogPanicGoal extends ExtendedMoveToBlockOrPanicGoal {
		private LivingEntity lookTarget;
		private int lastLookTick;
		public ExtendedHedgehogPanicGoal(CreatureEntity creature, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
			super(creature, pSpeedModifier, pSearchRange, pVerticalSearchRange);
		}

		@Override
		protected boolean isValidTarget(IWorldReader pLevel, BlockPos pPos) {
			BlockState blockstate = pLevel.getBlockState(pPos);
			return (blockstate.getBlock() instanceof LeavesBlock || blockstate.getBlock() instanceof ExtendedBerryHedgeBlock) && pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK);
		}

		@Override
		public void onReachedTarget() {
			if (isValidTarget(ExtendedHedgehogEntity.this.level, targetPos)) {
				if(ExtendedHedgehogEntity.this.getLastHurtByMob()==null) {
					if(lookTarget==null || lookTarget.tickCount -lastLookTick > 100) {
						lookTarget = getLookableEntity(4);
					}
					if(lookTarget==null) {
						ExtendedHedgehogEntity.this.moveTo(targetPos.getX()+0.5D, targetPos.getY(), targetPos.getZ()+0.5D);
						return;
					}
					ExtendedHedgehogEntity.this.moveTo(targetPos.getX()+0.5D, targetPos.getY(), targetPos.getZ()+0.5D, 0.0f, getXRotD(lookTarget));
					ExtendedHedgehogEntity.this.setYHeadRot(getYRotD(lookTarget));
				}else {
					ExtendedHedgehogEntity.this.moveTo(targetPos.getX()+0.5D, targetPos.getY(), targetPos.getZ()+0.5D, 0.0f, getXRotD(ExtendedHedgehogEntity.this.getLastHurtByMob()));
					ExtendedHedgehogEntity.this.setYHeadRot(getYRotD(ExtendedHedgehogEntity.this.getLastHurtByMob()));

				}
			}
		}

		@Override
		public double acceptedDistance() {
			return 2.0D;
		}

		protected float getXRotD(Entity lookEntity) {
			double d0 = lookEntity.getX() - this.mob.getX();
			double d1 = lookEntity.getEyeY()- this.mob.getEyeY();
			double d2 = lookEntity.getZ() - this.mob.getZ();
			double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
			return (float)(-(MathHelper.atan2(d1, d3) * (180F / (float)Math.PI)));
		}

		protected float getYRotD(Entity lookEntity) {
			double d0 = lookEntity.getX() - this.mob.getX();
			double d1 = lookEntity.getZ() - this.mob.getZ();
			return (float)(MathHelper.atan2(d1, d0) * (180F / (float)Math.PI)) - 90.0F;
		}

		@Nullable
		public LivingEntity getLookableEntity(int lookDistance) {
			return mob.level.getNearestLoadedEntity(LivingEntity.class, new EntityPredicate().range(lookDistance).allowSameTeam().allowInvulnerable().allowNonAttackable().selector((living) -> {
				return EntityPredicates.notRiding(mob).test(living);
			}), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.mob.getBoundingBox().inflate(lookDistance, 3.0D, lookDistance));
		}
	}

	private class ExtendedHedgehogChildFollowParentGoal extends FollowParentGoal {

		public ExtendedHedgehogChildFollowParentGoal(ExtendedHedgehogEntity creature, double pSpeedModifier) {
			super(creature, pSpeedModifier);
		}

		@Override
		public boolean canUse() {
			return ExtendedHedgehogEntity.this.getLastHurtByMob()==null ? super.canUse() : false;
		}

		@Override
		public boolean canContinueToUse() { // TODO hook into parent panic goal
			if(((ExtendedHedgehogEntity)parent).getLastHurtByMob()!=null) {
				ExtendedHedgehogEntity.this.setLastHurtByMob(parent.getLastHurtByMob());
				return false;
			}
			return super.canContinueToUse();
		}
	}
}