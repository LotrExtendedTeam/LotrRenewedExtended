package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lotr.common.entity.item.LOTRBoatEntity;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRMaterial;
import lotr.common.item.ExtendedDaggerItem;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRBlockItem;
import lotr.common.item.LOTRBoatItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import lotr.common.item.SpearItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.render.item.ItemRenderHelper;
import com.github.maximuslotro.lotrrextended.common.enums.CoinType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedFoods;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBedItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCopperIngotItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedGuldurilItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedPoisonBottleItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSilverCoin;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableConkerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedUseItemAwardStatItem;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.stats.ExtendedStats;
import com.google.common.base.Function;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.item.Item.Properties;

@Mixin(LOTRItems.class)
public class MixinLOTRItems {

	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}
	private static RegistryObject<Item> regItemB(String name, Supplier<? extends Item> sup)  {
		RegistryObject<Item> item = regItem(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(name, item);
		}
		return item;
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regCakeBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	private static RegistryObject<Item> regCakeBlockItemD(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regCakeBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group, Consumer<Properties> extraPropsSetter) {return null;}
	private static RegistryObject<Item> regBlockItemStackRenderer(RegistryObject<Block> blockSup, ItemGroup group, Supplier<Callable<ItemStackTileEntityRenderer>> ister) {
		RegistryObject<Item> item = regBlockItem(blockSup, group, (props) -> {
			props.setISTER(ister);
		});
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	private static RegistryObject<Item> regBlockItemB(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	private static RegistryObject<Item> regHiddenBlockItemB(RegistryObject<Block> blockSup) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> {
			return new LOTRBlockItem(b, new Properties());
		});
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	private static RegistryObject<Item> regArbitraryBlockItemD(RegistryObject<Block> blockSup, Function<Block, ? extends BlockItem> itemMaker) {
		String name = blockSup.getId().getPath();
		return regItem(name, () -> {
			return (BlockItem) itemMaker.apply(blockSup.get());
		});
	}

	private static RegistryObject<Item> regBedBlockItemD(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> {
			return new ExtendedBedItem(b, (new Properties()).stacksTo(1).tab(group));
		});
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	private static RegistryObject<Item> regBedBlockItemD(RegistryObject<Block> blockSup, ItemGroup group, Supplier<Callable<ItemStackTileEntityRenderer>> ister) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> {
			return new ExtendedBedItem(b, new Properties().stacksTo(1).tab(group).setISTER(ister));
		});
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regTallBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	private static RegistryObject<Item> regTallBlockItemB(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regTallBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regSignBlockItem(RegistryObject<Block> standingSignSup, RegistryObject<Block> wallSignSup) {return null;}
	private static RegistryObject<Item> regSignBlockItemB(RegistryObject<Block> standingSignSup, RegistryObject<Block> wallSignSup) {
		RegistryObject<Item> item = regSignBlockItem(standingSignSup, wallSignSup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	//Moved to overwrite method
	//@Shadow(remap=false)
	//private static RegistryObject<Item> regMaterialItem(String name) {return null;}
	private static RegistryObject<Item> regMaterialItemB(String name)  {
		RegistryObject<Item> item = regMaterialItem(name);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(name, item);
		}
		return item;
	}

	/**
	 * @author maximuslotro
	 * @reason Need to change registration objects for some materials while also being call-able
	 * */
	@Overwrite(remap = false)
	private static RegistryObject<Item> regMaterialItem(String name) {
		if(name.equals("gulduril")) {
			return regItem(name, () -> new ExtendedGuldurilItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		if(name.equals("copper_ingot")) {
			return regItem(name, () -> new ExtendedCopperIngotItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		return regItem(name, () -> new Item((new Properties()).tab(LOTRItemGroups.MATERIALS)));
	}

	@Shadow(remap=false)
	private static RegistryObject<Item> regWallOrFloorBlockItem(RegistryObject <Block>wallBlockSup, RegistryObject<Block> floorBlockSup, ItemGroup group)  {return null;}
	private static RegistryObject<Item> regWallOrFloorBlockItemB(RegistryObject <Block>wallBlockSup, RegistryObject<Block> floorBlockSup, ItemGroup group)  {
		RegistryObject<Item> item = regWallOrFloorBlockItem(wallBlockSup, floorBlockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(wallBlockSup.getId().getPath(), item);
		}
		return item;
	}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		//###########################################################BLOCKS################################################################
		ExtendedItems.CHESTNUT_LOG = regBlockItemB(ExtendedBlocks.CHESTNUT_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_WOOD = regBlockItemB(ExtendedBlocks.CHESTNUT_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_PLANKS = regBlockItemB(ExtendedBlocks.CHESTNUT_PLANKS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_LEAVES = regBlockItemB(ExtendedBlocks.CHESTNUT_LEAVES, LOTRItemGroups.DECO);
		ExtendedItems.CHESTNUT_LEAVES_FRUIT = regBlockItemB(ExtendedBlocks.CHESTNUT_LEAVES_FRUIT, LOTRItemGroups.DECO);
		ExtendedItems.CHESTNUT_LEAVES_FLOWERS = regBlockItemB(ExtendedBlocks.CHESTNUT_LEAVES_FLOWERS, LOTRItemGroups.DECO);
		ExtendedItems.CHESTNUT_SAPLING = regBlockItemB(ExtendedBlocks.CHESTNUT_SAPLING, LOTRItemGroups.DECO);
		ExtendedItems.CHESTNUT_SLAB = regBlockItemB(ExtendedBlocks.CHESTNUT_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_STAIRS = regBlockItemB(ExtendedBlocks.CHESTNUT_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_FENCE = regBlockItemB(ExtendedBlocks.CHESTNUT_FENCE, LOTRItemGroups.DECO);
		ExtendedItems.CHESTNUT_FENCE_GATE = regBlockItemB(ExtendedBlocks.CHESTNUT_FENCE_GATE, LOTRItemGroups.UTIL);
		ExtendedItems.CHESTNUT_DOOR = regTallBlockItemB(ExtendedBlocks.CHESTNUT_DOOR, LOTRItemGroups.UTIL);
		ExtendedItems.CHESTNUT_TRAPDOOR = regBlockItemB(ExtendedBlocks.CHESTNUT_TRAPDOOR, LOTRItemGroups.UTIL);
		ExtendedItems.CHESTNUT_PRESSURE_PLATE = regBlockItemB(ExtendedBlocks.CHESTNUT_PRESSURE_PLATE, LOTRItemGroups.UTIL);
		ExtendedItems.CHESTNUT_BUTTON = regBlockItemB(ExtendedBlocks.CHESTNUT_BUTTON, LOTRItemGroups.UTIL);
		ExtendedItems.STRIPPED_CHESTNUT_LOG = regBlockItemB(ExtendedBlocks.STRIPPED_CHESTNUT_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_WOOD = regBlockItemB(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_BEAM = regBlockItemB(ExtendedBlocks.CHESTNUT_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_SIGN = regSignBlockItemB(ExtendedBlocks.CHESTNUT_SIGN, ExtendedBlocks.CHESTNUT_WALL_SIGN);
		ExtendedItems.CHESTNUT_BOAT = regItemB("chestnut_boat", () -> new LOTRBoatItem(LOTRBoatEntity.ModBoatType.valueOf("CHESTNUT")));
		ExtendedItems.CHESTNUT_LOG_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_WOOD_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_WOOD_STAIRS = regBlockItem(ExtendedBlocks.CHESTNUT_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_BRANCH = regBlockItem(ExtendedBlocks.CHESTNUT_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_WOOD_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_WOOD_STAIRS = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_BRANCH = regBlockItem(ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_BEAM_SLAB = regBlockItem(ExtendedBlocks.CHESTNUT_BEAM_SLAB, LOTRItemGroups.BLOCKS);

		ExtendedItems.BANANA_LOG = regBlockItemB(ExtendedBlocks.BANANA_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_PLANKS = regBlockItemB(ExtendedBlocks.BANANA_PLANKS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_LEAVES = regBlockItemB(ExtendedBlocks.BANANA_LEAVES, LOTRItemGroups.DECO);
		ExtendedItems.BANANA_SAPLING = regBlockItemB(ExtendedBlocks.BANANA_SAPLING, LOTRItemGroups.DECO);
		ExtendedItems.BANANA_SLAB = regBlockItemB(ExtendedBlocks.BANANA_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_STAIRS = regBlockItemB(ExtendedBlocks.BANANA_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_FENCE = regBlockItemB(ExtendedBlocks.BANANA_FENCE, LOTRItemGroups.DECO);
		ExtendedItems.BANANA_FENCE_GATE = regBlockItemB(ExtendedBlocks.BANANA_FENCE_GATE, LOTRItemGroups.UTIL);
		ExtendedItems.BANANA_DOOR = regTallBlockItemB(ExtendedBlocks.BANANA_DOOR, LOTRItemGroups.UTIL);
		ExtendedItems.BANANA_TRAPDOOR = regBlockItemB(ExtendedBlocks.BANANA_TRAPDOOR, LOTRItemGroups.UTIL);
		ExtendedItems.BANANA_PRESSURE_PLATE = regBlockItemB(ExtendedBlocks.BANANA_PRESSURE_PLATE, LOTRItemGroups.UTIL);
		ExtendedItems.BANANA_BUTTON = regBlockItemB(ExtendedBlocks.BANANA_BUTTON, LOTRItemGroups.UTIL);
		ExtendedItems.STRIPPED_BANANA_LOG = regBlockItemB(ExtendedBlocks.STRIPPED_BANANA_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_BEAM = regBlockItemB(ExtendedBlocks.BANANA_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_SIGN = regSignBlockItemB(ExtendedBlocks.BANANA_SIGN, ExtendedBlocks.BANANA_WALL_SIGN);
		ExtendedItems.BANANA_BOAT = regItemB("banana_boat", () -> new LOTRBoatItem(LOTRBoatEntity.ModBoatType.valueOf("BANANA")));
		ExtendedItems.BANANA_LOG_SLAB = regBlockItem(ExtendedBlocks.BANANA_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_BANANA_LOG_SLAB = regBlockItem(ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_BEAM_SLAB = regBlockItem(ExtendedBlocks.BANANA_BEAM_SLAB, LOTRItemGroups.BLOCKS);

		//ORE/GEM STORAGE
		ExtendedItems.GALVORN_BLOCK = regBlockItemB(ExtendedBlocks.GALVORN_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_URUK_STEEL_BLOCK = regBlockItemB(ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GILDED_IRON_BLOCK = regBlockItemB(ExtendedBlocks.GILDED_IRON_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MORGUL_STEEL_BLOCK = regBlockItemB(ExtendedBlocks.MORGUL_STEEL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.TOPAZ_BLOCK = regBlockItemB(ExtendedBlocks.TOPAZ_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_BLOCK = regBlockItemB(ExtendedBlocks.AMETHYST_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_BLOCK = regBlockItemB(ExtendedBlocks.SAPPHIRE_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_BLOCK = regBlockItemB(ExtendedBlocks.RUBY_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_BLOCK = regBlockItemB(ExtendedBlocks.AMBER_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_BLOCK = regBlockItemB(ExtendedBlocks.DIAMOND_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_BLOCK = regBlockItemB(ExtendedBlocks.OPAL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_BLOCK = regBlockItemB(ExtendedBlocks.EMERALD_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.PEARL_BLOCK = regBlockItemB(ExtendedBlocks.PEARL_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CORAL_BLOCK = regBlockItemB(ExtendedBlocks.CORAL_BLOCK, LOTRItemGroups.BLOCKS);

		//ORE
		ExtendedItems.TOPAZ_ORE = regBlockItemB(ExtendedBlocks.TOPAZ_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMETHYST_ORE = regBlockItemB(ExtendedBlocks.AMETHYST_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.SAPPHIRE_ORE = regBlockItemB(ExtendedBlocks.SAPPHIRE_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.RUBY_ORE = regBlockItemB(ExtendedBlocks.RUBY_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.AMBER_ORE = regBlockItemB(ExtendedBlocks.AMBER_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIAMOND_ORE = regBlockItemB(ExtendedBlocks.DIAMOND_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.OPAL_ORE = regBlockItemB(ExtendedBlocks.OPAL_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.EMERALD_ORE = regBlockItemB(ExtendedBlocks.EMERALD_ORE, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_ELF = regBlockItemB(ExtendedBlocks.REMAINS_ELF, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_MAN = regBlockItemB(ExtendedBlocks.REMAINS_MAN, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_ORC = regBlockItemB(ExtendedBlocks.REMAINS_ORC, LOTRItemGroups.BLOCKS);
		ExtendedItems.REMAINS_DWARF = regBlockItemB(ExtendedBlocks.REMAINS_DWARF, LOTRItemGroups.BLOCKS);

		ExtendedItems.BARREN_DIRT = regBlockItemB(ExtendedBlocks.BARREN_DIRT, LOTRItemGroups.BLOCKS);
		ExtendedItems.BARREN_DIRT_SLAB = regBlockItemB(ExtendedBlocks.BARREN_DIRT_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.COARSE_DIRT_SLAB = regBlockItemB(ExtendedBlocks.COARSE_DIRT_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.DIRT_SLAB = regBlockItemB(ExtendedBlocks.DIRT_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GRASS_BLOCK_SLAB = regBlockItemB(ExtendedBlocks.GRASS_BLOCK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GRASS_PATH_SLAB = regBlockItemB(ExtendedBlocks.GRASS_PATH_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.PODZOL_SLAB = regBlockItemB(ExtendedBlocks.PODZOL_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MYCELIUM_SLAB = regBlockItemB(ExtendedBlocks.MYCELIUM_SLAB, LOTRItemGroups.BLOCKS);
		//scorched stuff
		ExtendedItems.SCORCHED_COBBLESTONE = regBlockItemB(ExtendedBlocks.SCORCHED_COBBLESTONE, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_COBBLESTONE_SLAB = regBlockItemB(ExtendedBlocks.SCORCHED_COBBLESTONE_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_COBBLESTONE_STAIRS = regBlockItemB(ExtendedBlocks.SCORCHED_COBBLESTONE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_COBBLESTONE_WALL = regBlockItemB(ExtendedBlocks.SCORCHED_COBBLESTONE_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_STONE = regBlockItemB(ExtendedBlocks.SCORCHED_STONE, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_STONE_SLAB = regBlockItemB(ExtendedBlocks.SCORCHED_STONE_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_STONE_STAIRS = regBlockItemB(ExtendedBlocks.SCORCHED_STONE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_STONE_WALL = regBlockItemB(ExtendedBlocks.SCORCHED_STONE_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_BRICK = regBlockItemB(ExtendedBlocks.SCORCHED_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_BRICK_SLAB = regBlockItemB(ExtendedBlocks.SCORCHED_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.SCORCHED_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SCORCHED_BRICK_WALL = regBlockItemB(ExtendedBlocks.SCORCHED_BRICK_WALL, LOTRItemGroups.BLOCKS);
		//Mud
		ExtendedItems.MUD = regBlockItemB(ExtendedBlocks.MUD, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_SLAB = regBlockItemB(ExtendedBlocks.MUD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_STAIRS = regBlockItemB(ExtendedBlocks.MUD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_GRASS = regBlockItemB(ExtendedBlocks.MUD_GRASS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_BRICK = regBlockItemB(ExtendedBlocks.MUD_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MUD_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MUD_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MUD_BRICK_WALL = regBlockItemB(ExtendedBlocks.MUD_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_MUD_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_MUD_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_MUD_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_MUD_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_MUD_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_MUD_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_MUD_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_MUD_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_MUD_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_MUD_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_MUD_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_MUD_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_MUD_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_MUD_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_MUD_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_MUD_BRICK_WALL, LOTRItemGroups.BLOCKS);
		//############################################################DECORATION##############################################################
		ExtendedItems.STRAW_BED = regBedBlockItemD(ExtendedBlocks.STRAW_BED, LOTRItemGroups.DECO, () -> strawBedRenderer());
		ExtendedItems.ORC_BED = regBedBlockItemD(ExtendedBlocks.ORC_BED, LOTRItemGroups.DECO, () -> orcBedRenderer());
		ExtendedItems.BLACK_FUR_BED = regBedBlockItemD(ExtendedBlocks.BLACK_FUR_BED, LOTRItemGroups.DECO, () -> blackFurBedRenderer());
		ExtendedItems.FUR_BED = regBedBlockItemD(ExtendedBlocks.FUR_BED, LOTRItemGroups.DECO, () -> furBedRenderer());
		ExtendedItems.GRAY_FUR_BED = regBedBlockItemD(ExtendedBlocks.GRAY_FUR_BED, LOTRItemGroups.DECO, () -> grayFurBedRenderer());
		ExtendedItems.SILVER_FUR_BED = regBedBlockItemD(ExtendedBlocks.SILVER_FUR_BED, LOTRItemGroups.DECO, () -> silverFurBedRenderer());
		ExtendedItems.WHITE_FUR_BED = regBedBlockItemD(ExtendedBlocks.WHITE_FUR_BED, LOTRItemGroups.DECO, () -> whiteFurBedRenderer());
		ExtendedItems.BEDROLL = regBedBlockItemD(ExtendedBlocks.BEDROLL, LOTRItemGroups.DECO);
		//Bushes
		ExtendedItems.RASPBERRY_BUSH = regBlockItemB(ExtendedBlocks.RASPBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.BLUEBERRY_BUSH = regBlockItemB(ExtendedBlocks.BLUEBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.BLACKBERRY_BUSH = regBlockItemB(ExtendedBlocks.BLACKBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.CRANBERRY_BUSH = regBlockItemB(ExtendedBlocks.CRANBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.ELDERBERRY_BUSH = regBlockItemB(ExtendedBlocks.ELDERBERRY_BUSH, LOTRItemGroups.DECO);
		ExtendedItems.WILDBERRY_BUSH = regBlockItemB(ExtendedBlocks.WILDBERRY_BUSH, LOTRItemGroups.DECO);
		
		ExtendedItems.WHITE_PACKED_FUR = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_PACKED_FUR = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_WALL, LOTRItemGroups.DECO);
		
		ExtendedItems.WHITE_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.GRAY_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.BLACK_FUR_BUNDLE, LOTRItemGroups.DECO);
		
		ExtendedItems.MORGUL_LANTERN = regBlockItemB(ExtendedBlocks.MORGUL_LANTERN, LOTRItemGroups.DECO);
		
		ExtendedItems.MUD_PATH = regBlockItemB(ExtendedBlocks.MUD_PATH, LOTRItemGroups.DECO);
		ExtendedItems.MUD_PATH_SLAB = regBlockItemB(ExtendedBlocks.MUD_PATH_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.MUD_PATH_STAIRS = regBlockItemB(ExtendedBlocks.MUD_PATH_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.MUD_FARMLAND = regBlockItemB(ExtendedBlocks.MUD_FARMLAND, LOTRItemGroups.DECO);

		//ExtendedItems.STONE_CHEST = regBlockItemStackRenderer(ExtendedBlocks.STONE_CHEST, LOTRItemGroups.DECO, ()-> BlockRendererSetup.createStoneChestRenderer());
		ExtendedItems.STONE_CHEST = regBlockItemB(ExtendedBlocks.STONE_CHEST, LOTRItemGroups.DECO);
		ExtendedItems.REED_BASKET = regBlockItemB(ExtendedBlocks.REED_BASKET, LOTRItemGroups.DECO);
		ExtendedItems.WEAPON_RACK = regBlockItemB(ExtendedBlocks.WEAPON_RACK, LOTRItemGroups.DECO);
		//########### BLOCK TYPES ##################
		//Stone types
		ExtendedItems.DRYSTONE_SLAB = regBlockItemB(ExtendedBlocks.DRYSTONE_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.DRYSTONE_STAIRS = regBlockItemB(ExtendedBlocks.DRYSTONE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.DRYSTONE_WALL = regBlockItemB(ExtendedBlocks.DRYSTONE_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.COBBLED_DRYSTONE = regBlockItemB(ExtendedBlocks.COBBLED_DRYSTONE, LOTRItemGroups.BLOCKS);
		ExtendedItems.CUT_DRYSTONE = regBlockItemB(ExtendedBlocks.CUT_DRYSTONE, LOTRItemGroups.BLOCKS);
		
		ExtendedItems.MOSSY_DRYSTONE = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DRYSTONE_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DRYSTONE_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DRYSTONE_WALL = regBlockItemB(ExtendedBlocks.MOSSY_DRYSTONE_WALL, LOTRItemGroups.BLOCKS);
		
		//Taurethrim Bricks
		ExtendedItems.TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_PILLAR = regBlockItemB(ExtendedBlocks.TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.TAURETHRIM_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_PILLAR = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_TAURETHRIM_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_SLAB = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_WALL = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		
		//Dwarven Bricks
		ExtendedItems.OBSIDIAN_DWARVEN_BRICK = regBlockItemB(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_DWARVEN_BRICK_SLAB = regBlockItemB(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_DWARVEN_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.OBSIDIAN_DWARVEN_BRICK_WALL = regBlockItemB(ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL, LOTRItemGroups.BLOCKS);

		//Dol Guldur Bricks
		ExtendedItems.DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_SLAB = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_BRICK_WALL = regBlockItemB(ExtendedBlocks.DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_PILLAR = regBlockItemB(ExtendedBlocks.DOL_GULDUR_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.DOL_GULDUR_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.CARVED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_DOL_GULDUR_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL, LOTRItemGroups.BLOCKS);

		//Rhunic Bricks
		ExtendedItems.RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_PILLAR = regBlockItemB(ExtendedBlocks.RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.RHUNIC_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CARVED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.FLOWERY_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_PILLAR = regBlockItemB(ExtendedBlocks.RED_RHUNIC_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.RED_RHUNIC_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_RED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CARVED_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_RED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RED_RHUNIC_BRICK = regBlockItemB(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RED_RHUNIC_BRICK_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RED_RHUNIC_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_RED_RHUNIC_BRICK_WALL = regBlockItemB(ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_WALL, LOTRItemGroups.BLOCKS);

		ExtendedItems.SMOOTH_GONDOR_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_GONDOR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_BLACK_GONDOR_BRICK = regBlockItemB(ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK, LOTRItemGroups.BLOCKS);

		ExtendedItems.MORGUL_TORCH = regWallOrFloorBlockItemB(ExtendedBlocks.MORGUL_TORCH, ExtendedBlocks.MORGUL_WALL_TORCH, LOTRItemGroups.DECO);
		
		ExtendedItems.SMOOTH_MORDOR_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_MORDOR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_ROHAN_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_ROHAN_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_BLUE_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_BLUE_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_RED_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_RED_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_STONE_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_STONE_STAIRS, LOTRItemGroups.BLOCKS);
		//###################################################################MATERIALS########################################################
		//Misc
		ExtendedItems.OBSIDIAN_SHARD = regMaterialItemB("obsidian_shard");
		ExtendedItems.HITHLAIN = regMaterialItemB("hithlain");
		ExtendedItems.ITHILDIN = regMaterialItemB("ithildin");

		ExtendedItems.WHITE_FUR = regMaterialItemB("white_fur");
		ExtendedItems.SILVER_FUR = regMaterialItemB("silver_fur");
		ExtendedItems.GRAY_FUR = regMaterialItemB("gray_fur");
		ExtendedItems.BLACK_FUR = regMaterialItemB("black_fur");

		//Ingots
		ExtendedItems.GALVORN_INGOT = regMaterialItemB("galvorn_ingot");
		ExtendedItems.BLACK_URUK_STEEL_INGOT = regMaterialItemB("black_uruk_steel_ingot");
		ExtendedItems.GILDED_IRON_INGOT = regMaterialItemB("gilded_iron_ingot");
		ExtendedItems.MORGUL_STEEL_INGOT = regMaterialItemB("morgul_steel_ingot");
		ExtendedItems.ORC_STEEL_NUGGET = regMaterialItemB("orc_steel_nugget");
		//Gems
		ExtendedItems.TOPAZ = regMaterialItemB("topaz");
		ExtendedItems.AMETHYST = regMaterialItemB("amethyst");
		ExtendedItems.SAPPHIRE = regMaterialItemB("sapphire");
		ExtendedItems.RUBY = regMaterialItemB("ruby");
		ExtendedItems.AMBER = regMaterialItemB("amber");
		ExtendedItems.DIAMOND = regMaterialItemB("diamond");
		ExtendedItems.OPAL = regMaterialItemB("opal");
		ExtendedItems.EMERALD = regMaterialItemB("emerald");
		ExtendedItems.PEARL = regMaterialItemB("pearl");
		ExtendedItems.CORAL = regMaterialItemB("coral");

		//#########################################################################TOOLS#####################################################
		//Taurethrim Tools
		ExtendedItems.TAURETHRIM_SHOVEL = regItemB("taurethrim_shovel", () -> new LOTRShovelItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_PICKAXE = regItemB("taurethrim_pickaxe", () -> new LOTRPickaxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_AXE = regItemB("taurethrim_axe", () -> new LOTRAxeItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_HOE = regItemB("taurethrim_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("TAURETHRIM"))); 
		
		//Angmar Tools
		ExtendedItems.ANGMAR_SHOVEL = regItemB("angmar_shovel", () -> new LOTRShovelItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_PICKAXE = regItemB("angmar_pickaxe", () -> new LOTRPickaxeItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_AXE = regItemB("angmar_axe", () -> new LOTRAxeItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_HOE = regItemB("angmar_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("ANGMAR")));

		//Dol Guldur Tools
		ExtendedItems.DOL_GULDUR_SHOVEL = regItemB("dol_guldur_shovel", () -> new LOTRShovelItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_PICKAXE = regItemB("dol_guldur_pickaxe", () -> new LOTRPickaxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_AXE = regItemB("dol_guldur_axe", () -> new LOTRAxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_HOE = regItemB("dol_guldur_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("DOL_GULDUR")));

		ExtendedItems.BRANDING_IRON = regItemB("branding_iron", () -> new ExtendedBrandingIronItem());
		//######################################################################FOOD########################################################
		//Berries
		ExtendedItems.BLACKBERRY = regItemB("blackberry", () -> new BlockNamedItem(ExtendedBlocks.BLACKBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLACKBERRY)));
		ExtendedItems.BLUEBERRY = regItemB("blueberry", () -> new BlockNamedItem(ExtendedBlocks.BLUEBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLUEBERRY)));
		ExtendedItems.CRANBERRY = regItemB("cranberry", () -> new BlockNamedItem(ExtendedBlocks.CRANBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CRANBERRY)));
		ExtendedItems.ELDERBERRY = regItemB("elderberry", () -> new BlockNamedItem(ExtendedBlocks.ELDERBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ELDERBERRY)));
		ExtendedItems.RASPBERRY = regItemB("raspberry", () -> new BlockNamedItem(ExtendedBlocks.RASPBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.RASPBERRY)));
		ExtendedItems.WILDBERRY = regItemB("wildberry", ()-> new BlockNamedItem(ExtendedBlocks.WILDBERRY_BUSH_PLANT.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.WILDBERRY)));
		ExtendedItems.BOTTLE_OF_POISON = regItemB("bottle_of_poison", () -> new ExtendedPoisonBottleItem(new Properties().stacksTo(16).tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_CURDS)));
		ExtendedItems.CHEESE_CURDS = regItemB("cheese_curds", () -> new Item(new Properties().stacksTo(32).tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_CURDS)));
		ExtendedItems.CHEESE_WHEEL  = regCakeBlockItemD(ExtendedBlocks.CHEESE_WHEEL, LOTRItemGroups.FOOD);
		ExtendedItems.AGED_CHEESE_WHEEL  = regCakeBlockItemD(ExtendedBlocks.AGED_CHEESE_WHEEL, LOTRItemGroups.FOOD);
		ExtendedItems.CHEESE_SLICE = regItemB("cheese_slice", () -> new ExtendedUseItemAwardStatItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_SLICE), ExtendedStats.EAT_CHEESE_SLICE));
		ExtendedItems.AGED_CHEESE_SLICE = regItemB("aged_cheese_slice", () -> new ExtendedUseItemAwardStatItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.AGED_CHEESE_SLICE), ExtendedStats.EAT_CHEESE_SLICE));

		ExtendedItems.BANANA = regItemB("banana", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BANANA)));
		ExtendedItems.BANANA_BREAD = regItemB("banana_bread", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BANANA_BREAD)));
		ExtendedItems.BANANA_CAKE  = regCakeBlockItemD(ExtendedBlocks.BANANA_CAKE, LOTRItemGroups.FOOD);


		ExtendedItems.ROASTED_CHESTNUT = regItemB("roasted_chestnut", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ROASTED_CHESTNUT)));
		//#######################################################################EQUIPMENT##################################################
		ExtendedItems.COMMANDHORN = regItemB("horn_of_command", () -> new ExtendedCommandHornItem());

		//Morgul Equipment
		ExtendedItems.MORGUL_HELMET = regItemB("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.MORGUL_CHESTPLATE = regItemB("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
		ExtendedItems.MORGUL_LEGGINGS = regItemB("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
		ExtendedItems.MORGUL_BOOTS = regItemB("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
		ExtendedItems.MORGUL_SWORD = regItemB("morgul_blade",() -> new LOTRSwordItem(LOTRMaterial.valueOf("MORGUL")));
		ExtendedItems.MORGUL_SPEAR = regItemB("morgul_spear",() -> new SpearItem(LOTRMaterial.valueOf("MORGUL")));
		ExtendedItems.MORGUL_DAGGER = regItemB("morgul_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("MORGUL")));
		
		//Taurethrim Equipment
		ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET = regItemB("taurethrim_chieftain_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD,"chieftain_helmet"));
		ExtendedItems.TAURETHRIM_HELMET = regItemB("taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD));
		ExtendedItems.TAURETHRIM_CHESTPLATE = regItemB("taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.TAURETHRIM_LEGGINGS = regItemB("taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.TAURETHRIM_BOOTS = regItemB("taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.FEET));
		ExtendedItems.TAURETHRIM_SWORD = regItemB("taurethrim_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_SPEAR = regItemB("taurethrim_spear",() -> new SpearItem(LOTRMaterial.valueOf("TAURETHRIM")));
		ExtendedItems.TAURETHRIM_DAGGER = regItemB("taurethrim_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("TAURETHRIM")));

		ExtendedItems.GOLDEN_TAURETHRIM_HELMET = regItemB("golden_taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE = regItemB("golden_taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS = regItemB("golden_taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.GOLDEN_TAURETHRIM_BOOTS = regItemB("golden_taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GOLDEN_TAURETHRIM"), EquipmentSlotType.FEET));

		//Angmar Equipment
		ExtendedItems.ANGMAR_HELMET = regItemB("angmar_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.HEAD));
		ExtendedItems.ANGMAR_CHESTPLATE = regItemB("angmar_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.CHEST));
		ExtendedItems.ANGMAR_LEGGINGS = regItemB("angmar_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.LEGS));
		ExtendedItems.ANGMAR_BOOTS = regItemB("angmar_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("ANGMAR"), EquipmentSlotType.FEET));
		ExtendedItems.ANGMAR_SWORD = regItemB("angmar_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_SPEAR = regItemB("angmar_spear",() -> new SpearItem(LOTRMaterial.valueOf("ANGMAR")));
		ExtendedItems.ANGMAR_DAGGER = regItemB("angmar_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("ANGMAR")));

		//Dol Guldur Equipment
		ExtendedItems.DOL_GULDUR_HELMET = regItemB("dol_guldur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.HEAD));
		ExtendedItems.DOL_GULDUR_CHESTPLATE = regItemB("dol_guldur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.CHEST));
		ExtendedItems.DOL_GULDUR_LEGGINGS = regItemB("dol_guldur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.LEGS));
		ExtendedItems.DOL_GULDUR_BOOTS = regItemB("dol_guldur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.FEET));
		ExtendedItems.DOL_GULDUR_SWORD = regItemB("dol_guldur_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_SPEAR = regItemB("dol_guldur_spear",() -> new SpearItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_DAGGER = regItemB("dol_guldur_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("DOL_GULDUR")));

		// misc equipment
		ExtendedItems.LEATHER_HAT = regItemB("leather_hat", () -> new LOTRArmorItem(LOTRMaterial.valueOf("COSMETIC"), EquipmentSlotType.HEAD, "leather_hat"));

		//########################################################################UTIL##########################################################
		//Crafting Tables
		ExtendedItems.DOL_GULDUR_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.GUNDABAD_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.GUNDABAD_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.HALF_TROLL_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.MORWAITH_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.MORWAITH_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.RHUNIC_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.TAURETHRIM_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.TAURETHRIM_STONE_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.TAURETHRIM_STONE_CRAFTING_TABLE, LOTRItemGroups.UTIL);

		ExtendedItems.CONQUEST_TABLE = regHiddenBlockItemB(ExtendedBlocks.CONQUEST_TABLE);

		//########################################################################MISC##########################################################

		ExtendedItems.HOBBIT_MARRIAGE_RING =  regItemB("hobbit_marriage_ring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
		ExtendedItems.DWARF_MARRIAGE_RING =  regItemB("dwarven_marriage_ring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
		ExtendedItems.SILVER_COIN_ONE = regItemB("silver_coin_one", () -> new ExtendedSilverCoin(new Properties().tab(LOTRItemGroups.MISC), CoinType.ONE));
		ExtendedItems.SILVER_COIN_TEN = regItemB("silver_coin_ten", () -> new ExtendedSilverCoin(new Properties().tab(LOTRItemGroups.MISC), CoinType.TEN));
		ExtendedItems.SILVER_COIN_HUNDRED = regItemB("silver_coin_hundred", () -> new ExtendedSilverCoin(new Properties().tab(LOTRItemGroups.MISC), CoinType.HUNDRED));
		ExtendedItems.CONKER =  regItemB("conker", () -> new ExtendedThrowableConkerItem(new Properties().tab(LOTRItemGroups.MISC).stacksTo(16)));
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> blackFurBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedBlackFurBedTileEntity::new);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> furBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedFurBedTileEntity::new);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> grayFurBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedGrayFurBedTileEntity::new);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> silverFurBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedSilverFurBedTileEntity::new);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> whiteFurBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedWhiteFurBedTileEntity::new);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> orcBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedOrcBedTileEntity::new);
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> strawBedRenderer() {
		return () -> new ItemRenderHelper(ExtendedStrawBedTileEntity::new);
	}
}
