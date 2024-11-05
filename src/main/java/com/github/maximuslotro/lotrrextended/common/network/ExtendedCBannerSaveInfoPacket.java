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
import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;
import com.github.maximuslotro.lotrrextended.common.enums.BannerProtectionType;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity.ProtectionEntry;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ExtendedCBannerSaveInfoPacket {
	public List<BannerPermission> defaultPerms;
	public float alignmentLevel;
	public List<ProtectionEntry> whitelist;
	public boolean canSelfProtect;
	public int protectionType;
	private BlockPos configurePos;

	public ExtendedCBannerSaveInfoPacket() {}

	public ExtendedCBannerSaveInfoPacket(List<BannerPermission> defaultPerms, float alignmentLevel, List<ProtectionEntry> whitelist, boolean canSelfProtect, int protectionType, BlockPos configurePos) {
		this.defaultPerms = defaultPerms;
		this.alignmentLevel = alignmentLevel;
		this.whitelist = whitelist;
		this.canSelfProtect = canSelfProtect;
		this.configurePos = configurePos;
		this.protectionType = protectionType;
	}

	public static void encode(ExtendedCBannerSaveInfoPacket packet, PacketBuffer buf) {
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
			buf.writeUtf(entry.getProfile().getName(), 32767);
		}
		buf.writeBoolean(packet.canSelfProtect);
		buf.writeInt(packet.protectionType);
		buf.writeBlockPos(packet.configurePos);
	}

	public static ExtendedCBannerSaveInfoPacket decode(PacketBuffer buf) {
		ExtendedCBannerSaveInfoPacket packet = new ExtendedCBannerSaveInfoPacket();
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
				type = new ExtendedBannerProfileFellowship(name, id);
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

	public static void handle(ExtendedCBannerSaveInfoPacket pPacket, Supplier<Context> context) {
		ServerPlayerEntity player = context.get().getSender();
		TileEntity tileEntity = player.level.getBlockEntity(pPacket.configurePos);
	    if (tileEntity instanceof ExtendedMEBannerTileEntity) {
	    	ExtendedMEBannerTileEntity bannerTE = (ExtendedMEBannerTileEntity)tileEntity;
	    	if(!ExtendedBannerProtectionManager.isBannerProtected(player.level, pPacket.configurePos, new SingleProtectionInstance.PlayerProtection(player, BannerPermission.BANNER), true)) {
	    		bannerTE.setDefaultPerms(pPacket.defaultPerms);
	    		bannerTE.setAlignmentLevel(pPacket.alignmentLevel);
	    		bannerTE.setProtectionWhitelist(pPacket.whitelist);
	    		bannerTE.setCanProtectSelf(pPacket.canSelfProtect);
	    		bannerTE.setProtectionType(BannerProtectionType.getByID(pPacket.protectionType));
	    		ExtendedSBannerOpenChangedScreenPacket returnPacket = bannerTE.verifyInfo(true, player);
	    		if(returnPacket!=null) {
	    			ExtendedPacketHandler.sendToClient(returnPacket, player);
	    		}
	    	}
		}
		context.get().setPacketHandled(true);
	}
}