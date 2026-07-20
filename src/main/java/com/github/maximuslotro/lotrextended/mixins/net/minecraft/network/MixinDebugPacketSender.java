package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network;

import java.util.Set;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.Sets;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(DebugPacketSender.class)
public class MixinDebugPacketSender {

	@Shadow
	private static void sendPacketToAllPlayers(ServerWorld pLevel, PacketBuffer pBuffer, ResourceLocation pType) {}

	@Overwrite
	public static void sendPathFindingPacket(World pLevel, MobEntity pMob, @Nullable Path pPath, float pMaxDistanceToWaypoint) {
		PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
		packetbuffer.writeInt(pMob.getId());
		packetbuffer.writeFloat(pMaxDistanceToWaypoint);
		writeToStream(packetbuffer, pPath);
		sendPacketToAllPlayers((ServerWorld) pLevel, packetbuffer, SCustomPayloadPlayPacket.DEBUG_PATHFINDING_PACKET);
	}

	private static void writeToStream(PacketBuffer pBuf, Path path) {
		pBuf.writeBoolean(path.canReach());
		pBuf.writeInt(path.getNextNodeIndex());

		Set<FlaggedPathPoint> set = Sets.newHashSet();
		// FlaggedPathPoint count 
		pBuf.writeInt(set.size());
		set.forEach(flagged -> writeFlaggedToStream(pBuf, flagged));

		pBuf.writeInt(path.getTarget().getX());
		pBuf.writeInt(path.getTarget().getY());
		pBuf.writeInt(path.getTarget().getZ());

		pBuf.writeInt(path.getNodeCount());
		for(int i = 0; i < path.getNodeCount(); i++) {
			writeToStream(pBuf, path.getNode(i));
		}

		PathPoint[] apathpoint = new PathPoint[0];
		pBuf.writeInt(0);
		for(int i = 0; i < 0; i++) {
			writeToStream(pBuf, apathpoint[i]);
		}

		PathPoint[] apathpoint1 = new PathPoint[0];
		pBuf.writeInt(0);
		for(int i = 0; i < 0; i++) {
			writeToStream(pBuf, apathpoint1[i]);
		}

	}

	private static void writeFlaggedToStream(PacketBuffer pBuf, FlaggedPathPoint flaggedPathPoint) {
		pBuf.writeInt(flaggedPathPoint.x);
		pBuf.writeInt(flaggedPathPoint.y);
		pBuf.writeInt(flaggedPathPoint.z);
		pBuf.writeFloat(flaggedPathPoint.walkedDistance);
		pBuf.writeFloat(flaggedPathPoint.costMalus);
		pBuf.writeBoolean(flaggedPathPoint.closed);
		pBuf.writeInt(flaggedPathPoint.type.ordinal());
		pBuf.writeFloat(flaggedPathPoint.f);
	}

	private static void writeToStream(PacketBuffer pBuf, PathPoint pathPoint) {
		pBuf.writeInt(pathPoint.x);
		pBuf.writeInt(pathPoint.y);
		pBuf.writeInt(pathPoint.z);
		pBuf.writeFloat(pathPoint.walkedDistance);
		pBuf.writeFloat(pathPoint.costMalus);
		pBuf.writeBoolean(pathPoint.closed);
		pBuf.writeInt(pathPoint.type.ordinal());
		pBuf.writeFloat(pathPoint.f);
	}
}
