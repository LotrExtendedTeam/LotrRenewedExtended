package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.LotrExtendedModServer;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedPlayerCodexes;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkTriggers;
import com.github.maximuslotro.lotrrextended.common.extensions.entity.player.ExtendedServerPlayerEntity;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.server.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity implements ExtendedServerPlayerEntity{
	private ExtendedPlayerCodexes playerCodex;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void injectExtendedPlayerCodexInfo(MinecraftServer server, ServerWorld level, GameProfile profile, PlayerInteractionManager interactionManager, CallbackInfo ci) {
		this.playerCodex = LotrExtendedModServer.getCodexPlayerList().getPlayerCodex((ServerPlayerEntity)(Object)this);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void injectExtendedCodexTick(CallbackInfo ci) {
		playerCodex.flushDirty((ServerPlayerEntity)(Object)this);
	}

	@Override
	public ExtendedPlayerCodexes getPlayerCodexes() {
		return this.playerCodex;
	}

	@Inject(method = "doTick", at = @At("TAIL"))
	public void injectExtendedCodexdoTick(CallbackInfo ci) {
		if(((ServerPlayerEntity)(Object)this).tickCount %20==0) {
			ExtendedBenchmarkTriggers.LOCATION.trigger((ServerPlayerEntity)(Object)this);
		}
	}
}
