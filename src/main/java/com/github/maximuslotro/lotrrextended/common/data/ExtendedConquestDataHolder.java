package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestPos;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import lotr.common.fac.Faction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

// TODO add conquest add logic
public class ExtendedConquestDataHolder extends ExtendedAbstractLevelDataHolder {
	private final Map<ExtendedConquestPos, ExtendedConquestRegion> conquestMap = new HashMap<>();
	private File conquestFilesFolder;
	private File conquestDataFile;

	public ExtendedConquestDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	public void addConquest(PlayerEntity player, float conquestAdd, Faction playerFaction, Faction opposingFactions) {
		//TODO handle adding this data to CONQUEST_MAP
	}

	@Override
	public void tick(ServerWorld level) {
		// Line from wiki: Conquest decay begins only after there have been no changes in that square for one hour. 
		// It then decreases by about one point every hour. In order to keep up high rates, you need to keep on fighting enemies in the desired region. 
		// TODO write conquest decay logic
	}

	@Override
	public void loadDataOnce(CompoundNBT pTag, MinecraftServer server) {
		conquestFilesFolder = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "conquest_regions"));
		conquestDataFile = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "CONQUEST.dat");
		conquestMap.clear();
	}

	@Override
	public void clearData() {
		conquestMap.clear();
		conquestFilesFolder=null;
		conquestDataFile=null;
	}

	@Override
	public void saveData(CompoundNBT pTag) {
		//TODO figure out a less expensive way to handle this
		for (Entry<ExtendedConquestPos, ExtendedConquestRegion> regions : conquestMap.entrySet()) {
			if (regions.getValue().needsSave()) {
				ExtendedIOUtils.save(getConquestDatafile(regions.getKey()), regions.getValue().save());
			}
		}
	}

	private File getConquestDatafile(ExtendedConquestPos conquestPos) {
		return new File(conquestFilesFolder, conquestPos.getPosX() + "." + conquestPos.getPosZ() + ".dat");
	}

	public ExtendedConquestRegion getConquestSquare(ExtendedConquestPos conquestPos) {
		ExtendedConquestRegion conquestSquare = conquestMap.computeIfAbsent(conquestPos, id -> loadConquestSquare(id));
		return conquestSquare;
	}

	private ExtendedConquestRegion loadConquestSquare(ExtendedConquestPos conquestPos) {
		CompoundNBT nbt = ExtendedIOUtils.load(getConquestDatafile(conquestPos));
		return nbt.isEmpty() ? new ExtendedConquestRegion(conquestPos) : ExtendedConquestRegion.loadFromNBT(nbt);
	}
}