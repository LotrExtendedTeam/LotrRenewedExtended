package com.github.maximuslotro.lotrrextended.common.entity.passive;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedAvoidEntityGoal;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntityGender;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
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
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

public class ExtendedDuckEntity extends ExtendedAbstractGenderedAnimalEntity<ExtendedDuckEntity> {
	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;
	public int eggTime = getRandomEggTime();
	private static final DataParameter<Byte> DATA_ID_FLAGS = EntityDataManager.defineId(ExtendedDuckEntity.class, DataSerializers.BYTE);

	public ExtendedDuckEntity(EntityType<? extends ExtendedDuckEntity> p_i50282_1_, World p_i50282_2_) {
		super(p_i50282_1_, p_i50282_2_);
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		//this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, Ingredient.of(ExtendedTags.Items.DUCK_FOOD)));
		this.goalSelector.addGoal(4, new ExtendedDuckEntity.MoveToWaterGoal(this, 1.5D));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new ExtendedAvoidEntityGoal<>(this, PlayerEntity.class, 16.0F, 0.8D, 1.6D, (living) -> {
			return !(((ExtendedDuckEntity) living).getGender()==ExtendedEntityGender.GENDERLESS);
		}));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D, 60));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(9, new LookAtGoal(this, ExtendedDuckEntity.class, 6.0F));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_FLAGS, (byte)0);
	}

	protected boolean getFlag(int pFlagId) {
		return (this.entityData.get(DATA_ID_FLAGS) & pFlagId) != 0;
	}

	protected void setFlag(int pFlagId, boolean pValue) {
		byte b0 = this.entityData.get(DATA_ID_FLAGS);
		if (pValue) {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | pFlagId));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~pFlagId));
		}

	}

	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return this.isBaby() ? p_213348_2_.height * 0.85F : p_213348_2_.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public static boolean checkDuckSpawnRules(EntityType<ExtendedDuckEntity> pTurtle, IWorld pLevel, SpawnReason pSpawnType, BlockPos pPos, Random pRandom) {
		return isNearWater(pLevel, pPos, pRandom) && onSandOrGrassOrGravel(pLevel, pPos) && pLevel.getRawBrightness(pPos, 0) > 8;
	}

	private static boolean isNearWater(IWorld pReader, BlockPos pPos, Random pRandom) {
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

	private static boolean onSandOrGrassOrGravel(IBlockReader pReader, BlockPos pPos) {
		return isSand(pReader, pPos.below()) || isGrass(pReader, pPos.below());
	}

	private static boolean isSand(IBlockReader pReader, BlockPos pPos) {
		return pReader.getBlockState(pPos).is(BlockTags.SAND);
	}

	private static boolean isGrass(IBlockReader pReader, BlockPos pPos) {
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
		if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0 && !this.getGender().isMale() && !this.isInWater()) {
			this.playSound(ExtendedSounds.DUCK_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			this.spawnAtLocation(ExtendedItems.DUCK_EGG.get());
			this.eggTime = getRandomEggTime();
		}

	}

	private int getRandomEggTime() {
		return this.random.nextInt(24000) + 12000;
	}

	@Override
	public boolean canStandOnFluid(Fluid pFluid) {
		return pFluid.is(FluidTags.WATER);
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (Ingredient.of(LOTRItems.FISH_AND_CHIPS.get()).test(itemstack)) {
			if(this.getGender() == ExtendedEntityGender.MALE) {
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
		setBob(nbt.getBoolean("IsBobModeActivated"));
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("EggLayTime", this.eggTime);
		nbt.putBoolean("IsBobModeActivated", isBob());
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
	public boolean isEdibleFood(ItemStack stack) {
		return Ingredient.of(ExtendedTags.Items.DUCK_FOOD).test(stack);
	}

	public boolean isBob() {
		return this.getFlag(2);
	}

	public void setBob(boolean isBob) {
		this.setFlag(2, isBob);
	}

	@Override
	public boolean isGendered() {
		return true;
	}

	@Override
	public ExtendedDuckEntity getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		ExtendedDuckEntity baby = ExtendedEntities.DUCK.get().create(pServerLevel);
		// Set gender
		baby.setGenderRandomly(true);
		// Set domesticated status
		if(this.getGender()==ExtendedEntityGender.GENDERLESS && ((ExtendedDuckEntity)pMate).getGender()==ExtendedEntityGender.GENDERLESS && this.random.nextFloat()<0.9f) {
			baby.setGender(ExtendedEntityGender.GENDERLESS);
		}else if((this.getGender()==ExtendedEntityGender.GENDERLESS || ((ExtendedDuckEntity)pMate).getGender()==ExtendedEntityGender.GENDERLESS) && this.random.nextFloat()<0.7f) {
			baby.setGender(ExtendedEntityGender.GENDERLESS);
		}else if(this.random.nextFloat()>0.6f) {
			baby.setGender(ExtendedEntityGender.GENDERLESS);
		}
		// Set bob status
		if(baby.getGender()==ExtendedEntityGender.MALE) {
			if(this.getGender()==ExtendedEntityGender.MALE && isBob()) {
				baby.setBob(true);
			} else if(((ExtendedDuckEntity)pMate).getGender()==ExtendedEntityGender.MALE && ((ExtendedDuckEntity)pMate).isBob()) {
				baby.setBob(true);
			}
		}
		return baby;
	}

	@Override
	public boolean canFoodHealAnimal() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isBaby() && this.isInWater() && this.getFluidHeight(FluidTags.WATER) > this.getEyeHeight()) {
			if (this.getDeltaMovement().y < 0.08D) {
				this.setDeltaMovement(this.getDeltaMovement().add(0, 0.08D, 0));
	        }
		}
		this.floatDuck();
		this.checkInsideBlocks();
	}

	// Handles ducks underwater
	private void floatDuck() {
		if (this.isInWater()) {
			ISelectionContext iselectioncontext = ISelectionContext.of(this);
			if (iselectioncontext.isAbove(FlowingFluidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
				this.onGround = true;
			} else {
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
			}
		}
	}

	@Override
	public float getWalkTargetValue(BlockPos pPos, IWorldReader pLevel) {
		if (pLevel.getBlockState(pPos).getFluidState().is(FluidTags.WATER)) {
			return 5.0F;
		} else {
			return super.getWalkTargetValue(pPos, pLevel);
		}
	}

	@Override
	protected PathNavigator createNavigation(World pLevel) {
		return new ExtendedDuckEntity.WaterPathNavigator(this, pLevel);
	}

	private static class WaterPathNavigator extends GroundPathNavigator {
		WaterPathNavigator(ExtendedDuckEntity pDuck, World pLevel) {
			super(pDuck, pLevel);
		}

		@Override
		protected PathFinder createPathFinder(int pMaxVisitedNodes) {
			this.nodeEvaluator = new WalkNodeProcessor();
			return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
		}

		@Override
		protected boolean hasValidPathType(PathNodeType pPathType) {
			return pPathType != PathNodeType.WATER && pPathType != PathNodeType.WATER_BORDER ? super.hasValidPathType(pPathType) : true;
		}

		@Override
		public boolean isStableDestination(BlockPos pPos) {
			return this.level.getBlockState(pPos).is(Blocks.WATER) || super.isStableDestination(pPos);
		}
	}

	private static class MoveToWaterGoal extends MoveToBlockGoal {
		private final ExtendedDuckEntity duck;

		private MoveToWaterGoal(ExtendedDuckEntity pDuck, double pSpeedModifier) {
			super(pDuck, pSpeedModifier, 8, 2);
			this.duck = pDuck;
		}

		@Override
		public BlockPos getMoveToTarget() {
			return this.blockPos;
		}

		@Override
		public boolean canContinueToUse() {
			return !this.duck.isInWater() && this.isValidTarget(this.duck.level, this.blockPos);
		}

		@Override
		public boolean canUse() {
			return !this.duck.isInLava() && this.duck.random.nextFloat() < 0.125 && super.canUse();
		}

		@Override
		public boolean shouldRecalculatePath() {
			return this.tryTicks % 20 == 0;
		}

		/**
		 * Return true to set given position as destination
		 */
		@Override
		protected boolean isValidTarget(IWorldReader pLevel, BlockPos pPos) {
			return pLevel.getBlockState(pPos).is(Blocks.WATER) && pLevel.getBlockState(pPos.above()).isPathfindable(pLevel, pPos, PathType.LAND);
		}
	}
}