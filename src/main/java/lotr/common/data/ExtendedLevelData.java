package lotr.common.data;

import lotr.common.LOTRLog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
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

    private int timeUntilTrader = 0;

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
                File lotrDataFile = getLOTRDataFile(world);
                if (!lotrDataFile.exists()) {
                    SaveUtil.saveNBTToFile(lotrDataFile, new CompoundNBT());
                }

                CompoundNBT levelData = new CompoundNBT();
                levelData.putInt("timeUntilTrader", this.timeUntilTrader);
                SaveUtil.saveNBTToFile(lotrDataFile, levelData);
                this.needsSave = false;
            }
        } catch (Exception exception) {
            LOTRLog.error("Error saving mod data");
            exception.printStackTrace();
        }

    }

    public void load(ServerWorld world) {
        try {
            CompoundNBT levelData = SaveUtil.loadNBTFromFile(getLOTRDataFile(world));
            this.timeUntilTrader = levelData.getInt("timeUntilTrader");
            this.needsLoad = false;
            this.needsSave = true;
            this.save(world);
        } catch (Exception exception) {
            LOTRLog.error("Error loading mod data");
            exception.printStackTrace();
        }
    }

    public int getTimeUntilTrader() {
        return timeUntilTrader;
    }

    public void performTravellingTraderSpawns(World world) {
        if (timeUntilTrader > 0) {
            timeUntilTrader--;
        } else {
            for (PlayerEntity player : world.players()) {
                player.sendMessage(new StringTextComponent("Triggering wandering trader spawns"), player.getUUID());
            }
            timeUntilTrader = 4000;
        }
        needsSave = true;
    }

    private static File getLOTRDataFile(ServerWorld world) {
        String filename = "extended".toUpperCase();
        return new File(SaveUtil.getOrCreateLOTRDir(world), filename + ".dat");
    }

    static {
        SERVER_INSTANCE = new ExtendedLevelData(LogicalSide.SERVER);
    }
}
