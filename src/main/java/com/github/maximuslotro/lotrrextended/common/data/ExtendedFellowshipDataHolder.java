package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFellowshipDataHolder extends ExtendedAbstractLevelDataHolder {
	private Map<UUID, ExtendedFellowship> FELLOWSHIP_MAP = new HashMap<>();
	private File FELLOWSHIP_FOLDER;

	public ExtendedFellowshipDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void tick(ServerWorld level) {}

	@Override
	public void loadData(CompoundNBT pTag, ServerWorld world) {
		FELLOWSHIP_FOLDER = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, world.getServer(), true), "fellowships"));
		FELLOWSHIP_MAP.clear();
		// Copy fellowships from /lotr/fellowships folder
		File oldDir = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.LOTR_FOLDER, world.getServer(), false), "fellowships");
		if(oldDir.exists()) {
			ExtendedIOUtils.moveIfOldPath(FELLOWSHIP_FOLDER.toPath(), oldDir.toPath(), true);
		}
	}

	@Override
	public void saveData(CompoundNBT pTag, ServerWorld world) {
		for (ExtendedFellowship fs : FELLOWSHIP_MAP.values()) {
			if (fs.needsSave()) {
				ExtendedIOUtils.save(getFellowshipDatafile(fs.getFellowshipId()), fs.save());
			}
		}
	}

	private File getFellowshipDatafile(UUID pFellowshipId) {
		return new File(FELLOWSHIP_FOLDER, pFellowshipId.toString() + ".dat");
	}

	public void addFellowship(ExtendedFellowship pFellowship) {
		FELLOWSHIP_MAP.putIfAbsent(pFellowship.getFellowshipId(), pFellowship);
	}

	public ExtendedFellowship getFellowship(UUID pFellowshipId) {
		ExtendedFellowship fellowship = FELLOWSHIP_MAP.computeIfAbsent(pFellowshipId, id -> loadFellowship(id));
		if (fellowship != null && fellowship.isDisbanded()) {
			return null;
		}
		return fellowship;
	}

	private ExtendedFellowship loadFellowship(UUID pFellowshipId) {
		File fsDat = getFellowshipDatafile(pFellowshipId);
		CompoundNBT nbt = ExtendedIOUtils.load(fsDat);
		if (nbt.isEmpty()) {
			return null;
		}
		ExtendedFellowship fellowship = new ExtendedFellowship(pFellowshipId);
		fellowship.load(nbt);
		return fellowship;
	}
}