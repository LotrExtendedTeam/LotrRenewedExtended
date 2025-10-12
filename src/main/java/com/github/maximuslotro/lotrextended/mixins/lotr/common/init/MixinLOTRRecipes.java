package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.List;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.google.common.collect.ImmutableList;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedRecipes;
import lotr.common.recipe.FactionTableType;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.recipe.MultiTableType;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Used to register new recipe handlers added by Extended
 * @Mixin (LOTRRecipes.class)
 * */
@Mixin(LOTRRecipes.class)
public class MixinLOTRRecipes {
	/**
	 * Get the recipe serializers DeferredRegister for Renewed's recipe serializers
	 * @Shadow (remap=false)
	 * @Final Mark the field to be essentially final
	 * */
	@Final
	@Shadow(remap = false)
	public static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS;
	/**
	 * Get Renewed's base method for registering faction serializers, DO NOT DIRECTLY CALL
	 * @Shadow (remap = false)
	 * @param id The ID for this recipe serializers
	 * @param blockSup A block supplier for the block this is tied to
	 * @return A FactionTableType recipe type
	 * */
	@Shadow(remap = false)
	private static FactionTableType registerFaction(String id, Supplier<Block> blockSup) {return null;}
	/**
	 * Get Renewed's base method for registering faction serializers, handling dataGen
	 * @Shadow (remap = false)
	 * @param id The ID for this recipe serializers
	 * @param defaultName Default English string name for dataGen
	 * @param blockSup A block supplier for the block this is tied to
	 * @return A FactionTableType recipe type
	 * */
	@Unique
	private static FactionTableType registerFactionD(String id, String defaultName, Supplier<Block> blockSup) {
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.containerTitlesHolders.put("container.lotr."+id+"_crafting", defaultName); // lotr is hardcoded in the register method
		}
		return registerFaction(id, blockSup);
	}
	/**
	 * @reason Register Extended's recipe serializers after Renewed's static registering is done, but before the differed register is registered
	 * @Inject (method = "register()V", at = @At("HEAD"), remap=false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedItems(CallbackInfo ci) {
		ExtendedRecipes.TAURETHRIM_CRAFTING = registerFactionD("taurethrim", "Taurethrim Crafting", ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE);
		ExtendedRecipes.DOL_GULDUR_CRAFTING = registerFactionD("dol_guldur", "Dol Guldur Crafting", ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE);
		ExtendedRecipes.RHUNIC_CRAFTING = registerFactionD("rhunic", "Rhûnic Crafting", ExtendedBlocks.RHUNIC_CRAFTING_TABLE);
		ExtendedRecipes.MORWAITH_CRAFTING = registerFactionD("morwaith", "Morwaith Crafting", ExtendedBlocks.MORWAITH_CRAFTING_TABLE);
		ExtendedRecipes.HALF_TROLL_CRAFTING = registerFactionD("half_troll", "Half Troll Crafting", ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE);
		ExtendedRecipes.NORTHERN_ORC_CRAFTING = registerFactionD("northern_orc", "Gundabad Crafting", ExtendedBlocks.NORTHERN_ORC_CRAFTING_TABLE);
	}
	/**
	 * @reason Need to override renewed MultiTableType types to add missing crafting table types
	 * @Inject (method = "registerMulti(Ljava/lang/String;Ljava/util/List;)Llotr/common/recipe/MultiTableType;", at = @At("HEAD"), remap=false, cancellable = true)
	 * @param id The ID for this recipe serializers
	 * @param types A array of FactionTableType
	 * @param ci The local CallbackInfoReturnable for modifying the return of the method
	 * */
	@Inject(method = "registerMulti(Ljava/lang/String;Ljava/util/List;)Llotr/common/recipe/MultiTableType;", at = @At("HEAD"), remap=false, cancellable = true)
	private static void injectMultipart(String id, List<FactionTableType> types, CallbackInfoReturnable<MultiTableType> ci) {
		if(id.equals("any_orc")) {
			ci.setReturnValue(registerMultiPrivate(id, createList(LOTRRecipes.MORDOR_CRAFTING, LOTRRecipes.URUK_CRAFTING, LOTRRecipes.ANGMAR_CRAFTING,
				// New tables:
				ExtendedRecipes.DOL_GULDUR_CRAFTING, ExtendedRecipes.NORTHERN_ORC_CRAFTING, ExtendedRecipes.HALF_TROLL_CRAFTING)));
		}
		if(id.equals("any_morgul")) {
			ci.setReturnValue(registerMultiPrivate(id, createList(LOTRRecipes.MORDOR_CRAFTING, LOTRRecipes.ANGMAR_CRAFTING, 
				// New tables:
				ExtendedRecipes.DOL_GULDUR_CRAFTING)));
		}
	}

	/**
	 * Make list of FactionTableType for MultiTableType
	 * @param types An array of FactionTableType
	 * @return A list of FactionTableType
	 * */
	@Unique
	private static List<FactionTableType> createList(FactionTableType... types) {
		return ImmutableList.copyOf(types);
	}

	/**
	 * Register MultiTableType overrides
	 * @param id The ID for this recipe serializers
	 * @param types An array of FactionTableType
	 * @return A registered MultiTableType
	 * */
	@Unique
	private static MultiTableType registerMultiPrivate(String id, List<FactionTableType> types) {
		ResourceLocation res = new ResourceLocation("lotr", id);
		return Registry.register(Registry.RECIPE_TYPE, res, new MultiTableType(res, types));
	}
}
