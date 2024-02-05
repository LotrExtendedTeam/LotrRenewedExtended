package com.github.maximuslotro.lotroExtended.utils;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

public class DirectionTools {
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
