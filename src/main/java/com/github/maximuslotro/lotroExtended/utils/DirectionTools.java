package com.github.maximuslotro.lotroExtended.utils;

import java.util.Random;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

public class DirectionTools {
	@Deprecated
	public static Direction getRandomDirection(Random rand) {
		switch(rand.nextInt(4)) {
			case 0:
				return Direction.NORTH;
			case 1:
				return Direction.WEST;
			case 2:
				return Direction.SOUTH;
			case 3:
				return Direction.EAST;
			default:
				throw new IllegalArgumentException("Direction MUST be NORTH, SOUTH, WEST, or EAST!");
		}
	}

	public static Rotation getRotationDirection(Direction structure, Direction facing) {
		if(structure==facing||facing==Direction.UP) {
			return Rotation.NONE;
		}
		if(structure.getOpposite()==facing) {
			return Rotation.CLOCKWISE_180;
		}
		if(structure.getClockWise()==facing) {
			return Rotation.COUNTERCLOCKWISE_90;
		}
		if(structure.getCounterClockWise()==facing) {
			return Rotation.CLOCKWISE_90;
		}
		return Rotation.NONE;
	}
}
