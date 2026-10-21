package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCSelectHornPacket {
	private ExtendedUnitHornTypes type;

	public ExtendedCSelectHornPacket(ExtendedUnitHornTypes type) {
		this.type = type;
	}

	public static void encode(ExtendedCSelectHornPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.type.getUnchangingIndex());
	}

	public static ExtendedCSelectHornPacket decode(PacketBuffer buf) {
		return new ExtendedCSelectHornPacket(ExtendedUnitHornTypes.getByUnchangingIndex(buf.readInt()));
	}

	public static void handle(ExtendedCSelectHornPacket packet, Supplier<Context> context) {
		context.get().enqueueWork(()->{
			ServerPlayerEntity player = context.get().getSender();
			ItemStack itemstack = player.getMainHandItem();
		    if (itemstack != null && itemstack.getItem() == ExtendedItems.COMMANDHORN.get()) {
		    	ExtendedCommandHornItem.setType(itemstack, packet.type);
		    }
		});
		context.get().setPacketHandled(true);
	}
}
