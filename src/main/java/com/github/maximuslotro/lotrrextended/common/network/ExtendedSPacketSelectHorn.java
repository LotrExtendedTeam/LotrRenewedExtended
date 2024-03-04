package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import lotr.common.init.ExtendedItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedSPacketSelectHorn {
	private float type;

	public ExtendedSPacketSelectHorn() {}
	
	public ExtendedSPacketSelectHorn(float type) {
		this.type = type;
	}

	public static void encode(ExtendedSPacketSelectHorn packet, PacketBuffer buf) {
		buf.writeFloat(packet.type);
	}

	public static ExtendedSPacketSelectHorn decode(PacketBuffer buf) {
		ExtendedSPacketSelectHorn packet = new ExtendedSPacketSelectHorn();
		packet.type = buf.readFloat();
		return packet;
	}

	public static void handle(ExtendedSPacketSelectHorn packet, Supplier<Context> context) {
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
