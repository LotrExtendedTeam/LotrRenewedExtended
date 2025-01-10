package lotr.common.fellowship;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.data.SaveUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFellowshipHolderServer {
	private static Map<UUID, ExtendedFellowship> fellowshipMap = new HashMap<>();
	public static boolean needsLoad = true;

	private static File getSaveDir(ServerWorld world) {
		File dir = new File(SaveUtil.getOrCreateLOTRDir(world), "fellowships");
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}

	private static File getFellowshipDatafile(ServerWorld world, UUID ID) {
		return new File(getSaveDir(world), ID.toString() + ".dat");
	}

	public static void addFellowship(ExtendedFellowship fs) {
		if (!fellowshipMap.containsKey(fs.getFellowshipId()))
			fellowshipMap.put(fs.getFellowshipId(), fs);
	}

	public static ExtendedFellowship getFellowship(ServerWorld world, UUID fsID) {
		ExtendedFellowship fellowship = fellowshipMap.get(fsID);
		if (fellowship == null) {
			fellowship = loadFellowship(world, fsID);
			if (fellowship != null)
				fellowshipMap.put(fsID, fellowship);
		}
		if (fellowship != null && fellowship.isDisbanded()) {
			return null;
		}
		return fellowship;
	}

	private static ExtendedFellowship loadFellowship(ServerWorld world, UUID fsID) {
		File fsDat = getFellowshipDatafile(world, fsID);
		try {
			CompoundNBT nbt = SaveUtil.loadNBTFromFile(fsDat);
			if (nbt.isEmpty())
				return null;
			ExtendedFellowship fellowship = new ExtendedFellowship(fsID);
			fellowship.load(nbt);
			return fellowship;
		} catch (Exception e) {
			ExtendedLog.error("Error loading LOTR fellowship data for %s", new Object[] { fsDat.getName() });
			e.printStackTrace();
			return null;
		}
	}

	public static void saveAll(ServerWorld world) {
		try {
			for (ExtendedFellowship fs : fellowshipMap.values()) {
				if (fs.needsSave())
					saveFellowship(world, fs);
			}
		} catch (Exception e) {
			ExtendedLog.error("Error saving fellowship data", new Object[0]);
			e.printStackTrace();
		}
	}

	public static void loadAll(ServerWorld world) {
		try {
			fellowshipMap.clear();
			needsLoad = false;
			saveAll(world);
		} catch (Exception e) {
			ExtendedLog.error("Error loading fellowship data", new Object[0]);
			e.printStackTrace();
		}
	}

	public static void saveFellowship(ServerWorld world, ExtendedFellowship fs) {
		try {
			CompoundNBT nbt = new CompoundNBT();
			fs.save(nbt);
			SaveUtil.saveNBTToFile(getFellowshipDatafile(world, fs.getFellowshipId()), nbt);
		} catch (Exception e) {
			ExtendedLog.error("Error saving fellowship data for %s", new Object[] { fs.getFellowshipId() });
			e.printStackTrace();
		}
	}
}