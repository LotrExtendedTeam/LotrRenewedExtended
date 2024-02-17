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
	private int type;

	public ExtendedSPacketSelectHorn() {}
	
	public ExtendedSPacketSelectHorn(int type) {
		this.type = type;
	}

	public static void encode(ExtendedSPacketSelectHorn packet, PacketBuffer buf) {
		buf.writeInt(packet.type);
	}

	public static ExtendedSPacketSelectHorn decode(PacketBuffer buf) {
		ExtendedSPacketSelectHorn packet = new ExtendedSPacketSelectHorn();
		packet.type = buf.readInt();
		return packet;
	}

	public static void handle(ExtendedSPacketSelectHorn packet, Supplier<Context> context) {
		ServerPlayerEntity player = ((Context) context.get()).getSender();
		ItemStack itemstack = player.getMainHandItem();
	    if (itemstack != null && itemstack.getItem() == ExtendedItems.COMMANDHORN.get()) {
	    	if(itemstack.getTag()==null||!itemstack.getTag().contains(ExtendedCommandHornItem.type)||itemstack.getTag().getInt(ExtendedCommandHornItem.type)==0) {
	    		CompoundNBT nbtTagCompound = itemstack.getTag();
	    		if (nbtTagCompound == null) {
	    	        nbtTagCompound = new CompoundNBT();
	    	        itemstack.setTag(nbtTagCompound);
	    	    }
	    		nbtTagCompound.putInt(ExtendedCommandHornItem.type, packet.type);
	    	}
	    }
		context.get().setPacketHandled(true);
	}
}
