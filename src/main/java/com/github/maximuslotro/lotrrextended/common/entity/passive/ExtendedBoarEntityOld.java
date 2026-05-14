package com.github.maximuslotro.lotrrextended.common.entity.passive;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Deprecated
public class ExtendedBoarEntityOld extends ExtendedAbstractGenderedAnimalEntity<ExtendedBoarEntityOld> implements IRideable, IEquipable {
	private static final DataParameter<Boolean> DATA_SADDLE_ID = EntityDataManager.defineId(ExtendedBoarEntityOld.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DATA_BOOST_TIME = EntityDataManager.defineId(ExtendedBoarEntityOld.class, DataSerializers.INT);
	private final BoostHelper steering = new BoostHelper(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);

	public ExtendedBoarEntityOld(EntityType<? extends ExtendedBoarEntityOld> p_i50250_1_, World p_i50250_2_) {
		super(p_i50250_1_, p_i50250_2_);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT_ON_A_STICK), false));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, Ingredient.of(ExtendedTags.Items.BOAR_FOOD)));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, Ingredient.of(ExtendedTags.Items.BOAR_BREEDING)));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	/**
	 * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
	 * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
	 */
	@Override
	@Nullable
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	/**
	 * Returns true if the entity's rider (EntityPlayer) should face forward when mounted.
	 * currently only used in vanilla code by pigs.
	 *
	 * @param player The player who is riding the entity.
	 * @return If the player should orient the same direction as this entity.
	 */
	@Override
	public boolean shouldRiderFaceForward(PlayerEntity player) {
		return true;
	}

	/**
	 * @return true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
	 * by a player and the player is holding a carrot-on-a-stick
	 */
	@Override
	public boolean canBeControlledByRider() {
		Entity entity = this.getControllingPassenger();
		if (!(entity instanceof PlayerEntity)) {
			return false;
		} else {
			PlayerEntity playerentity = (PlayerEntity)entity;
			return playerentity.getMainHandItem().getItem() == Items.CARROT_ON_A_STICK || playerentity.getOffhandItem().getItem() == Items.CARROT_ON_A_STICK;
		}
	}

	@Override
	public void onSyncedDataUpdated(DataParameter<?> pKey) {
		if (DATA_BOOST_TIME.equals(pKey) && this.level.isClientSide) {
			this.steering.onSynced();
		}

		super.onSyncedDataUpdated(pKey);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_SADDLE_ID, false);
		this.entityData.define(DATA_BOOST_TIME, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
		this.steering.addAdditionalSaveData(pCompound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
		this.steering.readAdditionalSaveData(pCompound);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PIG_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.PIG_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PIG_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {
		this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		boolean flag = this.isPossableFood(pPlayer.getItemInHand(pHand));
		if (!flag && this.isSaddled() && !this.isVehicle() && !pPlayer.isSecondaryUseActive()) {
			if (!this.level.isClientSide) {
				pPlayer.startRiding(this);
			}

			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else {
			ActionResultType actionresulttype = super.mobInteract(pPlayer, pHand);
			if (!actionresulttype.consumesAction()) {
				ItemStack itemstack = pPlayer.getItemInHand(pHand);
				return itemstack.getItem() == Items.SADDLE ? itemstack.interactLivingEntity(pPlayer, this, pHand) : ActionResultType.PASS;
			} else {
				return actionresulttype;
			}
		}
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby();
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.isSaddled()) {
			this.spawnAtLocation(Items.SADDLE);
		}

	}

	@Override
	public boolean isSaddled() {
		return this.steering.hasSaddle();
	}

	@Override
	public void equipSaddle(@Nullable SoundCategory pSource) {
		this.steering.setSaddle(true);
		if (pSource != null) {
			this.level.playSound((PlayerEntity)null, this, SoundEvents.PIG_SADDLE, pSource, 0.5F, 1.0F);
		}

	}

	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return super.getDismountLocationForPassenger(pLivingEntity);
		} else {
			int[][] aint = TransportationHelper.offsetsForDirection(direction);
			BlockPos blockpos = this.blockPosition();
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			for(Pose pose : pLivingEntity.getDismountPoses()) {
				AxisAlignedBB axisalignedbb = pLivingEntity.getLocalBoundsForPose(pose);

				for(int[] aint1 : aint) {
					blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
					double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
					if (TransportationHelper.isBlockFloorValid(d0)) {
						Vector3d vector3d = Vector3d.upFromBottomCenterOf(blockpos$mutable, d0);
						if (TransportationHelper.canDismountTo(this.level, pLivingEntity, axisalignedbb.move(vector3d))) {
							pLivingEntity.setPose(pose);
							return vector3d;
						}
					}
				}
			}

			return super.getDismountLocationForPassenger(pLivingEntity);
		}
	}

	@Override
	public void travel(Vector3d pTravelVector) {
		this.travel(this, this.steering, pTravelVector);
	}

	@Override
	public float getSteeringSpeed() {
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F;
	}

	@Override
	public void travelWithInput(Vector3d pTravelVec) {
		super.travel(pTravelVec);
	}

	@Override
	public boolean boost() {
		return this.steering.boost(this.getRandom());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Vector3d getLeashOffset() {
		return new Vector3d(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
	}

	@Override
	public boolean isEdibleFood(ItemStack stack) {
		return Ingredient.of(ExtendedTags.Items.BOAR_FOOD).test(stack);
	}

	@Override
	public boolean isBreedableFood(ItemStack stack) {
		return Ingredient.of(ExtendedTags.Items.BOAR_BREEDING).test(stack);
	}

	@Override
	public boolean isGendered() {
		return true;
	}

	@Override
	public ExtendedBoarEntityOld getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return null;
	}

	@Override
	public boolean canFoodHealAnimal() {
		return true;
	}
}
