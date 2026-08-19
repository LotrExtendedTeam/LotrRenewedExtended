package com.github.maximuslotro.lotrrextended.common.map.conquest;

import net.minecraft.nbt.CompoundNBT;

public class ExtendedConquestRegion {
	private final int x;
	private final int z;

	public ExtendedConquestRegion(ExtendedConquestPos conquestPos) {
		this(conquestPos.getPosX(), conquestPos.getPosZ());
	}

	public ExtendedConquestRegion(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getPosX() {
		return x;
	}

	public int getPosZ() {
		return z;
	}

	public boolean needsSave() {
		return false;
	}

	public CompoundNBT save() {
		CompoundNBT output = new CompoundNBT();
		output.putInt("x", x);
		output.putInt("z", z);
		return output;
	}

	public static ExtendedConquestRegion loadFromNBT(CompoundNBT nbt) {
		int x = nbt.getInt("x");
		int z = nbt.getInt("z");
		return new ExtendedConquestRegion(x, z);
	}
}
