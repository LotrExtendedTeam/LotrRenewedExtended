package lotr.common.fellowship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedFellowship {
	private UUID FellowshipId;
	private String FellowshipName;
	private UUID FellowshipOwner;
	private Set<UUID> FellowshipMembers = new HashSet<UUID>();
	private Set<UUID> FellowshipAdmins = new HashSet<UUID>();
	private boolean PreventHiredFF = true;
	private boolean PreventPVP = true;
	private boolean ShowMap = true;
	private boolean needsSaving = false;
	private boolean disbanded;
	private ItemStack icon = ItemStack.EMPTY;
	
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
		ExtendedFellowshipHolderServer.addFellowship(this);
		((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(null, FellowshipOwner)).getFellowshipData().addFellowship(this);
		markDirty();
	}
	public void load(CompoundNBT pCompound) {
		disbanded = pCompound.getBoolean("Disbanded");
		if (pCompound.contains("Owner"))
			FellowshipOwner = UUID.fromString(pCompound.getString("Owner")); 
		FellowshipMembers.clear();
		FellowshipAdmins.clear();
		ListNBT memberTags = pCompound.getList("Members", 10);
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
		if (pCompound.contains("Icon")) {
			CompoundNBT item = pCompound.getCompound("Icon");
		      icon = ItemStack.of(item);
		}
		if (pCompound.contains("Name"))
			FellowshipName = pCompound.getString("Name");
		if (pCompound.contains("PreventPVP"))
			PreventPVP = pCompound.getBoolean("PreventPVP");
		if (pCompound.contains("PreventHiredFF"))
			PreventHiredFF = pCompound.getBoolean("PreventHiredFF");
		if (pCompound.contains("ShowMap"))
			ShowMap = pCompound.getBoolean("ShowMap");
	}

	public void save(CompoundNBT pCompound) {
		pCompound.putBoolean("Disbanded", disbanded);
		if(FellowshipOwner != null)
			pCompound.putString("Owner", FellowshipOwner.toString());
		ListNBT memberTags = new ListNBT();
	    for (UUID member : FellowshipMembers) {
	    	CompoundNBT nbt = new CompoundNBT();
	    	nbt.putString("Member", member.toString());
	    	if (FellowshipAdmins.contains(member))
	    		nbt.putBoolean("Admin", true);
	    	memberTags.add(nbt);
	    } 
	    pCompound.put("Members", memberTags);
	    if (icon != null) {
	    	CompoundNBT item = new CompoundNBT();
	    	item = icon.serializeNBT();
	    	pCompound.put("Icon", item);
	    } 
	    if (FellowshipName != null)
	    	pCompound.putString("Name", FellowshipName);
	    pCompound.putBoolean("PreventPVP", PreventPVP);
	    pCompound.putBoolean("PreventHiredFF", PreventHiredFF);
	    pCompound.putBoolean("ShowMap", ShowMap);
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
	public boolean isInFellowship(UUID player) {
		return (isFellowshipMember(player) || isFellowshipOwner(player));
	}
	public boolean hasEditPowers(UUID player) {
		return ((isFellowshipMember(player) && FellowshipAdmins.contains(player))||FellowshipOwner.equals(player));
	}
	public void setOwnerUUID(UUID ownerUUID) {
		UUID prevOwner = FellowshipOwner;
		if (prevOwner != null)
			if (!FellowshipMembers.contains(prevOwner))
				FellowshipMembers.add(prevOwner);
		FellowshipOwner = ownerUUID;
		if (FellowshipMembers.contains(ownerUUID))
			FellowshipMembers.remove(ownerUUID);
		if (FellowshipAdmins.contains(ownerUUID))
			FellowshipAdmins.remove(ownerUUID);
		((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(null, FellowshipOwner)).getFellowshipData().addFellowship(this);
		markDirty();
	}
	public void addFellowshipMember(UUID player) {
		if (!isFellowshipOwner(player) && !this.FellowshipMembers.contains(player)) {
			FellowshipMembers.add(player);
			((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(null, FellowshipOwner)).getFellowshipData().addFellowship(this);
			markDirty();
		}
	}
	public void removeFellowshipMember(UUID player) {
		if (FellowshipMembers.contains(player)) {
			FellowshipMembers.remove(player);
		if (FellowshipAdmins.contains(player))
			FellowshipAdmins.remove(player);
		((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(null, FellowshipOwner)).getFellowshipData().removeFellowship(this);
		markDirty();
		}
	}
	public Set<UUID> getFellowshipMembers() {
		return FellowshipMembers;
	}

	public Set<UUID> getAllFellowshipMembers() {
		Set<UUID> list = new HashSet<UUID>();
		list.add(FellowshipOwner);
		list.addAll(FellowshipMembers);
		return list;
	}
	public Set<UUID> getFellowshipAdmins() {
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
		disbanded = true;
		for (UUID player : new ArrayList<UUID>(FellowshipMembers))
	    	removeFellowshipMember(player);
		markDirty();
	}
	public boolean isRemoved() {
		return disbanded;
	}

	public void createNotification(String message, PlayerEntity reciever) {
		TextComponent comp = new TranslationTextComponent(message);
		comp.withStyle(TextFormatting.YELLOW);
		reciever.sendMessage(comp, Util.NIL_UUID);
	}

	public void createNotification(String message, PlayerEntity reciever, Object... args) {
		TextComponent comp = new TranslationTextComponent(message, args);
		comp.withStyle(TextFormatting.YELLOW);
		reciever.sendMessage(comp, Util.NIL_UUID);
	}
}
