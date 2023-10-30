package com.github.maximuslotro.lotroExtended.mixins.common.init;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedRecipes;
import lotr.common.recipe.FactionTableType;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.Block;

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
}
