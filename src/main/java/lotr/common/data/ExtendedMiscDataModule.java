package lotr.common.data;

import com.github.maximuslotro.lotrrextended.common.network.ExtendedDualTogglePacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedDualTogglePacket.ExtendedNetworkToggles;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedNBTUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedMiscDataModule extends ExtendedPlayerDataModule {

	private boolean hiredUnitFriendlyFire = false;
	private boolean hiredUnitDeathMessages = true;
	private boolean highContrastMessages = false;
	private boolean hiredUnitInnerCombat = false;

	public ExtendedMiscDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT playerNBT) {
		CompoundNBT micsData = new CompoundNBT();
		micsData.putBoolean("HiredUnitFriendlyFire", hiredUnitFriendlyFire);
		micsData.putBoolean("HiredUnitDeathMessages", hiredUnitDeathMessages);
		micsData.putBoolean("HighContrastMessages", highContrastMessages);
		micsData.putBoolean("HiredUnitInnerCombat", hiredUnitInnerCombat);
		playerNBT.put("MiscData", micsData);
	}

	@Override
	public void load(CompoundNBT playerNBT) {
		if(playerNBT.contains("MiscData")) {
			CompoundNBT micsData = playerNBT.getCompound("MiscData");
			hiredUnitFriendlyFire = ExtendedNBTUtils.getBoolean(micsData, "HiredUnitFriendlyFire", false);
			hiredUnitDeathMessages = ExtendedNBTUtils.getBoolean(micsData, "HiredUnitDeathMessages", true);
			highContrastMessages = ExtendedNBTUtils.getBoolean(micsData, "HighContrastMessages", false);
			hiredUnitInnerCombat = ExtendedNBTUtils.getBoolean(micsData, "HiredUnitInnerCombat", false);
		}else {
			hiredUnitFriendlyFire = ExtendedNBTUtils.getBoolean(playerNBT, "HiredUnitFriendlyFire", false);
			hiredUnitDeathMessages = ExtendedNBTUtils.getBoolean(playerNBT, "HiredUnitDeathMessages", true);
			highContrastMessages = ExtendedNBTUtils.getBoolean(playerNBT, "HighContrastMessages", false);
			hiredUnitInnerCombat = ExtendedNBTUtils.getBoolean(playerNBT, "HiredUnitInnerCombat", false);
		}
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