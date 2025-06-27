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

/**
 * Abstract base class for gendered animal entities with extended behavior.
 * 
 * <p>This class extends {@link AnimalEntity} and adds support for gender management,
 * breeding mechanics based on gender, and serialization of gender data.</p>
 *
 * @param <T> The specific subclass type extending this class, used for type-safe breeding offspring.
 */
public abstract class ExtendedGenderedAnimalEntity<T extends ExtendedGenderedAnimalEntity<?>> extends AnimalEntity {

	private static final DataParameter<Integer> GENDER = EntityDataManager.defineId(ExtendedGenderedAnimalEntity.class, DataSerializers.INT);
	/**
     * Constructs a new ExtendedGenderedAnimalEntity with a randomly assigned gender
     * if the entity is gendered.
     *
     * @param entityType The entity type.
     * @param world The world the entity belongs to.
     */
	protected ExtendedGenderedAnimalEntity(EntityType<? extends ExtendedGenderedAnimalEntity<T>> entityType, World world) {
		super(entityType, world);
		this.setGenderRandomly(isGendered(), world);
	}

	/**
     * Checks if the given item stack is the food that this animal breed on.
     * Overrides the vanilla method to enforce breedable food logic.
     *
     * @param pStack The item stack to check.
     * @return {@code true} if the item is breedable food for this animal; {@code false} otherwise.
     */
	@Override
	public boolean isFood(ItemStack pStack) {
		return getBreedableFood(pStack);
	}

	/**
     * Abstract method to determine if the specified item stack is considered breedable food.
     *
     * @param stack The item stack to check.
     * @return {@code true} if the item is breedable food; {@code false} otherwise.
     */
	public abstract boolean getBreedableFood(ItemStack stack);

	/**
     * Abstract method to indicate whether this animal uses gender mechanics.
     *
     * @return {@code true} if the animal has gender; {@code false} otherwise.
     */
	public abstract boolean isGendered();

	/**
     * Abstract method to obtain the offspring entity resulting from breeding.
     *
     * @param pServerLevel The server world context.
     * @param pMate The mate entity.
     * @return The offspring entity of type T.
     */
	public abstract T getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate);

	/**
     * Allows subclasses to modify the offspring entity after it is created and gender is set.
     *
     * @param baby The newly created offspring entity.
     * @param pServerLevel The server world context.
     * @param pMate The mate entity.
     * @return The potentially modified offspring entity.
     */
	public T modifyBreedingOffspring(T baby, ServerWorld pServerLevel, AgeableEntity pMate) {
		return baby;
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
		T baby = getBreedingOffspring(pServerLevel, pMate);
		if(baby.isGendered()!=this.isGendered() || baby.isGendered()!=((ExtendedGenderedAnimalEntity<?>)pMate).isGendered()) {
			baby.setGenderRandomly(isGendered(), pServerLevel);
		}
		return modifyBreedingOffspring(baby, pServerLevel, pMate);
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
			this.setGender(EntityGender.byGenderName(nbt.getString("EntityGender")));
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
     * Returns the current gender of this animal entity.
     *
     * @return The entity's gender.
     */
	public EntityGender getGender() {
		return EntityGender.byGenderId(this.entityData.get(GENDER));
	}

	/**
     * Sets the gender of this animal entity.
     *
     * @param gender The gender to assign.
     */
	public void setGender(EntityGender gender) {
		this.entityData.set(GENDER, gender.getGenderId());
	}

	/**
     * Assigns a gender randomly to this animal if it is gendered;
     * otherwise sets it to genderless.
     *
     * @param gendered Whether this entity uses gender mechanics.
     * @param serverWorld The world instance to use for randomization.
     */
	public void setGenderRandomly(boolean gendered, World serverWorld) {
		if(!gendered) {
			this.setGender(EntityGender.GENDERLESS);
		}
		this.setGender(serverWorld.random.nextBoolean() ? EntityGender.MALE : EntityGender.FEMALE);
	}
}
