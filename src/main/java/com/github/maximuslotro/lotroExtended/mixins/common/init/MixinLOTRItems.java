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
import lotr.common.init.LOTRMaterial;
import lotr.common.item.CommandHornItem;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRSwordItem;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

@Mixin(LOTRItems.class)
public class MixinLOTRItems {

	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}

	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}

	@Shadow(remap=false)
	private static RegistryObject<Item> regMaterialItem(String name) {return null;}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedItems.COMMANDHORN = regItem("horn_of_command", () -> new CommandHornItem());
		
		ExtendedItems.ITHILDIN = regMaterialItem("ithildin");
		ExtendedItems.GALVORN_INGOT = regMaterialItem("galvorn_ingot");
		ExtendedItems.BLACK_URUK_STEEL_INGOT = regMaterialItem("black_uruk_steel_ingot");
		ExtendedItems.GILDED_IRON_INGOT = regMaterialItem("gilded_iron_ingot");
		ExtendedItems.MORGUL_STEEL_INGOT = regMaterialItem("morgul_steel_ingot");
		ExtendedItems.ITHILDIN = regMaterialItem("obsidian_shard");
		ExtendedItems.HITHLAIN = regMaterialItem("hithlain");
		
		ExtendedItems.GALVORN_BLOCK = regBlockItem(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_URUK_STEEL_BLOCK = regBlockItem(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GILDED_IRON_BLOCK = regBlockItem(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MORGUL_STEEL_BLOCK = regBlockItem(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		
		ExtendedItems.MORGUL_SWORD = regItem("morgul_blade",() -> new LOTRSwordItem(LOTRMaterial.valueOf("MORGUL")));
		ExtendedItems.MORGUL_HELMET = regItem("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD));
		ExtendedItems.MORGUL_CHESTPLATE = regItem("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
		ExtendedItems.MORGUL_LEGGINGS = regItem("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
		ExtendedItems.MORGUL_BOOTS = regItem("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
	}
}
