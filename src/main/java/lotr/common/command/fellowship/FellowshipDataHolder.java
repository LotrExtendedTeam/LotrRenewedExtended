package lotr.common.command.fellowship;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lotr.common.LOTRLog;
import lotr.common.data.SaveUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class FellowshipDataHolder {
	private static Map<UUID, ExtendedFellowship> fellowshipMap = new HashMap<UUID, ExtendedFellowship>();
	public static boolean needsLoad = true;

	private static File getSaveDir() {
		File dir = new File(SaveUtil.getOrCreateLOTRDir(ServerLifecycleHooks.getCurrentServer().overworld()), "fellowships");
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}

	private static File getFellowshipDatafile(UUID ID) {
		return new File(getSaveDir(), ID.toString() + ".dat");
	}

	public static void addFellowship(ExtendedFellowship fs) {
		if (!fellowshipMap.containsKey(fs.getFellowshipId()))
			fellowshipMap.put(fs.getFellowshipId(), fs);
	}

	public static ExtendedFellowship getFellowship(UUID fsID) {
		ExtendedFellowship fs = fellowshipMap.get(fsID);
		if (fs == null) {
			fs = loadFellowship(fsID);
			if (fs != null)
				fellowshipMap.put(fsID, fs);
		}
		return fs;
	}

	private static ExtendedFellowship loadFellowship(UUID fsID) {
		File fsDat = getFellowshipDatafile(fsID);
		try {
			CompoundNBT nbt = SaveUtil.loadNBTFromFile(fsDat);
			if (nbt.isEmpty())
				return null;
			ExtendedFellowship fs = new ExtendedFellowship(fsID);
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
			for (ExtendedFellowship fs : fellowshipMap.values()) {
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
			fellowshipMap.clear();
			needsLoad = false;
			saveAll();
		} catch (Exception e) {
			LOTRLog.error("Error loading fellowship data", new Object[0]);
			e.printStackTrace();
		}
	}

	public static void saveFellowship(ExtendedFellowship fs) {
		try {
			CompoundNBT nbt = new CompoundNBT();
			fs.save(nbt);
			SaveUtil.saveNBTToFile(getFellowshipDatafile(fs.getFellowshipId()), nbt);
		} catch (Exception e) {
			LOTRLog.error("Error saving fellowship data for %s", new Object[] { fs.getFellowshipId() });
			e.printStackTrace();
		}
	}
	private static void saveAndClearFellowship(ExtendedFellowship fs) {
	    if (fellowshipMap.containsValue(fs)) {
	      saveFellowship(fs);
	      fellowshipMap.remove(fs.getFellowshipId());
	    } else {
	    	LOTRLog.error("Attempted to clear LOTR fellowship data for %s; no data found", new Object[] { fs.getFellowshipId() });
	    } 
	  }
}
