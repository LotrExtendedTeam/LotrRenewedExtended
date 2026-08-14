package com.github.maximuslotro.lotrrextended.common.init;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemRenderHelper;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedBoatEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCoinType;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCrossbowMaterial;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBowMaterial;
import com.github.maximuslotro.lotrrextended.common.item.*;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMountedArmorItem.MountType;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedDwarvenBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedHighElvenBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;

import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRMaterial;
import lotr.common.item.EmptyVesselBlockItem;
import lotr.common.item.FallenLeavesItem;
import lotr.common.item.HammerItem;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRShieldItem;
import lotr.common.item.SpearItem;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SignItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LotrExtendedMod.MODID);

	//###########################################################BLOCKS################################################################
	public static RegistryObject<Item> CHESTNUT_LOG = regBlockItem(ExtendedBlocks.CHESTNUT_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_WOOD = regBlockItem(ExtendedBlocks.CHESTNUT_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_PLANKS = regBlockItem(ExtendedBlocks.CHESTNUT_PLANKS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_LEAVES = regBlockItem(ExtendedBlocks.CHESTNUT_LEAVES, LOTRItemGroups.DECO);
	public static RegistryObject<Item> CHESTNUT_LEAVES_FRUIT = regBlockItem(ExtendedBlocks.CHESTNUT_LEAVES_FRUIT, LOTRItemGroups.DECO);
	public static RegistryObject<Item> CHESTNUT_SAPLING = regBlockItem(ExtendedBlocks.CHESTNUT_SAPLING, LOTRItemGroups.DECO);
	public static RegistryObject<Item> CHESTNUT_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_STAIRS = regBlockItem(ExtendedBlocks.CHESTNUT_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_FENCE = regBlockItem(ExtendedBlocks.CHESTNUT_FENCE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> CHESTNUT_FENCE_GATE = regBlockItem(ExtendedBlocks.CHESTNUT_FENCE_GATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> CHESTNUT_DOOR = regTallBlockItem(ExtendedBlocks.CHESTNUT_DOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> CHESTNUT_TRAPDOOR = regBlockItem(ExtendedBlocks.CHESTNUT_TRAPDOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> CHESTNUT_PRESSURE_PLATE = regBlockItem(ExtendedBlocks.CHESTNUT_PRESSURE_PLATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> CHESTNUT_BUTTON = regBlockItem(ExtendedBlocks.CHESTNUT_BUTTON, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> STRIPPED_CHESTNUT_LOG = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_CHESTNUT_WOOD = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_BEAM = regBlockItem(ExtendedBlocks.CHESTNUT_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_BEAM_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_BEAM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_SIGN = regSignItem(ExtendedBlocks.CHESTNUT_SIGN, ExtendedBlocks.CHESTNUT_WALL_SIGN);
	public static RegistryObject<Item> CHESTNUT_BOAT = regBoatItem("chestnut_boat", ExtendedBoatEntity.Type.CHESTNUT, LOTRItemGroups.MISC);
	public static RegistryObject<Item> CHESTNUT_LOG_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_WOOD_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_WOOD_STAIRS = regBlockItem(ExtendedBlocks.CHESTNUT_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHESTNUT_BRANCH = regBlockItem(ExtendedBlocks.CHESTNUT_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_CHESTNUT_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_CHESTNUT_WOOD_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_CHESTNUT_WOOD_STAIRS = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_CHESTNUT_BRANCH = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FALLEN_CHESTNUT_LEAVES = regFallenLeavesItem(ExtendedBlocks.FALLEN_CHESTNUT_LEAVES, LOTRItemGroups.DECO);

	public static RegistryObject<Item> BANANA_LOG = regBlockItem(ExtendedBlocks.BANANA_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_WOOD = regBlockItem(ExtendedBlocks.BANANA_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_PLANKS = regBlockItem(ExtendedBlocks.BANANA_PLANKS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_LEAVES = regBlockItem(ExtendedBlocks.BANANA_LEAVES, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BANANA_SAPLING = regBlockItem(ExtendedBlocks.BANANA_SAPLING, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BANANA_SLAB = regBlockItem(ExtendedBlocks.BANANA_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_STAIRS = regBlockItem(ExtendedBlocks.BANANA_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_FENCE = regBlockItem(ExtendedBlocks.BANANA_FENCE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BANANA_FENCE_GATE = regBlockItem(ExtendedBlocks.BANANA_FENCE_GATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> BANANA_DOOR = regTallBlockItem(ExtendedBlocks.BANANA_DOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> BANANA_TRAPDOOR = regBlockItem(ExtendedBlocks.BANANA_TRAPDOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> BANANA_PRESSURE_PLATE = regBlockItem(ExtendedBlocks.BANANA_PRESSURE_PLATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> BANANA_BUTTON = regBlockItem(ExtendedBlocks.BANANA_BUTTON, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> STRIPPED_BANANA_LOG = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_BANANA_WOOD = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_BEAM = regBlockItem(ExtendedBlocks.BANANA_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_BEAM_SLAB = regBlockItem(ExtendedBlocks.BANANA_BEAM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_SIGN = regSignItem(ExtendedBlocks.BANANA_SIGN, ExtendedBlocks.BANANA_WALL_SIGN);
	public static RegistryObject<Item> BANANA_BOAT = regBoatItem("banana_boat", ExtendedBoatEntity.Type.BANANA, LOTRItemGroups.MISC);
	public static RegistryObject<Item> BANANA_LOG_SLAB = regBlockItem(ExtendedBlocks.BANANA_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_WOOD_SLAB = regBlockItem(ExtendedBlocks.BANANA_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_WOOD_STAIRS = regBlockItem(ExtendedBlocks.BANANA_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BANANA_BRANCH = regBlockItem(ExtendedBlocks.BANANA_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_BANANA_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_BANANA_WOOD_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_BANANA_WOOD_STAIRS = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_BANANA_BRANCH = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FALLEN_BANANA_LEAVES = regFallenLeavesItem(ExtendedBlocks.FALLEN_BANANA_LEAVES, LOTRItemGroups.DECO);

	public static RegistryObject<Item> PLUM_LOG = regBlockItem(ExtendedBlocks.PLUM_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_WOOD = regBlockItem(ExtendedBlocks.PLUM_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_PLANKS = regBlockItem(ExtendedBlocks.PLUM_PLANKS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_LEAVES = regBlockItem(ExtendedBlocks.PLUM_LEAVES, LOTRItemGroups.DECO);
	public static RegistryObject<Item> PLUM_LEAVES_FRUIT = regBlockItem(ExtendedBlocks.PLUM_LEAVES_FRUIT, LOTRItemGroups.DECO);
	public static RegistryObject<Item> PLUM_SAPLING = regBlockItem(ExtendedBlocks.PLUM_SAPLING, LOTRItemGroups.DECO);
	public static RegistryObject<Item> PLUM_SLAB = regBlockItem(ExtendedBlocks.PLUM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_STAIRS = regBlockItem(ExtendedBlocks.PLUM_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_FENCE = regBlockItem(ExtendedBlocks.PLUM_FENCE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> PLUM_FENCE_GATE = regBlockItem(ExtendedBlocks.PLUM_FENCE_GATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> PLUM_DOOR = regTallBlockItem(ExtendedBlocks.PLUM_DOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> PLUM_TRAPDOOR = regBlockItem(ExtendedBlocks.PLUM_TRAPDOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> PLUM_PRESSURE_PLATE = regBlockItem(ExtendedBlocks.PLUM_PRESSURE_PLATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> PLUM_BUTTON = regBlockItem(ExtendedBlocks.PLUM_BUTTON, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> STRIPPED_PLUM_LOG = regBlockItem(ExtendedBlocks.STRIPPED_PLUM_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_PLUM_WOOD = regBlockItem(ExtendedBlocks.STRIPPED_PLUM_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_BEAM = regBlockItem(ExtendedBlocks.PLUM_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_BEAM_SLAB = regBlockItem(ExtendedBlocks.PLUM_BEAM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_SIGN = regSignItem(ExtendedBlocks.PLUM_SIGN, ExtendedBlocks.PLUM_WALL_SIGN);
	public static RegistryObject<Item> PLUM_BOAT = regBoatItem("plum_boat", ExtendedBoatEntity.Type.PLUM, LOTRItemGroups.MISC);
	public static RegistryObject<Item> PLUM_LOG_SLAB = regBlockItem(ExtendedBlocks.PLUM_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_WOOD_SLAB = regBlockItem(ExtendedBlocks.PLUM_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_WOOD_STAIRS = regBlockItem(ExtendedBlocks.PLUM_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PLUM_BRANCH = regBlockItem(ExtendedBlocks.PLUM_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_PLUM_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_PLUM_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_PLUM_WOOD_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_PLUM_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_PLUM_WOOD_STAIRS = regBlockItem(ExtendedBlocks.STRIPPED_PLUM_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_PLUM_BRANCH = regBlockItem(ExtendedBlocks.STRIPPED_PLUM_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FALLEN_PLUM_LEAVES = regFallenLeavesItem(ExtendedBlocks.FALLEN_PLUM_LEAVES, LOTRItemGroups.DECO);

	public static RegistryObject<Item> WILLOW_LOG = regBlockItem(ExtendedBlocks.WILLOW_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_WOOD = regBlockItem(ExtendedBlocks.WILLOW_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_PLANKS = regBlockItem(ExtendedBlocks.WILLOW_PLANKS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_LEAVES = regBlockItem(ExtendedBlocks.WILLOW_LEAVES, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WILLOW_SAPLING = regBlockItem(ExtendedBlocks.WILLOW_SAPLING, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WILLOW_VINES = regBlockItem(ExtendedBlocks.WILLOW_VINES, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WILLOW_SLAB = regBlockItem(ExtendedBlocks.WILLOW_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_STAIRS = regBlockItem(ExtendedBlocks.WILLOW_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_FENCE = regBlockItem(ExtendedBlocks.WILLOW_FENCE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WILLOW_FENCE_GATE = regBlockItem(ExtendedBlocks.WILLOW_FENCE_GATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> WILLOW_DOOR = regTallBlockItem(ExtendedBlocks.WILLOW_DOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> WILLOW_TRAPDOOR = regBlockItem(ExtendedBlocks.WILLOW_TRAPDOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> WILLOW_PRESSURE_PLATE = regBlockItem(ExtendedBlocks.WILLOW_PRESSURE_PLATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> WILLOW_BUTTON = regBlockItem(ExtendedBlocks.WILLOW_BUTTON, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> STRIPPED_WILLOW_LOG = regBlockItem(ExtendedBlocks.STRIPPED_WILLOW_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_WILLOW_WOOD = regBlockItem(ExtendedBlocks.STRIPPED_WILLOW_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_BEAM = regBlockItem(ExtendedBlocks.WILLOW_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_BEAM_SLAB = regBlockItem(ExtendedBlocks.WILLOW_BEAM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_SIGN = regSignItem(ExtendedBlocks.WILLOW_SIGN, ExtendedBlocks.WILLOW_WALL_SIGN);
	public static RegistryObject<Item> WILLOW_BOAT = regBoatItem("willow_boat", ExtendedBoatEntity.Type.WILLOW, LOTRItemGroups.MISC);
	public static RegistryObject<Item> WILLOW_LOG_SLAB = regBlockItem(ExtendedBlocks.WILLOW_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_WOOD_SLAB = regBlockItem(ExtendedBlocks.WILLOW_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_WOOD_STAIRS = regBlockItem(ExtendedBlocks.WILLOW_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WILLOW_BRANCH = regBlockItem(ExtendedBlocks.WILLOW_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_WILLOW_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_WILLOW_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_WILLOW_WOOD_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_WILLOW_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_WILLOW_WOOD_STAIRS = regBlockItem(ExtendedBlocks.STRIPPED_WILLOW_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_WILLOW_BRANCH = regBlockItem(ExtendedBlocks.STRIPPED_WILLOW_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FALLEN_WILLOW_LEAVES = regFallenLeavesItem(ExtendedBlocks.FALLEN_WILLOW_LEAVES, LOTRItemGroups.DECO);

	public static RegistryObject<Item> EBONY_LOG = regBlockItem(ExtendedBlocks.EBONY_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_WOOD = regBlockItem(ExtendedBlocks.EBONY_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_PLANKS = regBlockItem(ExtendedBlocks.EBONY_PLANKS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_LEAVES = regBlockItem(ExtendedBlocks.EBONY_LEAVES, LOTRItemGroups.DECO);
	public static RegistryObject<Item> EBONY_SAPLING = regBlockItem(ExtendedBlocks.EBONY_SAPLING, LOTRItemGroups.DECO);
	public static RegistryObject<Item> EBONY_SLAB = regBlockItem(ExtendedBlocks.EBONY_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_STAIRS = regBlockItem(ExtendedBlocks.EBONY_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_FENCE = regBlockItem(ExtendedBlocks.EBONY_FENCE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> EBONY_FENCE_GATE = regBlockItem(ExtendedBlocks.EBONY_FENCE_GATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> EBONY_DOOR = regTallBlockItem(ExtendedBlocks.EBONY_DOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> EBONY_TRAPDOOR = regBlockItem(ExtendedBlocks.EBONY_TRAPDOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> EBONY_PRESSURE_PLATE = regBlockItem(ExtendedBlocks.EBONY_PRESSURE_PLATE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> EBONY_BUTTON = regBlockItem(ExtendedBlocks.EBONY_BUTTON, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> STRIPPED_EBONY_LOG = regBlockItem(ExtendedBlocks.STRIPPED_EBONY_LOG, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_EBONY_WOOD = regBlockItem(ExtendedBlocks.STRIPPED_EBONY_WOOD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_BEAM = regBlockItem(ExtendedBlocks.EBONY_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_BEAM_SLAB = regBlockItem(ExtendedBlocks.EBONY_BEAM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_SIGN = regSignItem(ExtendedBlocks.EBONY_SIGN, ExtendedBlocks.EBONY_WALL_SIGN);
	public static RegistryObject<Item> EBONY_BOAT = regBoatItem("ebony_boat", ExtendedBoatEntity.Type.EBONY, LOTRItemGroups.MISC);
	public static RegistryObject<Item> EBONY_LOG_SLAB = regBlockItem(ExtendedBlocks.EBONY_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_WOOD_SLAB = regBlockItem(ExtendedBlocks.EBONY_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_WOOD_STAIRS = regBlockItem(ExtendedBlocks.EBONY_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EBONY_BRANCH = regBlockItem(ExtendedBlocks.EBONY_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_EBONY_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_EBONY_LOG_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_EBONY_WOOD_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_EBONY_WOOD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_EBONY_WOOD_STAIRS = regBlockItem(ExtendedBlocks.STRIPPED_EBONY_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> STRIPPED_EBONY_BRANCH = regBlockItem(ExtendedBlocks.STRIPPED_EBONY_BRANCH, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FALLEN_EBONY_LEAVES = regFallenLeavesItem(ExtendedBlocks.FALLEN_EBONY_LEAVES, LOTRItemGroups.DECO);

	//ORE/GEM STORAGE
	public static RegistryObject<Item> GALVORN_BLOCK = regBlockItem(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BLACK_URUK_STEEL_BLOCK = regBlockItem(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GILDED_IRON_BLOCK = regBlockItem(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORGUL_STEEL_BLOCK = regBlockItem(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TOPAZ_BLOCK = regBlockItem(ExtendedBlocks.TOPAZ_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> AMETHYST_BLOCK = regBlockItem(ExtendedBlocks.AMETHYST_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SAPPHIRE_BLOCK = regBlockItem(ExtendedBlocks.SAPPHIRE_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RUBY_BLOCK = regBlockItem(ExtendedBlocks.RUBY_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> AMBER_BLOCK = regBlockItem(ExtendedBlocks.AMBER_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DIAMOND_BLOCK = regBlockItem(ExtendedBlocks.DIAMOND_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OPAL_BLOCK = regBlockItem(ExtendedBlocks.OPAL_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EMERALD_BLOCK = regBlockItem(ExtendedBlocks.EMERALD_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PEARL_BLOCK = regBlockItem(ExtendedBlocks.PEARL_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CORAL_BLOCK = regBlockItem(ExtendedBlocks.CORAL_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DEAD_CORAL_BLOCK = regBlockItem(ExtendedBlocks.DEAD_CORAL_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> WASTE_BLOCK = regBlockItem(ExtendedBlocks.WASTE_BLOCK, LOTRItemGroups.BLOCKS);
	//ORE
	public static RegistryObject<Item> METEORITE_ORE = regBlockItem(ExtendedBlocks.METEORITE_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TOPAZ_ORE = regBlockItem(ExtendedBlocks.TOPAZ_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> AMETHYST_ORE = regBlockItem(ExtendedBlocks.AMETHYST_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SAPPHIRE_ORE = regBlockItem(ExtendedBlocks.SAPPHIRE_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RUBY_ORE = regBlockItem(ExtendedBlocks.RUBY_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> AMBER_ORE = regBlockItem(ExtendedBlocks.AMBER_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DIAMOND_ORE = regBlockItem(ExtendedBlocks.DIAMOND_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OPAL_ORE = regBlockItem(ExtendedBlocks.OPAL_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> EMERALD_ORE = regBlockItem(ExtendedBlocks.EMERALD_ORE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> REMAINS_ELF = regBlockItem(ExtendedBlocks.REMAINS_ELF, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> REMAINS_MAN = regBlockItem(ExtendedBlocks.REMAINS_MAN, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> REMAINS_ORC = regBlockItem(ExtendedBlocks.REMAINS_ORC, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> REMAINS_DWARF = regBlockItem(ExtendedBlocks.REMAINS_DWARF, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> BARREN_DIRT = regBlockItem(ExtendedBlocks.BARREN_DIRT, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BARREN_DIRT_SLAB = regBlockItem(ExtendedBlocks.BARREN_DIRT_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> COARSE_DIRT_SLAB = regBlockItem(ExtendedBlocks.COARSE_DIRT_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DIRT_SLAB = regBlockItem(ExtendedBlocks.DIRT_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GRASS_BLOCK_SLAB = regBlockItem(ExtendedBlocks.GRASS_BLOCK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GRASS_PATH_SLAB = regBlockItem(ExtendedBlocks.GRASS_PATH_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> PODZOL_SLAB = regBlockItem(ExtendedBlocks.PODZOL_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MYCELIUM_SLAB = regBlockItem(ExtendedBlocks.MYCELIUM_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORDOR_DIRT_SLAB = regBlockItem(ExtendedBlocks.MORDOR_DIRT_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORDOR_DIRT_PATH_SLAB = regBlockItem(ExtendedBlocks.MORDOR_DIRT_PATH_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CHALK_PATH_SLAB = regBlockItem(ExtendedBlocks.CHALK_PATH_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SNOW_SLAB = regBlockItem(ExtendedBlocks.SNOW_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SNOW_PATH_SLAB = regBlockItem(ExtendedBlocks.SNOW_PATH_SLAB, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> BONE_BLOCK_SLAB = regBlockItem(ExtendedBlocks.BONE_BLOCK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BONE_BLOCK_STAIRS = regBlockItem(ExtendedBlocks.BONE_BLOCK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BONE_BLOCK_WALL = regBlockItem(ExtendedBlocks.BONE_BLOCK_WALL, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> WHITE_MATTED_FUR = regBlockItemProps(ExtendedBlocks.WHITE_MATTED_FUR, LOTRItemGroups.BLOCKS, (props) -> props.rarity(Rarity.UNCOMMON));
	public static RegistryObject<Item> WHITE_MATTED_FUR_SLAB = regBlockItemProps(ExtendedBlocks.WHITE_MATTED_FUR_SLAB, LOTRItemGroups.BLOCKS, (props) -> props.rarity(Rarity.UNCOMMON));
	public static RegistryObject<Item> WHITE_MATTED_FUR_STAIRS = regBlockItemProps(ExtendedBlocks.WHITE_MATTED_FUR_STAIRS, LOTRItemGroups.BLOCKS, (props) -> props.rarity(Rarity.UNCOMMON));
	public static RegistryObject<Item> WHITE_MATTED_FUR_WALL = regBlockItemProps(ExtendedBlocks.WHITE_MATTED_FUR_WALL, LOTRItemGroups.BLOCKS, (props) -> props.rarity(Rarity.UNCOMMON));
	public static RegistryObject<Item> SILVER_MATTED_FUR = regBlockItem(ExtendedBlocks.SILVER_MATTED_FUR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SILVER_MATTED_FUR_SLAB = regBlockItem(ExtendedBlocks.SILVER_MATTED_FUR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SILVER_MATTED_FUR_STAIRS = regBlockItem(ExtendedBlocks.SILVER_MATTED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SILVER_MATTED_FUR_WALL = regBlockItem(ExtendedBlocks.SILVER_MATTED_FUR_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GRAY_MATTED_FUR = regBlockItem(ExtendedBlocks.GRAY_MATTED_FUR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GRAY_MATTED_FUR_SLAB = regBlockItem(ExtendedBlocks.GRAY_MATTED_FUR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GRAY_MATTED_FUR_STAIRS = regBlockItem(ExtendedBlocks.GRAY_MATTED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GRAY_MATTED_FUR_WALL = regBlockItem(ExtendedBlocks.GRAY_MATTED_FUR_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BROWN_MATTED_FUR = regBlockItem(ExtendedBlocks.BROWN_MATTED_FUR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BROWN_MATTED_FUR_SLAB = regBlockItem(ExtendedBlocks.BROWN_MATTED_FUR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BROWN_MATTED_FUR_STAIRS = regBlockItem(ExtendedBlocks.BROWN_MATTED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BROWN_MATTED_FUR_WALL = regBlockItem(ExtendedBlocks.BROWN_MATTED_FUR_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BLACK_MATTED_FUR = regBlockItem(ExtendedBlocks.BLACK_MATTED_FUR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BLACK_MATTED_FUR_SLAB = regBlockItem(ExtendedBlocks.BLACK_MATTED_FUR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BLACK_MATTED_FUR_STAIRS = regBlockItem(ExtendedBlocks.BLACK_MATTED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> BLACK_MATTED_FUR_WALL = regBlockItem(ExtendedBlocks.BLACK_MATTED_FUR_WALL, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> CROSSED_WATTLE_AND_DAUB = regBlockItem(ExtendedBlocks.CROSSED_WATTLE_AND_DAUB, LOTRItemGroups.BLOCKS);

	//scorched stuff
	public static RegistryObject<Item> SCORCHED_COBBLESTONE = regBlockItem(ExtendedBlocks.SCORCHED_COBBLESTONE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_COBBLESTONE_SLAB = regBlockItem(ExtendedBlocks.SCORCHED_COBBLESTONE_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_COBBLESTONE_STAIRS = regBlockItem(ExtendedBlocks.SCORCHED_COBBLESTONE_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_COBBLESTONE_WALL = regBlockItem(ExtendedBlocks.SCORCHED_COBBLESTONE_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_STONE = regBlockItem(ExtendedBlocks.SCORCHED_STONE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_STONE_SLAB = regBlockItem(ExtendedBlocks.SCORCHED_STONE_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_STONE_STAIRS = regBlockItem(ExtendedBlocks.SCORCHED_STONE_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_STONE_WALL = regBlockItem(ExtendedBlocks.SCORCHED_STONE_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_BRICK = regBlockItem(ExtendedBlocks.SCORCHED_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_BRICK_SLAB = regBlockItem(ExtendedBlocks.SCORCHED_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_BRICK_STAIRS = regBlockItem(ExtendedBlocks.SCORCHED_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SCORCHED_BRICK_WALL = regBlockItem(ExtendedBlocks.SCORCHED_BRICK_WALL, LOTRItemGroups.BLOCKS);
	//Arnor
	public static RegistryObject<Item> CRACKED_ARNOR_PILLAR = regBlockItem(ExtendedBlocks.CRACKED_ARNOR_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_ARNOR_PILLAR_SLAB = regBlockItem(ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_ARNOR_PILLAR = regBlockItem(ExtendedBlocks.MOSSY_ARNOR_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_ARNOR_PILLAR_SLAB = regBlockItem(ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_STONE_PILLAR = regBlockItem(ExtendedBlocks.CRACKED_STONE_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_STONE_PILLAR_SLAB = regBlockItem(ExtendedBlocks.CRACKED_STONE_PILLAR_SLAB, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> RED_ANGMAR_BRICK = regBlockItem(ExtendedBlocks.RED_ANGMAR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_ANGMAR_BRICK_SLAB = regBlockItem(ExtendedBlocks.RED_ANGMAR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_ANGMAR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.RED_ANGMAR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_ANGMAR_BRICK_WALL = regBlockItem(ExtendedBlocks.RED_ANGMAR_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_ANGMAR_BRICK = regBlockItem(ExtendedBlocks.CRACKED_RED_ANGMAR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_ANGMAR_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_RED_ANGMAR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_ANGMAR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_RED_ANGMAR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_ANGMAR_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_RED_ANGMAR_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_ANGMAR_BRICK = regBlockItem(ExtendedBlocks.MOSSY_RED_ANGMAR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_ANGMAR_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_RED_ANGMAR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_ANGMAR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_RED_ANGMAR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_ANGMAR_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_RED_ANGMAR_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SNOWY_RED_ANGMAR_BRICK = regBlockItem(ExtendedBlocks.SNOWY_RED_ANGMAR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SNOWY_RED_ANGMAR_BRICK_SLAB = regBlockItem(ExtendedBlocks.SNOWY_RED_ANGMAR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SNOWY_RED_ANGMAR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.SNOWY_RED_ANGMAR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SNOWY_RED_ANGMAR_BRICK_WALL = regBlockItem(ExtendedBlocks.SNOWY_RED_ANGMAR_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_ANGMAR_PILLAR = regBlockItem(ExtendedBlocks.RED_ANGMAR_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_ANGMAR_PILLAR_SLAB = regBlockItem(ExtendedBlocks.RED_ANGMAR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_RED_ANGMAR_BRICK = regBlockItem(ExtendedBlocks.CARVED_RED_ANGMAR_BRICK, LOTRItemGroups.BLOCKS);

	//		ExtendedItems.GNEISS_ROCK = regBlockItemB(ExtendedBlocks.GNEISS_ROCK, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_ROCK_SLAB = regBlockItemB(ExtendedBlocks.GNEISS_ROCK_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_ROCK_STAIRS = regBlockItemB(ExtendedBlocks.GNEISS_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_ROCK_WALL = regBlockItemB(ExtendedBlocks.GNEISS_ROCK_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_BRICK = regBlockItemB(ExtendedBlocks.GNEISS_BRICK, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_BRICK_SLAB = regBlockItemB(ExtendedBlocks.GNEISS_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.GNEISS_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.GNEISS_BRICK_WALL = regBlockItemB(ExtendedBlocks.GNEISS_BRICK_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_GNEISS = regBlockItemB(ExtendedBlocks.POLISHED_GNEISS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_GNEISS_SLAB = regBlockItemB(ExtendedBlocks.POLISHED_GNEISS_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_GNEISS_STAIRS = regBlockItemB(ExtendedBlocks.POLISHED_GNEISS_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_GNEISS_WALL = regBlockItemB(ExtendedBlocks.POLISHED_GNEISS_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_ROCK = regBlockItemB(ExtendedBlocks.LIMESTONE_ROCK, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_ROCK_SLAB = regBlockItemB(ExtendedBlocks.LIMESTONE_ROCK_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_ROCK_STAIRS = regBlockItemB(ExtendedBlocks.LIMESTONE_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_ROCK_WALL = regBlockItemB(ExtendedBlocks.LIMESTONE_ROCK_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_BRICK = regBlockItemB(ExtendedBlocks.LIMESTONE_BRICK, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_BRICK_SLAB = regBlockItemB(ExtendedBlocks.LIMESTONE_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.LIMESTONE_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_BRICK_WALL = regBlockItemB(ExtendedBlocks.LIMESTONE_BRICK_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_TILE = regBlockItemB(ExtendedBlocks.LIMESTONE_TILE, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_TILE_SLAB = regBlockItemB(ExtendedBlocks.LIMESTONE_TILE_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_TILE_STAIRS = regBlockItemB(ExtendedBlocks.LIMESTONE_TILE_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.LIMESTONE_TILE_WALL = regBlockItemB(ExtendedBlocks.LIMESTONE_TILE_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_LIMESTONE = regBlockItemB(ExtendedBlocks.POLISHED_LIMESTONE, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_LIMESTONE_SLAB = regBlockItemB(ExtendedBlocks.POLISHED_LIMESTONE_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_LIMESTONE_STAIRS = regBlockItemB(ExtendedBlocks.POLISHED_LIMESTONE_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_LIMESTONE_WALL = regBlockItemB(ExtendedBlocks.POLISHED_LIMESTONE_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SMOOTH_LIMESTONE = regBlockItemB(ExtendedBlocks.SMOOTH_LIMESTONE, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SMOOTH_LIMESTONE_SLAB = regBlockItemB(ExtendedBlocks.SMOOTH_LIMESTONE_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SMOOTH_LIMESTONE_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_LIMESTONE_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SMOOTH_LIMESTONE_WALL = regBlockItemB(ExtendedBlocks.SMOOTH_LIMESTONE_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_ROCK = regBlockItemB(ExtendedBlocks.SLATE_ROCK, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_ROCK_SLAB = regBlockItemB(ExtendedBlocks.SLATE_ROCK_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_ROCK_STAIRS = regBlockItemB(ExtendedBlocks.SLATE_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_ROCK_WALL = regBlockItemB(ExtendedBlocks.SLATE_ROCK_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_BRICK = regBlockItemB(ExtendedBlocks.SLATE_BRICK, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_BRICK_SLAB = regBlockItemB(ExtendedBlocks.SLATE_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.SLATE_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.SLATE_BRICK_WALL = regBlockItemB(ExtendedBlocks.SLATE_BRICK_WALL, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_SLATE = regBlockItemB(ExtendedBlocks.POLISHED_SLATE, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_SLATE_SLAB = regBlockItemB(ExtendedBlocks.POLISHED_SLATE_SLAB, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_SLATE_STAIRS = regBlockItemB(ExtendedBlocks.POLISHED_SLATE_STAIRS, LOTRItemGroups.BLOCKS);
	//		ExtendedItems.POLISHED_SLATE_WALL = regBlockItemB(ExtendedBlocks.POLISHED_SLATE_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSS_BLOCK = regBlockItem(ExtendedBlocks.MOSS_BLOCK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSS_CARPET = regBlockItem(ExtendedBlocks.MOSS_CARPET, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> ANCIENT_RUNE_BLOCK = regBlockItem(ExtendedBlocks.ANCIENT_RUNE_BLOCK, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> DWARVEN_DOOR = regBlockItem(ExtendedBlocks.DWARVEN_DOOR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DWARVEN_DOOR_LUIGON = regBlockItem(ExtendedBlocks.DWARVEN_DOOR_LUIGON, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DWARVEN_DOOR_CARGON = regBlockItem(ExtendedBlocks.DWARVEN_DOOR_CARGON, LOTRItemGroups.BLOCKS);
	//Mud
	public static RegistryObject<Item> MUD = regBlockItem(ExtendedBlocks.MUD, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_SLAB = regBlockItem(ExtendedBlocks.MUD_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_STAIRS = regBlockItem(ExtendedBlocks.MUD_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_GRASS = regBlockItem(ExtendedBlocks.MUD_GRASS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_BRICK = regBlockItem(ExtendedBlocks.MUD_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_BRICK_SLAB = regBlockItem(ExtendedBlocks.MUD_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MUD_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MUD_BRICK_WALL = regBlockItem(ExtendedBlocks.MUD_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_MUD_BRICK = regBlockItem(ExtendedBlocks.CRACKED_MUD_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_MUD_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_MUD_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_MUD_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_MUD_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_MUD_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_MUD_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_MUD_BRICK = regBlockItem(ExtendedBlocks.MOSSY_MUD_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_MUD_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_MUD_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_MUD_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_MUD_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_MUD_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_MUD_BRICK_WALL, LOTRItemGroups.BLOCKS);

	//########### BLOCK TYPES ##################
	//Stone types
	public static RegistryObject<Item> DRYSTONE_SLAB = regBlockItem(ExtendedBlocks.DRYSTONE_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DRYSTONE_STAIRS = regBlockItem(ExtendedBlocks.DRYSTONE_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DRYSTONE_WALL = regBlockItem(ExtendedBlocks.DRYSTONE_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> COBBLED_DRYSTONE = regBlockItem(ExtendedBlocks.COBBLED_DRYSTONE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CUT_DRYSTONE = regBlockItem(ExtendedBlocks.CUT_DRYSTONE, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> MOSSY_DRYSTONE = regBlockItem(ExtendedBlocks.MOSSY_DRYSTONE, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DRYSTONE_SLAB = regBlockItem(ExtendedBlocks.MOSSY_DRYSTONE_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DRYSTONE_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_DRYSTONE_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DRYSTONE_WALL = regBlockItem(ExtendedBlocks.MOSSY_DRYSTONE_WALL, LOTRItemGroups.BLOCKS);

	// Goblin Bricks
	public static RegistryObject<Item> GOBLIN_BRICK = regBlockItem(ExtendedBlocks.GOBLIN_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOBLIN_BRICK_SLAB = regBlockItem(ExtendedBlocks.GOBLIN_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOBLIN_BRICK_STAIRS = regBlockItem(ExtendedBlocks.GOBLIN_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOBLIN_BRICK_WALL = regBlockItem(ExtendedBlocks.GOBLIN_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOBLIN_PILLAR = regBlockItem(ExtendedBlocks.GOBLIN_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOBLIN_PILLAR_SLAB = regBlockItem(ExtendedBlocks.GOBLIN_PILLAR_SLAB, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> ORC_PLATING_WALL = regBlockItem(ExtendedBlocks.ORC_PLATING_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RUSTED_ORC_PLATING_WALL = regBlockItem(ExtendedBlocks.RUSTED_ORC_PLATING_WALL, LOTRItemGroups.BLOCKS);

	//Taurethrim Bricks
	public static RegistryObject<Item> TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TAURETHRIM_PILLAR = regBlockItem(ExtendedBlocks.TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> TAURETHRIM_PILLAR_SLAB = regBlockItem(ExtendedBlocks.TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_PILLAR = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_PILLAR_SLAB = regBlockItem(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_TAURETHRIM_BRICK = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_TAURETHRIM_BRICK_SLAB = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_TAURETHRIM_BRICK_STAIRS = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_TAURETHRIM_BRICK_WALL = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_TAURETHRIM_PILLAR = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_TAURETHRIM_PILLAR_SLAB = regBlockItem(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);

	//Dwarven Bricks
	public static RegistryObject<Item> OBSIDIAN_DWARVEN_BRICK = regBlockItem(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_DWARVEN_BRICK_SLAB = regBlockItem(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_DWARVEN_BRICK_STAIRS = regBlockItem(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> OBSIDIAN_DWARVEN_BRICK_WALL = regBlockItem(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL, LOTRItemGroups.BLOCKS);

	//Dol Guldur Bricks
	public static RegistryObject<Item> DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DOL_GULDUR_BRICK_SLAB = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DOL_GULDUR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DOL_GULDUR_BRICK_WALL = regBlockItem(ExtendedBlocks.DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DOL_GULDUR_PILLAR = regBlockItem(ExtendedBlocks.DOL_GULDUR_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> DOL_GULDUR_PILLAR_SLAB = regBlockItem(ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.CARVED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_DOL_GULDUR_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_DOL_GULDUR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_DOL_GULDUR_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DOL_GULDUR_BRICK = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DOL_GULDUR_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DOL_GULDUR_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_DOL_GULDUR_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);

	//Rhunic Bricks
	public static RegistryObject<Item> RHUNIC_BRICK = regBlockItem(ExtendedBlocks.RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RHUNIC_PILLAR = regBlockItem(ExtendedBlocks.RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RHUNIC_PILLAR_SLAB = regBlockItem(ExtendedBlocks.RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.CARVED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLD_TRIMMED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLD_TRIMMED_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLD_TRIMMED_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLD_TRIMMED_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FLOWERY_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FLOWERY_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FLOWERY_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> FLOWERY_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_RHUNIC_PILLAR = regBlockItem(ExtendedBlocks.RED_RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> RED_RHUNIC_PILLAR_SLAB = regBlockItem(ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_RED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.CARVED_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CRACKED_RED_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_RHUNIC_BRICK = regBlockItem(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_RHUNIC_BRICK_SLAB = regBlockItem(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_RHUNIC_BRICK_STAIRS = regBlockItem(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MOSSY_RED_RHUNIC_BRICK_WALL = regBlockItem(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> ROHAN_BEAM = regBlockItem(ExtendedBlocks.ROHAN_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> ORNATE_ROHAN_BEAM = regBlockItem(ExtendedBlocks.ORNATE_ROHAN_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> GOLDEN_ROHAN_BEAM = regBlockItem(ExtendedBlocks.GOLDEN_ROHAN_BEAM, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> ORNATE_GOLDEN_ROHAN_BEAM = regBlockItem(ExtendedBlocks.ORNATE_GOLDEN_ROHAN_BEAM, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> SMOOTH_GONDOR_ROCK_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_GONDOR_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_BLACK_GONDOR_BRICK = regBlockItem(ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_BLACK_UMBAR_BRICK = regBlockItem(ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_UMBAR_BRICK = regBlockItem(ExtendedBlocks.CARVED_UMBAR_BRICK, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> MORGOND = regBlockItem(ExtendedBlocks.MORGOND, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORGOND_SLAB = regBlockItem(ExtendedBlocks.MORGOND_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORGOND_STAIRS = regBlockItem(ExtendedBlocks.MORGOND_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORGOND_WALL = regBlockItem(ExtendedBlocks.MORGOND_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORGOND_PILLAR = regBlockItem(ExtendedBlocks.MORGOND_PILLAR, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> MORGOND_PILLAR_SLAB = regBlockItem(ExtendedBlocks.MORGOND_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_MORGOND = regBlockItem(ExtendedBlocks.CARVED_MORGOND, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_MORGOND_SLAB = regBlockItem(ExtendedBlocks.CARVED_MORGOND_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_MORGOND_STAIRS = regBlockItem(ExtendedBlocks.CARVED_MORGOND_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> CARVED_MORGOND_WALL = regBlockItem(ExtendedBlocks.CARVED_MORGOND_WALL, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_MORGOND = regBlockItem(ExtendedBlocks.SMOOTH_MORGOND, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_MORGOND_SLAB = regBlockItem(ExtendedBlocks.SMOOTH_MORGOND_SLAB, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_MORGOND_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_MORGOND_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_MORGOND_WALL = regBlockItem(ExtendedBlocks.SMOOTH_MORGOND_WALL, LOTRItemGroups.BLOCKS);

	public static RegistryObject<Item> SMOOTH_MORDOR_ROCK_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_MORDOR_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_ROHAN_ROCK_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_ROHAN_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_BLUE_ROCK_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_BLUE_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_RED_ROCK_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_RED_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
	public static RegistryObject<Item> SMOOTH_STONE_STAIRS = regBlockItem(ExtendedBlocks.SMOOTH_STONE_STAIRS, LOTRItemGroups.BLOCKS);

	//############################################################DECORATION##############################################################
	public static RegistryObject<Item> STRAW_BED = regBedItem(ExtendedBlocks.STRAW_BED, LOTRItemGroups.DECO, ()->strawBedRenderer());
	public static RegistryObject<Item> ORC_BED = regBedItem(ExtendedBlocks.ORC_BED, LOTRItemGroups.DECO, ()->orcBedRenderer());
	public static RegistryObject<Item> BLACK_FUR_BED = regBedItem(ExtendedBlocks.BLACK_FUR_BED, LOTRItemGroups.DECO, ()->blackFurBedRenderer());
	public static RegistryObject<Item> FUR_BED = regBedItem(ExtendedBlocks.FUR_BED, LOTRItemGroups.DECO, ()->furBedRenderer());
	public static RegistryObject<Item> GRAY_FUR_BED = regBedItem(ExtendedBlocks.GRAY_FUR_BED, LOTRItemGroups.DECO, ()->grayFurBedRenderer());
	public static RegistryObject<Item> SILVER_FUR_BED = regBedItem(ExtendedBlocks.SILVER_FUR_BED, LOTRItemGroups.DECO, ()->silverFurBedRenderer());
	public static RegistryObject<Item> WHITE_FUR_BED = regBedItem(ExtendedBlocks.WHITE_FUR_BED, LOTRItemGroups.DECO, ()->whiteFurBedRenderer());
	public static RegistryObject<Item> BEDROLL = regBedItem(ExtendedBlocks.BEDROLL, LOTRItemGroups.DECO);
	public static RegistryObject<Item> DWARVEN_BED = regBedItem(ExtendedBlocks.DWARVEN_BED, LOTRItemGroups.DECO, ()->dwarvenBedRenderer());
	public static RegistryObject<Item> HIGH_ELVEN_BED = regBedItem(ExtendedBlocks.HIGH_ELVEN_BED, LOTRItemGroups.DECO, ()->highElvenBedRenderer());
	//Bushes
	public static RegistryObject<Item> RASPBERRY_HEDGE = regBlockItem(ExtendedBlocks.RASPBERRY_HEDGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BLUEBERRY_HEDGE = regBlockItem(ExtendedBlocks.BLUEBERRY_HEDGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BLACKBERRY_HEDGE = regBlockItem(ExtendedBlocks.BLACKBERRY_HEDGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> CRANBERRY_HEDGE = regBlockItem(ExtendedBlocks.CRANBERRY_HEDGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> ELDERBERRY_HEDGE = regBlockItem(ExtendedBlocks.ELDERBERRY_HEDGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WILDBERRY_HEDGE = regBlockItem(ExtendedBlocks.WILDBERRY_HEDGE, LOTRItemGroups.DECO);

	public static RegistryObject<Item> DEAD_MOSS_VINES = regBlockItem(ExtendedBlocks.DEAD_MOSS_VINES, LOTRItemGroups.DECO);

	public static RegistryObject<Item> WHITE_FUR_BUNDLE = regBlockItemProps(ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRItemGroups.DECO, (props) -> props.rarity(Rarity.UNCOMMON));
	public static RegistryObject<Item> SILVER_FUR_BUNDLE = regBlockItem(ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> GRAY_FUR_BUNDLE = regBlockItem(ExtendedBlocks.GRAY_FUR_BUNDLE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BLACK_FUR_BUNDLE = regBlockItem(ExtendedBlocks.BLACK_FUR_BUNDLE, LOTRItemGroups.DECO);

	public static RegistryObject<Item> BLACK_FUR_CARPET = regBlockItem(ExtendedBlocks.BLACK_FUR_CARPET, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BROWN_FUR_CARPET = regBlockItem(ExtendedBlocks.BROWN_FUR_CARPET, LOTRItemGroups.DECO);
	public static RegistryObject<Item> GRAY_FUR_CARPET = regBlockItem(ExtendedBlocks.GRAY_FUR_CARPET, LOTRItemGroups.DECO);
	public static RegistryObject<Item> SILVER_FUR_CARPET = regBlockItem(ExtendedBlocks.SILVER_FUR_CARPET, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WHITE_FUR_CARPET = regBlockItemProps(ExtendedBlocks.WHITE_FUR_CARPET, LOTRItemGroups.DECO, (props) -> props.rarity(Rarity.UNCOMMON));

	public static RegistryObject<Item> WOODEN_BIRDCAGE = regBlockItem(ExtendedBlocks.WOODEN_BIRDCAGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BRONZE_BIRDCAGE = regBlockItem(ExtendedBlocks.BRONZE_BIRDCAGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> IRON_BIRDCAGE = regBlockItem(ExtendedBlocks.IRON_BIRDCAGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> SILVER_BIRDCAGE = regBlockItem(ExtendedBlocks.SILVER_BIRDCAGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> GOLDEN_BIRDCAGE = regBlockItem(ExtendedBlocks.GOLDEN_BIRDCAGE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> MITHRIL_BIRDCAGE = regBlockItem(ExtendedBlocks.MITHRIL_BIRDCAGE, LOTRItemGroups.DECO);

	public static RegistryObject<Item> MORGUL_TORCH = regWallOrFloorItem(ExtendedBlocks.MORGUL_TORCH, ExtendedBlocks.MORGUL_WALL_TORCH, LOTRItemGroups.DECO);
	public static RegistryObject<Item> MORGUL_BRAZIER = regBlockItem(ExtendedBlocks.MORGUL_BRAZIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> MORGUL_CHANDELIER = regBlockItem(ExtendedBlocks.MORGUL_CHANDELIER, LOTRItemGroups.DECO);

	public static RegistryObject<Item> HIGH_ELVEN_LANTERN = regBlockItem(ExtendedBlocks.HIGH_ELVEN_LANTERN, LOTRItemGroups.DECO);
	public static RegistryObject<Item> HIGH_ELVEN_CHAIN = regBlockItem(ExtendedBlocks.HIGH_ELVEN_CHAIN, LOTRItemGroups.DECO);
	public static RegistryObject<Item> ORC_CHAIN = regBlockItem(ExtendedBlocks.ORC_CHAIN, LOTRItemGroups.DECO);
	public static RegistryObject<Item> ORC_CHANDELIER = regBlockItem(ExtendedBlocks.ORC_CHANDELIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> URUK_CHANDELIER = regBlockItem(ExtendedBlocks.URUK_CHANDELIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> DWARVEN_CHANDELIER = regBlockItem(ExtendedBlocks.DWARVEN_CHANDELIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> BLUE_DWARVEN_CHANDELIER = regBlockItem(ExtendedBlocks.BLUE_DWARVEN_CHANDELIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> NUMENOREAN_LANTERN = regBlockItem(ExtendedBlocks.NUMENOREAN_LANTERN, LOTRItemGroups.DECO);
	public static RegistryObject<Item> EDHELVIR_CRYSTAL_CHANDELIER = regBlockItem(ExtendedBlocks.EDHELVIR_CRYSTAL_CHANDELIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> GLOWSTONE_CRYSTAL_CHANDELIER = regBlockItem(ExtendedBlocks.GLOWSTONE_CRYSTAL_CHANDELIER, LOTRItemGroups.DECO);
	public static RegistryObject<Item> GULDURIL_CRYSTAL_CHANDELIER = regBlockItem(ExtendedBlocks.GULDURIL_CRYSTAL_CHANDELIER, LOTRItemGroups.DECO);

	public static RegistryObject<Item> MORGOND_DRIPSTONE = regBlockItem(ExtendedBlocks.MORGOND_DRIPSTONE, LOTRItemGroups.DECO);
	public static RegistryObject<Item> SMOOTH_MORGOND_DRIPSTONE = regBlockItem(ExtendedBlocks.SMOOTH_MORGOND_DRIPSTONE, LOTRItemGroups.DECO);

	public static RegistryObject<Item> NUMENOREAN_BARS = regBlockItem(ExtendedBlocks.NUMENOREAN_BARS, LOTRItemGroups.DECO);

	public static RegistryObject<Item> MUD_PATH = regBlockItem(ExtendedBlocks.MUD_PATH, LOTRItemGroups.DECO);
	public static RegistryObject<Item> MUD_PATH_SLAB = regBlockItem(ExtendedBlocks.MUD_PATH_SLAB, LOTRItemGroups.DECO);
	public static RegistryObject<Item> MUD_PATH_STAIRS = regBlockItem(ExtendedBlocks.MUD_PATH_STAIRS, LOTRItemGroups.DECO);
	public static RegistryObject<Item> MUD_FARMLAND = regBlockItem(ExtendedBlocks.MUD_FARMLAND, LOTRItemGroups.DECO);

	public static RegistryObject<Item> LARGE_POT = regBlockItem(ExtendedBlocks.LARGE_POT, LOTRItemGroups.DECO);
	public static RegistryObject<Item> LARGE_FLOWER_POT = regBlockItem(ExtendedBlocks.LARGE_FLOWER_POT, LOTRItemGroups.DECO);
	public static RegistryObject<Item> CHIMNEYPOT = regBlockItem(ExtendedBlocks.CHIMNEYPOT, LOTRItemGroups.DECO);
	public static RegistryObject<Item> STONE_CHEST = regBlockItem(ExtendedBlocks.STONE_CHEST, LOTRItemGroups.DECO);
	public static RegistryObject<Item> REED_BASKET = regBlockItem(ExtendedBlocks.REED_BASKET, LOTRItemGroups.DECO);
	public static RegistryObject<Item> ORC_BARREL = regBlockItem(ExtendedBlocks.ORC_BARREL, LOTRItemGroups.DECO);
	public static RegistryObject<Item> WEAPON_RACK = regBlockItem(ExtendedBlocks.WEAPON_RACK, LOTRItemGroups.DECO);

	public static RegistryObject<Item> TAPESTRY = regItem("tapestry", () -> new ExtendedTapestryEntityItem(new Properties().tab(LOTRItemGroups.DECO)));

	//BANNERS
	public static RegistryObject<Item> ANGMAR_BANNER = regItem("angmar_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.ANGMAR_BANNER.get(), ExtendedBlocks.ANGMAR_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> BLUE_MOUNTAINS_BANNER = regItem("blue_mountains_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.BLUE_MOUNTAINS_BANNER.get(), ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> BREE_BANNER = regItem("bree_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.BREE_BANNER.get(), ExtendedBlocks.BREE_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DALE_BANNER = regItem("dale_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DALE_BANNER.get(), ExtendedBlocks.DALE_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DOL_AMROTH_BANNER = regItem("dol_amroth_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DOL_AMROTH_BANNER.get(), ExtendedBlocks.DOL_AMROTH_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DOL_GULDUR_BANNER = regItem("dol_guldur_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DOL_GULDUR_BANNER.get(), ExtendedBlocks.DOL_GULDUR_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DORWINION_BANNER = regItem("dorwinion_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DORWINION_BANNER.get(), ExtendedBlocks.DORWINION_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DUNEDAIN_BANNER = regItem("dunedain_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DUNEDAIN_BANNER.get(), ExtendedBlocks.DUNEDAIN_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DUNLAND_BANNER = regItem("dunland_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DUNLAND_BANNER.get(), ExtendedBlocks.DUNLAND_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> DURINS_FOLK_BANNER = regItem("durins_folk_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DURINS_FOLK_BANNER.get(), ExtendedBlocks.DURINS_FOLK_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> GONDOR_BANNER = regItem("gondor_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.GONDOR_BANNER.get(), ExtendedBlocks.GONDOR_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> GUNDABAD_ORC_BANNER = regItem("gundabad_orc_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.GUNDABAD_ORC_BANNER.get(), ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> HOBBIT_BANNER = regItem("hobbit_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.HOBBIT_BANNER.get(), ExtendedBlocks.HOBBIT_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> ISENGARD_BANNER = regItem("isengard_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.ISENGARD_BANNER.get(), ExtendedBlocks.ISENGARD_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> LINDON_BANNER = regItem("lindon_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.LINDON_BANNER.get(), ExtendedBlocks.LINDON_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> LOTHLORIEN_BANNER = regItem("lothlorien_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.LOTHLORIEN_BANNER.get(), ExtendedBlocks.LOTHLORIEN_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> MORDOR_BANNER = regItem("mordor_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.MORDOR_BANNER.get(), ExtendedBlocks.MORDOR_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> RIVENDELL_BANNER = regItem("rivendell_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.RIVENDELL_BANNER.get(), ExtendedBlocks.RIVENDELL_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> ROHAN_BANNER = regItem("rohan_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.ROHAN_BANNER.get(), ExtendedBlocks.ROHAN_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
	public static RegistryObject<Item> WOODLAND_REALM_BANNER = regItem("woodland_realm_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.WOODLAND_REALM_BANNER.get(), ExtendedBlocks.WOODLAND_REALM_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));

	//###################################################################MATERIALS########################################################
	//Misc
	public static RegistryObject<Item> OBSIDIAN_SHARD = regItem("obsidian_shard", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> HITHLAIN = regItem("hithlain", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> ITHILDIN = regItem("ithildin", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));

	public static RegistryObject<Item> WHITE_FUR = regItem("white_fur", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> SILVER_FUR = regItem("silver_fur", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> GRAY_FUR = regItem("gray_fur", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> BLACK_FUR = regItem("black_fur", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));

	//Ingots
	public static RegistryObject<Item> SKY_IRON = regItem("sky_iron", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.DARK_GRAY));
	public static RegistryObject<Item> GALVORN_INGOT = regItem("galvorn_ingot", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> BLACK_URUK_STEEL_INGOT = regItem("black_uruk_steel_ingot", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> GILDED_IRON_INGOT = regItem("gilded_iron_ingot", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> MORGUL_STEEL_INGOT = regItem("morgul_steel_ingot", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> ORC_STEEL_NUGGET = regItem("orc_steel_nugget", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	//Gems
	public static RegistryObject<Item> TOPAZ = regItem("topaz", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.GOLD));
	public static RegistryObject<Item> AMETHYST = regItem("amethyst", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.LIGHT_PURPLE));
	public static RegistryObject<Item> SAPPHIRE = regItem("sapphire", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.BLUE));
	public static RegistryObject<Item> RUBY = regItem("ruby", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.RED));
	public static RegistryObject<Item> AMBER = regItem("amber", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.YELLOW));
	public static RegistryObject<Item> DIAMOND = regItem("diamond", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.GRAY));
	public static RegistryObject<Item> OPAL = regItem("opal", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.AQUA));
	public static RegistryObject<Item> EMERALD = regItem("emerald", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.GREEN));
	public static RegistryObject<Item> PEARL = regItem("pearl", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.WHITE));
	public static RegistryObject<Item> CORAL = regItem("coral", () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	public static RegistryObject<Item> DEAD_CORAL = regItem("dead_coral", () -> new ExtendedColoringItem(new Properties().tab(LOTRItemGroups.MATERIALS), TextFormatting.GRAY));

	//#########################################################################TOOLS#####################################################
	//Taurethrim Tools
	public static RegistryObject<Item> TAURETHRIM_SHOVEL = regItem("taurethrim_shovel", () -> new ShovelItem(LOTRMaterial.valueOf("TAURETHRIM").asTool(), 1.5F, -3.0F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> TAURETHRIM_PICKAXE = regItem("taurethrim_pickaxe", () -> new PickaxeItem(LOTRMaterial.valueOf("TAURETHRIM").asTool(), 1, -2.8F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> TAURETHRIM_AXE = regItem("taurethrim_axe", () -> new AxeItem(LOTRMaterial.valueOf("TAURETHRIM").asTool(), 6.0F, -3.1F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> TAURETHRIM_HOE = regItem("taurethrim_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("TAURETHRIM")));

	//Angmar Tools
	public static RegistryObject<Item> ANGMAR_SHOVEL = regItem("angmar_shovel", () -> new ShovelItem(LOTRMaterial.valueOf("ANGMAR").asTool(), 1.5F, -3.0F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> ANGMAR_PICKAXE = regItem("angmar_pickaxe", () -> new PickaxeItem(LOTRMaterial.valueOf("ANGMAR").asTool(), 1, -2.8F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> ANGMAR_AXE = regItem("angmar_axe", () -> new AxeItem(LOTRMaterial.valueOf("ANGMAR").asTool(), 6.0F, -3.1F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> ANGMAR_HOE = regItem("angmar_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("ANGMAR")));

	//Bone Tools
	public static RegistryObject<Item> BONE_SHOVEL = regItem("bone_shovel", () -> new ShovelItem(LOTRMaterial.valueOf("EXT_BONE").asTool(), 1.5F, -3.0F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> BONE_PICKAXE = regItem("bone_pickaxe", () -> new PickaxeItem(LOTRMaterial.valueOf("EXT_BONE").asTool(), 1, -2.8F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> BONE_AXE = regItem("bone_axe", () -> new AxeItem(LOTRMaterial.valueOf("EXT_BONE").asTool(), 6.0F, -3.1F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> BONE_HOE = regItem("bone_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("EXT_BONE")));

	//Dol Guldur Tools
	public static RegistryObject<Item> DOL_GULDUR_SHOVEL = regItem("dol_guldur_shovel", () -> new ShovelItem(LOTRMaterial.valueOf("DOL_GULDUR").asTool(), 1.5F, -3.0F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> DOL_GULDUR_PICKAXE = regItem("dol_guldur_pickaxe", () -> new PickaxeItem(LOTRMaterial.valueOf("DOL_GULDUR").asTool(), 1, -2.8F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> DOL_GULDUR_AXE = regItem("dol_guldur_axe", () -> new AxeItem(LOTRMaterial.valueOf("DOL_GULDUR").asTool(), 6.0F, -3.1F, new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> DOL_GULDUR_HOE = regItem("dol_guldur_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("DOL_GULDUR")));

	public static RegistryObject<Item> BRANDING_IRON = regItem("branding_iron", ExtendedBrandingIronItem::new);
	public static RegistryObject<Item> CHISEL = regItem("chisel", () -> new ExtendedChiselItem(ExtendedBlocks.STONE_SIGN.get(), new Properties().tab(LOTRItemGroups.TOOLS).defaultDurability(100)));

	public static RegistryObject<Item> DWARVEN_MATTOCK = regItem("dwarven_mattock", () -> new ExtendedMattockItem(LOTRMaterial.DWARVEN.asTool(), new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> BLUE_DWARVEN_MATTOCK = regItem("blue_dwarven_mattock", () -> new ExtendedMattockItem(LOTRMaterial.BLUE_DWARVEN.asTool(), new Properties().tab(LOTRItemGroups.TOOLS)));
	public static RegistryObject<Item> MITHRIL_MATTOCK = regItem("mithril_mattock", () -> new ExtendedMattockItem(LOTRMaterial.MITHRIL.asTool(), new Properties().tab(LOTRItemGroups.TOOLS)));

	public static RegistryObject<Item> IRON_THROWABLE_AXE = regItem("iron_throwing_axe", () -> new ExtendedThrowableAxeItem(ItemTier.IRON));
	public static RegistryObject<Item> BRONZE_THROWABLE_AXE = regItem("bronze_throwing_axe", () -> new ExtendedThrowableAxeItem(LOTRMaterial.BRONZE));
	public static RegistryObject<Item> LOSSARNACH_THROWABLE_AXE = regItem("lossarnach_throwing_axe", () -> new ExtendedThrowableAxeItem(LOTRMaterial.GONDOR));
	public static RegistryObject<Item> DWARVEN_THROWABLE_AXE = regItem("dwarven_throwing_axe", () -> new ExtendedThrowableAxeItem(LOTRMaterial.DWARVEN));
	public static RegistryObject<Item> BLUE_DWARVEN_THROWABLE_AXE = regItem("blue_dwarven_throwing_axe", () -> new ExtendedThrowableAxeItem(LOTRMaterial.BLUE_DWARVEN));
	//######################################################################FOOD########################################################
	//Berries
	public static RegistryObject<Item> BLACKBERRY = regItem("blackberry", () -> new ExtendedBerryItem(ExtendedBlocks.BLACKBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLACKBERRY)));
	public static RegistryObject<Item> BLUEBERRY = regItem("blueberry", () -> new ExtendedBerryItem(ExtendedBlocks.BLUEBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLUEBERRY)));
	public static RegistryObject<Item> CRANBERRY = regItem("cranberry", () -> new ExtendedBerryItem(ExtendedBlocks.CRANBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CRANBERRY)));
	public static RegistryObject<Item> ELDERBERRY = regItem("elderberry", () -> new ExtendedBerryItem(ExtendedBlocks.ELDERBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ELDERBERRY)));
	public static RegistryObject<Item> RASPBERRY = regItem("raspberry", () -> new ExtendedBerryItem(ExtendedBlocks.RASPBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.RASPBERRY)));
	public static RegistryObject<Item> WILDBERRY = regItem("wildberry", ()-> new ExtendedBerryItem(ExtendedBlocks.WILDBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.WILDBERRY)));
	public static RegistryObject<Item> BLACKBERRY_PIE  = regCakeItem(ExtendedBlocks.BLACKBERRY_PIE, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> BLUEBERRY_PIE  = regCakeItem(ExtendedBlocks.BLUEBERRY_PIE, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> CRANBERRY_PIE  = regCakeItem(ExtendedBlocks.CRANBERRY_PIE, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> ELDERBERRY_PIE  = regCakeItem(ExtendedBlocks.ELDERBERRY_PIE, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> RASPBERRY_PIE  = regCakeItem(ExtendedBlocks.RASPBERRY_PIE, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> WILDBERRY_PIE  = regCakeItem(ExtendedBlocks.WILDBERRY_PIE, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> SWEET_BERRY_PIE  = regCakeItem(ExtendedBlocks.SWEET_BERRY_PIE, LOTRItemGroups.FOOD);

	public static RegistryObject<Item> BOTTLE_OF_POISON = regItem("bottle_of_poison", () -> new ExtendedPoisonBottleItem(new Properties().stacksTo(16).tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_CURDS)));
	public static RegistryObject<Item> BUCKET_OF_CHEESE_CURDS = regItem("bucket_of_cheese_curds", () -> new ExtendedBucketOfCheeseCurds(new Properties().stacksTo(32).tab(LOTRItemGroups.FOOD)));
	public static RegistryObject<Item> CHEESE_CURDS = regItem("cheese_curds", () -> new Item(new Properties().stacksTo(32).tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_CURDS)));
	public static RegistryObject<Item> CHEESE_WHEEL  = regCakeItem(ExtendedBlocks.CHEESE_WHEEL, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> AGED_CHEESE_WHEEL  = regCakeItem(ExtendedBlocks.AGED_CHEESE_WHEEL, LOTRItemGroups.FOOD);
	public static RegistryObject<Item> CHEESE_SLICE = regItem("cheese_slice", () -> new ExtendedUseItemAwardStatItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_SLICE), ExtendedStats.EAT_CHEESE_SLICE));
	public static RegistryObject<Item> AGED_CHEESE_SLICE = regItem("aged_cheese_slice", () -> new ExtendedUseItemAwardStatItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.AGED_CHEESE_SLICE), ExtendedStats.EAT_CHEESE_SLICE));

	public static RegistryObject<Item> HOBBIT_PANCAKE = regItem("hobbit_pancake", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.HOBBIT_PANCAKE)));
	public static RegistryObject<Item> HOBBIT_PANCAKE_SYRUP = regItem("hobbit_pancake_syrup", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.HOBBIT_PANCAKE_SYRUP)));
	public static RegistryObject<Item> HOBBIT_PANCAKE_SYRUP_BUTTER = regItem("hobbit_pancake_syrup_butter", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.HOBBIT_PANCAKE_SYRUP_BUTTER)));
	public static RegistryObject<Item> BUTTER = regItem("butter", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BUTTER)));

	public static RegistryObject<Item> BANANA = regItem("banana", () -> new ExtendedCompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BANANA), 0.65F));
	public static RegistryObject<Item> BANANA_BREAD = regItem("banana_bread", () -> new ExtendedCompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BANANA_BREAD), 0.85F));
	public static RegistryObject<Item> BANANA_CAKE  = regCakeItem(ExtendedBlocks.BANANA_CAKE, LOTRItemGroups.FOOD);

	public static RegistryObject<Item> ROASTED_CHESTNUT = regItem("roasted_chestnut", () -> new ExtendedCompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ROASTED_CHESTNUT), 0.5F));
	public static RegistryObject<Item> PLUM = regItem("plum", () -> new ExtendedCompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.PLUM), 0.65F));

	public static RegistryObject<Item> DUCK_EGG = regItem("duck_egg", () -> new ExtendedDuckEggItem(new Properties().stacksTo(16).tab(LOTRItemGroups.FOOD)));
	public static RegistryObject<Item> RAW_DUCK = regItem("raw_duck", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.DUCK)));
	public static RegistryObject<Item> COOKED_DUCK = regItem("cooked_duck", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.COOKED_DUCK)));

	public static RegistryObject<Item> SKULL_GOBLET = regEmptyVesselBlockItem(ExtendedBlocks.SKULL_GOBLET, VesselType.valueOf("SKULL_GOBLET"));

	public static RegistryObject<Item> BANANA_BEER = regItem("banana_beer", () -> VesselDrinkItem.newAlcohol(0.5F, 4, 0.6F));
	public static RegistryObject<Item> PLUM_KVASS = regItem("plum_kvass", () -> VesselDrinkItem.newAlcohol(0.2F, 4, 0.4F));
	//#######################################################################EQUIPMENT##################################################
	public static RegistryObject<Item> LEATHER_WARG_ARMOR = regItem("leather_warg_armor", () -> new ExtendedMountedDyeableArmorItem(8, "leather", MountType.WARG, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> MAIL_WARG_ARMOR = regItem("mail_warg_armor", () -> new ExtendedMountedArmorItem(10, "mail", MountType.WARG, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));

	//Generic Orc Equipment
	public static RegistryObject<Item> ORC_BOW = regItem("orc_bow", () -> new ExtendedBowItem(ExtendedBowMaterial.ORC));
	public static RegistryObject<Item> ORC_BOMB = regGenericBlockItem(ExtendedBlocks.ORC_BOMB, () -> new ExtendedOrcBombItem(ExtendedBlocks.ORC_BOMB, new Properties().tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> ORC_BOMB_DOUBLE = regGenericBlockItem(ExtendedBlocks.ORC_BOMB_DOUBLE, () -> new ExtendedOrcBombItem(ExtendedBlocks.ORC_BOMB_DOUBLE, new Properties().tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> ORC_BOMB_TRIPLE = regGenericBlockItem(ExtendedBlocks.ORC_BOMB_TRIPLE, () -> new ExtendedOrcBombItem(ExtendedBlocks.ORC_BOMB_TRIPLE, new Properties().tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> ORC_BOMB_FIRE = regGenericBlockItem(ExtendedBlocks.ORC_BOMB_FIRE, () -> new ExtendedOrcBombItem(ExtendedBlocks.ORC_BOMB_FIRE, new Properties().tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> ORC_BOMB_FIRE_DOUBLE = regGenericBlockItem(ExtendedBlocks.ORC_BOMB_FIRE_DOUBLE, () -> new ExtendedOrcBombItem(ExtendedBlocks.ORC_BOMB_FIRE_DOUBLE, new Properties().tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> ORC_BOMB_FIRE_TRIPLE = regGenericBlockItem(ExtendedBlocks.ORC_BOMB_FIRE_TRIPLE, () -> new ExtendedOrcBombItem(ExtendedBlocks.ORC_BOMB_FIRE_TRIPLE, new Properties().tab(LOTRItemGroups.COMBAT)));

	//Morgul Equipment
	//ExtendedItems.MORGUL_HELMET = regItemB("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD, "helmet"));
	//ExtendedItems.MORGUL_CHESTPLATE = regItemB("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
	//ExtendedItems.MORGUL_LEGGINGS = regItemB("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
	//ExtendedItems.MORGUL_BOOTS = regItemB("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> MORGUL_BLADE = regItem("morgul_blade",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("MORGUL").asTool(), LOTRItemGroups.COMBAT));

	//Taurethrim Equipment
	public static RegistryObject<Item> TAURETHRIM_CHIEFTAIN_HELMET = regItem("taurethrim_chieftain_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD,"chieftain_helmet"));
	public static RegistryObject<Item> TAURETHRIM_HELMET = regItem("taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD));
	public static RegistryObject<Item> TAURETHRIM_CHESTPLATE = regItem("taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> TAURETHRIM_LEGGINGS = regItem("taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> TAURETHRIM_BOOTS = regItem("taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> TAURETHRIM_SWORD = regItem("taurethrim_sword",() -> new HammerItem(LOTRMaterial.valueOf("TAURETHRIM")).noClink());
	public static RegistryObject<Item> TAURETHRIM_SPEAR = regItem("taurethrim_spear",() -> new SpearItem(LOTRMaterial.valueOf("TAURETHRIM")));
	public static RegistryObject<Item> TAURETHRIM_DAGGER = regItem("taurethrim_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("TAURETHRIM").asTool(), LOTRItemGroups.COMBAT));

	public static RegistryObject<Item> GOLDEN_TAURETHRIM_HELMET = regItem("golden_taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.HEAD, "helmet"));
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_CHESTPLATE = regItem("golden_taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_LEGGINGS = regItem("golden_taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> GOLDEN_TAURETHRIM_BOOTS = regItem("golden_taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.FEET));

	//Angmar Equipment
	public static RegistryObject<Item> ANGMAR_HELMET = regItem("angmar_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.HEAD));
	public static RegistryObject<Item> ANGMAR_CHESTPLATE = regItem("angmar_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> ANGMAR_LEGGINGS = regItem("angmar_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> ANGMAR_BOOTS = regItem("angmar_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> ANGMAR_SWORD = regItem("angmar_sword",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("ANGMAR").asTool(), LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> ANGMAR_SPEAR = regItem("angmar_spear",() -> new SpearItem(LOTRMaterial.valueOf("ANGMAR")));
	public static RegistryObject<Item> ANGMAR_DAGGER = regItem("angmar_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("ANGMAR").asTool(), LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> MORGUL_VASSAL_HELMET = regItem("morgul_vassal_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL_VASSAL"), EquipmentSlotType.HEAD, "helmet"));
	public static RegistryObject<Item> MORGUL_VASSAL_CHESTPLATE = regItem("morgul_vassal_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL_VASSAL"), EquipmentSlotType.CHEST, "chestplate"));
	public static RegistryObject<Item> MORGUL_VASSAL_LEGGINGS = regItem("morgul_vassal_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL_VASSAL"), EquipmentSlotType.LEGS, "leggings"));
	public static RegistryObject<Item> MORGUL_VASSAL_BOOTS = regItem("morgul_vassal_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL_VASSAL"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> ANGMAR_WARG_ARMOR = regItem("angmar_warg_armor", () -> new ExtendedMountedArmorItem(11, "angmar", MountType.WARG, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));

	//Bone Equipment
	public static RegistryObject<Item> BONE_SWORD = regItem("bone_sword",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("EXT_BONE").asTool(), LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> BONE_SPEAR = regItem("bone_spear",() -> new SpearItem(LOTRMaterial.valueOf("EXT_BONE")));
	public static RegistryObject<Item> BONE_DAGGER = regItem("bone_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("EXT_BONE").asTool(), LOTRItemGroups.COMBAT));

	//Warg Equipment
	public static RegistryObject<Item> BLACK_WARG_HELMET = regItem("black_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
	public static RegistryObject<Item> BLACK_WARG_CHESTPLATE = regItem("black_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> BLACK_WARG_LEGGINGS = regItem("black_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> BLACK_WARG_BOOTS = regItem("black_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> BROWN_WARG_HELMET = regItem("warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
	public static RegistryObject<Item> BROWN_WARG_CHESTPLATE = regItem("warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> BROWN_WARG_LEGGINGS = regItem("warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> BROWN_WARG_BOOTS = regItem("warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> GRAY_WARG_HELMET = regItem("gray_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
	public static RegistryObject<Item> GRAY_WARG_CHESTPLATE = regItem("gray_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> GRAY_WARG_LEGGINGS = regItem("gray_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> GRAY_WARG_BOOTS = regItem("gray_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> SILVER_WARG_HELMET = regItem("silver_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
	public static RegistryObject<Item> SILVER_WARG_CHESTPLATE = regItem("silver_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> SILVER_WARG_LEGGINGS = regItem("silver_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> SILVER_WARG_BOOTS = regItem("silver_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> WHITE_WARG_HELMET = regItem("white_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
	public static RegistryObject<Item> WHITE_WARG_CHESTPLATE = regItem("white_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> WHITE_WARG_LEGGINGS = regItem("white_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> WHITE_WARG_BOOTS = regItem("white_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.FEET));

	//Dol Guldur Equipment
	public static RegistryObject<Item> DOL_GULDUR_HELMET = regItem("dol_guldur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.HEAD));
	public static RegistryObject<Item> DOL_GULDUR_CHESTPLATE = regItem("dol_guldur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> DOL_GULDUR_LEGGINGS = regItem("dol_guldur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> DOL_GULDUR_BOOTS = regItem("dol_guldur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> DOL_GULDUR_SWORD = regItem("dol_guldur_sword",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("DOL_GULDUR").asTool(), LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> DOL_GULDUR_SPEAR = regItem("dol_guldur_spear",() -> new SpearItem(LOTRMaterial.valueOf("DOL_GULDUR")));
	public static RegistryObject<Item> DOL_GULDUR_DAGGER = regItem("dol_guldur_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("DOL_GULDUR").asTool(), LOTRItemGroups.COMBAT));

	//Gundabad Uruk Equipment
	public static RegistryObject<Item> GUNDABAD_URUK_HELMET = regItem("gundabad_uruk_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.HEAD, "helmet"));
	public static RegistryObject<Item> GUNDABAD_URUK_CHESTPLATE = regItem("gundabad_uruk_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> GUNDABAD_URUK_LEGGINGS = regItem("gundabad_uruk_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> GUNDABAD_URUK_BOOTS = regItem("gundabad_uruk_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> GUNDABAD_SHIELD = regItem("gundabad_shield", () -> new LOTRShieldItem(LOTRMaterial.MORDOR));
	public static RegistryObject<Item> GUNDABAD_URUK_CLEAVER = regItem("gundabad_uruk_sword",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("GUNDABAD_URUK").asTool(), LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> GUNDABAD_URUK_SPEAR = regItem("gundabad_uruk_spear",() -> new SpearItem(LOTRMaterial.valueOf("GUNDABAD_URUK")));
	public static RegistryObject<Item> GUNDABAD_URUK_DAGGER = regItem("gundabad_uruk_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("GUNDABAD_URUK").asTool(), LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> GUNDABAD_URUK_BOW = regItem("gundabad_uruk_bow",() -> new ExtendedBowItem(ExtendedBowMaterial.GUNDABAD_URUK));
	public static RegistryObject<Item> GUNDABAD_WARG_ARMOR = regItem("gundabad_warg_armor", () -> new ExtendedMountedArmorItem(10, "gundabad", MountType.WARG, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));

	//Isengard
	public static RegistryObject<Item> ISENGARD_WARG_ARMOR = regItem("isengard_warg_armor", () -> new ExtendedMountedArmorItem(13, "isengard", MountType.WARG, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> URUK_SHIELD = regItem("uruk_shield", () -> new LOTRShieldItem(LOTRMaterial.URUK));
	public static RegistryObject<Item> URUK_CROSSBOW = regItem("uruk_crossbow", () -> new ExtendedCrossbowItem(ExtendedCrossbowMaterial.URUK));

	// Dunland
	public static RegistryObject<Item> DUNLENDING_SHIELD = regItem("dunlending_shield", () -> new LOTRShieldItem(LOTRMaterial.DUNLENDING));

	//Mordor
	public static RegistryObject<Item> MORDOR_WARG_ARMOR = regItem("mordor_warg_armor", () -> new ExtendedMountedArmorItem(11, "mordor", MountType.WARG, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));

	public static RegistryObject<Item> LONGBOW = regItem("longbow", () -> new ExtendedBowItem(ExtendedBowMaterial.GENERIC));
	public static RegistryObject<Item> BRONZE_CROSSBOW = regItem("bronze_crossbow", () -> new ExtendedCrossbowItem(ExtendedCrossbowMaterial.BRONZE));
	public static RegistryObject<Item> IRON_CROSSBOW = regItem("iron_crossbow", () -> new ExtendedCrossbowItem(ExtendedCrossbowMaterial.IRON));
	public static RegistryObject<Item> MITHRIL_CROSSBOW = regItem("mithril_crossbow", () -> new ExtendedCrossbowItem(ExtendedCrossbowMaterial.MITHRIL));

	//Ranger of the North equipment
	public static RegistryObject<Item> DUNEDAIN_SHIELD = regItem("dunedain_shield", () -> new LOTRShieldItem(LOTRMaterial.RANGER_NORTH));
	public static RegistryObject<Item> RANGER_BOW = regItem("ranger_bow",() -> new ExtendedBowItem(ExtendedBowMaterial.RANGER_NORTH));
	public static RegistryObject<Item> DUNEDAIN_BOW = regItem("dunedain_bow",() -> new ExtendedBowItem(ExtendedBowMaterial.DUNEDAIN_NORTH));

	//Bree equipment
	public static RegistryObject<Item> BREE_SHIELD = regItem("bree_shield", () -> new LOTRShieldItem(LOTRMaterial.valueOf("BREE")));

	//Arnorian equipment
	public static RegistryObject<Item> ARNOR_SHIELD = regItem("arnor_shield", () -> new LOTRShieldItem(LOTRMaterial.ARNOR));

	//Blue Mountains equipment
	public static RegistryObject<Item> DWARVEN_SHIELD = regItem("dwarven_shield", () -> new LOTRShieldItem(LOTRMaterial.DWARVEN));
	public static RegistryObject<Item> BLUE_DWARVEN_SHIELD = regItem("blue_dwarven_shield", () -> new LOTRShieldItem(LOTRMaterial.BLUE_DWARVEN));

	public static RegistryObject<Item> LEATHER_BOAR_ARMOR = regItem("leather_boar_armor", () -> new ExtendedMountedDyeableArmorItem(8, "leather", MountType.BOAR, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> DWARVEN_BOAR_ARMOR = regItem("dwarven_boar_armor", () -> new ExtendedMountedArmorItem(13, "dwarven", MountType.BOAR, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));
	public static RegistryObject<Item> BLUE_DWARVEN_BOAR_ARMOR = regItem("blue_dwarven_boar_armor", () -> new ExtendedMountedArmorItem(13, "blue_dwarven", MountType.BOAR, new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT)));

	// High Elven Equipment
	public static RegistryObject<Item> RIVENDELL_SHIELD = regItem("rivendell_shield", () -> new LOTRShieldItem(LOTRMaterial.RIVENDELL));
	public static RegistryObject<Item> RIVENDELL_BOW = regItem("rivendell_bow", () -> new ExtendedBowItem(ExtendedBowMaterial.RIVENDELL));
	public static RegistryObject<Item> IMLADRIS_GUARD_HELMET = regItem("imladris_guard_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("IMLADRIS_GUARD"), EquipmentSlotType.HEAD));
	public static RegistryObject<Item> IMLADRIS_GUARD_CHESTPLATE = regItem("imladris_guard_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("IMLADRIS_GUARD"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> IMLADRIS_GUARD_LEGGINGS = regItem("imladris_guard_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("IMLADRIS_GUARD"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> IMLADRIS_GUARD_BOOTS = regItem("imladris_guard_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("IMLADRIS_GUARD"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> IMLADRIS_GUARD_SHIELD = regItem("imladris_guard_shield", () -> new LOTRShieldItem(LOTRMaterial.valueOf("IMLADRIS_GUARD")));
	public static RegistryObject<Item> EREGION_HELMET = regItem("eregion_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("EREGION"), EquipmentSlotType.HEAD));
	public static RegistryObject<Item> EREGION_CHESTPLATE = regItem("eregion_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("EREGION"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> EREGION_LEGGINGS = regItem("eregion_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("EREGION"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> EREGION_BOOTS = regItem("eregion_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("EREGION"), EquipmentSlotType.FEET));
	public static RegistryObject<Item> EREGION_SHIELD = regItem("eregion_shield", () -> new LOTRShieldItem(LOTRMaterial.valueOf("EREGION")));
	public static RegistryObject<Item> LINDON_SHIELD = regItem("lindon_shield", () -> new LOTRShieldItem(LOTRMaterial.LINDON));
	public static RegistryObject<Item> LINDON_BOW = regItem("lindon_bow", () -> new ExtendedBowItem(ExtendedBowMaterial.LINDON));
	public static RegistryObject<Item> GALVORN_HELMET = regItem("galvorn_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GALVORN"), EquipmentSlotType.HEAD, "helmet"));
	public static RegistryObject<Item> GALVORN_CHESTPLATE = regItem("galvorn_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GALVORN"), EquipmentSlotType.CHEST));
	public static RegistryObject<Item> GALVORN_LEGGINGS = regItem("galvorn_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GALVORN"), EquipmentSlotType.LEGS));
	public static RegistryObject<Item> GALVORN_BOOTS = regItem("galvorn_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GALVORN"), EquipmentSlotType.FEET));
	// misc equipment
	public static RegistryObject<Item> LEATHER_HAT = regItem("leather_hat", ExtendedLeatherHatItem::new);
	public static RegistryObject<Item> BARROW_BLADE = regItem("barrow_blade", ()-> new ExtendedBarrowBlade(LOTRItemGroups.COMBAT));
	public static RegistryObject<Item> COMMANDHORN = regItem("horn_of_command", ExtendedCommandHornItem::new);

	//########################################################################UTIL##########################################################
	public static RegistryObject<Item> ORC_STEEL_DOOR = regTallBlockItem(ExtendedBlocks.ORC_STEEL_DOOR, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> ORC_STEEL_TRAPDOOR = regBlockItem(ExtendedBlocks.ORC_STEEL_TRAPDOOR, LOTRItemGroups.UTIL);
	//Crafting Tables
	public static RegistryObject<Item> DOL_GULDUR_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> NORTHERN_ORC_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.NORTHERN_ORC_CRAFTING_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> HALF_TROLL_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> MORWAITH_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.MORWAITH_CRAFTING_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> RHUNIC_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.RHUNIC_CRAFTING_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> TAURETHRIM_CRAFTING_TABLE = regBlockItem(ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> CONQUEST_TABLE = regBlockItem(ExtendedBlocks.CONQUEST_TABLE, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> STONE_GRINDER = regBlockItem(ExtendedBlocks.STONE_GRINDER, LOTRItemGroups.UTIL);
	public static RegistryObject<Item> CRUCIBLE = regBlockItem(ExtendedBlocks.CRUCIBLE, LOTRItemGroups.UTIL);

	//########################################################################MISC##########################################################

	public static RegistryObject<Item> HOBBIT_MARRIAGE_RING =  regItem("hobbit_marriage_ring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DWARF_MARRIAGE_RING =  regItem("dwarven_marriage_ring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> SILVER_COIN_ONE = regItem("silver_coin_one", () -> new ExtendedCoinItem(new Properties().tab(LOTRItemGroups.MISC), ExtendedCoinType.ONE));
	public static RegistryObject<Item> SILVER_COIN_TEN = regItem("silver_coin_ten", () -> new ExtendedCoinItem(new Properties().tab(LOTRItemGroups.MISC), ExtendedCoinType.TEN));
	public static RegistryObject<Item> SILVER_COIN_HUNDRED = regItem("silver_coin_hundred", () -> new ExtendedCoinItem(new Properties().tab(LOTRItemGroups.MISC), ExtendedCoinType.HUNDRED));
	public static RegistryObject<Item> BOWSTRING = regItem("bowstring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> CONKER =  regItem("conker", () -> new ExtendedThrowableConkerItem(new Properties().tab(LOTRItemGroups.MISC).stacksTo(16)));
	public static RegistryObject<Item> BLUE_MOUNTAINS_BANNER_PATTERN = regBannerPaternItem("blue_mountains_banner_pattern", ExtendedBannerPatterns.blue_mountains, () -> new BannerPatternItem(ExtendedBannerPatterns.blue_mountains, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DURINS_FOLK_BANNER_PATTERN = regBannerPaternItem("durin_banner_pattern", ExtendedBannerPatterns.durin, () -> new BannerPatternItem(ExtendedBannerPatterns.durin, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DUNEDAIN_BANNER_PATTERN = regBannerPaternItem("dunedain_banner_pattern", ExtendedBannerPatterns.dunedain, () -> new BannerPatternItem(ExtendedBannerPatterns.dunedain, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> GUNDABAD_ORC_BANNER_PATTERN = regBannerPaternItem("gundabad_orc_banner_pattern", ExtendedBannerPatterns.gundabad_orc, () -> new BannerPatternItem(ExtendedBannerPatterns.gundabad_orc, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> HOBBIT_BANNER_PATTERN = regBannerPaternItem("hobbit_banner_pattern", ExtendedBannerPatterns.hobbit, () -> new BannerPatternItem(ExtendedBannerPatterns.hobbit, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> BREE_BANNER_PATTERN = regBannerPaternItem("bree_banner_pattern", ExtendedBannerPatterns.bree, () -> new BannerPatternItem(ExtendedBannerPatterns.bree, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> ANGMAR_BANNER_PATTERN = regBannerPaternItem("angmar_banner_pattern", ExtendedBannerPatterns.angmar, () -> new BannerPatternItem(ExtendedBannerPatterns.angmar, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DALE_BANNER_PATTERN = regBannerPaternItem("dale_banner_pattern", ExtendedBannerPatterns.dale, () -> new BannerPatternItem(ExtendedBannerPatterns.dale, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DOL_AMROTH_BANNER_PATTERN = regBannerPaternItem("dol_amroth_banner_pattern", ExtendedBannerPatterns.dol_amroth, () -> new BannerPatternItem(ExtendedBannerPatterns.dol_amroth, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DOL_GULDUR_BANNER_PATTERN = regBannerPaternItem("dol_guldur_banner_pattern", ExtendedBannerPatterns.dol_guldur, () -> new BannerPatternItem(ExtendedBannerPatterns.dol_guldur, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DORWINION_BANNER_PATTERN = regBannerPaternItem("dorwinion_banner_pattern", ExtendedBannerPatterns.dorwinion, () -> new BannerPatternItem(ExtendedBannerPatterns.dorwinion, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> DUNLAND_BANNER_PATTERN = regBannerPaternItem("dunland_banner_pattern", ExtendedBannerPatterns.dunland, () -> new BannerPatternItem(ExtendedBannerPatterns.dunland, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> GONDOR_BANNER_PATTERN = regBannerPaternItem("gondor_banner_pattern", ExtendedBannerPatterns.gondor, () -> new BannerPatternItem(ExtendedBannerPatterns.gondor, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> ISENGARD_BANNER_PATTERN = regBannerPaternItem("isengard_banner_pattern", ExtendedBannerPatterns.isengard, () -> new BannerPatternItem(ExtendedBannerPatterns.isengard, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> LINDON_BANNER_PATTERN = regBannerPaternItem("lindon_banner_pattern", ExtendedBannerPatterns.lindon, () -> new BannerPatternItem(ExtendedBannerPatterns.lindon, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> LOTHLORIEN_BANNER_PATTERN = regBannerPaternItem("lothlorien_banner_pattern", ExtendedBannerPatterns.lothlorien, () -> new BannerPatternItem(ExtendedBannerPatterns.lothlorien, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> MORDOR_BANNER_PATTERN = regBannerPaternItem("mordor_banner_pattern", ExtendedBannerPatterns.mordor, () -> new BannerPatternItem(ExtendedBannerPatterns.mordor, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> RIVENDELL_BANNER_PATTERN = regBannerPaternItem("rivendell_banner_pattern", ExtendedBannerPatterns.rivendell, () -> new BannerPatternItem(ExtendedBannerPatterns.rivendell, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> ROHAN_BANNER_PATTERN = regBannerPaternItem("rohan_banner_pattern", ExtendedBannerPatterns.rohan, () -> new BannerPatternItem(ExtendedBannerPatterns.rohan, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
	public static RegistryObject<Item> WOODLAND_REALM_BANNER_PATTERN = regBannerPaternItem("woodland_realm_banner_pattern", ExtendedBannerPatterns.woodland_realm, () -> new BannerPatternItem(ExtendedBannerPatterns.woodland_realm, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));

	public static RegistryObject<Item> ENTITY_SPAWNER = regItem("structure_entity_spawner", () -> new ExtendedEntitySpawnerItem(new Properties().tab(LOTRItemGroups.MISC).fireResistant()));
	public static RegistryObject<Item> ROPE = regBlockItemProps(ExtendedBlocks.ROPE, LOTRItemGroups.MISC, props -> props.stacksTo(64));
	public static RegistryObject<Item> MODIFIER_SCROLL = regItem("modifier_scroll", () -> new ExtendedModifierScrollItem(new Properties().tab(LOTRItemGroups.MISC).rarity(Rarity.UNCOMMON).stacksTo(1)));
	public static RegistryObject<Item> COMBAT_TOME = regItem("combat_tome", () -> new ExtendedModifierReforgeTomeItem(new Properties().tab(LOTRItemGroups.MISC).stacksTo(1), "combat", ExtendedModifierType.TOOL_SPEED, ExtendedModifierType.TOOL_SILK));
	public static RegistryObject<Item> ARTISAN_TOME = regItem("artisan_tome", () -> new ExtendedModifierReforgeTomeItem(new Properties().tab(LOTRItemGroups.MISC).stacksTo(1), "artisan", ExtendedModifierType.MELEE_KNOCKBACK, ExtendedModifierType.MELEE_DAMAGE, ExtendedModifierType.MELEE_REACH, ExtendedModifierType.MELEE_SPEED));
	public static RegistryObject<Item> SMITH_TOME = regItem("smith_tome", () -> new ExtendedModifierRepairTomeItem(new Properties().tab(LOTRItemGroups.MISC).stacksTo(1), "ignore", "smith"));

	//########################################################################LORE##########################################################
	public static RegistryObject<Item> SHARDS_OF_NARSIL = regBlockItemProps(ExtendedBlocks.SHARDS_OF_NARSIL, LOTRItemGroups.STORY, props -> props.rarity(Rarity.RARE));
	public static RegistryObject<Item> ANDURIL = regItem("anduril",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("ANDURIL").asTool(), new Properties().tab(LOTRItemGroups.STORY).rarity(Rarity.RARE)));
	public static RegistryObject<Item> GLAMDRING = regItem("glamdring",() -> new ExtendedSwordItem(LOTRMaterial.valueOf("GLAMDRING").asTool(), new Properties().tab(LOTRItemGroups.STORY).rarity(Rarity.RARE)));
	public static RegistryObject<Item> STAFF_GANDALF_GRAY = regItem("staff_gandalf_the_gray",() -> new ExtendedStaffGandalfGrey(LOTRMaterial.valueOf("GANDALF_GRAY").asTool(), new Properties().tab(LOTRItemGroups.STORY).rarity(Rarity.RARE)));
	public static RegistryObject<Item> STAFF_GANDALF_WHITE = regItem("staff_gandalf_the_white",() -> new ExtendedStaffGandalfWhite(LOTRMaterial.valueOf("GANDALF_WHITE").asTool(), new Properties().tab(LOTRItemGroups.STORY).rarity(Rarity.RARE)));

	// HIDDEN
	public static RegistryObject<Item> GRASS_VOID = regHiddenItemB(ExtendedBlocks.GRASS_VOID);
	public static RegistryObject<Item> HOIST = regHiddenItemB(ExtendedBlocks.HOIST);

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}

	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {
		RegistryObject<Item> item = ITEMS.register(name, sup);
		LOTRItems.putItemInCreativeTabOrder(item.getId());
		return item;
	}
	private static RegistryObject<Item> regBannerPaternItem(String name, BannerPattern bannerPattern, Supplier<? extends Item> sup)  {
		RegistryObject<Item> item = regItem(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.bannerPatternItemNameHolders.put(name, bannerPattern.getFilename());
		}
		return item;
	}
	private static RegistryObject<Item> regBoatItem(String name, ExtendedBoatEntity.Type pType, ItemGroup group) {
		RegistryObject<Item> item = regItem(name, () -> new ExtendedBoatItem(pType, new Properties().stacksTo(1).tab(group)));
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.newWoodTypesHolder.add(pType.getName());
		}
		return item;
	}
	private static RegistryObject<Item> regGenericBlockItem(RegistryObject<Block> blockSup, Supplier<? extends BlockItem> sup) {
		return regItem(blockSup.getId().getPath(), sup);
	}
	private static RegistryObject<Item> regBlockItemProps(RegistryObject<Block> blockSup, ItemGroup group, Consumer<Properties> props) {
		Properties properties =  new Properties().tab(group);
		props.accept(properties);
		return regGenericBlockItem(blockSup, () -> new BlockItem(blockSup.get(), properties));
	}
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {
		return regBlockItemProps(blockSup, group, props -> {});
	}
	private static RegistryObject<Item> regCakeItem(RegistryObject<Block> blockSup, ItemGroup group) {
		return regBlockItemProps(blockSup, group, props -> props.stacksTo(1));
	}
	private static RegistryObject<Item> regHiddenItemB(RegistryObject<Block> blockSup) {
		return regGenericBlockItem(blockSup, () -> new BlockItem(blockSup.get(), new Properties()));
	}
	private static RegistryObject<Item> regBedItem(RegistryObject<Block> blockSup, ItemGroup group) {
		return regGenericBlockItem(blockSup, () -> new ExtendedBedItem(blockSup.get(), new Properties().stacksTo(1).tab(group)));
	}
	private static RegistryObject<Item> regBedItem(RegistryObject<Block> blockSup, ItemGroup group, Supplier<Callable<ItemStackTileEntityRenderer>> ister) {
		return regGenericBlockItem(blockSup, () -> new ExtendedBedItem(blockSup.get(), new Properties().stacksTo(1).tab(group).setISTER(ister)));
	}
	private static RegistryObject<Item> regFallenLeavesItem(RegistryObject<Block> blockSup, ItemGroup group) {
		return regGenericBlockItem(blockSup, () -> new FallenLeavesItem(blockSup.get(), new Properties().tab(group)));
	}
	private static RegistryObject<Item> regTallBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {
		return regGenericBlockItem(blockSup, () -> new TallBlockItem(blockSup.get(), new Properties().tab(group)));
	}
	private static RegistryObject<Item> regSignItem(RegistryObject<Block> standingSignSup, RegistryObject<Block> wallSignSup) {
		return regGenericBlockItem(standingSignSup, () -> new SignItem(new Properties().tab(LOTRItemGroups.DECO).stacksTo(16), standingSignSup.get(), wallSignSup.get()));
	}
	private static RegistryObject<Item> regWallOrFloorItem(RegistryObject<Block> standingSignSup, RegistryObject<Block> wallSignSup, ItemGroup group) {
		return regGenericBlockItem(standingSignSup, () -> new WallOrFloorItem(standingSignSup.get(), wallSignSup.get(), new Properties().tab(group)));
	}
	private static RegistryObject<Item> regEmptyVesselBlockItem(RegistryObject<Block> blockSup, VesselType vesselType) {
		return regGenericBlockItem(blockSup, () -> new EmptyVesselBlockItem(blockSup.get(), vesselType));
	}

	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> blackFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedBlackFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> furBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> grayFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedGrayFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> silverFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedSilverFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> whiteFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedWhiteFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> dwarvenBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedDwarvenBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> orcBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedOrcBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> strawBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedStrawBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> highElvenBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedHighElvenBedTileEntity::new);
	}
}
