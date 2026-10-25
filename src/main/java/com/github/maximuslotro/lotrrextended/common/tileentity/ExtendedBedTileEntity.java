package com.github.maximuslotro.lotrrextended.common.tileentity;

import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBedType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;

import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Based on {@link BedTileEntity}
 */
public class ExtendedBedTileEntity extends TileEntity {
	private ExtendedBedType bedType;

	public ExtendedBedTileEntity() {
		super(ExtendedTileEntities.BED.get());
	}

	public ExtendedBedTileEntity(ExtendedBedType pBedType) {
		this();
		this.setBedType(pBedType);
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 11, this.getUpdateTag());
	}

	public ExtendedBedType getBedType() {
		if (this.bedType == null) {
			this.bedType = ((ExtendedBedBlock)this.getBlockState().getBlock()).getBedType();
		}

		return this.bedType;
	}

	public void setBedType(ExtendedBedType pBedType) {
		this.bedType = pBedType;
	}
}