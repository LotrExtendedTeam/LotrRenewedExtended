package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitOverviewContainer;

import io.netty.buffer.Unpooled;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExtendedCOpenHiredOverviewMenuPacket {

	private final int entityId;

	public ExtendedCOpenHiredOverviewMenuPacket(int entityId) {
		this.entityId = entityId;
	}

	public static ExtendedCOpenHiredOverviewMenuPacket decode(PacketBuffer buf) {
		return new ExtendedCOpenHiredOverviewMenuPacket(buf.readInt());
	}

	public static void encode(ExtendedCOpenHiredOverviewMenuPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
	}

	public static void handle(ExtendedCOpenHiredOverviewMenuPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity pPlayer = context.get().getSender();
			Entity entity = pPlayer.level.getEntity(msg.entityId);
			if(!(entity instanceof ExtendedINPCEntity) 
				|| !((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty()
				|| !pPlayer.getUUID().equals(((ExtendedINPCEntity)entity).getUnitInfo().getOwnerUUID())) {
				return;
			}
			ExtendedNpcHiredUnitInfoHolder info = ((ExtendedINPCEntity)entity).getUnitInfo();
			PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
			ExtendedHiredUnitOverviewContainer.writeContainerInitData(initData, msg.entityId, info.getUnitProfile(), info.getOrders());
			info.isInteracting = true;
			NetworkHooks.openGui(Objects.requireNonNull(context.get().getSender()),
				new SimpleNamedContainerProvider((windowId, playerInventory, player) ->
				ExtendedContainerTypes.HIRED_UNIT_OVERVIEW.get().create(windowId, playerInventory, initData),
				info.getHiredUnitName()),
				buf -> ExtendedHiredUnitOverviewContainer.writeContainerInitData(buf, msg.entityId, info.getUnitProfile(), info.getOrders())
				);
		});
		context.get().setPacketHandled(true);
	}

}
