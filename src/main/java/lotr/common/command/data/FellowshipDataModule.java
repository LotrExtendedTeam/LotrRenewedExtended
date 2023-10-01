package lotr.common.command.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lotr.common.command.fellowship.ExtendedFellowship;
import lotr.common.command.fellowship.FellowshipDataHolder;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerDataModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class FellowshipDataModule extends PlayerDataModule {

	private List<UUID> fellowshipIDs = new ArrayList<UUID>();

	// private List<LOTRFellowshipClient> fellowshipsClient = new
	// ArrayList<LOTRFellowshipClient>();

	// private List<LOTRFellowshipInvite> fellowshipInvites = new
	// ArrayList<LOTRFellowshipInvite>();

	// private List<LOTRFellowshipClient> fellowshipInvitesClient = new
	// ArrayList<LOTRFellowshipClient>();

	// private UUID chatBoundFellowshipID;

	public FellowshipDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT playerNBT) {
		ListNBT fellowshipTags = new ListNBT();
		for (UUID fsID : this.fellowshipIDs) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("ID", fsID.toString());
			fellowshipTags.add(nbt);
		}
		playerNBT.put("Fellowships", fellowshipTags);

	}

	@Override
	public void load(CompoundNBT playerNBT) {
		this.fellowshipIDs.clear();
		ListNBT fellowshipTags = playerNBT.getList("Fellowships", 10);
		for (int i = 0; i < fellowshipTags.size(); i++) {
			CompoundNBT nbt = fellowshipTags.getCompound(i);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			if (fsID != null)
				this.fellowshipIDs.add(fsID);
		}
	}

	public List<UUID> getFellowshipIDs() {
		return fellowshipIDs;
	}

	public List<ExtendedFellowship> getFellowships() {
		List<ExtendedFellowship> ships = new ArrayList<ExtendedFellowship>();
		for (UUID ids : fellowshipIDs) {
			ExtendedFellowship fellowship = FellowshipDataHolder.getFellowship(ids);
			if (fellowship != null)
				ships.add(fellowship);
		}
		return ships;
	}

	public void addFellowship(ExtendedFellowship fellowship) {
		if (fellowship.isFellowshipMember(this.getPlayerUUID())) {
			UUID fsID = fellowship.getFellowshipId();
			if (!fellowshipIDs.contains(fsID)) {
				fellowshipIDs.add(fsID);
				markDirty();
			}
		}
	}

	public void removeFellowship(ExtendedFellowship fellowship) {
		if (!fellowship.isFellowshipMember(this.getPlayerUUID())) {
			UUID fsID = fellowship.getFellowshipId();
			if (!fellowshipIDs.contains(fsID)) {
				fellowshipIDs.remove(fsID);
				markDirty();
			}
		}
	}

	public void renameFellowship(ExtendedFellowship fs, String name) {
		if (fs.isOwner(this.getPlayerUUID()))
			fs.setFellowshipName(name);
	}

	public void setFellowshipIcon(ExtendedFellowship fs, ItemStack itemstack) {
		if (fs.isOwner(this.getPlayerUUID()) || fs.isFellowshipAdmin(this.getPlayerUUID()))
			fs.setIcon(itemstack);
	}

	public void setFellowshipPreventPVP(ExtendedFellowship fs, boolean prevent) {
		if (fs.isOwner(this.getPlayerUUID()) || fs.isFellowshipAdmin(this.getPlayerUUID()))
			fs.setPreventPVP(prevent);
	}

	public void setFellowshipPreventHiredFF(ExtendedFellowship fs, boolean prevent) {
		if (fs.isOwner(this.getPlayerUUID()) || fs.isFellowshipAdmin(this.getPlayerUUID()))
			fs.setPreventHiredFF(prevent);
	}

	public void setFellowshipShowMapLocations(ExtendedFellowship fs, boolean show) {
		if (fs.isOwner(this.getPlayerUUID()))
			fs.setHideMapLocation(show);
	}

	public void createFellowship(String name) {
		// if (!LOTRConfig.enableFellowshipCreation)
		// return;
		if (!anyMatchingFellowshipNames(name)) {
			ExtendedFellowship fellowship = new ExtendedFellowship(name, this.getPlayerUUID());
			fellowship.createAndRegister();
		}
	}

	public boolean anyMatchingFellowshipNames(String name) {
		for (UUID fsID : this.fellowshipIDs) {
			ExtendedFellowship fs = FellowshipDataHolder.getFellowship(fsID);
			if (fs != null) {
				String otherName = fs.getFellowshipName();
				otherName = StringUtils.strip(otherName).toLowerCase();
				if (name.equals(otherName))
					return true;
			}
		}
		return false;
	}

	public ExtendedFellowship getFellowshipByName(String fsName) {
		for (UUID fsID : this.fellowshipIDs) {
			ExtendedFellowship fs = FellowshipDataHolder.getFellowship(fsID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}
}
