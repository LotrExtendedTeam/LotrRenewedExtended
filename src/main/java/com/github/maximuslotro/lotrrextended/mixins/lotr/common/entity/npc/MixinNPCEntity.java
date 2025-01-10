package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Mixin(NPCEntity.class)
public abstract class MixinNPCEntity extends CreatureEntity implements ExtendedNPCEntity{
	protected MixinNPCEntity(EntityType<? extends CreatureEntity> p_i48575_1_, World p_i48575_2_) {
		super(p_i48575_1_, p_i48575_2_);
	}

	private ExtendedNpcMarriageHolder marriageInfo = new ExtendedNpcMarriageHolder((NPCEntity)(Object)this);

	@Shadow(remap = false)
	protected boolean spawnRequiresSurfaceBlock;

	@Shadow(remap = false)
	protected boolean spawnRequiresDarkness;

	@Shadow(remap = false)
	private boolean checkSpawningOnSurfaceBlock(IWorld iworld, SpawnReason reason) {return false;}
	@Shadow(remap = false)
	private boolean isValidLightLevelForDarkSpawn(IWorld iworld) {return false;}

	@Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundNBT nbt, CallbackInfo ci) {
		marriageInfo.writeToNbt(nbt);

	}

	@Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundNBT nbt, CallbackInfo ci) {
		marriageInfo.loadFromNbt(nbt);
	}

	@Inject(method = "onPlayerStartTrackingNPC(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"), remap = false)
	public void onPlayerStartTrackingNPC(ServerPlayerEntity player, CallbackInfo ci) {
		marriageInfo.sendDataToPlayer(player);
	}

	@Inject(method = "tick()V", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		marriageInfo.tick();
	}

	@Override
	public ExtendedNpcMarriageHolder getMarriageInfo() {
		return marriageInfo;
	}

	/**
	 * @reason Need to change complex if statements
	 * @author maximuslotro
	 * */
	@Override
	@Overwrite
	public boolean checkSpawnRules(IWorld iworld, SpawnReason reason) {
		if (super.checkSpawnRules(iworld, reason)) {
			if (reason == SpawnReason.NATURAL) {
				// Extended Start
				if((this.spawnRequiresDarkness && !this.isValidLightLevelForDarkSpawn(iworld)) || ExtendedBannerProtectionManager.isBannerProtected(level, this.blockPosition(), new SingleProtectionInstance.EntityProtection(this), false, "checkSpawnRules")) {
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
