package com.github.maximuslotro.lotrextended.mixins.lotr.common.tileentity;

import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedSingleItemDataHolderIInventory;

import lotr.common.inv.KegContainer;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselDrinkItem.Potency;
import lotr.common.recipe.DrinkBrewingRecipe;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.util.DrinkUtils;
import lotr.common.util.tileentity.ExtendedLootTableAccessor;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Mixin class for {@link KegTileEntity} to integrate extended loot-table behavior,
 * brewing recipe stability, and data persistence.
 *
 * <p>Handles injection points for loot table loading, menu creation, inventory interaction,
 * and brewing recipe recalculation.</p>
 *
 * <p>This mixin fixes NPEs during structure generation by null-guarding level usage.
 * It also emulates loot table unpacking similar to vanilla chests and ensures
 * synced server-client keg inventory behavior.</p>
 *
 */
@Mixin(KegTileEntity.class)
public class MixinKegTileEntity implements ExtendedLootTableAccessor {
	/**
	 * The loot table resource location assigned to this keg.
	 * Used to populate inventory contents when unpacked.
	 * 
	 * @Nullable
	 * @Unique
	 */
	@Nullable
	@Unique
	public ResourceLocation lootTable;
	/**
	 * Random seed used when generating loot from the assigned loot table.
	 * 
	 * @Unique
	 */
	@Unique
	protected long lootTableSeed;

	/**
	 * Ingredient slot indices in the keg inventory.
	 * 
	 * @Shadow (remap = false)
	 * @Final
	 */
	@Shadow(remap = false)
	@Final
	private static final int[] SLOTS_INGREDIENT = { 0, 1, 2, 3, 4, 5 };

	/** 
	 * Water slot indices in the keg inventory. 
	 * 
	 * @Shadow (remap = false)
	 * @Final
	 * */
	@Shadow(remap = false)
	@Final
	private static final int[] SLOTS_WATER = { 6, 7, 8 };

	/** 
	 * The current operational mode of the keg (e.g., EMPTY, BREWING).
	 * 
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private KegTileEntity.KegMode kegMode;

	/** 
	 * The current brewing recipe ID being applied in the keg. 
	 * 
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private ResourceLocation currentBrewingRecipe;

	/** 
	 * Re-syncs keg inventory contents to nearby players. 
	 * 
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private void resendKegInventoryToPlayers() {}

	/**
	 * Determines if an inventory index refers to an ingredient or water slot.
	 *
	 * @param index the inventory index
	 * @return true if the index refers to an ingredient or water slot
	 */
	@Shadow(remap = false)
	private boolean isIngredientOrWaterSlot(int index) {return false;}

	/** 
	 * Total brewing time required for the current recipe. 
	 * 
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private int brewTimeTotal;

	/** 
	 * Internal inventory list for the keg tile entity. 
	 * 
	 * @Shadow (remap = false)
	 * */
	@Shadow(remap = false)
	private NonNullList<ItemStack> inventory;

	/** 
	 * Keg data array used for syncing UI progress (brew time, etc.). 
	 * 
	 * @Shadow (remap = false)
	 * @Final
	 * */
	@Shadow(remap = false)
	@Final
	private IIntArray kegData;

	@Inject(method = "handleBrewButtonPress(Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At("TAIL"), remap = false)
	public void handleBrewButtonPress(ServerPlayerEntity player, CallbackInfo ci) {
		ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(player, "brewDrink");
	}

	/**
	 * Overwrites the original recipe update method to null-guard level access
	 * during structure generation and initialize the keg output item.
	 *
	 * @author maximuslotro
	 * @reason Fixing structure generation null-pointer exception
	 * @Overwrite
	 */
	@Overwrite(remap = false)
	private void updateBrewingRecipe() {
		if (this.kegMode == KegTileEntity.KegMode.EMPTY) {
			IInventory proxyInv = new Inventory(9);
			int[] var2 = SLOTS_INGREDIENT;
			int var3 = var2.length;

			int var4;
			int i;
			for (var4 = 0; var4 < var3; ++var4) {
				i = var2[var4];
				proxyInv.setItem(i, getItem(i));
			}

			var2 = SLOTS_WATER;
			var3 = var2.length;

			for (var4 = 0; var4 < var3; ++var4) {
				i = var2[var4];
				proxyInv.setItem(i, getItem(i));
			}
			KegTileEntity entity = ((KegTileEntity)(Object)(this));
			// Extended Start
			//Fix structure generation null-pointer exception. Level is null before chunk creation baking
			World level = entity.getLevel();
			if(level==null) {
				currentBrewingRecipe = null;
				setItem(9, ItemStack.EMPTY);
				brewTimeTotal = 0;
				entity.setChanged();
				return;
			}
			//Extended End
			Optional<DrinkBrewingRecipe> opt = level.getRecipeManager().getRecipeFor(LOTRRecipes.DRINK_BREWING,
				proxyInv, level);
			if (opt.isPresent()) {
				DrinkBrewingRecipe recipe = opt.get();
				this.currentBrewingRecipe = recipe.getId();
				ItemStack result = recipe.assemble(proxyInv);
				result.setCount(16);
				VesselDrinkItem.setPotency(result, Potency.WEAK);
				this.setItem(9, result);
				this.brewTimeTotal = recipe.getBrewTime();
			} else {
				this.currentBrewingRecipe = null;
				this.setItem(9, ItemStack.EMPTY);
				this.brewTimeTotal = 0;
			}

			entity.setChanged();
			if (!level.isClientSide) {
				this.resendKegInventoryToPlayers();
			}
		}
	}

	/**
	 * Injected method that appends loot table data to the provided NBT compound
	 * after the base {@code save()} method is executed.
	 * 
	 * <p>If a loot table is set, its identifier is written under the "LootTable" key,
	 * and if the loot table seed is non-zero, it is stored under the "LootTableSeed" key.
	 *
	 * @param nbt  The NBT compound to write data to.
	 * @param info The callback return information (not modified here).
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
	 * Injected method that reads loot table data from the provided NBT compound
	 * after the base {@code load()} method is executed.
	 * 
	 * <p>If the "LootTable" key exists, a new {@code ResourceLocation} is created
	 * from it and assigned to {@code this.lootTable}. The "LootTableSeed" is also
	 * read and stored if present.
	 *
	 * @param state The current block state (unused in this method).
	 * @param nbt   The NBT compound to read data from.
	 * @param info  The callback information (not modified here).
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
	 * Overwrites the container creation method to ensure loot tables are unpacked before opening the keg UI.
	 * 
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @param id container window ID
	 * @param playerInventory the player's inventory
	 * @return a new {@link KegContainer} instance
	 * @Overwrite
	 */
	@Overwrite
	protected Container createMenu(int id, PlayerInventory playerInventory) {
		// Extended Start
		unpackLootTableKeg(playerInventory.player);
		// Extended End
		return new KegContainer(id, playerInventory, ((KegTileEntity)(Object)(this)), kegData);
	}

	/**
	 * Overwrites item access method to unpack loot table on access.
	 * 
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @param index inventory index to retrieve
	 * @return the item stack at the specified index
	 * @Overwrite
	 */
	@Overwrite
	public ItemStack getItem(int index) {
		// Extended Start
		unpackLootTableKeg(null);
		// Extended End
		return inventory.get(index);
	}

	/**
	 * Overwrites item removal method to ensure brewing logic and loot unpacking.
	 * 
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @param index index to remove items from
	 * @param count number of items to remove
	 * @return the removed {@link ItemStack}
	 * @Overwrite
	 */
	@Overwrite
	public ItemStack removeItem(int index, int count) {
		// Extended Start
		unpackLootTableKeg(null);
		// Extended End
		ItemStack ret = ItemStackHelper.removeItem(inventory, index, count);
		if (isIngredientOrWaterSlot(index)) {
			updateBrewingRecipe();
		}

		return ret;
	}

	/**
	 * Overwrites no-update item removal method to trigger brewing update and loot unpacking.
	 * 
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @param index index to clear
	 * @return the cleared {@link ItemStack}
	 * @Overwrite
	 */
	@Overwrite
	public ItemStack removeItemNoUpdate(int index) {
		// Extended Start
		unpackLootTableKeg(null);
		// Extended End
		ItemStack ret = ItemStackHelper.takeItem(inventory, index);
		if (isIngredientOrWaterSlot(index)) {
			updateBrewingRecipe();
		}

		return ret;
	}

	/**
	 * Overwrites isEmpty() check to trigger loot unpacking before test.
	 *
	 * @return true if all inventory slots are empty
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @Overwrite
	 */
	@Overwrite
	public boolean isEmpty() {
		// Extended Start
		unpackLootTableKeg(null);
		// Extended End
		for(ItemStack itemstack : inventory) {
			if(!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Overwrites item set logic to support brewing logic recalculation and loot table unpacking.
	 * 
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 * @param index inventory slot to modify
	 * @param stack item stack to place in the slot
	 * @Overwrite
	 */
	@Overwrite
	public void setItem(int index, ItemStack stack) {
		// Extended Start
		unpackLootTableKeg(null);
		// Extended End
		inventory.get(index);
		inventory.set(index, stack);
		KegTileEntity entity = ((KegTileEntity)(Object)(this));
		if (stack.getCount() > entity.getMaxStackSize()) {
			stack.setCount(entity.getMaxStackSize());
		}

		if (isIngredientOrWaterSlot(index)) {
			updateBrewingRecipe();
		}

	}

	/**
	 * Loads and applies loot table contents to the keg inventory.
	 * Based on vanilla chest behavior, applies the loot only once.
	 *
	 * @param player the player interacting (optional, can be null)
	 * @Unique
	 */
	@Unique
	private void unpackLootTableKeg(@Nullable PlayerEntity player) {
		KegTileEntity entity = ((KegTileEntity)(Object)(this));
		try {
			if(!ExtendedServerConfig.enableDevMode.get()) {
				if (this.lootTable != null && entity.getLevel() != null && entity.getLevel().getServer() != null) {
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
					entity.fillBrewedWith(DrinkUtils.createDrink(new Random(), placeHolder.getItem(0)));
				}
			}
		}catch(NullPointerException e) {
			ExtendedLog.error("Caught a null-pointer exception while unpacking keg loot-table");
			ExtendedLog.error("Location: "+lootTable.toString()+", Pos: "+entity.getBlockPos().toString());
			e.printStackTrace();
			ExtendedLog.error("Please report this if it happens!");
		}
	}


	/**
	 * Sets the loot table and its associated random seed for unpacking.
	 *
	 * @param table loot table location
	 * @param seed random seed
	 */
	@Override
	public void setLootTable(ResourceLocation table, long seed) {
		lootTable = table;
		lootTableSeed = seed;
	}

	/**
	 * Retrieves the current assigned loot table location.
	 *
	 * @return the loot table resource location
	 */
	@Override
	public ResourceLocation getLootTable() {
		return lootTable;
	}
}
