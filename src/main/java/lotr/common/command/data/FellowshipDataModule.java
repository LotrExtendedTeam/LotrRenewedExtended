package lotr.common.command.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotroExtended.LOTRHooks;

import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerDataModule;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.fellowship.ExtendedFellowshipInvite;
import lotr.common.fellowship.FellowshipHolderServer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class FellowshipDataModule extends PlayerDataModule {

	private List<UUID> fellowshipIDs = new ArrayList<UUID>();

	private List<ExtendedFellowshipInvite> invites = new ArrayList<ExtendedFellowshipInvite>();

	public FellowshipDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT playerNBT) {
		ListNBT fellowships = new ListNBT();
		for (UUID fsID : fellowshipIDs) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("ID", fsID.toString());
			fellowships.add(nbt);
		}
		playerNBT.put("Fellowships", fellowships);
		ListNBT fellowshipInvites = new ListNBT();
		for (ExtendedFellowshipInvite invite : invites) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("ID", invite.fellowshipID.toString());
			if (invite.Invitor != null)
				nbt.putString("InviterID", invite.Invitor.toString());
//			if (invite.Fname != null)
//				nbt.putString("FellowshipName", invite.Fname);
			fellowshipInvites.add(fellowshipInvites);
		}

	}

	@Override
	public void load(CompoundNBT playerNBT) {
		fellowshipIDs.clear();
		ListNBT fellowships = playerNBT.getList("Fellowships", 10);
		for (int i = 0; i < fellowships.size(); i++) {
			CompoundNBT nbt = fellowships.getCompound(i);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			if (fsID != null)
				fellowshipIDs.add(fsID);
		}
		invites.clear();
		ListNBT fellowshipInvite = playerNBT.getList("FellowshipInvites", 10);
		for (int i = 0; i < fellowshipInvite.size(); i++) {
			CompoundNBT nbt = fellowshipInvite.getCompound(i);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			if (fsID != null) {
				UUID inviterID = null;
				if (nbt.contains("InviterID"))
					inviterID = UUID.fromString(nbt.getString("InviterID"));
//				String fname = null;
//				if (nbt.contains("FellowshipName"))
//					fname = nbt.getString("FellowshipName");
				invites.add(new ExtendedFellowshipInvite(fsID, inviterID));
			}
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
	public void addInvitePlayer(ExtendedFellowship fs, UUID playerSendingInvite) {
		for(ExtendedFellowshipInvite invite : invites) {
			if(invite.fellowshipID.equals(fs.getFellowshipId())) {
				return;
			}
		}
		invites.add(new ExtendedFellowshipInvite(fs.getFellowshipId(), playerSendingInvite));
		markDirty();
	}
	public void removeInvitePlayer(ExtendedFellowship fs) {
		ExtendedFellowshipInvite foundInvite=null;
		for(ExtendedFellowshipInvite invite : invites) {
			if(invite.fellowshipID.equals(fs.getFellowshipId())) {
				foundInvite=invite;
				break;
			}
		}
		if(foundInvite!=null) {
			invites.remove(foundInvite);
			markDirty();
		}
	}

	public void acceptInvitePlayer(ExtendedFellowship fs) {
		ExtendedFellowshipInvite existingInvite = null;
		for (ExtendedFellowshipInvite invite : invites) {
			if (invite.fellowshipID.equals(fs.getFellowshipId())) {
				existingInvite = invite;
				break;
			}
		}
		if(existingInvite!=null) {
			if (fs.isRemoved()) {
				removeInvitePlayer(fs);
				return;
			}
			fs.addFellowshipMember(getPlayerUUID());
			invites.remove(existingInvite);
			markDirty();
		}
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
		for (UUID fsID : fellowshipIDs) {
			ExtendedFellowship fs = FellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}
	public boolean getInviteExistsForFellowship(ExtendedFellowship fellowship) {
		for (ExtendedFellowshipInvite invite : this.invites) {
			ExtendedFellowship fs = FellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), invite.fellowshipID);
			if (fs != null)
				return true;
		}
		return false;
	}

	public List<String> listAllFellowshipNames() {
		List<String> list = new ArrayList<String>();
		for (UUID fsID : fellowshipIDs) {
			ExtendedFellowship fs = FellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && (fs.isFellowshipMember(getPlayerUUID()) || fs.isFellowshipOwner(getPlayerUUID())))
				list.add(fs.getFellowshipName());
		}
		return list;
	}

	public List<String> listAllInviteNames() {
		List<String> list = new ArrayList<String>();
		for (ExtendedFellowshipInvite fsID : invites) {
			ExtendedFellowship fs = FellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID.fellowshipID);
			if (fs != null && (fs.isFellowshipMember(getPlayerUUID()) || fs.isFellowshipOwner(getPlayerUUID())))
				list.add(fs.getFellowshipName());
		}
		return list;
	}
}