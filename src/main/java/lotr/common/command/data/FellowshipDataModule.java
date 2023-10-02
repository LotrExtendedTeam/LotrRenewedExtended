package lotr.common.command.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotroExtended.LOTRHooks;

import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerDataModule;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.fellowship.FellowshipHolderServer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class FellowshipDataModule extends PlayerDataModule {

	private List<UUID> fellowshipIDs = new ArrayList<UUID>();

	// private List<LOTRFellowshipInvite> fellowshipInvites;

	public FellowshipDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT playerNBT) {
		ListNBT fellowshipTags = new ListNBT();
		for (UUID fsID : fellowshipIDs) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("ID", fsID.toString());
			fellowshipTags.add(nbt);
		}
		playerNBT.put("Fellowships", fellowshipTags);

	}

	@Override
	public void load(CompoundNBT playerNBT) {
		fellowshipIDs.clear();
		ListNBT fellowshipTags = playerNBT.getList("Fellowships", 10);
		for (int i = 0; i < fellowshipTags.size(); i++) {
			CompoundNBT nbt = fellowshipTags.getCompound(i);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			if (fsID != null)
				fellowshipIDs.add(fsID);
		}
	}

	public List<UUID> getFellowshipIDs() {
		return fellowshipIDs;
	}

	public List<ExtendedFellowship> getFellowships() {
		List<ExtendedFellowship> ships = new ArrayList<ExtendedFellowship>();
		for (UUID ids : fellowshipIDs) {
			ExtendedFellowship fellowship = FellowshipHolderServer
					.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), ids);
			if (fellowship != null)
				ships.add(fellowship);
		}
		return ships;
	}

	public void addFellowship(ExtendedFellowship fellowship) {
		if (fellowship.isFellowshipMember(getPlayerUUID()) || fellowship.isFellowshipOwner(getPlayerUUID())) {
			UUID fsID = fellowship.getFellowshipId();
			if (!fellowshipIDs.contains(fsID)) {
				fellowshipIDs.add(fsID);
				markDirty();
			}
		}
	}

	public void removeFellowship(ExtendedFellowship fellowship) {
		if (!fellowship.isFellowshipOwner(getPlayerUUID()) || fellowship.isRemoved()) {
			UUID fsID = fellowship.getFellowshipId();
			if (fellowshipIDs.contains(fsID)) {
				fellowshipIDs.remove(fsID);
				markDirty();
			}
		}
	}

	public void renameFellowship(ExtendedFellowship fs, String name) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setFellowshipName(name);
	}

	public void setFellowshipIcon(ExtendedFellowship fs, ItemStack itemstack) {
		if (fs.hasEditPowers(getPlayerUUID()) || fs.isFellowshipAdmin(getPlayerUUID()))
			fs.setIcon(itemstack);
	}

	public void setFellowshipPreventPVP(ExtendedFellowship fs, boolean prevent) {
		if (fs.hasEditPowers(getPlayerUUID()) || fs.isFellowshipAdmin(getPlayerUUID()))
			fs.setPreventPVP(prevent);
	}

	public void setFellowshipPreventHiredFF(ExtendedFellowship fs, boolean prevent) {
		if (fs.hasEditPowers(getPlayerUUID()) || fs.isFellowshipAdmin(getPlayerUUID()))
			fs.setPreventHiredFF(prevent);
	}

	public void setFellowshipShowMapLocations(ExtendedFellowship fs, boolean show) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setHideMapLocation(show);
	}

	public void removeFellowshipAdmin(ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID()))
			fs.removeFellowshipAdmin(player);
	}

	public void addFellowshipAdmin(ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID()))
			fs.addFellowshipAdmin(player);
	}

	public void addPlayer(ExtendedFellowship fs, UUID player) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.addFellowshipMember(player);
	}

	public void setIcon(ExtendedFellowship fs, UUID player, ItemStack stack) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setIcon(stack);
	}

	public void removePlayer(ExtendedFellowship fs, UUID player) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.removeFellowshipMember(player);
	}

	public void setOwner(ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID()))
			fs.setOwnerUUID(player);
	}

	public boolean createFellowship(String name, boolean adminOverride) {
		if (!LOTRHooks.getFellowshipConfigData() && !adminOverride)
			return false;

		if (!anyMatchingFellowshipNames(name)) {
			ExtendedFellowship fellowship = new ExtendedFellowship(name, getPlayerUUID());
			fellowship.createAndRegister();
			return true;
		}
		return false;
	}

	public void leaveFellowship(ExtendedFellowship fs) {
		if (!fs.isFellowshipOwner(getPlayerUUID())) {
			fs.removeFellowshipMember(getPlayerUUID());
			if (fellowshipIDs.contains(fs.getFellowshipId()))
				removeFellowship(fs);
		}
	}

	public void disbandFellowship(ExtendedFellowship fs) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			List<UUID> memberUUIDs = new ArrayList<UUID>(fs.getFellowshipMembers());
			fs.disband();
			removeFellowship(fs);
		}
	}

	public boolean anyMatchingFellowshipNames(String name) {
		if (getFellowshipByName(name) != null) {
			return true;
		}
		return false;
	}

	public ExtendedFellowship getFellowshipByName(String fsName) {
		for (UUID fsID : this.fellowshipIDs) {
			ExtendedFellowship fs = FellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}
}
