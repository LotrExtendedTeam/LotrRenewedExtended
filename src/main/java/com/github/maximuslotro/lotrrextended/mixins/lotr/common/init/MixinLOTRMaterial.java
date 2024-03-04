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
import net.minecraft.util.IItemProvider;
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
	
	private static final LOTRMaterial MORGUL = lotrmaterialExpansion$addVariant("MORGUL", "morgul", 2, 450, 5.5F, 3.0F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, () -> {
		return Ingredient.of(new IItemProvider[]{(IItemProvider) ExtendedItems.MORGUL_STEEL_INGOT.get()});}, new LOTRMaterial.Specials[0]);

	private static final LOTRMaterial TAURETHRIM = lotrmaterialExpansion$addVariant("TAURETHRIM", "taurethrim", 2, 300, 5.5F, 2.5F, 10, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, () -> {
		return Ingredient.of(new IItemProvider[]{(IItemProvider) ExtendedItems.OBSIDIAN_SHARD.get()});},() -> {
			return Ingredient.of(new IItemProvider[]{(IItemProvider) LOTRItems.BRONZE_INGOT.get()});}, new LOTRMaterial.Specials[0]);

	private static final LOTRMaterial GOLDEN_TAURETHRIM = lotrmaterialExpansion$addVariant("GOLDEN_TAURETHRIM", "golden_taurethrim", 2, 450, 0, 0, 8, 0.65F, SoundEvents.ARMOR_EQUIP_GOLD, 0, () -> {
		return Ingredient.of(new IItemProvider[]{(IItemProvider) Items.INGOTS_GOLD});}, new LOTRMaterial.Specials[0]);


	private static final LOTRMaterial ANGMAR = lotrmaterialExpansion$addVariant("ANGMAR", "angmar", 2, 450, 5.5F, 3.0F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, () -> {
		return Ingredient.of(new IItemProvider[]{(IItemProvider) LOTRItems.ORC_STEEL_INGOT.get()});}, new LOTRMaterial.Specials[0]);

	private static final LOTRMaterial DOL_GULDUR = lotrmaterialExpansion$addVariant("DOL_GULDUR", "dol_guldur", 2, 450, 5.5F, 3.0F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, () -> {
		return Ingredient.of(new IItemProvider[]{(IItemProvider) LOTRItems.ORC_STEEL_INGOT.get()});}, new LOTRMaterial.Specials[0]);

	
	@Invoker("<init>")
	public static LOTRMaterial lotrmaterialExpansion$invokeInit(String internalName, int internalId, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, Supplier<Ingredient> armorRepair, LOTRMaterial.Specials[] specs) {
		throw new AssertionError();
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
