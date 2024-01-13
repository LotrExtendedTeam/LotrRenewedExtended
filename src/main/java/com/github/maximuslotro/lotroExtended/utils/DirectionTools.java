package com.github.maximuslotro.lotroExtended.utils;

import java.util.Random;

import net.minecraft.util.Direction;

public class DirectionTools {
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

	public static Direction getOppositeDirection(Direction direction) {
		switch(direction) {
			case NORTH:
				return Direction.SOUTH;
			case SOUTH:
				return Direction.NORTH;
			case WEST:
				return Direction.EAST;
			case EAST:
				return Direction.WEST;
			default:
				throw new IllegalArgumentException("Direction MUST be NORTH, SOUTH, WEST, or EAST!");
		}
	}

	public static boolean isOppositeDirection(Direction direction, Direction directionOPP) {
		switch(direction) {
			case NORTH:
				if(directionOPP.equals(Direction.SOUTH)) {
					return true;
				}
				return false;
			case SOUTH:
				if(directionOPP.equals(Direction.NORTH)) {
					return true;
				}
				return false;
			case WEST:
				if(directionOPP.equals(Direction.EAST)) {
					return true;
				}
				return false;
			case EAST:
				if(directionOPP.equals(Direction.WEST)) {
					return true;
				}
				return false;
			default:
				throw new IllegalArgumentException("Direction MUST be NORTH, SOUTH, WEST, or EAST!");
		}
	}
}
