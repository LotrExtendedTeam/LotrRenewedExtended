package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import lotr.common.item.*;
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

		ExtendedItems.GALVORN_INGOT = regMaterialItem("galvorn_ingot");
		ExtendedItems.GALVORN_BLOCK = regBlockItem(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);

		ExtendedItems.BLACK_URUK_STEEL_INGOT = regMaterialItem("black_uruk_steel_ingot");
		ExtendedItems.BLACK_URUK_STEEL_BLOCK = regBlockItem(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);

		ExtendedItems.GILDED_IRON_INGOT = regMaterialItem("gilded_iron_ingot");
		ExtendedItems.GILDED_IRON_BLOCK = regBlockItem(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);

		ExtendedItems.MORGUL_STEEL_INGOT = regMaterialItem("morgul_steel_ingot");
		ExtendedItems.MORGUL_STEEL_BLOCK = regBlockItem(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);

		ExtendedItems.OBSIDIAN_SHARD = regMaterialItem("obsidian_shard");
		ExtendedItems.HITHLAIN = regMaterialItem("hithlain");
		ExtendedItems.ITHILDIN = regMaterialItem("ithildin");

		ExtendedItems.TOPAZ = regMaterialItem("topaz");
		ExtendedItems.AMETHYST = regMaterialItem("amethyst");
		ExtendedItems.SAPPHIRE = regMaterialItem("sapphire");
		ExtendedItems.RUBY = regMaterialItem("ruby");
		ExtendedItems.AMBER = regMaterialItem("amber");
		ExtendedItems.DIAMOND = regMaterialItem("diamond");
		ExtendedItems.OPAL = regMaterialItem("opal");
		ExtendedItems.EMERALD = regMaterialItem("emerald");

		ExtendedItems.TOPAZ_ORE = regBlockItem(ExtendedBlocks.TOPAZ_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_ORE = regBlockItem(ExtendedBlocks.AMETHYST_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_ORE = regBlockItem(ExtendedBlocks.SAPPHIRE_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_ORE = regBlockItem(ExtendedBlocks.RUBY_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_ORE = regBlockItem(ExtendedBlocks.AMBER_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_ORE = regBlockItem(ExtendedBlocks.DIAMOND_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_ORE = regBlockItem(ExtendedBlocks.OPAL_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_ORE = regBlockItem(ExtendedBlocks.EMERALD_ORE, LOTRItemGroups.BLOCKS);

		ExtendedItems.TOPAZ_BLOCK = regBlockItem(ExtendedBlocks.TOPAZ_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_BLOCK = regBlockItem(ExtendedBlocks.AMETHYST_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_BLOCK = regBlockItem(ExtendedBlocks.SAPPHIRE_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_BLOCK = regBlockItem(ExtendedBlocks.RUBY_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_BLOCK = regBlockItem(ExtendedBlocks.AMBER_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_BLOCK = regBlockItem(ExtendedBlocks.DIAMOND_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_BLOCK = regBlockItem(ExtendedBlocks.OPAL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_BLOCK = regBlockItem(ExtendedBlocks.EMERALD_BLOCK, LOTRItemGroups.BLOCKS);
		
		ExtendedItems.MORGUL_HELMET = regItem("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.MORGUL_CHESTPLATE = regItem("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
		ExtendedItems.MORGUL_LEGGINGS = regItem("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
		ExtendedItems.MORGUL_BOOTS = regItem("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
		
		ExtendedItems.MORGUL_SWORD = regItem("morgul_blade",() -> new LOTRSwordItem(LOTRMaterial.valueOf("MORGUL")));

		ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET = regItem("taurethrim_chieftain_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD,"chieftain_helmet"));
		ExtendedItems.TAURETHRIM_HELMET = regItem("taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD));
		ExtendedItems.TAURETHRIM_CHESTPLATE = regItem("taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.TAURETHRIM_LEGGINGS = regItem("taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.TAURETHRIM_BOOTS = regItem("taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.FEET));
		
		ExtendedItems.GOLDEN_TAURETHRIM_HELMET = regItem("golden_taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE = regItem("golden_taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS = regItem("golden_taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.GOLDEN_TAURETHRIM_BOOTS = regItem("golden_taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.FEET));
		
		ExtendedItems.TAURETHRIM_HOE = regItem("taurethrim_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_AXE = regItem("taurethrim_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_SHOVEL = regItem("taurethrim_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_PICKAXE = regItem("taurethrim_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_SWORD = regItem("taurethrim_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("TAURETHRIM")));
	}
}
