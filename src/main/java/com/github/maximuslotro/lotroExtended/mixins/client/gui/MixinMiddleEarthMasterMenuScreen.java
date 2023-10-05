package com.github.maximuslotro.lotroExtended.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.client.gui.MiddleEarthMasterMenuScreen;

import org.spongepowered.asm.mixin.injection.At;

import lotr.client.gui.widget.button.MiddleEarthMenuButton;
import net.minecraft.util.text.StringTextComponent;

@Mixin(MiddleEarthMasterMenuScreen.class)
public class MixinMiddleEarthMasterMenuScreen{

	@Inject(method = "init()V", 
			at = @At(value = "INVOKE", 
			target = "Llotr/client/gui/MiddleEarthMasterMenuScreen;resetLastMenuScreen()V", 
			remap=false,
			shift = At.Shift.AFTER))
	public void addAchevmentButton(CallbackInfo ci) {
		//((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Achievements"), 2, 65));
		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Fellowships"), 6, 71));
		//((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Titles"), 7, 84));
		//((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Shields"), 5, 83));
		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Options"), 1, 79));
	}
//	@Inject(method = "init()V", 
//			at = @At(value = "NEW", 
//			target = "Ljava/util/ArrayList;",
//			shift = At.Shift.AFTER))
//	public void addFellowshipButton(CallbackInfo ci) {
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Fellowships"), 6, 71));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Titles"), 7, 84));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Shields"), 5, 83));
//		((MiddleEarthMasterMenuScreen)(Object)this).addButton(new MiddleEarthMenuButton(0, 0, null, new StringTextComponent("Options"), 1, 79));
//	}
}
