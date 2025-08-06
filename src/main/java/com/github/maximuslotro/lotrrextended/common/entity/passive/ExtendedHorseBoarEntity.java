package com.github.maximuslotro.lotrrextended.common.entity.passive;

import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedTamingGoal;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBoarColors;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBoarArmorItem;

import lotr.common.init.ExtendedEntities;
import net.minecraft.block.SoundType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Modified version of {@link net.minecraft.entity.passive.horse.HorseEntity}
 * */
public class ExtendedHorseBoarEntity extends ExtendedAbstractGenderedHorseEntity {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");

	private static final DataParameter<Integer> DATA_ID_TYPE_VARIANT = EntityDataManager.defineId(ExtendedHorseBoarEntity.class, DataSerializers.INT);

	public ExtendedHorseBoarEntity(EntityType<? extends ExtendedHorseBoarEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
		this.goalSelector.addGoal(2, new ExtendedTamingGoal(this, 1.2D));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D, ExtendedHorseBoarEntity.class));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());

	}

	public static AttributeModifierMap.MutableAttribute createWildBoarAttributes() {
		return createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Override
	protected void randomizeAttributes() {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
	}

	/**
	 * @return randomized max health
	 */
	@Override
	protected float generateRandomMaxHealth() { // Adjusted to legacy values according to wiki
		return 16.0F + (float)this.random.nextInt(6) + (float)this.random.nextInt(5);
	}

	/**
	 * @return randomized jump strength
	 */
	@Override
	protected double generateRandomJumpStrength() { // adjuested so jump is not as high as a horse
		return (double)0.3F + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
	}

	/**
	 * Conversion factor between internal units and blocks/sec is roughly 43.17, in legacy wild boars were known to 9.9 (average), 12.5 (max) speeds
	 * This equals a average of 0.229, which when calculated only using the default horse speed at 0.5D random gives 0.225.
	 * So setting the additive value to 0.465 results in a average speed of 0.22875, but then again, using max speed on wiki results in a different value
	 * As such, all I can conclude is that legacy used a different means of calculating random speeds, and all I can do is adapt the horse random speed generator
	 * to be as close as a can get.
	 * @return randomized movement speed
	 */
	@Override
	protected double generateRandomSpeed() {
		return ((double)0.32F + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
	}

	private void setTypeVariant(ExtendedBoarColors pVariant) {
		this.setTypeVariant(pVariant.getId());
	}

	private void setTypeVariant(int pTypeVariant) {
		this.entityData.set(DATA_ID_TYPE_VARIANT, pTypeVariant);
	}

	public ExtendedBoarColors getVariant() {
		return ExtendedBoarColors.byId(this.getTypeVariant());
	}

	private int getTypeVariant() {
		return this.entityData.get(DATA_ID_TYPE_VARIANT);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Variant", this.getTypeVariant());
		if (!this.inventory.getItem(1).isEmpty()) {
			pCompound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundNBT()));
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
		this.setTypeVariant(pCompound.getInt("Variant"));
		if (pCompound.contains("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.of(pCompound.getCompound("ArmorItem"));
			if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
				this.inventory.setItem(1, itemstack);
			}
		}
		this.updateContainerEquipment();
	}

	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlotType.CHEST);
	}

	private void setArmor(ItemStack pStack) {
		this.setItemSlot(EquipmentSlotType.CHEST, pStack);
		this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
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
				int i = ((ExtendedBoarArmorItem)pStack.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

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
	public SoundEvent getSaddleSound() {
		return SoundEvents.HORSE_SADDLE;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.PIG_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.PIG_DEATH;
	}

	@Override
	@Nullable
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		super.getHurtSound(pDamageSource);
		return SoundEvents.PIG_HURT;
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
			if (this.isPossableFood(itemstack)) {
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
		return true;
	}

	@Override
	public boolean isArmor(ItemStack pStack) {
		return pStack.getItem() instanceof ExtendedBoarArmorItem;
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
		ExtendedHorseBoarEntity baby = ExtendedEntities.HORSE_BOAR.get().create(pServerLevel);
		int i = this.random.nextInt(9);
		ExtendedBoarColors coatcolors;
		if (i < 4) {
			coatcolors = this.getVariant();
		} else if (i < 8) {
			coatcolors = ((ExtendedHorseBoarEntity)pMate).getVariant();
		} else {
			coatcolors = ExtendedBoarColors.getRandom(this.random);
		}
		baby.setTypeVariant(coatcolors);
		return baby;
	}

	@Override
	public ExtendedAbstractGenderedHorseEntity modifyBreedingOffspring(ExtendedAbstractGenderedHorseEntity baby, ServerWorld pServerLevel, AgeableEntity pMate) {
		this.setOffspringAttributes(pMate, baby);
		return baby;
	}

	@Override
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
		ExtendedBoarColors coatcolors;
		if (pSpawnData instanceof ExtendedHorseBoarEntity.ExtendedBoarData) {
			coatcolors = ((ExtendedHorseBoarEntity.ExtendedBoarData)pSpawnData).variant;
		} else {
			coatcolors = ExtendedBoarColors.getRandom(this.random);
			pSpawnData = new ExtendedHorseBoarEntity.ExtendedBoarData(coatcolors);
		}
		this.setTypeVariant(coatcolors);
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	public static class ExtendedBoarData extends AgeableEntity.AgeableData {
		public final ExtendedBoarColors variant;

		public ExtendedBoarData(ExtendedBoarColors pVariant) {
			super(true);
			this.variant = pVariant;
		}
	}
}
