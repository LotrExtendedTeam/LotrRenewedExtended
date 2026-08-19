package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestPos;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public class ExtendedConquestDataHolder extends ExtendedAbstractLevelDataHolder {
	private Map<ExtendedConquestPos, ExtendedConquestRegion> CONQUEST_MAP = new HashMap<>();
	private File CONQUEST_FOLDER;
	private File CONQUEST_DATA_FILE;

	public ExtendedConquestDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void tick(ServerWorld level) {
		
	}

	@Override
	public void loadData(CompoundNBT pTag, ServerWorld world) {
		CONQUEST_FOLDER = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, world.getServer(), true), "conquest_regions"));
		CONQUEST_DATA_FILE = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, world.getServer(), true), "CONQUEST.dat");
		CONQUEST_MAP.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag, ServerWorld world) {
		for (Entry<ExtendedConquestPos, ExtendedConquestRegion> regions : CONQUEST_MAP.entrySet()) {
			if (regions.getValue().needsSave()) {
				ExtendedIOUtils.save(getConquestDatafile(regions.getKey()), regions.getValue().save());
			}
		}
	}

	private File getConquestDatafile(ExtendedConquestPos conquestPos) {
		return new File(CONQUEST_FOLDER, conquestPos.getPosX() + "." + conquestPos.getPosZ() + ".dat");
	}

	public ExtendedConquestRegion getConquestSquare(ExtendedConquestPos conquestPos) {
		ExtendedConquestRegion conquestSquare = CONQUEST_MAP.computeIfAbsent(conquestPos, id -> loadConquestSquare(id));
		return conquestSquare;
	}

	private ExtendedConquestRegion loadConquestSquare(ExtendedConquestPos conquestPos) {
		CompoundNBT nbt = ExtendedIOUtils.load(getConquestDatafile(conquestPos));
		return nbt.isEmpty() ? new ExtendedConquestRegion(conquestPos) : ExtendedConquestRegion.loadFromNBT(nbt);
	}
}