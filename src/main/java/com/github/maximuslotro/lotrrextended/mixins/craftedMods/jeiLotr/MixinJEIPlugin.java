package com.github.maximuslotro.lotrrextended.mixins.craftedMods.jeiLotr;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import craftedMods.jeiLotr.JEIPlugin;
import lotr.common.init.ExtendedRecipes;
import lotr.common.init.LOTRContainers;

/**
 * Adds support for Extended's crafting table recipes
 * @Mixin (JEIPlugin.class)
 * */
@Mixin(JEIPlugin.class)
public class MixinJEIPlugin {

	/**
	 * Adds support for Extended's crafting table recipes
	 * @Shadow (remap=false)
	 * @param recipeClass The class path of the to search for new table recipe types
	 * @param containerclass The class path of the crafting container being registered
	 * @param modid The id of the recipe handlers being registered
	 * */
	@Shadow(remap=false)
	private void scanFields (String recipeClass, String containerclass, String modid) {}

	/**
	 * Injects the recipe handlers into the
	 * @Inject (method = "initDevices()V", at = @At( value = "INVOKE", target = "LcraftedMods/jeiLotr/JEIPlugin;scanFields(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", ordinal = 0, shift = At.Shift.AFTER), remap=false)
	 * @param ci The CallbackInfo for canceling the event
	 * */
	@Inject(method = "initDevices()V", 
		at = @At( 
			value = "INVOKE", 
			target = "LcraftedMods/jeiLotr/JEIPlugin;scanFields(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", 
			ordinal = 0, 
			shift = At.Shift.AFTER), 
		remap=false
	)
	public void initDevicesExtended(CallbackInfo ci) {
		System.out.println(ExtendedRecipes.class.getName());
		scanFields(ExtendedRecipes.class.getName(), LOTRContainers.class.getName(), "lotr");
	}
}
