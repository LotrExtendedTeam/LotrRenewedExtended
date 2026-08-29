package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFellowshipDataHolder extends ExtendedAbstractLevelDataHolder {
	private final Map<UUID, ExtendedFellowship> fellowshipMap = new HashMap<>();
	private File fellowshipDataFolder;

	public ExtendedFellowshipDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void tick(ServerWorld level) {}

	@Override
	public void loadDataServer(CompoundNBT pTag, MinecraftServer server) {
		fellowshipDataFolder = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "fellowships"));
		fellowshipMap.clear();
		// Copy fellowships from /lotr/fellowships folder
		File oldDir = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.LOTR_FOLDER, server, false), "fellowships");
		if(oldDir.exists()) {
			ExtendedIOUtils.moveIfOldPath(fellowshipDataFolder.toPath(), oldDir.toPath(), true);
		}
	}

	@Override
	public void clearData() {
		fellowshipMap.clear();
	}

	@Override
	public void saveData(CompoundNBT pTag) {
		for (ExtendedFellowship fs : fellowshipMap.values()) {
			if (fs.needsSave()) {
				ExtendedIOUtils.save(getFellowshipDatafile(fs.getFellowshipId()), fs.save());
			}
		}
	}

	private File getFellowshipDatafile(UUID pFellowshipId) {
		return new File(fellowshipDataFolder, pFellowshipId.toString() + ".dat");
	}

	public void addFellowship(ExtendedFellowship pFellowship) {
		fellowshipMap.putIfAbsent(pFellowship.getFellowshipId(), pFellowship);
	}

	public ExtendedFellowship getFellowship(UUID pFellowshipId) {
		ExtendedFellowship fellowship = fellowshipMap.computeIfAbsent(pFellowshipId, id -> loadFellowship(id));
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