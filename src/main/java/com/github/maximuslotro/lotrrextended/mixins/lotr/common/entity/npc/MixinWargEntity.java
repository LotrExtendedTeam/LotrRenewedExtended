package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedPlayerTypedRunArroundLikeCrazyGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedPlayerTypedWaterAvoidingRandomWalkingGoal;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedAttributes;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedWargInventoryContainer;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenWargWindowPacket;

import lotr.common.entity.ai.goal.PanicIfBurningGoal;
import lotr.common.entity.npc.ExtendedRidableWargEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.WargEntity;
import lotr.common.entity.npc.ai.goal.WargLeapAndDisableShieldGoal;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
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
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Mixin(WargEntity.class)
public abstract class MixinWargEntity extends NPCEntity implements IInventoryChangedListener, IJumpingMount, IEquipable, ExtendedRidableWargEntity {
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT, Items.SUGAR, Blocks.HAY_BLOCK.asItem(), Items.APPLE, Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
	private static final DataParameter<Byte> DATA_ID_FLAGS = EntityDataManager.defineId(WargEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Optional<UUID>> DATA_ID_OWNER_UUID = EntityDataManager.defineId(WargEntity.class, DataSerializers.OPTIONAL_UUID);
	private boolean isJumping;
	private Inventory inventory;
	/** The higher this value, the more likely the horse is to be tamed next time a player rides it. */
	private int temper;
	private float playerJumpPendingScale;

	protected MixinWargEntity(EntityType<? extends WargEntity> type, World w) {
		super(type, w);
	}

	@Shadow(remap = false)
	public boolean getIsLeaping() {return false;}

	@Shadow(remap = false)
	public void startLeaping() {}

	@Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("TAIL"), remap=false)
	public void constructor(EntityType<? extends WargEntity> type, World w, CallbackInfo ci) {
		this.createInventory();
	}

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
		goalSelector.addGoal(2, new ExtendedPlayerTypedRunArroundLikeCrazyGoal((WargEntity)(Object)this, 1.2D));
		goalSelector.addGoal(3, new WargLeapAndDisableShieldGoal((WargEntity)(Object)this, 0.45F));
		addAttackGoal(4);
		goalSelector.addGoal(7, new ExtendedPlayerTypedWaterAvoidingRandomWalkingGoal(this, 1.0D));
		goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 12.0F, 0.02F));
		goalSelector.addGoal(8, new LookAtGoal(this, NPCEntity.class, 8.0F, 0.02F));
		goalSelector.addGoal(9, new LookAtGoal(this, LivingEntity.class, 12.0F, 0.02F));
		goalSelector.addGoal(10, new LookRandomlyGoal(this));
	}

	/**
	 * @author maximuslotro
	 * @reason Needing to add Jump Attribute to Wargs
	 */
	@Overwrite(remap = false)
	public static MutableAttribute regAttrs() {
		return NPCEntity.registerBaseNPCAttributes().add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.22D).add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(ExtendedAttributes.WARG_JUMP_STRENGTH.get());
	}

	private boolean getFlag(int pFlagId) {
		return (this.entityData.get(DATA_ID_FLAGS) & pFlagId) != 0;
	}

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

	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_ID_OWNER_UUID).orElse(null);
	}

	public void setOwnerUUID(@Nullable UUID pUuid) {
		this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(pUuid));
	}

	public boolean isJumping() {
		return this.isJumping;
	}

	private void setTamed(boolean pTamed) {
		this.setFlag(2, pTamed);
	}

	public void setIsJumping(boolean pJumping) {
		this.isJumping = pJumping;
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

	@Override
	public boolean isSaddled() {
		return this.getFlag(4);
	}

	@Override
	public int getTemper() {
		return this.temper;
	}

	private void setTemper(int pTemper) {
		this.temper = pTemper;
	}

	@Override
	public int modifyTemper(int pAddedTemper) {
		int i = MathHelper.clamp(this.getTemper() + pAddedTemper, 0, this.getMaxTemper());
		this.setTemper(i);
		return i;
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if(!pPlayer.level.isClientSide) {
			pPlayer.sendMessage(new StringTextComponent("Is Tamed:"+isTamed()+", isSecondaryUseActive"+pPlayer.isSecondaryUseActive()+", isFood:"+isFood(itemstack)), Util.NIL_UUID);
		}
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
				this.playSound(getAmbientSound(), this.getSoundVolume(), this.getVoicePitch());
				this.level.broadcastEntityEvent(this, (byte)6);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.getItem() == Items.SADDLE;
			pPlayer.sendMessage(new StringTextComponent(!this.isBaby() +":"+ !this.isSaddled() +":"+ (itemstack.getItem() == Items.SADDLE)), Util.NIL_UUID);
			if (this.isArmor(itemstack) || flag) {
				this.openInventory(pPlayer);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
		}
		this.doPlayerRide(pPlayer);
		return ActionResultType.sidedSuccess(this.level.isClientSide);
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean isPushable() {
		return !this.isVehicle();
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
		if (pFallDistance > 1.0F) {
			this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
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

	private void updateContainerEquipment() {
		if (!this.level.isClientSide) {
			this.setFlag(4, !this.inventory.getItem(0).isEmpty());
		}
	}

	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	 */
	@Override
	public void containerChanged(IInventory pInvBasic) {
		boolean flag = this.isSaddled();
		this.updateContainerEquipment();
		if (this.tickCount > 20 && !flag && this.isSaddled()) {
			this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
		}

	}

	//public static AttributeModifierMap.MutableAttribute createBaseHorseAttributes() {
	//	return MobEntity.createMobAttributes().add(Attributes.JUMP_STRENGTH).add(Attributes.MAX_HEALTH, 53.0D).add(Attributes.MOVEMENT_SPEED, (double)0.225F);
	//}

	@Override
	public int getMaxTemper() {
		return 100;
	}

	private void openInventory(PlayerEntity pPlayer) {
		if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(pPlayer)) && this.isTamed()) {
			if(pPlayer instanceof ServerPlayerEntity) {
				openWargInventory((ServerPlayerEntity)pPlayer, (WargEntity)(Object)this, this.inventory);
			}
		}

	}

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

	private boolean handleEating(PlayerEntity pPlayer, ItemStack pStack) {
		boolean flag = false;
		float f = 0.0F;
		int i = 0;
		int j = 0;
		Item item = pStack.getItem();
		if (item == Items.WHEAT) {
			f = 2.0F;
			i = 20;
			j = 3;
		}

		if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
			this.heal(f);
			flag = true;
		}


		if (j > 0 && (flag || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
			flag = true;
			if (!this.level.isClientSide) {
				this.modifyTemper(j);
			}
		}
		return flag;
	}

	private void doPlayerRide(PlayerEntity pPlayer) {
		if (!this.level.isClientSide) {
			pPlayer.yRot = this.yRot;
			pPlayer.xRot = this.xRot;
			pPlayer.startRiding(this);
		}
	}

	/**
	 * Dead and sleeping entities cannot move
	 */
	@Override
	protected boolean isImmobile() {
		return super.isImmobile() && this.isVehicle() && this.isSaddled();
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	private boolean isFood(ItemStack pStack) {
		return FOOD_ITEMS.test(pStack);
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
			if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
				LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
				this.yRot = livingentity.yRot;
				this.yRotO = this.yRot;
				this.xRot = livingentity.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
				}

				if (this.onGround && this.playerJumpPendingScale == 0.0F) {
					f = 0.0F;
					f1 = 0.0F;
				}

				if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
					double d0 = this.getAttributeValue(ExtendedAttributes.WARG_JUMP_STRENGTH.get()) * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
					double d1;
					if (this.hasEffect(Effects.JUMP)) {
						d1 = d0 + (double)((float)(this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
					} else {
						d1 = d0;
					}

					Vector3d vector3d = this.getDeltaMovement();
					this.setDeltaMovement(vector3d.x, d1, vector3d.z);
					this.setIsJumping(true);
					this.hasImpulse = true;
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (f1 > 0.0F) {
						float f2 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F));
						float f3 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f2 * this.playerJumpPendingScale, 0.0D, 0.4F * f3 * this.playerJumpPendingScale));
					}

					this.playerJumpPendingScale = 0.0F;
				}

				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vector3d(f, pTravelVector.y, f1));
				} else if (livingentity instanceof PlayerEntity) {
					this.setDeltaMovement(Vector3d.ZERO);
				}

				if (this.onGround) {
					this.playerJumpPendingScale = 0.0F;
					this.setIsJumping(false);
				}

				this.calculateEntityAnimation(this, false);
			} else {
				this.flyingSpeed = 0.02F;
				super.travel(pTravelVector);
			}
		}
	}

	private void playJumpSound() {
		this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
	}

	/**
	 * @return true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
	 * by a player and the player is holding a carrot-on-a-stick
	 */
	@Override
	public boolean canBeControlledByRider() {
		System.out.println("canBeControlledByRider"+(getControllingPassenger() instanceof PlayerEntity));
		return this.getControllingPassenger() instanceof PlayerEntity;
	}

	/**
	 * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
	 * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
	 */
	@Nullable
	@Override
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : (this.getPassengers().get(0) instanceof PlayerEntity ? this.getPassengers().get(0) : null);
	}

	@Override
	public boolean isControlledByLocalInstance() {
		System.out.println("isControlledByLocalInstance:"+ super.isControlledByLocalInstance());
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

	/**
	 * Spawns particles for the horse entity. par1 tells whether to spawn hearts. If it is false, it spawns smoke."
	 */
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


	@Override
	public void positionRider(Entity pPassenger) {
		super.positionRider(pPassenger);
		if (pPassenger instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity)pPassenger;
			this.yBodyRot = playerEntity.yBodyRot;
		}

//		if (this.standAnimO > 0.0F) {
//			float f3 = MathHelper.sin(this.yBodyRot * ((float)Math.PI / 180F));
//			float f = MathHelper.cos(this.yBodyRot * ((float)Math.PI / 180F));
//			float f1 = 0.7F * this.standAnimO;
//			float f2 = 0.15F * this.standAnimO;
//			pPassenger.setPos(this.getX() + (double)(f1 * f3), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + (double)f2, this.getZ() - (double)(f1 * f));
//			if (pPassenger instanceof LivingEntity) {
//				((LivingEntity)pPassenger).yBodyRot = this.yBodyRot;
//			}
//		}

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
		return false;
	}

	@Override
	public boolean isWearingArmor() {
		return !this.getItemBySlot(EquipmentSlotType.CHEST).isEmpty();
	}

	@Override
	public boolean isArmor(ItemStack pStack) {
		return false;
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

	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
		if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
			return itemHandler.cast();
		return super.getCapability(capability, facing);
	}

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