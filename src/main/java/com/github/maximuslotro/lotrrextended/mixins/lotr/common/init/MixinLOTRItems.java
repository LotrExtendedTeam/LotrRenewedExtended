package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lotr.common.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.client.render.item.ExtendedItemRenderHelper;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.github.maximuslotro.lotrrextended.common.enums.CoinType;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBowMaterial;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedFoods;
import com.github.maximuslotro.lotrrextended.common.item.CompostableItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBannerPatterns;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBedItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBerries;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBucketOfCheeseCurds;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCopperIngotItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedDuckEggItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedEntitySpawnerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedGuldurilItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedPoisonBottleItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSilverCoin;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableConkerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedUseItemAwardStatItem;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedGrayFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedStrawBedTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedWhiteFurBedTileEntity;
import com.google.common.base.Function;

import lotr.common.entity.item.LOTRBoatEntity;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRItemGroups.LOTRItemGroup;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;

/**
 * Used to register new items added by Extended
 * @Mixin (LOTRItems.class)
 * */
@Mixin(LOTRItems.class)
public class MixinLOTRItems {
	/**
	 * Get Renewed's very base method for registering items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param name A String name of the entity
	 * @param sup A supplier for the item
	 * @return A RegistryObject instance for the item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {return null;}
	/**
	 * Extended's base method for registering items, handling dataGen
	 * @param name A String name of the entity
	 * @param sup A supplier for the item
	 * @return A RegistryObject instance for the item
	 * */
	@Unique
	private static RegistryObject<Item> regItemB(String name, Supplier<? extends Item> sup)  {
		RegistryObject<Item> item = regItem(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(name, item);
		}
		return item;
	}
	/**
	 * Extended's method for registering banner pattern items, handling their unique dataGen needs
	 * @param name A String name of the entity
	 * @param bannerPattern The BannerPattern instance for this item
	 * @param sup A supplier for the item
	 * @return A RegistryObject instance for the item
	 * */
	@Unique
	private static RegistryObject<Item> regBannerPaternItem(String name, BannerPattern bannerPattern, Supplier<? extends Item> sup)  {
		RegistryObject<Item> item = regItemB(name, sup);
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.bannerPatternItemNameHolders.put(name, bannerPattern.getFilename());
		}
		return item;
	}
	/**
	 * Get Renewed's base method for registering cake-like items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param blockSup A block supplier for the cake-like block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the cake-like item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regCakeBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	/**
	 * Extended's method for registering cake-like item, handling dataGen
	 * @param blockSup A block supplier for the cake-like block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the cake-like item
	 * */
	@Unique
	private static RegistryObject<Item> regCakeBlockItemD(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regCakeBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Get Renewed's base method for registering block items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param blockSup A block supplier for the block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the block item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	/**
	 * Get Renewed's base method for registering block items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param blockSup A block supplier for the block
	 * @param group The ItemGroup for this item to be placed in
	 * @param extraPropsSetter More Item.Properties to be registered for this item's instance
	 * @return A RegistryObject instance for the block item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group, Consumer<Properties> extraPropsSetter) {return null;}
	/**
	 * Extended's method for registering tileEntity-based block items, handles dataGen
	 * @param blockSup A block supplier for the block
	 * @param group The ItemGroup for this item to be placed in
	 * @param ister A TileEntityRenderer instance to be used for rendering
	 * @return A RegistryObject instance for the block item
	 * */
	@Unique
	private static RegistryObject<Item> regBlockItemStackRenderer(RegistryObject<Block> blockSup, ItemGroup group, Supplier<Callable<ItemStackTileEntityRenderer>> ister) {
		RegistryObject<Item> item = regBlockItem(blockSup, group, (props) -> props.setISTER(ister));
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Extended's base method for registering block items, handling dataGen
	 * @param blockSup A block supplier for the block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the block item
	 * */
	@Unique
	private static RegistryObject<Item> regBlockItemB(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Extended's method for registering custom block items, handling dataGen
	 * @param blockSup A block supplier for the block
	 * @param itemMaker The Function for creating a custom BlockItem
	 * @return A RegistryObject instance for the block item
	 * */
	@Unique
	private static RegistryObject<Item> regExtendedBlockItemD(RegistryObject<Block> blockSup, Function<Block, ? extends BlockItem> itemMaker) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, itemMaker);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Extended's method for registering hidden block items so they don't show in the creative menu, handling dataGen
	 * @param blockSup A block supplier for the block
	 * @return A RegistryObject instance for the block item
	 * */
	@Unique
	private static RegistryObject<Item> regHiddenBlockItemB(RegistryObject<Block> blockSup) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> new LOTRBlockItem(b, new Properties()));
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Extended's base method for registering block items, NO DATAGEN DONE HERE. DO NOT CALL DIRECTLY, USE HELPER METHODS
	 * @param blockSup A block supplier for the block
	 * @param itemMaker A Function to build the block item
	 * @return A RegistryObject instance for the block item
	 * */
	@Unique
	private static RegistryObject<Item> regArbitraryBlockItemD(RegistryObject<Block> blockSup, Function<Block, ? extends BlockItem> itemMaker) {
		String name = blockSup.getId().getPath();
		return regItem(name, () -> itemMaker.apply(blockSup.get()));
	}
	/**
	 * Extended's method for registering model-based bed items, handles dataGen
	 * @param blockSup A block supplier for the bed
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the bed item
	 * */
	@Unique
	private static RegistryObject<Item> regBedBlockItemD(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> new ExtendedBedItem(b, new Properties().stacksTo(1).tab(group)));
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Extended's method for registering tileEntity-based bed items, handles dataGen
	 * @param blockSup A block supplier for the bed
	 * @param group The ItemGroup for this item to be placed in
	 * @param ister A TileEntityRenderer instance to be used for rendering
	 * @return A RegistryObject instance for the bed item
	 * */
	@Unique
	private static RegistryObject<Item> regBedBlockItemD(RegistryObject<Block> blockSup, ItemGroup group, Supplier<Callable<ItemStackTileEntityRenderer>> ister) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> new ExtendedBedItem(b, new Properties().stacksTo(1).tab(group).setISTER(ister)));
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Extended's method for registering fallen leaves block items, handles dataGen
	 * @param blockSup A block supplier for the fallen leaves block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the fallen leaves block item
	 * */
	@Unique
	private static RegistryObject<Item> regFallenLeavesBlockItemD(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item =  regArbitraryBlockItemD(blockSup, (b) -> new FallenLeavesItem(b, (new Properties()).tab(group)));
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Get Renewed's base method for registering vessel block items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param blockSup A block supplier for the vessel block
	 * @param vesselType A VesselType for this item
	 * @return A RegistryObject instance for the vessel block item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regEmptyVesselBlockItem(RegistryObject<Block> blockSup, VesselType vesselType) {return null;}
	/**
	 * Extended's method for registering vessel block items, handling datGen
	 * @param blockSup A block supplier for the vessel block
	 * @param vesselType A VesselType for this item
	 * @return A RegistryObject instance for the vessel block item
	 * */
	@Unique
	private static RegistryObject<Item> regEmptyVesselBlockItemD(RegistryObject<Block> blockSup, VesselType vesselType) {
		RegistryObject<Item> item = regEmptyVesselBlockItem(blockSup, vesselType);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * Get Renewed's base method for registering tall block items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param blockSup A block supplier for the tall block item
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the tall block item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regTallBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {return null;}
	/**
	 * Extended's method for registering tall block items, handling dataGen
	 * @param blockSup A block supplier for the tall block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the tall block item
	 * */
	@Unique
	private static RegistryObject<Item> regTallBlockItemB(RegistryObject<Block> blockSup, ItemGroup group) {
		RegistryObject<Item> item = regTallBlockItem(blockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	/**
	 * Get Renewed's base method for registering sign block items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param standingSignSup A block supplier for the sign block
	 * @param wallSignSup A block supplier for the wall sign block
	 * @return A RegistryObject instance for the sign block item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regSignBlockItem(RegistryObject<Block> standingSignSup, RegistryObject<Block> wallSignSup) {return null;}
	/**
	 * Extended's method for registering sign block items, handling dataGen
	 * @param standingSignSup A block supplier for the sign block
	 * @param wallSignSup A block supplier for the wall sign block
	 * @return A RegistryObject instance for the sign block item
	 * */
	@Unique
	private static RegistryObject<Item> regSignBlockItemB(RegistryObject<Block> standingSignSup, RegistryObject<Block> wallSignSup) {
		RegistryObject<Item> item = regSignBlockItem(standingSignSup, wallSignSup);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDBLOCKITEMS.put(item.getId().getPath(), item);
		}
		return item;
	}

	/**
	 * Extended's method for registering material items, handling dataGen
	 * @param name A String name for the item
	 * @return A RegistryObject instance for the material item
	 * */
	//Moved to overwrite method
	//@Shadow(remap=false)
	//private static RegistryObject<Item> regMaterialItem(String name) {return null;}
	@Unique
	private static RegistryObject<Item> regMaterialItemB(String name)  {
		RegistryObject<Item> item = regMaterialItem(name);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(name, item);
		}
		return item;
	}

	/**
	 * @author maximuslotro
	 * @reason Extended's method for registering material items, but also handling overwriting renewed items with needed new classes, DO NOT DIRECTLY CALL
	 * @Overwrite (remap = false)
	 * @param name A String name for the item
	 * @return A RegistryObject instance for the material item
	 * */
	@Overwrite(remap = false)
	private static RegistryObject<Item> regMaterialItem(String name) {
		if(name.equals("gulduril")) {
			return regItem(name, () -> new ExtendedGuldurilItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		if(name.equals("copper_ingot")) {
			return regItem(name, () -> new ExtendedCopperIngotItem(new Properties().tab(LOTRItemGroups.MATERIALS)));
		}
		return regItem(name, () -> new Item(new Properties().tab(LOTRItemGroups.MATERIALS)));
	}
	/**
	 * Get Renewed's base method for registering wall/floor block items from the parent class, DO NOT DIRECTLY CALL
	 * @Shadow (remap=false)
	 * @param wallBlockSup A block supplier for the wall block
	 * @param floorBlockSup A block supplier for the floor block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the wall/floor block item
	 * */
	@Shadow(remap=false)
	private static RegistryObject<Item> regWallOrFloorBlockItem(RegistryObject <Block>wallBlockSup, RegistryObject<Block> floorBlockSup, ItemGroup group)  {return null;}
	/**
	 * Extended's method for registering wall/floor block items, handling dataGen
	 * @param wallBlockSup A block supplier for the wall block
	 * @param floorBlockSup A block supplier for the floor block
	 * @param group The ItemGroup for this item to be placed in
	 * @return A RegistryObject instance for the wall/floor block item
	 * */
	@Unique
	private static RegistryObject<Item> regWallOrFloorBlockItemB(RegistryObject <Block>wallBlockSup, RegistryObject<Block> floorBlockSup, ItemGroup group)  {
		RegistryObject<Item> item = regWallOrFloorBlockItem(wallBlockSup, floorBlockSup, group);
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedItems.REGISTEREDITEMS.put(wallBlockSup.getId().getPath(), item);
		}
		return item;
	}
	/**
	 * @reason Register Extended's items after Renewed's static registering is done, but before the item differed register is registered
	 * @Inject (method = "register()V", at = @At("HEAD"), remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		//###########################################################BLOCKS################################################################
		ExtendedItems.CHESTNUT_LOG = regBlockItemB(ExtendedBlocks.CHESTNUT_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_WOOD = regBlockItemB(ExtendedBlocks.CHESTNUT_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_PLANKS = regBlockItemB(ExtendedBlocks.CHESTNUT_PLANKS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_LEAVES = regBlockItemB(ExtendedBlocks.CHESTNUT_LEAVES, LOTRItemGroups.DECO);
		ExtendedItems.CHESTNUT_LEAVES_FRUIT = regBlockItemB(ExtendedBlocks.CHESTNUT_LEAVES_FRUIT, LOTRItemGroups.DECO);
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
		ExtendedItems.CHESTNUT_LOG_SLAB = regBlockItemB(ExtendedBlocks.CHESTNUT_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_WOOD_SLAB = regBlockItemB(ExtendedBlocks.CHESTNUT_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.CHESTNUT_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_BRANCH = regBlockItemB(ExtendedBlocks.CHESTNUT_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_LOG_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_WOOD_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_CHESTNUT_BRANCH = regBlockItemB(ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.CHESTNUT_BEAM_SLAB = regBlockItemB(ExtendedBlocks.CHESTNUT_BEAM_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FALLEN_CHESTNUT_LEAVES = regFallenLeavesBlockItemD(ExtendedBlocks.FALLEN_CHESTNUT_LEAVES, LOTRItemGroups.DECO);

		ExtendedItems.BANANA_LOG = regBlockItemB(ExtendedBlocks.BANANA_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_WOOD = regBlockItemB(ExtendedBlocks.BANANA_WOOD, LOTRItemGroups.BLOCKS);
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
		ExtendedItems.STRIPPED_BANANA_WOOD = regBlockItemB(ExtendedBlocks.STRIPPED_BANANA_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_BEAM = regBlockItemB(ExtendedBlocks.BANANA_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_SIGN = regSignBlockItemB(ExtendedBlocks.BANANA_SIGN, ExtendedBlocks.BANANA_WALL_SIGN);
		ExtendedItems.BANANA_BOAT = regItemB("banana_boat", () -> new LOTRBoatItem(LOTRBoatEntity.ModBoatType.valueOf("BANANA")));
		ExtendedItems.BANANA_LOG_SLAB = regBlockItemB(ExtendedBlocks.BANANA_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_WOOD_SLAB = regBlockItemB(ExtendedBlocks.BANANA_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.BANANA_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_BRANCH = regBlockItemB(ExtendedBlocks.BANANA_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_BANANA_LOG_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_BANANA_WOOD_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_BANANA_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_BANANA_BRANCH = regBlockItemB(ExtendedBlocks.STRIPPED_BANANA_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.BANANA_BEAM_SLAB = regBlockItemB(ExtendedBlocks.BANANA_BEAM_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FALLEN_BANANA_LEAVES = regFallenLeavesBlockItemD(ExtendedBlocks.FALLEN_BANANA_LEAVES, LOTRItemGroups.DECO);

		ExtendedItems.PLUM_LOG = regBlockItemB(ExtendedBlocks.PLUM_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_WOOD = regBlockItemB(ExtendedBlocks.PLUM_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_PLANKS = regBlockItemB(ExtendedBlocks.PLUM_PLANKS, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_LEAVES = regBlockItemB(ExtendedBlocks.PLUM_LEAVES, LOTRItemGroups.DECO);
		ExtendedItems.PLUM_LEAVES_FRUIT = regBlockItemB(ExtendedBlocks.PLUM_LEAVES_FRUIT, LOTRItemGroups.DECO);
		ExtendedItems.PLUM_SAPLING = regBlockItemB(ExtendedBlocks.PLUM_SAPLING, LOTRItemGroups.DECO);
		ExtendedItems.PLUM_SLAB = regBlockItemB(ExtendedBlocks.PLUM_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_STAIRS = regBlockItemB(ExtendedBlocks.PLUM_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_FENCE = regBlockItemB(ExtendedBlocks.PLUM_FENCE, LOTRItemGroups.DECO);
		ExtendedItems.PLUM_FENCE_GATE = regBlockItemB(ExtendedBlocks.PLUM_FENCE_GATE, LOTRItemGroups.UTIL);
		ExtendedItems.PLUM_DOOR = regTallBlockItemB(ExtendedBlocks.PLUM_DOOR, LOTRItemGroups.UTIL);
		ExtendedItems.PLUM_TRAPDOOR = regBlockItemB(ExtendedBlocks.PLUM_TRAPDOOR, LOTRItemGroups.UTIL);
		ExtendedItems.PLUM_PRESSURE_PLATE = regBlockItemB(ExtendedBlocks.PLUM_PRESSURE_PLATE, LOTRItemGroups.UTIL);
		ExtendedItems.PLUM_BUTTON = regBlockItemB(ExtendedBlocks.PLUM_BUTTON, LOTRItemGroups.UTIL);
		ExtendedItems.STRIPPED_PLUM_LOG = regBlockItemB(ExtendedBlocks.STRIPPED_PLUM_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_PLUM_WOOD = regBlockItemB(ExtendedBlocks.STRIPPED_PLUM_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_BEAM = regBlockItemB(ExtendedBlocks.PLUM_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_SIGN = regSignBlockItemB(ExtendedBlocks.PLUM_SIGN, ExtendedBlocks.PLUM_WALL_SIGN);
		ExtendedItems.PLUM_BOAT = regItemB("plum_boat", () -> new LOTRBoatItem(LOTRBoatEntity.ModBoatType.valueOf("PLUM")));
		ExtendedItems.PLUM_LOG_SLAB = regBlockItemB(ExtendedBlocks.PLUM_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_WOOD_SLAB = regBlockItemB(ExtendedBlocks.PLUM_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.PLUM_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_BRANCH = regBlockItemB(ExtendedBlocks.PLUM_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_PLUM_LOG_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_PLUM_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_PLUM_WOOD_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_PLUM_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_PLUM_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.STRIPPED_PLUM_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_PLUM_BRANCH = regBlockItemB(ExtendedBlocks.STRIPPED_PLUM_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.PLUM_BEAM_SLAB = regBlockItemB(ExtendedBlocks.PLUM_BEAM_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FALLEN_PLUM_LEAVES = regFallenLeavesBlockItemD(ExtendedBlocks.FALLEN_PLUM_LEAVES, LOTRItemGroups.DECO);
		
		ExtendedItems.WILLOW_LOG = regBlockItemB(ExtendedBlocks.WILLOW_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_WOOD = regBlockItemB(ExtendedBlocks.WILLOW_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_PLANKS = regBlockItemB(ExtendedBlocks.WILLOW_PLANKS, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_LEAVES = regBlockItemB(ExtendedBlocks.WILLOW_LEAVES, LOTRItemGroups.DECO);
		ExtendedItems.WILLOW_SAPLING = regBlockItemB(ExtendedBlocks.WILLOW_SAPLING, LOTRItemGroups.DECO);
		ExtendedItems.WILLOW_VINES = regBlockItemB(ExtendedBlocks.WILLOW_VINES, LOTRItemGroups.DECO);
		ExtendedItems.WILLOW_SLAB = regBlockItemB(ExtendedBlocks.WILLOW_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_STAIRS = regBlockItemB(ExtendedBlocks.WILLOW_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_FENCE = regBlockItemB(ExtendedBlocks.WILLOW_FENCE, LOTRItemGroups.DECO);
		ExtendedItems.WILLOW_FENCE_GATE = regBlockItemB(ExtendedBlocks.WILLOW_FENCE_GATE, LOTRItemGroups.UTIL);
		ExtendedItems.WILLOW_DOOR = regTallBlockItemB(ExtendedBlocks.WILLOW_DOOR, LOTRItemGroups.UTIL);
		ExtendedItems.WILLOW_TRAPDOOR = regBlockItemB(ExtendedBlocks.WILLOW_TRAPDOOR, LOTRItemGroups.UTIL);
		ExtendedItems.WILLOW_PRESSURE_PLATE = regBlockItemB(ExtendedBlocks.WILLOW_PRESSURE_PLATE, LOTRItemGroups.UTIL);
		ExtendedItems.WILLOW_BUTTON = regBlockItemB(ExtendedBlocks.WILLOW_BUTTON, LOTRItemGroups.UTIL);
		ExtendedItems.STRIPPED_WILLOW_LOG = regBlockItemB(ExtendedBlocks.STRIPPED_WILLOW_LOG, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_WILLOW_WOOD = regBlockItemB(ExtendedBlocks.STRIPPED_WILLOW_WOOD, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_BEAM = regBlockItemB(ExtendedBlocks.WILLOW_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_SIGN = regSignBlockItemB(ExtendedBlocks.WILLOW_SIGN, ExtendedBlocks.WILLOW_WALL_SIGN);
		ExtendedItems.WILLOW_BOAT = regItemB("willow_boat", () -> new LOTRBoatItem(LOTRBoatEntity.ModBoatType.valueOf("WILLOW")));
		ExtendedItems.WILLOW_LOG_SLAB = regBlockItemB(ExtendedBlocks.WILLOW_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_WOOD_SLAB = regBlockItemB(ExtendedBlocks.WILLOW_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.WILLOW_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_BRANCH = regBlockItemB(ExtendedBlocks.WILLOW_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_WILLOW_LOG_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_WILLOW_LOG_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_WILLOW_WOOD_SLAB = regBlockItemB(ExtendedBlocks.STRIPPED_WILLOW_WOOD_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_WILLOW_WOOD_STAIRS = regBlockItemB(ExtendedBlocks.STRIPPED_WILLOW_WOOD_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.STRIPPED_WILLOW_BRANCH = regBlockItemB(ExtendedBlocks.STRIPPED_WILLOW_BRANCH, LOTRItemGroups.BLOCKS);
		ExtendedItems.WILLOW_BEAM_SLAB = regBlockItemB(ExtendedBlocks.WILLOW_BEAM_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.FALLEN_WILLOW_LEAVES = regFallenLeavesBlockItemD(ExtendedBlocks.FALLEN_WILLOW_LEAVES, LOTRItemGroups.DECO);

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

		ExtendedItems.BONE_BLOCK_SLAB = regBlockItemB(ExtendedBlocks.BONE_BLOCK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BONE_BLOCK_STAIRS = regBlockItemB(ExtendedBlocks.BONE_BLOCK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BONE_BLOCK_WALL = regBlockItemB(ExtendedBlocks.BONE_BLOCK_WALL, LOTRItemGroups.BLOCKS);

		ExtendedItems.CROSSED_WATTLE_AND_DAUB = regBlockItemB(ExtendedBlocks.CROSSED_WATTLE_AND_DAUB, LOTRItemGroups.BLOCKS);

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
		//Arnor
		ExtendedItems.CRACKED_ARNOR_PILLAR = regBlockItemB(ExtendedBlocks.CRACKED_ARNOR_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.CRACKED_ARNOR_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_ARNOR_PILLAR = regBlockItemB(ExtendedBlocks.MOSSY_ARNOR_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSSY_ARNOR_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB, LOTRItemGroups.BLOCKS);

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
		ExtendedItems.MOSS_BLOCK = regBlockItemB(ExtendedBlocks.MOSS_BLOCK, LOTRItemGroups.BLOCKS);
		ExtendedItems.MOSS_CARPET = regBlockItemB(ExtendedBlocks.MOSS_CARPET, LOTRItemGroups.BLOCKS);
		ExtendedItems.ANCIENT_RUNE_BLOCK = regBlockItemB(ExtendedBlocks.ANCIENT_RUNE_BLOCK, LOTRItemGroups.BLOCKS);

		ExtendedItems.DWARVEN_DOOR = regBlockItemB(ExtendedBlocks.DWARVEN_DOOR, LOTRItemGroups.BLOCKS);
		ExtendedItems.DWARVEN_DOOR_LUIGON = regBlockItemB(ExtendedBlocks.DWARVEN_DOOR_LUIGON, LOTRItemGroups.BLOCKS);
		ExtendedItems.DWARVEN_DOOR_CARGON = regBlockItemB(ExtendedBlocks.DWARVEN_DOOR_CARGON, LOTRItemGroups.BLOCKS);
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
		ExtendedItems.STRAW_BED = regBedBlockItemD(ExtendedBlocks.STRAW_BED, LOTRItemGroups.DECO, ()->strawBedRenderer());
		ExtendedItems.ORC_BED = regBedBlockItemD(ExtendedBlocks.ORC_BED, LOTRItemGroups.DECO, ()->orcBedRenderer());
		ExtendedItems.BLACK_FUR_BED = regBedBlockItemD(ExtendedBlocks.BLACK_FUR_BED, LOTRItemGroups.DECO, ()->blackFurBedRenderer());
		ExtendedItems.FUR_BED = regBedBlockItemD(ExtendedBlocks.FUR_BED, LOTRItemGroups.DECO, ()->furBedRenderer());
		ExtendedItems.GRAY_FUR_BED = regBedBlockItemD(ExtendedBlocks.GRAY_FUR_BED, LOTRItemGroups.DECO, ()->grayFurBedRenderer());
		ExtendedItems.SILVER_FUR_BED = regBedBlockItemD(ExtendedBlocks.SILVER_FUR_BED, LOTRItemGroups.DECO, ()->silverFurBedRenderer());
		ExtendedItems.WHITE_FUR_BED = regBedBlockItemD(ExtendedBlocks.WHITE_FUR_BED, LOTRItemGroups.DECO, ()->whiteFurBedRenderer());
		ExtendedItems.BEDROLL = regBedBlockItemD(ExtendedBlocks.BEDROLL, LOTRItemGroups.DECO);
		//Bushes
		ExtendedItems.RASPBERRY_HEDGE = regBlockItemB(ExtendedBlocks.RASPBERRY_HEDGE, LOTRItemGroups.DECO);
		ExtendedItems.BLUEBERRY_HEDGE = regBlockItemB(ExtendedBlocks.BLUEBERRY_HEDGE, LOTRItemGroups.DECO);
		ExtendedItems.BLACKBERRY_HEDGE = regBlockItemB(ExtendedBlocks.BLACKBERRY_HEDGE, LOTRItemGroups.DECO);
		ExtendedItems.CRANBERRY_HEDGE = regBlockItemB(ExtendedBlocks.CRANBERRY_HEDGE, LOTRItemGroups.DECO);
		ExtendedItems.ELDERBERRY_HEDGE = regBlockItemB(ExtendedBlocks.ELDERBERRY_HEDGE, LOTRItemGroups.DECO);
		ExtendedItems.WILDBERRY_HEDGE = regBlockItemB(ExtendedBlocks.WILDBERRY_HEDGE, LOTRItemGroups.DECO);

		ExtendedItems.MORDOR_MOSS_VINES = regBlockItemB(ExtendedBlocks.MORDOR_MOSS_VINES, LOTRItemGroups.DECO);

		ExtendedItems.WHITE_PACKED_FUR = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR, LOTRItemGroups.BLOCKS);
		ExtendedItems.WHITE_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.WHITE_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.WHITE_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.WHITE_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.SILVER_PACKED_FUR = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR, LOTRItemGroups.BLOCKS);
		ExtendedItems.SILVER_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.SILVER_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SILVER_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.SILVER_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GRAY_PACKED_FUR = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR, LOTRItemGroups.BLOCKS);
		ExtendedItems.GRAY_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GRAY_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GRAY_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.GRAY_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.BROWN_PACKED_FUR = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR, LOTRItemGroups.BLOCKS);
		ExtendedItems.BROWN_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BROWN_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BROWN_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.BROWN_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR_SLAB = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR_STAIRS = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.BLACK_PACKED_FUR_WALL = regBlockItemB(ExtendedBlocks.BLACK_PACKED_FUR_WALL, LOTRItemGroups.BLOCKS);

		ExtendedItems.WHITE_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.GRAY_FUR_BUNDLE, LOTRItemGroups.DECO);
		ExtendedItems.BLACK_FUR_BUNDLE = regBlockItemB(ExtendedBlocks.BLACK_FUR_BUNDLE, LOTRItemGroups.DECO);

		ExtendedItems.BLACK_FUR_CARPET = regBlockItemB(ExtendedBlocks.BLACK_FUR_CARPET, LOTRItemGroups.DECO);
		ExtendedItems.BROWN_FUR_CARPET = regBlockItemB(ExtendedBlocks.BROWN_FUR_CARPET, LOTRItemGroups.DECO);
		ExtendedItems.GRAY_FUR_CARPET = regBlockItemB(ExtendedBlocks.GRAY_FUR_CARPET, LOTRItemGroups.DECO);
		ExtendedItems.SILVER_FUR_CARPET = regBlockItemB(ExtendedBlocks.SILVER_FUR_CARPET, LOTRItemGroups.DECO);
		ExtendedItems.WHITE_FUR_CARPET = regBlockItemB(ExtendedBlocks.WHITE_FUR_CARPET, LOTRItemGroups.DECO);

		ExtendedItems.WOODEN_BIRDCAGE = regHiddenBlockItemB(ExtendedBlocks.WOODEN_BIRDCAGE);
		ExtendedItems.BRONZE_BIRDCAGE = regHiddenBlockItemB(ExtendedBlocks.BRONZE_BIRDCAGE);
		ExtendedItems.IRON_BIRDCAGE = regHiddenBlockItemB(ExtendedBlocks.IRON_BIRDCAGE);
		ExtendedItems.SILVER_BIRDCAGE = regHiddenBlockItemB(ExtendedBlocks.SILVER_BIRDCAGE);
		ExtendedItems.GOLDEN_BIRDCAGE = regHiddenBlockItemB(ExtendedBlocks.GOLDEN_BIRDCAGE);
		ExtendedItems.MITHRIL_BIRDCAGE = regHiddenBlockItemB(ExtendedBlocks.MITHRIL_BIRDCAGE);

		ExtendedItems.MORGUL_TORCH = regWallOrFloorBlockItemB(ExtendedBlocks.MORGUL_TORCH, ExtendedBlocks.MORGUL_WALL_TORCH, LOTRItemGroups.DECO);
		ExtendedItems.MORGUL_LANTERN = regBlockItemB(ExtendedBlocks.MORGUL_LANTERN, LOTRItemGroups.DECO);
		ExtendedItems.MORGUL_CHANDELIER = regBlockItemB(ExtendedBlocks.MORGUL_CHANDELIER, LOTRItemGroups.DECO);
		
		ExtendedItems.ORC_CHAIN = regBlockItemB(ExtendedBlocks.ORC_CHAIN, LOTRItemGroups.DECO);
		ExtendedItems.ORC_CHANDELIER = regBlockItemB(ExtendedBlocks.ORC_CHANDELIER, LOTRItemGroups.DECO);
		ExtendedItems.URUK_CHANDELIER = regBlockItemB(ExtendedBlocks.URUK_CHANDELIER, LOTRItemGroups.DECO);

		ExtendedItems.EDHELVIR_CRYSTAL_CHANDELIER = regBlockItemB(ExtendedBlocks.EDHELVIR_CRYSTAL_CHANDELIER, LOTRItemGroups.DECO);
		ExtendedItems.GLOWSTONE_CRYSTAL_CHANDELIER = regBlockItemB(ExtendedBlocks.GLOWSTONE_CRYSTAL_CHANDELIER, LOTRItemGroups.DECO);
		ExtendedItems.GULDURIL_CRYSTAL_CHANDELIER = regBlockItemB(ExtendedBlocks.GULDURIL_CRYSTAL_CHANDELIER, LOTRItemGroups.DECO);
		
		ExtendedItems.MUD_PATH = regBlockItemB(ExtendedBlocks.MUD_PATH, LOTRItemGroups.DECO);
		ExtendedItems.MUD_PATH_SLAB = regBlockItemB(ExtendedBlocks.MUD_PATH_SLAB, LOTRItemGroups.DECO);
		ExtendedItems.MUD_PATH_STAIRS = regBlockItemB(ExtendedBlocks.MUD_PATH_STAIRS, LOTRItemGroups.DECO);
		ExtendedItems.MUD_FARMLAND = regBlockItemB(ExtendedBlocks.MUD_FARMLAND, LOTRItemGroups.DECO);

		ExtendedItems.LARGE_POT = regBlockItemB(ExtendedBlocks.LARGE_POT, LOTRItemGroups.DECO);
		ExtendedItems.LARGE_FLOWER_POT = regBlockItemB(ExtendedBlocks.LARGE_FLOWER_POT, LOTRItemGroups.DECO);
		ExtendedItems.STONE_CHEST = regBlockItemB(ExtendedBlocks.STONE_CHEST, LOTRItemGroups.DECO);
		ExtendedItems.REED_BASKET = regBlockItemB(ExtendedBlocks.REED_BASKET, LOTRItemGroups.DECO);
		ExtendedItems.ORC_BARREL = regBlockItemB(ExtendedBlocks.ORC_BARREL, LOTRItemGroups.DECO);
		ExtendedItems.WEAPON_RACK = regBlockItemB(ExtendedBlocks.WEAPON_RACK, LOTRItemGroups.DECO);
		//BANNERS
		ExtendedItems.DUNEDAIN_BANNER = regItemB("dunedain_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DUNEDAIN_BANNER.get(), ExtendedBlocks.DUNEDAIN_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
		ExtendedItems.HOBBIT_BANNER = regItemB("hobbit_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.HOBBIT_BANNER.get(), ExtendedBlocks.HOBBIT_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
		ExtendedItems.DURINS_FOLK_BANNER = regItemB("durins_folk_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.DURINS_FOLK_BANNER.get(), ExtendedBlocks.DURINS_FOLK_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
		ExtendedItems.BLUE_MOUNTAINS_BANNER = regItemB("blue_mountains_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.BLUE_MOUNTAINS_BANNER.get(), ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
		ExtendedItems.GUNDABAD_ORC_BANNER = regItemB("gundabad_orc_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.GUNDABAD_ORC_BANNER.get(), ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));
		ExtendedItems.BREE_BANNER = regItemB("bree_banner", () -> new ExtendedMCBannerItem(ExtendedBlocks.BREE_BANNER.get(), ExtendedBlocks.BREE_WALL_BANNER.get(), new Properties().stacksTo(16).tab(LOTRItemGroups.DECO)));

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

		// Goblin Bricks
		ExtendedItems.GOBLIN_BRICK = regBlockItemB(ExtendedBlocks.GOBLIN_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOBLIN_BRICK_SLAB = regBlockItemB(ExtendedBlocks.GOBLIN_BRICK_SLAB, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOBLIN_BRICK_STAIRS = regBlockItemB(ExtendedBlocks.GOBLIN_BRICK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOBLIN_BRICK_WALL = regBlockItemB(ExtendedBlocks.GOBLIN_BRICK_WALL, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOBLIN_PILLAR = regBlockItemB(ExtendedBlocks.GOBLIN_PILLAR, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOBLIN_PILLAR_SLAB = regBlockItemB(ExtendedBlocks.GOBLIN_PILLAR_SLAB, LOTRItemGroups.BLOCKS);
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

		ExtendedItems.ROHAN_BEAM = regBlockItemB(ExtendedBlocks.ROHAN_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.ORNATE_ROHAN_BEAM = regBlockItemB(ExtendedBlocks.ORNATE_ROHAN_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.GOLDEN_ROHAN_BEAM = regBlockItemB(ExtendedBlocks.GOLDEN_ROHAN_BEAM, LOTRItemGroups.BLOCKS);
		ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM = regBlockItemB(ExtendedBlocks.ORNATE_GOLDEN_ROHAN_BEAM, LOTRItemGroups.BLOCKS);

		ExtendedItems.SMOOTH_GONDOR_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_GONDOR_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_BLACK_GONDOR_BRICK = regBlockItemB(ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_BLACK_UMBAR_BRICK = regBlockItemB(ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK, LOTRItemGroups.BLOCKS);
		ExtendedItems.CARVED_UMBAR_BRICK = regBlockItemB(ExtendedBlocks.CARVED_UMBAR_BRICK, LOTRItemGroups.BLOCKS);

		ExtendedItems.ORC_STEEL_DOOR = regTallBlockItemB(ExtendedBlocks.ORC_STEEL_DOOR, LOTRItemGroups.UTIL);
		ExtendedItems.ORC_STEEL_TRAPDOOR = regBlockItemB(ExtendedBlocks.ORC_STEEL_TRAPDOOR, LOTRItemGroups.UTIL);

		ExtendedItems.SMOOTH_MORDOR_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_MORDOR_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_ROHAN_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_ROHAN_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_BLUE_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_BLUE_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
		ExtendedItems.SMOOTH_RED_STAIRS = regBlockItemB(ExtendedBlocks.SMOOTH_RED_ROCK_STAIRS, LOTRItemGroups.BLOCKS);
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

		//Bone Tools
		ExtendedItems.BONE_SHOVEL = regItemB("bone_shovel", () -> new LOTRShovelItem(LOTRMaterial.valueOf("EXT_BONE")));
		ExtendedItems.BONE_PICKAXE = regItemB("bone_pickaxe", () -> new LOTRPickaxeItem(LOTRMaterial.valueOf("EXT_BONE")));
		ExtendedItems.BONE_AXE = regItemB("bone_axe", () -> new LOTRAxeItem(LOTRMaterial.valueOf("EXT_BONE")));
		ExtendedItems.BONE_HOE = regItemB("bone_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("EXT_BONE")));

		//Dol Guldur Tools
		ExtendedItems.DOL_GULDUR_SHOVEL = regItemB("dol_guldur_shovel", () -> new LOTRShovelItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_PICKAXE = regItemB("dol_guldur_pickaxe", () -> new LOTRPickaxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_AXE = regItemB("dol_guldur_axe", () -> new LOTRAxeItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_HOE = regItemB("dol_guldur_hoe", () -> new LOTRHoeItem(LOTRMaterial.valueOf("DOL_GULDUR")));

		ExtendedItems.BRANDING_IRON = regItemB("branding_iron", ExtendedBrandingIronItem::new);
		ExtendedItems.CHISEL = regItemB("chisel", () -> new ExtendedChiselItem(new Properties().tab(LOTRItemGroups.TOOLS).defaultDurability(100)));
		//######################################################################FOOD########################################################
		//Berries
		ExtendedItems.BLACKBERRY = regItemB("blackberry", () -> new ExtendedBerries(ExtendedBlocks.BLACKBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLACKBERRY)));
		ExtendedItems.BLUEBERRY = regItemB("blueberry", () -> new ExtendedBerries(ExtendedBlocks.BLUEBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BLUEBERRY)));
		ExtendedItems.CRANBERRY = regItemB("cranberry", () -> new ExtendedBerries(ExtendedBlocks.CRANBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CRANBERRY)));
		ExtendedItems.ELDERBERRY = regItemB("elderberry", () -> new ExtendedBerries(ExtendedBlocks.ELDERBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ELDERBERRY)));
		ExtendedItems.RASPBERRY = regItemB("raspberry", () -> new ExtendedBerries(ExtendedBlocks.RASPBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.RASPBERRY)));
		ExtendedItems.WILDBERRY = regItemB("wildberry", ()-> new ExtendedBerries(ExtendedBlocks.WILDBERRY_BUSH.get(), new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.WILDBERRY)));
		ExtendedItems.BOTTLE_OF_POISON = regItemB("bottle_of_poison", () -> new ExtendedPoisonBottleItem(new Properties().stacksTo(16).tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_CURDS)));
		ExtendedItems.BUCKET_OF_CHEESE_CURDS = regItemB("bucket_of_cheese_curds", () -> new ExtendedBucketOfCheeseCurds(new Properties().stacksTo(32).tab(LOTRItemGroups.FOOD)));
		ExtendedItems.CHEESE_CURDS = regItemB("cheese_curds", () -> new Item(new Properties().stacksTo(32).tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_CURDS)));
		ExtendedItems.CHEESE_WHEEL  = regCakeBlockItemD(ExtendedBlocks.CHEESE_WHEEL, LOTRItemGroups.FOOD);
		ExtendedItems.AGED_CHEESE_WHEEL  = regCakeBlockItemD(ExtendedBlocks.AGED_CHEESE_WHEEL, LOTRItemGroups.FOOD);
		ExtendedItems.CHEESE_SLICE = regItemB("cheese_slice", () -> new ExtendedUseItemAwardStatItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.CHEESE_SLICE), ExtendedStats.EAT_CHEESE_SLICE));
		ExtendedItems.AGED_CHEESE_SLICE = regItemB("aged_cheese_slice", () -> new ExtendedUseItemAwardStatItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.AGED_CHEESE_SLICE), ExtendedStats.EAT_CHEESE_SLICE));

		ExtendedItems.HOBBIT_PANCAKE = regItemB("hobbit_pancake", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.HOBBIT_PANCAKE)));
		ExtendedItems.HOBBIT_PANCAKE_SYRUP = regItemB("hobbit_pancake_syrup", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.HOBBIT_PANCAKE_SYRUP)));
		ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER = regItemB("hobbit_pancake_syrup_butter", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.HOBBIT_PANCAKE_SYRUP_BUTTER)));
		ExtendedItems.BUTTER = regItemB("butter", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BUTTER)));

		ExtendedItems.BANANA = regItemB("banana", () -> new CompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BANANA), 0.65F));
		ExtendedItems.BANANA_BREAD = regItemB("banana_bread", () -> new CompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.BANANA_BREAD), 0.85F));
		ExtendedItems.BANANA_CAKE  = regCakeBlockItemD(ExtendedBlocks.BANANA_CAKE, LOTRItemGroups.FOOD);


		ExtendedItems.ROASTED_CHESTNUT = regItemB("roasted_chestnut", () -> new CompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.ROASTED_CHESTNUT), 0.5F));
		ExtendedItems.PLUM = regItemB("plum", () -> new CompostableItem(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.PLUM), 0.65F));

		ExtendedItems.DUCK_EGG = regItemB("duck_egg", () -> new ExtendedDuckEggItem(new Properties().stacksTo(16).tab(LOTRItemGroup.TAB_FOOD)));
		ExtendedItems.RAW_DUCK = regItemB("raw_duck", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.DUCK)));
		ExtendedItems.COOKED_DUCK = regItemB("cooked_duck", () -> new Item(new Properties().tab(LOTRItemGroups.FOOD).food(ExtendedFoods.COOKED_DUCK)));

		ExtendedItems.SKULL_GOBLET = regEmptyVesselBlockItemD(ExtendedBlocks.SKULL_GOBLET, VesselType.valueOf("SKULL_GOBLET"));
		ExtendedItems.BANANA_BEER = regItemB("banana_beer", () -> VesselDrinkItem.newAlcohol(0.5F, 4, 0.6F));
		ExtendedItems.PLUM_KVASS = regItemB("plum_kvass", () -> VesselDrinkItem.newAlcohol(0.2F, 4, 0.4F));
		//#######################################################################EQUIPMENT##################################################

		//Generic Orc Equipment
		ExtendedItems.ORC_BOW = regItemB("orc_bow", () -> new ExtendedBowItem(ExtendedBowMaterial.ORC));

		//Morgul Equipment
		ExtendedItems.MORGUL_HELMET = regItemB("morgul_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.MORGUL_CHESTPLATE = regItemB("morgul_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.CHEST));
		ExtendedItems.MORGUL_LEGGINGS = regItemB("morgul_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.LEGS));
		ExtendedItems.MORGUL_BOOTS = regItemB("morgul_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("MORGUL"), EquipmentSlotType.FEET));
		ExtendedItems.MORGUL_BLADE = regItemB("morgul_blade",() -> new LOTRSwordItem(LOTRMaterial.valueOf("MORGUL")));

		//Taurethrim Equipment
		ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET = regItemB("taurethrim_chieftain_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD,"chieftain_helmet"));
		ExtendedItems.TAURETHRIM_HELMET = regItemB("taurethrim_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.HEAD));
		ExtendedItems.TAURETHRIM_CHESTPLATE = regItemB("taurethrim_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.CHEST));
		ExtendedItems.TAURETHRIM_LEGGINGS = regItemB("taurethrim_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.LEGS));
		ExtendedItems.TAURETHRIM_BOOTS = regItemB("taurethrim_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("TAURETHRIM"), EquipmentSlotType.FEET));
		ExtendedItems.TAURETHRIM_SWORD = regItemB("taurethrim_sword",() -> new HammerItem(LOTRMaterial.valueOf("TAURETHRIM")).noClink());
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

		//Bone Equipment
		ExtendedItems.BONE_SWORD = regItemB("bone_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("EXT_BONE")));
		ExtendedItems.BONE_SPEAR = regItemB("bone_spear",() -> new SpearItem(LOTRMaterial.valueOf("EXT_BONE")));
		ExtendedItems.BONE_DAGGER = regItemB("bone_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("EXT_BONE")));

		//Warg Equipment
		ExtendedItems.BLACK_WARG_HELMET = regItemB("black_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
		ExtendedItems.BLACK_WARG_CHESTPLATE = regItemB("black_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.CHEST));
		ExtendedItems.BLACK_WARG_LEGGINGS = regItemB("black_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.LEGS));
		ExtendedItems.BLACK_WARG_BOOTS = regItemB("black_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BLACK_WARG"), EquipmentSlotType.FEET));
		ExtendedItems.BROWN_WARG_HELMET = regItemB("warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
		ExtendedItems.BROWN_WARG_CHESTPLATE = regItemB("warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.CHEST));
		ExtendedItems.BROWN_WARG_LEGGINGS = regItemB("warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.LEGS));
		ExtendedItems.BROWN_WARG_BOOTS = regItemB("warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("BROWN_WARG"), EquipmentSlotType.FEET));
		ExtendedItems.GRAY_WARG_HELMET = regItemB("gray_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
		ExtendedItems.GRAY_WARG_CHESTPLATE = regItemB("gray_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.CHEST));
		ExtendedItems.GRAY_WARG_LEGGINGS = regItemB("gray_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.LEGS));
		ExtendedItems.GRAY_WARG_BOOTS = regItemB("gray_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GRAY_WARG"), EquipmentSlotType.FEET));
		ExtendedItems.SILVER_WARG_HELMET = regItemB("silver_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
		ExtendedItems.SILVER_WARG_CHESTPLATE = regItemB("silver_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.CHEST));
		ExtendedItems.SILVER_WARG_LEGGINGS = regItemB("silver_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.LEGS));
		ExtendedItems.SILVER_WARG_BOOTS = regItemB("silver_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("SILVER_WARG"), EquipmentSlotType.FEET));
		ExtendedItems.WHITE_WARG_HELMET = regItemB("white_warg_fur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.HEAD, "fur_hat"));
		ExtendedItems.WHITE_WARG_CHESTPLATE = regItemB("white_warg_fur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.CHEST));
		ExtendedItems.WHITE_WARG_LEGGINGS = regItemB("white_warg_fur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.LEGS));
		ExtendedItems.WHITE_WARG_BOOTS = regItemB("white_warg_fur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("WHITE_WARG"), EquipmentSlotType.FEET));

		//Dol Guldur Equipment
		ExtendedItems.DOL_GULDUR_HELMET = regItemB("dol_guldur_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.HEAD));
		ExtendedItems.DOL_GULDUR_CHESTPLATE = regItemB("dol_guldur_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.CHEST));
		ExtendedItems.DOL_GULDUR_LEGGINGS = regItemB("dol_guldur_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.LEGS));
		ExtendedItems.DOL_GULDUR_BOOTS = regItemB("dol_guldur_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("DOL_GULDUR"), EquipmentSlotType.FEET));
		ExtendedItems.DOL_GULDUR_SWORD = regItemB("dol_guldur_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_SPEAR = regItemB("dol_guldur_spear",() -> new SpearItem(LOTRMaterial.valueOf("DOL_GULDUR")));
		ExtendedItems.DOL_GULDUR_DAGGER = regItemB("dol_guldur_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("DOL_GULDUR")));

		//Gundabad Uruk Equipment
		ExtendedItems.GUNDABAD_URUK_HELMET = regItemB("gundabad_uruk_helmet", () -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.HEAD, "helmet"));
		ExtendedItems.GUNDABAD_URUK_CHESTPLATE = regItemB("gundabad_uruk_chestplate",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.CHEST));
		ExtendedItems.GUNDABAD_URUK_LEGGINGS = regItemB("gundabad_uruk_leggings",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.LEGS));
		ExtendedItems.GUNDABAD_URUK_BOOTS = regItemB("gundabad_uruk_boots",() -> new LOTRArmorItem(LOTRMaterial.valueOf("GUNDABAD_URUK"), EquipmentSlotType.FEET));
		ExtendedItems.GUNDABAD_SHIELD = regItemB("gundabad_shield", () -> new LOTRShieldItem(LOTRMaterial.MORDOR));
		ExtendedItems.GUNDABAD_URUK_CLEAVER = regItemB("gundabad_uruk_sword",() -> new LOTRSwordItem(LOTRMaterial.valueOf("GUNDABAD_URUK")));
		ExtendedItems.GUNDABAD_URUK_SPEAR = regItemB("gundabad_uruk_spear",() -> new SpearItem(LOTRMaterial.valueOf("GUNDABAD_URUK")));
		ExtendedItems.GUNDABAD_URUK_DAGGER = regItemB("gundabad_uruk_dagger",() -> new ExtendedDaggerItem(LOTRMaterial.valueOf("GUNDABAD_URUK")));
		ExtendedItems.GUNDABAD_URUK_BOW = regItemB("gundabad_uruk_bow",() -> new ExtendedBowItem(ExtendedBowMaterial.GUNDABAD_URUK));

		ExtendedItems.LONGBOW = regItemB("longbow", () -> new ExtendedBowItem(ExtendedBowMaterial.GENERIC));
		
		//Ranger of the North equipment
		ExtendedItems.DUNEDAIN_SHIELD = regItemB("dunedain_shield", () -> new LOTRShieldItem(LOTRMaterial.RANGER_NORTH));
		ExtendedItems.DUNEDAIN_BOW = regItemB("dunedain_bow",() -> new ExtendedBowItem(ExtendedBowMaterial.RANGER_NORTH));

		//Bree equipment
		ExtendedItems.BREE_SHIELD = regItemB("bree_shield", () -> new LOTRShieldItem(LOTRMaterial.valueOf("BREE")));

		//Blue Mountains equipment
		ExtendedItems.BLUE_DUNEDAIN_SHIELD = regItemB("blue_dwarven_shield", () -> new LOTRShieldItem(LOTRMaterial.BLUE_DWARVEN));

		// misc equipment
		ExtendedItems.LEATHER_HAT = regItemB("leather_hat", () -> new ExtendedDyeableLOTRArmorItem(LOTRMaterial.COSMETIC, EquipmentSlotType.HEAD, "leather_hat"));
		ExtendedItems.BARROW_BLADE = regItemB("barrow_blade", ExtendedBarrowBlade::new);
		ExtendedItems.COMMANDHORN = regItemB("horn_of_command", ExtendedCommandHornItem::new);

		//########################################################################UTIL##########################################################
		//Crafting Tables
		ExtendedItems.DOL_GULDUR_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.NORTHERN_ORC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.NORTHERN_ORC_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.HALF_TROLL_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.MORWAITH_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.MORWAITH_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.RHUNIC_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.RHUNIC_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.TAURETHRIM_CRAFTING_TABLE = regBlockItemB(ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.CONQUEST_TABLE = regBlockItemB(ExtendedBlocks.CONQUEST_TABLE, LOTRItemGroups.UTIL);
		ExtendedItems.STONE_GRINDER = regBlockItemB(ExtendedBlocks.STONE_GRINDER, LOTRItemGroups.UTIL);
		ExtendedItems.CRUCIBLE = regBlockItemB(ExtendedBlocks.CRUCIBLE, LOTRItemGroups.UTIL);

		//########################################################################MISC##########################################################

		ExtendedItems.HOBBIT_MARRIAGE_RING =  regItemB("hobbit_marriage_ring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
		ExtendedItems.DWARF_MARRIAGE_RING =  regItemB("dwarven_marriage_ring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
		ExtendedItems.SILVER_COIN_ONE = regItemB("silver_coin_one", () -> new ExtendedSilverCoin(new Properties().tab(LOTRItemGroups.MISC), CoinType.ONE));
		ExtendedItems.SILVER_COIN_TEN = regItemB("silver_coin_ten", () -> new ExtendedSilverCoin(new Properties().tab(LOTRItemGroups.MISC), CoinType.TEN));
		ExtendedItems.SILVER_COIN_HUNDRED = regItemB("silver_coin_hundred", () -> new ExtendedSilverCoin(new Properties().tab(LOTRItemGroups.MISC), CoinType.HUNDRED));
		ExtendedItems.BOWSTRING = regItemB("bowstring", () -> new Item(new Properties().tab(LOTRItemGroups.MISC)));
		ExtendedItems.CONKER =  regItemB("conker", () -> new ExtendedThrowableConkerItem(new Properties().tab(LOTRItemGroups.MISC).stacksTo(16)));
		ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN = regBannerPaternItem("blue_mountains_banner_pattern", ExtendedBannerPatterns.blue_mountains, () -> new BannerPatternItem(ExtendedBannerPatterns.blue_mountains, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
		ExtendedItems.DURINS_FOLK_BANNER_PATTERN = regBannerPaternItem("durin_banner_pattern", ExtendedBannerPatterns.durin, () -> new BannerPatternItem(ExtendedBannerPatterns.durin, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
		ExtendedItems.DUNEDAIN_BANNER_PATTERN = regBannerPaternItem("dunedain_banner_pattern", ExtendedBannerPatterns.dunedain, () -> new BannerPatternItem(ExtendedBannerPatterns.dunedain, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
		ExtendedItems.GUNDABAD_ORC_BANNER_PATTERN = regBannerPaternItem("gundabad_orc_banner_pattern", ExtendedBannerPatterns.gundabad_orc, () -> new BannerPatternItem(ExtendedBannerPatterns.gundabad_orc, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
		ExtendedItems.HOBBIT_BANNER_PATTERN = regBannerPaternItem("hobbit_banner_pattern", ExtendedBannerPatterns.hobbit, () -> new BannerPatternItem(ExtendedBannerPatterns.hobbit, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
		ExtendedItems.BREE_BANNER_PATTERN = regBannerPaternItem("bree_banner_pattern", ExtendedBannerPatterns.bree, () -> new BannerPatternItem(ExtendedBannerPatterns.bree, new Properties().stacksTo(1).tab(LOTRItemGroups.MISC)));
		ExtendedItems.ENTITY_SPAWNER = regItemB("structure_entity_spawner", () -> new ExtendedEntitySpawnerItem(new Properties().tab(LOTRItemGroups.MISC).fireResistant()));
		ExtendedItems.ROPE = regExtendedBlockItemD(ExtendedBlocks.ROPE, block -> new BlockItem(block, new Properties().stacksTo(64).tab(LOTRItemGroups.MISC)));

		// HIDDEN
		ExtendedItems.GRASS_VOID = regHiddenBlockItemB(ExtendedBlocks.GRASS_VOID);
	}

	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> blackFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedBlackFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> furBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> grayFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedGrayFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> silverFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedSilverFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> whiteFurBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedWhiteFurBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> orcBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedOrcBedTileEntity::new);
	}
	/**
	 * Supplier for getting bed tileEntity renderers
	 * @return A ItemStackTileEntityRenderer
	 * */
	@Unique
	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> strawBedRenderer() {
		return () -> new ExtendedItemRenderHelper<>(ExtendedStrawBedTileEntity::new);
	}
}
