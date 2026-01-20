package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileBase;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileFellowship;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfilePlayer;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;
import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * Server -> Client packet used to open and populate the Banner configuration screen.
 *
 * <p>This packet sends all banner settings, permissions, whitelist data,
 * protection type, and client OP status so the GUI can be rendered correctly.</p>
 */
public class ExtendedSBannerOpenScreenPacket {
	/** Default permissions applied under the current protection mode. */
    public List<ExtendedBannerPermissions> defaultPerms;
    /** Alignment level used for faction-based protection logic. */
    public float alignmentLevel;
    /** Whitelist entries defining special access rules for players/factions. */
    public List<ProtectionEntry> whitelist;
    /** Whether the banner protects itself from unauthorized destruction. */
    public boolean canSelfProtect;
    /** Numeric ID of the banner protection owner type. */
    public int protectionType;
    /** Position of the banner tile entity being configured. */
    public BlockPos configurePos;
    /** Whether banner protection is enabled for this banner. */
    public boolean enabled;
    /** Whether the receiving client has OP-level permissions. */
    public boolean isClientOP;
    /**
     * Empty constructor used during packet decoding.
     */
	public ExtendedSBannerOpenScreenPacket() {}
	/**
     * Creates a packet indicating that the banner screen should open,
     * but with no banner data (used when protection is disabled).
     *
     * @param enabled whether banner protection is enabled
     */
	public ExtendedSBannerOpenScreenPacket(boolean enabled) {
		this.defaultPerms = new ArrayList<>();
		this.alignmentLevel = 0;
		this.whitelist = new ArrayList<>();
		this.canSelfProtect = false;
		this.protectionType = 0;
		this.configurePos = new BlockPos(0,0,0);
		this.enabled=enabled;
		this.isClientOP = false;
	}
	/**
     * Creates a packet populated from a banner tile entity.
     *
     * @param bannerEntity the banner tile entity being configured
     * @param pIsClientOP  whether the receiving client is an OP
     */
	public ExtendedSBannerOpenScreenPacket(ExtendedMEBannerTileEntity bannerEntity, boolean pIsClientOP) {
		this.defaultPerms = bannerEntity.getDefaultPerms();
		this.alignmentLevel = bannerEntity.getAlignmentLevel();
		this.whitelist = bannerEntity.getProtectionWhitelist();
		this.canSelfProtect = bannerEntity.isCanProtectSelf();
		this.protectionType = bannerEntity.getProtectionType().getId();
		this.configurePos = bannerEntity.getBlockPos();
		this.enabled = true;
		this.isClientOP = pIsClientOP;
	}
	/**
     * Encodes this packet into a {@link PacketBuffer} for transmission to the client.
     *
     * @param packet the packet instance to encode
     * @param buf    the buffer to write to
     */
	public static void encode(ExtendedSBannerOpenScreenPacket packet, PacketBuffer buf) {
		buf.writeInt(packet.defaultPerms.size());
		for(ExtendedBannerPermissions perm : packet.defaultPerms) {
			buf.writeByte((byte) perm.ordinal());
		}
		buf.writeFloat(packet.alignmentLevel);
		buf.writeInt(packet.whitelist.size());
		for(ProtectionEntry entry : packet.whitelist) {
			buf.writeInt(entry.getEntryPerms().size());
			for(ExtendedBannerPermissions perm : entry.getEntryPerms()) {
				buf.writeByte((byte) perm.ordinal());
			}
			buf.writeUUID(entry.getProfile().getId());
			buf.writeUtf(entry.getProfile().getDisplayName(), 32767);
		}
		buf.writeBoolean(packet.canSelfProtect);
		buf.writeInt(packet.protectionType);
		buf.writeBlockPos(packet.configurePos);
		buf.writeBoolean(packet.enabled);
		buf.writeBoolean(packet.isClientOP);
	}
	/**
     * Decodes a packet received from the server.
     *
     * @param buf the buffer to read from
     * @return the decoded {@link ExtendedSBannerOpenScreenPacket}
     */
	public static ExtendedSBannerOpenScreenPacket decode(PacketBuffer buf) {
		ExtendedSBannerOpenScreenPacket packet = new ExtendedSBannerOpenScreenPacket();
		int defaultPermsSize = buf.readInt();
		packet.defaultPerms = new ArrayList<>();
		for(int i = 0; i<defaultPermsSize; i++) {
			packet.defaultPerms.add(ExtendedBannerPermissions.values[buf.readByte()]);
		}
		packet.alignmentLevel = buf.readFloat();
		packet.whitelist = new ArrayList<>();
		int whitelistSize = buf.readInt();
		for(int i = 0; i<whitelistSize; i++) {
			int permListSize = buf.readInt();
			List<ExtendedBannerPermissions> profilePerms = new ArrayList<>();
			for(int j = 0; j<permListSize; j++) {
				profilePerms.add(ExtendedBannerPermissions.values[buf.readByte()]);
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
		packet.isClientOP = buf.readBoolean();
		return packet;
	}
	/**
     * Handles this packet on the client side and opens the banner GUI.
     *
     * @param pPacket the received packet
     * @param context network context supplier
     */
	public static void handle(ExtendedSBannerOpenScreenPacket pPacket, Supplier<NetworkEvent.Context> context) {
		ExtendedClientsideSeparatedScreenHandler.openBannerGUI(pPacket);
		context.get().setPacketHandled(true);
	}
}