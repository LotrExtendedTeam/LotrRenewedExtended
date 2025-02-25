package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileBase;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileFellowship;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfilePlayer;
import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

import lotr.common.util.client.ExtendedClientsideSeparatedScreenHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSBannerOpenScreenPacket {
	public List<BannerPermission> defaultPerms;
	public float alignmentLevel;
	public List<ProtectionEntry> whitelist;
	public boolean canSelfProtect;
	public int protectionType;
	public BlockPos configurePos;
	public boolean enabled;

	public ExtendedSBannerOpenScreenPacket() {}

	public ExtendedSBannerOpenScreenPacket(boolean enabled) {
		this.defaultPerms = new ArrayList<>();
		this.alignmentLevel = 0;
		this.whitelist = new ArrayList<>();
		this.canSelfProtect = false;
		this.protectionType = 0;
		this.configurePos = new BlockPos(0,0,0);
		this.enabled=enabled;
	}

	public ExtendedSBannerOpenScreenPacket(ExtendedMEBannerTileEntity bannerEntity) {
		this.defaultPerms = bannerEntity.getDefaultPerms();
		this.alignmentLevel = bannerEntity.getAlignmentLevel();
		this.whitelist = bannerEntity.getProtectionWhitelist();
		this.canSelfProtect = bannerEntity.isCanProtectSelf();
		this.protectionType = bannerEntity.getProtectionType().getId();
		this.configurePos = bannerEntity.getBlockPos();
		this.enabled = true;
	}

	public static void encode(ExtendedSBannerOpenScreenPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.defaultPerms.size());
		for(BannerPermission perm : packet.defaultPerms) {
			buf.writeByte((byte) perm.ordinal());
		}
		buf.writeFloat(packet.alignmentLevel);
		buf.writeInt(packet.whitelist.size());
		for(ProtectionEntry entry : packet.whitelist) {
			buf.writeInt(entry.getEntryPerms().size());
			for(BannerPermission perm : entry.getEntryPerms()) {
				buf.writeByte((byte) perm.ordinal());
			}
			buf.writeUUID(entry.getProfile().getId());
			buf.writeUtf(entry.getProfile().getDisplayName(), 32767);
		}
		buf.writeBoolean(packet.canSelfProtect);
		buf.writeInt(packet.protectionType);
		buf.writeBlockPos(packet.configurePos);
		buf.writeBoolean(packet.enabled);
	}

	public static ExtendedSBannerOpenScreenPacket decode(PacketBuffer buf) {
		ExtendedSBannerOpenScreenPacket packet = new ExtendedSBannerOpenScreenPacket();
		int defaultPermsSize = buf.readInt();
		packet.defaultPerms = new ArrayList<>();
		for(int i = 0; i<defaultPermsSize; i++) {
			packet.defaultPerms.add(BannerPermission.values[buf.readByte()]);
		}
		packet.alignmentLevel = buf.readFloat();
		packet.whitelist = new ArrayList<>();
		int whitelistSize = buf.readInt();
		for(int i = 0; i<whitelistSize; i++) {
			int permListSize = buf.readInt();
			List<BannerPermission> profilePerms = new ArrayList<>();
			for(int j = 0; j<permListSize; j++) {
				profilePerms.add(BannerPermission.values[buf.readByte()]);
			}
			UUID id = buf.readUUID();
			String name = buf.readUtf(32767);
			ExtendedBannerProfileBase type;
			if(name.startsWith("f/")) {
				type = new ExtendedBannerProfileFellowship(name.substring(2), id);
			}else {
				type = new ExtendedBannerProfilePlayer(id, name);
			}
			ProtectionEntry entry = new ProtectionEntry(type);
			entry.setPerms(profilePerms);
			packet.whitelist.add(entry);
		}
		packet.canSelfProtect = buf.readBoolean();
		packet.protectionType = buf.readInt();
		packet.configurePos = buf.readBlockPos();
		packet.enabled = buf.readBoolean();
		return packet;
	}

	public static void handle(ExtendedSBannerOpenScreenPacket pPacket, Supplier<NetworkEvent.Context> context) {
		ExtendedClientsideSeparatedScreenHandler.openBannerGUI(pPacket);
		context.get().setPacketHandled(true);
	}
}