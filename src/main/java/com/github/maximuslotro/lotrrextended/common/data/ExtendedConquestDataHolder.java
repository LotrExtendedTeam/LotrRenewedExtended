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
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedIOUtils;

import lotr.common.fac.Faction;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
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
	private boolean lazyLoadedData = false;

	public ExtendedConquestDataHolder(ExtendedLevelData pHolder) {
		super(pHolder);
	}

	@Override
	public void tick(ServerWorld level) {
		// Line from wiki: "Conquest decay begins only after there have been no changes in that square for one hour. 
		// It then decreases by about one point every hour. In order to keep up high rates, you need to keep on fighting enemies in the desired region. "
		// TODO write conquest decay logic
	}

	//######################### Coordinate transformations
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
	public ExtendedConquestPos convertVectorToConquest(Vector3d position) {
		return convertCordsToConquest((int)position.x(), (int)position.z);
	}
	//######################### Conquest handling
	public void addConquest(PlayerEntity player, float conquestAdd, Faction playerFaction, Faction opposingFaction, BlockPos position, boolean hiredUnit) {
		ExtendedConquestRegion conquestRegion = getConquestSquare(convertCordsToConquest(position.getX(), position.getZ()));
		conquestAdd *= conquestRate;
		if(hiredUnit) {
			conquestAdd *= 0.25F;
		}
		conquestRegion.addConquestRaw(playerFaction.getName(), conquestAdd);
		conquestRegion.addConquestRaw(opposingFaction.getName(), -conquestAdd);
		//TODO circular conquest
		//TODO handle other factions inc or dec
		player.sendMessage(new StringTextComponent(String.format("+%.2f Conquest", conquestAdd)), Util.NIL_UUID);
		ExtendedLog.info("Added {} conquest strength for {} for faction {} against {} for new amount {} ", conquestAdd, player.getDisplayName().getString(), 
			playerFaction.getName().toString(), opposingFaction.getName().toString(), conquestRegion.getConquest(playerFaction.getName()));
	}
	//########### Conquest map
	public ExtendedConquestRegion getConquestSquare(Vector3d position) {
		return getConquestSquare(convertVectorToConquest(position));
	}
	public ExtendedConquestRegion getConquestSquare(ExtendedConquestPos conquestPos) {
		lazyLoadConquestData();
		return conquestMap.computeIfAbsent(conquestPos, id -> loadConquestSquare(id));
	}

	public @Nonnull Map<ExtendedConquestPos, ExtendedConquestRegion> getConquestMap() {
		lazyLoadConquestData();
		return Collections.unmodifiableMap(conquestMap);
	}
	public boolean isConquestMapEmpty() {
		return conquestMap.isEmpty();
	}

	public void clearConquestMap() {
		conquestMap.clear();
		lazyLoadedData=true;
		if(this.holder.getSide().isServer()) {
			if(this.conquestFilesFolder != null && this.conquestFilesFolder.exists()) {
				ExtendedIOUtils.tryDeleteDirectoryContents(this.conquestFilesFolder.toPath());
			}
			ExtendedPacketHandler.sendToAllClients(ExtendedSConquestDataPacket.makeResetPacket(gridSquareScale));
		}
	}
	//########### Conquest scale
	public int getGridSquareScale() {
		return gridSquareScale;
	}
	/**
	 * If called on server, ensure the conquest map was also wiped afterwards
	 * @param gridSquareScale The new grid square scale, represented in powers of 2
	 * */
	public void setGridSquareScale(int gridSquareScale) {
		if(this.gridSquareScale != gridSquareScale) {
			this.gridSquareScale = gridSquareScale;
			this.conquestMap.clear();
			this.lazyLoadedData = false;
			if (this.holder.getSide().isServer()) {
				saveDataFile();
			}
		}
	}

	//########### Conquest rate
	public float getConquestRate() {
		return conquestRate;
	}

	public void setConquestRate(float conquestRate) {
		this.conquestRate = conquestRate;
		if(this.holder.getSide().isServer()) {
			saveDataFile();
		}
	}

	//######################### Data Read/Write + Send/Receive sync
	private ExtendedConquestRegion loadConquestSquare(ExtendedConquestPos conquestPos) {
		CompoundNBT nbt = ExtendedIOUtils.load(getConquestDatafile(conquestPos));
		if(nbt.isEmpty()) {
			return new ExtendedConquestRegion(conquestPos);
		}
		ExtendedConquestRegion loadedRegion = ExtendedConquestRegion.loadFromNBT(nbt);
		if(!conquestPos.equals(loadedRegion.getConquestPos())) {
			ExtendedLog.warn("Mismatched region position loaded at {}. Correcting key position.", conquestPos);
			loadedRegion.setConquestPos(conquestPos);
		}
		return loadedRegion;
	}

	private void lazyLoadConquestData() {
		if(!lazyLoadedData) {
			if(this.holder.getSide().isServer() && conquestFilesFolder != null && conquestFilesFolder.exists() && conquestFilesFolder.isDirectory()) {
				File[] files = conquestFilesFolder.listFiles((folder, name) -> name.endsWith(".dat"));
				if(files != null) {
					for(File file : files) {
						String dataFile = file.getName();
						String[] cords = dataFile.substring(0, dataFile.length() - 4).split("\\.");
						if(cords.length == 2) {
							try {
								int x = Integer.parseInt(cords[0]);
								int z = Integer.parseInt(cords[1]);
								ExtendedConquestPos pos = new ExtendedConquestPos(x, z);
								conquestMap.computeIfAbsent(pos, this::loadConquestSquare);
							} catch (NumberFormatException e) {
								ExtendedLog.warn("Failed to parse conquest pos from filename: " + dataFile);
							}
						}
					}
				}
			}
			lazyLoadedData = true;
		}
	}
	@Override
	public void loadDataServer(CompoundNBT pTag, MinecraftServer server) {
		conquestFilesFolder = ExtendedIOUtils.getDirOrMake(new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "conquest_regions"));
		conquestDataFile = new File(ExtendedIOUtils.getDirInServer(ExtendedIOUtils.EXTENDED_FOLDER, server, true), "CONQUEST.dat");
		conquestMap.clear();
		lazyLoadedData=false;
		CompoundNBT nbt = ExtendedIOUtils.load(conquestDataFile);
		if(nbt.contains("conquest_scale")) {
			gridSquareScale=nbt.getInt("conquest_scale");
		}
		if(nbt.contains("conquest_rate")) {
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

	public void sendConquestDataToClient(ResourceLocation pFaction, ServerPlayerEntity pPlayer) {
		lazyLoadConquestData();
		ExtendedSConquestDataPacket packet = new ExtendedSConquestDataPacket(pFaction, gridSquareScale, conquestMap);
		ExtendedPacketHandler.sendToClient(packet, pPlayer);
	}

	public void receiveConquestDataOnClient(ExtendedSConquestDataPacket pPacket) {
		this.gridSquareScale = pPacket.getConquestScale();
		this.conquestMap.clear();
		this.conquestMap.putAll(pPacket.getConquestMap());
	}

	@Override
	protected void clearData() {
		conquestMap.clear();
		conquestFilesFolder=null;
		conquestDataFile=null;
		lazyLoadedData=false;
	}

	@Override
	public void saveData(CompoundNBT pTag) {
		//TODO figure out a less expensive way to handle this
		for(Entry<ExtendedConquestPos, ExtendedConquestRegion> regions : conquestMap.entrySet()) {
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
}