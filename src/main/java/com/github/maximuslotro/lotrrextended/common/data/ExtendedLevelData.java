package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.*;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

public class ExtendedLevelData {
	private static final ExtendedLevelData SERVER_INSTANCE = new ExtendedLevelData(LogicalSide.SERVER);
	private File EXTENDED_DATA_FILE;
	private final List<ExtendedAbstractLevelDataHolder> dataHolders;
	private final ExtendedTraderDataHolder traderData;
	private final ExtendedBanditDataHolder banditData;
	private final ExtendedInvasionDataHolder invasionData;
	private final ExtendedBarrowsDataHolder barrowData;
	private final ExtendedTolkienDataHolder tolkienData;
	private final ExtendedFellowshipDataHolder fellowshipData;
	private final ExtendedConquestDataHolder conquestData;

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

	public File getOrCreateDataFile(ServerWorld world) {
		if(EXTENDED_DATA_FILE==null) {
			EXTENDED_DATA_FILE = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, world.getServer(), true), "EXTENDED.dat");
		}
		return EXTENDED_DATA_FILE;
	}

	public void save(ServerWorld world) {
		CompoundNBT levelData = new CompoundNBT();
		for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
			holder.saveData(levelData, world);
		}
		ExtendedIOUtils.save(getOrCreateDataFile(world), levelData);
		this.dirty = false;
	}
	// Should only be loaded once on server start
	public void load(ServerWorld world) {
		if(!needsLoad) {
			return;
		}
		// Copy ExtendedLevelDat from /lotr/EXTENDED.dat
		File oldData = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.LOTR_FOLDER, world.getServer(), false), "EXTENDED.dat");
		if(oldData.exists()) {
			ExtendedIOUtils.moveIfOldPath(getOrCreateDataFile(world).toPath(), oldData.toPath(), false);
		}
		CompoundNBT levelData = ExtendedIOUtils.load(getOrCreateDataFile(world));
		for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
			holder.loadData(levelData, world);
		}
		this.needsLoad = false;
		this.save(world);
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
}
