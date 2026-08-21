package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.*;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

public class ExtendedLevelData {
	private static final ExtendedLevelData SERVER_INSTANCE = new ExtendedLevelData(LogicalSide.SERVER);
	private File extendedDataFile;
	private final List<ExtendedAbstractLevelDataHolder> dataHolders;
	private final ExtendedTraderDataHolder traderData;
	private final ExtendedBanditDataHolder banditData;
	private final ExtendedInvasionDataHolder invasionData;
	private final ExtendedBarrowsDataHolder barrowData;
	private final ExtendedTolkienDataHolder tolkienData;
	private final ExtendedFellowshipDataHolder fellowshipData;
	private final ExtendedConquestDataHolder conquestData;
	private final ExtendedPlayerInfoHolder playerData;

	private boolean dirty = false;
	private boolean needsLoad = true;
	private final LogicalSide side;

	public static ExtendedLevelData serverInstance() {
		return SERVER_INSTANCE;
	}

	public ExtendedLevelData(LogicalSide side) {
		this.side = side;
		dataHolders = new ArrayList<>();
		traderData = addHolder(new ExtendedTraderDataHolder(this));
		banditData = addHolder(new ExtendedBanditDataHolder(this));
		invasionData = addHolder(new ExtendedInvasionDataHolder(this));
		barrowData = addHolder(new ExtendedBarrowsDataHolder(this));
		tolkienData = addHolder(new ExtendedTolkienDataHolder(this));
		fellowshipData = addHolder(new ExtendedFellowshipDataHolder(this));
		conquestData = addHolder(new ExtendedConquestDataHolder(this));
		playerData = addHolder(new ExtendedPlayerInfoHolder(this));
	}

	private <H extends ExtendedAbstractLevelDataHolder> H addHolder(H dataHolder) {
		dataHolders.add(dataHolder);
		return dataHolder;
	}

	public boolean needsLoad() {
		return needsLoad;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void markDirty() {
		this.dirty = true;
	}

	public File getOrCreateDataFile(MinecraftServer server) {
		if(extendedDataFile==null) {
			extendedDataFile = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "EXTENDED.dat");
		}
		return extendedDataFile;
	}

	public void save(MinecraftServer server) {
		CompoundNBT levelData = new CompoundNBT();
		for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
			holder.saveData(levelData);
		}
		ExtendedIOUtils.save(getOrCreateDataFile(server), levelData);
		this.dirty = false;
	}
	public void loadAndLock(MinecraftServer server) {
		if(!needsLoad) {
			return;
		}
		// Copy ExtendedLevelDat from /lotr/EXTENDED.dat
		File oldData = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.LOTR_FOLDER, server, false), "EXTENDED.dat");
		if(oldData.exists()) {
			ExtendedIOUtils.moveIfOldPath(getOrCreateDataFile(server).toPath(), oldData.toPath(), false);
		}
		CompoundNBT levelData = ExtendedIOUtils.load(getOrCreateDataFile(server));
		for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
			holder.loadDataOnce(levelData, server);
		}
		this.needsLoad = false;
		this.save(server);
	}
	/**
	 * Resets instance when the server stops to prevent leaking between worlds
	 */
	public void resetOnServerStop() {
		this.extendedDataFile = null;
		this.needsLoad = true;
		this.dirty = false;
		for (ExtendedAbstractLevelDataHolder holder : dataHolders) {
            holder.clearData();
        }
	}

	public void tick(ServerWorld world) {
		for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
			holder.tick(world);
		}
	}

	public ExtendedTraderDataHolder getTraderData() {
		return traderData;
	}

	public ExtendedBanditDataHolder getBanditData() {
		return banditData;
	}

	public ExtendedInvasionDataHolder getInvasionData() {
		return invasionData;
	}

	public ExtendedBarrowsDataHolder getBarrowData() {
		return barrowData;
	}

	public ExtendedTolkienDataHolder getTolkienData() {
		return tolkienData;
	}

	public ExtendedFellowshipDataHolder getFellowshipData() {
		return fellowshipData;
	}

	public ExtendedConquestDataHolder getConquestData() {
		return conquestData;
	}

	public ExtendedPlayerInfoHolder getPlayerData() {
		return playerData;
	}
}
