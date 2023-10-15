package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.init.LOTRMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

@Mixin(LOTRMaterial.class)
@Unique
public class MixinLOTRMaterial {

	@Shadow(remap = false)
	@Final
	@Mutable
	private static LOTRMaterial[] $VALUES;
	
	private static final LOTRMaterial MORGUL = lotrmaterialExpansion$addVariant("MORGUL", "morgul", 2, 450, 6.0F, 3.0F, 8, 0.6F, SoundEvents.ARMOR_EQUIP_IRON, 0, () -> {
		return Ingredient.of(net.minecraftforge.common.Tags.Items.INGOTS_IRON);}, new LOTRMaterial.Specials[0]);
	
	@Invoker("<init>")
	public static LOTRMaterial lotrmaterialExpansion$invokeInit(String internalName, int internalId, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, LOTRMaterial.Specials[] specs) {
		throw new AssertionError();
	}

	private static LOTRMaterial lotrmaterialExpansion$addVariant(String internalName, String name, int lvl, int uses, float eff, float atk, int ench, float pr, SoundEvent sound,
			float tough, Supplier<Ingredient> repair, LOTRMaterial.Specials[] specs) {
		ArrayList<LOTRMaterial> variants = new ArrayList<LOTRMaterial>(Arrays.asList(MixinLOTRMaterial.$VALUES));
		LOTRMaterial instrument = lotrmaterialExpansion$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, name, lvl, uses, eff, atk, ench, pr, sound, tough, repair, specs);
		variants.add(instrument);
		MixinLOTRMaterial.$VALUES = variants.toArray(new LOTRMaterial[0]);
		return instrument;
	}
}
