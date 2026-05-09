package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedWorldPruneScreen;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedWorldRegenEngine;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSWorldPruneProgress {
	private ExtendedWorldRegenEngine.Stage stage;
	private boolean finished;
	private boolean dryRun;

	private int totalChunks;
	private int deletableChunks;
	private int bannerProtectedChunks;
	private int waypointProtectedChunks;
	private int spawnProtectedChunks;
	private int skippedErrorChunks;
	private int currentRegions;
	private int totalRegions;
	private int deletedRegions;
	private int protectedRegions;
	private int skippedLoadedRegions;

	public ExtendedSWorldPruneProgress(ExtendedWorldRegenEngine.Stage stage, boolean finished, boolean dryRun, int totalChunks, 
		int deletableChunks, int bannerProtectedChunks, int waypointProtectedChunks, int spawnProtectedChunks, int skippedErrorChunks, 
		int currentRegions, int totalRegions, int deletedRegions, int protectedRegions, int skippedLoadedRegions) {
		this.stage = stage;
		this.finished = finished;
		this.dryRun = dryRun;

		this.totalChunks = totalChunks;
		this.deletableChunks = deletableChunks;
		this.bannerProtectedChunks = bannerProtectedChunks;
		this.waypointProtectedChunks = waypointProtectedChunks;
		this.spawnProtectedChunks = spawnProtectedChunks;
		this.skippedErrorChunks = skippedErrorChunks;

		this.currentRegions = currentRegions;
		this.totalRegions = totalRegions;
		this.deletedRegions = deletedRegions;
		this.protectedRegions = protectedRegions;
		this.skippedLoadedRegions = skippedLoadedRegions;
	}

	public ExtendedSWorldPruneProgress(ExtendedWorldRegenEngine engine) {
		this.stage = engine.getStage();
		this.finished = engine.isFinished();
		this.dryRun = engine.isDryRun();

		this.totalChunks = engine.getTotalChunks();
		this.deletableChunks = engine.getDeletableChunks();
		this.bannerProtectedChunks = engine.getBannerProtected();
		this.waypointProtectedChunks = engine.getWaypointProtected();
		this.spawnProtectedChunks = engine.getSpawnProtected();
		this.skippedErrorChunks = engine.getErrorProtected();

		this.currentRegions = engine.getCurrentRegions();
		this.totalRegions = engine.getTotalRegions();
		this.deletedRegions = engine.getDeletedRegions();
		this.protectedRegions = engine.getProtectedRegions();
		this.skippedLoadedRegions = engine.getSkippedLoadedRegions();
	}

	public static void encode(ExtendedSWorldPruneProgress pkt, PacketBuffer buf) {
		buf.writeEnum(pkt.stage);
		buf.writeBoolean(pkt.finished);
		buf.writeBoolean(pkt.dryRun);

		buf.writeInt(pkt.totalChunks);
		buf.writeInt(pkt.deletableChunks);
		buf.writeInt(pkt.bannerProtectedChunks);
		buf.writeInt(pkt.waypointProtectedChunks);
		buf.writeInt(pkt.spawnProtectedChunks);
		buf.writeInt(pkt.skippedErrorChunks);

		buf.writeInt(pkt.currentRegions);
		buf.writeInt(pkt.totalRegions);
		buf.writeInt(pkt.deletedRegions);
		buf.writeInt(pkt.protectedRegions);
		buf.writeInt(pkt.skippedLoadedRegions);
	}

	// DECODE
	public static ExtendedSWorldPruneProgress decode(PacketBuffer buf) {
		return new ExtendedSWorldPruneProgress(buf.readEnum(ExtendedWorldRegenEngine.Stage.class), 
			buf.readBoolean(), buf.readBoolean(), 
			buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(),
			buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void handle(ExtendedSWorldPruneProgress packet, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			Minecraft mc = Minecraft.getInstance();
			if (mc.screen instanceof ExtendedWorldPruneScreen) {
	            ((ExtendedWorldPruneScreen) mc.screen).applyProgressPacket(packet);
	        }
		});
		context.get().setPacketHandled(true);
	}

	public ExtendedWorldRegenEngine.Stage getStage() { return stage; }
	public boolean isFinished() { return finished; }
	public boolean isDryRun() { return dryRun; }
	public int getTotalChunks() { return totalChunks; }
	public int getDeletableChunks() { return deletableChunks; }
	public int getBannerProtectedChunks() { return bannerProtectedChunks; }
	public int getWaypointProtectedChunks() { return waypointProtectedChunks; }
	public int getSpawnProtectedChunks() { return spawnProtectedChunks; }
	public int getErrorProtectedChunks() { return skippedErrorChunks; }

	public int getCurrentRegions() { return currentRegions; }
	public int getTotalRegions() { return totalRegions; }
	public int getDeletedRegions() { return deletedRegions; }
	public int getProtectedRegions() { return protectedRegions; }
	public int getSkippedLoadedRegions() { return skippedLoadedRegions; }
}