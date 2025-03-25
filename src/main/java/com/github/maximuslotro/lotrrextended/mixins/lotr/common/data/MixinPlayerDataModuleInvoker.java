package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.data.PlayerDataModule;

/**
 * @reason To retrieve information from PlayerDataModule
 * @Mixin (PlayerDataModule.class)
 * */
@Mixin(PlayerDataModule.class)
public interface MixinPlayerDataModuleInvoker {
	/**
	 * @reason Used to retrieve the getPlayerUUID Method from PlayerDataModule since we can't make it public with AccessTransformers
	 * @Invoker (remap = false, value = "getPlayerUUID")
	 * @return The UUID for this PlayerDataModule
	 * */
	@Invoker(remap = false, value = "getPlayerUUID")
	UUID invokeGetPlayerUUID();
}
