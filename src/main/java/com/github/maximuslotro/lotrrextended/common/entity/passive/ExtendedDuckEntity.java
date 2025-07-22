package com.github.maximuslotro.lotrrextended.common.entity.passive;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.enums.EntityGender;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

public class ExtendedDuckEntity extends ExtendedGenderedAnimalEntity<ExtendedDuckEntity> {
	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;
	public int eggTime = this.random.nextInt(6000) + 6000;
	private static final DataParameter<Boolean> IS_BOB = EntityDataManager.defineId(ExtendedDuckEntity.class, DataSerializers.BOOLEAN);

	public ExtendedDuckEntity(EntityType<? extends ExtendedDuckEntity> p_i50282_1_, World p_i50282_2_) {
		super(p_i50282_1_, p_i50282_2_);
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, Ingredient.of(ExtendedTags.Items.DUCK_FOOD)));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_BOB, false);
	}

	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return this.isBaby() ? p_213348_2_.height * 0.85F : p_213348_2_.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public static boolean checkTurtleSpawnRules(EntityType<ExtendedDuckEntity> pTurtle, IWorld pLevel, SpawnReason pSpawnType, BlockPos pPos, Random pRandom) {
		return isNearWater(pLevel, pPos, pRandom) && onSandOrGrassOrGravel(pLevel, pPos) && pLevel.getRawBrightness(pPos, 0) > 8;
	}

	public static boolean isNearWater(IWorld pReader, BlockPos pPos, Random pRandom) {
		Biome biome = pReader.getBiome(pPos);
		if((biome.getBiomeCategory() == Biome.Category.RIVER) || (pRandom.nextInt(4)==0)) {
			return true;
		}
		for(BlockPos pos : BlockPos.betweenClosed(pPos.offset(-12, -5, -12), pPos.offset(12, 4, 12))) {
			if(pReader.getBlockState(pPos).is(Blocks.WATER)) {
				return true;
			}
		}
		return false;
	}

	public static boolean onSandOrGrassOrGravel(IBlockReader pReader, BlockPos pPos) {
		return isSand(pReader, pPos.below()) || isGrass(pReader, pPos.below());
	}

	public static boolean isSand(IBlockReader pReader, BlockPos pPos) {
		return pReader.getBlockState(pPos).is(BlockTags.SAND);
	}

	public static boolean isGrass(IBlockReader pReader, BlockPos pPos) {
		return pReader.getBlockState(pPos).is(Blocks.GRASS_BLOCK);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.oFlap = this.flap;
		this.oFlapSpeed = this.flapSpeed;
		this.flapSpeed = (float) (this.flapSpeed + (this.onGround ? -1 : 4) * 0.3D);
		this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
		if (!this.onGround && this.flapping < 1.0F) {
			this.flapping = 1.0F;
		}

		this.flapping = (float) (this.flapping * 0.9D);
		Vector3d vector3d = this.getDeltaMovement();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		this.flap += this.flapping * 2.0F;
		if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
			this.playSound(ExtendedSounds.DUCK_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			this.spawnAtLocation(ExtendedItems.DUCK_EGG.get());
			this.eggTime = this.random.nextInt(6000) + 6000;
		}

	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (Ingredient.of(LOTRItems.FISH_AND_CHIPS.get()).test(itemstack)) {
			if(this.getGender() == EntityGender.MALE) {
				setBob(true);
			}
			super.mobInteract(pPlayer, pHand);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		}
		return super.mobInteract(pPlayer, pHand);
	}

	@Override
	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ExtendedSounds.DUCK_AMBIANT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return ExtendedSounds.DUCK_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ExtendedSounds.DUCK_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(ExtendedSounds.DUCK_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		if (nbt.contains("EggLayTime")) {
			this.eggTime = nbt.getInt("EggLayTime");
		}
		if (nbt.contains("IsBobModeActivated")) {
			setBob(nbt.getBoolean("IsBobModeActivated"));
		}

	}

	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("EggLayTime", this.eggTime);
		if(isBob()) {
			nbt.putBoolean("IsBobModeActivated", true);
		}
	}

	@Override
	public void positionRider(Entity p_184232_1_) {
		super.positionRider(p_184232_1_);
		float f = MathHelper.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f1 = MathHelper.cos(this.yBodyRot * ((float) Math.PI / 180F));
		float f2 = 0.1F;
		float f3 = 0.0F;
		p_184232_1_.setPos(this.getX() + f2 * f, this.getY(0.5D) + p_184232_1_.getMyRidingOffset() + f3, this.getZ() - f2 * f1);
		if (p_184232_1_ instanceof LivingEntity) {
			((LivingEntity) p_184232_1_).yBodyRot = this.yBodyRot;
		}
	}

	@Override
	public boolean getBreedableFood(ItemStack stack) {
		return Ingredient.of(ExtendedTags.Items.DUCK_FOOD).test(stack);
	}

	public boolean isBob() {
		return this.entityData.get(IS_BOB);
	}

	public void setBob(boolean isBob) {
		this.entityData.set(IS_BOB, isBob);
	}

	@Override
	public boolean isGendered() {
		return true;
	}

	@Override
	public ExtendedDuckEntity getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return ExtendedEntities.DUCK.get().create(pServerLevel);
	}

	/**
	 * Set baby ducks {@link ExtendedGenderedAnimalEntity} if parent is bob type
	 * */
	@Override
	public ExtendedDuckEntity modifyBreedingOffspring(ExtendedDuckEntity baby, ServerWorld pServerLevel, AgeableEntity pMate) {
		if(baby.getGender() == EntityGender.MALE) {
			if(getGender()==EntityGender.MALE) {
				if(isBob()) {
					baby.setBob(true);
				}
			} else if(((ExtendedDuckEntity)pMate).getGender()==EntityGender.MALE && ((ExtendedDuckEntity)pMate).isBob()) {
				baby.setBob(true);
			}
		}
		return baby;
	}
}