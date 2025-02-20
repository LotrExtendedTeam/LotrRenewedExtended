package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.maximuslotro.lotrrextended.LotrExtendedModServer;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedPlayerCodexes;
import com.github.maximuslotro.lotrrextended.common.extensions.entity.player.ExtendedServerPlayerEntity;

import net.minecraft.entity.player.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity implements ExtendedServerPlayerEntity{
	private ExtendedPlayerCodexes playerCodex;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void injectExtendedPlayerCodexInfo() {
		this.playerCodex = LotrExtendedModServer.codexPlayerList.getPlayerCodex((ServerPlayerEntity)(Object)this);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void injectExtendedCodexTick() {
		playerCodex.flushDirty((ServerPlayerEntity)(Object)this);
	}

	@Override
	public ExtendedPlayerCodexes getPlayerCodexes() {
		return this.playerCodex;
	}
}
