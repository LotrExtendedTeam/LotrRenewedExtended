package com.github.maximuslotro.lotrrextended.common.data;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestPos;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSConquestDataPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSConquestResetPacket;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import lotr.common.fac.Faction;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

// TODO add missing logic
public class ExtendedConquestDataHolder extends ExtendedAbstractLevelDataHolder {
	private final Map<ExtendedConquestPos, ExtendedConquestRegion> conquestMap = new ConcurrentHashMap<>();
	private File conquestFilesFolder;
	private File conquestDataFile;
	private int gridSquareScale=3;
	private int mapScale;
	private int mapOriginX;
	private int mapOriginZ;
	private float conquestRate=1.0F;

	public ExtendedConquestDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}
	public int getBlocksPerMapPixel() {
		return 1 << mapScale;
	}
	public int getMapPixelsPerConquestRegion() {
		return 1 << gridSquareScale;
	}
	public int getBlocksPerConquestRegion() {
		return 1 << (mapScale + gridSquareScale);
	}
	public int convertWorldToMapPixelX(int xBlock) {
		return Math.floorDiv(xBlock, getBlocksPerMapPixel()) + mapOriginX;
	}
	public int convertWorldToMapPixelZ(int zBlock) {
		return Math.floorDiv(zBlock, getBlocksPerMapPixel()) + mapOriginZ;
	}
	public int convertMapPixelToWorldX(int mapPixelX) {
		return (mapPixelX - mapOriginX) * getBlocksPerMapPixel();
	}
	public int convertMapPixelToWorldZ(int mapPixelZ) {
		return (mapPixelZ - mapOriginZ) * getBlocksPerMapPixel();
	}
	public int convertMapPixelToConquestX(int mapPixelX) {
		return Math.floorDiv(mapPixelX, getMapPixelsPerConquestRegion());
	}
	public int convertMapPixelToConquestZ(int mapPixelZ) {
		return Math.floorDiv(mapPixelZ, getMapPixelsPerConquestRegion());
	}
	public int convertConquestToMapPixelX(int conquestX) {
		return conquestX * getMapPixelsPerConquestRegion();
	}
	public int convertConquestToMapPixelZ(int conquestZ) {
		return conquestZ * getMapPixelsPerConquestRegion();
	}
	public int convertCordsToConquestX(int xBlock) {
		int mapPixelX = convertWorldToMapPixelX(xBlock);
		return convertMapPixelToConquestX(mapPixelX);
	}
	public int convertCordsToConquestZ(int zBlock) {
		int mapPixelZ = convertWorldToMapPixelZ(zBlock);
		return convertMapPixelToConquestZ(mapPixelZ);
	}
	public int getConquestSizeInMapPixels() {
		return getMapPixelsPerConquestRegion();
	}
	public ExtendedConquestPos convertCordsToConquest(int xCord, int zCord) {
		return new ExtendedConquestPos(convertCordsToConquestX(xCord), convertCordsToConquestZ(zCord));
	}

	public void addConquest(PlayerEntity player, float conquestAdd, Faction playerFaction, Faction opposingFaction, Vector3i position) {
		ExtendedConquestRegion conquestRegion = getConquestSquare(convertCordsToConquest(position.getX(), position.getZ()));
		conquestAdd *= conquestRate;
		conquestRegion.addConquestRaw(playerFaction.getName(), conquestAdd);
		conquestRegion.addConquestRaw(opposingFaction.getName(), -conquestAdd);
		//TODO handle hired unit lesser kill amount
		//TODO circular conquest
		//TODO add player notification
		//TODO handle other factions inc or dec
		ExtendedLog.info("Added {} conquest strength for {} for faction {} against {} for new amount {} ", conquestAdd, player.getDisplayName().getString(), 
			playerFaction.getName().toString(), opposingFaction.getName().toString(), conquestRegion.getConquest(playerFaction.getName()));
	}
	public ExtendedConquestRegion getConquestSquareCords(Vector3d position) {
		return getConquestSquare(convertCordsToConquest((int)position.x(), (int)position.z));
	}
	public ExtendedConquestRegion getConquestSquare(ExtendedConquestPos conquestPos) {
		return conquestMap.computeIfAbsent(conquestPos, id -> loadConquestSquare(id));
	}

	private ExtendedConquestRegion loadConquestSquare(ExtendedConquestPos conquestPos) {
		CompoundNBT nbt = ExtendedIOUtils.load(getConquestDatafile(conquestPos));
		if (nbt.isEmpty()) {
			return new ExtendedConquestRegion(conquestPos);
		}
		ExtendedConquestRegion loadedRegion = ExtendedConquestRegion.loadFromNBT(nbt);
		if (!conquestPos.equals(loadedRegion.getConquestPos())) {
			ExtendedLog.warn("Mismatched region position loaded at {}. Correcting key position.", conquestPos);
			loadedRegion.setConquestPos(conquestPos);
		}
		return loadedRegion;
	}

	public void sendConquestDataToClient(ResourceLocation pFaction, ServerPlayerEntity pPlayer) {
		ExtendedSConquestDataPacket packet = new ExtendedSConquestDataPacket(pFaction, gridSquareScale, conquestMap);
		ExtendedPacketHandler.sendToClient(packet, pPlayer);
	}

	public void receiveConquestDataOnClient(ExtendedSConquestDataPacket pPacket) {
		this.gridSquareScale = pPacket.getConquestScale();
		this.conquestMap.clear();
		this.conquestMap.putAll(pPacket.getConquestMap());
	}

	@Override
	public void tick(ServerWorld level) {
		// Line from wiki: "Conquest decay begins only after there have been no changes in that square for one hour. 
		// It then decreases by about one point every hour. In order to keep up high rates, you need to keep on fighting enemies in the desired region. "
		// TODO write conquest decay logic
	}

	@Override
	public void loadDataServer(CompoundNBT pTag, MinecraftServer server) {
		conquestFilesFolder = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "conquest_regions"));
		conquestDataFile = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "CONQUEST.dat");
		conquestMap.clear();
		CompoundNBT nbt = ExtendedIOUtils.load(conquestDataFile);
		if (nbt.contains("conquest_scale")) {
			gridSquareScale=nbt.getInt("conquest_scale");
		}
		if (nbt.contains("conquest_rate")) {
			conquestRate=nbt.getFloat("conquest_rate");
		}
		loadDataShared();
	}

	@Override
	public void loadDataClient() {
		loadDataShared();
	}

	private void loadDataShared() {
		MapSettingsManager mapSettings = MapSettingsManager.sidedInstance(holder.getSide());
		mapScale = mapSettings.getCurrentLoadedMap().getScalePower();
		mapOriginX = mapSettings.getCurrentLoadedMap().getOriginX();
		mapOriginZ = mapSettings.getCurrentLoadedMap().getOriginZ();
	}

	@Override
	protected void clearData() {
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
		saveDataFile();
	}

	public void saveDataFile() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("conquest_scale", gridSquareScale);
		nbt.putFloat("conquest_rate", conquestRate);
		ExtendedIOUtils.save(conquestDataFile, nbt);
	}

	private File getConquestDatafile(ExtendedConquestPos conquestPos) {
		return new File(conquestFilesFolder, conquestPos.getPosX() + "." + conquestPos.getPosZ() + ".dat");
	}
	public @Nonnull Map<ExtendedConquestPos, ExtendedConquestRegion> getConquestMap() {
		return Collections.unmodifiableMap(conquestMap);
	}

	public int getGridSquareScale() {
		return gridSquareScale;
	}

	/**
	 * If called on server, ensure the conquest map was also wiped afterwards
	 * @param gridSquareScale The new grid square scale, represented in powers of 2
	 * */
	public void setGridSquareScale(int gridSquareScale) {
		if (this.gridSquareScale != gridSquareScale) {
			this.gridSquareScale = gridSquareScale;
			this.conquestMap.clear();
			if (this.holder.getSide().isServer()) {
				saveDataFile();
			}
		}
	}

	public float getConquestRate() {
		return conquestRate;
	}

	public void setConquestRate(float conquestRate) {
		this.conquestRate = conquestRate;
		if(this.holder.getSide().isServer()) {
			saveDataFile();
		}
	}

	public void clearConquestMap() {
		conquestMap.clear();
		if(this.holder.getSide().isServer()) {
			if (this.conquestFilesFolder != null && this.conquestFilesFolder.exists()) {
				ExtendedIOUtils.tryDeleteDirectoryContents(this.conquestFilesFolder.toPath());
			}
			ExtendedPacketHandler.sendToAllClients(new ExtendedSConquestResetPacket(gridSquareScale));
		}
	}
}