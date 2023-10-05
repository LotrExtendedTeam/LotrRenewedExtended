package lotr.common.command.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotroExtended.hooks.MixinHooks;

import lotr.common.LOTRMod;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerDataModule;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.fellowship.ExtendedFellowshipInvite;
import lotr.common.fellowship.ExtendedFellowshipHolderServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedFellowshipDataModule extends PlayerDataModule {

	private List<UUID> fellowshipIDs = new ArrayList<UUID>();

	private List<ExtendedFellowshipInvite> invites = new ArrayList<ExtendedFellowshipInvite>();

	public ExtendedFellowshipDataModule(LOTRPlayerData pd) {
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
			fellowshipInvites.add(nbt);
		}
		playerNBT.put("FellowshipInvites", fellowshipInvites);

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
			ExtendedFellowship fellowship = ExtendedFellowshipHolderServer
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
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			fs.removeFellowshipAdmin(player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("You been demoted from fellowship admin in "+fs.getFellowshipName(), owner);
		}
	}

	public void addFellowshipAdmin(ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			fs.addFellowshipAdmin(player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("You been promoted to fellowship admin in "+fs.getFellowshipName(), owner);
		}
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
		for(UUID invite : fellowshipIDs) {
			if(invite.equals(fs.getFellowshipId())) {
				return;
			}
		}
		invites.add(new ExtendedFellowshipInvite(fs.getFellowshipId(), playerSendingInvite));
		markDirty();
		PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
		fs.createNotification("You have recieved a fellowship invite", owner);
	}

	public void removeInvitePlayer(ExtendedFellowship fs, boolean reject) {
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
			if(reject) {
				PlayerEntity player = getPlayerFromUUID(getPlayerUUID());
				fs.createNotification("You have accepted rejected fellowship invite", player);
			}
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
				removeInvitePlayer(fs, false);
				return;
			}
			fs.addFellowshipMember(getPlayerUUID());
			invites.remove(existingInvite);
			markDirty();
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			fs.createNotification("You have accepted the fellowship invite", owner);
		}
	}

	public void setIcon(ExtendedFellowship fs, UUID player, ItemStack stack) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setIcon(stack);
	}

	public void removePlayer(ExtendedFellowship fs, UUID player) {
		if (fs.hasEditPowers(getPlayerUUID())) {
			fs.removeFellowshipMember(player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("You been removed from the fellowship "+fs.getFellowshipName(), owner);
		}
	}

	public void setOwner(ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			fs.setOwnerUUID(player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("You have been transfered ownership of the fellowship "+fs.getFellowshipName(), owner);
		}
	}

	public void createFellowship(String name, boolean adminOverride) {
		if (!MixinHooks.getFellowshipConfigData() && !adminOverride) {
			TextComponent comp = new StringTextComponent("Fellowship creation is disabled!");
			comp.withStyle(TextFormatting.YELLOW);
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			owner.sendMessage(comp, owner.getUUID());
			return;
		}

		if (!anyMatchingFellowshipNames(name)) {
			ExtendedFellowship fellowship = new ExtendedFellowship(name, getPlayerUUID());
			fellowship.createAndRegister();
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			fellowship.createNotification("Fellowship created!", owner);
		}
	}

	public void leaveFellowship(ExtendedFellowship fs) {
		if (!fs.isFellowshipOwner(getPlayerUUID())) {
			fs.removeFellowshipMember(getPlayerUUID());
			if (fellowshipIDs.contains(fs.getFellowshipId()))
				removeFellowship(fs);
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			fs.createNotification("You have left the fellowship", owner);
		}
	}

	public void disbandFellowship(ExtendedFellowship fs) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			for(UUID user : fs.getFellowshipMembers()) {
				PlayerEntity player = getPlayerFromUUID(user);
				if(player!=null) {
					fs.createNotification("The fellowship "+fs.getFellowshipName()+"  has been disbanded", player);
				}
			}
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
			ExtendedFellowship fs = ExtendedFellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}

	public ExtendedFellowship getFellowshipByInviteName(String fsName) {
		for (ExtendedFellowshipInvite fsID : invites) {
			ExtendedFellowship fs = ExtendedFellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID.fellowshipID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}

	public List<String> listAllFellowshipNames() {
		List<String> list = new ArrayList<String>();
		for (UUID fsID : fellowshipIDs) {
			ExtendedFellowship fs = ExtendedFellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && (fs.isFellowshipMember(getPlayerUUID()) || fs.isFellowshipOwner(getPlayerUUID())))
				list.add(fs.getFellowshipName());
		}
		return list;
	}

	public List<String> listAllInviteNames() {
		List<String> list = new ArrayList<String>();
		for (ExtendedFellowshipInvite fsID : invites) {
			ExtendedFellowship fs = ExtendedFellowshipHolderServer.getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID.fellowshipID);
			if (fs != null)
				list.add(fs.getFellowshipName());
		}
		return list;
	}

	public PlayerEntity getPlayerFromUUID(UUID id) {
		if (LOTRMod.PROXY.isClient()) {
			return null;
		} else {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			return server.getPlayerList().getPlayer(id);
		}
	}
}