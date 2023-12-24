package com.github.maximuslotro.lotroExtended;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedLog {
	private static Logger LOGGER;

	public static void debug(String s) {
		LOGGER.debug("lotrExtended: " + s);
	}

	public static void debug(String s, Object... params) {
		LOGGER.debug(String.format("lotrExtended: " + s, params));
	}

	public static void error(String s) {
		LOGGER.error("lotrExtended: " + s);
	}

	public static void error(String s, Object... params) {
		LOGGER.error(String.format("lotrExtended: " + s, params));
	}

	public static void make() {
		LOGGER = LogManager.getLogger();
	}

	public static void info(String s) {
		LOGGER.info("lotrExtended: " + s);
	}

	public static void info(String s, Object... params) {
		LOGGER.info(String.format("lotrExtended: " + s, params));
	}

	public static void warn(String s) {
		LOGGER.warn("lotrExtended: " + s);
	}

	public static void warn(String s, Object... params) {
		LOGGER.warn(String.format("lotrExtended: " + s, params));
	}
}
