package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

import lotr.common.util.client.ExtendedClientsideSeperatedScreenHandler;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ExtendedSBannerOpenScreenPacket {
	private List<BannerPermission> defaultPerms;
	private float alignmentLevel;
	private List<ProtectionEntry> whitelist;
	private boolean canSelfProtect;

	public ExtendedSBannerOpenScreenPacket() {}

	public ExtendedSBannerOpenScreenPacket(ExtendedMEBannerTileEntity bannerEntity) {
		this.defaultPerms = bannerEntity.getDefaultPerms();
		this.alignmentLevel = bannerEntity.getAlignmentLevel();
		this.whitelist = bannerEntity.getProtectionWhitelist();
		this.canSelfProtect = bannerEntity.isCanProtectSelf();
		if(!protectionWhitelist.isEmpty()) {
			ListNBT whitelist = new ListNBT();
			for(ProtectionEntry protectionWWhitelist : protectionWhitelist) {
				whitelist.add(protectionWWhitelist.save());
			}
			pCompound.put("whitelistEntrys", pCompound);
		}
		if (!defaultPerms.isEmpty()) {
			// Initialize a byte array to store the ordinal values of BannerPermission
			byte[] ordinalValues = new byte[defaultPerms.size()];
			// Iterate over the entryPerms list to populate the byte array
			for (int i = 0; i < defaultPerms.size(); i++) {
				// Get the ordinal of the current BannerPermission enum and cast it to byte
				ordinalValues[i] = (byte) defaultPerms.get(i).ordinal();
			}
			// Store the byte array in the CompoundNBT with the key "perms"
			pCompound.putByteArray("defaultPerms", ordinalValues);
		}
	}

	public static void encode(ExtendedSBannerOpenScreenPacket packet, PacketBuffer buf) {
		buf.writeVarInt(packet.defaultPerms.size());
		for(BannerPermission perm : packet.defaultPerms) {
			buf.writeByte((byte) perm.ordinal());
		}
		buf.writeFloat(packet.alignmentLevel);
		buf.writeVarInt(packet.whitelist.size());
		for(ProtectionEntry entry : packet.whitelist) {
			buf.writeVarInt(entry.getEntryPerms().size());
			for(BannerPermission perm : entry.getEntryPerms()) {
				buf.writeByte((byte) perm.ordinal());
			}
			buf.writeUUID(entry.getProfile().getId());
		}
		buf.writeBoolean(packet.canSelfProtect);
	}

	public static ExtendedSBannerOpenScreenPacket decode(PacketBuffer buf) {
		ExtendedSBannerOpenScreenPacket packet = new ExtendedSBannerOpenScreenPacket();
		int defaultPermsSize = buf.readVarInt();
		packet.defaultPerms = new ArrayList<>();
		for(int i = 0; i<defaultPermsSize; i++) {
			packet.defaultPerms.add(BannerPermission.values[buf.readByte()]);
		}
		packet.alignmentLevel = buf.readFloat();
		packet.whitelist = new ArrayList<>();
		int whitelistSize = buf.readVarInt();
		for(int i = 0; i<whitelistSize; i++) {
			int permListSize = buf.readVarInt();
			List<BannerPermission> profilePerms = new ArrayList<>();
			for(int j = 0; j<permListSize; j++) {
				profilePerms.add(BannerPermission.values[buf.readByte()]);
			}
			UUID id = buf.readUUID();
			String name = buf.readUtf(32767);
		}
		packet.canSelfProtect = buf.readBoolean();
		return packet;
	}

	public static void handle(ExtendedSBannerOpenScreenPacket pPacket, Supplier<NetworkEvent.Context> context) {
		ExtendedClientsideSeperatedScreenHandler.openBannerGUI(pPacket);
		context.get().setPacketHandled(true);
	}
}