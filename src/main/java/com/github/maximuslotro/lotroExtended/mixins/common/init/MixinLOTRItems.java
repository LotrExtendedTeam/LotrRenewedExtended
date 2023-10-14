package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItems;
import lotr.common.item.CommandHornItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

@Mixin(LOTRItems.class)
public class MixinLOTRItems {

	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}

	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedItems.COMMANDHORN = regItem("command_horn", () -> new CommandHornItem());
		
		ExtendedItems.ITHILDIN = regItem("ithildin", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		ExtendedItems.GALVORN_INGOT = regItem("galvorn_ingot", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		ExtendedItems.BLACK_URUK_STEEL_INGOT = regItem("black_uruk_steel_ingot", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		ExtendedItems.GILDED_IRON_INGOT = regItem("gilded_iron_ingot", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		ExtendedItems.MORGUL_STEEL_INGOT = regItem("morgul_steel_ingot", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		ExtendedItems.ITHILDIN = regItem("obsidian_shard", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		ExtendedItems.HITHLAIN = regItem("hithlain", () -> new Item((new Item.Properties()).tab(LOTRItemGroups.MATERIALS)));
		
		ExtendedItems.GALVORN_BLOCK = regBlockItem(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_URUK_STEEL_BLOCK = regBlockItem(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GILDED_IRON_BLOCK = regBlockItem(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MORGUL_STEEL_BLOCK = regBlockItem(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
	}
}
