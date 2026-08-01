package com.github.maximuslotro.lotrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.ProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredHurtByTargetGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredPlayerAttackedEntityTargetGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedHiredPlayerHurtByEntityTargetGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.ExtendedNearestAttackableTargetNPCGoal;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming.ExtendedFarmingAbstractTask;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredOverviewMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;
import com.google.common.base.Predicates;

import lotr.common.entity.npc.ExtendedICaptainEntity;
import lotr.common.entity.npc.ExtendedIFarmhand;
import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.ExtendedINPCEntitySettings;
import lotr.common.entity.npc.ExtendedITraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.NPCTargetSelector;
import lotr.common.entity.npc.data.ExtendedNpcCaptainInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcFarmhandInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import lotr.common.fac.Faction;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.ExtendedHiredUnitHelper;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

/**
 * We need to add additional data modules to NPCs, and we need to change spawn logic
 * @Mixin (NPCEntity.class)
 * */
@Mixin(NPCEntity.class)
public abstract class MixinNPCEntity extends CreatureEntity implements ExtendedINPCEntity {
	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of CreatureEntity
	 * @param world A placeholder variable instance of World
	 * */
	protected MixinNPCEntity(EntityType<? extends CreatureEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("TAIL"), remap=false)
	public void constructorExtended(EntityType<? extends NPCEntity> type, World w, CallbackInfo ci) {}

	/**
	 * A private data holder for the ExtendedNpcMarriageHolder module
	 * */
	@Unique
	private ExtendedNpcMarriageHolder marriageInfo = new ExtendedNpcMarriageHolder((NPCEntity)(Object)this);

	/**
	 * A private data holder for the ExtendedNpcTraderInfoHolder module
	 * */
	@Unique
	private ExtendedNpcTraderInfoHolder tradeInfo = new ExtendedNpcTraderInfoHolder((NPCEntity)(Object)this);

	/**
	 * A private data holder for the ExtendedNpcHiredUnitInfoHolder module
	 * */
	@Unique
	private ExtendedNpcHiredUnitInfoHolder unitInfo = new ExtendedNpcHiredUnitInfoHolder((NPCEntity)(Object)this);

	/**
	 * A private data holder for the ExtendedNpcHiredUnitInfoHolder module
	 * */
	@Unique
	private ExtendedNpcCaptainInfoHolder captainInfo = new ExtendedNpcCaptainInfoHolder((NPCEntity)(Object)this);

	/**
	 * A private data holder for the ExtendedNpcHiredUnitInfoHolder module
	 * */
	@Unique
	private ExtendedNpcFarmhandInfoHolder farmhandInfo = new ExtendedNpcFarmhandInfoHolder((NPCEntity)(Object)this);

	/**
	 * Get if this NPC requires surface to spawn
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	protected boolean spawnRequiresSurfaceBlock;

	/**
	 * Get if this NPC requires darkness to spawn
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	protected boolean spawnRequiresDarkness;

	/**
	 * Get if this NPC has had targeting goals set
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	protected boolean addedTargetingGoals;

	/**
	 * Get if this NPC has aggressive AI
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	protected boolean isTargetSeeker;

	/**
	 * Check if NPC can spawn on a block
	 * @Shadow (remap = false)
	 * @param iworld The current IWorld instance
	 * @param reason The SpawnReason for this check
	 * @return True if the NPC can spawn
	 * */
	@Shadow(remap = false)
	private boolean checkSpawningOnSurfaceBlock(IWorld iworld, SpawnReason reason) {return false;}

	/**
	 * Check if the current light level is valid to spawn at
	 * @Shadow (remap = false)
	 * @param iworld The current IWorld instance
	 * @return True if the NPC can spawn
	 * */
	@Shadow(remap = false)
	private boolean isValidLightLevelForDarkSpawn(IWorld iworld) {return false;}

	/**
	 * Check if NPC can be spoken to
	 * @Shadow (remap = false)
	 * @return True if the NPC can be spoken to
	 * */
	@Shadow(remap = false)
	protected boolean canSpeakToNPC() {return false;}

	/**
	 * Send a speechbank to a player
	 * @Shadow (remap = false)
	 * @param player The player to send a speechbank to
	 * @return True if speechbank was sent
	 * */
	@Shadow(remap = false)
	public boolean sendNormalSpeechTo(ServerPlayerEntity player) {return false;}

	/**
	 * Set NPC as talking to a given entity
	 * @Shadow (remap = false)
	 * @param entity The entity that is being spoken to
	 * @param time The length of the speechbank time spoken
	 * */
	@Shadow(remap = false)
	public void setTalkingToEntity(LivingEntity entity, int time) {}

	/**
	 * Add NPC targeting goals
	 * @Shadow (remap = false)
	 * @param seekTargets If the entity should be aggressive
	 * @return The top index of the added targeting goals
	 * */
	@Shadow(remap = false)
	private int addTargetingGoals(boolean seekTargets) {return 0;}

	/**
	 * Checks combat and alignment of NPC against passed player
	 * @Shadow (remap = false)
	 * @param player Player to be checked
	 * @return True if player is friendly
	 * */
	@Shadow(remap = false)
	public boolean isFriendlyAndAligned(PlayerEntity player) {return false;}
	/**
	 * Gets the faction of the NPC
	 * @Shadow (remap = false)
	 * @return The Faction
	 * */
	@Shadow(remap = false)
	public final Faction getFaction() {return null;}

	/**
	 * @reason Save additional entity data modules
	 * @Inject (method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	 * @param nbt The CompoundNBT to be saved to
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundNBT nbt, CallbackInfo ci) {
		marriageInfo.writeToNbtI(nbt);
		tradeInfo.writeToNbtI(nbt);
		unitInfo.writeToNbtI(nbt);
		captainInfo.writeToNbtI(nbt);
		farmhandInfo.writeToNbtI(nbt);
	}

	/**
	 * @reason Load additional entity data modules
	 * @Inject (method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	 * @param nbt The CompoundNBT to be loaded from
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundNBT nbt, CallbackInfo ci) {
		marriageInfo.loadFromNbtI(nbt);
		tradeInfo.loadFromNbtI(nbt);
		unitInfo.loadFromNbtI(nbt);
		captainInfo.loadFromNbtI(nbt);
		farmhandInfo.loadFromNbtI(nbt);
	}

	/**
	 * @reason Send additional entity information to the client
	 * @Inject (method = "onPlayerStartTrackingNPC(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"), remap = false)
	 * @param player The ServerPlayerEntity that the data will be sent to
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "onPlayerStartTrackingNPC(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"), remap = false)
	public void onPlayerStartTrackingNPC(ServerPlayerEntity player, CallbackInfo ci) {
		marriageInfo.sendDataToPlayerI(player);
		tradeInfo.sendDataToPlayerI(player);
		unitInfo.sendDataToPlayerI(player);
		captainInfo.sendDataToPlayerI(player);
		farmhandInfo.sendDataToPlayerI(player);
	}

	/**
	 * @reason Tick logic in additional entity data modules
	 * @Inject (method = "tick()V", at = @At("TAIL"))
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "tick()V", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		marriageInfo.tickI();
		tradeInfo.tickI();
		unitInfo.tickI();
		captainInfo.tickI();
		farmhandInfo.tickI();
	}

	/**
	 * Gets the ExtendedNpcMarriageHolder from this class on runtime via ExtendedNPCEntity
	 * @return The ExtendedNpcMarriageHolder of this NPCEntity instance
	 * */
	@Override
	public ExtendedNpcMarriageHolder getMarriageInfo() {
		return marriageInfo;
	}

	/**
	 * Gets the ExtendedNpcTraderInfoHolder from this class on runtime via ExtendedNPCEntity
	 * @return The ExtendedNpcTraderInfoHolder of this NPCEntity instance
	 * */
	@Override
	public ExtendedNpcTraderInfoHolder getTraderInfo() {
		return tradeInfo;
	}

	/**
	 * Gets the ExtendedNpcHiredUnitInfoHolder from this class on runtime via ExtendedNPCEntity
	 * @return The ExtendedNpcHiredUnitInfoHolder of this NPCEntity instance
	 * */
	@Override
	public ExtendedNpcHiredUnitInfoHolder getUnitInfo() {
		return unitInfo;
	}

	/**
	 * Gets the ExtendedNpcCaptainInfoHolder from this class on runtime via ExtendedNPCEntity
	 * @return The ExtendedNpcCaptainInfoHolder of this NPCEntity instance
	 * */
	@Override
	public ExtendedNpcCaptainInfoHolder getCaptainInfo() {
		return captainInfo;
	}

	/**
	 * Gets the ExtendedNpcFarmhandInfoHolder from this class on runtime via ExtendedNPCEntity
	 * @return The ExtendedNpcFarmhandInfoHolder of this NPCEntity instance
	 * */
	@Override
	public ExtendedNpcFarmhandInfoHolder getFarmhandInfo() {
		return farmhandInfo;
	}

	@Override
	public ItemStack getValidOtherFood() {
		if(unitInfo.isNpcActiveDuty()) {
			return unitInfo.getValidOtherFood();
		}
		return ItemStack.EMPTY;
	}

	@Override
	public NPCEntity getNPC() {
		return (NPCEntity)(Object)this;
	}
	/**
	 * Overwrites the default spawn rule logic to include banner protection checks.
	 * 
	 * @author maximuslotro
	 * @reason Need to implement banner protection spawn checks to entity spawns
	 * @reason Implements spawn checks based on banner protection mechanics to prevent spawning in protected areas.
	 * @param iworld The current {@link IWorld} instance where the entity is attempting to spawn.
	 * @param reason The {@link SpawnReason} representing the cause of the spawn event.
	 * @return {@code true} if the NPC is allowed to spawn; {@code false} otherwise.
	 */
	@Override
	@Overwrite
	public boolean checkSpawnRules(IWorld iworld, SpawnReason reason) {
		if (super.checkSpawnRules(iworld, reason)) {
			if (reason == SpawnReason.NATURAL) {
				// Extended Start
				if((this.spawnRequiresDarkness && !this.isValidLightLevelForDarkSpawn(iworld)) || ExtendedBannerProtectionManager.getProtectionStatus(level, this.blockPosition(), new ProtectionInstance.EntityProtection(this)).isNotAllowed()) {
					return false;
				}
				// Extended End
				if (this.spawnRequiresSurfaceBlock && !this.checkSpawningOnSurfaceBlock(iworld, reason)) {
					return false;
				}
			}
			if (reason == SpawnReason.EVENT) {
				return ExtendedBannerProtectionManager.getProtectionStatus(level, this.blockPosition(), new ProtectionInstance.EntityProtection(this)).isAllowed();
			}
			if (reason == SpawnReason.SPAWNER) {
				return ExtendedBannerProtectionManager.getProtectionStatus(level, this.blockPosition(), new ProtectionInstance.EntityProtection(this)).isAllowed();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Injects logic into mob interaction to handle custom trader and captain GUIs, and disallows use of spawn eggs.
	 * 
	 * @Inject (method = "mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), remap = true, cancellable = true)
	 * @reason Adds GUI opening behavior for custom trader and captain entities, and handles hired unit interaction.
	 * @param player The {@link PlayerEntity} interacting with this NPC.
	 * @param hand The {@link Hand} used to interact (MAIN_HAND or OFF_HAND).
	 * @param ci The {@link CallbackInfoReturnable} for controlling method return value.
	 */
	@Inject(method = "mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), remap = true, cancellable = true)
	public void mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> ci) {
		if(player.getItemInHand(Hand.OFF_HAND).getItem().getUseAnimation(player.getItemInHand(hand))==UseAction.BLOCK) {
			ci.setReturnValue(ActionResultType.PASS);
		} else if(this instanceof ExtendedITraderEntity) {
			if (player.getItemInHand(hand).getItem() instanceof SpawnEggItem || player.getItemInHand(hand).getItem() instanceof LOTRSpawnEggItem) {
				ci.setReturnValue(ActionResultType.PASS);
			}else {
				ci.setReturnValue(ExtendedTraderHelper.openTraderGui((NPCEntity)(Object)this, player));
			}

		}
		if (hand == Hand.MAIN_HAND) {
			if (unitInfo.isNpcActiveDuty() && player.getUUID().equals(unitInfo.getOwnerUUID())) {
				if (this.level.isClientSide()) {
					ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredOverviewMenuPacket(getId()));
				}
				ci.setReturnValue(ActionResultType.sidedSuccess(this.level.isClientSide()));
			}
		}
	}

	/**
	 * Overrides the eating behavior of NPCs to apply healing when on active duty.
	 *
	 * @param world The current {@link World} instance.
	 * @param stack The {@link ItemStack} being consumed.
	 * @return The same item stack after attempting to consume it.
	 */
	// Double healing?
	/*@Override
	public ItemStack eat(World world, ItemStack stack) {
		if (stack.isEdible() && unitInfo.isNpcActiveDuty()) {
			this.heal(stack.getItem().getFoodProperties().getNutrition());
		}
		super.eat(world, stack);
		return stack;
	}*/ 

	/**
	 * Determines if this NPC is allied with another entity.
	 *
	 * @param otherEntity The other {@link Entity} to compare with.
	 * @return {@code true} if allied; otherwise {@code false}.
	 */
	@Override
	public boolean isAlliedTo(Entity otherEntity) {
		if (unitInfo.isNpcActiveDuty()) {
			LivingEntity livingentity = unitInfo.getUnitOwner();
			if (otherEntity == livingentity) {
				return true;
			}

			if (livingentity != null) {
				return livingentity.isAlliedTo(otherEntity);
			}
		}
		return super.isAlliedTo(otherEntity);
	}

	/**
	 * Called when the entity dies; handles extended behavior for hired units.
	 *
	 * @param damageSource The {@link DamageSource} responsible for the entity’s death.
	 */
	@Override
	public void die(DamageSource damageSource) {
		if(unitInfo.isNpcActiveDuty()) {
			ExtendedHiredUnitHelper.die(unitInfo, damageSource);
		}
		super.die(damageSource);
	}

	/**
	 * Overrides the default loot-dropping behavior for hired NPC units when they die.
	 * <p>
	 * If the unit is a hired NPC (active or recently dismissed), this method cancels the
	 * standard loot drop logic and triggers custom loot logic instead. This ensures that
	 * hired units only drop their equipment and not standard entity loot.
	 * 
	 * @Inject (method = "dropCustomDeathLoot(Lnet/minecraft/util/DamageSource;IZ)V", at = @At("HEAD"), remap = true, cancellable = true)
	 * @param source     The {@link DamageSource} that caused the entity's death.
	 * @param looting    The looting level applied from the attacker, typically from enchantments.
	 * @param playerHit  {@code true} if the final hit was delivered by a player.
	 * @param ci         The {@link CallbackInfo} object used to cancel the method execution.
	 */
	@Inject(method = "dropCustomDeathLoot(Lnet/minecraft/util/DamageSource;IZ)V", at = @At("HEAD"), remap = true, cancellable = true)
	protected void dropCustomDeathLootExtended(DamageSource source, int looting, boolean playerHit, CallbackInfo ci) {
		if(unitInfo.isNpcActiveOrDismissed()) {
			ExtendedHiredUnitHelper.dropCustomDeathLoot(unitInfo, random, looting);
			ci.cancel();// Hired units should only handle it's own equipment.
		}
	}
	/**
	 * Overrides the method to add aggressive targeting goals for NPCs,
	 * injecting extended targeting logic at the head of the goal list.
	 * 
	 * @author maximuslotro
	 * @reason to inject extended targeting goals at the head
	 * @return the current top goal index after adding aggressive goals
	 */
	@Overwrite(remap = false)
	protected final int addAggressiveTargetingGoals() {
		if (this instanceof ExtendedINPCEntitySettings) {
			return this.addExtendedTargetingGoals(true);
		}
		return this.addTargetingGoals(true);
	}

	/**
	 * Overrides the method to add non-aggressive targeting goals for NPCs,
	 * injecting extended targeting logic at the head of the goal list.
	 * 
	 * @author maximuslotro
	 * @reason to inject extended targeting goals at the head
	 * @return the current top goal index after adding non-aggressive goals
	 */
	@Overwrite(remap = false)
	protected final int addNonAggressiveTargetingGoals() {
		if (this instanceof ExtendedINPCEntitySettings) {
			return this.addExtendedTargetingGoals(false);
		}
		return this.addTargetingGoals(false);
	}

	/**
	 * Adds extended targeting goals to the NPC's {@link #targetSelector}.
	 * <p>
	 * The following goals are added:
	 * <ul>
	 *     <li>{@link ExtendedHiredPlayerHurtByEntityTargetGoal}</li>
	 *     <li>{@link ExtendedHiredPlayerAttackedEntityTargetGoal}</li>
	 *     <li>{@link ExtendedHiredHurtByTargetGoal}</li>
	 *     <li>{@link ExtendedNearestAttackableTargetNPCGoal} for players (if {@code seekTargets})</li>
	 *     <li>{@link ExtendedNearestAttackableTargetNPCGoal} for other NPCs (if {@code seekTargets})</li>
	 * </ul>
	 * This method can only be called once per NPC instance.
	 *
	 * @param seekTargets whether to include nearest attackable target goals
	 * @return the highest goal index added (always 5)
	 * @throws IllegalStateException if called more than once
	 */
	@Unique
	private int addExtendedTargetingGoals(boolean seekTargets) {
		if (this.addedTargetingGoals) {
			throw new IllegalStateException("Mod development error - NPC addTargetingGoals can only be called once!");
		} else {
			this.addedTargetingGoals = true;
			this.isTargetSeeker = seekTargets;
			int index =1;
			this.targetSelector.addGoal(index++, new ExtendedHiredPlayerHurtByEntityTargetGoal((NPCEntity)(Object)this));
			this.targetSelector.addGoal(index++, new ExtendedHiredPlayerAttackedEntityTargetGoal((NPCEntity)(Object)this));
			this.targetSelector.addGoal(index++, new ExtendedHiredHurtByTargetGoal((NPCEntity)(Object)this));
			if (seekTargets) {
				this.targetSelector.addGoal(index++, new ExtendedNearestAttackableTargetNPCGoal<PlayerEntity>((NPCEntity)(Object)this, PlayerEntity.class, true, Predicates.alwaysTrue()));
				this.targetSelector.addGoal(index++, new ExtendedNearestAttackableTargetNPCGoal<NPCEntity>((NPCEntity)(Object)this, NPCEntity.class, true, new NPCTargetSelector((NPCEntity)(Object)this)));
			}
			return index;
		}
	}

	/**
	 * Injects custom targeting goals for NPCs at the start of the entity's goal selector.
	 *
	 * @Inject (method = "addTargetingGoals(Z)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", shift = At.Shift.BEFORE, ordinal = 0, remap = true), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
	 * @param seekTargets Indicates whether the entity should seek targets.
	 * @param cir         The {@link CallbackInfoReturnable} for controlling return behavior.
	 * @param index       The initial goal index prior to modification.
	 */
	@Inject(method = "addTargetingGoals(Z)I", 
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", shift = At.Shift.BEFORE, 
		ordinal = 0, remap = true),
		locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
	private void extended$addAdditionalTargetingGoals(boolean seekTargets, CallbackInfoReturnable<Integer> cir, int index) {
		this.targetSelector.addGoal(1, new ExtendedHiredPlayerHurtByEntityTargetGoal((NPCEntity)(Object)this));
		this.targetSelector.addGoal(2, new ExtendedHiredPlayerAttackedEntityTargetGoal((NPCEntity)(Object)this));
		index = 3;
	}

	/**
	 * Replaces the default {@code HurtByTargetGoal} with {@link ExtendedHiredHurtByTargetGoal} 
	 * during goal initialization, adding custom logic to prevent NPC units hired by the same player 
	 * from attacking each other unless explicitly allowed.
	 * <p>
	 * This method is injected using Mixin and intercepts the goal registration at the point where
	 * the first targeting goal is added.
	 *
	 * @ModifyArg(method = "addTargetingGoals(Z)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 0, remap = true), index = 1, remap = false)
	 * @param goal The original goal instance being registered (usually a {@code HurtByTargetGoal}).
	 * @return A new instance of {@link ExtendedHiredHurtByTargetGoal} with unit-aware, friendly-fire-safe behavior.
	 */
	@ModifyArg(method = "addTargetingGoals(Z)I",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", 
		ordinal = 0, remap = true), index = 1, remap = false)
	private Goal extended$replaceHurtGoalWithUnitRespectingGoal(Goal goal) {
		return new ExtendedHiredHurtByTargetGoal((NPCEntity)(Object)this);
	}

	/**
	 * Adjusts the targeting goal index offset after custom goals have been injected and injects custom targeting goals for NPCs at the end of the entity's goal selector.
	 * <p>
	 * This ensures that the goal priority index continues correctly after the addition
	 * of custom NPC targeting goals. Prevents conflicts with subsequent goal registrations.
	 *
	 * @Inject(method = "addTargetingGoals(Z)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", shift = At.Shift.AFTER, ordinal = 0, remap = true), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
	 * @param seekTargets Indicates whether the entity should seek targets.
	 * @param cir         The {@link CallbackInfoReturnable} for controlling return behavior.
	 * @param index       The targeting goal index after custom goals have been injected.
	 */
	@Inject(method = "addTargetingGoals(Z)I", 
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/entity/ai/goal/Goal;)V", shift = At.Shift.AFTER, 
		ordinal = 0, remap = true),
		locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
	private void extended$fixTargetingGoalsOffset(boolean seekTargets, CallbackInfoReturnable<Integer> cir, int index) {
		index = 4;
	}

	/**
	 * Server-side check for speechbanks
	 * @author maximuslotro
	 * @reason Add proper checks if player can trade
	 * @param player The player to check
	 * @return True if player can trade with NPC
	 */
	@Overwrite(remap=false)
	public boolean canTrade(PlayerEntity player) {
		if(isFriendlyAndAligned(player)) {
			if(this instanceof ExtendedIMerchantEntity && ((ExtendedIMerchantEntity)this).canTradeWithPlayer(player)) {
				return true;
			}else if(this instanceof ExtendedICaptainEntity && ((ExtendedICaptainEntity)this).canHireToPlayer(player)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canPickUpLootExtended() {
		return this instanceof ExtendedIFarmhand;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.level.getProfiler().push("looting_extended");
		if (!this.level.isClientSide && canPickUpLootExtended() && this.isAlive() && !this.dead && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
			for(ItemEntity itementity : this.level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(1.5D, 1.0D, 1.5D))) {
				if (!itementity.removed && !itementity.getItem().isEmpty() && !itementity.hasPickUpDelay() && this.wantsToPickUp(itementity.getItem())) {
					this.pickUpItem(itementity);
				}
			}
		}
		this.level.getProfiler().pop();
	}

	@Override
	protected void pickUpItem(ItemEntity pItemEntity) {
		ItemStack itemstack = pItemEntity.getItem();
		if (this instanceof ExtendedIFarmhand && getUUID().equals(pItemEntity.getOwner())) {
			ItemStack seeds = this.getFarmhandInfo().getSeedsItem();
			boolean isSeedMatch = !seeds.isEmpty() && seeds.sameItem(itemstack) && ItemStack.tagMatches(seeds, itemstack);
			boolean canFitInSeeds = isSeedMatch && seeds.getCount() < seeds.getMaxStackSize();
			boolean isSingleStackOnly = isSeedMatch && !ExtendedFarmingAbstractTask.shouldDepositItem(itemstack, seeds);

			ItemStack bonemeal = this.getFarmhandInfo().getBonemealItem();
			boolean isBonemealMatch = itemstack.getItem() == net.minecraft.item.Items.BONE_MEAL;
			boolean canFitInBonemeal = isBonemealMatch && !bonemeal.isEmpty() && bonemeal.getCount() < bonemeal.getMaxStackSize();

			Inventory outputInv = this.getFarmhandInfo().getFarmhandOutputInventory();
			if (canFitInSeeds || canFitInBonemeal || (!isBonemealMatch && ExtendedItemStackUtils.canAddItemToInventory(outputInv, itemstack, isSingleStackOnly))) {
				this.onItemPickup(pItemEntity);
				this.take(pItemEntity, itemstack.getCount());
				ItemStack remaining = itemstack.copy();
				if (isSeedMatch && canFitInSeeds) {
					int maxStack = seeds.getMaxStackSize();
					int seedSpaceRemaining = maxStack - seeds.getCount();
					int toAdd = Math.min(seedSpaceRemaining, remaining.getCount());
					seeds.grow(toAdd);
					remaining.shrink(toAdd);
				}
				if (!remaining.isEmpty() && isBonemealMatch && canFitInBonemeal) {
					int maxStack = bonemeal.getMaxStackSize();
					int bonemealSpaceRemaining = maxStack - bonemeal.getCount();
					int toAdd = Math.min(bonemealSpaceRemaining, remaining.getCount());
					bonemeal.grow(toAdd);
					remaining.shrink(toAdd);
				}
				if (!remaining.isEmpty() && !isBonemealMatch) {
					remaining = ExtendedItemStackUtils.addStackToInventory(outputInv, remaining, isSingleStackOnly);
				}				
				// Delete the entity and discard any remaining unhandled count
				pItemEntity.remove();
			}else {
				pItemEntity.remove(); // Delete items that could not be picked up
			}
		}
	}
	@Override
	public boolean wantsToPickUp(ItemStack pStack) {
		return this instanceof ExtendedIFarmhand;
	}
}
