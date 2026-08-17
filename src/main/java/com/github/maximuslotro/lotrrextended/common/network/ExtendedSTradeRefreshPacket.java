package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedClientsideSeparatedScreenHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedSTradeRefreshPacket {

	private int traderId;

    public ExtendedSTradeRefreshPacket(int traderId) {
        this.traderId = traderId;
    }

    public static void encode(ExtendedSTradeRefreshPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.traderId);
    }

    public static ExtendedSTradeRefreshPacket decode(PacketBuffer buf) {
        return new ExtendedSTradeRefreshPacket(buf.readInt());
    }

	public static void handle(ExtendedSTradeRefreshPacket packet, Supplier<Context> context) {
        context.get().setPacketHandled(true);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ExtendedClientsideSeparatedScreenHandler.closeTraderGui(packet.traderId));

	}
}

