package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import lotr.common.entity.item.LOTRBoatEntity.ModBoatType;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.DatagenModLoader;

/**
 * Used to register new boat types, via Renewed's existing registration system
 * @Mixin (ModBoatType.class)
 * */
@Mixin(ModBoatType.class)
public class MixinModBoatType {
	@SuppressWarnings("target")
	/*
	 * @reason This value is the runtime array variable for the ModBoatType
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * @Dynamic This field is a runtime fabricated field
	 * */
	@Shadow(remap = false)
	@Final
	@Mutable
	@Dynamic
	private static ModBoatType[] $VALUES;

	/**
	 * Local Extended ModBoatType instance
	 * */
	@Unique
	private static final ModBoatType CHESTNUT = lotrModBoatType$addVariant("CHESTNUT", ExtendedBlocks.CHESTNUT_PLANKS, ExtendedItems.CHESTNUT_BOAT, "chestnut");
	/**
	 * Local Extended ModBoatType instance
	 * */
	@Unique
	private static final ModBoatType BANANA = lotrModBoatType$addVariant("BANANA", ExtendedBlocks.BANANA_PLANKS, ExtendedItems.BANANA_BOAT, "banana");
	/**
	 * Local Extended ModBoatType instance
	 * */
	@Unique
	private static final ModBoatType PLUM = lotrModBoatType$addVariant("PLUM", ExtendedBlocks.PLUM_PLANKS, ExtendedItems.PLUM_BOAT, "plum");
	/**
	 * Local Extended ModBoatType instance
	 * */
	@Unique
	private static final ModBoatType WILLOW = lotrModBoatType$addVariant("WILLOW", ExtendedBlocks.WILLOW_PLANKS, ExtendedItems.WILLOW_BOAT, "willow");

	/**
	 * Accessing the Enum creation method, DO NOT CALL THIS METHOD DIRECTLY, CALL THE HELPER METHODS
	 * @Invoker ("<init>")
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param internalId The internal id (ordinal) of this Enum entry
	 * @param plank A supplier for the boat's plank block
	 * @param boat A supplier for the boat's boat item
	 * @param name The texture id of the boat
	 * @return A built ModBoatType instance
	 * */
	@Invoker("<init>")
	public static ModBoatType lotrModBoatType$invokeInit(String internalName, int internalId, Supplier<Block> plank, Supplier<Item> boat, String name) {
		throw new AssertionError();
	}

	/**
	 * Accessing the Enum creation method
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param plank A supplier for the boat's plank block
	 * @param boat A supplier for the boat's boat item
	 * @param name The texture id of the boat
	 * @return A built ModBoatType instance
	 * */
	@Unique
	private static ModBoatType lotrModBoatType$addVariant(String internalName, Supplier<Block> plank, Supplier<Item> boat, String name) {
		ArrayList<ModBoatType> variants = new ArrayList<>(Arrays.asList(MixinModBoatType.$VALUES));
		ModBoatType instrument = lotrModBoatType$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, plank, boat, name);
		variants.add(instrument);
		MixinModBoatType.$VALUES = variants.toArray(new ModBoatType[0]);
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.newWoodTypesHolder.add(name);
		}
		return instrument;
	}
}
