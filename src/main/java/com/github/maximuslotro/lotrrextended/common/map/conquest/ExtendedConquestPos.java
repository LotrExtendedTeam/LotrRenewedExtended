package com.github.maximuslotro.lotrrextended.common.map.conquest;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;

/**
 * Based of {@link ChunkPos}
 * */
public class ExtendedConquestPos implements Comparable<ExtendedConquestPos> {
	private final int x;
	private final int z;

	public ExtendedConquestPos(int pX, int pZ) {
		this.x = pX;
		this.z = pZ;
	}

	public int getPosX() {
		return x;
	}

	public int getPosZ() {
		return z;
	}

	public long toLong() {
		return ((long) this.x & 4294967295L) | ((long) this.z & 4294967295L) << 32;
	}

	public static ExtendedConquestPos fromLong(long packed) {
		return new ExtendedConquestPos((int)(packed & 4294967295L), (int)(packed >>> 32));
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
		int cmp = Integer.compare(this.x, other.x);
		return cmp != 0 ? cmp : Integer.compare(this.z, other.z);
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
		return Long.hashCode(toLong());
	}

	@Override
	public String toString() {
		return "ConquestPos[" + this.x + ", " + this.z + "]";
	}
}
