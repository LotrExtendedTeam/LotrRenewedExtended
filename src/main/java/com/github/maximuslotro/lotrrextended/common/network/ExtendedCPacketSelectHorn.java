package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import lotr.common.init.ExtendedItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCPacketSelectHorn {
	private float type;

	public ExtendedCPacketSelectHorn() {}

	public ExtendedCPacketSelectHorn(float type) {
		this.type = type;
	}

	public static void encode(ExtendedCPacketSelectHorn packet, PacketBuffer buf) {
		buf.writeFloat(packet.type);
	}

	public static ExtendedCPacketSelectHorn decode(PacketBuffer buf) {
		ExtendedCPacketSelectHorn packet = new ExtendedCPacketSelectHorn();
		packet.type = buf.readFloat();
		return packet;
	}

	public static void handle(ExtendedCPacketSelectHorn packet, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
		ItemStack itemstack = player.getMainHandItem();
	    if (itemstack != null && itemstack.getItem() == ExtendedItems.COMMANDHORN.get()) {
	    	if(!ExtendedCommandHornItem.isNotEmpty(itemstack)) {
	    		ExtendedCommandHornItem.setType(itemstack, packet.type);
	    	}
	    }
		context.get().setPacketHandled(true);
	}
}
