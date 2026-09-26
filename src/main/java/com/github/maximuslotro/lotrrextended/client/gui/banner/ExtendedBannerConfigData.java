package com.github.maximuslotro.lotrrextended.client.gui.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenChangedScreenPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenScreenPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

public class ExtendedBannerConfigData {
    public List<ExtendedBannerPermissions> globalPerms;
    public List<ExtendedBannerPermissions> factionPerms;
    public float alignmentLevel;
    public List<ProtectionEntry> whitelist;
    public boolean canSelfProtect;
	// 0 Faction, 1 Player, 2 None, 3 Server
    public int protectionType;
    public String protMessage;

    // Constructor from initial open packet
    public ExtendedBannerConfigData(ExtendedSBannerOpenScreenPacket packet) {
        this.globalPerms = new ArrayList<>(packet.globalPerms);
        this.factionPerms = new ArrayList<>(packet.factionPerms);
        this.alignmentLevel = Math.round(packet.alignmentLevel * 10) / 10f;
        this.whitelist = new ArrayList<>(packet.whitelist);
        this.canSelfProtect = packet.canSelfProtect;
        this.protectionType = packet.protectionType;
        this.protMessage = packet.protMessage;
    }

    // Constructor from sync/update packet
    public ExtendedBannerConfigData(ExtendedSBannerOpenChangedScreenPacket packet) {
        this.globalPerms = new ArrayList<>(packet.globalPerms);
        this.factionPerms = new ArrayList<>(packet.factionPerms);
        this.alignmentLevel = Math.round(packet.alignmentLevel * 10) / 10f;
        this.whitelist = new ArrayList<>(packet.whitelist);
        this.canSelfProtect = packet.canSelfProtect;
        this.protectionType = packet.protectionType;
        this.protMessage = packet.protMessage;
    }

    public boolean differsFrom(ExtendedBannerConfigData other) {
        if (other == null) return true;
        return !Objects.equals(this.globalPerms, other.globalPerms) ||
               !Objects.equals(this.factionPerms, other.factionPerms) ||
               Float.compare(this.alignmentLevel, other.alignmentLevel) != 0 ||
               !Objects.equals(this.whitelist, other.whitelist) ||
               this.canSelfProtect != other.canSelfProtect ||
               this.protectionType != other.protectionType ||
               !this.protMessage.equals(other.protMessage);
    }
}