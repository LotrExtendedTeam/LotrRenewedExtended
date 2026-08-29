package com.github.maximuslotro.lotrrextended.common.map.conquest;

import net.minecraft.network.PacketBuffer;

public class ExtendedConquestPos {
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
}
