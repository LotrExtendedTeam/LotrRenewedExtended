package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainerNew;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCAnvilButtonPacket {
	public int buttonType;

	public ExtendedCAnvilButtonPacket(int pButtonType) {
		this.buttonType = pButtonType;
	}

	public static void encode(ExtendedCAnvilButtonPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.buttonType);
	}

	public static ExtendedCAnvilButtonPacket decode(PacketBuffer buf) {
		return new ExtendedCAnvilButtonPacket(buf.readInt());
	}

	public static void handle(ExtendedCAnvilButtonPacket pPacket, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
		if(player.containerMenu instanceof ExtendedRepairContainerNew && pPacket.buttonType==0) {
			((ExtendedRepairContainerNew)player.containerMenu).reforgeItem();
		}else if(player.containerMenu instanceof ExtendedRepairContainerNew && pPacket.buttonType==1) {
			((ExtendedRepairContainerNew)player.containerMenu).engraveItem();
		}
		context.get().setPacketHandled(true);
	}
}