package com.github.maximuslotro.lotrrextended.mixins.lotr.common.entity.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import lotr.common.entity.item.LOTRBoatEntity.ModBoatType;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.DatagenModLoader;

@Mixin(ModBoatType.class)
public class MixinModBoatType {

	@Shadow(remap = false)
	@Final
	@Mutable
	@Dynamic
	private static ModBoatType[] $VALUES;

	private static final ModBoatType CHESTNUT = lotrModBoatType$addVariant("CHESTNUT", ExtendedBlocks.CHESTNUT_PLANKS, ExtendedItems.CHESTNUT_BOAT, "chestnut");
	private static final ModBoatType BANANA = lotrModBoatType$addVariant("BANANA", ExtendedBlocks.BANANA_PLANKS, ExtendedItems.BANANA_BOAT, "banana");
	private static final ModBoatType PLUM = lotrModBoatType$addVariant("PLUM", ExtendedBlocks.PLUM_PLANKS, ExtendedItems.PLUM_BOAT, "plum");

	
	@Invoker("<init>")
	public static ModBoatType lotrModBoatType$invokeInit(String internalName, int internalId, Supplier<Block> plank, Supplier<Item> boat, String s) {
		throw new AssertionError();
	}

	private static ModBoatType lotrModBoatType$addVariant(String internalName, Supplier<Block> plank, Supplier<Item> boat, String s) {
		ArrayList<ModBoatType> variants = new ArrayList<ModBoatType>(Arrays.asList(MixinModBoatType.$VALUES));
		ModBoatType instrument = lotrModBoatType$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, plank, boat, s);
		variants.add(instrument);
		MixinModBoatType.$VALUES = variants.toArray(new ModBoatType[0]);
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.newWoodTypesHolder.add(s);
		}
		return instrument;
	}
}
