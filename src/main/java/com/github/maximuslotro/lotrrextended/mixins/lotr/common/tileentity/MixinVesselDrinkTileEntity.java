package com.github.maximuslotro.lotrrextended.mixins.lotr.common.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.inventory.ExtendedSingleItemDataHolderIInventory;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import lotr.common.tileentity.VesselDrinkTileEntity;
import lotr.common.util.tileentity.ExtendedLootTableAccessor;
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

@Mixin(VesselDrinkTileEntity.class)
public class MixinVesselDrinkTileEntity implements ExtendedLootTableAccessor, ITickableTileEntity {
	@Nullable
	public ResourceLocation lootTable;
	protected long lootTableSeed;
	private boolean ticked=false;

	@Shadow(remap = false)
	private ItemStack drinkItem;

	@Shadow(remap = false)
	private VesselType vesselType;

	@Override
	public void tick() {
		if(!ExtendedServerConfig.enableDevMode.get()) {
			VesselDrinkTileEntity entity = ((VesselDrinkTileEntity)(Object)(this));
			if (this.lootTable != null && entity.getLevel()!=null && !entity.getLevel().isClientSide() && !ticked) {
				ticked=true;
				unpackLootTableVessel(null);
			}
		}
	}

	//Handle saving loot-tables
	@Inject(method = "save(Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/nbt/CompoundNBT;", at = @At("TAIL"))
	public void injectedSave(CompoundNBT nbt, CallbackInfoReturnable<CompoundNBT> info) {
		if (this.lootTable != null) {
			nbt.putString("LootTable", this.lootTable.toString());
			if (this.lootTableSeed != 0L) {
				nbt.putLong("LootTableSeed", this.lootTableSeed);
			}
		}
	}

	//Handle loading loot-tables
	@Inject(method = "load(Lnet/minecraft/block/BlockState;Lnet/minecraft/nbt/CompoundNBT;)V", at = @At("TAIL"))
	public void injectedLoad(BlockState state, CompoundNBT nbt, CallbackInfo info) {
		if (nbt.contains("LootTable", 8)) {
	         this.lootTable = new ResourceLocation(nbt.getString("LootTable"));
	         this.lootTableSeed = nbt.getLong("LootTableSeed");
	      }
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite(remap = false)
	public ItemStack getVesselItem() {
		// Extended Start
		VesselDrinkTileEntity entity = ((VesselDrinkTileEntity)(Object)(this));
		if(entity.hasLevel() && !entity.getLevel().isClientSide()) {
			unpackLootTableVessel(null);
		}
		// Extended End

		if (drinkItem.isEmpty()) {
			return vesselType.createEmpty();
		}
		ItemStack copy = drinkItem.copy();
		if (copy.getItem() instanceof VesselDrinkItem) {
			VesselDrinkItem.setVessel(copy, vesselType);
		}

		copy.setCount(1);
		return copy;
	}

	/**
	 * @reason Inject loot-tables
	 * @author maximuslotro
	 */
	@Overwrite(remap = false)
	private void writeVessel(CompoundNBT nbt) {
		// Extended Start
		unpackLootTableVessel(null);
		// Extended End
		nbt.put("DrinkItem", drinkItem.save(new CompoundNBT()));
		nbt.putString("Vessel", vesselType.getCodeName());
	}

	/**
	 * Extended method to load in a loot table for plates, uses chest loot-table code
	 */
	private void unpackLootTableVessel(@Nullable PlayerEntity player) {
		VesselDrinkTileEntity entity = ((VesselDrinkTileEntity)(Object)(this));
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
					ItemStack drink = placeHolder.getItem(0);
					VesselDrinkItem.setVessel(drink, vesselType);
					VesselDrinkItem.setPotency(drink, VesselDrinkItem.Potency.randomForNPC(new Random()));
					drink.setCount(1);
					entity.setVesselItem(drink);
				}
			}
		}catch(NullPointerException e) {
			ExtendedLog.error("Caught a null-pointer exception while unpacking vessel loot-table");
			ExtendedLog.error("Location: "+lootTable.toString()+", Pos: "+entity.getBlockPos().toString());
			e.printStackTrace();
			ExtendedLog.error("Please report this if it happens!");
		}
	}

	@Override
	public void setLootTable(ResourceLocation table, long seed) {
		lootTable = table;
		lootTableSeed = seed;
		ticked = false;
	}

	@Override
	public ResourceLocation getLootTable() {
		return lootTable;
	}
}
