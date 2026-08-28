package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestPos;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSConquestDataPacket;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import lotr.common.fac.Faction;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

// TODO add conquest add logic
public class ExtendedConquestDataHolder extends ExtendedAbstractLevelDataHolder {
	private final Map<ExtendedConquestPos, ExtendedConquestRegion> conquestMap = new HashMap<>();
	private File conquestFilesFolder;
	private File conquestDataFile;
	private int gridSquareScale=3;
	private int mapScale;
	private int mapXOffset;
	private int mapZOffset;

	public ExtendedConquestDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
		MapSettingsManager mapSettings = MapSettingsManager.serverInstance();
		mapScale = mapSettings.getCurrentLoadedMap().getScaleFactor();
		mapXOffset = mapSettings.getCurrentLoadedMap().getOriginX();
		mapZOffset = mapSettings.getCurrentLoadedMap().getOriginZ();
	}

	public int convertCordsToConquestX(int xCord) {
		return convertMapToConquest((xCord >> mapScale) + mapXOffset);
	}
	public int convertCordsToConquestZ(int zCord) {
		return convertMapToConquest((zCord >> mapScale) + mapZOffset);
	}
	public int convertMapToConquest(int mapCord) {
		return mapCord >> gridSquareScale;
	}
	public int convertConquestToMap(int conquestCord) {
		return conquestCord << gridSquareScale;
	}

	public ExtendedConquestPos convertCordsToConquest(int xCord, int zCord) {
		return new ExtendedConquestPos(convertCordsToConquestX(xCord), convertCordsToConquestZ(zCord));
	}

	public void addConquest(PlayerEntity player, float conquestAdd, Faction playerFaction, Faction opposingFaction, Vector3i position) {
		ExtendedConquestRegion conquestRegion = getConquestSquare(convertCordsToConquest(position.getX(), position.getZ()));
		conquestRegion.addConquest(playerFaction.getName(), conquestAdd);
		conquestRegion.addConquest(opposingFaction.getName(), -conquestAdd);
		//TODO handle hired lesser kill amount
		//TODO circular conquest
		//TODO add player notification
		//TODO handle other factions inc or dec
		ExtendedLog.info("Added Conquest for {} for faction {} against {} for amount: {}", player.getDisplayName().getString(), 
				playerFaction.getName().toString(), opposingFaction.getName().toString(), conquestAdd);
	}

	public ExtendedConquestRegion getConquestSquare(ExtendedConquestPos conquestPos) {
		ExtendedConquestRegion conquestSquare = conquestMap.computeIfAbsent(conquestPos, id -> loadConquestSquare(id));
		return conquestSquare;
	}

	private ExtendedConquestRegion loadConquestSquare(ExtendedConquestPos conquestPos) {
		CompoundNBT nbt = ExtendedIOUtils.load(getConquestDatafile(conquestPos));
		return nbt.isEmpty() ? new ExtendedConquestRegion(conquestPos) : ExtendedConquestRegion.loadFromNBT(nbt);
	}

	public void sendConquestDataToClient(ResourceLocation pFaction, ServerPlayerEntity pPlayer) {
		ExtendedSConquestDataPacket packet = new ExtendedSConquestDataPacket(pFaction, gridSquareScale);
		ExtendedPacketHandler.sendToClient(packet, pPlayer);
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
		CompoundNBT nbt = ExtendedIOUtils.load(conquestDataFile);
		if (nbt.contains("conquest_scale")) {
			gridSquareScale=nbt.getInt("conquest_scale");
		}
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
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("conquest_scale", gridSquareScale);
		ExtendedIOUtils.save(conquestDataFile, nbt);
	}

	private File getConquestDatafile(ExtendedConquestPos conquestPos) {
		return new File(conquestFilesFolder, conquestPos.getPosX() + "." + conquestPos.getPosZ() + ".dat");
	}
}