package lotr.common.command.fellowship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class Fellowship {
	private String publicName;
	private UUID privatename;
	private UUID ownerUUID;
	private List<UUID> membersUUID = new ArrayList<UUID>();
	private List<UUID> accessUUID = new ArrayList<UUID>();
	private boolean allowPVP = false;
	private boolean allowUnitPVP = false;
	private boolean hideMapLocation = false;
	private boolean needsSave = false;
	public Fellowship(String publicName, UUID ownerUUID) {
		this.privatename = UUID.randomUUID();
		this.publicName = publicName;
		this.ownerUUID = ownerUUID;
	}

	  public Fellowship(UUID ID) {
		  privatename = ID;
	}

	public void markDirty() {
		    this.needsSave = true;
		  }
		  
		  public boolean needsSave() {
		    return this.needsSave;
		  }
		  
	public void load(CompoundNBT arg0) {
		if (arg0.contains("Owner"))
		      this.ownerUUID = UUID.fromString(arg0.getString("Owner")); 
		    membersUUID.clear();
		    accessUUID.clear();
		    ListNBT memberTags = arg0.getList("Members", 10);
		    for (int i = 0; i < memberTags.size(); i++) {
		      CompoundNBT nbt = memberTags.getCompound(i);
		      UUID member = UUID.fromString(nbt.getString("Member"));
		      if (member != null) {
		        membersUUID.add(member);
		        if (nbt.contains("Admin")) {
		          boolean isAdmin = nbt.getBoolean("Admin");
		          if (isAdmin)
		        	  accessUUID.add(member); 
		        } 
		      } 
		    } 
		    if (arg0.contains("Name"))
		    	publicName = arg0.getString("Name"); 
		    if (arg0.contains("AllowPVP"))
		      allowPVP = arg0.getBoolean("AllowPVP"); 
		    if (arg0.contains("AllowUnitPVP"))
		      allowUnitPVP = arg0.getBoolean("AllowUnitPVP"); 
		    if (arg0.contains("HideMap"))
		      hideMapLocation = arg0.getBoolean("HideMap"); 
	}

	public void save(CompoundNBT arg0) {
		if(this.ownerUUID != null)
			arg0.putString("Owner", ownerUUID.toString());
		ListNBT memberTags = new ListNBT();
	    for (UUID member : this.membersUUID) {
	      CompoundNBT nbt = new CompoundNBT();
	      nbt.putString("Member", member.toString());
	      if (this.accessUUID.contains(member))
	        nbt.putBoolean("Admin", true); 
	      memberTags.add(nbt);
	    } 
	    arg0.put("Members", memberTags);
	    if (this.publicName != null)
	    	arg0.putString("Name", this.publicName);
	    arg0.putBoolean("AllowPVP", this.allowPVP);
	    arg0.putBoolean("AllowUnitPVP", this.allowUnitPVP);
	    arg0.putBoolean("HideMap", this.hideMapLocation);
	    this.needsSave = false;
	}
	public String getPublicName() {
		return publicName;
	}
	public void setPublicName(String publicName) {
		this.publicName = publicName;
		markDirty();
	}
	public UUID getID() {
		return privatename;
	}
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	public boolean isOwner(UUID player) {
		return ownerUUID.equals(player);
	}

	public void setOwnerUUID(UUID ownerUUID) {
		UUID prevOwner = this.ownerUUID;
		if (prevOwner != null)
			if (!this.membersUUID.contains(prevOwner))
				this.membersUUID.add(0, prevOwner);
		this.ownerUUID = ownerUUID;
		if (this.membersUUID.contains(ownerUUID))
			this.membersUUID.remove(ownerUUID);
		if (this.accessUUID.contains(ownerUUID))
			this.accessUUID.remove(ownerUUID);
		markDirty();
	}
	public List<UUID> getMembersUUID() {
		return membersUUID;
	}

	public List<UUID> getAccessUUID() {
		return accessUUID;
	}

	public boolean isAllowPVP() {
		return allowPVP;
	}
	public void setAllowPVP(boolean allowPVP) {
		this.allowPVP = allowPVP;
		markDirty();
	}
	public boolean isAllowUnitPVP() {
		return allowUnitPVP;
	}
	public void setAllowUnitPVP(boolean allowUnitPVP) {
		this.allowUnitPVP = allowUnitPVP;
		markDirty();
	}
	public boolean isHideMapLocation() {
		return hideMapLocation;
	}
	public void setHideMapLocation(boolean hideMapLocation) {
		this.hideMapLocation = hideMapLocation;
		markDirty();
	}
}
