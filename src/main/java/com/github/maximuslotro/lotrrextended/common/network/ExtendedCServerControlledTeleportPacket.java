package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCServerControlledTeleportPacket {
	private int x;
	private int z;

	public ExtendedCServerControlledTeleportPacket(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public static void encode(ExtendedCServerControlledTeleportPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.x);
		buf.writeInt(packet.z);
	}

	public static ExtendedCServerControlledTeleportPacket decode(PacketBuffer buf) {
		return new ExtendedCServerControlledTeleportPacket(buf.readInt(), buf.readInt());
	}

	public static void handle(ExtendedCServerControlledTeleportPacket packet, Supplier<Context> context) {
		context.get().enqueueWork(()->{
			ServerPlayerEntity player = context.get().getSender();
			if(player.hasPermissions(2)) {
				int x = packet.x;
				int z = packet.z;
				int y = player.level.getChunkAt(new BlockPos(x, 0, z)).getHeight(Type.MOTION_BLOCKING_NO_LEAVES, x, z)+1;
				if(player.isCreative() && player.blockPosition().getY() > y) {
					y = player.blockPosition().getY();
				}
				player.getServer().getCommands().performCommand(player.createCommandSourceStack(), String.format(ExtendedServerConfig.alternateMapTeleportCommand.get(), packet.x, y, packet.z));
			}
		});
		context.get().setPacketHandled(true);
	}
}
