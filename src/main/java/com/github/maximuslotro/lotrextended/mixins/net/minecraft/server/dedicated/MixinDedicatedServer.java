package com.github.maximuslotro.lotrextended.mixins.net.minecraft.server.dedicated;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.ExtendedVanillaProtectionHandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(DedicatedServer.class)
public class MixinDedicatedServer {

	@Shadow
	public DedicatedPlayerList getPlayerList() {return null;}

	@Inject(method = "isUnderSpawnProtection(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At(value = "HEAD"), cancellable = true)
	private void isUnderSpawnProtection$Extended(ServerWorld pLevel, BlockPos pPos, PlayerEntity pPlayer, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(ExtendedVanillaProtectionHandlers.isUnderSpawnProtection(pLevel, pPos, pPlayer, this.getPlayerList(), true, false));
	}
}
