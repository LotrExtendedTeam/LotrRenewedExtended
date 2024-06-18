package lotr.common.data;

import lotr.common.LOTRLog;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

import java.io.File;

/**
 * @author Richardb122
 *
 * This class is only designed to save and handle the data on the server side for now.
 * I plan to eventually synchronize it with the client but for now it will store data
 * on the server side and be used to keep track of when Wandering Traders last spawned
 */
public class ExtendedLevelData {

    private static final ExtendedLevelData SERVER_INSTANCE;

    private boolean needsSave = false;
    private boolean needsLoad = true;

    private final LogicalSide side;

    private int ticksSinceSpawnedWanderingTraders = 0;

    public ExtendedLevelData(LogicalSide side) {
        this.side = side;
    }

    public static ExtendedLevelData serverInstance() {
        return SERVER_INSTANCE;
    }

    public boolean needsLoad() {
        return needsLoad;
    }

    public void save(ServerWorld world) {
        try {
            if (this.needsSave) {
                File LOTR_dat = getLOTRDat(world);
                if (!LOTR_dat.exists()) {
                    SaveUtil.saveNBTToFile(LOTR_dat, new CompoundNBT());
                }

                CompoundNBT levelData = new CompoundNBT();
                levelData.putInt("ticksSinceSpawnedWanderingTraders", this.ticksSinceSpawnedWanderingTraders);
                SaveUtil.saveNBTToFile(LOTR_dat, levelData);
                this.needsSave = false;
            }
        } catch (Exception exception) {
            LOTRLog.error("Error saving mod data");
            exception.printStackTrace();
        }

    }

    public void load(ServerWorld world) {
        try {
            CompoundNBT levelData = SaveUtil.loadNBTFromFile(getLOTRDat(world));
            this.ticksSinceSpawnedWanderingTraders = levelData.getInt("ticksSinceSpawnedWanderingTraders");
            this.needsLoad = false;
            this.needsSave = true;
            this.save(world);
        } catch (Exception exception) {
            LOTRLog.error("Error loading mod data");
            exception.printStackTrace();
        }
    }

    private static File getLOTRDat(ServerWorld world) {
        String filename = "extended".toUpperCase();
        return new File(SaveUtil.getOrCreateLOTRDir(world), filename + ".dat");
    }

    static {
        SERVER_INSTANCE = new ExtendedLevelData(LogicalSide.SERVER);
    }
}
