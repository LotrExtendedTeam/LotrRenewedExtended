package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.projectile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;

/**
 * A Mixin targeting {@link FishingBobberEntity} to override loot table selection
 * when fishing in custom dimensions.
 * <p>
 * This mixin ensures that if a player is fishing in a mod-defined dimension
 * (e.g., Middle-earth), the retrieved loot comes from a custom fishing loot table
 * instead of the vanilla ones.
 * </p>
 * @Mixin (FishingBobberEntity.class)
 */
@Mixin(FishingBobberEntity.class)
public abstract class MixinFishingBobberEntity {
	//TODO transition to an inject
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
	/**
	 * Redirects {@link LootTableManager#get(ResourceLocation)} during
	 * {@link FishingBobberEntity#retrieve(ItemStack)} to swap out the default loot table.
	 * <p>
	 * Logic:
	 * <ul>
	 *   <li>If the fishing bobber is in a LOTR dimension (checked with {@link LOTRDimensions#isModDimension}), 
	 *       return the custom {@link ExtendedLootTables#MIDDLE_EARTH_FISHING} loot table.</li>
	 *   <li>Otherwise, return the vanilla loot table as normal.</li>
	 * </ul>
	 * </p>
	 *
	 * <p>This approach allows dimension-specific fishing behavior without replacing
	 * the entire {@code retrieve} method, keeping compatibility with vanilla logic.</p>
	 *
	 * @param manager the loot table manager responsible for resolving loot tables
	 * @param table   the original loot table resource location being requested
	 * @return either the vanilla loot table (default) or the custom LOTR fishing loot table
	 * @Redirect(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTableManager;get(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/loot/LootTable;"))
	 */
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