package com.github.maximuslotro.lotrrextended.common.entity.passive;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEntityGender;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.init.LOTRItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class ExtendedAbstractGenderedHorseEntity extends AbstractHorseEntity {

	private static final DataParameter<Integer> GENDER = EntityDataManager.defineId(ExtendedAbstractGenderedHorseEntity.class, DataSerializers.INT);

	protected ExtendedAbstractGenderedHorseEntity(EntityType<? extends ExtendedAbstractGenderedHorseEntity> genderedHorse, World world) {
		super(genderedHorse, world);
	}

	@Override
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
		this.setGenderRandomly(isGendered());
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	/**
     * Checks if the given item stack is the food that this animal can be fed.
     * Overrides the vanilla method to enforce breedable food logic.
     *
     * @param pStack The item stack to check.
     * @return {@code true} if the item is breedable food for this animal; {@code false} otherwise.
     */
	@Override
	public boolean isFood(ItemStack pStack) {
		return isEdibleFood(pStack);
	}

	/**
     * Checks if the given item stack is any food type that this animal can be fed or breed with.
     *
     * @param pStack The item stack to check.
     * @return {@code true} if the item is breedable food for this animal; {@code false} otherwise.
     */
	public boolean isPossableFood(ItemStack pStack) {
		return isBreedableFood(pStack) || isEdibleFood(pStack);
	}

	/**
     * Determine if the specified item stack is considered breedable food.
     *
     * @param stack The item stack to check.
     * @return {@code true} if the item is breedable food; {@code false} otherwise.
     */
	public boolean isBreedableFood(ItemStack stack) {
		return isEdibleFood(stack);
	}

	/**
     * Abstract method to determine if the specified item stack is considered edible food.
     *
     * @param stack The item stack to check.
     * @return {@code true} if the item is breedable food; {@code false} otherwise.
     */
	public abstract boolean isEdibleFood(ItemStack stack);

	/**
     * Abstract method to indicate whether this animal uses gender mechanics.
     *
     * @return {@code true} if the animal has gender; {@code false} otherwise.
     */
	public abstract boolean isGendered();

	/**
     * Abstract method to indicate whether this food should heal this animal
     *
     * @return {@code true} if the animal can be healed; {@code false} otherwise.
     */
	public abstract boolean canFoodHealAnimal();

	/**
     * Abstract method to obtain the offspring entity resulting from breeding.
     *
     * @param pServerLevel The server world context.
     * @param pMate The mate entity.
     * @return The offspring entity of type T.
     */
	public abstract ExtendedAbstractGenderedHorseEntity getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate);
	
	/**
     * Abstract method to allow sub-classes to override the saddle sound
     *
     * @return The {@link SoundEvent} for equipping a saddle
     */
	public abstract SoundEvent getSaddleSound();

	/**
     * Overridden method to allow sub-classes to override the saddle sound
     *
     * @param pSource The {@link SoundCategory} associated with this event
     */
	@Override
	public void equipSaddle(@Nullable SoundCategory pSource) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
		if (pSource != null) {
			this.level.playSound((PlayerEntity)null, this, getSaddleSound(), pSource, 0.5F, 1.0F);
		}
	}

	/**
     * Overridden method to allow sub-classes to override the saddle sound
     *
     * @param pInvBasic The {@link IInventory} that is being changed
     */
	@Override
	public void containerChanged(IInventory pInvBasic) {
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(getSaddleSound(), 0.5F, 1.0F);
		}
	}

	/**
	 * Defines synchronized entity data parameters including gender.
	 * Called during entity initialization.
	 */
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(GENDER, 3);
	}

	/**
     * Produces offspring by resetting gender if needed based on parents' gender status.
     *
     * @param pServerLevel The server world context.
     * @param pMate The mate entity.
     * @return The offspring entity with appropriately set gender.
     */
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return getBreedingOffspring(pServerLevel, pMate);
	}

	/**
     * Reads additional entity data from NBT, including the stored gender.
     *
     * @param nbt The compound NBT data.
     */
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		if (nbt.contains("EntityGender")) {
			this.setGender(ExtendedEntityGender.byGenderName(nbt.getString("EntityGender")));
		}

	}

	/**
     * Writes additional entity data to NBT, including the current gender.
     *
     * @param nbt The compound NBT data.
     */
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putString("EntityGender", this.getGender().getGenderName());
	}

	/**
     * Determines if this animal can mate with another animal, factoring gender compatibility.
     *
     * @param pOtherAnimal The other animal to check mating compatibility with.
     * @return {@code true} if mating is possible; {@code false} otherwise.
     */
	@Override
	public boolean canMate(AnimalEntity pOtherAnimal) {
		if (pOtherAnimal == this) {
			return false;
		} else if (pOtherAnimal.getClass() != this.getClass()) {
			return false;
		} else if (!(pOtherAnimal instanceof ExtendedAbstractGenderedHorseEntity) && !(this instanceof ExtendedAbstractGenderedHorseEntity)) {
			return this.isInLove() && pOtherAnimal.isInLove();
		} else {
			boolean canMateGeneric = isInLove() && pOtherAnimal.isInLove();
			if (!canMateGeneric) {
				return false;
			}
			ExtendedEntityGender otherAnimalGender = ((ExtendedAbstractGenderedHorseEntity) pOtherAnimal).getGender();
			if(otherAnimalGender == ExtendedEntityGender.UNKNOWN || getGender() == ExtendedEntityGender.UNKNOWN) {
				return false;
			} else if(otherAnimalGender == ExtendedEntityGender.FEMALE && getGender() == ExtendedEntityGender.MALE) {
				return true;
			} else if(otherAnimalGender == ExtendedEntityGender.MALE && getGender() == ExtendedEntityGender.FEMALE) {
				return true;
			} else if(otherAnimalGender == ExtendedEntityGender.GENDERLESS && getGender() == ExtendedEntityGender.GENDERLESS) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
     * Returns the current gender of this animal entity.
     *
     * @return The entity's gender.
     */
	public ExtendedEntityGender getGender() {
		return ExtendedEntityGender.byGenderId(this.entityData.get(GENDER));
	}

	/**
     * Sets the gender of this animal entity.
     *
     * @param gender The gender to assign.
     */
	public void setGender(ExtendedEntityGender gender) {
		this.entityData.set(GENDER, gender.getGenderId());
	}

	/**
     * Assigns a gender randomly to this animal if it is gendered;
     * otherwise sets it to genderless.
     *
     * @param gendered Whether this entity uses gender mechanics.
     */
	public void setGenderRandomly(boolean gendered) {
		if(!gendered) {
			this.setGender(ExtendedEntityGender.GENDERLESS);
			return;
		}
		this.setGender(this.random.nextBoolean() ? ExtendedEntityGender.MALE : ExtendedEntityGender.FEMALE);
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (this.isBreedableFood(itemstack)) {
			if (!this.level.isClientSide && this.getAge() == 0 && this.canFallInLove()) {
				this.usePlayerItem(pPlayer, itemstack);
				this.setInLove(pPlayer);
				return ActionResultType.SUCCESS;
			}
		}
		if (this.isEdibleFood(itemstack)) {
			if (this.isBaby()) {
				this.usePlayerItem(pPlayer, itemstack);
				this.ageUp((int)((float)(-this.getAge() / 20) * 0.1F), true);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
			// TODO add system for different heal amounts like horses
			//if (canFoodHealAnimal() && this.getHealth() < this.getMaxHealth()) { //&& f > 0.0F) {
			//	ExtendedLog.info("healing");
			//	this.usePlayerItem(pPlayer, itemstack);
			//	this.heal(1.0f);
			//	return ActionResultType.sidedSuccess(this.level.isClientSide);
			//}
		}
		return ActionResultType.PASS;
	}

	@Override
	public ActionResultType fedFood(PlayerEntity pPlayer, ItemStack pStack) {
		boolean flag = this.handleEating(pPlayer, pStack);
		if (!pPlayer.abilities.instabuild) {
			pStack.shrink(1);
		}

		if (this.level.isClientSide) {
			return ActionResultType.CONSUME;
		} else {
			return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
		}
	}

	@Override
	protected boolean handleEating(PlayerEntity pPlayer, ItemStack pStack) {
		boolean flag = false;
		float healAmount = 0.0F;
		int ageUpAmount = 0;
		int modifyTemperAmount = 0;
		Item item = pStack.getItem();
		if (item == Items.BEETROOT_SEEDS) {
			healAmount = 0.1F;
			ageUpAmount = 1;
		} else if (item == Items.MELON_SEEDS) {
			healAmount = 0.1F;
			ageUpAmount = 1;
		} else if (item == Items.PUMPKIN_SEEDS) {
			healAmount = 0.1F;
			ageUpAmount = 1;
		} else if (item == Items.WHEAT_SEEDS) {
			healAmount = 0.1F;
			ageUpAmount = 1;
		} else if (item == LOTRItems.FLAX_SEEDS.get()) {
			healAmount = 0.1F;
			ageUpAmount = 1;
		} else if (item == LOTRItems.PIPEWEED_SEEDS.get()) {
			healAmount = 0.1F;
			ageUpAmount = 1;
		} else if (item == Items.APPLE) {
			healAmount = 3.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 3;
		} else if (item == LOTRItems.GREEN_APPLE.get()) {
			healAmount = 3.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 3;
		} else if (item == LOTRItems.PEAR.get()) {
			healAmount = 3.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 3;
		} else if (item == LOTRItems.CHERRY.get()) {
			healAmount = 3.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 3;
		} else if (item == ExtendedItems.PLUM.get()) {
			healAmount = 3.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 3;
		} else if (item == Items.POTATO) {
			healAmount = 3.0F;
			ageUpAmount = 50;
			modifyTemperAmount = 3;
		} else if (item == Items.BEETROOT) {
			healAmount = 2.5F;
			ageUpAmount = 50;
			modifyTemperAmount = 3;
		} else if (item == Items.CARROT) {
			healAmount = 2.5F;
			ageUpAmount = 50;
			modifyTemperAmount = 3;
			if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
				flag = true;
				this.setInLove(pPlayer);
			}
		} else if (item == Items.WHEAT) {
			healAmount = 2.0F;
			ageUpAmount = 20;
			modifyTemperAmount = 3;
		} else if (item == LOTRItems.LETTUCE.get()) {
			healAmount = 2.0F;
			ageUpAmount = 20;
			modifyTemperAmount = 3;
		} else if (item == Items.SUGAR) {
			healAmount = 1.0F;
			ageUpAmount = 30;
			modifyTemperAmount = 3;
		} else if (item == Blocks.HAY_BLOCK.asItem()) {
			healAmount = 20.0F;
			ageUpAmount = 180;
		} else if (item == Items.APPLE) {
			healAmount = 3.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 3;
		} else if (item == Items.GOLDEN_CARROT) {
			healAmount = 4.0F;
			ageUpAmount = 60;
			modifyTemperAmount = 5;
			if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
				flag = true;
				this.setInLove(pPlayer);
			}
		} else if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
			healAmount = 10.0F;
			ageUpAmount = 240;
			modifyTemperAmount = 10;
			if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
				flag = true;
				this.setInLove(pPlayer);
			}
		} else if (item == ExtendedItems.BLACKBERRY.get()) {
			healAmount = 0.5F;
			ageUpAmount = 20;
			modifyTemperAmount = 1;
		} else if (item == ExtendedItems.BLUEBERRY.get()) {
			healAmount = 0.5F;
			ageUpAmount = 20;
			modifyTemperAmount = 1;
		} else if (item == ExtendedItems.CRANBERRY.get()) {
			healAmount = 0.5F;
			ageUpAmount = 20;
			modifyTemperAmount = 1;
		} else if (item == ExtendedItems.ELDERBERRY.get()) {
			healAmount = 0.5F;
			ageUpAmount = 20;
			modifyTemperAmount = 1;
		} else if (item == ExtendedItems.RASPBERRY.get()) {
			healAmount = 0.5F;
			ageUpAmount = 20;
			modifyTemperAmount = 1;
		} else {
			// Handle new foods being added
			healAmount = 0.5F;
			ageUpAmount = 2;
			modifyTemperAmount = 1;
		}

		if (this.getHealth() < this.getMaxHealth() && healAmount > 0.0F) {
			this.heal(healAmount);
			flag = true;
		}

		if (this.isBaby() && ageUpAmount > 0) {
			this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
			if (!this.level.isClientSide) {
				this.ageUp(ageUpAmount);
			}

			flag = true;
		}

		if (modifyTemperAmount > 0 && (flag || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
			flag = true;
			if (!this.level.isClientSide) {
				this.modifyTemper(modifyTemperAmount);
			}
		}

		if (flag) {
			this.eating();
		}

		return flag;
	}
}
