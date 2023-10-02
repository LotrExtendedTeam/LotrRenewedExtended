package lotr.common.fellowship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotroExtended.LOTRHooks;

import lotr.common.data.LOTRLevelData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class ExtendedFellowship {
	private UUID FellowshipId;
	private String FellowshipName;
	private UUID FellowshipOwner;
	private List<UUID> FellowshipMembers = new ArrayList<UUID>();
	private List<UUID> FellowshipAdmins = new ArrayList<UUID>();
	private boolean PreventHiredFF = true;
	private boolean PreventPVP = true;
	private boolean ShowMap = true;
	private boolean needsSaving = false;
	private boolean removed;
	private ItemStack icon;
	
	public ExtendedFellowship(String publicName, UUID ownerUUID) {
		this.FellowshipId = UUID.randomUUID();
		this.FellowshipName = publicName;
		this.FellowshipOwner = ownerUUID;
	}
	public ExtendedFellowship(UUID ID) {
		FellowshipId = ID;
	}

	public void markDirty() {
		needsSaving = true;
	}
	public boolean needsSave() {
		return needsSaving;
	}
	  public void createAndRegister() {
		    FellowshipHolderServer.addFellowship(this);
		    LOTRHooks.getFellowshipData(LOTRLevelData.serverInstance().getData(null, FellowshipOwner)).addFellowship(this);
		    markDirty();
		  }
	public void load(CompoundNBT arg0) {
		removed = arg0.getBoolean("Disbanded");
		if (arg0.contains("Owner"))
			FellowshipOwner = UUID.fromString(arg0.getString("Owner")); 
		FellowshipMembers.clear();
		FellowshipAdmins.clear();
		ListNBT memberTags = arg0.getList("Members", 10);
		for (int i = 0; i < memberTags.size(); i++) {
			CompoundNBT nbt = memberTags.getCompound(i);
			UUID member = UUID.fromString(nbt.getString("Member"));
			if (member != null) {
				FellowshipMembers.add(member);
				if (nbt.contains("Admin")) {
					boolean isAdmin = nbt.getBoolean("Admin");
					if (isAdmin)
						FellowshipAdmins.add(member); 
				}
			}
		}
		if (arg0.contains("Icon")) {
			CompoundNBT item = arg0.getCompound("Icon");
		      icon = ItemStack.of(item);
		}
		if (arg0.contains("Name"))
			FellowshipName = arg0.getString("Name");
		if (arg0.contains("PreventPVP"))
			PreventPVP = arg0.getBoolean("PreventPVP");
		if (arg0.contains("PreventHiredFF"))
			PreventHiredFF = arg0.getBoolean("PreventHiredFF");
		if (arg0.contains("ShowMap"))
			ShowMap = arg0.getBoolean("ShowMap");
	}

	public void save(CompoundNBT arg0) {
		arg0.putBoolean("Disbanded", removed);
		if(FellowshipOwner != null)
			arg0.putString("Owner", FellowshipOwner.toString());
		ListNBT memberTags = new ListNBT();
	    for (UUID member : FellowshipMembers) {
	    	CompoundNBT nbt = new CompoundNBT();
	    	nbt.putString("Member", member.toString());
	    	if (FellowshipAdmins.contains(member))
	    		nbt.putBoolean("Admin", true);
	    	memberTags.add(nbt);
	    } 
	    arg0.put("Members", memberTags);
	    if (icon != null) {
	    	CompoundNBT item = new CompoundNBT();
	    	item = icon.serializeNBT();
	    	arg0.put("Icon", item);
	    } 
	    if (FellowshipName != null)
	    	arg0.putString("Name", FellowshipName);
	    arg0.putBoolean("PreventPVP", PreventPVP);
	    arg0.putBoolean("PreventHiredFF", PreventHiredFF);
	    arg0.putBoolean("ShowMap", ShowMap);
	    needsSaving = false;
	}
	public String getFellowshipName() {
		return FellowshipName;
	}
	public void setFellowshipName(String name) {
		FellowshipName = name;
		markDirty();
	}
	public UUID getFellowshipId() {
		return FellowshipId;
	}
	public UUID getOwnerUUID() {
		return FellowshipOwner;
	}
	public boolean isFellowshipOwner(UUID player) {
		return FellowshipOwner.equals(player);
	}
	public boolean isFellowshipMember(UUID player) {
		return FellowshipMembers.contains(player);
	}
	public boolean isFellowshipAdmin(UUID player) {
		return (isFellowshipMember(player) && FellowshipAdmins.contains(player));
	}
	public boolean hasEditPowers(UUID player) {
		return ((isFellowshipMember(player) && FellowshipAdmins.contains(player))||FellowshipOwner.equals(player));
	}
	public void setOwnerUUID(UUID ownerUUID) {
		UUID prevOwner = FellowshipOwner;
		if (prevOwner != null)
			if (!FellowshipMembers.contains(prevOwner))
				FellowshipMembers.add(0, prevOwner);
		FellowshipOwner = ownerUUID;
		if (FellowshipMembers.contains(ownerUUID))
			FellowshipMembers.remove(ownerUUID);
		if (FellowshipAdmins.contains(ownerUUID))
			FellowshipAdmins.remove(ownerUUID);
		LOTRHooks.getFellowshipData(LOTRLevelData.serverInstance().getData(null, ownerUUID)).addFellowship(this);
		markDirty();
	}
	public void addFellowshipMember(UUID player) {
		if (!isFellowshipOwner(player) && !this.FellowshipMembers.contains(player)) {
			FellowshipMembers.add(player);
			LOTRHooks.getFellowshipData(LOTRLevelData.serverInstance().getData(null, player)).addFellowship(this);
			markDirty();
		}
	}
	public void removeFellowshipMember(UUID player) {
		if (FellowshipMembers.contains(player)) {
			FellowshipMembers.remove(player);
		if (FellowshipAdmins.contains(player))
			FellowshipAdmins.remove(player);
		LOTRHooks.getFellowshipData(LOTRLevelData.serverInstance().getData(null, player)).removeFellowship(this);
		markDirty();
		}
	}
	public List<UUID> getFellowshipMembers() {
		return FellowshipMembers;
	}

	public List<UUID> getAllFellowshipMembers() {
		List<UUID> list = new ArrayList<UUID>();
		list.add(FellowshipOwner);
		list.addAll(FellowshipMembers);
		return list;
	}
	public List<UUID> getFellowshipAdmins() {
		return FellowshipAdmins;
	}
	public void addFellowshipAdmin(UUID player) {
		if (FellowshipMembers.contains(player)) {
			if(!FellowshipAdmins.contains(player)) {
				FellowshipAdmins.add(player);
				markDirty();
			}
		}
	}
	public void removeFellowshipAdmin(UUID player) {
		if (FellowshipMembers.contains(player)) {
			if(FellowshipAdmins.contains(player)) {
				FellowshipAdmins.remove(player);
				markDirty();
			}
		}
	}
	public boolean isPreventPVP() {
		return PreventPVP;
	}
	public void setPreventPVP(boolean allowPVP) {
		PreventPVP = allowPVP;
		markDirty();
	}
	public boolean isPreventHiredFF() {
		return PreventHiredFF;
	}
	public void setPreventHiredFF(boolean allowUnitPVP) {
		PreventHiredFF = allowUnitPVP;
		markDirty();
	}
	public boolean isHideMapLocation() {
		return ShowMap;
	}
	public void setHideMapLocation(boolean hideMapLocation) {
		ShowMap = hideMapLocation;
		markDirty();
	}
	public ItemStack getIcon() {
		return icon;
	}
	public void setIcon(ItemStack itemstack) {
		icon = itemstack;
		markDirty();
	}
	public void disband() {
		removed = true;
		markDirty();
		for (UUID player : FellowshipMembers)
	    	removeFellowshipMember(player);
	}
	public boolean isRemoved() {
		return removed;
	}
}
