package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AxisAlignedBBUtils {
	public static AxisAlignedBB makeCroppedBB(BlockPos pos) {
		return makeCroppedBB(pos.getX(), pos.getY(), pos.getZ());
	}
	public static AxisAlignedBB makeCroppedBB(BlockPos pos, int xOffset1, int zOffset1, BlockPos pos2, int xOffset2, int zOffset2) {
		return makeCroppedBB(pos.getX(), pos.getY(), pos.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), xOffset1, zOffset1, xOffset2, zOffset2);
	}

	public static AxisAlignedBB makeCroppedBB(double pX, double pY, double pZ) {
		int x = MathHelper.floor(pX);
		int y = MathHelper.floor(pY);
		int z = MathHelper.floor(pZ);
		return (new AxisAlignedBB(x, y, z, x+1, y+1, z+1));
	}

	public static AxisAlignedBB makeCroppedBB(double pX1, double pY1, double pZ1, double pX2, double pY2, double pZ2, double xOffset1, double zOffset1, double xOffset2, double zOffset2 ) {
		int x1 = MathHelper.floor(pX1);
		int y1 = MathHelper.floor(pY1);
		int z1 = MathHelper.floor(pZ1);
		int x2 = MathHelper.floor(pX2);
		int y2 = MathHelper.floor(pY2);
		int z2 = MathHelper.floor(pZ2);
		return (new AxisAlignedBB(x1+xOffset1, y1, z1+zOffset1, x2+xOffset2, y2, z2+zOffset2));
	}
}
