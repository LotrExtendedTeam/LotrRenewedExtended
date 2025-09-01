package com.github.maximuslotro.lotrextended.mixins.net.minecraft.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedFlintItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

@Mixin(Items.class)
public class MixinItems {

	@Shadow
	private static Item registerItem(ResourceLocation pKey, Item pItem) { return null;}

	@Inject(method = "registerItem(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("HEAD"), cancellable = true)
	private static void register$ExtendedItem(String name, Item itemInstance, CallbackInfoReturnable<Item> clr) {
		if("flint".equals(name)) {
			clr.setReturnValue(registerItem(new ResourceLocation(name), new ExtendedFlintItem((new Item.Properties()).tab(ItemGroup.TAB_MATERIALS))));
		}
	}
}
