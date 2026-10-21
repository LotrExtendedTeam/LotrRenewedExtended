package com.github.maximuslotro.lotrrextended.common.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public abstract class ExtendedAbstractLevelDataHolder {
	protected final ExtendedLevelData holder;
	
	public ExtendedAbstractLevelDataHolder(ExtendedLevelData pHolder) {
		this.holder = pHolder;
	}

	public abstract void loadDataServer(CompoundNBT pTag, MinecraftServer server);

	public void loadDataClient() {}

	public abstract void saveData(CompoundNBT pTag);

	public abstract void tick(ServerWorld level);

	protected abstract void clearData();

	public void markDirty() {
		holder.markDirty();
	}
}
