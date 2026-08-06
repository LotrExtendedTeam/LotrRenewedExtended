package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerDebugToggles;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedDebugPackets;

import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(DebugPacketSender.class)
public class MixinDebugPacketSender {

	@Shadow
	private static void sendPacketToAllPlayers(ServerWorld pLevel, PacketBuffer pBuffer, ResourceLocation pType) {}

	@Overwrite
	public static void sendPathFindingPacket(World pLevel, MobEntity pMob, @Nullable Path pPath, float pMaxDistanceToWaypoint) {
		if(ExtendedServerDebugToggles.renderNavPaths && pPath!=null) {
			PacketBuffer packetbuffer = ExtendedDebugPackets.writePathFindingPacket(pLevel, pMob, pPath, pMaxDistanceToWaypoint);
			if(packetbuffer!=null) {
				sendPacketToAllPlayers((ServerWorld) pLevel, packetbuffer, SCustomPayloadPlayPacket.DEBUG_PATHFINDING_PACKET);
			}
		}
	}
}
