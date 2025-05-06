package com.github.maximuslotro.lotrrextended;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedLog {
	private static Logger LOGGER;

	public static void make() {
		LOGGER = LogManager.getLogger();
	}

	public static void debug(String s) {
		LOGGER.debug("LotrExtended: {}", s);
	}

	public static void debug(String s, Object... params) {
		LOGGER.debug(String.format("LotrExtended: " + s, params));
	}

	public static void infoShown(String s) {
		info(s, true);
	}

	public static void infoHideable(String s, boolean flag) {
		info(s, flag);
	}

	private static void info(String s, boolean override) {
		if(override) {
			LOGGER.info("LotrExtended: {}", s);
		}
	}

	public static void infoParms(String s, Object... params) {
		LOGGER.info(String.format("LotrExtended: " + s, params));
	}

	public static void warn(String s) {
		LOGGER.warn("LotrExtended: {}", s);
	}

	public static void warn(String s, Object... params) {
		LOGGER.warn(String.format("LotrExtended: " + s, params));
	}

	public static void error(String s) {
		LOGGER.error("LotrExtended: {}", s);
	}

	public static void error(String s, Object... params) {
		LOGGER.error(String.format("LotrExtended: " + s, params));
	}
}
