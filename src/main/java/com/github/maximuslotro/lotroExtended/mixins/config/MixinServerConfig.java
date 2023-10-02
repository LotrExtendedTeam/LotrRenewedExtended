package com.github.maximuslotro.lotroExtended.mixins.config;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.config.LOTRConfig;
import lotr.common.config.LOTRConfig.ServerConfig;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

@Mixin(ServerConfig.class)
public class MixinServerConfig {

	@Shadow(remap=false)
	private List<LOTRConfig.ConfigValueHolder<?>> serverFields;

	public LOTRConfig.BoolHolder enableFellowshipCreation;

	@Inject(method = "<init>(Lnet/minecraftforge/common/ForgeConfigSpec$Builder;)V", at = @At("TAIL"), remap=false)
	private void injected(Builder builder, CallbackInfo ci) {
	  builder.push("ExtendedSettings");
	  builder.comment("Enable or disable fellowship creation").translation(String.format("config.%s.%s", "lotr", enableFellowshipCreation));
	  BooleanValue boolVal = builder.define("enableFellowshipCreation", true);
	  LOTRConfig.BoolHolder holder = new LOTRConfig.BoolHolder(boolVal);
	  serverFields.add(holder);
	  enableFellowshipCreation = holder;
	  builder.pop();
	}
}
