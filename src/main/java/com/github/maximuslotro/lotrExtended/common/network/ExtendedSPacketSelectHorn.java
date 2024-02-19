package com.github.maximuslotro.lotrExtended.common.network;

import java.util.function.Supplier;

import lotr.common.init.ExtendedItems;
import lotr.common.item.ExtendedCommandHornItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
	    		CompoundNBT nbtTagCompound = itemstack.getTag();
	    		if (nbtTagCompound == null) {
	    	        nbtTagCompound = new CompoundNBT();
	    	        itemstack.setTag(nbtTagCompound);
	    	    }
	    		nbtTagCompound.putFloat(ExtendedCommandHornItem.type, packet.type);
	    	}
	    }
		context.get().setPacketHandled(true);
	}
}
