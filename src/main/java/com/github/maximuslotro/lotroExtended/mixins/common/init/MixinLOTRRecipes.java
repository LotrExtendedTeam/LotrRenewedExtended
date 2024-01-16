package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;

import lotr.common.datagen.DataGenValueHolder;
import lotr.common.datagen.crafting.builders.ExtendedTippedArrowRecipe;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedRecipes;
import lotr.common.recipe.FactionTableType;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.recipe.MultiTableType;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.registries.DeferredRegister;

@Mixin(LOTRRecipes.class)
public class MixinLOTRRecipes {

	@Final
	@Shadow(remap = false)
	public static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS;

	@Shadow(remap = false)
	private static FactionTableType registerFaction(String s, Supplier<Block> blockSup) {return null;}

	private static FactionTableType registerFactionD(String s, Supplier<Block> blockSup) {
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.containerTitlesHolders.add("container.lotr."+s+"_crafting");
		}
		return registerFaction(s, blockSup);
	}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedRecipes.TAURETHRIM_CRAFTING = registerFactionD("taurethrim", ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE);
		ExtendedRecipes.DOL_GULDUR_CRAFTING = registerFactionD("dol_guldur", ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE);
		ExtendedRecipes.RHUNIC_CRAFTING = registerFactionD("rhunic", ExtendedBlocks.RHUNIC_CRAFTING_TABLE);
		ExtendedRecipes.MORWAITH_CRAFTING = registerFactionD("morwaith", ExtendedBlocks.MORWAITH_CRAFTING_TABLE);
		ExtendedRecipes.HALF_TROLL_CRAFTING = registerFactionD("half_troll", ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE);
		ExtendedRecipes.GUNDABAD_CRAFTING = registerFactionD("northern_orc", ExtendedBlocks.GUNDABAD_CRAFTING_TABLE);

		ExtendedRecipes.EXTENDED_TIPPED_ARROW = RECIPE_SERIALIZERS.register("extended_crafting_special_tippedarrow", ()-> new SpecialRecipeSerializer<>(ExtendedTippedArrowRecipe::new));
	}
	
	@Inject(method = "registerMulti(Ljava/lang/String;Ljava/util/List;)Llotr/common/recipe/MultiTableType;", at = @At("HEAD"), remap=false, cancellable = true)
	private static void injectMultipart(String s, List<FactionTableType> types, CallbackInfoReturnable<MultiTableType> ci) {
		if(s.equals("any_orc")) {
			ci.setReturnValue(registerMultiPrivate(s, createList(LOTRRecipes.MORDOR_CRAFTING, LOTRRecipes.URUK_CRAFTING, LOTRRecipes.ANGMAR_CRAFTING, ExtendedRecipes.DOL_GULDUR_CRAFTING, ExtendedRecipes.GUNDABAD_CRAFTING, ExtendedRecipes.HALF_TROLL_CRAFTING)));
		}
		if(s.equals("any_morgul")) {
			ci.setReturnValue(registerMultiPrivate(s, createList(LOTRRecipes.MORDOR_CRAFTING, LOTRRecipes.ANGMAR_CRAFTING, ExtendedRecipes.DOL_GULDUR_CRAFTING)));
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
