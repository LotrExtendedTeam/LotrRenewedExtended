package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.npc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcMarriageHolder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

@Mixin(NPCEntity.class)
public class MixinNPCEntity implements ExtendedNPCEntity{
	private ExtendedNpcMarriageHolder marriageInfo = new ExtendedNpcMarriageHolder((NPCEntity)(Object)this);


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
}
