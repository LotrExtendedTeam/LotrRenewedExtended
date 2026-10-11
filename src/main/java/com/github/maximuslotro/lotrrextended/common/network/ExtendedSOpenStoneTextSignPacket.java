package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedClientsideSeparatedScreenHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSOpenStoneTextSignPacket {

	private BlockPos pos;

    public ExtendedSOpenStoneTextSignPacket(BlockPos pPos) {
    	this.pos = pPos;
    }
   
    public static void encode(ExtendedSOpenStoneTextSignPacket packet, PacketBuffer buf) {
    	buf.writeBlockPos(packet.pos);

    }

    public static ExtendedSOpenStoneTextSignPacket decode(PacketBuffer buf) {
    	BlockPos pos = buf.readBlockPos();
        return new ExtendedSOpenStoneTextSignPacket(pos);
    }


    public BlockPos getPos() {
       return this.pos;
    }
   
    public static void handle(ExtendedSOpenStoneTextSignPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ExtendedClientsideSeparatedScreenHandler.openCarvingGUI(packet));

    }

}
