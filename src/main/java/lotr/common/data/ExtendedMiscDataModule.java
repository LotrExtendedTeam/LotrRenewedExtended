package lotr.common.data;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedNetworkToggles;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedDualTogglePacket;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedMiscDataModule extends ExtendedPlayerDataModule {

	private boolean hiredUnitFriendlyFire;

	public ExtendedMiscDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT nbt) {
		nbt.putBoolean("HiredUnitFriendlyFire", hiredUnitFriendlyFire);
	}

	@Override
	public void load(CompoundNBT nbt) {
		hiredUnitFriendlyFire = nbt.getBoolean("HiredUnitFriendlyFire");
	}

	@Override
	protected void sendLoginData(PacketBuffer buf) {
		buf.writeBoolean(hiredUnitFriendlyFire);
	}

	@Override
	protected void receiveLoginData(PacketBuffer buf) {
		hiredUnitFriendlyFire = buf.readBoolean();
	}

	public boolean canHurtHiredUnits() {
		return hiredUnitFriendlyFire;
	}

	public void setHurtHiredUnits(boolean canUnitFF) {
		if (this.hiredUnitFriendlyFire != canUnitFF) {
			this.hiredUnitFriendlyFire = canUnitFF;
			this.markDirty();
			this.sendPacketToClientExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNITS_FRIENDLY_FIRE, hiredUnitFriendlyFire));
		}
	}

	public void toggleHurtHiredUnitsAndSendToServer() {
		this.hiredUnitFriendlyFire = !this.hiredUnitFriendlyFire;
		this.sendPacketToServerExtended(new ExtendedDualTogglePacket(ExtendedNetworkToggles.UNITS_FRIENDLY_FIRE, hiredUnitFriendlyFire));
	}
}