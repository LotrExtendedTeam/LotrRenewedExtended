package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.maximuslotro.lotrrextended.LotrExtendedModServer;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedServerPlayerCodex;

import net.minecraft.entity.player.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
	private ExtendedServerPlayerCodex playerCodex;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void injectPlayerCodexInfo() {
		this.playerCodex = LotrExtendedModServer.codexPlayerList.getPlayerCodex((ServerPlayerEntity)(Object)this);
	}

	public ExtendedServerPlayerCodex getAdvancements() {
		return this.playerCodex;
	}
}
