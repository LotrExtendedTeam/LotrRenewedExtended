package lotr.common.network;

import lotr.common.inv.ExtendedCoinExchangeContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class ExtendedSPacketExchangeCoins {
	private int side; // 0 is left, 1 is right

	public ExtendedSPacketExchangeCoins() {}

	public ExtendedSPacketExchangeCoins(int side) {
		this.side = side;
	}

	public static void encode(ExtendedSPacketExchangeCoins packet, PacketBuffer buf) {
		buf.writeInt(packet.side);
	}

	public static ExtendedSPacketExchangeCoins decode(PacketBuffer buf) {
		ExtendedSPacketExchangeCoins packet = new ExtendedSPacketExchangeCoins();
		packet.side = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedSPacketExchangeCoins packet, Supplier<Context> context) {
		ServerPlayerEntity player = ((Context) context.get()).getSender();
	    if (player.containerMenu instanceof ExtendedCoinExchangeContainer) {
			ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) player.containerMenu;
			container.handleExchange(packet.side);
		}
		context.get().setPacketHandled(true);
	}
}
