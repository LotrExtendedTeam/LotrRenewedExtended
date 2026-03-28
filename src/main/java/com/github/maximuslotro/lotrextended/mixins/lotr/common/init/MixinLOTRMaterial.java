package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Items;

/**
 * Used to register new LOTRMaterial instances, as Renewed requires this material type to be used for its equipment classes
 * @Mixin (LOTRMaterial.class)
 * */
@Mixin(LOTRMaterial.class)
public class MixinLOTRMaterial {

	@SuppressWarnings("target")
	/*
	 * @reason This value is the runtime array variable for the LOTRMaterial
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * @Mutable Tell Mixin that it can modify the Final field
	 * @Dynamic This field is a runtime fabricated field
	 * */
	@Shadow(remap = false)
	@Final
	@Mutable
	@Dynamic
	private static LOTRMaterial[] $VALUES;

	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial MORGUL = lE$addVariant("MORGUL", "morgul", 2, 450, 5.5F, 2.5F, 8, 0.7F, SoundEvents.ARMOR_EQUIP_IRON, 0,
									() -> Ingredient.of(ExtendedItems.MORGUL_STEEL_INGOT.get()), new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH});
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial MORGUL_VASSAL = lE$addVariant("MORGUL_VASSAL", "morgul_vassal", 2, 450, 5.5F, 2.5F, 8, 0.7F, SoundEvents.ARMOR_EQUIP_IRON, 0,
									() -> Ingredient.of(ExtendedItems.MORGUL_STEEL_INGOT.get()), new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH});
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial TAURETHRIM = lE$addVariant("TAURETHRIM", "taurethrim", 2, 300, 5.5F, 2.5F, 10, 0.5F, SoundEvents.ARMOR_EQUIP_IRON, 0,
									() -> Ingredient.of(ExtendedItems.OBSIDIAN_SHARD.get()),() -> Ingredient.of(LOTRItems.BRONZE_INGOT.get()), new LOTRMaterial.Specials[0]);
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial GOLDEN_TAURETHRIM = lE$addVariant("GOLDEN_TAURETHRIM", "golden_taurethrim", 2, 450, 0, 0, 8, 0.6F, SoundEvents.ARMOR_EQUIP_GOLD, 0,
									() -> Ingredient.of(Items.INGOTS_GOLD));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial BLACK_WARG = lE$addVariant("BLACK_WARG", "black_warg", 0, 230, 0, 0, 8, 0.4F, SoundEvents.ARMOR_EQUIP_LEATHER, 0,
									() -> Ingredient.of(ExtendedItems.BLACK_FUR.get()));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial BROWN_WARG = lE$addVariant("BROWN_WARG", "brown_warg", 0, 230, 0, 0, 8, 0.4F, SoundEvents.ARMOR_EQUIP_LEATHER, 0,
									() -> Ingredient.of(LOTRItems.FUR.get()));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial GRAY_WARG = lE$addVariant("GRAY_WARG", "gray_warg", 0, 230, 0, 0, 8, 0.4F, SoundEvents.ARMOR_EQUIP_LEATHER, 0,
									() -> Ingredient.of(ExtendedItems.GRAY_FUR.get()));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial SILVER_WARG = lE$addVariant("SILVER_WARG", "silver_warg", 0, 230, 0, 0, 8, 0.4F, SoundEvents.ARMOR_EQUIP_LEATHER, 0,
									() -> Ingredient.of(ExtendedItems.SILVER_FUR.get()));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial WHITE_WARG = lE$addVariant("WHITE_WARG", "white_warg", 0, 230, 0, 0, 8, 0.4F, SoundEvents.ARMOR_EQUIP_LEATHER, 0,
									() -> Ingredient.of(ExtendedItems.WHITE_FUR.get()));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial ANGMAR = lE$addVariant("ANGMAR", "angmar", 2, 350, 5.5F, 2.5F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0,
									() -> Ingredient.of(LOTRItems.ORC_STEEL_INGOT.get()), new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH});
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial DOL_GULDUR = lE$addVariant("DOL_GULDUR", "dol_guldur", 2, 350, 5.5F, 2.5F, 10, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0,
									() -> Ingredient.of(LOTRItems.ORC_STEEL_INGOT.get()), new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH});
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial GUNDABAD_URUK = lE$addVariant("GUNDABAD_URUK", "gundabad_uruk", 2, 550, 6.0F, 3.0F, 4, 0.7F, SoundEvents.ARMOR_EQUIP_IRON, 0,
			() -> Ingredient.of(LOTRItems.URUK_STEEL_INGOT.get()), new LOTRMaterial.Specials[]{LOTRMaterial.Specials.MAN_FLESH});
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial EXT_BONE = lE$addVariant("EXT_BONE", "ext_bone", 0, 150, 3.0F, 2.0F, 10, 0.3F, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F,
									() -> Ingredient.of(Tags.Items.BONES));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial BREE = lE$addVariant("BREE", "bree", 2, 300, 6.0F, 2.0F, 10, 0.5F, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F,
			() -> Ingredient.of(Tags.Items.INGOTS_IRON));
	/**
	 * Local Extended LOTRMaterial instance
	 * */
	@Unique
	private static final LOTRMaterial IRON = lE$addVariant("IRON", "iron", 2, 300, 6.0F, 2.0F, 10, 0.5F, SoundEvents.ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.of(Tags.Items.INGOTS_IRON));
	@Unique
	private static final LOTRMaterial IMLADRIS_GUARD = lE$addVariant("IMLADRIS_GUARD", "imladris_guard", 0, 950, 0, 0, 10, 0.7F, SoundEvents.ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.of(LOTRItems.ELVEN_STEEL_INGOT.get()));
	@Unique
	private static final LOTRMaterial ANDURIL = lE$addVariant("ANDURIL", "anduril", 2, 1500, 0.0f, 5.0f, 14, 0.0F, SoundEvents.ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.of(Tags.Items.INGOTS_IRON));
	@Unique
	private static final LOTRMaterial EREGION = lE$addVariant("EREGION", "eregion", 0, 950, 0, 0, 10, 0.7F, SoundEvents.ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.of(LOTRItems.ELVEN_STEEL_INGOT.get()));
	@Unique
	private static final LOTRMaterial GALVORN = lE$addVariant("GALVORN", "galvorn", 2, 600, 7.0f, 3.0f, 15, 0.7F, SoundEvents.ARMOR_EQUIP_IRON, 0.0F,
			() -> Ingredient.of(ExtendedItems.GALVORN_INGOT.get()));
	

	/**
	 * Accessing the Enum creation method, DO NOT CALL THIS METHOD DIRECTLY, CALL THE HELPER METHODS
	 * @Invoker ("&lt;init&gt;")
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param internalId The internal id (ordinal) of this Enum entry
	 * @param name The localized name of this Enum
	 * @param toolTier The tier of this material's tools
	 * @param uses The durability of this material type
	 * @param efficiency The tool speed of this material
	 * @param atk The attack damage of this tool type
	 * @param ench The enchantability of this material
	 * @param pr The protection level of this equipment type
	 * @param sound The equip sound for this equipment type
	 * @param tough The additional toughness for this equipment type
	 * @param repair The item repair ingredient for this material
	 * @param armorRepair The armor repair ingredient for this material
	 * @param specs Additional special modifiers for this material
	 * @return A built LOTRMaterial instance
	 * */
	@Invoker("<init>")
	public static LOTRMaterial lotrmaterialExpansion$invokeInit(String internalName, int internalId, String name, int toolTier, int uses, float efficiency, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, Supplier<Ingredient> armorRepair, LOTRMaterial.Specials[] specs) {
		throw new AssertionError();
	}
	/**
	 * Accessing the Enum creation method, with single repair ingredient
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param name The localized name of this Enum
	 * @param toolTier The tier of this material's tools
	 * @param uses The durability of this material type
	 * @param efficiency The tool speed of this material
	 * @param atk The attack damage of this tool type
	 * @param ench The enchantability of this material
	 * @param pr The protection level of this equipment type
	 * @param sound The equip sound for this equipment type
	 * @param tough The additional toughness for this equipment type
	 * @param repair The item+armor repair ingredient for this material
	 * @return A built LOTRMaterial instance
	 * @Unique There will only be one instance of this
	 * */
	@Unique
	private static LOTRMaterial lE$addVariant(String internalName, String name, int toolTier, int uses, float efficiency, float atk, int ench, float pr, SoundEvent sound,
																 float tough, Supplier<Ingredient> repair) {
		return lE$addVariant(internalName, name, toolTier, uses, efficiency, atk, ench, pr, sound, tough, repair, repair, new LOTRMaterial.Specials[0]);
	}
	/**
	 * Accessing the Enum creation method, with single repair ingredient and special modifiers
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param name The localized name of this Enum
	 * @param toolTier The tier of this material's tools
	 * @param uses The durability of this material type
	 * @param efficiency The tool speed of this material
	 * @param atk The attack damage of this tool type
	 * @param ench The enchantability of this material
	 * @param pr The protection level of this equipment type
	 * @param sound The equip sound for this equipment type
	 * @param tough The additional toughness for this equipment type
	 * @param repair The item+armor repair ingredient for this material
	 * @param specs Additional special modifiers for this material
	 * @return A built LOTRMaterial instance
	 * @Unique There will only be one instance of this
	 * */
	@Unique
	private static LOTRMaterial lE$addVariant(String internalName, String name, int toolTier, int uses, float efficiency, float atk, int ench, float pr, SoundEvent sound,
																 float tough, Supplier<Ingredient> repair, LOTRMaterial.Specials[] specs) {
		return lE$addVariant(internalName, name, toolTier, uses, efficiency, atk, ench, pr, sound, tough, repair, repair, specs);
	}	/**
	 * Accessing the Enum creation method, with full parameter set
	 * @param internalName The internal name for the Enum entry, must be uppercase and match the variable name
	 * @param name The localized name of this Enum
	 * @param toolTier The tier of this material's tools
	 * @param uses The durability of this material type
	 * @param efficiency The tool speed of this material
	 * @param atk The attack damage of this tool type
	 * @param ench The enchantability of this material
	 * @param pr The protection level of this equipment type
	 * @param sound The equip sound for this equipment type
	 * @param tough The additional toughness for this equipment type
	 * @param repair The item repair ingredient for this material
	 * @param armorRepair The armor repair ingredient for this material
	 * @param specs Additional special modifiers for this material
	 * @return A built LOTRMaterial instance
	 * @Unique There will only be one instance of this
	 * */
	@Unique
	private static LOTRMaterial lE$addVariant(String internalName, String name, int toolTier, int uses, float efficiency, float atk, int ench, float pr, SoundEvent sound,
																 float tough, Supplier<Ingredient> repair, Supplier<Ingredient> armorRepair, LOTRMaterial.Specials[] specs) {
		ArrayList<LOTRMaterial> variants = new ArrayList<>(Arrays.asList(MixinLOTRMaterial.$VALUES));
		LOTRMaterial instrument = lotrmaterialExpansion$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, name, toolTier, uses, efficiency, atk, ench, pr, sound, tough, repair, armorRepair, specs);
		variants.add(instrument);
		MixinLOTRMaterial.$VALUES = variants.toArray(new LOTRMaterial[0]);
		return instrument;
	}
}
