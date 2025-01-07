package lotr.common.util.tileentity;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

public interface ExtendedLootTableAccessor {
	public void setLootTable(ResourceLocation table, long seed);
	@Nullable
	public ResourceLocation getLootTable();
}
