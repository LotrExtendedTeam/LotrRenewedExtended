package com.github.maximuslotro.lotrrextended.common.staffserver;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedServerData {
	private static final Logger LOGGER = LogManager.getLogger();
	public static final Path EXTENDED_DIR = Paths.get("lotrextended/");

	public static void loadAll() {
		try {
			Files.createDirectories(EXTENDED_DIR);
			ExtendedModTracker.load();
			ExtendedCombatTracker.load();
			ExtendedTravelTracker.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveAll() {
		try {
			Files.createDirectories(EXTENDED_DIR);
			ExtendedModTracker.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}