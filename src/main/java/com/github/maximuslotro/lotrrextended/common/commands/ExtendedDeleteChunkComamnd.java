package com.github.maximuslotro.lotrrextended.common.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.github.maximuslotro.lotrrextended.common.utils.world.ExtendedDeleatableRegionFile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

public class ExtendedDeleteChunkComamnd {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedDeleteChunk")
			.requires(context -> context.hasPermission(4))
			.then(Commands.argument("pos", Vec3Argument.vec3())
				.executes(context -> deleteChunk(context.getSource(), Vec3Argument.getVec3(context, "pos")))));
	}

	public static int deleteChunk(CommandSource source, Vector3d pPos) throws CommandSyntaxException {
		ServerWorld world = source.getLevel();
		BlockPos blockPos = new BlockPos(pPos.x, pPos.y, pPos.z);
		ChunkPos chunkPos = new ChunkPos(blockPos);

		ServerChunkProvider chunkProvider = world.getChunkSource();
		chunkProvider.save(true);

		if (chunkProvider.hasChunk(chunkPos.x, chunkPos.z)) {
			source.sendFailure(new StringTextComponent("Cannot delete chunk [" + chunkPos.x + ", " + chunkPos.z + "]: Chunk is currently loaded in memory. Unload it first."));
			return 0;
		}

		File regionFolder = getRegionFolderForWorld(world);
		File regionFilePath = ExtendedDeleatableRegionFile.getRegionFilePath(regionFolder, chunkPos);

		if(!Files.exists(regionFilePath.toPath())) {
			source.sendFailure(new StringTextComponent("Region file does not exist: " + regionFilePath.getName()));
			return 0;
		}

		long bytesFreed = 0L;

		try(ExtendedDeleatableRegionFile regionFile = new ExtendedDeleatableRegionFile(regionFilePath, regionFolder, false)) {
			if(!regionFile.hasChunk(chunkPos)) {
				source.sendFailure(new StringTextComponent("Chunk " + chunkPos + " does not exist in region storage."));
				return 0;
			}

			bytesFreed = regionFile.getChunkSizeBytes(chunkPos);

			regionFile.deleteChunk(chunkPos);
		} catch(IOException e) {
			source.sendFailure(new StringTextComponent("Failed to delete chunk: " + e.getMessage()));
			e.printStackTrace();
			return 0;
		}

		long sizeAfterBytes = regionFilePath.length();

		String freedFormatted = String.format("%.2f KB", bytesFreed / 1024.0);

		source.sendSuccess(new StringTextComponent("Successfully deleted chunk [" + chunkPos.x + ", " + chunkPos.z +  "] from disk. Freed " + freedFormatted + " of chunk data."), true);
		return Command.SINGLE_SUCCESS;
	}

	public static File getRegionFolderForWorld(ServerWorld world) {
		// 1. Get the root save directory for the world container
		File rootWorldDir = world.getServer().getWorldPath(FolderName.ROOT).toFile();

		RegistryKey<World> dimensionKey = world.dimension();
		if(dimensionKey == World.OVERWORLD) {
			return new File(rootWorldDir, "region");
		} else if(dimensionKey == World.NETHER) {
			return new File(rootWorldDir, "DIM-1/region");
		} else if(dimensionKey == World.END) {
			return new File(rootWorldDir, "DIM1/region");
		} else {
			String namespace = dimensionKey.location().getNamespace();
			String path = dimensionKey.location().getPath();
			return new File(rootWorldDir, "dimensions/" + namespace + "/" + path + "/region");
		}
	}

}
