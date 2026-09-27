package com.github.maximuslotro.lotrrextended.client.gui.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerScreenPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

import net.minecraft.util.math.MathHelper;

public class ExtendedBannerConfigData {
	public List<ExtendedBannerPermissions> globalPerms;
	public List<ExtendedBannerPermissions> factionPerms;
	public float alignmentLevel;
	public List<ProtectionEntry> whitelist;
	public boolean canSelfProtect;
	// 0 Faction, 1 Player, 2 None, 3 Server
	public int protectionType;
	public String protMessage;

	public ExtendedBannerConfigData(ExtendedSBannerScreenPacket packet) {
		this.globalPerms = new ArrayList<>(packet.globalPerms);
		this.factionPerms = new ArrayList<>(packet.factionPerms);
		this.alignmentLevel = MathHelper.clamp(Math.round(packet.alignmentLevel * 10) / 10f, 0f, 10000f);
		// Deep copy using the new copy constructor
	    this.whitelist = new ArrayList<>();
	    for (ProtectionEntry entry : packet.whitelist) {
	        this.whitelist.add(new ProtectionEntry(entry));
	    }
		this.canSelfProtect = packet.canSelfProtect;
		this.protectionType = packet.protectionType;
		this.protMessage = packet.protMessage;
	}

	public boolean differsFrom(ExtendedBannerConfigData other) {
		if(other == null) return true;
		return !Objects.equals(this.globalPerms, other.globalPerms) ||
			!Objects.equals(this.factionPerms, other.factionPerms) ||
			Float.compare(this.alignmentLevel, other.alignmentLevel) != 0 ||
			!isWhitelistEquivalent(this.whitelist, other.whitelist) ||
			this.canSelfProtect != other.canSelfProtect ||
			this.protectionType != other.protectionType ||
			!this.protMessage.equals(other.protMessage);
	}

	private boolean isWhitelistEquivalent(List<ProtectionEntry> list1, List<ProtectionEntry> list2) {
		if(list1 == list2) return true;
		if(list1 == null || list2 == null) return false;
		if(list1.size() != list2.size()) return false;
		for(ProtectionEntry entry1 : list1) {
			boolean found = false;
			for(ProtectionEntry entry2 : list2) {
				if(entry1.equals(entry2)) {
					found = true;
					break;
				}
			}
			if (!found) return false;
		}
		return true;
	}
}