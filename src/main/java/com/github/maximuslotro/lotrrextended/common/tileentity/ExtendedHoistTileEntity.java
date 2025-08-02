package com.github.maximuslotro.lotrrextended.common.tileentity;

import javax.annotation.Nullable;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class ExtendedHoistTileEntity extends TileEntity {
	private int horizontalOffset;
	private int verticalOffset;
	private int verticalHeight;

	public ExtendedHoistTileEntity() {
		super(ExtendedTileEntities.HOIST.get());
	}

	public int getHorizontalOffset() {
		return horizontalOffset;
	}

	public int getVerticalOffset() {
		return verticalOffset;
	}

	public int getVerticalHeight() {
		return verticalHeight;
	}

	public void setParams(int horizontalOffset, int verticalOffset, int verticalHeight) {
		this.horizontalOffset = horizontalOffset;
		this.verticalOffset = verticalOffset;
		this.verticalHeight = verticalHeight;
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		pCompound.putInt("horOffset", horizontalOffset);
		pCompound.putInt("verOffset", verticalOffset);
		pCompound.putInt("verHeight", verticalHeight);
		return pCompound;
	}

	@Override
	public void load(BlockState state, CompoundNBT pCompound) {
		super.load(state, pCompound);
		if(pCompound.contains("horOffset")) {
			horizontalOffset = pCompound.getInt("horOffset");
		}
		if(pCompound.contains("verOffset")) {
			verticalOffset = pCompound.getInt("verOffset");
		}
		if(pCompound.contains("verHeight")) {
			verticalHeight = pCompound.getInt("verHeight");
		}
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
	 * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
	 */
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, -1, this.getUpdateTag());
	}


	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
	}
	
	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link #onDataPacket}
	 */
	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}
}
