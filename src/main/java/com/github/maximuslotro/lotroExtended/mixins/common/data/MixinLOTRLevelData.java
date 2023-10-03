package com.github.maximuslotro.lotroExtended.mixins.common.data;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import lotr.common.data.LOTRLevelData;
import lotr.common.fellowship.FellowshipHolderServer;
import net.minecraft.world.server.ServerWorld;

@Mixin(LOTRLevelData.class)
public class MixinLOTRLevelData {
	
	@Inject(method = "save(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	public void saveFactionData(ServerWorld world, CallbackInfo info) {
		FellowshipHolderServer.saveAll(world);
	}
	@Inject(method = "save(Lnet/minecraft/world/server/ServerWorld;)V", at = @At("HEAD"), remap=false)
	public void loadFactionData(ServerWorld world, CallbackInfo info) {
		FellowshipHolderServer.loadAll(world);
	}

}
