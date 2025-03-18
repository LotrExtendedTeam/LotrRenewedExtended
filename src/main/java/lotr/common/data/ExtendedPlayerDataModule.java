package lotr.common.data;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;

import net.minecraftforge.fml.LogicalSide;

public abstract class ExtendedPlayerDataModule extends PlayerDataModule {

	protected ExtendedPlayerDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	public void sendPacketToClientExtended(Object packet) {
		boolean executed = this.playerData.executeIfPlayerExistsServerside((player) -> {
			ExtendedPacketHandler.sendToClient(packet, player);
		});
		if (!executed && this.getLevelData().getLogicalSide() == LogicalSide.SERVER) {
			ExtendedLog.error("Server tried to send a playerdata packet (%s) to %s, but didn't find the player online!", new Object[]{packet.getClass().getSimpleName(), this.getPlayerUUID()});
		}
	}

	public void sendPacketToServerExtended(Object packet) {
		ExtendedPacketHandler.sendToServer(packet);
	}
}
