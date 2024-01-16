package com.github.maximuslotro.lotroExtended.mixins.common.config;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.config.LOTRConfig;
import lotr.common.config.LOTRConfig.ServerConfig;
import lotr.common.init.ExtendedServerConfig;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

@Mixin(ServerConfig.class)
public class MixinServerConfig {

	@Final
	@Shadow(remap=false)
	private List<LOTRConfig.ConfigValueHolder<?>> serverFields;

	@Inject(method = "<init>(Lnet/minecraftforge/common/ForgeConfigSpec$Builder;)V", at = @At("TAIL"), remap=false)
	private void injected(Builder builder, CallbackInfo ci) {
	  builder.push("ExtendedSettings");
	  ExtendedServerConfig.enableFellowshipCreation = createBoolValue(builder, "enableFellowshipCreation", true, false, "Enable or disable fellowship creation");
	  builder.pop();
	}
	private LOTRConfig.BoolHolder createBoolValue(Builder builder, String key, boolean def, boolean worldRestart, String comment) {
		String transKey = String.format("config.%s.%s", "lotr", key);
		builder.comment(comment).translation(transKey);
		if (worldRestart) {
			builder.worldRestart();
		}
		BooleanValue boolVal = builder.define("key", def);
		LOTRConfig.BoolHolder holder = new LOTRConfig.BoolHolder(boolVal);
		serverFields.add(holder);
		return holder;
	}
}
