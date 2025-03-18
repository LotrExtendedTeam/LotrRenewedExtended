package lotr.common.data;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedNetworkToggles;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedDualTogglePacket;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedMiscDataModule extends ExtendedPlayerDataModule {

	private boolean hiredUnitFriendlyFire;
	private boolean hiredUnitDeathMessages;
	private boolean highContrastMessages;

	public ExtendedMiscDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT nbt) {
		nbt.putBoolean("HiredUnitFriendlyFire", hiredUnitFriendlyFire);
		nbt.putBoolean("HiredUnitDeathMessages", hiredUnitDeathMessages);
		nbt.putBoolean("HighContrastMessages", highContrastMessages);
	}

	@Override
	public void load(CompoundNBT nbt) {
		hiredUnitFriendlyFire = nbt.getBoolean("HiredUnitFriendlyFire");
		if(!nbt.contains("HiredUnitDeathMessages")) {
			hiredUnitDeathMessages = true; // Need to return true by default
		}else {
			hiredUnitDeathMessages = nbt.getBoolean("HiredUnitDeathMessages");
		}
		highContrastMessages = nbt.getBoolean("HighContrastMessages");
	}

	@Override
	protected void sendLoginData(PacketBuffer buf) {
		buf.writeBoolean(hiredUnitFriendlyFire);
		buf.writeBoolean(hiredUnitDeathMessages);
		buf.writeBoolean(highContrastMessages);
	}

	@Override
	protected void receiveLoginData(PacketBuffer buf) {
		hiredUnitFriendlyFire = buf.readBoolean();
		hiredUnitDeathMessages = buf.readBoolean();
		highContrastMessages = buf.readBoolean();
	}

	public boolean canHurtHiredUnits() {
		return hiredUnitFriendlyFire;
	}

	public boolean canHiredUnitDeathMessages() {
		return hiredUnitDeathMessages;
	}

	public boolean doHighContrastMessages() {
		return highContrastMessages;
	}

	public void handleTogglePackets(ExtendedNetworkToggles type, boolean toggle) {
		if(type==ExtendedNetworkToggles.FRIENDLY_FIRE) {
			this.playerData.getAlignmentData().setFriendlyFireEnabled(toggle);
		}else if(type==ExtendedNetworkToggles.UNITS_FRIENDLY_FIRE) {
			if (this.hiredUnitFriendlyFire != toggle) {
				this.hiredUnitFriendlyFire = toggle;
				this.markDirty();
				this.sendPacketToClientExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNITS_FRIENDLY_FIRE, hiredUnitFriendlyFire));
			}
		}else if(type==ExtendedNetworkToggles.MAP_LOCATION) {
			this.playerData.getMiscData().setShowMapLocation(toggle);
		}else if(type==ExtendedNetworkToggles.MAP_MARKERS) {
			this.playerData.getMapMarkerData().setShowMarkers(toggle);
		}else if(type==ExtendedNetworkToggles.UNIT_DEATH_MESSAGES) {
			if (this.hiredUnitDeathMessages != toggle) {
				this.hiredUnitDeathMessages = toggle;
				this.markDirty();
				this.sendPacketToClientExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNIT_DEATH_MESSAGES, hiredUnitDeathMessages));
			}
		}else if(type==ExtendedNetworkToggles.HIGH_CONTRAST_MESSAGES) {
			if (this.highContrastMessages != toggle) {
				this.highContrastMessages = toggle;
				this.markDirty();
				this.sendPacketToClientExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.HIGH_CONTRAST_MESSAGES, highContrastMessages));
			}
		}
	}

	public void toggleHurtHiredUnitsAndSendToServer() {
		this.hiredUnitFriendlyFire = !this.hiredUnitFriendlyFire;
		this.sendPacketToServerExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNITS_FRIENDLY_FIRE, hiredUnitFriendlyFire));
	}

	public void toggleHiredUnitDeathMessagesAndSendToServer() {
		this.hiredUnitDeathMessages = !this.hiredUnitDeathMessages;
		this.sendPacketToServerExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNIT_DEATH_MESSAGES, hiredUnitDeathMessages));
	}

	public void toggleHighContrastMessagesAndSendToServer() {
		this.highContrastMessages = !this.highContrastMessages;
		this.sendPacketToServerExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.HIGH_CONTRAST_MESSAGES, highContrastMessages));
	}
}