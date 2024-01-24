package lotr.common.structure;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import lotr.common.structure.util.StructureStrings;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;

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
		Direction dir;
		try {
			size = compound.getCompound(StructureStrings.structureSizeHolder);
			blockMap = compound.getList(StructureStrings.blockMapHolder, 10);
			signMap = compound.getList(StructureStrings.signMapHolder, 10);
			entityMap = compound.getList(StructureStrings.entityMapHolder, 10);
			dir = Direction.byName(compound.getString(StructureStrings.structureDirectionHolder));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		if(size.isEmpty()) {
			ExtendedLog.error("Loaded empty structure size! "+strName);
			return null;
		}
		if(blockMap.isEmpty()) {
			ExtendedLog.error("Loaded empty structure array! "+strName);
			return null;
		}
		if(signMap.isEmpty()) {
			ExtendedLog.warn("Loaded empty sign array! "+strName);
		}
		if(entityMap.isEmpty()) {
			ExtendedLog.warn("Loaded empty entity array! "+strName);
		}
		StructureFile file = new StructureFile(strName, blockMap, size, signMap, entityMap, dir);
		loadedStructures.put(strName, file);
		return file;
	}
}
