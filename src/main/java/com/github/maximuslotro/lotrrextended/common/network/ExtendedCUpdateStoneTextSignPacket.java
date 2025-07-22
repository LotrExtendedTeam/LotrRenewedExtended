package com.github.maximuslotro.lotrrextended.common.network;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedCUpdateStoneTextSignPacket {

	private BlockPos pos;
	private String[] lines;

    public ExtendedCUpdateStoneTextSignPacket(BlockPos pPos, String[] lines) {
    	this.pos = pPos;
        this.lines = lines;
    }
   
    public static void encode(ExtendedCUpdateStoneTextSignPacket packet, PacketBuffer buf) {
    	buf.writeBlockPos(packet.pos);

        for(int i = 0; i < 8; ++i) {
        	buf.writeUtf(packet.lines[i]);
        }
    }

    public static ExtendedCUpdateStoneTextSignPacket decode(PacketBuffer buf) {
    	BlockPos pos = buf.readBlockPos();
    	String[] lines = new String[8];

        for(int i = 0; i < 8; ++i) {
           lines[i] = buf.readUtf(384);
        }
        return new ExtendedCUpdateStoneTextSignPacket(pos, lines);
    }


    public BlockPos getPos() {
       return this.pos;
    }

    public String[] getLines() {
       return this.lines;
    }
   
    public static void handle(ExtendedCUpdateStoneTextSignPacket packet, Supplier<NetworkEvent.Context> context) {
    	List<String> list = Stream.of(packet.getLines()).map(TextFormatting::stripFormatting).collect(Collectors.toList());
    	context.get().getSender().resetLastActionTime();
        ServerWorld serverworld = context.get().getSender().getLevel();
        BlockPos blockpos = packet.getPos();
        if (serverworld.hasChunkAt(blockpos)) {
           BlockState blockstate = serverworld.getBlockState(blockpos);
           TileEntity tileentity = serverworld.getBlockEntity(blockpos);
           if (!(tileentity instanceof ExtendedStoneTextSignTileEntity)) {
              return;
           }

           ExtendedStoneTextSignTileEntity signtileentity = (ExtendedStoneTextSignTileEntity)tileentity;
           if (signtileentity.getPlayerWhoMayEdit() != context.get().getSender()) {
              ExtendedLog.warn("Player {} just tried to change non-editable sign", context.get().getSender().getName().getString());
              return;
           }

           for(int i = 0; i < list.size(); ++i) {
              signtileentity.setMessage(i, new StringTextComponent(list.get(i)));
           }
           signtileentity.setAllowedPlayerEditor(null);
           signtileentity.setChanged();
           serverworld.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
        }

        context.get().setPacketHandled(true);
    }

}
