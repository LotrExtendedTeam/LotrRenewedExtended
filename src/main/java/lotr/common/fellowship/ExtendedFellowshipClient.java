package lotr.common.fellowship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.item.ItemStack;

public class ExtendedFellowshipClient {
	private final UUID FellowshipId;
	private String FellowshipName;
	private UUID FellowshipOwner;
	private Set<UUID> FellowshipMembers = new HashSet<>();
	private Set<UUID> FellowshipAdmins = new HashSet<>();
	private boolean PreventHiredFF;
	private boolean PreventPVP;
	private boolean ShowMap;
	private ItemStack icon;
	private boolean isOwned;
	private boolean isAdminned;
	private Map<UUID, String> usernameMap = new HashMap<>();

	public ExtendedFellowshipClient(UUID id, String name, boolean owned,
		boolean admin, GameProfile owner, List<GameProfile> members) {
		this.FellowshipId = id;
		this.FellowshipName = name;
		this.isOwned = owned;
		this.isAdminned = admin;
		this.FellowshipOwner = owner.getId();
		this.usernameMap.put(this.FellowshipOwner, owner.getName());
		for (GameProfile member : members) {
			this.FellowshipMembers.add(member.getId());
			this.usernameMap.put(member.getId(), member.getName());
		}
	}
	public void setOwner(GameProfile newOwner, boolean owned) {
		UUID prevOwnerUuid = this.FellowshipOwner;
		UUID newOwnerUuid = newOwner.getId();
		if (!prevOwnerUuid.equals(newOwnerUuid)) {
			this.FellowshipMembers.add(prevOwnerUuid);
			this.FellowshipOwner = newOwnerUuid;
			this.usernameMap.put(this.FellowshipOwner, newOwner.getName());
			this.FellowshipMembers.remove(newOwnerUuid);
			this.FellowshipAdmins.remove(newOwnerUuid);
			this.isOwned = owned;
			if (this.isOwned)
				this.isAdminned = false;
		}
	}
	public void addMember(GameProfile member) {
		UUID memberUuid = member.getId();
		if (!this.FellowshipMembers.contains(memberUuid)) {
			this.FellowshipMembers.add(memberUuid);
			this.usernameMap.put(memberUuid, member.getName());
		}
	}

	public void removeMember(GameProfile member) {
		UUID memberUuid = member.getId();
		if (this.FellowshipMembers.contains(memberUuid)) {
			this.FellowshipMembers.remove(memberUuid);
			this.usernameMap.remove(memberUuid);
			this.FellowshipAdmins.remove(memberUuid);
		}
	}

	public void setAdmins(Set<UUID> admins) {
		this.FellowshipAdmins = admins;
	}

	public void setAdmin(UUID playerUuid, boolean adminned) {
		if (!this.FellowshipAdmins.contains(playerUuid)) {
			this.FellowshipAdmins.add(playerUuid);
			this.isAdminned = adminned;
		}
	}

	public void removeAdmin(UUID playerUuid, boolean adminned) {
		if (this.FellowshipAdmins.contains(playerUuid)) {
			this.FellowshipAdmins.remove(playerUuid);
			this.isAdminned = adminned;
		}
	}

	public String getFellowshipName() {
		return FellowshipName;
	}
	public void setFellowshipName(String fellowshipName) {
		FellowshipName = fellowshipName;
	}
	public boolean isPreventHiredFF() {
		return PreventHiredFF;
	}
	public void setPreventHiredFF(boolean preventHiredFF) {
		PreventHiredFF = preventHiredFF;
	}
	public boolean isPreventPVP() {
		return PreventPVP;
	}
	public void setPreventPVP(boolean preventPVP) {
		PreventPVP = preventPVP;
	}
	public boolean isShowMap() {
		return ShowMap;
	}
	public void setShowMap(boolean showMap) {
		ShowMap = showMap;
	}
	public ItemStack getIcon() {
		return icon;
	}
	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	public UUID getFellowshipId() {
		return FellowshipId;
	}
	public UUID getFellowshipOwner() {
		return FellowshipOwner;
	}
	public Set<UUID> getFellowshipMembers() {
		return FellowshipMembers;
	}
	public Set<UUID> getFellowshipAdmins() {
		return FellowshipAdmins;
	}
	public boolean isOwned() {
		return isOwned;
	}
	public boolean isAdminned() {
		return isAdminned;
	}

}
