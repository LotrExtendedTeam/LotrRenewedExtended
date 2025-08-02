package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedHoistTileEntity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCUpdateHoistPacket {
	private int horizontalOffset;
	private int verticalOffset;
	private int verticalHeight;
	private BlockPos configurePos;

	public ExtendedCUpdateHoistPacket(int horizontalOffset, int verticalOffset, int verticalHeight, BlockPos configurePos) {
		this.horizontalOffset = horizontalOffset;
		this.verticalOffset = verticalOffset;
		this.verticalHeight = verticalHeight;
		this.configurePos = configurePos;
	}

	public static void encode(ExtendedCUpdateHoistPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.horizontalOffset);
		buf.writeInt(packet.verticalOffset);
		buf.writeInt(packet.verticalHeight);
		buf.writeBlockPos(packet.configurePos);
	}

	public static ExtendedCUpdateHoistPacket decode(PacketBuffer buf) {
		return new ExtendedCUpdateHoistPacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readBlockPos());
	}

	public static void handle(ExtendedCUpdateHoistPacket packet, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
		TileEntity tileEntity = player.level.getBlockEntity(packet.configurePos);
	    if (tileEntity instanceof ExtendedHoistTileEntity && player.isCreative()) {
	    	ExtendedHoistTileEntity hoistTe = (ExtendedHoistTileEntity)tileEntity;
	    	hoistTe.setParams(packet.horizontalOffset, packet.verticalOffset, packet.verticalHeight);
	    	SUpdateTileEntityPacket supdatetileentitypacket = hoistTe.getUpdatePacket();
	         if (supdatetileentitypacket != null) {
	        	 player.connection.send(supdatetileentitypacket);
	         }
		}
		context.get().setPacketHandled(true);
	}
}
