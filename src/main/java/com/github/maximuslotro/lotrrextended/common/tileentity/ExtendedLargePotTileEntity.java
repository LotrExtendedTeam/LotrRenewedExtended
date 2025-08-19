package com.github.maximuslotro.lotrrextended.common.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.init.ExtendedTileEntities;
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
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants.NBT;

public class ExtendedLargePotTileEntity extends LockableLootTileEntity{
	private ItemStack containedItem;
	public ExtendedLargePotTileEntity() {
		super(ExtendedTileEntities.LARGE_POT.get());
		this.containedItem = ItemStack.EMPTY;
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT nbt) {
		super.load(p_230337_1_, nbt);
		// Set empty if loot-table is set
		if (this.tryLoadLootTable(nbt)) {
			containedItem = ItemStack.EMPTY;
		}else {
			if (nbt.contains("contained_item", NBT.TAG_COMPOUND)) {
				this.containedItem = ItemStack.of(nbt.getCompound("contained_item"));
			}
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		// Don't save item if loot-table is saved
		if (!this.trySaveLootTable(nbt)) {
			nbt.put("contained_item", this.containedItem.save(new CompoundNBT()));
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
		this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.handleUpdateTag(state, tag);
	}

	public ItemStack getContainedItem(boolean triggerTable) {
		if(triggerTable) {
			return this.getItem(0);
		}
		return containedItem;
	}

	public void setContainedItem(ItemStack containedItem) {
		this.containedItem = containedItem;
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return NonNullList.withSize(1, this.containedItem);
	}

	@Override
	protected void setItems(NonNullList<ItemStack> list) {
		if(list.size()>=1) {
			this.containedItem=list.get(0);
		}
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new StringTextComponent("Large Pot Hangout Pool");
	}

	@Override
	protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
		return null; //Return null, since pots don't have an GUI
	}

	@Override
	public void unpackLootTable(@Nullable PlayerEntity p_184281_1_) {
		try {
			if (this.lootTable != null && this.level.getServer() != null) {
				LootTable loottable = this.level.getServer().getLootTables().get(this.lootTable);
				if (p_184281_1_ instanceof ServerPlayerEntity) {
					CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayerEntity) p_184281_1_, this.lootTable);
				}
				this.lootTable = null;
				LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.level)).withParameter(LootParameters.ORIGIN,Vector3d.atCenterOf(this.worldPosition)).withOptionalRandomSeed(this.lootTableSeed);
				if (p_184281_1_ != null) {
					lootcontext$builder.withLuck(p_184281_1_.getLuck()).withParameter(LootParameters.THIS_ENTITY, p_184281_1_);
				}
				List<ItemStack> list = loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.CHEST));
				if(list.size()>0) {
					ItemStack stack = list.get(0);
					stack.setCount(1);
					setContainedItem(stack);
				}
			}
		}catch(NullPointerException e) {
			ExtendedLog.error("Caught a null-pointer exception while unpacking large pot loot-table");
			ExtendedLog.error("Location: "+lootTable.toString()+", Pos: "+this.getBlockPos().toString());
			e.printStackTrace();
			ExtendedLog.error("Please report this if it happens again");
		}

	}
}
