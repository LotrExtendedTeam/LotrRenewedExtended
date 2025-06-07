package com.github.maximuslotro.lotrrextended.mixins.craftedMods.jeiLotr;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;

import craftedMods.jeiLotr.JEILotr;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 * Add option to disable the broken version check present in the latest version of JEILotr
 * @Mixin (JEILotr.class)
 * */
@Mixin(JEILotr.class)
public class MixinJEILotr {

	/**
	 * Add setting check to disable the broken version check
	 * @Inject (method = "login(Lnet/minecraftforge/event/entity/EntityJoinWorldEvent;)V", at = @At(value = "INVOKE"), remap=false, cancellable=true)
	 * @param event The EntityJoinWorldEvent event handler this is injecting into
	 * @param ci The CallbackInfo for canceling the event
	 * */
	@Inject(method = "login(Lnet/minecraftforge/event/entity/EntityJoinWorldEvent;)V", at = @At(value = "INVOKE"), remap=false, cancellable=true)
	public void login(EntityJoinWorldEvent event, CallbackInfo ci){
		if(ExtendedClientConfig.disableJEILOTRversionCheck.get()) {
			ci.cancel();
		}
	}
}
