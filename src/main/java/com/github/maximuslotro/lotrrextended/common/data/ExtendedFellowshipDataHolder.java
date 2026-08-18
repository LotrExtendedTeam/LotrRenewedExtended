package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.data.SaveUtil;
import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFellowshipDataHolder extends ExtendedAbstractLevelDataHolder {
	private static Map<UUID, ExtendedFellowship> FELLOWSHIP_MAP = new HashMap<>();

	public ExtendedFellowshipDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void tick(ServerWorld level) {}

	@Override
	public void loadData(CompoundNBT pTag) {	
		FELLOWSHIP_MAP.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag, ServerWorld world) {
		for (ExtendedFellowship fs : FELLOWSHIP_MAP.values()) {
			if (fs.needsSave()) {
				saveFellowship(world, fs);
			}
		}
	}

	public static void saveFellowship(ServerWorld world, ExtendedFellowship pFellowship) {
		try {
			CompoundNBT nbt = new CompoundNBT();
			pFellowship.save(nbt);
			SaveUtil.saveNBTToFile(getFellowshipDatafile(world, pFellowship.getFellowshipId()), nbt);
		} catch (IOException e) {
			ExtendedLog.error("Error saving fellowship data for {}", pFellowship.getFellowshipId(), e);
		}
	}

	private static File getSaveDir(ServerWorld pLevel) {
		File dir = new File(SaveUtil.getOrCreateLOTRDir(pLevel), "fellowships");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	private static File getFellowshipDatafile(ServerWorld pLevel, UUID pFellowshipId) {
		return new File(getSaveDir(pLevel), pFellowshipId.toString() + ".dat");
	}

	public static void addFellowship(ExtendedFellowship pFellowship) {
		FELLOWSHIP_MAP.putIfAbsent(pFellowship.getFellowshipId(), pFellowship);
	}

	public static ExtendedFellowship getFellowship(ServerWorld world, UUID pFellowshipId) {
		ExtendedFellowship fellowship = FELLOWSHIP_MAP.computeIfAbsent(pFellowshipId, id -> loadFellowship(world, id));
		if (fellowship != null && fellowship.isDisbanded()) {
			return null;
		}
		return fellowship;
	}

	private static ExtendedFellowship loadFellowship(ServerWorld world, UUID pFellowshipId) {
		File fsDat = getFellowshipDatafile(world, pFellowshipId);
		try {
			CompoundNBT nbt = SaveUtil.loadNBTFromFile(fsDat);
			if (nbt.isEmpty()) {
				return null;
			}
			ExtendedFellowship fellowship = new ExtendedFellowship(pFellowshipId);
			fellowship.load(nbt);
			return fellowship;
		} catch (IOException e) {
			ExtendedLog.error("Error loading LOTR fellowship data for {}", fsDat.getName(), e);
			return null;
		}
	}
}