package lotr.common.command.fellowship;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lotr.common.LOTRLog;
import lotr.common.data.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class FellowshipDataHolder {
	private static Map<UUID, Fellowship> fellowshipMap = new HashMap<UUID, Fellowship>();
	public static boolean needsLoad = true;

	private static File getSaveDir() {
		MinecraftServer server;
		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			server = Minecraft.getInstance().getSingleplayerServer();
		} else {
			server = ServerLifecycleHooks.getCurrentServer();
		}
		File dir = new File(SaveUtil.getOrCreateLOTRDir(server.overworld()), "fellowships");
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}

	private static File getFellowshipDatafile(UUID ID) {
		return new File(getSaveDir(), ID.toString() + ".dat");
	}

	public static void addFellowship(Fellowship fs) {
		if (!fellowshipMap.containsKey(fs.getID()))
			fellowshipMap.put(fs.getID(), fs);
	}

	public static Fellowship getFellowship(UUID fsID) {
		Fellowship fs = fellowshipMap.get(fsID);
		if (fs == null) {
			fs = loadFellowship(fsID);
			if (fs != null)
				fellowshipMap.put(fsID, fs);
		}
		return fs;
	}

	private static Fellowship loadFellowship(UUID fsID) {
		File fsDat = getFellowshipDatafile(fsID);
		try {
			CompoundNBT nbt = SaveUtil.loadNBTFromFile(fsDat);
			if (nbt.isEmpty())
				return null;
			Fellowship fs = new Fellowship(fsID);
			fs.load(nbt);
			return fs;
		} catch (Exception e) {
			LOTRLog.error("Error loading LOTR fellowship data for %s", new Object[] { fsDat.getName() });
			e.printStackTrace();
			return null;
		}
	}

	public static void saveAll() {
		try {
			for (Fellowship fs : fellowshipMap.values()) {
				if (fs.needsSave())
					saveFellowship(fs);
			}
		} catch (Exception e) {
			LOTRLog.error("Error saving fellowship data", new Object[0]);
			e.printStackTrace();
		}
	}

	public static void loadAll() {
		try {
			needsLoad = false;
			saveAll();
		} catch (Exception e) {
			LOTRLog.error("Error loading fellowship data", new Object[0]);
			e.printStackTrace();
		}
	}

	public static void saveFellowship(Fellowship fs) {
		try {
			CompoundNBT nbt = new CompoundNBT();
			fs.save(nbt);
			SaveUtil.saveNBTToFile(getFellowshipDatafile(fs.getID()), nbt);
		} catch (Exception e) {
			LOTRLog.error("Error saving fellowship data for %s", new Object[] { fs.getID() });
			e.printStackTrace();
		}
	}
}
