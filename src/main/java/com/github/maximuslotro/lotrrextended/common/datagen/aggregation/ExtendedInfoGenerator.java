package com.github.maximuslotro.lotrrextended.common.datagen.aggregation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedInfoGenerator implements IDataProvider {
	private static final Logger LOGGER = LogManager.getLogger();

	protected static void save(Gson pGson, DirectoryCache pCache, JsonElement info, Path pPath) throws IOException {
		try {
			String s = pGson.toJson(info);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
				Files.createDirectories(pPath.getParent());
				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
					bufferedwriter.write(s);
				}
			}

			pCache.putNew(pPath, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save info {}", pPath, ioexception);
		}
	}

	protected static Path createPath(Path base, ResourceLocation loc, String type) {
	    return base.resolve("info/" + loc.getNamespace() + "/" + type + "/" + loc.getPath() + ".json");
	}

}
