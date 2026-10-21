package com.github.maximuslotro.lotrrextended.client.render.utils;

/**
 * Utility class providing static methods for packing and unpacking color values
 * between packed integer formats and normalized float arrays for rendering.
 */
public class ExtendedColorUtil {
	/**
	 * Packs individual ARGB color components (0–255) into a single 32-bit integer.
	 *
	 * @param alpha the alpha (opacity) component, from 0 to 255
	 * @param red   the red color component, from 0 to 255
	 * @param green the green color component, from 0 to 255
	 * @param blue  the blue color component, from 0 to 255
	 * @return the packed 32-bit ARGB color integer (0xAARRGGBB)
	 */
	public static int color(int alpha, int red, int green, int blue) {
		return alpha << 24 | red << 16 | green << 8 | blue;
	}
	/**
	 * Unpacks a 24-bit RGB (0xRRGGBB) or 32-bit ARGB (0xAARRGGBB) integer into 
	 * normalized RGBA float components.
	 * <p>
	 * If the alpha bits are 0 (e.g., in a standard 24-bit RGB integer), the alpha component 
	 * defaults to fully opaque (1.0F).
	 *
	 * @param color the packed color integer
	 * @return a 4-element float array containing normalized {@code [r, g, b, a]} values (0.0F–1.0F)
	 */
	public static float[] unpackRGBA(int color) {
		float r = ((color >> 16) & 0xFF) / 255.0F;
		float g = ((color >> 8) & 0xFF) / 255.0F;
		float b = (color & 0xFF) / 255.0F;
		// Extract alpha if present; if alpha bits are 0 (24-bit RGB), default to 1.0F (opaque)
		float a = ((color >> 24) & 0xFF) / 255.0F;
		if (a == 0.0F) {
			a = 1.0F;
		}
		return new float[] { r, g, b, a };
	}
	/**
	 * Unpacks a 24-bit RGB (0xRRGGBB) integer into normalized RGB float components.
	 *
	 * @param color the packed color integer
	 * @return a 3-element float array containing normalized {@code [r, g, b]} values (0.0F–1.0F)
	 */
	public static float[] unpackRGB(int color) {
		return new float[] {
				((color >> 16) & 0xFF) / 255.0F,
				((color >> 8) & 0xFF) / 255.0F,
				(color & 0xFF) / 255.0F
		};
	}
	/**
	 * Combines an RGB color integer with a specified alpha transparency value.
	 * <p>
	 * Strips any existing alpha bits from the input {@code rgb} color and injects 
	 * the new alpha channel into the highest 8 bits (ARGB format).
	 *
	 * @param rgb   the base RGB (or ARGB) color integer (e.g., {@code 0x024B75})
	 * @param alpha the desired alpha transparency value, clamped between {@code 0} (fully transparent) 
	 *              and {@code 255} (fully opaque)
	 * @return the combined ARGB color integer (e.g., {@code 0x80024B75})
	 */
	public static int applyAlpha(int rgb, int alpha) {
		int clampedAlpha = Math.min(255, Math.max(0, alpha));
		return (clampedAlpha << 24) | (rgb & 0x00FFFFFF);
	}
}
