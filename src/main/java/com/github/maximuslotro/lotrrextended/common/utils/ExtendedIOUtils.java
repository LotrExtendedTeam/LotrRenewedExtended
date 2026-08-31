package com.github.maximuslotro.lotrrextended.common.utils;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.resources.IResource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.FolderName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendedIOUtils {
	public static final FolderName LOTR_FOLDER = new FolderName("lotr");
	public static final FolderName EXTENDED_FOLDER = new FolderName("lotrextended");

	public static CompoundNBT load(File file) {
		try {
			if(file.exists()) {
				FileInputStream fileStream = new FileInputStream(file);
				CompoundNBT compound = CompressedStreamTools.readCompressed(fileStream);
				fileStream.close();
				return compound;
			}
		} catch(IOException e) {
			ExtendedLog.error("Failed to load file: "+file.toString(), e);
		}
		return new CompoundNBT();
	}

	public static CompoundNBT loadInternal(ResourceLocation file, MinecraftServer server) {
		try(IResource iresource = server.getDataPackRegistries().getResourceManager().getResource(file)) {
			return CompressedStreamTools.readCompressed(iresource.getInputStream());
		} catch(Exception e) {
			ExtendedLog.error("Failed to find file: "+file.toString(), e);
		}
		return new CompoundNBT();
	}

	public static void save(File file, CompoundNBT compound) {
		try {
			CompressedStreamTools.writeCompressed(compound, new FileOutputStream(file));
		} catch(IOException e) {
			ExtendedLog.error("Error saving data file: {} for data [{}]", file.toString(), compound.toString(), e);
		}
	}

	public static File getDirOrMake(File pFolderName) {
		if(!pFolderName.exists()) {
			pFolderName.mkdirs();
		}
		return pFolderName;
	}

	public static File getDirInServer(FolderName pFolderName, MinecraftServer server, boolean shouldCreate) {
		File dir = server.getWorldPath(pFolderName).toFile();
		if(shouldCreate && !dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public static void moveIfOldPath(Path newPath, Path oldPath, boolean bypassNewFolderCheck) {
		try {
			if((bypassNewFolderCheck || !Files.exists(newPath)) && Files.exists(oldPath)) {
				// Handle moving folders
				if(Files.isDirectory(oldPath)) {
		            Files.createDirectories(newPath);
		            List<Path> children;
		            try(Stream<Path> s = Files.list(oldPath)) {
		                children = s.collect(Collectors.toList());
		            }		            // Handle recursive folders
		            for(Path child : children) {
		                Path targetChild = newPath.resolve(child.getFileName());
		                if(Files.isDirectory(child)) {
		                    moveIfOldPath(targetChild, child, bypassNewFolderCheck);
		                } else {
		                    Files.move(child, targetChild, StandardCopyOption.REPLACE_EXISTING);
		                }
		            }
		            tryDeleteDirectory(oldPath);
		        } else {
		            if(newPath.getParent() != null) {
		                Files.createDirectories(newPath.getParent());
		            }
		            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
		            if(oldPath.getParent() != null) {
		                tryDeleteDirectory(oldPath.getParent());
		            }
		        }
				ExtendedLog.info("Migrated {} -> {}", oldPath.toString(), newPath.toString());
			}
		} catch(IOException e) {
			ExtendedLog.warn("Failed moving files: from {} -> {}", oldPath.toString(), newPath.toString(), e);
		}
	}

	public static void tryDeleteDirectory(Path path) {
		try {
			if(Files.isDirectory(path)) {
				// Open the stream inside try-with-resources to ensure it closes properly
				try(Stream<Path> stream = Files.list(path)) {
					if(!stream.findAny().isPresent()) {
						Files.delete(path);
					}
				}
			}
		} catch(IOException e) {
			ExtendedLog.warn("Failed deleting path: {}", path.toString(), e);
		}
	}

	public static void tryDeleteDirectoryContents(Path path) {
	    if (!Files.exists(path)) return;
	    
	    try(Stream<Path> stream = Files.list(path)) {
	        stream.forEach(child -> {
	            try {
	                Files.delete(child);
	            } catch (IOException e) {
	                ExtendedLog.warn("Failed deleting file: {}", child, e);
	            }
	        });
	    } catch (IOException e) {
	        ExtendedLog.warn("Failed reading directory: {}", path, e);
	    }
	}
}
