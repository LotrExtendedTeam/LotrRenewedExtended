package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedWorldRegenEngineWatcher;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCWorldPruneCommands {
	private boolean dryRun;
	private boolean start;

    public ExtendedCWorldPruneCommands(boolean pDryRun, boolean pStart) {
    	this.dryRun = pDryRun;
    	this.start = pStart;
    }
   
    public static void encode(ExtendedCWorldPruneCommands packet, PacketBuffer buf) {
    	buf.writeBoolean(packet.dryRun);
    	buf.writeBoolean(packet.start);
    }

    public static ExtendedCWorldPruneCommands decode(PacketBuffer buf) {
        return new ExtendedCWorldPruneCommands(buf.readBoolean(), buf.readBoolean());
    }
   
    public static void handle(ExtendedCWorldPruneCommands packet, Supplier<NetworkEvent.Context> context) {
    	context.get().setPacketHandled(true);
		ServerPlayerEntity player = context.get().getSender();
		if(player.server.getProfilePermissions(player.getGameProfile())<4) {
			ExtendedLog.warn("{} tried pruning world without permissions!", player.getName().getString());
			return;
		}
		if(packet.start) {
			ExtendedWorldRegenEngineWatcher.startWorldPrune(player.server, player, false, packet.dryRun);
		}else {
			ExtendedWorldRegenEngineWatcher.stopWorldPrune();
		}
    }

}
