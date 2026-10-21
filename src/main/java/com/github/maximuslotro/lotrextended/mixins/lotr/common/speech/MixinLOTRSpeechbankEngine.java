package com.github.maximuslotro.lotrextended.mixins.lotr.common.speech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import lotr.common.speech.LOTRSpeechbankEngine;
import lotr.common.speech.NPCSpeechbankContext;
import lotr.curuquesta.SpeechbankEngine;
import lotr.curuquesta.condition.BooleanSpeechbankCondition;
import lotr.curuquesta.replaceablevar.ReplaceableSpeechVariable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;

@Mixin(value = LOTRSpeechbankEngine.class)
public class MixinLOTRSpeechbankEngine {

	@ModifyReturnValue(method = "createEngine()Llotr/curuquesta/SpeechbankEngine;", at = @At("RETURN"), remap=false)
	private static SpeechbankEngine<NPCSpeechbankContext> modifyReturnValue(SpeechbankEngine<NPCSpeechbankContext> engine) {
		engine.registerCondition(new BooleanSpeechbankCondition<>("covered", context -> !context.getWorld().canSeeSky(context.getNPCPosition())));
		engine.registerReplaceableVariable(new ReplaceableSpeechVariable<>("s", "self", context -> context.getNPC().getName().getString()));
		engine.registerReplaceableVariable(new ReplaceableSpeechVariable<>("b", "biome", context -> generateBiomeString(context.getNPCBiome().getRegistryName())));
		return engine;
	}
	/* Injecting late, use Mixin Extras to inject at return value
	@Inject(method = "createEngine()Llotr/curuquesta/SpeechbankEngine;", at = @At("RETURN"), remap=false)
	private static void createEngineExtended(CallbackInfoReturnable<SpeechbankEngine<NPCSpeechbankContext>> cir) {
		SpeechbankEngine<NPCSpeechbankContext> engine = cir.getReturnValue();
		engine.registerReplaceableVariable(new ReplaceableSpeechVariable<>("s", "self", context -> context.getNPC().getName().getString()));
		engine.registerReplaceableVariable(new ReplaceableSpeechVariable<>("b", "biome", context -> generateBiomeString(context.getNPCBiome().getRegistryName())));
	}*/

	@Unique
	private static String generateBiomeString(ResourceLocation biome) {
		if(biome == null) {
			return "UNKNOWN BIOME";
		}
		LanguageMap languagemap = LanguageMap.getInstance();
		return languagemap.getOrDefault(String.format("biome.%s.%s", biome.getNamespace(), biome.getPath()));
	}
}