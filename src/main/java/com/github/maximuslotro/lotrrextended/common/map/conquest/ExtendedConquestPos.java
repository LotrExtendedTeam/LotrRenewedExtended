package com.github.maximuslotro.lotrrextended.common.map.conquest;

import java.util.Objects;

import net.minecraft.network.PacketBuffer;

public class ExtendedConquestPos implements Comparable<ExtendedConquestPos> {
	private final int x;
	private final int z;

	public ExtendedConquestPos(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getPosX() {
		return x;
	}

	public int getPosZ() {
		return z;
	}

	public void writeBuf(PacketBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(z);
	}

	public static ExtendedConquestPos readBuf(PacketBuffer buf) {
		return new ExtendedConquestPos(buf.readInt(), buf.readInt());
	}

	@Override
	public int compareTo(ExtendedConquestPos other) {
		if (other == null) {
			throw new NullPointerException("Cannot compare ExtendedConquestPos to null");
		}
		int same = Integer.compare(this.x, other.x);
		if (same != 0) {
			return same;
		}
		return Integer.compare(this.z, other.z);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ExtendedConquestPos that = (ExtendedConquestPos) o;
		return this.x == that.x && this.z == that.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, z);
	}

	@Override
	public String toString() {
		return "ExtendedConquestPos{" + "x=" + x + ", z=" + z + '}';
	}
}
