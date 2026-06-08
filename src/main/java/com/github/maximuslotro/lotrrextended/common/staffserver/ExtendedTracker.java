package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.google.gson.Gson;

public interface ExtendedTracker {

	public static void saveJson(Gson gson, Path path, Object obj) throws IOException {
		Files.write(path, gson.toJson(obj).getBytes());
	}

	public static void logToFile(Logger logger, Path logFile, String message) {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    String timedMessage = "[" + timestamp + "] " + message;
		logger.warn(message);
		try {
			Files.write(logFile, (timedMessage + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE,  StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void migrateIfNeeded(Path newFile, Path oldFile) {
	    try {
	        if (!Files.exists(newFile) && Files.exists(oldFile)) {
	            // Ensure new directory exists
	            Files.createDirectories(newFile.getParent());
	            // Move old file to new location
	            Files.move(oldFile, newFile);
	            // Delete old directory if empty
	            tryDeleteDirectory(oldFile.getParent());

	            ExtendedLog.info("[Extended] Migrated " + oldFile + " → " + newFile);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void tryDeleteDirectory(Path dir) {
	    try {
	        if (Files.isDirectory(dir) && Files.list(dir).findAny().isPresent()) {
	            Files.delete(dir);
	        }
	    } catch (IOException ignored) {}
	}
}
