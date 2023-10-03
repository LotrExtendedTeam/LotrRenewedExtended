package com.github.maximuslotro.lotroExtended.mixins.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

import org.spongepowered.asm.mixin.injection.At;

import lotr.common.command.FellowshipCommand;
import lotr.common.command.FellowshipMessageCommand;
import lotr.common.init.LOTRCommands;
import net.minecraft.command.CommandSource;

@Mixin(LOTRCommands.class)
public class MixinLOTRCommands {
	@Inject(method = "registerCommands(Lcom/mojang/brigadier/CommandDispatcher;)V", at = @At("HEAD"), remap=false)
	private static void injected(CommandDispatcher<CommandSource> dispatcher, CallbackInfo ci) {
		FellowshipMessageCommand.register(dispatcher);
		FellowshipCommand.register(dispatcher);
	}
}
