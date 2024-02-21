package com.github.maximuslotro.lotrExtended.mixins.lotr.common.tileentity;

import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.inv.ExtendedKegDataHolderIInventory;
import lotr.common.inv.KegContainer;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselDrinkItem.Potency;
import lotr.common.recipe.DrinkBrewingRecipe;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.tileentity.KegTileEntity;
import lotr.common.util.DrinkUtils;
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

@Mixin(KegTileEntity.class)
public class MixinKegTileEntity {

	@Nullable
	public ResourceLocation lootTable;
	protected long lootTableSeed;

	@Shadow(remap = false)
	private static final int[] SLOTS_INGREDIENT = { 0, 1, 2, 3, 4, 5 };

	@Shadow(remap = false)
	private static final int[] SLOTS_WATER = { 6, 7, 8 };
	
	@Shadow(remap = false)
	private KegTileEntity.KegMode kegMode;

	@Shadow(remap = false)
	private ResourceLocation currentBrewingRecipe;

	@Shadow(remap = false)
	private void resendKegInventoryToPlayers() {}

	@Shadow(remap = false)
	private boolean isIngredientOrWaterSlot(int index) {return false;}

	@Shadow(remap = false)
	private int brewTimeTotal;

	@Shadow(remap = false)
	private NonNullList<ItemStack> inventory;

	@Final
	@Shadow(remap = false)
	private IIntArray kegData;
	/**
	 * @reason Fixing structure generation null-pointer exception
	 * @author maximuslotro
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
			//Fix structure generation null-pointer exception. Level is null before chunk creation baking
			if(!entity.hasLevel()) {
				currentBrewingRecipe = null;
				setItem(9, ItemStack.EMPTY);
				brewTimeTotal = 0;
				entity.setChanged();
				return;
			}
			World level = entity.getLevel();
			Optional<DrinkBrewingRecipe> opt = level.getRecipeManager().getRecipeFor(LOTRRecipes.DRINK_BREWING,
					proxyInv, level);
			if (opt.isPresent()) {
				DrinkBrewingRecipe recipe = (DrinkBrewingRecipe) opt.get();
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

	//Handle Loot-tables
	@Inject(method = "save(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/nbt/CompoundNBT;", at = @At("TAIL"))
	public void injectedSave(CompoundNBT nbt, CallbackInfoReturnable<CompoundNBT> info) {
		if (this.lootTable != null) {
			nbt.putString("LootTable", this.lootTable.toString());
			if (this.lootTableSeed != 0L) {
				nbt.putLong("LootTableSeed", this.lootTableSeed);
			}
		}
	}

	@Inject(method = "load(Lnet/minecraft/block/BlockState;Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void load(BlockState state, CompoundNBT nbt, CallbackInfo info) {
		if (nbt.contains("LootTable", 8)) {
	         this.lootTable = new ResourceLocation(nbt.getString("LootTable"));
	         this.lootTableSeed = nbt.getLong("LootTableSeed");
	      }
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite
	protected Container createMenu(int id, PlayerInventory playerInventory) {
		unpackLootTableKeg(playerInventory.player);
		return new KegContainer(id, playerInventory, ((KegTileEntity)(Object)(this)), kegData);
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite
	public ItemStack getItem(int index) {
		unpackLootTableKeg((PlayerEntity)null);
		return inventory.get(index);
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite
	public ItemStack removeItem(int index, int count) {
		unpackLootTableKeg((PlayerEntity)null);
		ItemStack ret = ItemStackHelper.removeItem(inventory, index, count);
		if (isIngredientOrWaterSlot(index)) {
			updateBrewingRecipe();
		}

		return ret;
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite
	public ItemStack removeItemNoUpdate(int index) {
		unpackLootTableKeg((PlayerEntity)null);
		ItemStack ret = ItemStackHelper.takeItem(inventory, index);
		if (isIngredientOrWaterSlot(index)) {
			updateBrewingRecipe();
		}

		return ret;
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite
	public boolean isEmpty() {
		unpackLootTableKeg((PlayerEntity)null);
		for(ItemStack itemstack : inventory) {
			if(!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite
	public void setItem(int index, ItemStack stack) {
		unpackLootTableKeg((PlayerEntity)null);
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

	private void unpackLootTableKeg(@Nullable PlayerEntity player) {
		KegTileEntity entity = ((KegTileEntity)(Object)(this));
		if (this.lootTable != null && entity.getLevel().getServer() != null) {
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
			ExtendedKegDataHolderIInventory placeHolder = new ExtendedKegDataHolderIInventory();
			loottable.fill(placeHolder, lootcontext$builder.create(LootParameterSets.CHEST));
			entity.fillBrewedWith(DrinkUtils.createDrink(new Random(lootTableSeed), placeHolder.getItem(0)));
		}
	}

	//Inject intp 
	public void setLootTable(ResourceLocation table, long seed) {
		lootTable = table;
		lootTableSeed = seed;
	}
}
