package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedMerchantEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author RichardB122
 * This packet is used for telling the server that the trading main menu has been closed so that the trader can move again.
 */
public class ExtendedCStopInteractingWithInteractablePacket {

    private int interactableId;


    public ExtendedCStopInteractingWithInteractablePacket() {}

    public ExtendedCStopInteractingWithInteractablePacket(int interactableId) {
        this.interactableId = interactableId;
    }

    public static void encode(ExtendedCStopInteractingWithInteractablePacket packet, PacketBuffer buf) {
        buf.writeInt(packet.interactableId);
    }

    public static ExtendedCStopInteractingWithInteractablePacket decode(PacketBuffer buf) {
        ExtendedCStopInteractingWithInteractablePacket packet = new ExtendedCStopInteractingWithInteractablePacket();
        packet.interactableId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCStopInteractingWithInteractablePacket packet, Supplier<NetworkEvent.Context> context) {
    	World level = Objects.requireNonNull(context.get().getSender()).level;
        if (level.getEntity(packet.interactableId) != null && level.getEntity(packet.interactableId) instanceof NPCEntity) {
            NPCEntity entity = (NPCEntity) level.getEntity(packet.interactableId);
            if (!(entity instanceof ExtendedMerchantEntity)) {
                return;
            }

            ExtendedMerchantEntity interactable = (ExtendedMerchantEntity) entity;
            interactable.removePlayerFromInteracting(context.get().getSender());
        }
        context.get().setPacketHandled(true);
    }

}
