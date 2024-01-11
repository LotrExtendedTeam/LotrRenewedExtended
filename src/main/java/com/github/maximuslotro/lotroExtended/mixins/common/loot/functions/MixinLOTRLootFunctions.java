package com.github.maximuslotro.lotroExtended.mixins.common.loot.functions;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.loot.functions.LOTRLootFunctions;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;

@Mixin(LOTRLootFunctions.class)
public class MixinLOTRLootFunctions {

	@Shadow(remap = false)
	private static LootFunctionType registerFunction(String name, ILootSerializer<? extends ILootFunction> serializer) {return null;}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedLootFunctions(CallbackInfo ci) {
		//ExtendedLootFunctions.WARG_FUR_TYPE = registerFunction("warg_fur_type", new ExtendedSetWargFurType.Serializer());
	}
}
