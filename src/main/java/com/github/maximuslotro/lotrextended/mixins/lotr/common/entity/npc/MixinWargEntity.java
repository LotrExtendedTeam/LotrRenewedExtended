package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedPlayerTypedRunArroundLikeCrazyGoal;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedAttributes;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMountedArmorItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMountedArmorItem.MountType;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenWargWindowPacket;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedAlignmentHelper;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.ai.goal.PanicIfBurningGoal;
import lotr.common.entity.ai.goal.RandomWalkingEvenWhenRiddenGoal;
import lotr.common.entity.npc.ExtendedIRidableEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import lotr.common.entity.npc.ai.goal.WargLeapAndDisableShieldGoal;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * This class is currently unused, and its purpose is to implement make Wargs rideable again (mwra)
 * @Mixin (WargEntity.class)
 * This class is not documented on purpose as it is unused and subject to re-write or deletion
 * */
@Mixin(WargEntity.class)
public abstract class MixinWargEntity extends NPCEntity implements IInventoryChangedListener, IJumpingMount, IEquipable, ExtendedIRidableEntity {
	@Unique
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("90833995-95a9-4586-b044-08e411e7d60c");
	@Unique
	private static final DataParameter<Byte> DATA_ID_FLAGS = EntityDataManager.defineId(WargEntity.class, DataSerializers.BYTE);
	@Unique
	private static final DataParameter<Optional<UUID>> DATA_ID_OWNER_UUID = EntityDataManager.defineId(WargEntity.class, DataSerializers.OPTIONAL_UUID);
	@Unique
	private Inventory inventory;
	/** The higher this value, the more likely the horse is to be tamed next time a player rides it. */
	@Unique
	private int temper;
	@Unique
	private float playerJumpPendingScale;

	protected MixinWargEntity(EntityType<? extends WargEntity> type, World w) {
		super(type, w);
	}

	@Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("TAIL"), remap=false)
	public void constructor(EntityType<? extends WargEntity> type, World w, CallbackInfo ci) {
		this.createInventory();
		this.maxUpStep = 1.0F;
	}

	@Shadow(remap = false)
	public boolean getIsLeaping() {return false;}

	@Shadow(remap = false)
	public void startLeaping() {}

	@Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void addAdditionalSaveDataExtended(CompoundNBT pCompound, CallbackInfo ci) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Temper", this.getTemper());
		pCompound.putBoolean("Tame", this.isTamed());
		if (this.getOwnerUUID() != null) {
			pCompound.putUUID("Owner", this.getOwnerUUID());
		}

		if (!this.inventory.getItem(0).isEmpty()) {
			pCompound.put("SaddleItem", this.inventory.getItem(0).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			pCompound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundNBT()));
		}
	}

	@Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void readAdditionalSaveDataExtended(CompoundNBT pCompound, CallbackInfo ci) {
		super.readAdditionalSaveData(pCompound);
		this.setTemper(pCompound.getInt("Temper"));
		this.setTamed(pCompound.getBoolean("Tame"));
		UUID uuid;
		if (pCompound.hasUUID("Owner")) {
			uuid = pCompound.getUUID("Owner");
		} else {
			String s = pCompound.getString("Owner");
			uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), s);
		}

		if (uuid != null) {
			this.setOwnerUUID(uuid);
		}

		if (pCompound.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(pCompound.getCompound("SaddleItem"));
			if (itemstack.getItem() == Items.SADDLE) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (pCompound.contains("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.of(pCompound.getCompound("ArmorItem"));
			if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
				this.inventory.setItem(1, itemstack);
			}
		}
		this.updateContainerEquipment();
	}

	@Inject(method = "defineSynchedData()V", at = @At("TAIL"))
	protected void defineSynchedData(CallbackInfo ci) {
		this.entityData.define(DATA_ID_FLAGS, (byte)0);
		this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
	}

	@Override
	protected void addNPCAI() {
		super.addNPCAI();
		goalSelector.addGoal(0, new SwimGoal(this));
		goalSelector.addGoal(1, new PanicIfBurningGoal(this, 1.6D));
		goalSelector.addGoal(3, new WargLeapAndDisableShieldGoal((WargEntity)(Object)this, 0.45F));
		addAttackGoal(4);
		goalSelector.addGoal(5, new ExtendedPlayerTypedRunArroundLikeCrazyGoal((WargEntity)(Object)this, 1.2D));
		goalSelector.addGoal(7, new RandomWalkingEvenWhenRiddenGoal(this, 1.0D));
		goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 12.0F, 0.02F));
		goalSelector.addGoal(8, new LookAtGoal(this, NPCEntity.class, 8.0F, 0.02F));
		goalSelector.addGoal(9, new LookAtGoal(this, LivingEntity.class, 12.0F, 0.02F));
		goalSelector.addGoal(10, new LookRandomlyGoal(this));
	}

	/**
	 * @author maximuslotro
	 * @reason Needing to add Jump Attribute to Wargs
	 * @return A MutableAttribute instance
	 */
	@Overwrite(remap = false)
	public static MutableAttribute regAttrs() {
		return NPCEntity.registerBaseNPCAttributes().add(Attributes.FOLLOW_RANGE, 32.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.22D).add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(ExtendedAttributes.WARG_JUMP_STRENGTH.get());
	}

	/**
	 * Need to set Warg health after base health is set, otherwise Wargs will only stay at 20 health
	 * @param ci Callback info
	 * @Inject (method = "applyRandomisedWargAttributes()V", at = @At("TAIL"), remap=false)
	 * */
	@Inject(method = "applyRandomisedWargAttributes()V", at = @At("TAIL"), remap=false)
	private void applyRandomisedWargAttributes(CallbackInfo ci) {
		this.setHealth((float) getAttribute(Attributes.MAX_HEALTH).getBaseValue());
	}

	@Unique
	private boolean getFlag(int pFlagId) {
		return (this.entityData.get(DATA_ID_FLAGS) & pFlagId) != 0;
	}

	@Unique
	private void setFlag(int pFlagId, boolean pValue) {
		byte b0 = this.entityData.get(DATA_ID_FLAGS);
		if (pValue) {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | pFlagId));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~pFlagId));
		}

	}

	@Override
	public boolean isTamed() {
		return this.getFlag(2);
	}

	@Unique
	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_ID_OWNER_UUID).orElse(null);
	}

	@Unique
	public void setOwnerUUID(@Nullable UUID pUuid) {
		this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(pUuid));
	}


	@Unique
	private void setTamed(boolean pTamed) {
		this.setFlag(2, pTamed);
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTamed();
	}

	@Override
	public void equipSaddle(@Nullable SoundCategory pSource) {
		this.inventory.setItem(0, new ItemStack(Items.SADDLE));
		if (pSource != null) {
			this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, pSource, 0.5F, 1.0F);
		}

	}

	/**
	 * Updates the control flags for the entity's goal selector.
	 * <p>
	 * This override modifies vanilla behavior to ensure that if the Warg is being 
	 * ridden by an {@link NPCEntity}, it retains its own autonomous AI control 
	 * (movement, looking, and jumping) rather than yielding full control to the passenger.
	 * If the passenger is not an NPC (e.g., a player) or if there is no rider, it 
	 * falls back to standard vanilla behavior.
	 * </p>
	 * 
	 * @see MobEntity#updateControlFlags()
	 */
	@Override
	protected void updateControlFlags() {
		// If an NPC is riding, we want the Warg to keep its own brain!
		if (this.getControllingPassenger() instanceof NPCEntity) {
			// Force the flags to remain TRUE so the AI goals can still execute
			this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
			this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
			this.goalSelector.setControlFlag(Goal.Flag.JUMP, !(this.getVehicle() instanceof BoatEntity));
		} else {
			// Fall back to vanilla behavior if a player or nothing is riding it
			super.updateControlFlags();
		}
	}

	@Override
	public boolean isSaddled() {
		return this.getFlag(4);
	}

	@Override
	public int getTemper() {
		return this.temper;
	}

	@Unique
	private void setTemper(int pTemper) {
		this.temper = pTemper;
	}

	@Override
	public int modifyTemper(int pAddedTemper) {
		int i = MathHelper.clamp(this.getTemper() + pAddedTemper, 0, this.getMaxTemper());
		this.setTemper(i);
		return i;
	}
	@Inject(method = "addWargRider(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;)V",
		at = @At(value = "INVOKE", target = "Llotr/common/entity/npc/WargEntity;createWargRider()Llotr/common/entity/npc/NPCEntity;", 
		shift = At.Shift.BY, by=2, remap=false), 
		remap=false, locals = LocalCapture.CAPTURE_FAILHARD
		)
	private void beforeNullCheckRiderLogic(IServerWorld sw, DifficultyInstance diff, SpawnReason reason, CallbackInfo ci, NPCEntity rider) {
		if (rider != null) {
			equipSaddle(null);
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		boolean hasAlignment = LOTRLevelData.getSidedData(pPlayer).getAlignmentData().getAlignment(getFaction())>=50;
		if (!this.isBaby()) {
			if (this.isTamed() && pPlayer.isSecondaryUseActive() && hasAlignment) {
				this.openInventory(pPlayer);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
			if (this.isVehicle()) {
				return super.mobInteract(pPlayer, pHand);
			}
		}
		if (!itemstack.isEmpty()) {
			if (this.isFood(itemstack) && hasAlignment) {
				return this.fedFood(pPlayer, itemstack);
			}

			ActionResultType actionresulttype = itemstack.interactLivingEntity(pPlayer, this, pHand);
			if (actionresulttype.consumesAction()) {
				return actionresulttype;
			}

			if (!this.isTamed() && hasAlignment) {
				this.playSound(getAmbientSound(), this.getSoundVolume(), this.getVoicePitch());
				this.level.broadcastEntityEvent(this, (byte)6);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.getItem() == Items.SADDLE;
			if ((this.isArmor(itemstack) || flag) && hasAlignment) {
				this.openInventory(pPlayer);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
		}
		if(pPlayer.getItemInHand(Hand.OFF_HAND).getItem().getUseAnimation(pPlayer.getItemInHand(pHand))==UseAction.BLOCK) {
			return ActionResultType.PASS;
		}
		if(hasAlignment) {
			this.doPlayerRide(pPlayer);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		}else {
			ExtendedAlignmentHelper.sendAlignmentError(pPlayer, "+50.0", getFaction());
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		}
	}

	@Override
	public boolean isPushable() {
		return !this.isVehicle() || !this.canBeControlledByRider();
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
		if (pFallDistance > 1.0F) {
			this.playSound(SoundEvents.GENERIC_BIG_FALL, 0.4F, 1.0F);
		}

		int i = this.calculateFallDamage(pFallDistance, pDamageMultiplier);
		if (i <= 0) {
			return false;
		} else {
			this.hurt(DamageSource.FALL, (float)i);
			if (this.isVehicle()) {
				for(Entity entity : this.getIndirectPassengers()) {
					entity.hurt(DamageSource.FALL, (float)i);
				}
			}

			this.playBlockFallSound();
			return true;
		}
	}

	@Override
	protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
		return MathHelper.ceil((pDistance * 0.5F - 3.0F) * pDamageMultiplier);
	}

	@Override
	public void makeMad() {
		this.playSound(getAmbientSound(), this.getSoundVolume(), this.getVoicePitch());

	}

	@Override
	public int getInventorySize() {
		return 2;
	}

	@Unique
	private void createInventory() {
		Inventory inventory = this.inventory;
		this.inventory = new Inventory(this.getInventorySize());
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getContainerSize(), this.inventory.getContainerSize());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getItem(j);
				if (!itemstack.isEmpty()) {
					this.inventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.inventory.addListener(this);
		this.updateContainerEquipment();
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
	}


	@Override
	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlotType.CHEST);
	}

	@Unique
	private void updateContainerEquipment() {
		if (!this.level.isClientSide) {
			this.setFlag(4, !this.inventory.getItem(0).isEmpty());
			this.setArmorEquipment(this.inventory.getItem(1));
			this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
		}
	}

	@Override
	public void setArmorEquipment(ItemStack pStack) {
		this.setItemSlot(EquipmentSlotType.CHEST, pStack);
		this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
		if (!this.level.isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isArmor(pStack)) {
				int i = ((ExtendedMountedArmorItem)pStack.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

	@Override
	public void containerChanged(IInventory pInvBasic) {
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
		}

	}

	@Override
	public int getMaxTemper() {
		return 100;
	}

	@Override
	public void openInventory(PlayerEntity pPlayer) {
		if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(pPlayer)) && this.isTamed()) {
			if(pPlayer instanceof ServerPlayerEntity) {
				openWargInventory((ServerPlayerEntity)pPlayer, (WargEntity)(Object)this, this.inventory);
			}
		}

	}

	@Unique
	private void openWargInventory(ServerPlayerEntity pPlayer, WargEntity pHorse, IInventory pInventory) {
		if (pPlayer.containerMenu != pPlayer.inventoryMenu) {
			pPlayer.closeContainer();
		}
		pPlayer.nextContainerCounter();
		ExtendedPacketHandler.sendToClient(new ExtendedSOpenWargWindowPacket(pPlayer.containerCounter, pInventory.getContainerSize(), pHorse.getId()), pPlayer);
		pPlayer.containerMenu = new ExtendedWargInventoryContainer(pPlayer.containerCounter, pPlayer.inventory, pInventory, pHorse);
		pPlayer.containerMenu.addSlotListener(pPlayer);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(pPlayer, pPlayer.containerMenu));
	}

	@Unique
	private ActionResultType fedFood(PlayerEntity pPlayer, ItemStack pStack) {
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

	@Unique
	private boolean handleEating(PlayerEntity pPlayer, ItemStack pStack) {
		boolean hatEaten = false;
		if (this.getHealth() < this.getMaxHealth()) {
			this.heal(2);
			hatEaten = true;
		}
		if ((hatEaten || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
			hatEaten = true;
			if (!this.level.isClientSide) {
				this.modifyTemper(3);
			}
		}
		return hatEaten;
	}

	@Unique
	private void doPlayerRide(PlayerEntity pPlayer) {
		if (!this.level.isClientSide) {
			pPlayer.yRot = this.yRot;
			pPlayer.xRot = this.xRot;
			pPlayer.startRiding(this);
		}
	}

	@Override
	protected boolean isImmobile() {
		return  super.isImmobile() || (this.isVehicle() && this.isSaddled() && this.canBeControlledByRider());

	}

	@Unique
	private boolean isFood(ItemStack pStack) {
		Item item = pStack.getItem();
		return item.isEdible() && item.getFoodProperties().isMeat();
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.inventory != null) {
			for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
				ItemStack itemstack = this.inventory.getItem(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}

		}
	}

	@Override
	public boolean tameWithName(PlayerEntity pPlayer) {
		this.setOwnerUUID(pPlayer.getUUID());
		this.setTamed(true);
		if (pPlayer instanceof ServerPlayerEntity) {
			ExtendedCriteriaTriggers.TAME_LIVING_TRIGGER.trigger((ServerPlayerEntity)pPlayer, this);
		}

		this.level.broadcastEntityEvent(this, (byte)7);
		return true;
	}

	@Override
	public void travel(Vector3d pTravelVector) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled() && this.isTamed()) {
				LivingEntity rider = (LivingEntity)this.getControllingPassenger();
				this.yRot = rider.yRot;
				this.yRotO = this.yRot;
				this.xRot = rider.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float strafe = rider.xxa * 0.5F;
				float forward = rider.zza;
				if (forward <= 0.0F) {
					forward *= 0.25F;
				}
				/*
				if (this.onGround && this.playerJumpPendingScale == 0.0F) {
					forward1 = 0.0F;
					forward2 = 0.0F;
				}
				 */
				if (this.playerJumpPendingScale > 0.0F && !this.getIsLeaping() && this.onGround) {
					double d0 = this.getAttributeValue(ExtendedAttributes.WARG_JUMP_STRENGTH.get()) * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
					double d1;
					if (this.hasEffect(Effects.JUMP)) {
						d1 = d0 + (double)((float)(this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
					} else {
						d1 = d0;
					}

					Vector3d vector3d = this.getDeltaMovement();
					this.setDeltaMovement(vector3d.x, d1, vector3d.z);
					startLeaping();
					this.hasImpulse = true;
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (forward > 0.0F) {
						float f2 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F));
						float f3 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f2 * this.playerJumpPendingScale, 0.0D, 0.4F * f3 * this.playerJumpPendingScale));
					}

					this.playerJumpPendingScale = 0.0F;
				}

				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vector3d(strafe, pTravelVector.y, forward));
				} else if (rider instanceof PlayerEntity) {
					this.setDeltaMovement(Vector3d.ZERO);
				}

				if (this.onGround) {
					this.playerJumpPendingScale = 0.0F;
					//this.setIsLeaping(false);
				}

				this.calculateEntityAnimation(this, false);
			} else {
				this.flyingSpeed = 0.02F;
				super.travel(pTravelVector);
			}
		}
	}

	@Unique
	private void playJumpSound() {
		this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
	}

	/**
	 * Currently only players can command wargs as allowing orcs to handle movement causes wargs to be very slow
	 * @return true if entity can control this mount
	 */
	@Override
	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof PlayerEntity;
	}

	/**
	 * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
	 * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
	 */
	@Nullable
	@Override
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public boolean isControlledByLocalInstance() {
		return super.isControlledByLocalInstance();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onPlayerJump(int pJumpPower) {
		if (this.isSaddled()) {
			if (pJumpPower < 0) {
				pJumpPower = 0;
			} else {
				startLeaping();
			}

			if (pJumpPower >= 90) {
				this.playerJumpPendingScale = 1.0F;
			} else {
				this.playerJumpPendingScale = 0.4F + 0.4F * (float)pJumpPower / 90.0F;
			}

		}
	}

	@Override
	public boolean canJump() {
		return this.isSaddled();
	}

	@Override
	public void handleStartJump(int pJumpPower) {
		this.playJumpSound();
	}

	@Override
	public void handleStopJump() {
	}

	/**
	 * Handles an entity event fired from net.minecraft.world.level.Level#broadcastEntityEvent
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte pId) {
		if (pId == 7) {
			this.spawnTamingParticles(true);
		} else if (pId == 6) {
			this.spawnTamingParticles(false);
		} else {
			super.handleEntityEvent(pId);
		}
	}

	@Unique
	@OnlyIn(Dist.CLIENT)
	protected void spawnTamingParticles(boolean pTamed) {
		IParticleData iparticledata = pTamed ? ParticleTypes.HEART : ParticleTypes.SMOKE;

		for(int i = 0; i < 10; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(iparticledata, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}

	}

	//	@Override
	//	public double getPassengersRidingOffset() {
	//		return (double)this.getBbHeight() * 0.75D;
	//	}

	/**
	 * Calculates a custom vertical riding offset based on the type of passenger.
	 * <p>
	 * Gives players a slightly higher seating position (75% of bounding box height) 
	 * compared to non-player entities like NPCs or mobs (63% of bounding box height) 
	 * to ensure proper visual scaling.
	 * </p>
	 *
	 * @param pPassenger The entity currently riding this mount.
	 * @return The vertical offset distance in blocks.
	 */
	public double getPassengersRidingOffsetExtended(Entity pPassenger) {
		return pPassenger instanceof PlayerEntity ? getBbHeight() * 0.75D : getBbHeight() * 0.63F;
	}
	/**
	 * Positions the passenger on top of this entity every tick.
	 * <p>
	 * This override applies a custom vertical offset using {@link #getPassengersRidingOffsetExtended}
	 * and introduces a horizontal offset ({@code offsetBack}) to position the rider slightly 
	 * backward on the mount's back using rotational vector math.
	 * </p>
	 * <p>
	 * Additionally, if the rider is a player and the mount is both tamed and saddled, 
	 * the mount's body and head rotation are synchronized to match the player's looking direction.
	 * </p>
	 *
	 * @param pPassenger The passenger entity to position.
	 * @see Entity#positionRider(Entity)
	 */
	@Override
	public void positionRider(Entity pPassenger) {
		if (this.hasPassenger(pPassenger) && pPassenger instanceof LivingEntity) {
			double d0 = this.getY() + this.getPassengersRidingOffsetExtended(pPassenger) + pPassenger.getMyRidingOffset();
			if(pPassenger instanceof PlayerEntity && this.isSaddled() && this.isTamed()) {
				LivingEntity rider = (LivingEntity)pPassenger;
				this.yRot = rider.yHeadRot;
				this.yBodyRot = this.yRot;
			}

			double offsetBack = -0.2; // negative = behind mount, positive = forward

			Vector3d vector3d = (new Vector3d(offsetBack, 0.0D, 0.0D)).yRot(-this.yRot * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
			pPassenger.setPos(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);
			if(pPassenger instanceof PlayerEntity && this.isSaddled() && this.isTamed()) {
				LivingEntity rider = (LivingEntity)pPassenger;
				rider.yBodyRot = this.yRot;
			}
		}
	}

	/**
	 * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
	 * for AI reasons)
	 */
	@Override
	public boolean onClimbable() {
		return false;
	}

	@Override
	public boolean canWearArmor() {
		return true;
	}

	@Override
	public boolean isWearingArmor() {
		return !this.getItemBySlot(EquipmentSlotType.CHEST).isEmpty();
	}

	@Override
	public boolean isArmor(ItemStack pStack) {
		return pStack.getItem() instanceof ExtendedMountedArmorItem && ((ExtendedMountedArmorItem)pStack.getItem()).getMountType()==MountType.WARG;
	}

	@Override
	public boolean setSlot(int pSlotIndex, ItemStack pStack) {
		int i = pSlotIndex - 400;
		if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
			if (i == 0 && pStack.getItem() != Items.SADDLE) {
				return false;
			} else if (i != 1 || this.canWearArmor() && this.isArmor(pStack)) {
				this.inventory.setItem(i, pStack);
				this.updateContainerEquipment();
				return true;
			} else {
				return false;
			}
		} else {
			int j = pSlotIndex - 500 + 2;
			if (j >= 2 && j < this.inventory.getContainerSize()) {
				this.inventory.setItem(j, pStack);
				return true;
			} else {
				return false;
			}
		}
	}
	/**
	 * Attempts to find a valid, safe coordinate for a passenger to land when dismounting in a specific direction.
	 * <p>
	 * This method scans vertically from the bottom of the mount's bounding box up to its maximum height 
	 * plus a padding threshold (0.75 blocks). It checks various player {@link Pose} variants to ensure the passenger 
	 * can physically fit into the target space without suffocating or clipping into solid blocks.
	 * </p>
	 *
	 * @param pDirection The directional offset vector relative to the mount's current position.
	 * @param pPassenger The living entity attempting to dismount.
	 * @return A {@link Vector3d} representing the target safe landing position, 
	 * or {@code null} if no valid location is found within bounds.
	 */
	@Unique
	@Nullable
	private Vector3d getDismountLocationInDirection(Vector3d pDirection, LivingEntity pPassenger) {
		double d0 = this.getX() + pDirection.x;
		double d1 = this.getBoundingBox().minY;
		double d2 = this.getZ() + pDirection.z;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(Pose pose : pPassenger.getDismountPoses()) {
			blockpos$mutable.set(d0, d1, d2);
			double d3 = this.getBoundingBox().maxY + 0.75D;

			while(true) {
				double d4 = this.level.getBlockFloorHeight(blockpos$mutable);
				if ((double)blockpos$mutable.getY() + d4 > d3) {
					break;
				}

				if (TransportationHelper.isBlockFloorValid(d4)) {
					AxisAlignedBB axisalignedbb = pPassenger.getLocalBoundsForPose(pose);
					Vector3d vector3d = new Vector3d(d0, (double)blockpos$mutable.getY() + d4, d2);
					if (TransportationHelper.canDismountTo(this.level, pPassenger, axisalignedbb.move(vector3d))) {
						pPassenger.setPose(pose);
						return vector3d;
					}
				}

				blockpos$mutable.move(Direction.UP);
				if (!((double)blockpos$mutable.getY() < d3)) {
					break;
				}
			}
		}

		return null;
	}
	/**
	 * Determines the final landing coordinates for a passenger exiting this mount.
	 * <p>
	 * It prioritizes ejecting the passenger to their **dominant-hand side** first. If that side is 
	 * blocked by terrain or solid blocks, it falls back to the **off-hand side**. If both escape 
	 * directions are obstructed, the passenger is dropped directly onto the mount's current position.
	 * </p>
	 *
	 * @param pLivingEntity The passenger entity requesting dismount placement.
	 * @return The optimal {@link Vector3d} coordinates to place the dismounted entity.
	 * @see Entity#getDismountLocationForPassenger(LivingEntity)
	 */
	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
		Vector3d vector3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HandSide.RIGHT ? 90.0F : -90.0F));
		Vector3d vector3d1 = this.getDismountLocationInDirection(vector3d, pLivingEntity);
		if (vector3d1 != null) {
			return vector3d1;
		} else {
			Vector3d vector3d2 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HandSide.LEFT ? 90.0F : -90.0F));
			Vector3d vector3d3 = this.getDismountLocationInDirection(vector3d2, pLivingEntity);
			return vector3d3 != null ? vector3d3 : this.position();
		}
	}
	/**
	 * Lazily initialized capability handler instance. 
	 * Used primarily to expose inventory slots (e.g., horse armor, chests, saddles) to automation or UI components.
	 */
	@Unique
	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;
	/**
	 * Retrieves the specified capability if it matches requirements.
	 * <p>
	 * Exposes the internal item handler capability if this entity is alive and the 
	 * requested capability is an {@code ITEM_HANDLER_CAPABILITY}.
	 * </p>
	 * * @param capability The requested capability instance.
	 * @param facing The directional face interacting with the capability (can be {@code null}).
	 * @return A {@link net.minecraftforge.common.util.LazyOptional} containing the cast capability handler, 
	 * or the super method's result if not matching.
	 */
	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
		if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
			return itemHandler.cast();
		return super.getCapability(capability, facing);
	}
	/**
	 * Invalidates and unregisters Forge capabilities attached to this entity.
	 * <p>
	 * Clears out the active {@link #itemHandler} reference and explicitly calls 
	 * {@link net.minecraftforge.common.util.LazyOptional#invalidate()} to prevent memory leaks 
	 * when the entity is unloaded or discarded from the world.
	 * </p>
	 */
	@Override
	protected void invalidateCaps() {
		super.invalidateCaps();
		if (itemHandler != null) {
			net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
			itemHandler = null;
			oldHandler.invalidate();
		}
	}
}