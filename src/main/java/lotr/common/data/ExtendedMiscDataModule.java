package lotr.common.data;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedDualTogglePacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedDualTogglePacket.ExtendedNetworkToggles;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedNBTUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedMiscDataModule extends ExtendedPlayerDataModule {

	private boolean hiredUnitFriendlyFire;
	private boolean hiredUnitDeathMessages;
	private boolean highContrastMessages;
	private boolean hiredUnitInnerCombat;

	public ExtendedMiscDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT nbt) {
		nbt.putBoolean("HiredUnitFriendlyFire", hiredUnitFriendlyFire);
		nbt.putBoolean("HiredUnitDeathMessages", hiredUnitDeathMessages);
		nbt.putBoolean("HighContrastMessages", highContrastMessages);
		nbt.putBoolean("HiredUnitInnerCombat", hiredUnitInnerCombat);
	}

	@Override
	public void load(CompoundNBT nbt) {
		hiredUnitFriendlyFire = ExtendedNBTUtils.getBoolean(nbt, "HiredUnitFriendlyFire", false);
		hiredUnitDeathMessages = ExtendedNBTUtils.getBoolean(nbt, "HiredUnitDeathMessages", true);
		highContrastMessages = ExtendedNBTUtils.getBoolean(nbt, "HighContrastMessages", false);
		hiredUnitInnerCombat = ExtendedNBTUtils.getBoolean(nbt, "HiredUnitInnerCombat", false);
	}

	@Override
	protected void sendLoginData(PacketBuffer buf) {
		buf.writeBoolean(hiredUnitFriendlyFire);
		buf.writeBoolean(hiredUnitDeathMessages);
		buf.writeBoolean(highContrastMessages);
		buf.writeBoolean(hiredUnitInnerCombat);
	}

	@Override
	protected void receiveLoginData(PacketBuffer buf) {
		hiredUnitFriendlyFire = buf.readBoolean();
		hiredUnitDeathMessages = buf.readBoolean();
		highContrastMessages = buf.readBoolean();
		hiredUnitInnerCombat = buf.readBoolean();
	}

	public boolean canHurtHiredUnits() {
		return hiredUnitFriendlyFire;
	}

	public boolean canHiredUnitAttackOtherHiredUnit() {
		return hiredUnitInnerCombat;
	}

	public boolean shouldShowHiredUnitDeathMessages() {
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
		}else if(type==ExtendedNetworkToggles.UNIT_V_UNIT) {
			if (this.hiredUnitInnerCombat != toggle) {
				this.hiredUnitInnerCombat = toggle;
				this.markDirty();
				this.sendPacketToClientExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNIT_V_UNIT, hiredUnitInnerCombat));
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

	public void toggleCombatBetweenUnitsAndSendToServer() {
		this.hiredUnitInnerCombat = !this.hiredUnitInnerCombat;
		this.sendPacketToServerExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNIT_V_UNIT, hiredUnitInnerCombat));
	}
}