package com.github.maximuslotro.lotrrextended.mixins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedMixinLog {
	private static Logger LOGGER;

	public static void error(String s) {
		LOGGER.error("lotrExtendedMixin: {}", s);
	}

	public static void error(String s, Object... params) {
		LOGGER.error(String.format("lotrExtendedMixin: " + s, params));
	}

	public static void make() {
		LOGGER = LogManager.getLogger();
	}

	public static void info(String s) {
		LOGGER.info("lotrExtendedMixin: {}", s);
	}

	public static void info(String s, Object... params) {
		LOGGER.info(String.format("lotrExtendedMixin: " + s, params));
	}

	public static void warn(String s) {
		LOGGER.warn("lotrExtendedMixin: {}", s);
	}

	public static void warn(String s, Object... params) {
		LOGGER.warn(String.format("lotrExtendedMixin: " + s, params));
	}

	public static void debug(String s) {
		LOGGER.debug("lotrExtendedMixin: {}", s);
	}

	public static void debug(String s, Object... params) {
		LOGGER.debug(String.format("lotrExtendedMixin: " + s, params));
	}
}
