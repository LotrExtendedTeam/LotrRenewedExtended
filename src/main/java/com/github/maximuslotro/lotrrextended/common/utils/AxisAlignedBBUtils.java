package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AxisAlignedBBUtils {
	public static AxisAlignedBB makeCroppedBB(BlockPos pos) {
		return makeCroppedBB(pos.getX(), pos.getY(), pos.getZ());
	}

	public static AxisAlignedBB makeCroppedBB(double pX, double pY, double pZ) {
		int x = MathHelper.floor(pX);
		int y = MathHelper.floor(pY);
		int z = MathHelper.floor(pZ);
		return (new AxisAlignedBB(x, y, z, x+1, y+1, z+1));
	}
}
