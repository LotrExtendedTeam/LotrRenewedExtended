package com.github.maximuslotro.lotrrextended.common.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileBase;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfileFellowship;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.whitelist.ExtendedBannerProfilePlayer;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerProtectionOwners;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

/**
 * Client -> Server packet used to save and apply banner configuration data.
 *
 * <p>This packet sends updated protection settings, permissions, whitelist entries,
 * and ownership type for a banner tile entity at a specific block position.</p>
 */
public class ExtendedCBannerSaveInfoPacket {
	/**
     * Default permissions applied to all users under the current protection mode.
     */
	public List<ExtendedBannerPermissions> defaultPerms;
	/**
     * Alignment level used for faction-based protection logic.
     */
	public float alignmentLevel;
	/**
     * Whitelist entries defining special access rules for specific players or factions.
     */
	public List<ProtectionEntry> whitelist;
	/**
     * Whether the banner should protect itself from unauthorized destruction.
     */
	public boolean canSelfProtect;
	/**
     * Numeric ID of the banner protection owner type.
     * See {@link ExtendedBannerProtectionOwners#getByID(int)}.
     */
	public int protectionType;
	/**
     * Position of the banner tile entity being configured.
     */
	private BlockPos configurePos;
	/**
     * Empty constructor used for decoding.
     */
	public ExtendedCBannerSaveInfoPacket() {}
	/**
     * Creates a new packet containing banner configuration data.
     *
     * @param defaultPerms     default permissions for users
     * @param alignmentLevel  faction alignment level
     * @param whitelist       whitelist entries
     * @param canSelfProtect  whether the banner protects itself
     * @param protectionType  owner type ID
     * @param configurePos    banner tile entity position
     */
	public ExtendedCBannerSaveInfoPacket(List<ExtendedBannerPermissions> defaultPerms, float alignmentLevel, List<ProtectionEntry> whitelist, boolean canSelfProtect, int protectionType, BlockPos configurePos) {
		this.defaultPerms = defaultPerms;
		this.alignmentLevel = alignmentLevel;
		this.whitelist = whitelist;
		this.canSelfProtect = canSelfProtect;
		this.configurePos = configurePos;
		this.protectionType = protectionType;
	}
	/**
     * Encodes this packet into a {@link PacketBuffer} for network transmission.
     *
     * @param packet the packet instance to encode
     * @param buf    the buffer to write to
     */
	public static void encode(ExtendedCBannerSaveInfoPacket packet, PacketBuffer buf) {
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
	}
	/**
     * Decodes a packet from the network buffer.
     *
     * @param buf the buffer to read from
     * @return the decoded {@link ExtendedCBannerSaveInfoPacket}
     */
	public static ExtendedCBannerSaveInfoPacket decode(PacketBuffer buf) {
		ExtendedCBannerSaveInfoPacket packet = new ExtendedCBannerSaveInfoPacket();
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
		return packet;
	}
	 /**
     * Handles this packet on the server thread.
     *
     * <p>Validates protection permissions and applies the configuration
     * to the target {@link ExtendedMEBannerTileEntity} if allowed.</p>
     *
     * @param pPacket the received packet
     * @param context network context supplier
     */
	public static void handle(ExtendedCBannerSaveInfoPacket pPacket, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
		TileEntity tileEntity = player.level.getBlockEntity(pPacket.configurePos);
	    if (tileEntity instanceof ExtendedMEBannerTileEntity && ExtendedServerConfig.enableBannerProtection.get()) {
	    	ExtendedMEBannerTileEntity bannerTE = (ExtendedMEBannerTileEntity)tileEntity;
	    	if(ExtendedBannerProtectionManager.getProtectionStatus(player.level, pPacket.configurePos, new SingleProtectionInstance.PlayerProtection(player, ExtendedBannerPermissions.BANNER)).isAllowed()) {
	    		bannerTE.setDefaultPerms(pPacket.defaultPerms);
	    		bannerTE.setAlignmentLevel(pPacket.alignmentLevel);
	    		bannerTE.setProtectionWhitelist(pPacket.whitelist);
	    		bannerTE.setCanProtectSelf(pPacket.canSelfProtect);
	    		bannerTE.setProtectionType(ExtendedBannerProtectionOwners.getByID(pPacket.protectionType));
	    		ExtendedSBannerOpenChangedScreenPacket returnPacket = bannerTE.verifyInfo(player);
	    		if(returnPacket!=null) {
	    			ExtendedPacketHandler.sendToClient(returnPacket, player);
	    		}
	    	}
		}
		context.get().setPacketHandled(true);
	}
}