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

/**
 * Mixin targeting {@link Items} to replace or extend vanilla item registration.
 * <p>
 * Specifically, this mixin intercepts the registration of certain items
 * and allows mod-specific versions to be injected instead. For example,
 * the vanilla "flint" item is replaced with {@link ExtendedFlintItem} during
 * registration.
 * </p>
 * @Mixin (Items.class)
 */
@SuppressWarnings("javadoc")
@Mixin(Items.class)
public class MixinItems {
	/**
	 * Shadow of the private {@link Items#registerItem(ResourceLocation, Item)} method.
	 * <p>
	 * Allows this mixin to call the original registration logic for a given
	 * resource location and item instance.
	 * </p>
	 *
	 * @param pKey  the resource location of the item
	 * @param pItem the item instance to register
	 * @return the registered item
	 * @Shadow
	 */
	@Shadow
	private static Item registerItem(ResourceLocation pKey, Item pItem) { return null;}
	/**
	 * Intercepts vanilla item registration to inject custom behavior.
	 * <p>
	 * Runs at the head of {@link Items#registerItem(String, Item)}, allowing
	 * this mixin to replace specific vanilla items with custom implementations.
	 * <p>
	 * Current overrides:
	 * <ul>
	 *     <li><b>"flint"</b> — replaced with {@link ExtendedFlintItem}.</li>
	 *     <!-- Add future overrides here as new <li> entries -->
	 * </ul>
	 * </p>
	 *
	 * @param name         the registry name of the item being registered
	 * @param itemInstance the item instance being registered
	 * @param clr          callback info used to override the return value
	 * @Inject (method = "registerItem(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("HEAD"), cancellable = true)
	 */
	@Inject(method = "registerItem(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("HEAD"), cancellable = true)
	private static void register$ExtendedItem(String name, Item itemInstance, CallbackInfoReturnable<Item> clr) {
		if("flint".equals(name)) {
			clr.setReturnValue(registerItem(new ResourceLocation(name), new ExtendedFlintItem((new Item.Properties()).tab(ItemGroup.TAB_MATERIALS))));
		}
	}
}
