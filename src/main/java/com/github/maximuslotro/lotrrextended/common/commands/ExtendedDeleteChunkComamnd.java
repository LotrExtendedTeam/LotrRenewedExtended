package com.github.maximuslotro.lotrrextended.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;

public class ExtendedDeleteChunkComamnd {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedDeleteChunk")
			.requires(context -> context.hasPermission(0))
			.executes(context -> deleteChunk(context.getSource())));
	}

	public static int deleteChunk(CommandSource source) throws CommandSyntaxException {
		// Inside your command executor or custom hook:
		ServerWorld world = source.getLevel();
		BlockPos playerPos = source.getPlayerOrException().blockPosition();

		// Get the chunk coordinates
		ChunkPos chunkPos = new ChunkPos(playerPos);
		int chunkX = chunkPos.x;
		int chunkZ = chunkPos.z;

		IChunk chunk = world.getChunk(chunkX, chunkZ, ChunkStatus.EMPTY, true);
        if (!chunk.getStatus().isOrAfter(ChunkStatus.FULL)) {
            chunk = world.getChunk(chunkX, chunkZ, ChunkStatus.FULL);
        }
		return Command.SINGLE_SUCCESS;
	}

}
