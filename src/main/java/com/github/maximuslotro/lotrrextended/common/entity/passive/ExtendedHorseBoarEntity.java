package com.github.maximuslotro.lotrrextended.common.entity.passive;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import lotr.common.init.ExtendedEntities;
import net.minecraft.block.SoundType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedHorseBoarEntity extends ExtendedAbstractGenderedHorseEntity {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");

	public ExtendedHorseBoarEntity(EntityType<? extends ExtendedHorseBoarEntity> p_i50238_1_, World p_i50238_2_) {
		super(p_i50238_1_, p_i50238_2_);
	}

	@Override
	protected void randomizeAttributes() {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
		if (!this.inventory.getItem(1).isEmpty()) {
			pCompound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundNBT()));
		}
	}

	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlotType.CHEST);
	}

	private void setArmor(ItemStack pStack) {
		this.setItemSlot(EquipmentSlotType.CHEST, pStack);
		this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.of(pCompound.getCompound("ArmorItem"));
			if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
				this.inventory.setItem(1, itemstack);
			}
		}
		this.updateContainerEquipment();
	}

	@Override
	protected void updateContainerEquipment() {
		if (!this.level.isClientSide) {
			super.updateContainerEquipment();
			this.setArmorEquipment(this.inventory.getItem(1));
			this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
		}
	}

	private void setArmorEquipment(ItemStack pStack) {
		this.setArmor(pStack);
		if (!this.level.isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isArmor(pStack)) {
				int i = ((HorseArmorItem)pStack.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	 */
	@Override
	public void containerChanged(IInventory pInvBasic) {
		ItemStack itemstack = this.getArmor();
		super.containerChanged(pInvBasic);
		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}

	}

	@Override
	protected void playGallopSound(SoundType pSoundType) {
		super.playGallopSound(pSoundType);
		if (this.random.nextInt(10) == 0) {
			this.playSound(SoundEvents.HORSE_BREATHE, pSoundType.getVolume() * 0.6F, pSoundType.getPitch());
		}
		ItemStack stack = this.inventory.getItem(1);
		if (isArmor(stack)) {
			stack.onHorseArmorTick(level, this);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.HORSE_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.HORSE_DEATH;
	}

	@Override
	@Nullable
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		super.getHurtSound(pDamageSource);
		return SoundEvents.HORSE_HURT;
	}

	@Override
	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.HORSE_ANGRY;
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) { // TODO re-write
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (!this.isBaby()) {
			if (this.isTamed() && pPlayer.isSecondaryUseActive()) {
				this.openInventory(pPlayer);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			if (this.isVehicle()) {
				return super.mobInteract(pPlayer, pHand);
			}
		}

		if (!itemstack.isEmpty()) {
			if (this.isFood(itemstack)) {
				return this.fedFood(pPlayer, itemstack);
			}

			ActionResultType actionresulttype = itemstack.interactLivingEntity(pPlayer, this, pHand);
			if (actionresulttype.consumesAction()) {
				return actionresulttype;
			}

			if (!this.isTamed()) {
				this.makeMad();
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.getItem() == Items.SADDLE;
			if (this.isArmor(itemstack) || flag) {
				this.openInventory(pPlayer);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
		}

		if (this.isBaby()) {
			return super.mobInteract(pPlayer, pHand);
		} else {
			this.doPlayerRide(pPlayer);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		}
	}

	@Override
	public boolean canWearArmor() {
		return false;
	}

	@Override
	public boolean isArmor(ItemStack pStack) {
		return false;
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
	public boolean canFoodHealAnimal() {
		return true;
	}

	@Override
	public ExtendedHorseBoarEntity getBreedingOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return ExtendedEntities.HORSE_BOAR.get().create(pServerLevel);
	}

	@Override
	public ExtendedAbstractGenderedHorseEntity modifyBreedingOffspring(ExtendedAbstractGenderedHorseEntity baby, ServerWorld pServerLevel, AgeableEntity pMate) {
		this.setOffspringAttributes(pMate, baby);
		return baby;
	}
}
