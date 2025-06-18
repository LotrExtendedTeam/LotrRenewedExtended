package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenHiredMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.ExtendedCaptainHelper;
import lotr.common.util.ExtendedHiredUnitHelper;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
public abstract class MixinNPCEntity extends CreatureEntity implements ExtendedNPCEntity {
	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param type A placeholder variable instance of CreatureEntity
	 * @param world A placeholder variable instance of World
	 * */
	protected MixinNPCEntity(EntityType<? extends CreatureEntity> type, World world) {
		super(type, world);
	}

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

	@Override
	public ItemStack getValidOtherFood() {
		if(unitInfo.isNpcActiveUnit()) {
			return unitInfo.getValidOtherFood();
		}
		return ItemStack.EMPTY;
	}
	/**
	 * @reason Need to implement banner protection spawn checks to entity spawns
	 * @author maximuslotro
	 * @param iworld The current IWorld instance
	 * @param reason The SpawnReason for this check
	 * @return True if the NPC can spawn
	 */
	@Override
	@Overwrite
	public boolean checkSpawnRules(IWorld iworld, SpawnReason reason) {
		if (super.checkSpawnRules(iworld, reason)) {
			if (reason == SpawnReason.NATURAL) {
				// Extended Start
				if((this.spawnRequiresDarkness && !this.isValidLightLevelForDarkSpawn(iworld)) || ExtendedBannerProtectionManager.getProtectionStatus(level, this.blockPosition(), new SingleProtectionInstance.EntityProtection(this), false, "checkSpawnRules").isNotAllowed()) {
					return false;
				}
				// Extended End
				if (this.spawnRequiresSurfaceBlock && !this.checkSpawningOnSurfaceBlock(iworld, reason)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @reason  Need to implement proper trader GUI logic
	 * @Inject (method = "mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), remap = true, cancellable = true)
	 * @param player The PlayerEntity that is interacting with this entity
	 * @param hand The player's hand that is interacting with this entity
	 * @param ci The local CallbackInfoReturnable instance for this Inject to return a value
	 * */
	@Inject(method = "mobInteract(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), remap = true, cancellable = true)
	public void mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> ci) {
		if(player.getItemInHand(Hand.OFF_HAND).getItem().getUseAnimation(player.getItemInHand(hand))==UseAction.BLOCK) {
			ci.setReturnValue(ActionResultType.PASS);
		} else {
			if(this instanceof ExtendedTraderEntity) {
				if (player.getItemInHand(hand).getItem() instanceof SpawnEggItem || player.getItemInHand(hand).getItem() instanceof LOTRSpawnEggItem) {
					ci.setReturnValue(ActionResultType.PASS);
				}else {
					ci.setReturnValue(ExtendedTraderHelper.openTraderGui((NPCEntity)(Object)this, player, (ExtendedTraderEntity)this));
				}
			}else if(this instanceof ExtendedCaptainEntity) {
				if (player.getItemInHand(hand).getItem() instanceof SpawnEggItem || player.getItemInHand(hand).getItem() instanceof LOTRSpawnEggItem) {
					ci.setReturnValue(ActionResultType.PASS);
				}else {
					ci.setReturnValue(ExtendedCaptainHelper.openCaptainGui((NPCEntity)(Object)this, player, (ExtendedCaptainEntity)this));
				}
			}
		}
		if (hand == Hand.MAIN_HAND) {
			if (unitInfo.isNpcActiveUnit() && unitInfo.getOwnerUUID().equals(player.getUUID())) { //TODO sync hired status and uuid to client
				if (!this.level.isClientSide()) {
					ExtendedPacketHandler.sendToServer(new ExtendedCOpenHiredMenuPacket(getId()));
				}
				ci.setReturnValue(ActionResultType.sidedSuccess(this.level.isClientSide()));
			}
		}
	}

	@Override
	public ItemStack eat(World world, ItemStack stack) { //TODO override npc eat goal, this does nothing rn (checkFood())
		if (stack.isEdible() && unitInfo.isNpcActiveUnit()) {
			this.heal(stack.getItem().getFoodProperties().getNutrition());
		}
		super.eat(world, stack);
		return stack;
	}

	@Override
	public boolean isAlliedTo(Entity otherEntity) {
		if (unitInfo.isNpcActiveUnit()) {
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

	@Override
	public void die(DamageSource damageSource) {
		if(unitInfo.isNpcActiveUnit()) {
			ExtendedHiredUnitHelper.die(unitInfo, damageSource);
		}
		super.die(damageSource);
	}

	@Inject(method = "dropCustomDeathLoot(Lnet/minecraft/util/DamageSource;IZ)V", at = @At("HEAD"), remap = true, cancellable = true)
	protected void dropCustomDeathLootExtended(DamageSource source, int looting, boolean playerHit, CallbackInfo ci) {
		if(unitInfo.isNpcNowBeforeUnit()) {
			ExtendedHiredUnitHelper.dropCustomDeathLoot(unitInfo, random, looting);
			ci.cancel();// Hired units should only handle it's own equipment.
		}
	}
}
