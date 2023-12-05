package lotr.common.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import lotr.common.LOTRLog;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.resources.IResource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

public class StructureFileIO {
	private static final FolderName STRUCTURE_GEN_FOLDER = new FolderName("structure_gen");
	private static File MOD_FILE;
	
	public static void init() {
		List<ModInfo> mods = ModList.get().getMods();
        for (ModInfo mod : mods)
        {
            if (mod.getModId().equals("lotrextended"))
            {
            	MOD_FILE  = mod.getOwningFile().getFile().getFilePath().toFile();
                break;
            }
        }
	}
	public static CompoundNBT load(String fileName, MinecraftServer server) {
		try {
			if (getStructureScanDataFile(fileName, server).exists()) {
				FileInputStream fileStream = new FileInputStream(getStructureScanDataFile(fileName, server));
				CompoundNBT compound = CompressedStreamTools.readCompressed(fileStream);
				fileStream.close();
				return compound;
			} else {
				ResourceLocation location = new ResourceLocation("lotr:lotr_structures/"+fileName+".dat");
				try (IResource iresource = server.getDataPackRegistries().getResourceManager().getResource(location)) {
					return CompressedStreamTools.readCompressed(iresource.getInputStream());
				} catch (FileNotFoundException filenotfoundexception) {
					LOTRLog.error("Failed to find structure file: "+fileName);
					filenotfoundexception.printStackTrace();
					return null;
				} catch (Throwable throwable) {
					LOTRLog.error("Failed to load structure file: "+fileName, throwable.toString());
					return null;
				}
			}
		} catch (Exception var2) {
			LOTRLog.error("Error loading structure file: "+fileName);
			var2.printStackTrace();
		}
		return null;
	}

	public static void save(String fileName, MinecraftServer server, CompoundNBT compound) {
		try {
			File structureGen = getStructureScanDataFile(fileName, server);
			if (!structureGen.exists()) {
				CompressedStreamTools.writeCompressed(new CompoundNBT(), new FileOutputStream(structureGen));
			}
			CompressedStreamTools.writeCompressed(compound, new FileOutputStream(structureGen));
		} catch (Exception var3) {
			LOTRLog.error("Error saving structure: "+fileName);
			var3.printStackTrace();
		}
	}

	private static File getStructureScanDataFile(String fileName, MinecraftServer server) {
		File dir = server.getWorldPath(STRUCTURE_GEN_FOLDER).toFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return new File(dir, fileName + ".dat");
	}
}
