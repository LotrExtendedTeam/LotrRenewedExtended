package com.github.maximuslotro.lotrrextended.mixins.lotr.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.block.ExtendedSignTypes;
import lotr.common.block.LOTRSignTypes;
import net.minecraft.block.WoodType;

@Mixin(LOTRSignTypes.class)
public class MixinLOTRSignTypes {

	@Shadow(remap = false)
	private static WoodType register(String name) {return null;}

	@Inject(at = @At("TAIL"), method = "<init>()V", remap = false)
	private void init(CallbackInfo info) {
		ExtendedSignTypes.CHESTNUT = register("chestnut");
	}
}
