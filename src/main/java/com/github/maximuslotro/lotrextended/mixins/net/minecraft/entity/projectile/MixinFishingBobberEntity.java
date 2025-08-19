package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.projectile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;

@Mixin(FishingBobberEntity.class)
public abstract class MixinFishingBobberEntity {
//	@Inject(method = "retrieve(Lnet/minecraft/item/ItemStack;)I", 
//		at = @At(value = "INVOKE", 
//		target = "Lnet/minecraft/loot/LootTable;getRandomItems(Lnet/minecraft/loot/LootContext;)Ljava/util/List;", 
//		shift = At.Shift.BEFORE),
//		locals = LocalCapture.CAPTURE_FAILHARD)
//	private void changeTable$lotrextended(ItemStack pStack, CallbackInfoReturnable<Integer> cir, PlayerEntity playerentity, int i, ItemFishedEvent event, LootContext.Builder lootcontext$builder, LootTable loottable) {
//		if(LOTRDimensions.isModDimension(((FishingBobberEntity)(Object)this).level)) {
//			loottable = ((FishingBobberEntity)(Object)this).level.getServer().getLootTables().get(ExtendedLootTables.MIDDLE_EARTH_FISHING);
//		}
//	}

	@Redirect(
	    method = "retrieve",
	    at = @At(
	        value = "INVOKE",
	        target = "Lnet/minecraft/loot/LootTableManager;get(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/loot/LootTable;"
	    ))
	private LootTable changeTable$lotrextended(LootTableManager manager, ResourceLocation table) {
		if(LOTRDimensions.isModDimension(((FishingBobberEntity)(Object)this).level)) {
			return manager.get(ExtendedLootTables.MIDDLE_EARTH_FISHING);
		}
		return manager.get(table);
	}
}
