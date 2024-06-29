package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import lotr.common.init.LOTRItems;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.Tags.Items;

@Mixin(LOTRMaterial.class)
@Unique
public class MixinLOTRMaterial {

	/**
	 * The field exists, just not until runtime
	 * */
	@Shadow(remap = false)
	@Final
	@Mutable
	@Dynamic
	private static LOTRMaterial[] $VALUES;
	
	private static final LOTRMaterial MORGUL = lotrmaterialExpansion$addVariant("MORGUL", "morgul", 2, 450, 5.5F, 2.5F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, 
									() -> Ingredient.of(ExtendedItems.MORGUL_STEEL_INGOT.get()));

	private static final LOTRMaterial TAURETHRIM = lotrmaterialExpansion$addVariant("TAURETHRIM", "taurethrim", 2, 300, 5.5F, 2.5F, 10, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, 
									() -> Ingredient.of(ExtendedItems.OBSIDIAN_SHARD.get()),() -> Ingredient.of(LOTRItems.BRONZE_INGOT.get()), new LOTRMaterial.Specials[0]);

	private static final LOTRMaterial GOLDEN_TAURETHRIM = lotrmaterialExpansion$addVariant("GOLDEN_TAURETHRIM", "golden_taurethrim", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, 
									() -> Ingredient.of(Items.INGOTS_GOLD));

	private static final LOTRMaterial BLACK_WARG = lotrmaterialExpansion$addVariant("BLACK_WARG", "black_warg", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, 
									() -> Ingredient.of(ExtendedItems.BLACK_FUR.get()));
	private static final LOTRMaterial BROWN_WARG = lotrmaterialExpansion$addVariant("BROWN_WARG", "brown_warg", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, 
									() -> Ingredient.of(LOTRItems.FUR.get()));
	private static final LOTRMaterial GRAY_WARG = lotrmaterialExpansion$addVariant("GRAY_WARG", "gray_warg", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, 
									() -> Ingredient.of(ExtendedItems.GRAY_FUR.get()));
	private static final LOTRMaterial SILVER_WARG = lotrmaterialExpansion$addVariant("SILVER_WARG", "silver_warg", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, 
									() -> Ingredient.of(ExtendedItems.SILVER_FUR.get()));
	private static final LOTRMaterial WHITE_WARG = lotrmaterialExpansion$addVariant("WHITE_WARG", "white_warg", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, 
									() -> Ingredient.of(ExtendedItems.WHITE_FUR.get()));

	private static final LOTRMaterial ANGMAR = lotrmaterialExpansion$addVariant("ANGMAR", "angmar", 2, 350, 5.5F, 2.5F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, 
									() -> Ingredient.of(LOTRItems.ORC_STEEL_INGOT.get()));

	private static final LOTRMaterial DOL_GULDUR = lotrmaterialExpansion$addVariant("DOL_GULDUR", "dol_guldur", 2, 350, 5.5F, 2.5F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, 
									() -> Ingredient.of(LOTRItems.ORC_STEEL_INGOT.get()));

	
	@Invoker("<init>")
	public static LOTRMaterial lotrmaterialExpansion$invokeInit(String internalName, int internalId, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, Supplier<Ingredient> armorRepair, LOTRMaterial.Specials[] specs) {
		throw new AssertionError();
	}

	private static LOTRMaterial lotrmaterialExpansion$addVariant(String internalName, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair) {
		return lotrmaterialExpansion$addVariant(internalName, name, lvl, uses, eff, atk, ench, pr, sound, tough, repair, repair, new LOTRMaterial.Specials[0]);
	}
	private static LOTRMaterial lotrmaterialExpansion$addVariant(String internalName, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, LOTRMaterial.Specials[] specs) {
		return lotrmaterialExpansion$addVariant(internalName, name, lvl, uses, eff, atk, ench, pr, sound, tough, repair, repair, specs);
	}
	private static LOTRMaterial lotrmaterialExpansion$addVariant(String internalName, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, Supplier<Ingredient> armorRepair, LOTRMaterial.Specials[] specs) {
		ArrayList<LOTRMaterial> variants = new ArrayList<LOTRMaterial>(Arrays.asList(MixinLOTRMaterial.$VALUES));
		LOTRMaterial instrument = lotrmaterialExpansion$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, name, lvl, uses, eff, atk, ench, pr, sound, tough, repair, armorRepair, specs);
		variants.add(instrument);
		MixinLOTRMaterial.$VALUES = variants.toArray(new LOTRMaterial[0]);
		return instrument;
	}
}
