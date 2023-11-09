package lotr.common.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import lotr.common.LOTRLog;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

public class StructureSaveHandler {
	private static final FolderName STRUCTURE_GEN_FOLDER = new FolderName("structure_gen");
	
	public static CompoundNBT load(String fileName, ServerWorld world) {
		try {
			if (getStructureDat(fileName, world).exists()) {
				FileInputStream fileStream = new FileInputStream(getStructureDat(fileName, world));
				CompoundNBT compound = CompressedStreamTools.readCompressed(fileStream);
				fileStream.close();
				save(fileName, world, compound);
				return compound;
			} else {
				return new CompoundNBT();
			}
		} catch (Exception var2) {
			LOTRLog.error("Error loading structure files");
			var2.printStackTrace();
		}
		return null;
	}

	public static void save(String fileName, ServerWorld world, CompoundNBT compound) {
		try {
			File structureGen = getStructureDat(fileName, world);
			if (!structureGen.exists()) {
				CompressedStreamTools.writeCompressed(new CompoundNBT(), new FileOutputStream(structureGen));
			}
			CompressedStreamTools.writeCompressed(compound, new FileOutputStream(structureGen));
		} catch (Exception var3) {
			LOTRLog.error("Error saving structure");
			var3.printStackTrace();
		}
	}

	private static File getStructureDat(String fileName, ServerWorld world) {
		MinecraftServer server = world.getServer();
		File dir = server.getWorldPath(STRUCTURE_GEN_FOLDER).toFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return new File(dir, fileName+".dat");
	}
	
	public static CompoundNBT writeBlockPos(int X, int Y, int Z) {
	      CompoundNBT compoundnbt = new CompoundNBT();
	      compoundnbt.putInt("X", X);
	      compoundnbt.putInt("Y", Y);
	      compoundnbt.putInt("Z", Z);
	      return compoundnbt;
	   }
}
