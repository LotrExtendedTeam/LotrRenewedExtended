package lotr.common.structure;

import java.util.HashMap;
import java.util.Map;

import lotr.common.LOTRLog;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;

public class StructureFileHandler {
	private static Map<String, StructureFile> loadedStructures = new HashMap<>();
	
	public static StructureFile getStructureFile(String strName, MinecraftServer server) {
		if(loadedStructures.containsKey(strName)) {
			return loadedStructures.get(strName);
		}
		CompoundNBT compound = StructureFileIO.load(strName, server);
		if(compound==null) {
			return null;
		}
		
		CompoundNBT size;
		ListNBT blockMap;
		ListNBT signMap;
		ListNBT entityMap;
		try {
			size = compound.getCompound(StructureScanner.structureSizeHolder);
			blockMap = compound.getList(StructureScanner.blockMapHolder, 10);
			signMap = compound.getList(StructureScanner.signMapHolder, 10);
			entityMap = compound.getList(StructureScanner.entityMapHolder, 10);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		if(size.isEmpty()) {
			LOTRLog.error("Loaded empty structure size! "+strName);
			return null;
		}
		if(blockMap.isEmpty()) {
			LOTRLog.error("Loaded empty structure array! "+strName);
			return null;
		}
		if(signMap.isEmpty()) {
			LOTRLog.error("Loaded empty sign array! "+strName);
		}
		if(entityMap.isEmpty()) {
			LOTRLog.error("Loaded empty entity array! "+strName);
		}
		StructureFile file = new StructureFile(strName, blockMap, size, signMap, entityMap);
		loadedStructures.put(strName, file);
		return file;
	}
}
