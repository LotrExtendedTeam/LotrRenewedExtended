package com.github.maximuslotro.lotrextended.mixins.lotr.common.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedSingleItemDataHolderIInventory;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedILootTableAccessor;

import lotr.common.tileentity.PlateTileEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

/**
 * Mixin extension for {@link PlateTileEntity} to support loot table generation and NBT serialization.
 * Implements {@link ExtendedILootTableAccessor} for extended loot behavior and {@link ITickableTileEntity}
 * to manage lazy loot unpacking during ticks.
 */
@Mixin(PlateTileEntity.class)
public class MixinPlateTileEntity implements ExtendedILootTableAccessor, ITickableTileEntity {
	/** 
	 * Loot table resource location to be unpacked lazily 
	 * @Unique
	 * */
	@Unique
	@Nullable
	public ResourceLocation lootTable;
	/** 
	 * Loot table seed used to generate deterministic loot 
	 * @Unique
	 * */
	@Unique
	protected long lootTableSeed;
	/** 
	 * Tracks whether loot has been unpacked to avoid duplication 
	 * @Unique
	 * */
	@Unique
	private boolean ticked=false;
	/** 
	 * Shadowed item stack representing the food stored in the plate 
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private ItemStack foodItem;
	/**
	 * Ticks the tile entity. Unpacks the loot table if not yet done and server-side.
	 * Prevents re-unpacking by setting the {@code ticked} flag.
	 */
	@Override
	public void tick() {
		if(!ExtendedServerConfig.enableDevMode.get()) {
			PlateTileEntity entity = ((PlateTileEntity)(Object)(this));
			if (this.lootTable != null && entity.getLevel()!=null && !entity.getLevel().isClientSide() && !ticked) {
				ticked=true;
				unpackLootTablePlate(null);
			}
		}
	}

	/**
	 * Injected method that appends loot table data to the NBT after the base save() method.
	 *
	 * @param nbt  The compound to write data to.
	 * @param info The callback return information.
	 * @Inject (method = "save(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/nbt/CompoundNBT;", at = @At("TAIL"))
	 */
	@Inject(method = "save(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/nbt/CompoundNBT;", at = @At("TAIL"))
	public void injectedSave(CompoundNBT nbt, CallbackInfoReturnable<CompoundNBT> info) {
		if (this.lootTable != null) {
			nbt.putString("LootTable", this.lootTable.toString());
			if (this.lootTableSeed != 0L) {
				nbt.putLong("LootTableSeed", this.lootTableSeed);
			}
		}
	}

	/**
	 * Injected method that reads loot table data from NBT after the base load() method.
	 *
	 * @param state The block state (unused).
	 * @param nbt   The compound to read data from.
	 * @param info  Callback context.
	 * @Inject (method = "load(Lnet/minecraft/block/BlockState;Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	 */
	@Inject(method = "load(Lnet/minecraft/block/BlockState;Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void injectedLoad(BlockState state, CompoundNBT nbt, CallbackInfo info) {
		if (nbt.contains("LootTable", 8)) {
	         this.lootTable = new ResourceLocation(nbt.getString("LootTable"));
	         this.lootTableSeed = nbt.getLong("LootTableSeed");
	      }
	}

	/**
	 * Adds logic to unpack loot-tables
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @return The contained food item
	 * @Overwrite (remap = false)
	 */
	@Overwrite(remap = false)
	public ItemStack getFoodItem() {
		// Extended Start
		PlateTileEntity entity = ((PlateTileEntity)(Object)(this));
		if(entity.hasLevel() && !entity.getLevel().isClientSide()) {
			unpackLootTablePlate(null);
		}
		// Extended End
		return foodItem;
	}

	/**
	 * Adds logic to unpack loot-tables
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @Overwrite (remap = false)
	 * @param nbt The {@link CompoundNBT} to save food data to
	 */
	@Overwrite(remap = false)
	private void writeFood(CompoundNBT nbt) {
		// Extended Start
		unpackLootTablePlate(null);
		// Extended End
		nbt.put("FoodItem", foodItem.save(new CompoundNBT()));
	}

	/**
	 * Unpacks the loot table and generates the food item from it, using the context of a chest loot table.
	 * Only executed once per tile entity instance unless manually reset.
	 * 
	 * @param player The player triggering the unpack (can be null).
	 * @Unique
	 */
	@Unique
	private void unpackLootTablePlate(@Nullable PlayerEntity player) {
		PlateTileEntity entity = ((PlateTileEntity)(Object)(this));
		try {
			if(!ExtendedServerConfig.enableDevMode.get()) {
				if (this.lootTable != null && entity.getLevel() != null && !entity.getLevel().isClientSide() && entity.getLevel().getServer() != null ) {
					LootTable loottable = entity.getLevel().getServer().getLootTables().get(lootTable);
					if (player instanceof ServerPlayerEntity) {
						CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayerEntity) player, lootTable);
					}

					this.lootTable = null;
					LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) entity.getLevel()))
							.withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(entity.getBlockPos())).withOptionalRandomSeed(lootTableSeed);
					if (player != null) {
						lootcontext$builder.withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY, player);
					}
					ExtendedSingleItemDataHolderIInventory placeHolder = new ExtendedSingleItemDataHolderIInventory();
					loottable.fill(placeHolder, lootcontext$builder.create(LootParameterSets.CHEST));
					ItemStack item = placeHolder.getItem(0);
					Random ran = new Random();
					if(ran.nextInt(4)==0) {
						if(item.getMaxStackSize()>1) {
							item.setCount(1 + ran.nextInt(3));
						}
					}
					entity.setFoodItem(item);
				}
			}
		}catch(NullPointerException e) {
			ExtendedLog.error("Caught a null-pointer exception while unpacking plate loot-table");
			ExtendedLog.error("Location: "+lootTable.toString()+", Pos: "+entity.getBlockPos().toString());
			e.printStackTrace();
			ExtendedLog.error("Please report this if it happens!");
		}
	}
	/**
	 * Sets the loot table and its seed for this tile entity.
	 * 
	 * @param table The loot table resource location.
	 * @param seed  The random seed used for loot generation.
	 */
	@Override
	public void setLootTable(ResourceLocation table, long seed) {
		lootTable = table;
		lootTableSeed = seed;
		ticked = false;
	}
	/**
	 * Gets the current loot table assigned to this tile entity.
	 * 
	 * @return The loot table resource location, or {@code null} if not set.
	 */
	@Override
	public ResourceLocation getLootTable() {
		return lootTable;
	}
}
