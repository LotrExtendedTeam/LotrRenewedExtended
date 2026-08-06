package com.github.maximuslotro.lotrrextended.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

public interface ExtendedILootTableAccessor {
	public void setLootTable(ResourceLocation table, long seed);
	@Nullable
	public ResourceLocation getLootTable();
}
