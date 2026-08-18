package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.data.SaveUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

public class ExtendedLevelData {
	private static final ExtendedLevelData SERVER_INSTANCE = new ExtendedLevelData(LogicalSide.SERVER);
	private final List<ExtendedAbstractLevelDataHolder> dataHolders;
	private final ExtendedTraderDataHolder traderData;
	private final ExtendedBanditDataHolder banditData;
	private final ExtendedInvasionDataHolder invasionData;
	private final ExtendedBarrowsDataHolder barrowData;
	private final ExtendedTolkienDataHolder tolkienData;
	// Access through static methods instead of though this field
	private final ExtendedFellowshipDataHolder fellowshipData;

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

	public void save(ServerWorld world) {
		try {
			File lotrDataFile = getLOTRDataFile(world);
			if (!lotrDataFile.exists()) {
				SaveUtil.saveNBTToFile(lotrDataFile, new CompoundNBT());
			}
			CompoundNBT levelData = new CompoundNBT();
			for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
				holder.saveData(levelData, world);
			}

			SaveUtil.saveNBTToFile(lotrDataFile, levelData);
			this.dirty = false;
		} catch (IOException e) {
			ExtendedLog.error("Error saving Extended mod data!", e);
		}
	}
	// Should only be loaded once on server start
	public void load(ServerWorld world) {
		try {
			if(!needsLoad) {
				return;
			}
			CompoundNBT levelData = SaveUtil.loadNBTFromFile(getLOTRDataFile(world));
			for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
				holder.loadData(levelData);
			}
			this.needsLoad = false;
			this.save(world);
		} catch (IOException e) {
			ExtendedLog.error("Error loading Extended mod data!", e);
		}
	}

	public void tick(ServerWorld world) {
		for(ExtendedAbstractLevelDataHolder holder : dataHolders) {
			holder.tick(world);
		}
	}

	private static File getLOTRDataFile(ServerWorld world) {
		return new File(SaveUtil.getOrCreateLOTRDir(world), "EXTENDED.dat");
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
}
