package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Locale;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.tileentity.spawners.ExtendedHobbitFarmerSpawnerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.spawners.ExtendedStructureSpawnerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.spawners.HobbitBartenderSpawnerTileEntity;

import net.minecraft.util.IStringSerializable;

public enum ExtendedEntitySpawnTypes implements IStringSerializable {
	HOBBIT_BARTENDER(HobbitBartenderSpawnerTileEntity::new),
	HOBBIT_FARMER(ExtendedHobbitFarmerSpawnerTileEntity::new);
	
	private final Supplier<? extends ExtendedStructureSpawnerTileEntity<?, ?>> supplier;
	public static final ExtendedEntitySpawnTypes[] LIST=values();

	ExtendedEntitySpawnTypes(@Nullable Supplier<? extends ExtendedStructureSpawnerTileEntity<?, ?>> supplier) {
		this.supplier=supplier;
	}

	public boolean hasSupplier() {
		return supplier!=null;
	}

	@Nullable
	public Supplier<? extends ExtendedStructureSpawnerTileEntity<?, ?>> getSupplier() {
		return supplier != null ? supplier : null;
	}

	@Nullable
	public ExtendedStructureSpawnerTileEntity<?, ?> getSupplierObject() {
		return supplier != null ? supplier.get() : null;
	}
	
	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT).replace(' ', '_');
	}
}
