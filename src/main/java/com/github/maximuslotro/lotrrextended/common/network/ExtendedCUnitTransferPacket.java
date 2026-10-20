package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder.UnitHiredInformation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedCUnitTransferPacket {
	private final int entityId;
	private final String newOwner;

	public ExtendedCUnitTransferPacket(int entityId, String newOwner) {
		this.entityId = entityId;
		this.newOwner = newOwner;
	}

	public static ExtendedCUnitTransferPacket decode(PacketBuffer buf) {
		return new ExtendedCUnitTransferPacket(buf.readInt(), buf.readUtf());
	}

	public static void encode(ExtendedCUnitTransferPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeUtf(msg.newOwner);
	}

	public static void handle(ExtendedCUnitTransferPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity oldOwner = context.get().getSender();
			Entity entity = oldOwner.level.getEntity(msg.entityId);
			if(!(entity instanceof ExtendedINPCEntity) || !((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty()
					|| !oldOwner.getUUID().equals(((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID())) {
				return;
			}
			ServerPlayerEntity newOwner = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByName(msg.newOwner);
			if(newOwner!=null) {
				UnitHiredInformation hiringInfo = ((ExtendedINPCEntity)entity).getUnitInfo().getHireInfo();
				if(hiringInfo!=null) {
					LOTRPlayerData playerData = LOTRLevelData.getSidedData(newOwner);
					// verify the player has valid hire requirements
					if(hiringInfo.getAlignmentRequired() > playerData.getAlignmentData().getAlignment(((ExtendedINPCEntity)entity).getNPC().getFaction()) ||
							hiringInfo.isPledgeRequired() && !playerData.getAlignmentData().isPledgedTo(((ExtendedINPCEntity)entity).getNPC().getFaction())) {
						oldOwner.sendMessage(new TranslationTextComponent("message.lotrextended.hired_unit.transfer.invalid_stats"), Util.NIL_UUID);
					}else {
						((ExtendedINPCEntity)entity).getUnitInfo().transferUnitToPlayer(newOwner);
						oldOwner.sendMessage(new TranslationTextComponent("message.lotrextended.hired_unit.transfer.completed", newOwner.getDisplayName()), Util.NIL_UUID);
						newOwner.sendMessage(new TranslationTextComponent("message.lotrextended.hired_unit.transfer.completed_new_unit", oldOwner.getDisplayName()), Util.NIL_UUID);
					}
				}else {
					oldOwner.sendMessage(new TranslationTextComponent("gui.lotrextended.hired_unit.error.old_hiring_tooltip_1_11"), Util.NIL_UUID);
				}
			}else {
				oldOwner.sendMessage(new TranslationTextComponent("message.lotrextended.hired_unit.transfer.no_player"), Util.NIL_UUID);
			}
		});
		context.get().setPacketHandled(true);
	}
}
