package com.github.maximuslotro.lotrrextended.common.entity.passive;

import com.github.maximuslotro.lotrrextended.common.enums.EntityGender;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class ExtendedGenderedAnimalEntity<T extends ExtendedGenderedAnimalEntity<?>> extends AnimalEntity {

	private static final DataParameter<Integer> GENDER = EntityDataManager.defineId(ExtendedGenderedAnimalEntity.class, DataSerializers.INT);
	
	protected ExtendedGenderedAnimalEntity(EntityType<? extends ExtendedGenderedAnimalEntity<T>> entityType, World world) {
		super(entityType, world);
		this.setGenderRandomly(isGendered(), world);
	}

	/**
	 * Overridden vanilla method to ensure an animal has their breedable food set
	 * */
	@Override
	public boolean isFood(ItemStack pStack) {
		return getBreedableFood(pStack);
	}

	/**
	 * Abstract helper method to get breedable food for an animal
	 * */
	public abstract boolean getBreedableFood(ItemStack stack);

	/**
	 * Abstract method determine if the default gender setting of an animal
	 * */
	public abstract boolean isGendered();

	/**
	 * Abstract method to get the entities offspring entity
	 * */
	public abstract T getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate);

	/**
	 * Allow for modifying a baby's data by the parent after setting it's gender
	 * */
	public T modifyBreedingOffspring(T baby, ServerWorld pServerLevel, AgeableEntity pMate) {
		return baby;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(GENDER, 3);
	}

	/**
	 * Re-set gender based upon parents gender in the case mob can be gendered and genderless at different times
	 * */
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		T baby = getBreedingOffspring(pServerLevel, pMate);
		if(baby.isGendered()!=this.isGendered() || baby.isGendered()!=((ExtendedGenderedAnimalEntity<?>)pMate).isGendered()) {
			baby.setGenderRandomly(isGendered(), pServerLevel);
		}
		return modifyBreedingOffspring(baby, pServerLevel, pMate);
	}
	
	/**
	 * Overridden vanilla method to read entity gender from {@Link CompoundNBT}
	 * */
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		if (nbt.contains("EntityGender")) {
			this.setGender(EntityGender.byGenderName(nbt.getString("EntityGender")));
		}

	}

	/**
	 * Overridden vanilla method to save gender to {@Link CompoundNBT}
	 * */
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putString("EntityGender", this.getGender().getGenderName());
	}

	/**
	 * Overridden vanilla method to allow for gender checking when determining if two animals can mate 
	 * */
	@Override
	public boolean canMate(AnimalEntity pOtherAnimal) {
		if (pOtherAnimal == this) {
			return false;
		} else if (pOtherAnimal.getClass() != this.getClass()) {
			return false;
		} else if (!(pOtherAnimal instanceof ExtendedGenderedAnimalEntity) && !(this instanceof ExtendedGenderedAnimalEntity)) {
			return this.isInLove() && pOtherAnimal.isInLove();
		} else {
			boolean canMateGeneric = isInLove() && pOtherAnimal.isInLove();
			if (!canMateGeneric) {
				return false;
			}
			EntityGender otherAnimalGender = ((ExtendedGenderedAnimalEntity<?>) pOtherAnimal).getGender();
			if(otherAnimalGender == EntityGender.UNKNOWN || getGender() == EntityGender.UNKNOWN) {
				return false;
			} else if(otherAnimalGender == EntityGender.FEMALE && getGender() == EntityGender.MALE) {
				return true;
			} else if(otherAnimalGender == EntityGender.MALE && getGender() == EntityGender.FEMALE) {
				return true;
			} else if(otherAnimalGender == EntityGender.GENDERLESS && getGender() == EntityGender.GENDERLESS) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Helper method to get an animal's gender
	 * */
	public EntityGender getGender() {
		return EntityGender.byGenderId(this.entityData.get(GENDER));
	}

	/**
	 * Helper method to set an animal's gender
	 * */
	public void setGender(EntityGender gender) {
		this.entityData.set(GENDER, gender.getGenderId());
	}

	/**
	 * Helper method to set an animal's gender randomly
	 * */
	public void setGenderRandomly(boolean gendered, World serverWorld) {
		if(!gendered) {
			this.setGender(EntityGender.GENDERLESS);
		}
		this.setGender(serverWorld.random.nextBoolean() ? EntityGender.MALE : EntityGender.FEMALE);
	}
}
