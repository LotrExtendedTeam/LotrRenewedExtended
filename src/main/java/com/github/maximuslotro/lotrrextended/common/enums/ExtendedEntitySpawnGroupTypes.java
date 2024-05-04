package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Locale;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.tileentity.spawners.group.*;

import net.minecraft.util.IStringSerializable;

public enum ExtendedEntitySpawnGroupTypes implements IStringSerializable {
	RANGER_CAMP_ANIMALS(RangerCampAnimalsGroupSpawnerTileEntity::new);
	
	private final Supplier<? extends ExtendedStructureGroupSpawnerTileEntity<?>> supplier;
	public static final ExtendedEntitySpawnGroupTypes[] LIST=values();

	ExtendedEntitySpawnGroupTypes(@Nullable Supplier<? extends ExtendedStructureGroupSpawnerTileEntity<?>> supplier) {
		this.supplier=supplier;
	}

	public boolean hasSupplier() {
		return supplier!=null;
	}

	@Nullable
	public Supplier<? extends ExtendedStructureGroupSpawnerTileEntity<?>> getSupplier() {
		return supplier != null ? supplier : null;
	}

	@Nullable
	public ExtendedStructureGroupSpawnerTileEntity<?> getSupplierObject() {
		return supplier != null ? supplier.get() : null;
	}
	
	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT).replace(' ', '_');
	}
}
