package com.github.maximuslotro.lotrrextended.common.world.gen.placement;

import com.mojang.serialization.Codec;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

/**
 * Enum representing extended cave surfaces, defining the orientation and vertical direction
 * relative to the cave structure.
 * <p>
 * Each enum constant represents a surface type with an associated direction (up or down),
 * a vertical offset (1 for ceiling, -1 for floor), and a string identifier.
 * </p>
 * <p>
 * Provides utility methods for serialization and lookup by name.
 * </p>
 */
public enum ExtendedCaveSurface implements IStringSerializable {
	/**
	 * Represents the cave ceiling surface, pointing upward with vertical offset 1.
	 */
	CEILING(Direction.UP, 1, "ceiling"),
	/**
     * Represents the cave floor surface, pointing downward with vertical offset -1.
     */
	FLOOR(Direction.DOWN, -1, "floor");
	/**
     * Codec for serialization/deserialization of ExtendedCaveSurface values.
     */
	public static final Codec<ExtendedCaveSurface> CODEC = IStringSerializable.fromEnum(ExtendedCaveSurface::values, ExtendedCaveSurface::byName);
	/**
	 * The direction associated with this cave surface (e.g., up for ceiling, down for floor).
	 */
	private final Direction direction;
	/**
	 * The vertical offset value representing the relative position of the surface:
	 * typically 1 for ceiling (up) and -1 for floor (down).
	 */
	private final int y;
	/**
	 * The string identifier used for serialization and lookup of the surface type.
	 */
	private final String id;
	/**
	 * Cached array of all enum constants for efficient lookup by name.
	 */
	private static final ExtendedCaveSurface[] VALUES = values();
	/**
     * Constructs an ExtendedCaveSurface enum instance.
     *
     * @param direction the direction this surface faces (up or down)
     * @param y the vertical offset associated with this surface (1 for ceiling, -1 for floor)
     * @param id the string identifier for serialization and lookup
     */
	private ExtendedCaveSurface(Direction direction, int y, String id) {
		this.direction = direction;
		this.y = y;
		this.id = id;
	}
	 /**
     * Gets the direction this surface faces.
     *
     * @return the direction (UP for ceiling, DOWN for floor)
     */
	public Direction getDirection() {
		return this.direction;
	}
	 /**
     * Gets the vertical offset associated with this surface.
     *
     * @return 1 for ceiling, -1 for floor
     */
	public int getY() {
		return this.y;
	}
	/**
     * Returns the ExtendedCaveSurface corresponding to the given serialized name.
     *
     * @param name the serialized name to lookup
     * @return the matching ExtendedCaveSurface enum constant
     * @throws IllegalArgumentException if no matching surface is found
     */
	public static ExtendedCaveSurface byName(String name) {
		for(ExtendedCaveSurface cavesurface : VALUES) {
			if (cavesurface.getSerializedName().equals(name)) {
				return cavesurface;
			}
		}

		throw new IllegalArgumentException("Unknown Surface type: " + name);
	}
	/**
     * Gets the string identifier for serialization.
     *
     * @return the id string representing this surface
     */
	@Override
	public String getSerializedName() {
		return this.id;
	}
}
