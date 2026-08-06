package com.github.maximuslotro.lotrrextended.common.network;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrextended.mixins.net.minecraft.pathfinding.MixinPathAccessor;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedPath;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.world.World;

public class ExtendedDebugPackets {
	
	public static PacketBuffer writePathFindingPacket(World pLevel, MobEntity pMob, @Nullable Path pPath, float pMaxDistanceToWaypoint) {
		ExtendedPath pathExt = (ExtendedPath)pPath;
		if(pathExt.getTargetNodesExtended()==null || pathExt.getTargetNodesExtended().isEmpty()) {
			return null;
		}
		PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
		packetbuffer.writeInt(pMob.getId());
		packetbuffer.writeFloat(pMaxDistanceToWaypoint);
		MixinPathAccessor pathAcc = (MixinPathAccessor)pPath;
		packetbuffer.writeBoolean(pPath.canReach());
		packetbuffer.writeInt(pPath.getNextNodeIndex());

		packetbuffer.writeInt(pathExt.getTargetNodesExtended().size());
		pathExt.getTargetNodesExtended().forEach(flagged -> writeFlaggedPathPointToStream(packetbuffer, flagged));

		packetbuffer.writeInt(pPath.getTarget().getX());
		packetbuffer.writeInt(pPath.getTarget().getY());
		packetbuffer.writeInt(pPath.getTarget().getZ());

		packetbuffer.writeInt(pathAcc.getNodes().size());
		for(PathPoint node : pathAcc.getNodes()) {
			writePathPointToStream(packetbuffer, node);
		}

		packetbuffer.writeInt(pathAcc.getOpenSetExtended().length);
		for(PathPoint node : pathAcc.getOpenSetExtended()) {
			writePathPointToStream(packetbuffer, node);
		}
		
		packetbuffer.writeInt(pathAcc.getClosedSetExtended().length);
		for(PathPoint node : pathAcc.getClosedSetExtended()) {
			writePathPointToStream(packetbuffer, node);
		}

		return packetbuffer;
	}
	private static void writeFlaggedPathPointToStream(PacketBuffer pBuf, FlaggedPathPoint flaggedPathPoint) {
		pBuf.writeInt(flaggedPathPoint.x);
		pBuf.writeInt(flaggedPathPoint.y);
		pBuf.writeInt(flaggedPathPoint.z);
		pBuf.writeFloat(flaggedPathPoint.walkedDistance);
		pBuf.writeFloat(flaggedPathPoint.costMalus);
		pBuf.writeBoolean(flaggedPathPoint.closed);
		pBuf.writeInt(flaggedPathPoint.type.ordinal());
		pBuf.writeFloat(flaggedPathPoint.f);
	}

	private static void writePathPointToStream(PacketBuffer pBuf, PathPoint pathPoint) {
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
