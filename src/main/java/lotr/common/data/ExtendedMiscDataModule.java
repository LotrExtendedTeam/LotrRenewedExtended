package lotr.common.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedMiscDataModule extends PlayerDataModule {

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
}