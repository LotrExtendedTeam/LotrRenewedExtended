package lotr.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ExtendedPacketHandler {
	  private static final String PROTOCOL_VERSION = "1";
	  
	  private static SimpleChannel CHANNEL;
	  
	  public static void register() {
	    CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("lotr", "extended"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	    int id = 0;
	    CHANNEL.registerMessage(id++, ExtendedSPacketSelectHorn.class, ExtendedSPacketSelectHorn::encode, ExtendedSPacketSelectHorn::decode, ExtendedSPacketSelectHorn::handle);
	  }

		public static void sendTo(Object msg, ServerPlayerEntity player) {
			CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
		}

		public static void sendToAll(Object msg) {
			CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
		}

		public static void sendToServer(Object msg) {
			CHANNEL.send(PacketDistributor.SERVER.noArg(), msg);
		}
	}
