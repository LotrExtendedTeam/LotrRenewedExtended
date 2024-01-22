package com.github.maximuslotro.lotroExtended.mixins.lotr.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

import org.spongepowered.asm.mixin.injection.At;

import lotr.common.command.ExtendedFellowshipCommand;
import lotr.common.command.ExtendedStructureScanningCommand;
import lotr.common.command.ExtendedStructureSummon;
import lotr.common.command.FellowshipMessageCommand;
import lotr.common.init.LOTRCommands;
import net.minecraft.command.CommandSource;

@Mixin(LOTRCommands.class)
public class MixinLOTRCommands {
	@Inject(method = "registerCommands(Lcom/mojang/brigadier/CommandDispatcher;)V", at = @At("HEAD"), remap=false)
	private static void injected(CommandDispatcher<CommandSource> dispatcher, CallbackInfo ci) {
		FellowshipMessageCommand.register(dispatcher);
		ExtendedFellowshipCommand.register(dispatcher);
		ExtendedStructureScanningCommand.register(dispatcher);
		ExtendedStructureSummon.register(dispatcher);
	}
}
