package com.github.maximuslotro.lotrrextended.common.network;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitFarmhandContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedIFarmhand;
import lotr.common.entity.npc.ExtendedINPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCOpenHiredFarmhandMenuPacket {
	private final int entityId;

	public ExtendedCOpenHiredFarmhandMenuPacket(int entityId) {
		this.entityId = entityId;
	}

	public static ExtendedCOpenHiredFarmhandMenuPacket decode(PacketBuffer buf) {
		return new ExtendedCOpenHiredFarmhandMenuPacket(buf.readInt());
	}

	public static void encode(ExtendedCOpenHiredFarmhandMenuPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public static void handle(ExtendedCOpenHiredFarmhandMenuPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity pPlayer = context.get().getSender();
			Entity entity = pPlayer.level.getEntity(msg.entityId);
			if(!(entity instanceof ExtendedIFarmhand || !(entity instanceof ExtendedINPCEntity) 
				|| !((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty()
				|| !pPlayer.getUUID().equals(((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID()))) {
				return;
			}
			PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
			ExtendedHiredUnitFarmhandContainer.writeContainerInitData(initData, msg.entityId);
			NetworkHooks.openGui(pPlayer,
				new SimpleNamedContainerProvider((windowId, playerInventory, player) -> 
				ExtendedContainerTypes.HIRED_UNIT_OVERVIEW.get().create(windowId, playerInventory, initData), 
				ExtendedHiredUnitFarmhandContainer.CONTAINER_TITLE),
				buf -> ExtendedHiredUnitFarmhandContainer.writeContainerInitData(buf, msg.entityId)
				);
		});
		context.get().setPacketHandled(true);
	}
}
