package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

import net.minecraft.network.PacketBuffer;
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
		ExtendedClientsideSeparatedScreenHandler.closeTraderGui(packet.traderId);
        context.get().setPacketHandled(true);
	}
}

