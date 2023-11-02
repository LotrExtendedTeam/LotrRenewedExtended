package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedRecipes;
import lotr.common.recipe.FactionTableType;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.recipe.MultiTableType;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

@Mixin(LOTRRecipes.class)
public class MixinLOTRRecipes {

	@Shadow(remap = false)
	private static FactionTableType registerFaction(String s, Supplier<Block> blockSup) {return null;}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedRecipes.TAURETHRIM_CRAFTING = registerFaction("taurethrim", ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE);
		ExtendedRecipes.DOL_GULDUR_CRAFTING = registerFaction("dol_guldur", ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE);
		ExtendedRecipes.RHUNIC_CRAFTING = registerFaction("rhunic", ExtendedBlocks.RHUNIC_CRAFTING_TABLE);
	}
	
	@Inject(method = "registerMulti(Ljava/lang/String;Ljava/util/List;)Llotr/common/recipe/MultiTableType;", at = @At("HEAD"), remap=false, cancellable = true)
	private static void injectMultipart(String s, List<FactionTableType> types, CallbackInfoReturnable<MultiTableType> ci) {
		if(s.equals("any_orc")) {
			ci.setReturnValue(registerMultiPrivate(s, createList(LOTRRecipes.MORDOR_CRAFTING,LOTRRecipes.URUK_CRAFTING, LOTRRecipes.ANGMAR_CRAFTING, ExtendedRecipes.DOL_GULDUR_CRAFTING)));
		}
	}
	
	private static List<FactionTableType> createList(FactionTableType... types) {
		return (List<FactionTableType>) ImmutableList.copyOf(types);
	}
	
	private static MultiTableType registerMultiPrivate(String s, List<FactionTableType> types) {
		ResourceLocation res = new ResourceLocation("lotr", s);
		return (MultiTableType) Registry.register(Registry.RECIPE_TYPE, res, new MultiTableType(res, types));
	}
}
