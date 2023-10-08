package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

@Mixin(LOTRItems.class)
public class MixinLOTRItems {
	

	//@Shadow
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}
	
	public static final RegistryObject<Item> COMMANDHORN = regItem("command_horn", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.COMBAT)));
}
