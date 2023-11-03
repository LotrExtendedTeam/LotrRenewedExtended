package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import lotr.common.init.*;
import lotr.common.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.item.Item.Properties;

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
		//###########################################################BLOCKS################################################################
		//ORE/GEM STORAGE
		ExtendedItems.GALVORN_BLOCK = regBlockItem(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_URUK_STEEL_BLOCK = regBlockItem(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GILDED_IRON_BLOCK = regBlockItem(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MORGUL_STEEL_BLOCK = regBlockItem(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.TOPAZ_BLOCK = regBlockItem(ExtendedBlocks.TOPAZ_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_BLOCK = regBlockItem(ExtendedBlocks.AMETHYST_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_BLOCK = regBlockItem(ExtendedBlocks.SAPPHIRE_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_BLOCK = regBlockItem(ExtendedBlocks.RUBY_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_BLOCK = regBlockItem(ExtendedBlocks.AMBER_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_BLOCK = regBlockItem(ExtendedBlocks.DIAMOND_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_BLOCK = regBlockItem(ExtendedBlocks.OPAL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_BLOCK = regBlockItem(ExtendedBlocks.EMERALD_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.PEARL_BLOCK = regBlockItem(ExtendedBlocks.PEARL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CORAL_BLOCK = regBlockItem(ExtendedBlocks.CORAL_BLOCK, LOTRItemGroups.BLOCKS);

		//ORE
		ExtendedItems.TOPAZ_ORE = regBlockItem(ExtendedBlocks.TOPAZ_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_ORE = regBlockItem(ExtendedBlocks.AMETHYST_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_ORE = regBlockItem(ExtendedBlocks.SAPPHIRE_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_ORE = regBlockItem(ExtendedBlocks.RUBY_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_ORE = regBlockItem(ExtendedBlocks.AMBER_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_ORE = regBlockItem(ExtendedBlocks.DIAMOND_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_ORE = regBlockItem(ExtendedBlocks.OPAL_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_ORE = regBlockItem(ExtendedBlocks.EMERALD_ORE, LOTRItemGroups.BLOCKS);

		//############################################################DECORATION##############################################################
		//Bushes
		ExtendedItems.RASPBERRY_BUSH = regBlockItem(ExtendedBlocks.RASPBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.BLUEBERRY_BUSH = regBlockItem(ExtendedBlocks.BLUEBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.BLACKBERRY_BUSH = regBlockItem(ExtendedBlocks.BLACKBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.CRANBERRY_BUSH = regBlockItem(ExtendedBlocks.CRANBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.ELDERBERRY_BUSH = regBlockItem(ExtendedBlocks.ELDERBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.WILDBERRY_BUSH = regBlockItem(ExtendedBlocks.WILDBERRY_BUSH, LOTRItemGroups.DECO);
		
		ExtendedItems.WHITE_PACKED_FUR = regBlockItem(ExtendedBlocks.WHITE_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR = regBlockItem(ExtendedBlocks.SILVER_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.GREY_PACKED_FUR = regBlockItem(ExtendedBlocks.GREY_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR = regBlockItem(ExtendedBlocks.BROWN_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_SLAB = regBlockItem(ExtendedBlocks.BROWN_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BROWN_PACKED_FUR_STAIRS = regBlockItem(ExtendedBlocks.BROWN_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BROWN_PACKED_FUR_WALL = regBlockItem(ExtendedBlocks.BROWN_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR = regBlockItem(ExtendedBlocks.BLACK_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_SLAB = regBlockItem(ExtendedBlocks.BLACK_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR_STAIRS = regBlockItem(ExtendedBlocks.BLACK_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR_WALL = regBlockItem(ExtendedBlocks.BLACK_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);
		
		ExtendedItems.WHITE_FUR_BUNDLE = regBlockItem(ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_FUR_BUNDLE = regBlockItem(ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.GREY_FUR_BUNDLE = regBlockItem(ExtendedBlocks.GREY_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_FUR_BUNDLE = regBlockItem(ExtendedBlocks.BLACK_FUR_BUNDLE, LOTRItemGroups.DECO);
		
		//########### BLOCK TYPES ##################
		//Taurethrim Bricks
		ExtendedItems.TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_PILLAR = regBlockItem(ExtendedBlocks.TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_PILLAR_SLAB = regBlockItem(ExtendedBlocks.TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_PILLAR = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_PILLAR_SLAB = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR_SLAB = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		
		//Dol Guldur Bricks
		ExtendedItems.DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_SLAB = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_WALL = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_PILLAR = regBlockItem(ExtendedBlocks.DOL_GULDUR_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_PILLAR_SLAB = regBlockItem(ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.CARVED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);

		//Rhunic Bricks
		ExtendedItems.RHUNIC_BRICK = regBlockItem(ExtendedBlocks.RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_PILLAR = regBlockItem(ExtendedBlocks.RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_PILLAR_SLAB = regBlockItem(ExtendedBlocks.RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.CARVED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);

		//###################################################################MATERIALS########################################################
		//Misc
		ExtendedItems.OBSIDIAN_SHARD = regMaterialItem("obsidian_shard");
		ExtendedItems.HITHLAIN = regMaterialItem("hithlain");
		ExtendedItems.ITHILDIN = regMaterialItem("ithildin");

		ExtendedItems.WHITE_FUR = regMaterialItem("white_fur");
		ExtendedItems.SILVER_FUR = regMaterialItem("silver_fur");
		ExtendedItems.GREY_FUR = regMaterialItem("grey_fur");
		ExtendedItems.BLACK_FUR = regMaterialItem("black_fur");

		//Ingots
		ExtendedItems.GALVORN_INGOT = regMaterialItem("galvorn_ingot");
		ExtendedItems.BLACK_URUK_STEEL_INGOT = regMaterialItem("black_uruk_steel_ingot");
		ExtendedItems.GILDED_IRON_INGOT = regMaterialItem("gilded_iron_ingot");
		ExtendedItems.MORGUL_STEEL_INGOT = regMaterialItem("morgul_steel_ingot");

		//Gems
		ExtendedItems.TOPAZ = regMaterialItem("topaz");
		ExtendedItems.AMETHYST = regMaterialItem("amethyst");
		ExtendedItems.SAPPHIRE = regMaterialItem("sapphire");
		ExtendedItems.RUBY = regMaterialItem("ruby");
		ExtendedItems.AMBER = regMaterialItem("amber");
		ExtendedItems.DIAMOND = regMaterialItem("diamond");
		ExtendedItems.OPAL = regMaterialItem("opal");
		ExtendedItems.EMERALD = regMaterialItem("emerald");
		ExtendedItems.PEARL = regMaterialItem("pearl");
		ExtendedItems.CORAL = regMaterialItem("coral");

		//#########################################################################TOOLS#####################################################
		//Taurethrim Tools
		ExtendedItems.TAURETHRIM_SHOVEL = regItem("taurethrim_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_PICKAXE = regItem("taurethrim_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_AXE = regItem("taurethrim_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_HOE = regItem("taurethrim_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("TAURETHRIM"))); 
		
		//Angmar Tools
		ExtendedItems.ANGMAR_SHOVEL = regItem("angmar_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_PICKAXE = regItem("angmar_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_AXE = regItem("angmar_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_HOE = regItem("angmar_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("ANGMAR")));

		//Dol Guldur Tools
		ExtendedItems.DOL_GULDUR_SHOVEL = regItem("dol_guldur_shovel",() -> new LOTRShovelItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_PICKAXE = regItem("dol_guldur_pickaxe",() -> new LOTRPickaxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_AXE = regItem("dol_guldur_axe",() -> new LOTRAxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_HOE = regItem("dol_guldur_hoe",() -> new LOTRHoeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		//######################################################################FOOD########################################################
		//Berries
		ExtendedItems.RASPBERRY = regItem("raspberry", () -> new BerryItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.RASPBERRY)));
		ExtendedItems.BLACKBERRY = regItem("blackberry", () -> new BerryItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLACKBERRY)));
		ExtendedItems.BLUEBERRY = regItem("blueberry", () -> new BerryItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLUEBERRY)));
		ExtendedItems.CRANBERRY = regItem("cranberry", () -> new BerryItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CRANBERRY)));
		ExtendedItems.ELDERBERRY = regItem("elderberry", () -> new BerryItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ELDERBERRY)));
		ExtendedItems.WILDBERRY = regItem("wildberry", ()-> new BerryItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.WILDBERRY)));
		
		//#######################################################################EQUIPMENT##################################################
		ExtendedItems.COMMANDHORN = regItem("horn_of_command", () -> new CommandHornItem());

		//Morgul Equipment
		ExtendedItems.MORGUL_HELMET = regItem("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.MORGUL_CHESTPLATE = regItem("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
		ExtendedItems.MORGUL_LEGGINGS = regItem("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
		ExtendedItems.MORGUL_BOOTS = regItem("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
		ExtendedItems.MORGUL_SWORD = regItem("morgul_blade",() -> new LOTRSwordItem(LOTRMaterial.valueOf("MORGUL")));

		//Taurethrim Equipment
		ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET = regItem("taurethrim_chieftain_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD,"chieftain_helmet"));
		ExtendedItems.TAURETHRIM_HELMET = regItem("taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD));
		ExtendedItems.TAURETHRIM_CHESTPLATE = regItem("taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.TAURETHRIM_LEGGINGS = regItem("taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.TAURETHRIM_BOOTS = regItem("taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.FEET));
		ExtendedItems.TAURETHRIM_SWORD = regItem("taurethrim_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("TAURETHRIM")));

		ExtendedItems.GOLDEN_TAURETHRIM_HELMET = regItem("golden_taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE = regItem("golden_taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS = regItem("golden_taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.GOLDEN_TAURETHRIM_BOOTS = regItem("golden_taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.FEET));

		//Angmar Equipment
		ExtendedItems.ANGMAR_HELMET = regItem("angmar_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.HEAD));
		ExtendedItems.ANGMAR_CHESTPLATE = regItem("angmar_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.CHEST));
		ExtendedItems.ANGMAR_LEGGINGS = regItem("angmar_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.LEGS));
		ExtendedItems.ANGMAR_BOOTS = regItem("angmar_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.FEET));
		ExtendedItems.ANGMAR_SWORD = regItem("angmar_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("ANGMAR")));

		//Dol Guldur Equipment
		ExtendedItems.DOL_GULDUR_HELMET = regItem("dol_guldur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.HEAD));
		ExtendedItems.DOL_GULDUR_CHESTPLATE = regItem("dol_guldur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.CHEST));
		ExtendedItems.DOL_GULDUR_LEGGINGS = regItem("dol_guldur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.LEGS));
		ExtendedItems.DOL_GULDUR_BOOTS = regItem("dol_guldur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.FEET));
		ExtendedItems.DOL_GULDUR_SWORD = regItem("dol_guldur_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("DOL_GULDUR")));

		//########################################################################UTIL##########################################################
		//Crafting Tables
		ExtendedItems.DOL_GULDUR_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.TAURETHRIM_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.RHUNIC_CRAFTING_TABLE, LOTRItemGroups.UTIL);

		//########################################################################MISC##########################################################

		ExtendedItems.HOBBIT_MARRIAGE_RING =  regItem("hobbit_marriage_ring", () -> { return new Item((new Properties()).tab(LOTRItemGroups.MISC));});
		ExtendedItems.DWARF_MARRIAGE_RING =  regItem("dwarven_marriage_ring", () -> { return new Item((new Properties()).tab(LOTRItemGroups.MISC));});
		ExtendedItems.BOTTLE_OF_POISON = regItem("bottle_of_poison", () -> { return new Item((new Properties()).tab(LOTRItemGroups.MISC));});
	}
}
