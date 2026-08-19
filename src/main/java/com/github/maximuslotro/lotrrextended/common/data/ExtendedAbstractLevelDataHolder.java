package com.github.maximuslotro.lotrrextended.common.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public abstract class ExtendedAbstractLevelDataHolder {
	protected final ExtendedLevelData holder;
	
	public ExtendedAbstractLevelDataHolder(ExtendedLevelData pHolder) {
		this.holder = pHolder;
	}

	public abstract void loadData(CompoundNBT pTag, ServerWorld world);

	public abstract void saveData(CompoundNBT pTag, ServerWorld world);

	public abstract void tick(ServerWorld level);

	public void markDirty() {
		holder.markDirty();
	}
}
