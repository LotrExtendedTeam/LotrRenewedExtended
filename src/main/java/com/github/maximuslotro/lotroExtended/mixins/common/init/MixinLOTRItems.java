package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.CommandHornItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

@Mixin(LOTRItems.class)
public class MixinLOTRItems {
	
	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}
	
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedItems.COMMANDHORN = regItem("command_horn", () -> new CommandHornItem());
	}
}
