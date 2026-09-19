package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCSelectHornSquadPacket {
	private String squad;

	public ExtendedCSelectHornSquadPacket(@Nullable String pSquad) {
		this.squad = pSquad;
	}

	public static void encode(ExtendedCSelectHornSquadPacket packet, PacketBuffer buf) {
		 if(packet.squad!=null && !packet.squad.isEmpty()) {
	        	buf.writeBoolean(true);
	        	buf.writeUtf(packet.squad);
	        }else {
	        	buf.writeBoolean(false);
	        }
	}

	public static ExtendedCSelectHornSquadPacket decode(PacketBuffer buf) {
		return new ExtendedCSelectHornSquadPacket(buf.readBoolean() ? buf.readUtf() : null);
	}

	public static void handle(ExtendedCSelectHornSquadPacket packet, Supplier<Context> context) {
		context.get().enqueueWork(()->{
			ServerPlayerEntity player = context.get().getSender();
			ItemStack itemstack = player.getMainHandItem();
		    if (itemstack != null && itemstack.getItem() == ExtendedItems.COMMANDHORN.get()) {
		    	ExtendedCommandHornItem.setSquad(itemstack, packet.squad);
		    }
		});
		context.get().setPacketHandled(true);
	}
}
