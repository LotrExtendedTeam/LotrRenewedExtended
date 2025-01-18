package com.github.maximuslotro.lotrrextended.common.network;

import java.util.Objects;
import java.util.function.Supplier;

import lotr.common.entity.npc.ExtendedInteractableEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author RichardB122
 *
 * This is a dual-use packet for both handling opening the Coin Exchange menu and for telling the
 * server that the trading main menu has been closed so that the trader can move again. The boolean
 * shouldOpen is used to distinguish this.
 */
public class ExtendedCPacketStopInteractingWithInteractable {

    private int interactableId;


    public ExtendedCPacketStopInteractingWithInteractable() {}

    public ExtendedCPacketStopInteractingWithInteractable(int interactableId) {
        this.interactableId = interactableId;
    }

    public static void encode(ExtendedCPacketStopInteractingWithInteractable packet, PacketBuffer buf) {
        buf.writeInt(packet.interactableId);
    }

    public static ExtendedCPacketStopInteractingWithInteractable decode(PacketBuffer buf) {
        ExtendedCPacketStopInteractingWithInteractable packet = new ExtendedCPacketStopInteractingWithInteractable();
        packet.interactableId = buf.readInt();
        return packet;
    }

    public static void handle(ExtendedCPacketStopInteractingWithInteractable packet, Supplier<NetworkEvent.Context> context) {
    	World level = Objects.requireNonNull(context.get().getSender()).level;
        if (level.getEntity(packet.interactableId) != null && level.getEntity(packet.interactableId) instanceof NPCEntity) {
            NPCEntity entity = (NPCEntity) level.getEntity(packet.interactableId);
            if (!(entity instanceof ExtendedInteractableEntity)) {
                return;
            }

            ExtendedInteractableEntity interactable = (ExtendedInteractableEntity) entity;
            interactable.removePlayerFromInteracting(context.get().getSender());
        }
        context.get().setPacketHandled(true);
    }

}
