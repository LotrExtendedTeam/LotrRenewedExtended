package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedMapScreen;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSConquestResetPacket {
	private int conquestScale;

	public ExtendedSConquestResetPacket(int pConquestScale) {
		this.conquestScale = pConquestScale;
	}

	public static void encode(ExtendedSConquestResetPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.conquestScale);
	}

	public static ExtendedSConquestResetPacket decode(PacketBuffer buf) {
		int conquestScale = buf.readInt();
		return new ExtendedSConquestResetPacket(conquestScale);
	}

	public static void handle(ExtendedSConquestResetPacket packet, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		context.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->  {
				ExtendedLevelData.clientInstance().getConquestData().setGridSquareScale(packet.conquestScale);
				ExtendedLevelData.clientInstance().getConquestData().clearConquestMap();
				Minecraft mc = Minecraft.getInstance();
				if(mc!=null && mc.screen instanceof ExtendedMapScreen) {
					((ExtendedMapScreen)mc.screen).refreshConquestData();
				}
			});
		});
	}
}