package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.item.*;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;

/**
 * Used to replace items registered by Renewed
 * @Mixin (LOTRItems.class)
 * */
@Mixin(LOTRItems.class)
public class MixinLOTRItems {
	/**
	 * Get Renewed's very base method for registering items from the parent class
	 * @Shadow (remap=false)
	 * @param name A String name of the entity
	 * @param sup A supplier for the item
	 * @return A RegistryObject instance for the item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}

	/**
	 * @author maximuslotro
	 * @reason Handles overwriting renewed items with needed new classes
	 * @Overwrite (remap = false)
	 * @param name A String name for the item
	 * @return A RegistryObject instance for the material item
	 * */
	@Overwrite(remap = false)
	private static RegistryObject<Item> regMaterialItem(String name) {
		if(name.equals("gulduril")) {
			return regItem(name, () -> new ExtendedGuldurilItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		if(name.equals("edhelvir")) {
			return regItem(name, () -> new ExtendedEdhelvirItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		if(name.equals("copper_ingot")) {
			return regItem(name, () -> new ExtendedCopperIngotItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		return regItem(name, () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	}
}
