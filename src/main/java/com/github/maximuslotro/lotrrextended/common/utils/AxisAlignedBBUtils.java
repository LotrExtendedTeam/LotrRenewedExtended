package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class AxisAlignedBBUtils {
	public static AxisAlignedBB makeCroppedBB(BlockPos pos) {
		return makeCroppedBB(pos.getX(), pos.getY(), pos.getZ());
	}
	public static AxisAlignedBB makeCroppedBB(BlockPos pos, int xOffset1, int zOffset1, BlockPos pos2, int xOffset2, int zOffset2) {
		return makeCroppedBB(pos.getX(), pos.getY(), pos.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), xOffset1, zOffset1, xOffset2, zOffset2);
	}

	public static AxisAlignedBB makeOffsetBB(Vector3d pos, double xOffset1, double zOffset1, Vector3d pos2, double xOffset2, double zOffset2) {
		return makeCroppedBB(pos.x(), pos.y(), pos.z(), pos2.x(), pos2.y(), pos2.z(), xOffset1, zOffset1, xOffset2, zOffset2);
	}

	public static AxisAlignedBB makeCroppedBB(double pX, double pY, double pZ) {
		return new AxisAlignedBB(pX, pY, pZ, pX+1, pY+1, pZ+1);
	}

	public static AxisAlignedBB makeCroppedBB(BlockPos pos, int increase) {
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1);
	}

	public static AxisAlignedBB makeCroppedBB(double pX1, double pY1, double pZ1, double pX2, double pY2, double pZ2, double xOffset1, double zOffset1, double xOffset2, double zOffset2 ) {
		return new AxisAlignedBB(pX1+xOffset1, pY1, pZ1+zOffset1, pX2+xOffset2, pY2, pZ2+zOffset2);
	}
}
