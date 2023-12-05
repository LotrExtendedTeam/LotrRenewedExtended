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
		try {
			size = compound.getCompound("structureSize");
			blockMap = compound.getList("blockMap", 10);
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
		StructureFile file = new StructureFile(strName, blockMap, size);
		loadedStructures.put(strName, file);
		return file;
	}
}
