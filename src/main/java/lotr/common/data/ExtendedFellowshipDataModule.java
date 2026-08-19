package lotr.common.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedNBTUtils;

import lotr.common.LOTRMod;
import lotr.common.fellowship.ExtendedFellowship;
import lotr.common.fellowship.ExtendedFellowshipInvite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedFellowshipDataModule extends ExtendedPlayerDataModule {

	private List<UUID> fellowshipIDs = new ArrayList<>();

	private List<ExtendedFellowshipInvite> invites = new ArrayList<>();

	public ExtendedFellowshipDataModule(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public void save(CompoundNBT playerNBT) {
		CompoundNBT fellowshipData = new CompoundNBT();
		ListNBT fellowships = new ListNBT();
		for (UUID fsID : fellowshipIDs) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putUUID("ID", fsID);
			fellowships.add(nbt);
		}
		fellowshipData.put("Fellowships", fellowships);
		ListNBT fellowshipInvites = new ListNBT();
		for (ExtendedFellowshipInvite invite : invites) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putUUID("ID", invite.fellowshipID);
			if (invite.Invitor != null) {
				nbt.putUUID("InviterID", invite.Invitor);
			}
			fellowshipInvites.add(nbt);
		}
		fellowshipData.put("FellowshipInvites", fellowshipInvites);
		fellowshipData.putInt("datVer", 1);
		playerNBT.put("FellowshipData", fellowshipData);
	}

	@Override
	public void load(CompoundNBT playerNBT) {
		if(playerNBT.contains("FellowshipData")) {
			CompoundNBT fellowshipData = playerNBT.getCompound("FellowshipData");
			int dataVersion = ExtendedNBTUtils.getInt(fellowshipData, "datVer", 0);
			fellowshipIDs.clear();
			invites.clear();
			ListNBT fellowships = fellowshipData.getList("Fellowships", 10);
			for (int i = 0; i < fellowships.size(); i++) {
				CompoundNBT nbt = fellowships.getCompound(i);
				UUID fsID = ExtendedNBTUtils.getUUID(nbt, "ID", null);
				if (fsID != null) {
					fellowshipIDs.add(nbt.getUUID("ID"));
				}
			}
			ListNBT fellowshipInvite = fellowshipData.getList("FellowshipInvites", 10);
			for (int i = 0; i < fellowshipInvite.size(); i++) {
				CompoundNBT nbt = fellowshipInvite.getCompound(i);
				UUID fsID = ExtendedNBTUtils.getUUID(nbt, "ID", null);
				if (fsID != null) {
					UUID inviterID = ExtendedNBTUtils.getUUID(nbt, "InviterID", null);
					invites.add(new ExtendedFellowshipInvite(fsID, inviterID));
				}
			}
		}else {
			// Handle pre-1.10.0 fellowships
			fellowshipIDs.clear();
			invites.clear();
			ListNBT fellowships = playerNBT.getList("Fellowships", 10);
			for (int i = 0; i < fellowships.size(); i++) {
				CompoundNBT nbt = fellowships.getCompound(i);
				// Perform load uuid from string
				UUID fsID = UUID.fromString(nbt.getString("ID"));
				if (fsID != null)
					fellowshipIDs.add(fsID);
			}
			// Handle 1.0->1.9.3 fellowships having broken data
			List<UUID> fellowshipIDTemp = new ArrayList<>(fellowshipIDs);
			for (UUID id : fellowshipIDTemp) {
				ExtendedFellowship fellowship =ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), id);
				if (fellowship == null || !fellowship.isInFellowship(getPlayerUUID())) {
					fellowshipIDs.remove(id);
				}
			}
		}
	}

	public List<UUID> getFellowshipIDs() {
		return fellowshipIDs;
	}

	public List<ExtendedFellowship> getFellowships(World level) {
		if(!(level instanceof ServerWorld)) {
			return Collections.emptyList();
		}
		List<ExtendedFellowship> ships = new ArrayList<>();
		for (UUID ids : fellowshipIDs) {
			ExtendedFellowship fellowship = ExtendedLevelData.serverInstance().getFellowshipData().getFellowship((ServerWorld) level, ids);
			if (fellowship != null) {
				ships.add(fellowship);
			}
		}
		return ships;
	}

	public void addFellowship(ExtendedFellowship fellowship) {
		if (fellowship.isInFellowship(getPlayerUUID())) {
			UUID fsID = fellowship.getFellowshipId();
			if (!fellowshipIDs.contains(fsID)) {
				fellowshipIDs.add(fsID);
				markDirty();
			}
		}
	}

	public void removeFellowship(ExtendedFellowship fellowship) {
		if (!fellowship.isFellowshipOwner(getPlayerUUID()) || fellowship.isDisbanded()) {
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
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setIcon(itemstack);
	}

	public void setFellowshipPreventPVP(ExtendedFellowship fs, boolean prevent) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setPreventPVP(prevent);
	}

	public void setFellowshipColor(ExtendedFellowship fs, int color) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setFellowshipColor(color);
	}

	public void setFellowshipPreventHiredFF(ExtendedFellowship fs, boolean prevent) {
		if (fs.hasEditPowers(getPlayerUUID()))
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
			fs.createNotification("command.lotrextended.fellowship.client.admin.removed", owner, fs.getFellowshipName());
		}
	}

	public void addFellowshipAdmin(ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			fs.addFellowshipAdmin(player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("command.lotrextended.fellowship.client.admin.added", owner, fs.getFellowshipName());
		}
	}

	public void addPlayer(ServerWorld level, ExtendedFellowship fs, UUID player) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.addFellowshipMember(level, player);
	}

	public void addInvitePlayer(ExtendedFellowship fs, UUID playerSendingInvite) {
		// Check if currently has an invite
		for(ExtendedFellowshipInvite invite : invites) {
			if(invite.fellowshipID.equals(fs.getFellowshipId())) {
				return;
			}
		}
		// Check if currently in that fellowship
		for(UUID invite : fellowshipIDs) {
			if(invite.equals(fs.getFellowshipId())) {
				return;
			}
		}
		invites.add(new ExtendedFellowshipInvite(fs.getFellowshipId(), playerSendingInvite));
		markDirty();
		PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
		fs.createNotification("command.lotrextended.fellowship.client.invite.recieved", owner);
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
				fs.createNotification("command.lotrextended.fellowship.client.invite.rejected", player);
			}
		}
	}

	public void acceptInvitePlayer(ServerWorld level, ExtendedFellowship fs) {
		ExtendedFellowshipInvite existingInvite = null;
		for (ExtendedFellowshipInvite invite : invites) {
			if (invite.fellowshipID.equals(fs.getFellowshipId())) {
				existingInvite = invite;
				break;
			}
		}
		if(existingInvite!=null) {
			if (fs.isDisbanded()) {
				removeInvitePlayer(fs, false);
				return;
			}
			fs.addFellowshipMember(level, getPlayerUUID());
			if (!fellowshipIDs.contains(fs.getFellowshipId())) {
				fellowshipIDs.add(fs.getFellowshipId());
			}
			invites.remove(existingInvite);
			markDirty();
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			fs.createNotification("command.lotrextended.fellowship.client.invite.accepted", owner);
		}
	}

	public void setIcon(ExtendedFellowship fs, UUID player, ItemStack stack) {
		if (fs.hasEditPowers(getPlayerUUID()))
			fs.setIcon(stack);
	}

	public void removePlayer(ServerWorld level, ExtendedFellowship fs, UUID player) {
		if (fs.hasEditPowers(getPlayerUUID())) {
			fs.removeFellowshipMember(level, player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("command.lotrextended.fellowship.client.removed", owner, fs.getFellowshipName());
		}
	}

	public void setOwner(ServerWorld level, ExtendedFellowship fs, UUID player) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			fs.setOwnerUUID(level, player);
			PlayerEntity owner = getPlayerFromUUID(player);
			fs.createNotification("command.lotrextended.fellowship.client.transfered", owner, fs.getFellowshipName());
		}
	}

	public boolean createFellowship(ServerWorld level, String name, boolean adminOverride) {
		if (!ExtendedServerConfig.enableFellowshipCreation.get() && !adminOverride) {
			TextComponent comp = new TranslationTextComponent("gui.lotrextended.fellowships.creation.disabled");
			comp.withStyle(TextFormatting.YELLOW);
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			owner.sendMessage(comp, Util.NIL_UUID);
			return false;
		}

		if (!anyMatchingFellowshipNames(name)) {
			ExtendedFellowship fellowship = new ExtendedFellowship(name, getPlayerUUID());
			fellowship.createAndRegister(level);
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			fellowship.createNotification("command.lotrextended.fellowship.client.created", owner);
			return true;
		}
		return false;
	}

	public void leaveFellowship(ServerWorld level, ExtendedFellowship fs) {
		if (!fs.isFellowshipOwner(getPlayerUUID())) {
			fs.removeFellowshipMember(level, getPlayerUUID());
			if (fellowshipIDs.contains(fs.getFellowshipId()))
				removeFellowship(fs);
			PlayerEntity owner = getPlayerFromUUID(getPlayerUUID());
			fs.createNotification("command.lotrextended.fellowship.client.leave", owner);
		}
	}

	public boolean disbandFellowship(ServerWorld level, ExtendedFellowship fs) {
		if (fs.isFellowshipOwner(getPlayerUUID())) {
			for(UUID user : fs.getFellowshipMembers()) {
				PlayerEntity player = getPlayerFromUUID(user);
				if(player!=null) {
					fs.createNotification("command.lotrextended.fellowship.client.disbanded", player, fs.getFellowshipName());
				}
			}
			fs.disband(level);
			removeFellowship(fs);
			return true;
		}
		return false;
	}

	public boolean anyMatchingFellowshipNames(String name) {
		if (getFellowshipByName(name) != null) {
			return true;
		}
		return false;
	}

	/** 
	 * Warning! Only returns first match, not necessarily the correct one if in multiple fellowships with the same name
	 * @param fsName The fellowship name to search for
	 * @return The first matching fellowship by name
	 */
	public ExtendedFellowship getFellowshipByName(String fsName) {
		for (UUID fsID : fellowshipIDs) {
			ExtendedFellowship fs = ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}

	public ExtendedFellowship getFellowshipByID(UUID id) {
		if(fellowshipIDs.contains(id)) {
			return ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), id);
		}
		return null;
	}

	public ExtendedFellowship getFellowshipByInviteName(String fsName) {
		for (ExtendedFellowshipInvite fsID : invites) {
			ExtendedFellowship fs = ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID.fellowshipID);
			if (fs != null && fs.getFellowshipName().equalsIgnoreCase(fsName))
				return fs;
		}
		return null;
	}

	public List<String> listAllFellowshipNames() {
		List<String> list = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			ExtendedFellowship fs = ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID);
			if (fs != null && fs.isInFellowship(getPlayerUUID()))
				list.add(fs.getFellowshipName());
		}
		return list;
	}

	public List<String> listAllInviteNames() {
		List<String> list = new ArrayList<>();
		for (ExtendedFellowshipInvite fsID : invites) {
			ExtendedFellowship fs = ExtendedLevelData.serverInstance().getFellowshipData().getFellowship(ServerLifecycleHooks.getCurrentServer().overworld(), fsID.fellowshipID);
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