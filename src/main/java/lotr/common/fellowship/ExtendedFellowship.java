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
import net.minecraft.util.text.*;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFellowship {
	private final UUID fellowshipId;
	private String fellowshipName;
	private UUID fellowshipOwner;
	private final Set<UUID> fellowshipMembers = new HashSet<>();
	private final Set<UUID> fellowshipAdmins = new HashSet<>();
	private boolean preventHiredFF = true;
	private boolean preventPVP = true;
	private boolean showMap = true;
	private boolean needsSaving = false;
	private int fellowshipColor = TextFormatting.YELLOW.getColor();
	private boolean disbanded;
	private ItemStack icon = ItemStack.EMPTY;

	public ExtendedFellowship(String publicName, UUID ownerUUID) {
		this.fellowshipId = UUID.randomUUID();
		this.fellowshipName = publicName;
		this.fellowshipOwner = ownerUUID;
	}
	public ExtendedFellowship(UUID ID) {
		fellowshipId = ID;
	}

	public void markDirty() {
		needsSaving = true;
	}
	public boolean needsSave() {
		return needsSaving;
	}
	public void createAndRegister(ServerWorld level) {
		ExtendedFellowshipHolderServer.addFellowship(this);
		((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(level, fellowshipOwner)).getFellowshipData().addFellowship(this);
		markDirty();
	}
	public void load(CompoundNBT pCompound) {
		disbanded = pCompound.getBoolean("Disbanded");
		if (pCompound.contains("Owner"))
			fellowshipOwner = UUID.fromString(pCompound.getString("Owner"));
		fellowshipMembers.clear();
		fellowshipAdmins.clear();
		ListNBT memberTags = pCompound.getList("Members", 10);
		for (int i = 0; i < memberTags.size(); i++) {
			CompoundNBT nbt = memberTags.getCompound(i);
			UUID member = UUID.fromString(nbt.getString("Member"));
			if (member != null) {
				fellowshipMembers.add(member);
				if (nbt.contains("Admin")) {
					boolean isAdmin = nbt.getBoolean("Admin");
					if (isAdmin)
						fellowshipAdmins.add(member);
				}
			}
		}
		if (pCompound.contains("Icon")) {
			CompoundNBT item = pCompound.getCompound("Icon");
			icon = ItemStack.of(item);
		}
		if (pCompound.contains("Name"))
			fellowshipName = pCompound.getString("Name");
		if (pCompound.contains("preventPVP"))
			preventPVP = pCompound.getBoolean("preventPVP");
		if (pCompound.contains("fellowshipColor")) {
			int rgb = pCompound.getInt("fellowshipColor");
			fellowshipColor = rgb;
		}
		if (pCompound.contains("preventHiredFF"))
			preventHiredFF = pCompound.getBoolean("preventHiredFF");
		if (pCompound.contains("showMap"))
			showMap = pCompound.getBoolean("showMap");
	}

	public void save(CompoundNBT pCompound) {
		pCompound.putBoolean("Disbanded", disbanded);
		if(fellowshipOwner != null)
			pCompound.putString("Owner", fellowshipOwner.toString());
		ListNBT memberTags = new ListNBT();
		for (UUID member : fellowshipMembers) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("Member", member.toString());
			if (fellowshipAdmins.contains(member))
				nbt.putBoolean("Admin", true);
			memberTags.add(nbt);
		}
		pCompound.put("Members", memberTags);
		if (icon != null) {
			CompoundNBT item = new CompoundNBT();
			item = icon.serializeNBT();
			pCompound.put("Icon", item);
		}
		if (fellowshipName != null)
			pCompound.putString("Name", fellowshipName);
		pCompound.putBoolean("preventPVP", preventPVP);
		if (fellowshipColor != 0) {
			pCompound.putInt("fellowshipColor", fellowshipColor);
		}
		pCompound.putBoolean("preventHiredFF", preventHiredFF);
		pCompound.putBoolean("showMap", showMap);
		needsSaving = false;
	}
	public String getFellowshipName() {
		return fellowshipName;
	}
	public void setFellowshipName(String name) {
		fellowshipName = name;
		markDirty();
	}
	public UUID getFellowshipId() {
		return fellowshipId;
	}
	public UUID getOwnerUUID() {
		return fellowshipOwner;
	}

	/**
	 * Check if a player is the fellowship owner
	 * @param player A player UUID to check
	 * @return True if the player is the fellowship owner
	 */
	public boolean isFellowshipOwner(UUID player) {
		return fellowshipOwner.equals(player);
	}

	/**
	 * Check if a player is a fellowship member, but the owner
	 * @param player A player UUID to check
	 * @return True if the player is a fellowship member
	 */
	public boolean isFellowshipMember(UUID player) {
		return fellowshipMembers.contains(player);
	}

	/**
	 * Check if a player is a fellowship member and in the admin list, but not the owner
	 * @param player A player UUID to check
	 * @return True if the player is the fellowship admin, but not owner
	 */
	public boolean isFellowshipAdmin(UUID player) {
		return isFellowshipMember(player) && fellowshipAdmins.contains(player);
	}

	/**
	 * Check if a player is a fellowship member or the owner
	 * @param player A player UUID to check
	 * @return True if the player is in the fellowship
	 */
	public boolean isInFellowship(UUID player) {
		return isFellowshipMember(player) || isFellowshipOwner(player);
	}

	/**
	 * Check if a player is a fellowship admin or the owner
	 * @param player A player UUID to check
	 * @return True if the player has powers to edit the fellowship
	 */
	public boolean hasEditPowers(UUID player) {
		return isFellowshipAdmin(player) || isFellowshipOwner(player);
	}
	public void setOwnerUUID(ServerWorld level, UUID newUUID) {
		UUID prevOwner = fellowshipOwner;
		if (prevOwner != null)
			fellowshipMembers.add(prevOwner);
		fellowshipOwner = newUUID;
		fellowshipMembers.remove(newUUID);
		fellowshipAdmins.remove(newUUID);
		((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(level, newUUID)).getFellowshipData().addFellowship(this);
		markDirty();
	}
	public void addFellowshipMember(ServerWorld level, UUID player) {
		if (!isFellowshipOwner(player) && !this.fellowshipMembers.contains(player)) {
			fellowshipMembers.add(player);
			((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(level, player)).getFellowshipData().addFellowship(this);
			markDirty();
		}
	}
	public void removeFellowshipMember(ServerWorld level, UUID player) {
		if (fellowshipMembers.contains(player)) {
			fellowshipMembers.remove(player);
			fellowshipAdmins.remove(player);
			((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(level, player)).getFellowshipData().removeFellowship(this);
			markDirty();
		}
	}
	public Set<UUID> getFellowshipMembers() {
		return fellowshipMembers;
	}

	public Set<UUID> getAllFellowshipMembers() {
		Set<UUID> list = new HashSet<>();
		list.add(fellowshipOwner);
		list.addAll(fellowshipMembers);
		return list;
	}
	public Set<UUID> getFellowshipAdmins() {
		return fellowshipAdmins;
	}
	public void addFellowshipAdmin(UUID player) {
		if (fellowshipMembers.contains(player)) {
			if(!fellowshipAdmins.contains(player)) {
				fellowshipAdmins.add(player);
				markDirty();
			}
		}
	}
	public void removeFellowshipAdmin(UUID player) {
		if (fellowshipMembers.contains(player)) {
			if(fellowshipAdmins.contains(player)) {
				fellowshipAdmins.remove(player);
				markDirty();
			}
		}
	}
	public boolean isPreventPVP() {
		return preventPVP;
	}
	public void setPreventPVP(boolean allowPVP) {
		preventPVP = allowPVP;
		markDirty();
	}
	public boolean isPreventHiredFF() {
		return preventHiredFF;
	}
	public void setPreventHiredFF(boolean allowUnitPVP) {
		preventHiredFF = allowUnitPVP;
		markDirty();
	}
	public boolean isHideMapLocation() {
		return showMap;
	}
	public void setHideMapLocation(boolean hideMapLocation) {
		showMap = hideMapLocation;
		markDirty();
	}
	public ItemStack getIcon() {
		return icon;
	}
	public void setIcon(ItemStack itemstack) {
		icon = itemstack;
		markDirty();
	}
	public void disband(ServerWorld level) {
		disbanded = true;
		for (UUID player : new ArrayList<>(fellowshipMembers))
			removeFellowshipMember(level, player);
		markDirty();
	}
	public boolean isDisbanded() {
		return disbanded;
	}

	public void createNotification(String message, PlayerEntity reciever) {
		TextComponent comp = new TranslationTextComponent(message);
		comp.withStyle(TextFormatting.YELLOW);
		sendMessage(reciever, comp);
	}

	public void createNotification(String message, PlayerEntity reciever, Object... args) {
		TextComponent comp = new TranslationTextComponent(message, args);
		comp.withStyle(TextFormatting.YELLOW);
		sendMessage(reciever, comp);
	}

	private void sendMessage(PlayerEntity reciever, ITextComponent pComponent){
		if(reciever!=null) {
			reciever.sendMessage(pComponent, Util.NIL_UUID);
		}
	}

	public int getFellowshipColor() {
		return fellowshipColor;
	}

	public void setFellowshipColor(int fellowshipColor) {
		this.fellowshipColor = fellowshipColor;
	}
}
