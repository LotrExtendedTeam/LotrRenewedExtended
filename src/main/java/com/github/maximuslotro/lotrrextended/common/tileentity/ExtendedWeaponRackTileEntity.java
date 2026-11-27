package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants.NBT;

public class ExtendedWeaponRackTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
	private ItemStack containedItem = ItemStack.EMPTY;
	private boolean unpacked=false;
	public ExtendedWeaponRackTileEntity() {
		super(ExtendedTileEntities.WEAPON_RACK.get());
	}

	@Override
	public void tick() {
		if(!this.hasLevel() || this.level.isClientSide) {
			return;
		}
		if(!ExtendedServerConfig.enableDevMode.get() && this.lootTable != null && !unpacked && playerInDistance()) {
			unpacked=true;
			this.unpackLootTable(null);
			this.markAndUpdate();
		}

	}

	private boolean playerInDistance() {
		return this.hasLevel() && this.level.hasNearbyAlivePlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 50D);
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT nbt) {
		super.load(p_230337_1_, nbt);
		if(this.tryLoadLootTable(nbt)) {
			this.containedItem = ItemStack.EMPTY;
		} else if(nbt.contains("extended_ditem", NBT.TAG_COMPOUND)) {
			this.containedItem = ItemStack.of(nbt.getCompound("extended_ditem"));
		} else if(nbt.contains("Item", NBT.TAG_COMPOUND)) {
			this.containedItem = ItemStack.of(nbt.getCompound("Item"));
		} else {
			this.containedItem = ItemStack.EMPTY;
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if(!this.trySaveLootTable(nbt)) {
			nbt.put("Item", this.containedItem.save(new CompoundNBT()));
		}
		return nbt;
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, -1, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		if(this.hasLevel()) {
			this.load(this.level.getBlockState(pkt.getPos()), pkt.getTag());
		}
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		this.load(state, tag);
	}

	public void markAndUpdate() {
		this.setChanged();
		if (this.hasLevel()) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public boolean hasItem() {
		return !this.containedItem.isEmpty();
	}

	public ItemStack getContainedItem() {
		return this.containedItem;
	}

	public void setContainedItem(ItemStack pStack) {
		this.containedItem = (pStack == null) ? ItemStack.EMPTY : pStack;
		this.markAndUpdate();
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return NonNullList.withSize(1, containedItem);
	}

	@Override
	protected void setItems(NonNullList<ItemStack> list) {
		if(!list.isEmpty()) {
			this.containedItem=list.get(0);
			this.markAndUpdate();
		}
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new StringTextComponent("Weapon Rack Secret Lounge");
	}

	@Override
	protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
		return null; //Return null, since weapon racks don't have an GUI
	}

	@Override
	public void unpackLootTable(@Nullable PlayerEntity pPlayer) {
		if (this.lootTable == null || !this.hasLevel() || this.level.getServer() == null) {
			return;
		}
		ResourceLocation lootId = this.lootTable;
		try {
			LootTable loottable = this.level.getServer().getLootTables().get(this.lootTable);
			if (pPlayer instanceof ServerPlayerEntity) {
				CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayerEntity) pPlayer, this.lootTable);
			}
			this.lootTable = null;
			LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.level)).withParameter(LootParameters.ORIGIN,Vector3d.atCenterOf(this.worldPosition)).withOptionalRandomSeed(this.lootTableSeed);
			if (pPlayer != null) {
				lootcontext$builder.withLuck(pPlayer.getLuck()).withParameter(LootParameters.THIS_ENTITY, pPlayer);
			}
			List<ItemStack> drops = loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.CHEST));
			if(!drops.isEmpty()) {
				ItemStack stack = drops.get(0).copy();
				stack.setCount(1);
				setContainedItem(stack);
			}
		}catch(NullPointerException e) {
			ExtendedLog.error("Error unpacking weapon rack loot table: " + lootId + " at " + this.worldPosition);
			e.printStackTrace();
		}
	}
}
