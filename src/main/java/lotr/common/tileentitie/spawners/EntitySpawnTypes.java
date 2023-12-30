package lotr.common.tileentitie.spawners;

import java.util.Locale;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.util.IStringSerializable;

public enum EntitySpawnTypes implements IStringSerializable {
	HOBBIT_BARTENDER(HobbitBartenderSpawnerTileEntity::new);
	
	private final Supplier<? extends StructureSpawnerTileEntity<?, ?>> supplier;
	public static final EntitySpawnTypes[] LIST=values();

	EntitySpawnTypes(@Nullable Supplier<? extends StructureSpawnerTileEntity<?, ?>> supplier) {
		this.supplier=supplier;
	}

	public boolean hasSupplier() {
		return supplier!=null;
	}

	@Nullable
	public Supplier<? extends StructureSpawnerTileEntity<?, ?>> getSupplier() {
		return supplier != null ? supplier : null;
	}

	@Nullable
	public StructureSpawnerTileEntity<?, ?> getSupplierObject() {
		return supplier != null ? supplier.get() : null;
	}
	
	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT).replace(' ', '_');
	}
}
