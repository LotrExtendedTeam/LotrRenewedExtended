package lotr.common.network;

import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ExtendedCPacketUpdateExchangeCoinContainer {
	private int exchangedSide; // 0 is left, 1 is right
	private boolean exchanged;

	public ExtendedCPacketUpdateExchangeCoinContainer() {}

	public ExtendedCPacketUpdateExchangeCoinContainer(boolean exchanged, int exchangedSide) {
		this.exchanged = exchanged;
		this.exchangedSide = exchangedSide;
	}

	public static void encode(ExtendedCPacketUpdateExchangeCoinContainer packet, PacketBuffer buf) {
		buf.writeBoolean(packet.exchanged);
		buf.writeInt(packet.exchangedSide);
	}

	public static ExtendedCPacketUpdateExchangeCoinContainer decode(PacketBuffer buf) {
		ExtendedCPacketUpdateExchangeCoinContainer packet = new ExtendedCPacketUpdateExchangeCoinContainer();
		packet.exchanged = buf.readBoolean();
		packet.exchangedSide = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedCPacketUpdateExchangeCoinContainer packet, Supplier<Context> context) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
	    if (player.containerMenu instanceof ExtendedCoinExchangeContainer) {
			ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) player.containerMenu;
			container.sideExchanged = packet.exchangedSide;
			container.exchanged = packet.exchanged;
		}

		context.get().setPacketHandled(true);
	}
}
