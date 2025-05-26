package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

/**
 * We need to add additional data modules to NPCs, and we need to change spawn logic
 * @Mixin (NPCEntity.class)
 * */
@Mixin(NPCEntity.class)
public abstract class MixinNPCEntity extends CreatureEntity implements ExtendedNPCEntity{
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
	 * @reason Save additional entity data modules
	 * @Inject (method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	 * @param nbt The CompoundNBT to be saved to
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundNBT nbt, CallbackInfo ci) {
		marriageInfo.writeToNbtI(nbt);
		tradeInfo.writeToNbtI(nbt);
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
	 * @author maximuslotro
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
}
